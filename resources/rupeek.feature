Feature: To get users phone number
Scenario Outline: To retrieve the details of users after generating the JWT token
Given the authentication API
When hit the API with required post request
Then an JWT token will get generated
When the token is passed as an input to get customer details API
Then the customer details will get retrieved from the database and are displayed in JSON format
When the token is passed as an input to retrieve user with help of <phoneNumber>
Then the customer details for the provided phone number will get retrieved
examples:
|phoneNumber|
|8888888888|