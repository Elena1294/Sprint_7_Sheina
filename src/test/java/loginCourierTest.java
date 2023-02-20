import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;

public class loginCourierTest {
    Courier courier = new Courier(TestDataCourier.CREATED_LOGIN, TestDataCourier.CREATED_PASSWORD, TestDataCourier.CREATED_FIRST_NAME);
    CourierAPI courierAPI = new CourierAPI();

    @Before
    public void setUp() {

        RestAssured.baseURI = Endpoints.BASE;
    }

    @Test
    @DisplayName("Проверка успешной авторизации курьера") // имя теста
    @Description("Проверка того, в резултате успешной авторизации возвращается не пустой id") // описание теста
    public void authCourierSuccessTest() {

        Response response = courierAPI.LoginCourier(courier);
        response.then().assertThat()
                .statusCode(SC_OK)
                .and()
                .body("id", notNullValue());

    }

    @Test
    @DisplayName("Проверка авторизации курьера без логина") // имя теста
    @Description("Проверка авторизации курьера без указания логина") // описание теста
    public void authCourierWithOutLoginTest() {

        Courier courierWithOutLogin = new Courier("", TestDataCourier.PASSWORD, TestDataCourier.FIRST_NAME);
        Response response = courierAPI.LoginCourier(courierWithOutLogin);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для входа"));

    }

    @Test
    @DisplayName("Проверка авторизации курьера без пароля") // имя теста
    @Description("Проверка авторизации курьера без указания пароля") // описание теста
    public void authCourierWithOutPasswordTest() {

        Courier courierWithOutPassword = new Courier(TestDataCourier.LOGIN, "", TestDataCourier.FIRST_NAME);
        Response response = courierAPI.LoginCourier(courierWithOutPassword);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST);

    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным паролем") // имя теста
    @Description("Проверка авторизации курьера с неправильным паролем") // описание теста
    public void authCourierIncorrectPasswordTest() {

        Courier courierIncorrectPassword = new Courier(TestDataCourier.CREATED_LOGIN, TestDataCourier.WRONG_PASSWORD, TestDataCourier.CREATED_FIRST_NAME);
        Response response = courierAPI.LoginCourier(courierIncorrectPassword);
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Проверка авторизации курьера с неправильным или не существующим логином") // имя теста
    @Description("Проверка авторизации курьера с неправильным или не существующим логином") // описание теста
    public void authCourierIncorrectLoginTest() {

        Courier courierIncorrectLogin = new Courier(TestDataCourier.CREATED_LOGIN, TestDataCourier.CREATED_PASSWORD, TestDataCourier.WRONG_LOGIN);
        Response response = courierAPI.LoginCourier(courierIncorrectLogin);
        response.then().assertThat()
                .statusCode(SC_NOT_FOUND)
                .and()
                .body("message", equalTo("Учетная запись не найдена"));

    }
}