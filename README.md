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
