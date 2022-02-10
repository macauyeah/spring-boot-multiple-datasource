# Spring JPA

## Multiple DataSource
Project entityone, entitytwo define two different schemas' conneciton. They can be imported from other project.

For example, project applicationRunner utitlizes that two schemas to process read write operation. But spring need to defined a Primary datasource, it would be easier if you redefine the datasource configuration in applicationRunner.

[EntityOneDataSourceConfiguration.java](applicationRunner/src/main/java/macauyeah/personal/springbootdatajpa/applicationRunner/configuration/EntityOneDataSourceConfiguration.java)
[EntityTwoDataSourceConfiguration.java](applicationRunner/src/main/java/macauyeah/personal/springbootdatajpa/applicationRunner/configuration/EntityTwoDataSourceConfiguration.java)

### Maven dependency
In project entityone & entitytwo, you need to include dependency **spring-boot-starter-data-jpa**.

But in project **applicationRunner** ([pom.xml](applicationRunner/pom.xml)), it only need to include dependency to entityone, entitytwo. **Don't** include **spring-boot-starter-data-jpa** , becasue you will be asked to configuare one more standard jpa configuration.

## Envers
to be testing