import java.io.IOException;
import java.text.DecimalFormat;

public class Conversion {

    public static String getConversion(String message, Model model) throws IOException {


        double BYN, USD = 0, EUR = 0, RUB = 0, PLN = 0;
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
            RUB = BYN / model.getRUB() * 100;
            PLN = BYN / model.getPLN() * 10;
        } catch (Exception e) {
            e.printStackTrace();
        }
        DecimalFormat f = new DecimalFormat(".###");
        return "" + BYN + "  белорусских рублей это:" + "\n" +
                f.format(USD) + " USD" + "\n" +
                f.format(EUR) + " EUR" + "\n" +
                f.format(RUB) + " RUB" + "\n" +
                f.format(PLN) + " PLN";


    }

}
