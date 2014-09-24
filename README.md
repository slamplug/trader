trader
======

Test trader app utilising microservices using Spring Boot / AngularJS / ActiveMQ / Jetty and built with Gradle

git clone https://github.com/slamplug/trader.git

copy trader/trader_mq/activemq.xml to <ACTIVEMQ_HOME>/conf and restart

All applications can be run using gradle in place or a fat jar can be produced including all dependencies to be shipped

To run in place
- cd trader/trader_<app>/
- gradle clean run

To build fat jar
- cd trader/trader_<app>/
- gradle clean bootRepackage

The run using java -jar <jarfile>

Jetty server ports
- trader_collect = 8080
- trader_service = 9080
- trader_ui = 7080

Accesss trader UI on URL http://localhost:7080/traderui/#/summary



