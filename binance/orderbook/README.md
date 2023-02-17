# Introduction
This repository contains the barebone implementation of the `orderbook` microservice for the Binance assessment.

# Installation
1. (Required) Install `kafka` and `zookeeper` from the `gateway` repository.
2. Install the `orderbook` microservice using the following commands:
```
cd helmchar

helm install orderbook orderbook
```