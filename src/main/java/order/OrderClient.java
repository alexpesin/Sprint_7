package order;

import client.Client;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderClient extends Client {

    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String GET_ORDER_LIST = "/api/v1/orders";

    public ValidatableResponse createOrder(Order order){
        return given().log().all()
                .spec(getSpecification())
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then()
                .assertThat().body("track", notNullValue())
                .log().all();

    }

    public ValidatableResponse getOrderList(){
        return given().log().all()
                .spec(getSpecification())
                .when()
                .get(GET_ORDER_LIST)
                .then()
                .assertThat().body("orders", notNullValue())
                .log().all();

    }
}
