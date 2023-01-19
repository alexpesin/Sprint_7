import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class CreateCouriersNoRequiredFieldsTest {
    private CourierClient courierClient;
    private Courier courier;
    private int id;

    private final String courierLogin;
    private final String courierPassword;
    private final String courierFirstName;

    public CreateCouriersNoRequiredFieldsTest(String courierLogin, String courierPassword, String courierFirstName) {
        this.courierLogin = courierLogin;
        this.courierPassword = courierPassword;
        this.courierFirstName = courierFirstName;
    }

    @Before
    public void setUp() {
        courier = CourierGenerator.getCourierWithDataPredefined(courierLogin, courierPassword, courierFirstName);
        courierClient = new CourierClient();

    }

    @Parameterized.Parameters
    public static Object[][] getCourierData(){
        return new Object[][]{
                {"", "", ""},
                {"LoginTest", "", "FirstName"},
                {"LoginTest", "", ""},
                {"", "Password", "FirstName"},
                {"", "Password", ""},
                {"", "", "FirstName"},

        };
    }
    @Test
    public void createCourierNoRequiredFieldsShouldFailTest() {

        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int createStatusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, createStatusCode);
        assertEquals(message, "Недостаточно данных для создания учетной записи");

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        if(SC_CREATED == createStatusCode) {
            id = loginResponse.extract().path("id");
        }
        int loginStatusCode = loginResponse.extract().statusCode();
        String loginMessage = loginResponse.extract().path("message");

        assertEquals(SC_BAD_REQUEST, loginStatusCode);
        assertEquals(loginMessage, "Недостаточно данных для входа");

    }

    @After
    public void cleaUp(){
        courierClient.delete(id);
    }
}

