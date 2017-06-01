# Anagram REST API

Project for managing words within a mongoDB and fetching anagrams based on given input. 

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing purposes.

### Prerequisites

* Java 8
* Maven (3.3.9 used)
* MongoDB (3.2.9 used)

### Deploying locally

Make sure MongoDB is running and run the command:

```
$ mvn spring-boot:run
```

### Load Dictionary Into Database 

```
$ curl -i -X POST -H "Content-Type: application/json" -d @src/main/java/com/caubarreaux/dictionary/dictionary.json http://localhost:8080/words.json
```

## Running Tests

```
$ mvn test
```

## Swagger Documentation

```
http://localhost:8080/swagger-ui.html
```

## Built With

* [Java 8](http://docs.oracle.com/javase/8/docs/)
* [Spring Boot](https://projects.spring.io/spring-boot/) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [MongoDB](https://docs.mongodb.com/?_ga=2.13110334.880675633.1496115831-269637611.1496115831) - Database 

## Future Features
- Endpoint that returns a count of words in the corpus and min/max/median/average word length
- Respect a query param for whether or not to include proper nouns in the list of anagrams
- Endpoint that identifies words with the most anagrams
- Endpoint that takes a set of words and returns whether or not they are all anagrams of each other
- Endpoint to return all anagram groups of size >=*x*
- Endpoint to delete a word*and all of its anagrams*

## Authors

* **Ross Caubarreaux** [rcaubarreaux](https://github.com/rcaubarreaux)