
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.log4testng.Logger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import static io.restassured.RestAssured.given;


public class FindTopSurfPlacesInSydney {
    private static Logger log = Logger.getLogger(FindTopSurfPlacesInSydney.class);
    private String apiKey = "0f7e7efcd22a4ac8ba0f113db4a50fdc";
    List<WeatherData> listweatherData = new ArrayList<WeatherData>();

    HashMap<Integer, String> areaCodes = new HashMap<Integer, String>();


    /*    var topbeaches ={bondi :'2026',freshwater:'2096', bronte :'2024', Tamarama :'2026', murrayrosepool:'2028', avalon :'2107', Maroubra:'2035', cogee:'2035', manly : '2095', Clovelly:'2031'}*/

    /**
     * Setup for the test
     */
    @BeforeClass
    public static void setupURL() {
        // here we setup the default URL and API base path to use throughout the tests
        RestAssured.baseURI = "https://api.weatherbit.io";
        RestAssured.basePath = "/v2.0/forecast";
    }

    @Test
    public void test1() {
        //Add all the area codes
        areaCodes.put(2026, "bondi");
        areaCodes.put(2096, "freshwater");
        for (HashMap.Entry<Integer, String> entry : areaCodes.entrySet()) {
            Response response = doGetRequest(entry.getKey(), apiKey);
            responseFilter(response, areaCodes.entrySet().toString());
        }
        sortAndPrint();
    }

    /**
     * Return 16 days of Weather data for a given post code
     *
     * @param postCode
     * @param apiKey
     * @return
     */
    public Response doGetRequest(Integer postCode, String apiKey) {
        return
                given().log().all().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON)
                        .request().queryParam("key", apiKey)
                        .queryParam("country_code", "AU")
                        .queryParam("postal_code", postCode)
                        .when()
                        .get("/daily").
                        then().log().all()
                        .statusCode(200).contentType(ContentType.JSON).extract().response();
    }

    public JSONObject responseFilter(Response response, String postCode) {
        JSONObject jsonObject = new JSONObject(response.getBody().asString());

        for (int i = 0; i < ((JSONArray) jsonObject.get("data")).length(); i++) {

            String temp = ((JSONObject) ((JSONArray) jsonObject.get("data")).get(0)).get("temp").toString();
            BigDecimal uv = (BigDecimal) ((JSONObject) ((JSONArray) jsonObject.get("data")).get(0)).get("uv");
            Float fuv = uv.floatValue();
            BigDecimal wind_spd = (BigDecimal) ((JSONObject) ((JSONArray) jsonObject.get("data")).get(0)).get("wind_spd");
            Float fwind_spd = wind_spd.floatValue();
            listweatherData.add(new WeatherData(postCode, temp, fuv, fwind_spd));

        }
        return jsonObject;
    }

    public void sortAndPrint() {
        Collections.sort(listweatherData, new PostCodeChainedComparator(
                new UvComparator(),
                new WindSpeedComparator())
        );

        System.out.println("\n*** After sorting:");

        for (WeatherData wd : listweatherData) {
            System.out.println(wd);
        }
    }

    @AfterClass
    public static void tearDown() {

        //Nothing in particular for this test.
    }


}




