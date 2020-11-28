#!/bin/bash

curl --request GET -sL \
  --url "http://localhost:8080/api/persons/$1" \
  --header 'Content-Type: application/json' \
