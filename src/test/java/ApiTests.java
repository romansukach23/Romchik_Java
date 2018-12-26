import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

@FixMethodOrder(MethodSorters.JVM)
public class ApiTests {

    CollectionObjects collectionObjects = new CollectionObjects();

    String userToken = "8acc6ecd71a1f13d80e4e1c9a913c2ce";
    int userGameID = 6275006;
    String callUrl = "api/events/status?token=" + userToken + "&game_account_id=" + userGameID;
    String incorrectGameAccountIdURL = "api/events/status?token" + userToken + "&game_account_id=6276666";
    String incorrectTokenURL = "api/events/status?token" + "8acc6ecd71a1f13d80e4e1c9a9139999" + "&game_account_id=" + userGameID;


    public void jsonpathToString() {
        when().get(callUrl).jsonPath().getString("events[0].status");
        return;
    }

    @Before
    public void setup() {
        RestAssured.baseURI = "http://hov-uat-lo01.productmadness.com";

    }

    @Test
    public void statusCodeTest() {
        when().get(callUrl).then().log().all().statusCode(200);

    }

    @Test
    public void incorrectGameAccountTest() {
        when().get(incorrectGameAccountIdURL).then().log().all().assertThat().statusCode(422);
    }

    @Test
    public void incorrectTokenTest() {
        when().get(incorrectTokenURL).then().log().all().assertThat().statusCode(422);
    }

    @Test
    public void schemaValidationTest() {
        when().get(callUrl).then().assertThat().body(matchesJsonSchemaInClasspath("event_status_schema.json"));
    }

    @Test
    public void fieldValueValidationTest() {
//        when().get(callUrl).then().log().all().assertThat().body("events[0].collections[0].collectibles[0].quantity", equalTo(1));
        when().get(callUrl).then().log().all().assertThat().body(collectionObjects.quantity_collectible1_1, equalTo(1));
    }

    @Test
    public void statusTestInProgress() {
        when().get(callUrl).then().log().all().assertThat().body("events[0].status", equalTo("in_progress"));
    }


}
