import courier.Courier;
import courier.CourierClient;
import courier.CourierCredentials;
import courier.CourierGenerator;
import io.restassured.response.ValidatableResponse;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

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
        boolean isTrue = createResponse.extract().path("ok");

        assertEquals(SC_CREATED, createStatusCode);
        assertTrue(isTrue);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courier));

        id = loginResponse.extract().path("id");
        int loginStatusCode = loginResponse.extract().statusCode();

        MatcherAssert.assertThat(id, notNullValue());
        assertEquals(SC_OK,
                loginStatusCode);

    }

    @Test
    public void courierWithoutFirstNameCanBeCreated() {

        ValidatableResponse createResponse = courierClient.createCourier(courierWithoutFirstName);
        int createStatusCode = createResponse.extract().statusCode();
        boolean isTrue = createResponse.extract().path("ok");

        assertEquals(SC_CREATED, createStatusCode);
        assertTrue(isTrue);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.getCourierCredentials(courierWithoutFirstName));
        id = loginResponse.extract().path("id");
        int loginStatusCode = loginResponse.extract().statusCode();

        MatcherAssert.assertThat(id, notNullValue());
        assertEquals(SC_OK,
                loginStatusCode);
    }

   @After
    public void cleaUp(){
        courierClient.delete(id);
    }
}

