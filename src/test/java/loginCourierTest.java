import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class loginCourierTest {

    Courier courier = new Courier("Helen14", "new123", "Elena");

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    @DisplayName("Проверка успешной авторизации курьера") // имя теста
    @Description("Проверка того, в резултате успешной авторизации возвращается не пустой id") // описание теста
    public void authCourierSuccessTest() {

        Response response = given()
                .header("Content-type", "application/json")
                .body(courier)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(200)
                .and()
                .body("id", notNullValue());

    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина") // имя теста
    @Description("Проверка авторизации курьера без указания логина") // описание теста
    public void authCourierWithOutLoginTest() {
        Courier courierWithOutLogin = new Courier("", "12345", "Elena");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutLogin)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(400)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Проверка авторизации курьера без пароля") // имя теста
    @Description("Проверка авторизации курьера без указания пароля") // описание теста
    public void authCourierWithOutPasswordTest() {
        Courier courierWithOutPassword = new Courier("TestQA", "", "Elena");
        Response response = given()
                .header("Content-type", "application/json")
                .body(courierWithOutPassword)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(400);

    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным паролем") // имя теста
    @Description("Проверка авторизации курьера с неправильным паролем") // описание теста
    public void authCourierIncorrectPasswordTest() {
        Courier courierIncorrectPassword = new Courier("NewTesters", "1e345", "Elena");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courierIncorrectPassword)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным или не существующим логином") // имя теста
    @Description("Проверка авторизации курьера с неправильным или не существующим логином") // описание теста
    public void authCourierIncorrectLoginTest() {
        Courier courierIncorrectLogin = new Courier("qa", "12345", "Elena");

        Response response = given()
                .header("Content-type", "application/json")
                .body(courierIncorrectLogin)
                .when()
                .post("/api/v1/courier/login");

        response.then().assertThat()
                .statusCode(404)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}