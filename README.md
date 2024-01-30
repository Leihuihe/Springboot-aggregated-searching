# SpringBoot 

- Spring Boot 2.7.x
- Spring MVC
- MyBatis + MyBatis Plus 
- Spring Boot 
- Spring AOP 
- Spring Scheduler
- Spring 

### DB

- MySQL 
- Redis 
- Elasticsearch 

### Utils

- Easy Excel 
- Hutool 
- Gson 
- Apache Commons Lang3 
- Lombok 


## Business features

- Provide sample SQL (user, post, post likes, post favorites table)
- User login, registration, logout, update, retrieval, permission management
- Post creation, deletion, editing, updating, database retrieval, ES flexible retrieval
- Like and unlike posts
- Collection of posts, cancellation of collections, and retrieval of collected posts
- Full post synchronization ES, incremental synchronization ES scheduled tasks
- Support file upload by business

### Unit testing

- JUnit5


### MySQL Database

1） `application.yml` 

```yml
spring:
  datasource:
    driver-class-name: com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/my_db
    username: root
    password: 123456
```

2）run `sql/create_table.sql` for creating tables

3）start `http://localhost:8101/api/doc.html` 



### Elasticsearch searching engine

1）configure `application.yml` 

```yml
spring:
  elasticsearch:
    uris: http://localhost:9200
    username: root
    password: 123456
```

2）Duplicate `sql/post_es_mapping.json` for using Elasticsearch API or Kibana Dev Tools to create index

```
PUT post_v1
{
  sql/post_es_mapping.json 
}
```

3）Start Elasticsearch and sync data with MySQL

Find `FullSyncPostToEs` and `IncSyncPostToEs` under job folder, remove comment for `@Component` 

```java
// todo 
//@Component
```
