import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;

import static com.jayway.restassured.RestAssured.given;

public class UserUpload {
    @Test
    public void Poster() throws Exception {

        File src = new File("C:\\Users\\User\\Downloads\\GOURMET CIRCLE Contact Details (1).xlsx");
        FileInputStream fis = new FileInputStream(src);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh1 = wb.getSheetAt(0);
        //       XSSFSheet sh2 = wb.getSheetAt(2);


        String api_key = "";
        int users = sh1.getLastRowNum();

        for (int i = 2; i <= 14; i++) {

            String id = "";
            String name = sh1.getRow(i).getCell(1).getStringCellValue();
            String country_code = "91";
            String cell_number = sh1.getRow(i).getCell(2).getRawValue();
            String email = "";
            String dob = "";
            String anniversary = "";
            String age_group = "";
            String gender = "";
            String offeropt = "Yes";
            String outletid =
                    "49467a1f-e928-4304-b364-bfdeba89f87a";
            //sh1.getRow(i).getCell(7).getStringCellValue();
            String source = "mReward";
            String programid = "d3115c10-09b4-4fde-87e4-c69947971f49";
            String points = sh1.getRow(i).getCell(3).getRawValue();
            String district = "";
            String preference = "";

            Response resp = given().
                    header("$apiKey", api_key).
                    body("{" +
                            "  \"event\": \"Data Upload\"," +
                            "  \"data\": [" +
                            "    {" +
                            "      \"id\": \"" + id + "\"," +
                            "      \"name\": \"" + name + "\"," +
                            "      \"cellCountryCode\": \"" + country_code + "\"," +
                            "      \"cellNumber\": \"" + cell_number + "\"," +
                            "      \"email\": \"" + email + "\"," +
                            "      \"dateOfBirth\": \"" + dob + "\"," +
                            "      \"anniversary\": \"" + anniversary + "\"," +
                            "      \"ageGroup\": \"" + age_group + "\"," +
                            "      \"gender\": \"" + gender + "\"," +
                            "      \"offersOptIn\": \"" + offeropt + "\"," +
                            "      \"outletId\": \"" + outletid + "\"," +
                            "      \"source\": \"" + source + "\"," +
                            "      \"autoRegister\": true," +
                            "      \"loyaltyMemberships\": [" +
                            "        {" +
                            "          \"name\": \"" + name + "\"," +
                            "          \"loyaltyProgramId\": \"" + programid + "\"," +
                            "          \"redeemableLoyaltyPoints\": \"" + points + "\"," +
                            "          \"source\": \"" + source + "\"" +
                            "        }" +
                            "      ]," +
                            "      \"district\": \"" + district + "\"," +
                            "      \"nonVegPreferences\": \"" + preference + "\"" +
                            "    }" +
                            "  ]" +
                            "}").
                    when().
                    contentType(ContentType.JSON).
                    post("");


            String response = resp.asString();

            System.out.println(response);
            String id1 = resp.then().contentType(ContentType.JSON).extract().path("data[0].id");
            System.out.println("CX ID " + id1);

            String id3 = resp.then().contentType(ContentType.JSON).extract().path("data[0].loyaltyMemberships[0].id");
            System.out.println("Membership ID " + id3);


            String status = resp.then().contentType(ContentType.JSON).extract().path("status");
            System.out.println(i + " " + status);
            Thread.sleep(3000);

        }


    }


}
