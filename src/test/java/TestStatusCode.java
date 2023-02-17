
// импортируем RestAssured
/*import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class TestStatusCode {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2FhZTQxYTgwODNjMzAwNDIzNDI2NmUiLCJpYXQiOjE2NzYyNzg3NDcsImV4cCI6MTY3Njg4MzU0N30.aW8fJbiArEmVShK6WpdbPSoIQn-Cwf1eVCva4oGRQv0";
    // аннотация Before показывает, что метод будет выполняться перед каждым тестовым методом
    @Before
    public void setUp() {
        // повторяющуюся для разных ручек часть URL лучше записать в переменную в методе Before
        // если в классе будет несколько тестов, указывать её придётся только один раз
        RestAssured.baseURI = "https://qa-mesto.praktikum-services.ru";

    }

    // создаём метод автотеста
    @Test
    public void getMyInfoStatusCode() {
        // метод given() помогает сформировать запрос
        given()
                // указываем протокол и данные авторизации
                .auth().oauth2("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2M2FhZTQxYTgwODNjMzAwNDIzNDI2NmUiLCJpYXQiOjE2NzYyNzg3NDcsImV4cCI6MTY3Njg4MzU0N30.aW8fJbiArEmVShK6WpdbPSoIQn-Cwf1eVCva4oGRQv0")
                // отправляем GET-запрос с помощью метода get, недостающую часть URL (ручку) передаём в него в качестве параметра
                .get("/api/users/me")
                // проверяем, что статус-код ответа равен 200
                .then().statusCode(200);
    }

    @Test
    public void checkUserName() {
        given()
                .auth().oauth2(token)
                .get("/api/users/me")
                .then().assertThat().body("data.name", equalTo("Жак-Ив Кусто"));
    }

    @Test
    public void checkCardsStatusCode() {
        // проверяем статус-код ответа на запрос «Получение всех карточек»
        given()
                .auth().oauth2(token)
                .get("/api/cards")
                .then().statusCode(200);
    }

    @Test
    public void checkUserActivityAndPrintResponseBody() {

        // отправляет запрос и сохраняет ответ в переменную response, экзмепляр класса Response
        Response response = given().auth().oauth2(token).get("/api/users/me");
        // проверяет, что в теле ответа ключу name соответствует нужное имя пользователя
        response.then().assertThat().body("orderWeight",equalTo(11));
        // выводит тело ответа на экран
        System.out.println(response.body().asString());
    }

    @Test
    public void createNewPlaceAndCheckStatusCode() {

        File json = new File("src/test/resources/newCard.json");

        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(token)
                        .and()
                        .body(json)
                        .when()
                        .post("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void createNewPlaceAndCheckResponse(){
        File json = new File("src/test/resources/newCard.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(token)
                        .and()
                        .body(json)
                        .when()
                        .patch("/api/cards");
        response.then().assertThat().body("data._id", notNullValue())
                .and()
                .statusCode(201);
    }

    @Test
    public void updateProfileAndCheckStatusCode(){
        File json = new File("src/test/resources/updateProfile.json");
        Response response =
                given()
                        .header("Content-type", "application/json")
                        .auth().oauth2(token)
                        .and()
                        .body(json)
                        .when()
                        .patch("/api/cards");
        response.then().assertThat().body("data.name", equalTo("Василий Васильев"))
                .and()
                .statusCode(201);
    }
}
*/