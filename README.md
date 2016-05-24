# mico-client
---
MICO Client is a Java Client to connect to MICO Platform (http://www.mico-project.eu/).
This provides methods to inject content to platform and a query client for querying the platform backend for content analysis results.

## Build Connector
---
```
git clone https://github.com/zaizi/mico-client.git
cd mico-client
git checkout develop
mvn clean install -DskipTests
```

## Use as a maven dependency
```
<dependency>
    <groupId>org.zaizi</groupId>
    <artifactId>mico.client</artifactId>
    <version>2.0-SNAPSHOT</version>
</dependency>
```

