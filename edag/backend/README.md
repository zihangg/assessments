# Getting Started

Please change the settings in the application properties to correctly reflect your MySQL settings.

```bash
spring.jpa.hibernate.ddl-auto=update
spring.datasource.url=jdbc:mysql://localhost:<port_number>/<db_name>
spring.datasource.username=<username>
spring.datasource.password=<password>
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```

Once the settings are set up, you can run the backend by running:

```bash
mvn spring-boot:run
```
