#!/bin/bash

curl --request DELETE -siL \
  --url "http://localhost:8080/api/persons/$1" \
  --header 'Content-Type: application/json'
