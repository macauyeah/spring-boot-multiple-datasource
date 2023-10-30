# Spring Data JPA example

## Multiple DataSource
Project entityone, entitytwo define two different schemas' conneciton. They can be imported from other project.

For example, project applicationRunner utitlizes that two schemas to process read write operation. But spring need to defined a Primary datasource, it would be easier if you redefine the datasource configuration in applicationRunner.

Examples

- [EntityOneDataSourceConfiguration.java](applicationRunner/src/main/java/macauyeah/personal/springbootdatajpa/applicationRunner/configuration/EntityOneDataSourceConfiguration.java)
- [EntityTwoDataSourceConfiguration.java](applicationRunner/src/main/java/macauyeah/personal/springbootdatajpa/applicationRunner/configuration/EntityTwoDataSourceConfiguration.java)

### Maven dependency
In project entityone & entitytwo, you need to include dependency **spring-boot-starter-data-jpa**.

But in project **applicationRunner** ([pom.xml](applicationRunner/pom.xml)), it only need to include dependency to entityone, entitytwo. **Don't** include **spring-boot-starter-data-jpa** , becasue you will be asked to configuare one more standard jpa configuration.

## Specification
entitytwo defines a [SearchSpecification.java](
searchspecification/src/main/java/macauyeah/personal/springbootdatajpa/searchspecification/SearchSpecification.java) , it mainly uses generic type to search any entity within that project. It also supports **OneToMany** , **ManyToOne**  relationship.

Examples
- [SomethingTwoFilter.java](entitytwo/src/main/java/macauyeah/personal/springbootdatajpa/entitytwo/database/specification/SomethingTwoFilter.java)
- [QueryService.java](applicationRunner/src/main/java/macauyeah/personal/springbootdatajpa/applicationRunner/service/QueryService.java)

## Envers
to be testing

# Mariadb
example command to create mariadb database and user
```
mariadb -u root -p
create database entityone;
# create user access from any host
CREATE USER entityone IDENTIFIED BY 'entityone';
# create all permission on one database to one user
GRANT ALL PRIVILEGES ON entityone.* TO entityone;
FLUSH PRIVILEGES;
```