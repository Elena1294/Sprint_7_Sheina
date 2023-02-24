import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.MatcherAssert;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;

public class CourierTest {
    Courier courier = new Courier(TestDataCourier.LOGIN, TestDataCourier.PASSWORD, TestDataCourier.FIRST_NAME);
    CourierAPI courierAPI = new CourierAPI();
    static int id;


    @Before
    public void setUp() {
        RestAssured.baseURI = Endpoints.BASE;

    }

    @Test
    @DisplayName("Проверка создания курьера") // имя теста
    @Description("Проверка того, что курьер успешно создается") // описание теста
    public void createNewCourierTest() {

        Response response = courierAPI.NewCourier(courier);

        response.then().statusCode(SC_CREATED);
                MatcherAssert.assertThat(courier, notNullValue());

        Response responseLogin = courierAPI.LoginCourier(courier);
        id = responseLogin.then().extract().path("id");
    }

    @Test
    @DisplayName("Проверка создания второго одинакового курьера") // имя теста
    @Description("Проверка создания второго одинакового курьера с существующим логином") // описание теста
    public void createDoubleCourierTest() {

        Courier courierDouble = new Courier(TestDataCourier.CREATED_LOGIN, TestDataCourier.CREATED_PASSWORD, TestDataCourier.CREATED_FIRST_NAME);
        Response response = courierAPI.NewCourier(courierDouble);
        response.then().assertThat()
                .statusCode(SC_CONFLICT)
                .and()
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой."));
    }

    @Test
    @DisplayName("Успешная авторизация") // имя теста
    @Description("Успешная авторизация с существующей парой логин/пароль") // описание теста
    public void checkSuccessfulLoginTest() {
        Courier courierValid = new Courier(TestDataCourier.CREATED_LOGIN, TestDataCourier.CREATED_PASSWORD);
        Response response = courierAPI.LoginCourier(courierValid);
        response.then().statusCode(SC_OK);
        response.then().assertThat().body("id", notNullValue());
    }

    @Test
    @DisplayName("Проверка создания курьера без логина") // имя теста
    @Description("Проверка создания курьера без логина") // описание теста
    public void createCourierWithOutLoginTest() {

        Courier courierWithOutLogin = new Courier("", TestDataCourier.PASSWORD, TestDataCourier.FIRST_NAME);
        Response response = courierAPI.LoginCourier(courierWithOutLogin);
        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }
    @Test
    @DisplayName("Проверка создания курьера без пароля") // имя теста
    @Description("Проверка создания курьера без пароля") // описание теста
    public void createCourierWithOutPasswordTest() {

        Courier courierWithOutPassword = new Courier(TestDataCourier.LOGIN, "", TestDataCourier.FIRST_NAME);

        Response response = courierAPI.LoginCourier(courierWithOutPassword);

        response.then().assertThat()
                .statusCode(SC_BAD_REQUEST)
                .and()
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
    }

    @After
    public void cleanUp(){

        courierAPI.DeleteCourier(id);

    }

}
