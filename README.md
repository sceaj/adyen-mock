# adyen-mock
A mock implementation of some Adyen APIs that supports local development and testing

## Quick Start
Once you have pulled the repo, you should be able to build it by opening a shell, navigating to the root directory (`adyen-mock`) and issuing the command:
```
./gradlew clean build`
```

If the build is successful, you should be able to demo the application by running [MariaDB](https://mariadb.org/) in docker (you will need [Docker Desktop](https://www.docker.com/) installed).  To run MariaDB, issue the command:
```
docker compose up --detach mariadb
```
In response, you should see something similar to:
```
[+] Running 1/0
 ✔ Container adyen-mock-mariadb-1  Running
 ```
 
 Once MariaDB is running, you can start the application with the command:
 ```
 ./gradlew bootRun
 ```