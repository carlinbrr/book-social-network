#!/bin/bash

cd ci-cd
pwd
sudo docker-compose pull -q
sudo docker-compose up -d