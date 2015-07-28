# CloudCard Image Server

CloudCard Image Server provides and API for accessing a Blackboard Transact (BbTS) database.

### Description
CloudCard Image Server was originally developed to allow shcools using [CloudCard Photo Upload](http://start.cloudcardtools.com) to import their cardholders' photos directly into their BbTS database. This saves the card office time and money and helps them provide faster service to their cardholders.

One of the added benefits of putting an CloudCard Image Server on top of the BbTS database was that it allowed applications other than CloudCard to access cardholder photos. We add super long, random, secure keys to each photo to allow photos to be used in websites while preventing mass image scraping by internet bots. 

### Features
- Provides secure access your cardholders' ID card images, so that you can display them in web pages and apps.
- Allows other applications (i.e. [CloudCard Photo Upload](http://start.cloudcardtools.com) to import images into your BbTS DB

### Deployment
CloudCard Image Server is a Grails web application, which runs on the Java Virtual Machine (JVM), and it can be deployed either as a WAR file running in a Tomcat application server or using the embedded Tomcat container that runs with the Grails framework.

##### Requirements
- Java Version 1.7
- Grails Version 2.4.4
- Tomcat 7
- Git
- JVM Heap 1GB
- JVM PermGen 256MB

##### System Setup
1. Make sure Java is installed and accessible via the command line.  You can do this by typing `java -version`.  If you get and error, [install Java](http://docs.oracle.com/javase/7/docs/webnotes/install/).
2. [Install Grails 2.4.4](https://grails.org/download.html). NOTE: Do not install the most recent version of Grails.  Your Grails version must match the version, with which the application was built, so you must install Grails 2.4.4.  The easiest way to do this is to first [install GVM](http://gvmtool.net/) and the run `gvm install grails 2.4.4`.
3. You should be able to type `grails --version` and see exactly this output: `Grails version: 2.4.4`.  Do not continue until you have the correct version of Grails running at the command line.
4. Change directories to the directory into which you would like to put CloudCard Image Server.  You don't need to create a subdirectory for it.  It will create a directory called `cloudcard-image-server` when you clone it.
5. Clone this project. `git clone https://github.com/sharptopco/cloudcard-image-server.git`.

##### Building a WAR file to deploy to Tomcat 7
6. Change into the project directory: `cd cloudcard-image-server`.
7. Build the WAR file: `grails war`
8. Follow your normal procedures for deploying the WAR file to your Tomcat server(s).  If you use multiple nodes, sticky sessions must be enabled.

##### Running with the embedded Tomcat Container
6. Change into the project directory: `cd cloudcard-image-server`.
7. Start the application: `grails run-war`.

##### Configuration:
Comming Soon
