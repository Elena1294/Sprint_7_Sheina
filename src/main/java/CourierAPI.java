import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
public class CourierAPI {
    public Response NewCourier (Courier courier){
        return
        given()
                .header("Content-type", "application/json")
                .and()
                .body(courier)
                .when()
                .post(Endpoints.API_CREATE);
    }
    public Response LoginCourier (Courier courier){
        return
                given()
                        .header("Content-type", "application/json")
                        .body(courier)
                        .when().post(Endpoints.API_LOGIN);
    }

    public Response DeleteCourier (int id){
        return
                given().delete(Endpoints.API_DELETE + id);
    }

}
