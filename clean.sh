#!/bin/bash
set -e

pushd utils
mvn clean
popd 


pushd account-management
mvn clean
popd 

pushd customer
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


