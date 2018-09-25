# CloudCard Image Server

CloudCard Image Server is a simple application that pulls images from CloudCard into a Blackboard Transact (BbTS) database.

### Description
CloudCard Image Server allows schools using [CloudCard Online Photo Submission](http://onlinephotosubmission.com) to import their cardholders' photos directly into their BbTS database. This saves the card office time and money and helps them provide faster service to their cardholders.

### Deployment
CloudCard Image Server is a Grails web application, which runs on the Java Virtual Machine (JVM), and it can be deployed either as a WAR file running in a Tomcat application server or using the embedded Tomcat container that runs with the Grails framework.

##### Requirements
- Java Version 1.7
- Grails Version 2.4.4
- Tomcat 7
- Git
- JVM Heap 256MB
- JVM PermGen 128MB

##### System Setup
1. Make sure Java is installed and accessible via the command line. You can do this by typing `java -version`. If you get an error, [install Java](http://docs.oracle.com/javase/7/docs/webnotes/install/).
2. [Install Grails 2.4.4](https://grails.org/download.html). NOTE: Do not install the most recent version of Grails.  Your Grails version must match the version with which the application was built, so you must install Grails 2.4.4.  The easiest way to do this is to first [install sdkman](https://sdkman.io/) and the run `sdk install grails 2.4.4`.
3. You should be able to type `grails --version` and see exactly this output: `Grails version: 2.4.4`.  Do not continue until you have the correct version of Grails running at the command line.
4. Install Git
5. Change directories to the directory into which you would like to put CloudCard Image Server.  You don't need to create a subdirectory for it.  It will create a directory called `cloudcard-image-server` when you clone it.
6. Clone this project. `git clone https://github.com/sharptopco/cloudcard-image-server.git`.
7. Copy [Oracle JAR](http://www.oracle.com/technetwork/apps-tech/jdbc-112010-090769.html) into the project's lib directory.

##### Building a WAR file to deploy to Tomcat 7
6. Change into the project directory: `cd cloudcard-image-server`.
7. Build the WAR file: `grails war`
8. Follow your normal procedures for deploying the WAR file to your Tomcat server(s).  If you use multiple nodes, sticky sessions must be enabled.
9. In the home directory of the account that runs Tomcat, add a directory called `.grails`.
10. In the `.grails` directory that you just created, add the configuration file `cloudcard-image-server-config.properties`.
11. Follow the configuration steps below.

##### Running with Grails (using the embedded Tomcat Container)
6. Change into the project directory: `cd cloudcard-image-server`.
7. Start the application: `grails run-war`.
8. In the home directory of the account that runs Grails, add a directory called `.grails`.
9. In the `.grails` directory that you just created, add the configuration file `cloudcard-image-server-config.properties`.
10. Follow the configuration steps below.

##### Configuration:
- Configuration should be in the file `~/.grails/cloudcard-image-server-config.properties`.
- Example file:

```
# CloudCard OnlinePhotoSubmission api connection
cloudcard.apiURL=https://api.cloudcardtools.com/
cloudcard.accessToken={accessToken}

# bbts datasource properties
dataSource_bbts.pooled=true
dataSource_bbts.dbCreate=validate
dataSource_bbts.driverClassName=oracle.jdbc.OracleDriver
dataSource_bbts.url=jdbc:oracle:thin:@{database url or IP address}:{port number - probably 1521}/{SID - probably BBTS}
dataSource_bbts.username={database username}
dataSource_bbts.password={database password}
dataSource_bbts.properties.maxActive=-1
dataSource_bbts.properties.minEvictableIdleTimeMillis=1800000
dataSource_bbts.properties.timeBetweenEvictionRunsMillis=1800000
dataSource_bbts.properties.numTestsPerEvictionRun=3
dataSource_bbts.properties.testOnBorrow=true
dataSource_bbts.properties.testWhileIdle=true
dataSource_bbts.properties.testOnReturn=true
dataSource_bbts.properties.validationQuery=SELECT 1 FROM DUAL
```

You can also configure all the above properties through JVM System Properties by prefix each property with `cloudcard.`. For example: `cloudcard.dataSource_bbts.pooled=true`. In AWS Elastic Beanstalk you can configure the system properties for the JVM in the web interface.

To get the CloudCard API AccessToken, run the shell script `./get_token.sh`, and follow the prompts.

As with any application, it is always advisable to create a new database service account and to limit the priviledges of that account to the minimum access necessary to perform the desired tasks.  The service account for CloudCard Image Server only needs `SELECT` access to the `ENVISION.CUSTOMER` table and `SELECT, INSERT, UPDATE` access to the `ENVISION.CUSTOMER_PHOTO` table.

##### Polling
You can temporarily stop the server from pinging the CloudCard API by setting the `imageserver.pollingEnabled` config value to `false`. It defaults to true. This can be useful if you are wanting to prevent errors while interrupting service on your BbTS database.
