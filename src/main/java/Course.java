import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;

public class Course {


    public static String getCourse(Model model) throws IOException, InterruptedException {

        URL url = new URL("https://www.nbrb.by/api/exrates/rates?periodicity=0");

        Scanner in = new Scanner((InputStream) url.getContent());

        String result = "";
        while (in.hasNext()) {
            result += in.nextLine();
        }
        result = result.replace("[", "{ \n \"Course\": [");
        result = result.replace("]", "] \n }");
        JSONObject object = new JSONObject(result);
        JSONArray getArray = object.getJSONArray("Course");
        for (int i = 0; i < getArray.length(); i++) {
            JSONObject obj = getArray.getJSONObject(i);
            model.setID(obj.getInt("Cur_ID"));
            if (model.getID() == 145) {
                model.setUSD(obj.getDouble("Cur_OfficialRate"));
            }
            if (model.getID() == 292) {
                model.setEUR(obj.getDouble("Cur_OfficialRate"));
            }

        }


        return "USD: " + model.getUSD() + "\n" +
                "EUR: " + model.getEUR() + "\n";
    }


}

