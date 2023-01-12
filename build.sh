#!/bin/bash
set -e

pushd utils
./build.sh
popd 

pushd account-management
./build.sh
popd 

# Build the services
pushd customer
./build.sh
popd 

pushd merchant
./build.sh
popd 

pushd payment-management
./build.sh
popd 

pushd token-management
./build.sh
popd 


