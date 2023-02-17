import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class returnListOrdersTest {

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка возврата списка заказов") // имя теста
    @Description("Проверка возврата списка заказов") // описание теста
    public void returnListOrdersTest() {

        Response response = given()
                .header("Content-type", "application/json")
                .when()
                .get("/api/v1/orders");

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("orders", notNullValue());

    }
}