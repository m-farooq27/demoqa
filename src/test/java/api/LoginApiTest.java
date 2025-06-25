package api;

import com.selenium.utils.ExtentManager;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

public class LoginApiTest {

   private final String username = "maryamfarooq";
   private final String password = "Maryam123!";

   @BeforeClass
   public void setUpReport() {
    ExtentManager.initReport();
    ExtentManager.createTest("Login API Test");
   }

   @Test
   public void verifyLoginApi() {

    boolean loginSuccess = ApiUtils.login(username, password);

    Assert.assertTrue(loginSuccess, "Login API failed with given credentials");
   }

   @AfterClass
   public void tearDownReport() {
    ExtentManager.flushReport();
   }

   }
