# Ottomoto - Test Automation

This is Ottomoto automatic testing project.

## Requirements
- JDK 1.8
- Maven

## Download repository
- Clone repository

```
git clone 
```

## Run the tests

### Run all test
```
mvn clean test -P <profile>
```
There are two profiles for the use of test scenarios according to the environment where the tests will be run.

`profile` equals `test_uat` if the environment where the tests are to be run is the pre-production environment.
```
mvn clean test verify -P test_uat
```
`profile` equals `prod` if the environment where the tests are to be run is the production environment.
```
mvn clean test verify -P prod
```

### Run tests by tags

```
mvn clean test verify -P test_uat -D"cucumber.filter.tags"="@tag"
```
If you want to run some tests only by existing tags, you must replace the `tag` parameter by the tag that corresponds to the type of tests you want to run.

The existing tags in the test scenarios are:
```

```

#### Examples:
```

mvn clean test -D"cucumber.filter.tags"="@testFeature or @LoginInvalidEmailFormat" -P test_uat
```

## See the test report
You can see the test report and all the information on the test scenarios at
```

```