import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;


@RunWith(Parameterized.class)
public class OrderCreateTest {
    private Order order;
    private OrderClient orderClient;

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<ScooterColor> colors;

    public OrderCreateTest(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List<ScooterColor> colors) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.colors = colors;
    }

    @Before
    public void setUp() {
        orderClient = new OrderClient();
        order = OrderGenerator.getParametrizedOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment);

    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{

                {"Анна", "Александрова", "улица 3, д3, кв3","metroStation3","33333333",3,"2022-06-06", "comment3", List.of()},
                {"Иван", "Петров", "улица 1, д1, кв1", "metroStation1","111111111",1,"2020-06-06", "comment1", List.of(ScooterColor.BLACK)},
                {"Сергей", "Федоров", "улица 2, д2, кв2","metroStation2","22222222",2,"2021-06-06", "comment2", List.of(ScooterColor.GREY)},
                {"Ольга", "Сидорова", "улица 4, д4, кв4","metroStation4","444444",4,"2023-06-06", "comment4", List.of(ScooterColor.GREY,ScooterColor.BLACK)},

        };
    }

    @Test
    public void createOrderTest() {
        if (colors.size() != 0) {
            order.setColor(colors);
        }
        ValidatableResponse createResponse = orderClient.createOrder(order);
        int createStatusCode = createResponse.extract().statusCode();
        String createStatusLine = createResponse.extract().statusLine();

        assertEquals(SC_CREATED, createStatusCode);
        assertEquals(createStatusLine, "HTTP/1.1 201 Created");

    }
}

