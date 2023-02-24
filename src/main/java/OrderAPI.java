import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class OrderAPI {

    public Response NewOrder (Order order){
        return
                given()
                        .header("Content-type", "application/json")
                        .body(order)
                        .when()
                        .post(Endpoints.API_ORDERS);
    }
}
