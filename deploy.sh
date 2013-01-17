cd codechef-social-server
git pull origin master
./gradlew war
sudo rm -rf /var/lib/tomcat7/webapps/codechef-social-server*
cp ./build/libs/codechef-social-server.war /var/lib/tomcat7/webapps/
sudo service tomcat7 restart
