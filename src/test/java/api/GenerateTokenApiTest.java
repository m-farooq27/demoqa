package api;

import org.testng.Assert;
import org.testng.annotations.Test;


public class GenerateTokenApiTest {

    private final String username = "maryamfarooq";
    private final String password = "Maryam123!";

    @Test
    public void generateTokenTest() {

        String token = ApiUtils.generateToken(username, password);

        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token.length() > 10, "Token length is too short - must exceed 10 characters");
    }

}     