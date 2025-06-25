package api;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.selenium.utils.ExtentManager;

public class GenerateTokenApiTest {

    private final String username = "maryamfarooq";
    private final String password = "Maryam123!";

      @BeforeClass
   public void setUpReport() {
    ExtentManager.initReport();
    ExtentManager.createTest("Generate token API test");
   }

    @Test
    public void generateTokenTest() {

        String token = ApiUtils.generateToken(username, password);

        Assert.assertNotNull(token, "Token should not be null");
        Assert.assertTrue(token.length() > 10, "Token length is too short - must exceed 10 characters");
    }

     @AfterClass
   public void tearDownReport() {
    ExtentManager.flushReport();
   }


}     