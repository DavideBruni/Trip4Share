# Trip4Share
Trip4Share is a web-application that connects people around the world to travel together. <br>
The idea behind Trip4Share is that if a user planned to go somewhere, he/she can share the trip plan with the network in such a way to find other people with the same interest that want to join him/her. So on one side the user can create him/her own trip and get in touch with other users that sent a join request and the user could also receive reviews from them. On the other side, users can browse through available trips shared by other users, search using filters as price, date, destination and popularity and, if interested, can send a join request to the trip organizer. Trip4Share can also show to the users the most popular destination based on criteria like price, period and tags.<br><br>
The application includes also a social network side where people can follow each others in such a way to see trips shared by friends or to receive suggestion based on people they might know or places they might like to visit.<br>


## Required software
For this session it is required to have installed:
<ul> 
<li> Java Open JDK 19. </li> 
<li> Apache Maven 4.x version. </li>
<li> Apache Tomcat 9.x. </li>
<li> Neo4j Community Edition 5.3.x </li>
<li> MongoDB Community Edition 6.x </li>
</ul>

## Updating configuration parameters
In this version of this App, the configuration parameters are set in the following files:
<ul>
<li> it.unipi.lsmsd.ecommerce.dao.base.BaseDAOMongo </li>
<li> it.unipi.lsmsd.ecommerce.dao.base.BaseDAONeo4j </li>
</ul>
In a future version these parameters are going to be moved to an external configuration file.

## Running the application by using IntelliJ 
Open the maven project in IntelliJ. After that, follow <a href="https://www.jetbrains.com/idea/guide/tutorials/working-with-apache-tomcat/using-existing-application/">these steps</a> to run the application.

## Running the application directly from Apache Tomcat 9.x
The [Trip4Share.war](Trip4Share.war) file is provided. Follow <a href="https://tomcat.apache.org/tomcat-9.0-doc/deployer-howto.html">these steps</a> to run the application.
