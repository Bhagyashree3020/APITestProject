import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import static io.restassured.RestAssured.*;

import java.util.Scanner;


public class SignUP {

	public static void main(String[] args) 
	{
		RestAssured.baseURI="http://164.52.194.28:19085/";
		//System.out.println(baseURI);
		String response= given().log().all()
		.header("Content-Type", "application/json")
		.body("{\"mobileNumber\":7588414426,\"country\":91}")
		.when().post("/login")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		System.out.println(response);
		
		JsonPath js =new JsonPath(response);      
		String message = js.get("message");
		System.out.println(message);
		
		System.out.println("\n Enter valid OTP : ");
		Scanner sc= new Scanner(System.in);
		String otp=sc.next();
		System.out.println(otp);
		//Authenticate otp
		
		String response1 = given().log().all()
		.header("Content-Type","application/json").queryParam(otp)
	    .body("{\"mobileNumber\"\":7588414426\""
	    		+ "\"\n oneTimePassword \""+otp+"}")
	    .when().post("/authenticate")
	    .then().log().all().assertThat().statusCode(200)
	    .extract().response().asString();
		System.out.println(response1);
		
		JsonPath js1 =new JsonPath(response1);      
		String token = js1.get("token");
		System.out.println(token);
		
		
		

	}

}
