package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginApiTest {

    @Test
    public void testLoginApi() {

        RestAssured.baseURI = "https://demoqa.com";

        String requestBody = "{ \"userName\": \"maryamfarooq\", \"password\": \"Maryam123!\" }";

        Response response = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post("/Account/v1/Login");

        int statusCode = response.getStatusCode();
        System.out.println("Response code: " + statusCode);
        System.out.println("Response Body: " + response.getBody().asString());

        Assert.assertEquals(statusCode, 200, "Expected status code 200");
    }

}
