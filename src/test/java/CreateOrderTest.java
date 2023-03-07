import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import static io.restassured.RestAssured.given;
import static io.restassured.http.Cookie.COMMENT;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.apache.http.HttpStatus.*;

public class CreateOrderTest {
    OrderAPI orderAPI = new OrderAPI();

    @Before
    public void setUp() {
        RestAssured.baseURI = Endpoints.BASE;
    }

    @Test
    @DisplayName("Проверка создания заказа") // имя теста
    public void createOrderTest() {

        Order order = new Order(TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.METRO_STATION, TestDataOrder.PHONE, TestDataOrder.RENT_TIME, TestDataOrder.DELIVERY_DATE, TestDataOrder.COMMENT, TestDataOrder.COLOR);
        Response response = orderAPI.NewOrder(order);
        response.then().assertThat().statusCode(SC_CREATED);
    }

    @Test
    @DisplayName("Проверка создания заказа c одним цветом") // имя теста
    public void createOrderOneColor() {

        Order orderOneColor = new Order(TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.METRO_STATION, TestDataOrder.PHONE, TestDataOrder.RENT_TIME, TestDataOrder.DELIVERY_DATE, TestDataOrder.COMMENT, TestDataOrder.COLOR);
        Response response = orderAPI.NewOrder(orderOneColor);
        response.then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());

    }

    @Test
    @DisplayName("Проверка создания заказа без цвета") // имя теста
    public void createOrderWithOutColor() {

        Order orderWithOutColor = new Order(TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.FIRST_NAME, TestDataOrder.METRO_STATION, TestDataOrder.PHONE, TestDataOrder.RENT_TIME, TestDataOrder.DELIVERY_DATE, TestDataOrder.COMMENT);
        Response response = orderAPI.NewOrder(orderWithOutColor);
        response.then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());
    }

}