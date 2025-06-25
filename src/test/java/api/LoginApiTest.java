package api;

import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginApiTest {

   private final String username = "maryamfarooq";
   private final String password = "Maryam123!";

   @Test
   public void verifyLoginApi() {

    boolean loginSuccess = ApiUtils.login(username, password);

    Assert.assertTrue(loginSuccess, "Login API failed with given credentials");
   }
   
   }
