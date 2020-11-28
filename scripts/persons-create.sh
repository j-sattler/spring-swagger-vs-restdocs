#!/bin/bash

curl --request POST -siL \
  --url 'http://localhost:8080/api/persons' \
  --header 'Content-Type: application/json' \
  --data '{"firstName":"Max","lastName":"Mustermann"}' \
