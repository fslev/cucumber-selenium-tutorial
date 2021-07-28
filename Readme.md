# Cucumber Selenium tutorial
How to test a website using Cucumber and Selenium

## How to run
Start Selenium Grid via _docker-compose_ from _src/test/resources/selenium_:  
```
docker-compose -f selenium-grid.yml up
```
Run tests with Maven:  
```
mvn clean verify -Plocal,html-report -Dtags=@ui -Dconcurrent=true -Dbrowser.type=chrome
```

## Application 
The test application is a Grocery web page:  
<img src="grocery_app.png"/>

## Dependencies used
- [**selenium-jutils**](https://github.com/fslev/selenium-jutils)
- [**cucumber-jutils**](https://github.com/fslev/cucumber-utils)
- Selenium and Cucumber for java 
