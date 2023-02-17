import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
    String token = "5QgwD15jooUaCqL5r89TNZhQu3VtKWxEDLrpIg9usyJnyaaTtDnzkiMi3MgeIck=";
    Courier courier = new Courier("Helena15", "newpass123", "Elena");
    static int id;


    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";

    }

    @Test
    @DisplayName("Проверка создания курьера") // имя теста
    @Description("Проверка того, что курьер успешно создается") // описание теста
    public void createNewCourierTest() {

        Response response =
        given()
                .header("Content-type", "application/json")
                .auth().oauth2(token)
                .and()
                .body(courier)
                .when()
                .post("/api/v1/courier");
        response.then().statusCode(201);
                MatcherAssert.assertThat(courier, notNullValue());

        Response responseLogin = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when().post("/api/v1/courier/login");
        id = responseLogin.then().extract().path("id");
    }

    @Test
    @DisplayName("Проверка создания второго одинакового курьера") // имя теста
    @Description("Проверка создания второго одинакового курьера с существующим логином") // описание теста
    public void createDoubleCourierTest() {
        Courier courierDouble = new Courier("Helen14", "new123", "Elena");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierDouble)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(409)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Успешная авторизация") // имя теста
    @Description("Успешная авторизация с существующей парой логин/пароль") // описание теста
    public void checkSuccessfulLoginTest() {
        Courier courierValid = new Courier("Helen14", "new123");
        Response response;
        response = given()
                .auth().oauth2(token)
                .and()
                .body(courierValid)
                .when()
                .post("/api/v1/courier/login");
        response.then().statusCode(200);
        response.then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка создания курьера без логина") // имя теста
    @Description("Проверка создания курьера без логина") // описание теста
    public void createCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "1234", "courierWithOutLogin");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutLogin)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Проверка создания курьера без пароля") // имя теста
    @Description("Проверка создания курьера без пароля") // описание теста
    public void createCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "courierWithOutPassword");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutPassword)
                .when()
                .post("/api/v1/courier");

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp(){
        given().delete("/api/v1/courier/" + id);

    }

}
