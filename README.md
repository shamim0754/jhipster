### Jhipster

jhipster official site says, JHipster is a development platform to generate, develop and deploy Spring Boot + Angular / React / Vue Web applications and Spring microservices.

### Setting environment

1. Install Java >8
2. Install Node.js
3. Install JHipster: npm install -g generator-jhipster
4. (optional) If you want to use a module or a blueprint (for instance from the JHipster Marketplace), install Yeoman: npm install -g yo

### Generation Application

1. mkdir myapplication
2. jhipster

Another option
https://start.jhipster.tech/#/

### Running Application

1. <b>Maven</b> ./mvnw (Linux/MacOS/Windows PowerShell) mvnw on Windows Cmd <br/>
   <b>Gradle</b> (./gradlew on Linux/MacOS/Windows PowerShell, gradlew on Windows Cmd).

### Create entity

entity sub-generator that creates full CRUD entities <br>
jhipster entity <entityName> --[options]

jhipster entity --help

Options <br/>

1. --table-name <table_name>
2. --angular-suffix <suffix>
3. --client-root-folder <folder-name>
4. --regenerate
5. --skip-server code.
6. --db
7. --skip-client

### Create controller

jhipster spring-controller <controllerName>

### Create service

jhipster spring-service <serviceName>

### JDL(JHipster Domain Language)

JDL allows to describe all your applications, deployments, entities and their relationships in a single file (or more than one)

1. https://start.jhipster.tech/jdl-studio/
2. JHipster IDE plugins/extensions(For Eclipse, VS Code and Atom)

```java
/**
 *
 * @author Md.shamim Miah
 * Date : 12-07-2019
 */

entity PostCategory {
    name String
}
entity Post {
    title String required,
    body String
}



relationship OneToMany {
  PostCategory to Post
}

dto PostCategory,Post with mapstruct

filter PostCategory

paginate PostCategory,Post with pagination

service PostCategory,Post with serviceClass
```

### Setting up existing project

1. Setting jhipster environment
2. ```xml
   <dependency>
       <groupId>io.github.jhipster</groupId>
       <artifactId>jhipster</artifactId>
       <version>1.3,1</version>
       <type>pom</type>
   </dependency>

   <dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.6.1</version>
    <exclusions>
        <exclusion>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
        </exclusion>
        <exclusion>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-jdk8</artifactId>
        </exclusion>
    </exclusions>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.5.0</version>
        <exclusions>
            <exclusion>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct</artifactId>
            </exclusion>
            <exclusion>
                <groupId>org.mapstruct</groupId>
                <artifactId>mapstruct-jdk8</artifactId>
            </exclusion>
        </exclusions>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-data-rest</artifactId>
        <version>2.9.2</version>
    </dependency>
    <dependency>
        <groupId>org.zalando</groupId>
        <artifactId>problem-spring-web</artifactId>
        <version>0.23.0</version>
    </dependency>
   ```

````

3. copy .yo-re.json file to app root directory


```json
{
  "generator-jhipster": {
    "promptValues": {
      "packageName": "com.itc.blog"
    },
    "jhipsterVersion": "6.1.2",
    "applicationType": "monolith",
    "baseName": "blog",
    "packageName": "com.itc.blog",
    "packageFolder": "com/itc/blog",
    "serverPort": "8080",
    "authenticationType": "jwt",
    "cacheProvider": "hazelcast",
    "enableHibernateCache": false,
    "websocket": "spring-websocket",
    "databaseType": "sql",
    "devDatabaseType": "mysql",
    "prodDatabaseType": "mysql",
    "searchEngine": false,
    "messageBroker": false,
    "serviceDiscoveryType": false,
    "buildTool": "maven",
    "enableSwaggerCodegen": false,
    "jwtSecretKey": "MDUwZjkyMjE2M2E1NTRjNTI3YzNjMjkyNWJmODk4ZDczOGQxNzMxY2Ux",
    "useSass": true,
    "clientPackageManager": "npm",
    "clientFramework": "angularX",
    "clientTheme": "none",
    "clientThemeVariant": "",
    "testFrameworks": [],
    "jhiPrefix": "jhi",
    "entitySuffix": "",
    "dtoSuffix": "DTO",
    "otherModules": [],
    "enableTranslation": false,
    "skipClient": true
  }
}
````

4. Copy io.github.jhipster.web.rest.errors ,io.github.jhipster.rest.util,io.github.jhipster.service.filter,io.github.jhipster.service.QueryService,io.github.jhipster.service.Criteria ,io.github.jhipster.web.TestUtil from io.github.jhipster jar to your project

Note : you can also above file copy from this repove com.itc.blog to your project

### Integration Testing

Jhipster generate integration testing for us.
