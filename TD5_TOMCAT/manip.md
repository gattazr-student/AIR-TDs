

## Prepare shell
export CATALINA_HOME=/Users/gattazr/Documents/Cours/polytech/S8/CAR/TD5_TOMCAT/apache-tomcat-9.0.0.M4
export JAVA_HOME=/usr/local/var/jenv/versions/1.8.0.45/


## Compile HelloToExample
javac HelloToExample.java -cp $CATALINA_HOME/lib/servlet-api.jar

## Restart server
$CATALINA_HOME/bin/shutdown.sh && $CATALINA_HOME/bin/startup.sh 