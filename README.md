Java 8 and MySql 5.7 should be installed.

### Steps to run

* Create a file named `codechef_social.conf` and place it in `/etc/codechef`. Sample file is included in the code.
* Create database named `codechef` in mysql
* Import the mysql [dump](https://www.dropbox.com/s/5q8n7xypl4kvg7j/structure.sql?dl=0) to your mysql database
* In file `hibernate.cfg.xml`, change `connection.url` and `password` to your mysql database url and password
* Run `./run.sh` to start the server


### MySql dump

* After importing the dump, you need to create one entry manually in the users table.
* Enter a valid accessToken and refreshToken for the particular user.
* Username should be same as codechef username.
* Keep AppToken same as the AccessToken
* Also make sure that you update the admin field in the conf file to the username you created in above steps.

### Credits:
BoilerPlate of jersey code picked up from [JasonRay](https://github.com/jasonray/jersey-starterkit)

