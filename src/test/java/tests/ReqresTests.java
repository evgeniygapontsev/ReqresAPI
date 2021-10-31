package tests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.core.IsEqual.equalTo;


public class ReqresTests {

    @Test
    public void getListUsersTest() {
        when().
                get("https://reqres.in/api/users?page=2)").
        then().
                log().all().
                statusCode(200).
                body("total", equalTo(12));

    }

    @Test
    public void getSingleUserTest() {
        when().
                get("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("data.last_name", equalTo("Weaver"));
    }

    @Test
    public void getSingleUserNotFoundTest() {
        when().
                get("https://reqres.in/api/users/23").
        then().
                log().all().
                statusCode(404);
    }

    @Test
    public void getListResourceTest() {
        when().
                get("https://reqres.in/api/unknown").
        then().
                log().all().
                statusCode(200).
                body("data.name[4]", equalTo("tigerlily"));
    }

    @Test
    public void getSingleResourceTest() {
        when().
                get("https://reqres.in/api/unknown/2").
        then().
                log().all().
                statusCode(200).
                body("data.pantone_value", equalTo("17-2031"));
    }

    @Test
    public void getSingleResourceNotFoundTest() {
        when().
                get("https://reqres.in/api/unknown/23").
        then().
                log().all().
                statusCode(404);
    }

    @Test
    public void postCreateUserTest() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/users").
        then().
                log().all().
                statusCode(201).
                body("name", equalTo("morpheus"),
                        "job", equalTo("leader"));
    }

    @Test
    public void putUpdateUserTest() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                put("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("name" , equalTo("morpheus"),
                        "job", equalTo("zion resident"));
    }

    @Test
    public void patchUpdateUserTest() {
        given().
                body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                patch("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(200).
                body("name" , equalTo("morpheus"),
                        "job", equalTo("zion resident"));
    }

    @Test
    public void deleteUserTest() {
        when().
                get("https://reqres.in/api/users/2").
        then().
                log().all().
                statusCode(204);
    }

    @Test
    public void postRegisterSuccessfulTest() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/register").
        then().
                log().all().
                statusCode(200).
                body("id", equalTo(4),
                        "token", equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    public void postRegisterUnsuccessfulTest() {
        given().
                body("{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/register").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));

    }

    @Test
    public void postLoginSuccessfulTest() {
        given().
                body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/login").
        then().
                log().all().
                statusCode(200).
                body("token", equalTo("QpwL5tke4Pnpja7X4"));

    }

    @Test
    public void postLoginUnsuccessfulTest() {
        given().
                body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}").
                header("Content-type", "application/json").
                log().all().
        when().
                post("https://reqres.in/api/login").
        then().
                log().all().
                statusCode(400).
                body("error", equalTo("Missing password"));

    }

    @Test
    public void getDelayedResponseTest() {
        when().
                get("https://reqres.in/api/users?delay=3").
        then().
                log().all().
                statusCode(200).
                body("total", equalTo(12),
                        "data.first_name[4]", equalTo("Charles"));
    }

}
