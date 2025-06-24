package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;


public class GenerateTokenApiTest {

    @Test
    public void generateTokenTest() {

        //Set the base URI for Rest Assured
        RestAssured.baseURI = "https://demoqa.com";

        //Prepare the request body in JSON format
        String requestBody = "{\"userName\": \"maryamfarooq\", \"password\": \"Maryam123!\" }";

        //Send the POST request to GenerateToken endpoint
        Response response = RestAssured
            .given()
            .header("Content-type", "application/json")
            .body(requestBody)
            .post("/Account/v1/GenerateToken");

        //Extract response status code and body
        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        //Print response for debugging
        System.out.println("Response code: " + statusCode);
        System.out.println("Response body: " + responseBody);

        //Assert that HTTP status code is 200
        Assert.assertEquals(statusCode, 200, "Expected status code 200");

        //Extract token from response body
        String token = response.jsonPath().getString("token");
        System.out.println("Generated Token: " + token);

        //Assert that token is not null or empty
        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token.length() > 10, "Token length should be greater than 10 characters");
    }

}
