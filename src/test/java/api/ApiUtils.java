package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import com.aventstack.extentreports.Status;
import com.selenium.utils.ExtentManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ApiUtils {

    private static final String BASE_URL = "https://demoqa.com";
    private static final Logger log = LogManager.getLogger(ApiUtils.class);

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public static String generateToken(String username, String password) {
        log.info("Generating token for user: {}", username);
        ExtentManager.getTest().log(Status.INFO, "Generating token for user: " + username);

        String requestBody = "{\"userName\": \"" + username + "\", \"password\": \"" + password + "\" }";

        Response response = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post("/Account/v1/GenerateToken");

        int statusCode = response.getStatusCode();
        log.info("Response code: {}" , statusCode);
        ExtentManager.getTest().log(Status.INFO, "Response Code: " + statusCode);
        ExtentManager.getTest().log(Status.INFO, "Response Body " + response.getBody().asString());

        if (statusCode != 200) {
            throw new RuntimeException("Failed to generate token. Status code: " + statusCode + ". Response: " + response.getBody().asString());
        }

        String token = response.jsonPath().getString("token");
        log.info("Generated token: {}", token);
        ExtentManager.getTest().log(Status.INFO, "Generated Token: " + token);
        return token;
    }

    //Login API (Validate credentials)

    public static boolean login(String username, String password) {
        log.info("Performing login API for user: {}", username);
        ExtentManager.getTest().log(Status.INFO, "Performing login API for user: " + username);

        String requestBody = "{\"userName\": \"" + username + "\", \"password\": \"" + password + "\" }";

        Response response = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post("/Account/v1/Login");

        int statusCode = response.getStatusCode();
        log.info("Login Response Code: {}", statusCode);
        ExtentManager.getTest().log(Status.INFO, "Login Response Code: " + statusCode);
       
        return (statusCode == 200);

        }

        //Get user details using token and ID

        public static Response getUserDetails(String token, String userID) {

            Response response = RestAssured
                .given()
                .header("Authorization", "Bearer" + token)
                .get("/Account/v1/User/" + userID);

            int statusCode = response.getStatusCode();

            if (statusCode != 200) {
                throw new RuntimeException("Failed to get user details. Status Code: " + statusCode);
            }

            return response;
        }

}
