test-auto
=========

Test Automation Framework for testing GUI, API, Oracle, MongoDB, Solr, Redirect, etc

Instructions
=========

At this moment there is no binary license for Oracle Java driver so we need to download the jar manually then upload into local repo. First we need to download ojdbc6.jar from http://www.oracle.com. Then do 
```sh
mvn install:install-file -DgroupId=com.oracle -DartifactId=<ARTIFACT-ID> -Dversion=<VERSION> -Dpackaging=jar -Dfile=<PATH-TO-OJDBC-JAR> -DgeneratePom=true
```
Replace <ARTIFACT-ID> with example "ojdbc6"
Replace <VERSION> with example "6"
Replace <PATH-TO-OJDBC-JAR> with example "lib/ojdbc6.jar"


Run all tests
```sh
mvn test
```


Run single tests
```sh
mvn -Dtest=<TEST-CASE-NAME> test
```
Replace <TEST-CASE-NAME> with test case name example "GoogleTest"


Run a suite
```sh
mvn test -DsuiteXmlFile=<PATH-TO-SUITE-XML>
```
Replace <PATH-TO-SUITE-XML> with suite name example "configs/suiteSites.xml"
