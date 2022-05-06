# Task: Microservice Cache

The mission is to implement a java microservice that provide the functionality described by the [OpenAPI](https://en.wikipedia.org/wiki/Open_API) spec included in the jar file.

The cache must have a specifc size and we must keep in that cache the values that are used recently. For example:

````
Cache size = 3

PUT(0, A)
PUT(1, B)
PUT(2, C)
PUT(3, D)

Cache content:

key, value
3, D
2, C
1, B

PUT(4, X)

Cache content: 
4, X
3, D
2, C

As you can see the (1, B) was removed because the cache size is actually 3

GET(2)

Cache content: 
2, C
4, X
3, D
````

## OpenAPI Specification
[Specification] Included in [TC-cache-api-0.0.1.jar](/external-sources/TC-cache-api-0.0.1.jar)

## Before start

You should carry out the task according to the following instructions:

1. Fork and clone this repo.
2. Download TC-cache-api-0.0.1.jar [TC-cache-api-0.0.1.jar](/external-sources/TC-cache-api-0.0.1.jar)
3. execute ```mvn install:install-file –Dfile=TC-cache-api-0.0.1.jar -DgroupId=com.tc.cache -DartifactId=TC-cache-api -Dversion=0.0.1``` for install the jar dependency in your local repository
4. When done, submit (push) all your artifacts to the forked repo.

## You must consider the following conditions when submitting the task:
- Check that all the tests for the microservice passed (successfully)
- Achieve an 80% of code coverage
- We will check with this postman collection [TC - Cache.postman_collection.json](/external-sources/TC - Cache.postman_collection.json)
- Write clean and well-structured code, whenever possible.
- All the parts that can be variables (eg: cache size) must be in a configuration file.
- If needed, include a **readme** file with further instructions to build and execute the microservice and its associated tests.

## Contact
In case you might have questions, drop us a line to tcloud.research@gmail.com
