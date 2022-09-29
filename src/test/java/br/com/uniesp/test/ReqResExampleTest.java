package br.com.uniesp.test;

import br.com.uniesp.entidate.PessoaRequest;
import br.com.uniesp.entidate.PessoaResponse;
import com.google.gson.Gson;
import org.apache.http.HttpStatus;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;

public class ReqResExampleTest {

	@BeforeEach
	public void configuraApi() {
		baseURI = 	"https://reqres.in/";
	}
	@Test
	void methodGet() {
		given()
				.contentType(ContentType.JSON)
		.when()
//			.get("/api/users/{id}", 2)
			.get("/api/users/2")
		.then().contentType(ContentType.JSON)
			.statusCode(HttpStatus.SC_OK)
			.and()
			.log().all();
	}

	@Test
	public void methodPost() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");
		
		basePath= "/api/users";		
		PessoaResponse as = given()
			.contentType("application/json")
		.body(pessoaRequest)
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_CREATED).log().all()
			.extract().response().as(PessoaResponse.class);

		Assertions.assertNotNull(as);
		Assertions.assertNotNull(as.getId());
		Assertions.assertEquals(pessoaRequest.getName(), as.getName());
		Assertions.assertEquals(pessoaRequest.getJob(), as.getJob());
	}
	@Test
	public void methodPost2() {
		PessoaRequest pessoaRequest = new PessoaRequest("thiago","QA");
		Gson param = new Gson();

		basePath= "/api/users";		
		String as =
				given()
			.contentType("application/json")
		.body(param.toJson(pessoaRequest))
		.when()
			.post("/")
		.then()
			.statusCode(HttpStatus.SC_CREATED).log().all()
			.and().extract().response().path("name");
		
		System.out.println(as);
		Assertions.assertNotNull(as);
	}
}
