import io.restassured.response.ValidatableResponse;

import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;


public class LoginCourierWithInvalidCredsTest {

    private CourierClient courierClient;
    private Courier courier;

    @Before
    public void setUp() {
        courierClient = new CourierClient();

    }

    @Test
    public void courierWithNotExistingLoginAndPasswordShouldNotLogin() {

        courier = CourierGenerator.getRandomCourier();

        ValidatableResponse loginResponse = courierClient.loginWithInvalidCreds(CourierCredentials.getCourierCredentials(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        String loginStatusLine = loginResponse.extract().statusLine();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_NOT_FOUND, loginStatusCode);
        assertEquals(loginStatusLine, "HTTP/1.1 404 Not Found");
        assertEquals(message, "Учетная запись не найдена");

    }

    @Test
    public void courierWithNoLoginAndPasswordShouldNotLogin() {
        courier = CourierGenerator.getNoCredsCourier();

        ValidatableResponse loginResponse = courierClient.loginWithInvalidCreds(CourierCredentials.getCourierCredentials(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        String loginStatusLine = loginResponse.extract().statusLine();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, loginStatusCode);
        assertEquals(loginStatusLine, "HTTP/1.1 400 Bad Request");
        assertEquals(message, "Недостаточно данных для входа");

    }

}
