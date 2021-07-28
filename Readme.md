# Cucumber Selenium tutorial
How to test a website using Cucumber and Selenium

## How to run
Start Selenium Grid via docker-compose from _src/test/resources/selenium_:  
```
docker-compose -f selenium-grid.yml up
```
Run tests with Maven:  
```
mvn clean verify -Plocal,html-report -Dtags=@ui -Dconcurrent=true -Dbrowser.type=chrome
```