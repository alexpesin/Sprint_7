import io.restassured.response.ValidatableResponse;
import order.OrderClient;
import org.junit.Before;
import org.junit.Test;


import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;

public class GetOrderLIstTest {

    private OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();

    }

    @Test
    public void getOrderListTest() {

        ValidatableResponse createResponse = orderClient.getOrderList();
        int createStatusCode = createResponse.extract().statusCode();
        assertEquals(SC_OK, createStatusCode);

    }
}
