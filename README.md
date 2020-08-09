# Load Balancer
Library for sending requests to different hosts with possible two algorithms of load balancing.

## Installation
Use [maven](https://maven.apache.org/) command from project root directory:
```bash
mvn clean install
```

## Usage

### Command line usage
From project target directory execute jar file
```
java -jar load-balancer-$version-jar-with-dependencies.jar --lbvariant $load_balancer_algorithm
```

Possible $load_balancer_algorithm values:
* round - sequentially rotation each of the hosts;
* weight - take the first host that has a load under
  0.75, if all hosts are above 0.75 will take the one with the lowest load.

Request body will be read from console input and request process according to specified algorithm

Example: java -jar load-balancer-0.1-SNAPSHOT-jar-with-dependencies.jar --lbvariant round

### Call from code as a service
Service class:
```java
com.nkostiv.loadbalancer.LoadBalancerService
```
To use the service instance of this class should be created.
Constructor – takes two arguments. The first argument is a list of host
instances that should be load balanced. The second argument is the variant of load
balancing algorithm to be used (round or weight).
To send request for processoing method handleRequst(Request request) should be called.

##Implementation details
In command line usage few servers already prepared with random load for demo purpose.

### Open questions and assumtions
* Based on task description not clear about possible multithreading execution. From my point of view task done using minimum viable product approach. 
* Was not implemented exception handling from a host and possibility that host not alive at all so list of hosts should be refreshed.
