import random
import datetime

import redis
import json

def convertDate(date):
    return datetime.datetime.fromtimestamp(int(date['$date']['$numberLong'])/1000).strftime('%Y-%m-%d')


def createJSON(json):
    data = {}
    data['destination'] = json['destination']
    data['title'] = json['title']
    data['departureDate'] = convertDate(json['departureDate'])
    data['returnDate'] = convertDate(json['returnDate'])
    data['destination'] = json['destination']
    # data['imgURL'] = json['imgURL']
    return data

def generateTTL(departureDate):
    d1 = datetime.datetime.fromtimestamp(int(departureDate['$date']['$numberLong'])/1000)
    d2 = datetime.datetime.now()
    return int((d1 - d2).total_seconds())

r = redis.Redis()

file = open("dataset_users.json")
dataset_users = json.load(file)
file.close()

file = open("dataset_trips_extended_mongo.json", encoding="utf8")
dataset_trips = json.load(file)
file.close()

active_trips = [trip for trip in dataset_trips if convertDate(trip['departureDate']) > datetime.datetime.now().strftime('%Y-%m-%d')]

for trip in active_trips:

    samples = random.sample(dataset_users[1:], trip['likes'])
    for sample in samples:
        key = "trip4share:" + sample['username'] + ":" + trip['_id']['$oid']
        value = createJSON(trip)
        ttl = generateTTL(trip['departureDate'])

        r.set(key, str(value))
        r.expire(key, ttl)
        print(key, value, ttl)