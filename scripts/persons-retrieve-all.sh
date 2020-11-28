#!/bin/bash

curl --request GET -sL \
  --url 'http://localhost:8080/api/persons' \
  --header 'Content-Type: application/json' \
