#!/bin/bash
set -e

pushd account-management
mvn clean
popd 

# Build the services
pushd customer
mvn clean
popd 

pushd manager
mvn clean
popd 

pushd merchant
mvn clean
popd 

pushd payment-management
mvn clean
popd 

pushd token-management
mvn clean
popd 


