#!/usr/bin/env bash
sudo service spring_server stop
git pull
git reset origin/master --hard
gradle -g \tmp clean
gradle -g \tmp build
sudo service spring_server start
