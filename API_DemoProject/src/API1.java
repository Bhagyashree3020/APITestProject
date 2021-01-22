import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import Files.Payload;

public class API1 {

	public static void main(String[] args) 
	{
		//validate if Add place API is working as expected
		//Add Place-> Update place with new address
		//-> Get place to validate if new address is present 
		
		//given - all input details 
		//when - submit the API - recourse, http method
		//then - validate the response
		RestAssured.baseURI="https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123")
		.header("Content-Type","application/json")
		.body(Payload.AddPlace()).when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200)
		.body("scope", equalTo ("APP"))
		.header("server", "Apache/2.4.18 (Ubuntu)")
		.extract().response().asString();
	// For updating address we need place id so it is retrieving 
		//by parsing json response into string 
		System.out.println(response);
		JsonPath js =new JsonPath(response);
		String place_id =js.get("place_id");
		System.out.println(place_id);
		
		//Update place -> put 
        String address = "70 winter walk, USA";
		given().log().all().queryParam("key","qaclick123")
		.header("Content-Type","application/json")
		.body("{\r\n" + 
				"\"place_id\":\""+place_id+"\",\r\n" + 
				"\"address\":\""+address+"\n" + 
				"\"key\":\"qaclick123\"\n" + 
				"}")
		.when().put("/maps/api/place/update/json")
		.then().assertThat().log().all().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
		
		/* Validate place -> Get 
		given().log().all().queryParam("key", "qaclick123")
		.queryParam("place_id", place_id)
		.when().get("/maps/api/place/get/json")
		.then().log().all().assertThat()*/
		
		

	}

}
