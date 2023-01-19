#!/bin/bash
set -e
docker image prune -f
docker stop customer
docker rm customer
docker stop merchant
docker rm merchant
docker stop payment-management
docker rm payment-management
docker stop token-management
docker rm token-management
docker stop account-management
docker rm account-management
docker stop rabbitMq
docker rm rabbitMq
docker-compose up -d rabbitMq
sleep 10
docker-compose up -d account-management token-management payment-management customer merchant

