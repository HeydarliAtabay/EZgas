# Unit Testing Documentation

Authors:  Atabay Heydarli, Davide Lo Bianco, Gianluca Canitano, Nadir Casciola

Date: 19/05/2020

Version:1.0

# Contents

- [Black Box Unit Tests](#black-box-unit-tests)




- [White Box Unit Tests](#white-box-unit-tests)


# Black Box Unit Tests
### **Class *GasStation* - method *setGasPrice***



**Criteria for method *setGasPrice*:**

 -  Integer
 -  Sigh of the price


**Predicates for method *setGasPrice*:**

| Criteria | Predicate |
| -------- | --------- |
|   Integer       |     YES      |
|          |    NO       |
|   Sign of the price       |     Positive      |
|          |     Negative      |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Sign of the price      | -1, 0, 1                 |



**Combination of predicates**:


| Sign of the price| Integer | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|Positive|YES|Valid|gasStation.getGasPrice();gasStation.setGasPrice(3)-->True|it.polito.ezgas.EzGasApplicationTests.TestsetGasPrice1|
||NO|Valid|gasStation.getGasPrice();gasStation.setGasPrice(2.99)-->True|it.polito.ezgas.EzGasApplicationTests.TestsetGasPrice2|
|Negative|YES|Valid|gasStation.getGasPrice();gasStation.setGasPrice(-3)-->True|it.polito.ezgas.EzGasApplicationTests.TestsetGasPrice3|
||NO|Valid|gasStation.getGasPrice();gasStation.setGasPrice(-2.99)-->True|it.polito.ezgas.EzGasApplicationTests.TestsetGasPrice4|
|=0|NA|Valid|gasStation.getGasPrice();gasStation.setGasPrice(0)-->True|it.polito.ezgas.EzGasApplicationTests.TestsetGasPrice5|

 ### **Class *GasStation* - method *getGasStationId***



**Criteria for method *getGasStationId*:**
	
 - Sign of the input
 - Value of the input

**Predicates for method *getGasStationId*:**

| Criteria | Predicate |
| -------- | --------- |
|     Sign of the input     |   Positive        |
|          |    Negative       |
|     Value of the input     |  [minint,0]         |
|          |    [1,maxint]       |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Value of the input      | minint, minint+1, 0, maxint-1,maxint                 |



**Combination of predicates**:


| Value of the input | Sign of the input | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|[minint,0]|negative|Valid|gasStation.setGasStationId(-12345);gasStation.getGasStationId()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetGasStationId1|
|[0,maxint]|positive|Valid|gasStation.setGasStationId(12345);gasStation.getGasStationId()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetGasStationId2|


 ### **Class *GasStation* - method *getDieselPrice()***

**Criteria for method *getDieselPrice*:**
	

 - Integer
 - Sign of the input


**Predicates for method *getDieselPrice*:**

| Criteria | Predicate |
| -------- | --------- |
|   Integer       |     YES      |
|          |    NO       |
|   Sign of the input       |     Positive      |
|          |     Negative      |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Sign of the input      | -1 0 1                |


**Combination of predicates**:


| Integer| Sign of the input | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|YES|positive|Valid|gasStation.setDieselPrice(5);gasStation.getDieselPrice()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetDieselPrice1|
||Negative|Valid|gasStation.setDieselPrice(-5);gasStation.getDieselPrice()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetDieselPrice2|
|NO|positive|Valid|gasStation.setDieselPrice(4.46);gasStation.getDieselPrice()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetDieselPrice3|
||negative|Valid|gasStation.setDieselPrice(-4.46);gasStation.getDieselPrice()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetDieselPrice4|





 ### **Class *User* - method *getUserId***



**Criteria for method *getUserId*:**
	
 - Sign of the input
 - Value of the input

**Predicates for method *getUserId*:**

| Criteria | Predicate |
| -------- | --------- |
|     Sign of the input     |   Positive        |
|          |    Negative       |
|     Value of the input     |  [minint,0]         |
|          |    [1,maxint]       |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Value of the input      | minint, minint+1, 0, maxint-1,maxint                 |



**Combination of predicates**:


| Value of the input | Sign of the input | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|[minint,0]|negative|Valid|user.setUserId(-100099);user.getUserId()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetUserId1|
|[0,maxint]|positive|Valid|user.setUserId(100099);user.getUserId()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetUserId2|



 ### **Class *User* - method *getReputation***



**Criteria for method *getReputation*:**
	
 - Sign of the input
 - Value of the input

**Predicates for method *getReputation*:**

| Criteria | Predicate |
| -------- | --------- |
|     Sign of the input     |   Positive        |
|          |    Negative       |
|     Value of the input     |  [minint,0]         |
|          |    [1,maxint]       |


**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
|    Value of the input      | minint, minint+1, 0, maxint-1,maxint                 |



**Combination of predicates**:


| Value of the input | Sign of the input | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|-------|
|[minint,0]|negative|Valid|user.setReputation(-111111);user.getReputation()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetReputation1|
|[0,maxint]|positive|Valid|user.setReputation(111111);user.getReputation()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetReputation2|


 ### **Class *User* - method *getAdmin***

**Criteria for method *getAdmin*:**
	
 - Correctness

**Predicates for method *name*:**

| Criteria | Predicate |
| -------- | --------- |
|    Correctness      |    True       |
|          |    False       |



**Boundaries**:

| Criteria | Boundary values |
| -------- | --------------- |
| Correctness         |   True-false              |


**Combination of predicates**:

| Correctness | Valid / Invalid | Description of the test case | JUnit test case |
|-------|-------|-------|-------|
|True|Valid|user.setAdmin(True);user.getAdmin()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetAdmin1|
|False|Valid|user.setAdmin(False);user.getAdmin()-->True|it.polito.ezgas.EzGasApplicationTests.TestgetAdmin2|


# White Box Unit Tests

### Test cases definition
    
    <JUnit test classes must be in src/test/java/it/polito/ezgas>
    <Report here all the created JUnit test cases, and the units/classes under test >
    <For traceability write the class and method name that contains the test case>


| Unit name | JUnit test case |
|--|--|
|||
|||
||||

### Code coverage report

    <Add here the screenshot report of the statement and branch coverage obtained using
    the Eclemma tool. >


### Loop coverage analysis

    <Identify significant loops in the units and reports the test cases
    developed to cover zero, one or multiple iterations >

|Unit name | Loop rows | Number of iterations | JUnit test case |
|---|---|---|---|
|||||
|||||
||||||



