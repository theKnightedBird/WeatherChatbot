import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.time.ZonedDateTime;


public class Main {
    static final String key = "9affefXFTg46cZyHejEPCMbOzXS7jXn0";

    public static void main(String[] args) throws aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarFrenchRadicalAndGermanPoliceSpy {
        Scanner scanner = new Scanner(System.in);
        System.out.println("what you want");
        String prompt;
        while (true) {
            prompt = scanner.nextLine();
            respond(prompt);
            System.out.println("what else you want? stop to stop");
        }
    }

    public static void respond(String prompt) throws aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarFrenchRadicalAndGermanPoliceSpy {

        String promptSanitized = clean(prompt);
        String response;
        if (promptSanitized.contains("weather")) {
            response = weather();
        } else if (prompt.startsWith("random(") && prompt.contains(",") && prompt.endsWith(")")){
            response = rng(prompt);
        } else if (promptSanitized.contains("bad") || promptSanitized.contains("hate") || promptSanitized.contains("stupid")){
            response = doxx();
        } else if (promptSanitized.equals("stop") || promptSanitized.equals("kill yourself")) {
            throw new aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarFrenchRadicalAndGermanPoliceSpy("this is intended behavior, pls sahu give good grade");
        } else if (promptSanitized.contains(" time ") || promptSanitized.startsWith("time ") || promptSanitized.endsWith(" time") || promptSanitized.equals("time")) {
            response = time();
        } else {
            response = normal();
        }
        if (!response.equals("time is relative") && prompt.contains("?")) { response += "?"; }
        System.out.println(response);
    }

    public static String clean(String prompt) {
        return prompt.toLowerCase().replaceAll("([^\\w\\s])+", "").trim();
    }

    public static String weather() {
        Scanner temp = new Scanner(System.in);
        try{
            System.out.println("what city");
            String city = temp.nextLine();
            return readFromLocation(city);
        } catch (Exception e) {
            return "that isnt a real city moron";
        }
    }

    public static String rng(String prompt) {
        try {
            int comma = prompt.indexOf(",");
            int fin = prompt.indexOf(")");
            int start = Integer.parseInt(prompt.substring(7,comma));
            int end = Integer.parseInt(prompt.substring(comma+1,fin));
            return Integer.toString((int)( start + Math.random() * (end - start + 1)));
        } catch (Exception e) {
            return "you stupid idiot input random right moron";
        }
    }

    public static String time() {
        ZonedDateTime currentZone = ZonedDateTime.now();
        return ("the time is " + currentZone.getHour()
                + ":" +currentZone.getMinute()
                + " and " + currentZone.getSecond()
                + " seconds on " + currentZone.getDayOfWeek()
                + " " + currentZone.getMonth()
                + " " + currentZone.getDayOfMonth()
                + " of the year " + currentZone.getYear()).toLowerCase();
    }

    public static String doxx() {
        try {
            String ip = String.valueOf(InetAddress.getLocalHost());
            ip = ip.substring(ip.indexOf("/") + 1);
            return "your ip is " + ip;
        } catch (Exception e) {
            return "get better internet, dumbass";
        }
    }

    public static String normal() {
        String responses[] = {
                "i don't know what youre talking about",
                "i wasnt there, and if i was, i was asleep",
                "i saw nothin'",
                "no hablo ingles",
                "i aint no snitch",
                "i aint talkin to no feds",
                "i aint answering any questions and i wanna lawyer",
                "i plead the fifth",
                "what"
        };
        return responses[(int)(Math.random() * responses.length)];
    }

    public static String readFromLocation(String address) throws IOException {
        address = address.replace(" ", "+");
        String latLong = readFromURL(
                "https://www.mapquestapi.com/geocoding/v1/address?key=" + key + "&location="+ address,
                "latLng"
        );
        String lat = latLong.substring(
                latLong.indexOf("lat:") + 4,
                latLong.indexOf(",")
        );
        String lng = latLong.substring(
                latLong.indexOf("lng:") + 4,
                latLong.indexOf("}")
        );
        //System.out.println(lat +  "end         " + lng + "end");
        String URI = "https://api.weather.gov/points/" + lat + "," + lng;
        //System.out.println(URI);
        String nextURL = readFromURL(URI, "forecast");
        nextURL = nextURL.replace(" ", "");
        //System.out.println(nextURL);
        String forecast = readFromURL(nextURL, "detailedForecast");
        forecast = forecast.toLowerCase();
        forecast = forecast.replace("%", " percent").replace(".", ",");
        forecast = "the weather for " + address.replace("+", " ") + " is" + forecast
                + " and no matter what the weather is, you should touch grass";
        return forecast;
    }

    public static String readFromURL(String uri, String property) throws IOException {
        URL url = new URL(uri);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Weather chatbot project for school, jefferydoudou@gmail.com");
        BufferedReader input = new BufferedReader( new InputStreamReader(connection.getInputStream()) );
        String output = parseJson(input, property);
        connection.getInputStream().close();
        return output;
    }

    public static String parseJson(BufferedReader reader, String property) throws IOException {
        String outputLine = "";
        String output = null;
        do {
            outputLine = outputLine.replace("\"", "");
            outputLine = outputLine.replace("{", "");
            if (outputLine.contains( property + ":")){
                output = outputLine.substring(outputLine.indexOf(property + ":") + (property.length()) + 1);
                //System.out.println(output);
                output = output + ",";
                output = output.substring(0, output.indexOf(",", output.indexOf(",") + 1));
                //System.out.println(output);
            }
            outputLine = reader.readLine();
        } while (outputLine != null);
        return output;
    }
}

/**
    This class purely exists to rename Exception into something funny, so we can break the loop by not handling said
    exception.
 */
class aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarFrenchRadicalAndGermanPoliceSpy extends Throwable {
    public aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarFrenchRadicalAndGermanPoliceSpy(String message) {
        super(message);
    }
}


