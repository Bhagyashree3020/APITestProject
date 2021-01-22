import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;


import static io.restassured.RestAssured.*;

import java.util.Scanner;




public class Login {

	public static void main(String[] args) 
	{
		String response, message ;
		JsonPath js ;
		RestAssured.baseURI="http://164.52.194.28:19085/";
		//System.out.println(baseURI);
		response= given().log().all()
		.header("Content-Type", "application/json")
		.body("{\"mobileNumber\":7588414426,\"country\":91}")
		.when().post("/login")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		//System.out.println(response);
		
	    js =new JsonPath(response);      
		message = js.get("message");
		System.out.println(message);
		
		System.out.println("\n Enter valid OTP : ");
		Scanner sc= new Scanner(System.in);
		String otp=sc.next();
		System.out.println(otp);
		//Authenticate otp
		
		 response = given().log().all()
				.header("Content-Type","application/json")
				.body("{\"mobileNumber\":7588414426,\"oneTimePassword\":"+otp+"}")
				.when().post("/authenticate")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
		
	 js =new JsonPath(response);      
	 message = js.get("message");
		System.out.println(message);
		String token = js.get("token");
		System.out.println(token);
		response= given().log().all()
				.header("Content-Type","application/json")
				.body("{\"token\":"+token+"}")
				.when().post("/banner")
				.then().log().all().assertThat().statusCode(200)
				.extract().response().asString();
				System.out.println(response);
		
		

	}

}
