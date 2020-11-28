#!/bin/bash
echo '$1'
curl --request PUT -siL \
  --url "http://localhost:8080/api/persons/$1" \
  --header 'Content-Type: application/json' \
  --data '{"firstName":"Erika","lastName":"Musterfrau"}' \
