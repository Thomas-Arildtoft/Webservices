#!/bin/bash
set -e
docker image prune -f
docker stop $(docker ps -aq)
docker rm $(docker ps -aq)
docker-compose up -d rabbitMq
sleep 10
docker-compose up -d account-management token-management payment-management customer merchant

