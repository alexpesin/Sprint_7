import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CourierClient extends Client{

    private static final String CREATE_COURIER = "/api/v1/courier";
    private static final String LOGIN_COURIER = "/api/v1/courier/login";
    private static final String DELETE_COURIER = "/api/v1/courier/";

    public ValidatableResponse createCourier(Courier courier){
        return given().log().all()
                .spec(getSpecification())
                .body(courier)
                .when()
                .post(CREATE_COURIER)
                .then().log().all();

    }


    public ValidatableResponse login(CourierCredentials credentials){
        return given().log().all()
                .spec(getSpecification())
                .body(credentials).log().all()
                .when()
                .post(LOGIN_COURIER)
                .then()
                .assertThat().body("id", notNullValue())
                .log().all();
    }

    public ValidatableResponse loginWithInvalidCreds(CourierCredentials credentials){
        return given().log().all()
                .spec(getSpecification())
                .body(credentials).log().all()
                .when()
                .post(LOGIN_COURIER)
                .then()
                .log().all();
    }


    public ValidatableResponse delete(int id){
        return given().log().all()
                .spec(getSpecification())
                .body("")
                .when()
                .delete(DELETE_COURIER + id)
                .then().log().all();
    }
}

