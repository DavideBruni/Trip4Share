import json
from random import seed
from random import random
from neo4j import GraphDatabase
import datetime
import numpy as np

class Neo4jDAO:

    def __init__(self, uri, user, password):
        self.driver = GraphDatabase.driver(uri, auth=(user, password))

    def close(self):
        self.driver.close()

    def create_trip(self, trip):
        with self.driver.session() as session:
            res = session.execute_write(self._create_trips, trip)
            print(res)
            
    def create_user(self, username):
        with self.driver.session() as session:
            res = session.execute_write(self._create_user, username)
            print(res)

    def create_ORGANIZED_BY(self,trip_id,username):
        with self.driver.session() as session:
            res = session.execute_write(self._create_org_rel, trip_id, username)
            print(res)
            
    def create_JOIN(self,trip_id,username):
        with self.driver.session() as session:
            res = session.execute_write(self._create_join_rel, trip_id, username)
            print(res)        
    
    def create_follow(self,to_follow,follower):
        with self.driver.session() as session:
            res = session.execute_write(self._create_follow_rel, to_follow, follower)
            print(res) 
            
    @staticmethod
    def _create_follow_rel(tx, to_follow, follower):
        result = tx.run("MATCH (r1:RegisteredUser {username: $user1}),(r2:RegisteredUser {username: $user2}) "
                        "CREATE (r1)-[f:FOLLOW]->(r2) "
                        "RETURN type(f)",user1=follower, user2=to_follow)
        return result.single()[0]
    @staticmethod
    def _create_org_rel(tx,trip_id, username):
        result = tx.run("MATCH (r:RegisteredUser),(t:Trip) "
                        "WHERE r.username = $username AND t._id = $_id "
                        "CREATE (t)-[o:ORGANIZED_BY]->(r) "
                        "RETURN type(o)",username=username, _id=trip_id)
        return result.single()[0]

    
    @staticmethod
    def _create_join_rel(tx,trip_id, username):
        rand_n = random()
        if rand_n>0.05 and rand_n<0.10:
            status = "pending"
        elif rand_n < 0.05:
            status = "rejected"
        else:
            status = "accepted"
            deleted = True
        result = tx.run("MATCH (r:RegisteredUser),(t:Trip) "
                        "WHERE r.username = $username AND t._id = $_id "
                        "CREATE (r)-[j:JOIN {status: $status}]->(t) "
                        "RETURN type(j)",username=username, _id=trip_id, status = status)
        return result.single()[0]
        
    @staticmethod
    def _create_trips(tx,trip):
        seed(1)
        if random()>0.1:
            deleted = False
        else:
            deleted = True
        depDate = datetime.datetime.fromtimestamp(int(trip['departureDate']['$date']['$numberLong'])/1000).strftime('%Y-%m-%d')
        retDate = datetime.datetime.fromtimestamp(int(trip['returnDate']['$date']['$numberLong'])/1000).strftime('%Y-%m-%d')
        
        result = tx.run("CREATE (t:Trip) "
                        "SET t._id=$_id, t.destination = $destination, t.title = $title, "
                        "t.departureDate = date($departureDate), "
                        "t.returnDate = date($returnDate), t.imgUrl = $imgUrl, t.deleted=$deleted "
                        "RETURN t.destination ",_id=trip['_id']['$oid'], destination=trip['destination'],
                       title=trip['title'], departureDate= depDate,
                       returnDate =retDate, imgUrl="",deleted=deleted)
        return result.single()[0]


    @staticmethod
    def _create_user(tx,username):
        result = tx.run("CREATE (r:RegisteredUser) "
                        "SET r.username =$username "
                         "RETURN r.username ",username = username)
        return result.single()[0]




if __name__ == "__main__":
    # ---------------------- LOAD MONGO DATASET -----------------------
    with open('dataset_trips_extended_mongo.json', 'r', encoding="utf8") as f:
        data = json.load(f)
    data[0].keys()

    with open('dataset_users.json', 'r', encoding="utf8") as f:
        users = json.load(f)
        connection = Neo4jDAO("bolt://localhost:7687", "neo4j", "root")
    
    # ----------------------  START CYPHER ------------------
    
    #TRIPS
    for trip in data:
        connection.create_trip(trip)

    #USERS
    for user in users:
        connection.create_user(user['username'])
    
    #ORGANIZER, A SORT OF INFLUENCER
    organizer = users[1:228]
    start = 0
    end = 8
    try:
        for user in organizer:
            for i in range(start,end):
                connection.create_ORGANIZED_BY(data[i]['_id']['$oid'],user['username'])
                print(user['username']," - ORGANIZES ->",data[i]['_id']['$oid'])
            start = end
            end = end + 8
        while(i<1874):
            connection.create_ORGANIZED_BY(data[i]['_id']['$oid'], users[i%1135]['username'])
            print(user['username'], " - ORGANIZES ->", data[i]['_id']['$oid'])
            i=i+1
    except:
        print(end)

    # JOIN TRIP
    mu, sigma = 8, 7 # mean and standard deviation
    s = np.random.normal(mu, sigma, len(data))
    start = 0
    end = 6
    joiner = users[1:]
    j=0
    for trip in data:
        print(trip['_id']['$oid'])
        for i in range(0,int(s[j])):
            try:
                u = joiner[int(i+(random()*1120))]['username']
            except:
                u = joiner[i]['username']
            print(u)
            connection.create_JOIN(trip['_id']['$oid'],u)
        j=j+1

    # FOLLOWERS: INFLUNCER SECTION
    mu, sigma = 600,120 # mean and standard deviation
    followers = np.random.normal(mu, sigma, len(organizer))
    followers = np.sort(followers)
    
    organizer = users[1:228]
    j=0
    for user in organizer:
        for i in range(0,int(followers[j])):
            follower=users[-(i+1)]['username']
            if follower != user['username']:
                print(follower," - FOLLOWS -> ", user['username'])
                connection.create_follow(user['username'], follower)
        j=j+1

    #OTHER FOLLOWERS
    mu, sigma = 150,40 # mean and standard deviation
    followers = np.random.normal(mu, sigma, len(users[228:]))
    followers = np.sort(followers)
    connection = Neo4jDAO("bolt://localhost:7687", "neo4j", "root")
    normal_user = users[228:]
    j=0
    for user in normal_user:
        for i in range(0,int(followers[j])):
            follower=users[-(1+i)]['username']
            if follower != user['username']:
                print(follower," - FOLLOWS -> ", user['username'])
                connection.create_follow(user['username'],follower)
        j=j+1
    

    connection.close()