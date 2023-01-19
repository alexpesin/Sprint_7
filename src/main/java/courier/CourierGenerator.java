package courier;

import static java.time.LocalTime.now;

public class CourierGenerator {

    public static Courier getNoCredsCourier(){
        return new Courier("","","NoCreds");
    }

    public static Courier getRandomCourier(){
        return new Courier("rndLogin" + now().hashCode(), "123", "firstName");
    }

    public static Courier getCourierWithDataPredefined(String login, String password, String firstName){
        Courier courier = new Courier();
        courier.setLogin(login);
        courier.setPassword(password);
        courier.setFirstName(firstName);
        return courier;
    }

    public static Courier getCourierWithoutFirstName(){
        Courier courier = new Courier();
        courier.setLogin("NoFirstNameLogin");
        courier.setPassword("NoFirstNamePassword");
        return courier;
    }
}
