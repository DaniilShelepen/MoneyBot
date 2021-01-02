import java.io.IOException;
import java.text.DecimalFormat;

public class Conversion {

    public static String getConversion(String message, Model model) throws IOException {


        double BYN, USD = 0, EUR = 0;
        if (message.contains(",")) message = message.replace(",", ".");
        try {
            BYN = Double.parseDouble(message);
        } catch (NumberFormatException e) {
            return "Error";
        }
        try {
            Course.getCourse(model);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            USD = BYN / model.getUSD();
            EUR = BYN / model.getEUR();
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "" + BYN + "  белорусских рублей это:" + "\n" +
                USD + " USD" + "\n" + "или" + "\n" +
                EUR + " EUR";
    }

}
