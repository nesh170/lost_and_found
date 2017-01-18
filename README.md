Lost & Found
===================

lost and found is created by:-
- Ankit Kayastha
- James Mosca
- Shelley Wu
- Sivaneshwaran Loganathan

lost and found is an application developed as a final project for the CS316 - Database course at Duke University. Lost & found aims to tackle all the lost & found problems at Duke by creating a simple system for people to find their lost items easily and reduce the amount of work for people who found a lost item.

Technologies
------------------
To create the backend, we use the following tools:-
 - Java 8
 - Gradle Build Tools
 - Amazon AWS Java Client
 - Duke Streamer OIT
 - PostgreSQL
 - Jooq

Refer to the build.gradle file to learn more about the tools used

Design
-------------
The general design pattern for this project’s backend uses three major elements: Request-Response, Controller-Service, and Database Accessor. They are each discussed below.

 - Request-Response
Any HTTP Post request will require a request model in our database that extends the StdRequest. The StdRequest contains 2 attributes, uniqueId and access token. This allows us to run a pre(request) method which basically runs an authorization check whether the unique_id is associated with the access token in the database. This increases our security as each API post call requires the user to be logged in so they would have a valid access_token. The request model then contains each attribute that is required for the corresponding endpoint. 
A HTTP Request will require a response. A response model will either be a StdResponse or a StdResponseWithBody. This is then returned as a ResponseEntity through a method, wrap(response). This allows us to easily return error codes and the error messages depending on what the status of the request is. In addition to that, the StdResponseWithBody easily allows us to add any kind of object into the StdResponse and through Spring’s ResponseEntity type, it will convert it into a JSON object to be displayed in the frontend.
 - Controller-Service
The controller, service model allowed us to easily separate the endpoint code from the business logic. The controller just has code about the endpoints while the service contains the logic to call the database accessor, business logic and the external service.
 - Database Accessor
Each response from the database has it’s own separate object. For example, the query ‘SELECT * FROM lost_and_found_db.lost_items’ will store the output in a List<LostItem> object where the LostItem will contain all the attributes of a lost item.

Deployment
-----------------
The server is hosted at [colab-sbx-122](http://colab-sbx-122.oit.duke.edu). Currently it is a Ubuntu 14.04 stack with the appropriate Java, Gradle and PostgreSQL. The backend endpoints are on port 8080.

- PostgreSQL 9.3.14
Created a database called 'lost_and_found_db'

- Java and Gradle
In order to build and run the project we ran the following commands:-
```bash
gradle -g/tmp build
rm nohup.out
killall java
nohup java -jar build/libs/lost_and_found-0.1.0.jar &
```
- Properties file
	- There are a few properties file which are not on our repository and are kept in secret as the contain passwords. Therefore, in order to build the project, we have to manually add them to the server. The property files are:-
		- gradle.properties
		- application.properties
		- aws.properties

- Amazon 
	- In order to use the Amazon AWS Java client, you have to set up the credentials based on the instructions to [create your credentials](http://docs.aws.amazon.com/sdk-for-java/v1/developer-guide/setup-credentials.html)

Limitations
-----------
There are a few limitations to our systems and some bugs that need to be fixed:-
 - Maximum file upload size is only 1MB
 - Uploading a .png leads to a CORS error
 - The current design has duplicated code between lostItem and foundItem (might change in the future)
 - The tagMatching algorithm is very basic
