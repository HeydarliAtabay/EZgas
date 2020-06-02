# GUI  Testing Documentation 

Authors: Atabay Heydarli, Davide Lo Bianco, Gianluca Canitano, Nadir Casciola

Date: 29/05/2020

Version: 1.0

# GUI testing

This part of the document reports about testing at the GUI level. Tests are end to end, so they should cover the Use Cases, and corresponding scenarios.

## Coverage of Scenarios and FR

### 

| Scenario ID | Functional Requirements covered | GUI Test(s) |
| ----------- | ------------------------------- | ----------- | 
| UC1.1  | FR1, FR1.1  | GUITests.GUI_UC1.1.sikuli.GUI_UC1.1.py |
| UC1.2  | FR1, FR1.1  | GUITests.GUI_UC1.2.sikuli.GUI_UC1.2.py | 
| UC2.1  | FR1, FR1.1  | GUITests.GUI_UC2.1.sikuli.GUI_UC2.1.py | 
| UC2.2  | FR1, FR1.1  | GUITests.GUI_UC2.2.sikuli.GUI_UC2.2.py |
| UC3  | FR1.2       | GUITests.GUI_UC3.sikuli.GUI_UC3.py  |             
| UC4  | FR3, FR3.1  | GUITests.GUI_UC4.sikuli.GUI_UC4.py |
| UC5  | FR3.1       | GUITests.GUI_UC5.sikuli.GUI_UC5.py |             
| UC6  | FR3, FR3.2, FR3.3, FR4 |GUITests.GUI_UC6.sikuli.GUI_UC6.py | 
| UC7  | FR5, FR5.1  | GUITests.GUI_UC7.sikuli.GUI_UC7.py|
| UC8.1  | FR3.3, FR4, FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 |GUITests.GUI_UC8.1.sikuli.GUI_UC8.1.py| 
| UC8.2  | FR3.3, FR4, FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 |GUITests.GUI_UC8.2.sikuli.GUI_UC8.2.py| 
| UC8.3  | FR3.3, FR4, FR4.1, FR4.2, FR4.3, FR4.4, FR4.5 |GUITests.GUI_UC8.3.sikuli.GUI_UC8.3.py|
| UC9.1  | FR5.2 | |
| UC10.1 | FR5.3 | |
| UC10.2 | FR5.3 |  |       

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