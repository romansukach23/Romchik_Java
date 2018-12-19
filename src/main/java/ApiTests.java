import com.jayway.jsonpath.JsonPath;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.equalTo;

public class ApiTests {
    String userToken = "8acc6ecd71a1f13d80e4e1c9a913c2ce";
    int userGameID = 6275006;
    String callUrl = "api/events/status?token=" + userToken + "&game_account_id=" + userGameID;

    @Before
    public void setup() {
        RestAssured.baseURI = "http://hov-uat-lo01.productmadness.com";
    }

    @Test
    public void statusCodeTest() {
        when().get(callUrl).then().log().all().statusCode(200);


    }

    @Test
    public void schemaValidationTest() {
        when().get(callUrl).then().assertThat().body(matchesJsonSchemaInClasspath("event_status_schema.json"));
    }

    @Test
    public void fieldValueValidationTest () {
        when().get(callUrl).then().assertThat().body("events[0].collections[1].collectibles[10].quantity", equalTo(1));
    }

}
