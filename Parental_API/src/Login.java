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
		System.out.println("\n=========== First API Run Sucessfully============");
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
			String token =js.get("data.token");
			System.out.println(token);
		 
			 System.out.println("\n=========== Second API Run Sucessfully============");	
			 
				
			 response = given().log().all()
						.header("Content-Type","application/json")
						.body("{\"mobileNumber\":0011223344,\"token\":"+token+",\"bot_id\":\"000A7B\"}")
						.when().post("/getAppConfiguration")
						.then().log().all().assertThat().statusCode(200)
						.extract().response().asString();
			 System.out.println("\n=========== Third API Run Sucessfully============");	
			 
			 response = given().log().all()
						.header("Content-Type","application/json")
						.body("{\"mobileNumber\":0011223344,\"token\":"+token+",\"bot_id\":\"000A7B\"}")
						.when().post("/botAssociation")
						.then().log().all().assertThat().statusCode(200)
						.extract().response().asString();
			 System.out.println("\n=========== Fourth API Run Sucessfully============");	
			 

	}

}
