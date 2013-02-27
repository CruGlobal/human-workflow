# Cru Web App Quickstart

Get started quickly using our most cutting edge stack!

* JBoss AS 7
* Arquillian
* TestNG
* JAX-RS (EE6)
* CDI (EE6)
* Hibernate
* Google Guava
* Google Gson
* AngularJS
* Twitter Bootstrap
* HTML5 Boilerplate
* Font Awesome
* underscore.js

## Up and Running with Hello World

### Without an installed JBoss

To run: `mvn package jboss-as:run` and visit <http://localhost:8080/cru-quickstart/>

To test: `mvn test -Parquillian-jbossas-managed`

### With an installed JBoss (much faster for development)

To run: `mvn package jboss-as:deploy` and (depending on your configuration) visit <http://localhost:8080/cru-quickstart/>

To test: `mvn test -Parquillian-jboss-remote`