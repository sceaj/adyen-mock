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
 
## Features
 
### Currently Implemented

* None

### Planned

* Mimics (portions of) the Adyen system
    * Enables development and testing without access to real Adyen system.
    * Supports Adyen webhooks.
    * Provides control of various configurations and data, enabling recreation of actual production issues.
* Allows passthrough mode to proxy requests to Adyen test system.
    * Defaults to not passthrough.
    * Implements throttling in passthrough mode.
    * Enables validation of the Adyen mock definitions with the Adyen test environment definitions.
* Provides easy extension to support multiple versions of the Adyen APIs (goal, i.e. maybe it's not "easy")


