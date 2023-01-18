package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class Client {
    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";
    //private static final String TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmNiMDMyZTA0NmM5ZDAwM2QxNmY2MWUiLCJpYXQiOjE2NzI2OTk1OTMsImV4cCI6MTY3MzMwNDM5M30.jMwmUemEWN0GnoxBxP-ot-aspBmZsUrLYR0o5u0P-uA";

    protected RequestSpecification getSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(BASE_URL)
                .setContentType(ContentType.JSON)
                //.setAuth(oauth2(TOKEN))
                .build();
    }

}
