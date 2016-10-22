sudo service spring_server stop
git pull
git reset origin master
gradle -g \tmp clean
gradle -g \tmp build
sudo service spring_server start
