## Microservice Cache

### Clone repository
- Clone the https://github.com/raihanzohir/test-4.git repository to your local machine (PC/Laptop).

### Prepare Environment
- Navigate to the cloned repo and open terminal (i.e. Git Bash)
- Ensure you have installed Java 17 and Maven 3.6.3+
- Comment out (temporarily) the following dependency in pom.xml
    ````
    <!-- Commented temporarily -->
    <dependency>
        <groupId>com.tc.cache</groupId>
        <artifactId>TC-cache-api</artifactId>
        <version>0.0.1</version>
    </dependency>
    ````
- execute ```mvn install:install-file -Dfile=\external-sources\\TC-cache-api-0.0.1.jar -DgroupId=com.tc.cache -DartifactId=TC-cache-api -Dversion=0.0.1 -Dpackaging=jar``` for install the jar dependency in your local repository

### Building the project
- Uncomment the previously commented dependency in pom.xml
    ````
    <dependency>
        <groupId>com.tc.cache</groupId>
        <artifactId>TC-cache-api</artifactId>
        <version>0.0.1</version>
    </dependency>
    ````
- You can customize the cache size by changing value (any Integer) of ```tc.cache.size``` in application.properties file i.e. ```tc.cache.size=10``` 

- Clean and Package (Open terminal in the project root directory and run the following command)
    ````
    mvn clean package
    ````
### Run the project
   ````
    java -jar /target/TCCache-1.0.0-SNAPSHOT.jar
   ````

### Check the service endpoints
#### With CURL
- For Cache Store
  ````    
    http://localhost:8080/api/v1/caches
    curl --location --request PUT 'http://localhost:8080/api/v1/caches' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "id": 0,
    "data": "A"
    }'
  
  Output: 
  0, A
  
  http://localhost:8080/api/v1/caches
    curl --location --request PUT 'http://localhost:8080/api/v1/caches' \
    --header 'Content-Type: application/json' \
    --data-raw '{
    "id": 1,
    "data": "B"
    }'    
  ````
  - Output:
    ````
    1, B 
    0, A
    ````
  
- For Cache Get
    ````    
    curl --location --request GET 'http://localhost:8080/api/v1/caches/0'    
    ````
  - Output:
    ````
      0, A
      1, B
    ````
#### With Postman
##### - For Cache Store
  - Cache PUT 0
  <div align="center">
      <img src="/screenshots/Cache_PUT_0.png" width="100%"></img> 
  </div>
  
  - Cache PUT 1
  <div align="center">
      <img src="/screenshots/Cache_PUT_1.png" width="100%"></img> 
  </div>
  
  - Cache PUT 2
  <div align="center">
      <img src="/screenshots/Cache_PUT_2.png" width="100%"></img> 
  </div>
  
  - Cache PUT 3
  <div align="center">
      <img src="/screenshots/Cache_PUT_3.png" width="100%"></img> 
  </div>

##### - For Cache Get
  - Cache Get 1
  <div align="center">
      <img src="/screenshots/Cache_GET_1.png" width="100%"></img> 
  </div>

  - Cache Get 2
  <div align="center">
      <img src="/screenshots/Cache_GET_2.png" width="100%"></img> 
  </div>

##### Note: If any problem faced the check the Java version by ``` java --version ``` and Maven version and Java version associated with Maven by ```mvn --version```
