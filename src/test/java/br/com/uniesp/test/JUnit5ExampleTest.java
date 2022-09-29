package br.com.uniesp.test;

import br.com.uniesp.entidate.PessoaRequest;
import br.com.uniesp.entidate.PessoaResponse;
import io.restassured.http.ContentType;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.*;

class JUnit5ExampleTest {

    @BeforeEach
    void configuraApi() {
        baseURI = 	"https://reqres.in/";
    }
    @Test
    void justAnExample() {

    }
    @Test
    @DisplayName("esse teste faz isso")
    @Tag("E2E")
    void methodGet() {
       given().log().all()
                .when()
                .get("api/users/2")
                .then().contentType("application/json")
                .statusCode(HttpStatus.SC_OK)
                .log();
    }

    @Test
    void methodPostFull() {
        PessoaRequest pessoaRequest = new PessoaRequest("morpheus","leader");

        basePath= "/api/users";
        PessoaResponse as = given().log().all()
                .contentType(ContentType.JSON)
                .when()
                .body(pessoaRequest).log().all()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED)
                .and()
                .log().all().extract().response().as(PessoaResponse.class);

        Assertions.assertNotNull(as);
        Assertions.assertNotNull(as.getId());
        Assertions.assertEquals(pessoaRequest.getName(), as.getName());
        Assertions.assertEquals(pessoaRequest.getJob(), as.getJob());
    }
    @Test
    public void methodPost() {
        PessoaRequest pessoaRequest = new PessoaRequest("thiago", "QA");

        basePath = "/api/users";
        PessoaResponse as = given()
                .contentType("application/json")
                .body(pessoaRequest)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED).log().all()
                .extract().response().as(PessoaResponse.class);
    }
    @Test
    public void methodPost2() {
//        PessoaRequest pessoaRequest = new PessoaRequest("thiago", "QA");

        Map<String,Object> params = new HashMap<>();
        params.put("name","thiago");
        params.put("job","qa");


        basePath = "/api/users";
        PessoaResponse as = given()
                .contentType("application/json")
                .body(params)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED).log().all()
                .extract().response().as(PessoaResponse.class);
    }
    @Test
    public void methodPost3() {

        String json = "{\n" +
                "    \"name\": \"morpheus\",\n" +
                "    \"job\": \"leader\"\n" +
                "}";


        basePath = "/api/users";
        PessoaResponse as = given()
                .contentType("application/json")
                .body(json)
                .when()
                .post("/")
                .then()
                .statusCode(HttpStatus.SC_CREATED).log().all()
                .extract().response().as(PessoaResponse.class);
    }
}