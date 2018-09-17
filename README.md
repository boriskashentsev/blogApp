# blogapp

recruitment task

## Background

This was my first time creating a Java based backend. It was not easy. And even now I can spot couple of things that need additional work. But basic functionality is there.

## How-to run

1. Navigate to `blogapp` folder that contains `pom.xml` file.
2. Run command 
   ```
   mvn clean install tomcat7:exec-war-only
   ```
   It will create a jar package. In my case it is `blogapp.jar` which will be created in a `target` folder.
3. Navigate to the `target` folder and run
   ```
   java -jar blogapp.jar
   ```
4. The last step is to open browser of your choice and navigate to [localhost:8080](localhost:8080).

## Additional information

Backend uses DataBase that is created automatically on the first run of the application: `jdbc:derby:webAppDB;create=true`.

Structure of the DB is as follows:

TIMESTAMP | TITLE | TEXT
--- | --- | ---
BIGINT UNIQUE | VARCHAR(256) | VARCHAR(4096)

There wasn't any particular reason why I choose those sizes for `TITLE` and `TEXT`. However, `TIMESTAMP` has been chosen to be the unique value, as I assumed that user wouldn't be able to create two separate notes at the same millisecond.

## Things that need additional work

* If user puts new line in the title or text of the blog post, application won't be able to present the post again. Luckily it doesn't affect the deleting of the note.
* When user entered only title of text of the note, application won't add if to the list of posts. I will consider disabling the `save post` button until both fields are not empty.
* Currently response is fully constructed in servlet's code. It would be nice to find out how to take `javascript` and `css` pieces out of it.
* Give second thought about the table structure. Possibly add separate unique key, in case user would want to do editting in the future.


There might be more problems with the code. But I consider issues written above the most crucial.

## Used materials

* As it was my first time creating something on Java EE platform, I honestly started with [Java EE Tutorial](https://javaee.github.io/tutorial/toc.html). Unfortunately, the tutorial was too big to go through it all. But I was able to find some nice basic commands and strategies.
* Next thing that got my interest was the obility to embed a server and Java web application. [This tutorial](https://www.theserverside.com/tutorial/How-to-embed-Tomcat-and-Java-web-apps-in-an-executable-JAR) had very nice points. Although, after reading about Glass Fish in above tutorial, switching to Tomcat felt strange.
* Now that I had the server nicely wrapped in the jar I had to learn about connecting Database to my future servlet. I spent several hours going back and forth with [this tutorial](https://www.journaldev.com/1997/servlet-jdbc-database-connection-example), and by the end I had pretty good idea on how to use Apache Derby for for my needs.
* Besides mentined earlier I also looked though [this tutorial](http://www.ntu.edu.sg/home/ehchua/programming/java/javaservlets.html) and some others. But now it would be impossible to find them all. But here is a trick: [Google](https://google.com) was in the center of all the findings. So if you didn't find mentined earlier material helpful enough - `Google` is your friend.

#### Boris Kashentsev