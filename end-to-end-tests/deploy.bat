docker image prune -f
docker ps -aq | xargs docker stop | xargs docker rm
docker-compose up -d rabbitMq
timeout /t 10
docker-compose up -d account-management token-management payment-management customer merchant

