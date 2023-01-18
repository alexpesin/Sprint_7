import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.restassured.response.ValidatableResponse;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;


import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)

public class CreateCouriersWithSameLoginTest {
    private static CourierClient courierClient;
    private static Courier courier;
    private static int id;

    private final String courierPassword;
    private final String courierFirstName;

    public CreateCouriersWithSameLoginTest(String courierPassword, String courierFirstName) {
        this.courierPassword = courierPassword;
        this.courierFirstName = courierFirstName;
    }

    @BeforeClass
    public static void setUp() {
        //courier.Courier courier = courier.CourierGenerator.getCourierWithDataPredefined("SameLoginTest", "SamePassword", "SameFirstName");
        courier = CourierGenerator.getRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier);
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        id = loginResponse.extract().path("id");

    }

    @Parameterized.Parameters
    public static Object[][] getCourierData(){
        return new Object[][]{

                {"SamePassword", "SameFirstName"},
                {"AnotherPassword", "SameFirstName"},
                {"SamePassword", "AnotherFirstName"},
                {"AnotherPassword", "AnotherFirstName"},
                {"AnotherPassword", ""},
        };
    }
    @Test
    public void createCouriersSameLoginShouldFailTest() {

        Courier anotherCourier = CourierGenerator.getCourierWithDataPredefined(courier.getLogin(), courierPassword, courierFirstName);
        ValidatableResponse createResponse = courierClient.createCourier(anotherCourier);
        int createStatusCode = createResponse.extract().statusCode();
        String message = createResponse.extract().path("message");

        assertEquals(SC_CONFLICT, createStatusCode);
        assertEquals(message, "Этот логин уже используется. Попробуйте другой.");

    }

    @AfterClass
    public static void cleaUp(){
        courierClient.delete(id);
    }
}
