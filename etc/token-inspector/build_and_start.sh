echo -en "OS user is: $( id ) \n"

cd /opt/token-inspector

mvn clean package -s /home/jboss/.m2/settings.xml

# We make four distinct layers so if there are application changes the library layers can be re-used
cp -r target/quarkus-app/lib/ /deployments/lib/
cp -r target/quarkus-app/*.jar /deployments/
cp -r target/quarkus-app/app/ /deployments/app/
cp -r target/quarkus-app/quarkus/ /deployments/quarkus/

java -jar /deployments/quarkus-run.jar
