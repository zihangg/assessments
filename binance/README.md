# Introduction
This repository contains the microservices required for the Binance assessment provided as below:

```
PancakeSwap BE interview

Create a POC of OrderBook API NodeJs service with k8s setup

- Do not need to finish all the details; comment on what’s left to do
- Do not waste more than 2 hours
- Do focus on the setup instead of the OrderBook implementation detail**

API side:

- OrderBook microservice with min 2 pairs.
- Gateway microservice. Kind of a proxy API that redirects requests from outside.
- MQ - messaging queue which is used for communication between OrderBook and Gateway.

Kubertenes side:

- LoadBalaner or Ingress Controller*
- Deployment (for api’s)
- StatefullSet (in case you wanna create MQ cluster, or any DB (not necessary))
```
<br />

## Get started
As per assessment, the repo contains two microservices: `orderbook` and `gateway`. Within each microservice repository, there are individual readmes as a method to get the microservices up and running for testing.

Both microservices has been tested on `minikube`.

The general flow of the microservice interactions is as per assessment requirements:
1. The `gateway` microservice exposes APIs for external interactions, in this case, the `postOrder` endpoint is exposed.
2. Once the endpoint is called, the `gateway` microservice emits a message to the `order_created` topic, which the `orderbook` microservice consumes.
3. The `orderbook` microservice then handles the order through `handleOrderCreation` method.


<br />

## Installation
In general, the steps are as follows:

1. In the `gateway` repository, install `kafka` and `zookeeper` onto your kube cluster.
2. Install the `gateway` microservice.
3. Install the `orderbook` microservice.
4. (Optional) If running on minikube, run `minikube tunnel` to expose LoadBalancers to localhost.
5. Run the curl command `curl -X POST http://localhost:3000/gateway/postOrder` to test the API.

<br />

## Improvements
The microservices are, as per assessment requirements, done in minimal time. A lot of the assessment were copy and pasted (esp. kube setups) from my previous work, lacking the fine tuning needed for proper integration of microservices & kafka needed for efficient processing.

Similarly, the microservices are barebones, in which the implementation details of both `orderbook` and `gateway` is severely lacking. Given enough time, this could be fixed as well. 