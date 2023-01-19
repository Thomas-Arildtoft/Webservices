#!/bin/bash
set -e

./build.sh

./deploy.sh 
sleep 5

pushd end-to-end-tests
./test.sh
popd

# Cleanup the build images
docker image prune -f
