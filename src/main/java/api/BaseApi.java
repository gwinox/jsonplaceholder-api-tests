package api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.util.List;

public abstract class BaseApi {

    protected static <T> ApiResponse<T> get(String endpoint, Class<T> responseClass) {
        Response response = RestAssured.given().get(endpoint);
        return new ApiResponse<>(response.statusCode(), response.contentType(), response.as(responseClass));
    }

    protected static <T> ApiResponse<List<T>> getList(String endpoint, Class<T> responseClass) {
        Response response = RestAssured.given().get(endpoint);
        List<T> body = response.jsonPath().getList("", responseClass);
        return new ApiResponse<>(response.statusCode(), response.contentType(), body);
    }

    protected static <T> ApiResponse<T> post(String endpoint, Object requestBody, Class<T> responseClass) {
        Response response = RestAssured.given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .post(endpoint);
        return new ApiResponse<>(response.statusCode(), response.contentType(), response.as(responseClass));
    }
}