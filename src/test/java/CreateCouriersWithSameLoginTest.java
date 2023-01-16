import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class CreateCouriersWithSameLoginTest {
    private CourierClient courierClient;
    private int id;

    private final String courierLogin;
    private final String courierPassword;
    private final String courierFirstName;

    public CreateCouriersWithSameLoginTest(String courierLogin, String courierPassword, String courierFirstName) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
        this.courierFirstName = courierFirstName;
    }

    @Before
    public void setUp() {
        Courier courier = CourierGenerator.getCourierWithDataPredefined("SameLoginTest", "SamePassword", "SameFirstName");
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        id = loginResponse.extract().path("id");

    }

    @Parameterized.Parameters
    public static Object[][] getCourierData(){
        return new Object[][]{
                {"SameLoginTest", "SamePassword", "SameFirstName"},
                {"SameLoginTest", "AnotherPassword", "SameFirstName"},
                {"SameLoginTest", "SamePassword", "AnotherFirstName"},
                {"SameLoginTest", "AnotherPassword", "AnotherFirstName"},
                {"SameLoginTest", "AnotherPassword", ""},

        };
    }
    @Test
    public void createCouriersSameLoginShouldFailTest() {


        Courier anotherCourier = CourierGenerator.getCourierWithDataPredefined(courierLogin, courierPassword, courierFirstName);
        ValidatableResponse createSecondTimeResponse = courierClient.createCourier(anotherCourier);
        int createSecondStatusCode = createSecondTimeResponse.extract().statusCode();
        String createSecondStatusLine = createSecondTimeResponse.extract().statusLine();
         String message = createSecondTimeResponse.extract().path("message");

        assertEquals(SC_CONFLICT, createSecondStatusCode);
        assertEquals(createSecondStatusLine, "HTTP/1.1 409 Conflict");
        assertEquals(message, "Этот логин уже используется. Попробуйте другой.");

    }

    @After
    public void cleaUp(){
        courierClient.delete(id);
    }
}
