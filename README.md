# LP Trips Handler

This application is responsible to calculates the trips from touch ons and touch offs.
It reads the data from CSV file of raw records and generates another CSV file listing all the trips. 




### Tech Stack
* Java 17
* Spring boot 3.1.1
* Gradle 8.1.1
* Spock Framework for unit test


### Assumptions
* Taps always start with tap on for each PAN.
* Edge cases might not be implemented
* busId and companyId are not considered in the trip calculation logic
* Date times assumed that are in order
* CSV file assumed to be almost valid.
* As it was not too critical for the scope of this project, Date Times assume LocalDateTime.

### Running the application
After cloning the project:
```
cd lp-trips-handler
./gradlew bootRun
```
it tries to read taps.csv from the root of the project and then create corresponding trips.csv next to it.