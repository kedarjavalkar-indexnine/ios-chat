### To package the example run the following from the root dropwizard directory.
    mvn clean package
### Creating a local h2 database
    java -jar target/ios-chat-1.2.2.jar db migrate example.yml
### Running server
    java -jar target/ios-chat-1.2.2.jar server example.yml

---

## APIs

 **Creating a user**
 
`curl -X POST http://localhost:8080/users-H 'cache-control: no-cache' -H 'content-type: application/json' -d '{ "os":"ios", "name":"kedar","status":"active" }' `

---

**Getting all users**

`curl -X GET http://localhost:8080/users -H 'cache-control: no-cache'`

---

**Posting a Chat**

`curl -X POST http://localhost:8080/chat/user/1 -H 'cache-control: no-cache' -H 'content-type: application/json' -d '{ "text" : "it this 3st message", "from" : "kedar", "lastTimestamp" : 0 }'`

---

**Getting chat by user**

`curl -X GET http://localhost:8080/chat/user/1/1514441805067 -H 'cache-control: no-cache'`

`curl -X GET http://localhost:8080/chat/user/1/1514441805067?status=read -H 'cache-control: no-cache'`

`curl -X GET http://localhost:8080/chat/user/1/1514441805067?status=unread -H 'cache-control: no-cache'`

---

**Marking messages as READ**

`curl -X PUT http://localhost:8080/chat/user/1/read/1515057792329 -H 'cache-control: no-cache'`

---

**Getting all chats for admin**

`curl -X GET http://localhost:8080/chat/1514465013034 -H 'cache-control: no-cache'`

`curl -X GET http://localhost:8080/chat/1514465013034?status=unread -H 'cache-control: no-cache'`

`curl -X GET http://localhost:8080/chat/1514465013034?staus=read -H 'cache-control: no-cache'`

---

**Getting server time in millis**

`curl -X GET http://localhost:8080/timestamp -H 'cache-control: no-cache'`

---

