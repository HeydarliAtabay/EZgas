# GUI  Testing Documentation 

Authors: Atabay Heydarli, Davide Lo Bianco, Gianluca Canitano, Nadir Casciola

Date: 29/05/2020

Version: 1.0

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR

```
<Complete this table (from IntegrationApiTestReport.md) with the column on the right. In the GUI Test column, report the name of the .py  file with the test case you created.>
```

### 

| Scenario ID | Functional Requirements covered | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | 
| 1           | FRx                             |             |             
| 2           | FRy                             |             |             
| ...         |                                 |             |         
| ...         |                                 |             |             
| ...         |                                 |             |             
| ...         |                                 |             |             


# REST  API  Testing

This part of the document reports about testing the REST APIs of the back end. The REST APIs are implemented by classes in the Controller package of the back end. 
Tests should cover each function of classes in the Controller package

## Coverage of Controller methods


| class.method name | Functional Requirements covered |REST  API Test(s) | 
| ----------- | ------------------------------- | ----------- | 
| GasStationController.getGasStationById | FR4                             | getGasStationByIdApiTest() |     
|  GasStationController.getAllGasStations        | FR3.3                             |  getAllGasStationsApiTest()           |             
|GasStationController.saveGasStation        |   FR3.1                              | saveGasStationApiTest()            |             
| GasStationController.deleteGasStation       | FR3.2                                |           deleteGasStationApiTest()  |             
| GasStationController.getGasStationByGasolineType        |   FR4.5                              |        getGasStationsByGasolineApiTest()     |             
|GasStationController.getGasStationsByProximity        |  FR4.1, FR4.2                               |       getGasStationsByProximityApiTest()      |
|GasStationController.getGasStationsWithCoordinates        |  FR4.5, FR4.1, FR4.2                               |     getGasStationsWithCoordinates()        |
|GasStationController.setGasStationReport        | FR5.1                                |       setGasStationReportApiTest()      |
|UserController.getUserById        |     FR1.4                            |     getUserByIdApiTest()        |
|UserController.getAllUsers        |          FR1.3                       |      getAllUsersApiTest()       |
|UserController.saveUser        |               FR1.1                  |         saveUserApiTest()    |
|UserController.deleteUser        |               FR1.2                  |        deleteUserApiTest()     |
|UserController.increaseUserReputation        |        FR1.1                         |         increaseUserReputationApiTest()    |
|UserController.decreaseUserReputation        |           FR1.1                      |      decreaseUserReputationApiTest()       |
|UserController.login        |                            FR1.4     |       loginApiTest()      |             