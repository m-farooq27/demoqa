package api;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {

    private static final String BASE_URL = "https://demoqa.com";

    static {
        RestAssured.baseURI = BASE_URL;
    }

    public static String generateToken(String username, String password) {

        String requestBody = "{\"userName\": \"" + username + "\", \"password\": \"" + password + "\" }";

        Response response = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post("/Account/v1/GenerateToken");

        int statusCode = response.getStatusCode();

        if (statusCode != 200) {
            throw new RuntimeException("Failed to generate token. Status code: " + statusCode + ". Response: " + response.getBody().asString());
        }

        String token = response.jsonPath().getString("token");
        System.out.println("Generated token: " + token);
        return token;
    }

    //Login API (Validate credentials)

    public static boolean login(String username, String password) {

        String requestBody = "{\"userName\": \"" + username + "\", \"password\": \"" + password + "\" }";

        Response response = RestAssured
            .given()
            .header("Content-Type", "application/json")
            .body(requestBody)
            .post("/Account/v1/Login");

        int statusCode = response.getStatusCode();
        String message = response.jsonPath().getString("message");

        System.out.println("Login Status Code: " + statusCode);
        System.out.println("Login Response: " + message);

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
