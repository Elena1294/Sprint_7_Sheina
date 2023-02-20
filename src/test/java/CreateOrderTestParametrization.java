import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import java.util.ArrayList;
import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTestParametrization {

    final private String firstNameParam;
    final private String lastNameParam;
    final private String addressParam;
    final private String metroStationParam;
    final private String phoneParam;
    final private int rentTimeParam;
    final private String deliveryDateParam;
    final private String commentParam;
    final private String colorParam;

    OrderAPI orderAPI = new OrderAPI();

    public CreateOrderTestParametrization (String firstNameParam, String lastNameParam, String addressParam, String metroStationParam, String phoneParam, int rentTimeParam, String deliveryDateParam, String commentParam, String colorParam) {
        this.firstNameParam = firstNameParam;
        this.lastNameParam = lastNameParam;
        this.addressParam = addressParam;
        this.metroStationParam = metroStationParam;
        this.phoneParam = phoneParam;
        this.rentTimeParam = rentTimeParam;
        this.deliveryDateParam = deliveryDateParam;
        this.commentParam = commentParam;
        this.colorParam = colorParam;
    }

    @Parameterized.Parameters
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Елена", "Шеина", "str.Lenina", "Frunzinskya", "+79194965999", 5, "2023-01-16", "No comments", "Black"},
                {"Иван", "Заболотный", "str.Puchkina", "Sokol'skaya", "+79124965999", 5, "2023-01-15", "comment 1", "Grey"},
                {"Антон", "Подгорный", "str.Popova", "MoskvaCitu", "+79024965999", 5, "2023-01-14", "comment 2", "Blue"},
        };
    }

    @Before
    public void setUp() {

        RestAssured.baseURI = Endpoints.BASE;
    }

    @Test
    @DisplayName("Проверка создания заказа c несколькими цветами") // имя теста
    public void createOrderManyColor() {

        ArrayList<String> manyColor = new ArrayList<>();
        manyColor.add("Black");
        manyColor.add("Grey");
        Order orderManyColor = new Order(firstNameParam, lastNameParam, addressParam, metroStationParam, phoneParam, rentTimeParam, deliveryDateParam, commentParam, manyColor);

        Response response = orderAPI.NewOrder(orderManyColor);
        response.then().assertThat().statusCode(SC_CREATED)
                .and()
                .body("track", notNullValue());

    }
}