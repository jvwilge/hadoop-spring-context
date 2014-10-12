hadoop-spring-context
=====================

Sample project on how to load a Spring context from Hadoop Mapper/Reducer with property file per environment (ie dev/test/prodcution)

Build the project with `mvn clean install`

Start the Hadoop job with `hadoop -jar spring-context-1.0-SNAPSHOT-job.jar`

Don't forget to provide a folder where the properties are :

`export HADOOP_OPTS="$HADOOP_OPTS -Dproperties.folder=/home/hadoop/configuration"`

In the conf directory of this project are some samples

When your job is started look for the application id (somehting like application_1413103825819_0003) so you can collect the results with yarn :

 `yarn logs -applicationId=application_1413103825819_0003 | grep ello`

 This will show the name you provided in the property file
