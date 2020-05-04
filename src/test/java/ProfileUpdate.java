import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.response.Response;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;

import static com.jayway.restassured.RestAssured.given;

public class ProfileUpdate {

    @Test
    public void pointer() throws Exception {

        //       File src = new File("C:\\Users\\User\\Downloads\\Tea Villa Cafe Wakad Loyalty Customers - 9 Dec.xlsx");
        File src1 = new File("C:\\Users\\User\\Downloads\\Tea Vila Pune Loyalty Customers Data except Wakad.xlsx");

        FileInputStream fis = new FileInputStream(src1);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sh1 = wb.getSheetAt(0);
        //  XSSFSheet sh2 = wb.getSheetAt(1);


        String api_key = "";
        int users = sh1.getLastRowNum();

        for (int i = 3; i <= users; i++) {

            String c_id = sh1.getRow(i).getCell(3).getStringCellValue();
            String l_id = sh1.getRow(i).getCell(4).getStringCellValue();
            String point = sh1.getRow(i).getCell(8).getRawValue();
            int ponts = Integer.parseInt(point) * 13;
            String points = String.valueOf(ponts);
            String o_id = sh1.getRow(i).getCell(7).getStringCellValue();

            try {
                Response resp = given().
                        header("$apiKey", api_key).
                        body("{" +
                                "  \"billNumber\": \"11114\"," +
                                "  \"customerId\": \"" + c_id + "\"," +
                                "  \"outletId\": \"" + o_id + "\"," +
                                "  \"totalPrice\": \"" + points + "\"," +
                                "  \"orderItems\": [" +
                                "    {" +
                                "      \"quantity\": \"1\"," +
                                "      \"unitPrice\": \"" + points + "\"," +
                                "      \"item\": {" +
                                "        \"name\": \"\"," +
                                "        \"referenceKey\": \"\"" +
                                "      }" +
                                "    }" +
                                "  ]," +
                                "  \"orderType\": \"instore\"," +
                                "  \"loyaltyRedemptions\": [" +
                                "    {" +
                                "      \"loyaltyMembershipId\": \"" + l_id + "\"," +
                                "      \"rewards\": \"0\"" +
                                "    }" +
                                "  ]" +
                                "}").when().
                        contentType(ContentType.JSON).
                        post("");


                String response = resp.asString();
                System.out.println(response);
                try {
                    String status = resp.then().contentType(ContentType.JSON).extract().path("status");
                    System.out.println(i + " " + status);
                    Thread.sleep(10000);
                } catch (Throwable h) {
                    System.out.println("Server is down. Sleeping for a minute");
                    Thread.sleep(60000);
                }
            } catch (Exception ex) {
                System.out.println(ex);
                ex.printStackTrace();
                System.out.println("Exception found. Sleeping for a minute. Please check NETWORK CONNECTION");
                Thread.sleep(60000);

            }
        }
    }
}
