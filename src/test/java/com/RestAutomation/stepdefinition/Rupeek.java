package com.RestAutomation.stepdefinition;

import java.io.FileInputStream;
import java.util.Properties;

import com.RestAutomation.pojo.RupeekPojo;

import cucumber.api.PendingException;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

public class Rupeek {
	
	private Response response;
	private ValidatableResponse json;
	private RequestSpecification httpRequest;
	Properties apiProps;
	RupeekPojo rupeek = new RupeekPojo();
	
	@Given("^the authentication API$")
    public void the_authentication_api() throws Throwable {
        apiProps = new Properties();
        apiProps.load(new FileInputStream("resources//config.properties"));
		RestAssured.baseURI = apiProps.getProperty("baseURI");
		httpRequest = RestAssured.given();
		
    }

	@When("^hit the API with required post request$")
    public void hit_the_api_with_required_post_request() throws Throwable {
		String authenticationRequest = "{\"username\":\"rupeek\",\"password\":\"password\"}";
        response = httpRequest.when().body(authenticationRequest).post("/authenticate");
    }
	
	@Then("^an JWT token will get generated$")
    public void an_jwt_token_will_get_generated() throws Throwable {
		json = response.then().assertThat().statusCode(200);
		String token = json.extract().response().asString();
		rupeek.setToken(token);
    }
	@When("^the token is passed as an input to get customer details API$")
    public void the_token_is_passed_as_an_input_to_get_customer_details_api() throws Throwable {
        response = httpRequest.auth().basic("token", rupeek.getToken()).when().get("/api/v1/users");
    }
	
	@Then("^the customer details will get retrieved from the database and are displayed in JSON format$")
    public void the_customer_details_will_get_retrieved_from_the_database_and_are_displayed_in_json_format() throws Throwable {
        json = response.then().assertThat().statusCode(200);
        String customerRecords = json.extract().response().asString();
        System.out.println("Customer Records: "+customerRecords);
    }
	
	 @When("^the token is passed as an input to retrieve user with help of (.+)$")
	    public void the_token_is_passed_as_an_input_to_retrieve_user_with_help_of(String phonenumber) throws Throwable {
		 
	        response = httpRequest.auth().basic("token", rupeek.getToken()).when().get("/api/v1/users/"+phonenumber);
	    }
	
	@Then("^the customer details for the provided phone number will get retrieved$")
    public void the_customer_details_for_the_provided_phone_number_will_get_retrieved() throws Throwable {
        json = response.then().assertThat().statusCode(200);
        String customerWithPhoneNumb = json.extract().response().asString();
        System.out.println("Customer with Phone Number "+customerWithPhoneNumb);
    }

}
