package order;

public class OrderGenerator {

    public static Order getDefaultOrder(){
        Order order = new Order();
        order.setFirstName("FirstName");
        order.setLastName("LastName");
        order.setAddress("TestAddress");
        order.setMetroStation("TestMetroStation");
        order.setPhone("11111");
        order.setRentTime(1);
        order.setDeliveryDate("2023-01-30");
        order.setComment("TestComment");

        return order;
    }

    public static Order getParametrizedOrder(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment ){
        Order order = new Order();
        order.setFirstName(firstName);
        order.setLastName(lastName);
        order.setAddress(address);
        order.setMetroStation(metroStation);
        order.setPhone(phone);
        order.setRentTime(rentTime);
        order.setDeliveryDate(deliveryDate);
        order.setComment(comment);
        return order;

    }
}
