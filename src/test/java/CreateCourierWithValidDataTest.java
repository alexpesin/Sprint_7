import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class CreateCourierWithValidDataTest {

    private Courier courier;
    private Courier courierWithoutFirstName;
    private CourierClient courierClient;
    private int id;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
        courier = CourierGenerator.getRandomCourier();
        courierWithoutFirstName = CourierGenerator.getCourierWithoutFirstName();
    }

    @Test
    public void courierWithAllValidDataCanBeCreated() {


        ValidatableResponse createResponse = courierClient.createCourier(courier);
        int createStatusCode = createResponse.extract().statusCode();
        String createStatusLine = createResponse.extract().statusLine();
        boolean isTrue = createResponse.extract().path("ok");

        assertEquals(SC_CREATED, createStatusCode);
        assertEquals(createStatusLine, "HTTP/1.1 201 Created");
        assertTrue(isTrue);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));
        id = loginResponse.extract().path("id");
        int loginStatusCode = loginResponse.extract().statusCode();

        assertEquals(SC_OK,
                loginStatusCode);

    }

    @Test
    public void courierWithoutFirstNameCanBeCreated() {

        ValidatableResponse createResponse = courierClient.createCourier(courierWithoutFirstName);
        int createStatusCode = createResponse.extract().statusCode();
        String createStatusLine = createResponse.extract().statusLine();
        boolean isTrue = createResponse.extract().path("ok");

        assertEquals(SC_CREATED, createStatusCode);
        assertEquals(createStatusLine, "HTTP/1.1 201 Created");
        assertTrue(isTrue);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courierWithoutFirstName));
        id = loginResponse.extract().path("id");
        int loginStatusCode = loginResponse.extract().statusCode();

        assertEquals(SC_OK,
                loginStatusCode);
    }

   @After
    public void cleaUp(){
        courierClient.delete(id);
    }
}

