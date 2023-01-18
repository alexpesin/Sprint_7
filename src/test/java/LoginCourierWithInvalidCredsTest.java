import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
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

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_NOT_FOUND, loginStatusCode);
        assertEquals(message, "Учетная запись не найдена");

    }

    @Test
    public void courierWithNoLoginAndPasswordShouldNotLogin() {
        courier = CourierGenerator.getNoCredsCourier();

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        String message = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, loginStatusCode);
        assertEquals(message, "Недостаточно данных для входа");

    }

}
