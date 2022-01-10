import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.checkerframework.checker.units.qual.A;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.v85.fetch.model.AuthChallengeResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;

public class APITest {
    //ObjectMapper om = new ObjectMapper();
    //Root root = om.readValue(myJsonString), Root.class);
    WebDriver driver;

    //LIST USERS
    @Test
    public void first() throws InterruptedException{

        Root users = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/users?page=2")
                //.then()
                //.statusCode(200)
                .body()
                //.prettyPrint();
                .as(Root.class);
        System.out.println(users.toJson());

        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        driver = new ChromeDriver();
        //driver.get("https://reqres.in");
        //Thread.sleep(1000);
            users.data.forEach(item ->{
            String avatar = item.avatar;
            System.out.println(avatar);
           driver.get(avatar);
                        try {
                            TimeUnit.SECONDS.sleep(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        //Thread.sleep(3000);
                // driver.close();
                  }
        );
        driver.close();
        }

        //SINGLE USER
    @Test
    public void second(){

        RootSingle user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/users?id=2")
                //.then()
                //.statusCode(200)
                .body()
                //.prettyPrint();
                .as(RootSingle.class);
        System.out.println(user.toJson());

        Response response = RestAssured.get("https://reqres.in/api/users/2");
        System.out.println(response.getStatusCode());
        int actualResult = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(),200);

        String name = user.data.first_name;
        Assert.assertEquals(name, "Janet");
    }

    //SINGLE USER NOT FOUND
    @Test
    public void third(){

        RootSingle user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/users/23")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(RootSingle.class);
        System.out.println(user.toJson());
        Response response = RestAssured.get("https://reqres.in/api/users/23");
        System.out.println(response.getStatusCode());
        int actualResult = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(),404);

    }

   //LIST <RESOURCE>
    @Test
    public void four(){

        Root user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/unknown")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root.class);
        System.out.println(user.toJson());

        String name = user.data.get(1).name;
        Assert.assertEquals(name, "fuchsia rose");

        Response response = RestAssured.get("https://reqres.in/api/unknown");
        int actualResult = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(),200);

    }

    //SINGLE <RESOURCE>
    @Test
    public void five(){

        RootSingle user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/unknown/2")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(RootSingle.class);
        System.out.println(user.toJson());

        String name = user.data.color;
        Assert.assertEquals(name, "#C74375");

        Response response = RestAssured.get("https://reqres.in/api/unknown");
        int actualResult = response.getStatusCode();
        Assert.assertEquals(response.getStatusCode(),200);

    }
    //SINGLE <RESOURCE> NOT FOUND
    @Test
    public void six(){

        RootSingle user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/unknown/23")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(RootSingle.class);
        System.out.println(user.toJson());
        Response response = RestAssured.get("https://reqres.in/api/unknown/23");

        Assert.assertEquals(response.getStatusCode(),404);

    }

    //CREATE
    @Test
    public void seven(){
        Response response = RestAssured.get("https://reqres.in/api/users");
        Root_7 user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}")
                .when()
                .post("/api/users")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_7.class);
        System.out.println(user.toJson());
        Assert.assertEquals(user.job,"leader");
        Assert.assertEquals(response.getStatusCode(),201);

    }

    //UPDATE
    @Test
    public void eight(){

        Root_8 user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .put("/api/users/2")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_8.class);
        System.out.println(user.toJson());

        Response response = RestAssured.get("https://reqres.in/api/users/2");
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(user.job,"zion resident");
    }

    //UPDATE (patch)
    @Test
    public void nine(){

        Root_8 user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"zion resident\"\n" +
                        "}")
                .when()
                .patch("/api/users/2")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_8.class);
        System.out.println(user.toJson());
        Response response = RestAssured.get("https://reqres.in/api/users/2");
        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(user.name,"morpheus");
    }

    //DELETE
    @Test
    public void ten(){

        Response response1 = RestAssured.delete("https://reqres.in/api/users/2");
//        Root user = given()
//                .contentType(ContentType.JSON)
//                .baseUri("https://reqres.in")
//                .when()
//                .delete("/api/users/2")
//                //.then()
//                //.statusCode();
//                //.body()
//                //.prettyPrint();
//               .as(Root.class);
////        System.out.println(user.toJson());
        System.out.println(response1.getStatusCode());
        Assert.assertEquals(response1.getStatusCode(),204);
    }

    //REGISTER - SUCCESSFUL
    @Test
    public void eleven(){
        Response response = RestAssured.get("https://reqres.in/api/register");
        Root_11 user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"pistol\"\n" +
                        "}")
                .when()
                .post("/api/register")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_11.class);
        System.out.println(user.toJson());

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(user.token,"QpwL5tke4Pnpja7X4");
    }

    //REGISTER - UNSUCCESSFUL
    @Test
    public void twelve(){
        Response response = RestAssured.get("https://reqres.in/api/register");
        Root_error user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{{\n" +
                        "    \"email\": \"sydney@fife\"\n" +
                        "}")
                .when()
                .post("/api/register")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_error.class);
        System.out.println(user.toJson());

        Assert.assertEquals(response.getStatusCode(),400);
        Assert.assertEquals(user.error,"Missing password");
    }

    //LOGIN - SUCCESSFUL
    @Test
    public void thirteen(){
        Response response = RestAssured.get("https://reqres.in/api/login");
        Root_13 user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"email\": \"eve.holt@reqres.in\",\n" +
                        "    \"password\": \"cityslicka\"\n" +
                        "}")
                .when()
                .post("/api/login")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_13.class);
        System.out.println(user.toJson());

        Assert.assertEquals(response.getStatusCode(),200);
        Assert.assertEquals(user.token,"QpwL5tke4Pnpja7X4");
    }

    //LOGIN - UNSUCCESSFUL
    @Test
    public void fourteen(){
        Response response = RestAssured.get("https://reqres.in/api/login");
        Root_error user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .body("{\n" +
                        "    \"email\": \"peter@klaven\"\n" +
                        "}")
                .when()
                .post("/api/login")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root_error.class);
        System.out.println(user.toJson());

        Assert.assertEquals(response.getStatusCode(),400);
        Assert.assertEquals(user.error,"Missing password");
    }

    //DELAYED RESPONSE
    @Test
    public void fifteen(){
        Response response = RestAssured.get("https://reqres.in/api/users?delay=3");
        Root user = given()
                .contentType(ContentType.JSON)
                .baseUri("https://reqres.in")
                .when()
                .get("/api/users?delay=3")
                //.then()
                //.statusCode()
                .body()
                //.prettyPrint();
                .as(Root.class);
        System.out.println(user.toJson());

        Assert.assertEquals(response.getStatusCode(),200);
        System.out.println(user.data.size());
        Assert.assertEquals(user.data.get(0).last_name,"Bluth");
    }





    }


