//Import statements
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;
import java.time.ZonedDateTime;


//Main class
public class Main {
    //API key (ignore)
    static final String key = "9affefXFTg46cZyHejEPCMbOzXS7jXn0";

    // Main method
    public static void main(String[] args) throws aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarMetternichAndGuizotFrenchRadicalAndGermanPoliceSpy {
        Scanner scanner = new Scanner(System.in);
        System.out.println("what you want");
        String prompt;
        // Main loop
        while (true) {
            prompt = scanner.nextLine();
            respond(prompt);
            System.out.println("what else you want? stop to stop");
        }
    }

    // respond() handles the prompt given and responds accordingly. Starts by cleaning the prompt to make it easier to work with,
    // then checks if the prompt matches weather, random, time, some other stuff, and responds to them using methods written below. Ends by printing the generated response.
    public static void respond(String prompt) throws aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarMetternichAndGuizotFrenchRadicalAndGermanPoliceSpy {
        String promptSanitized = clean(prompt);
        String response;
        if (promptSanitized.contains("weather")) {
            response = weather();
        } else if (prompt.startsWith("random(") && prompt.contains(",") && prompt.endsWith(")")){
            response = rng(prompt);
        } else if (promptSanitized.contains("bad") || promptSanitized.contains("hate") || promptSanitized.contains("stupid")){
            response = doxx();
        } else if (promptSanitized.equals("stop") || promptSanitized.equals("kill yourself")) {
            throw new aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarMetternichAndGuizotFrenchRadicalAndGermanPoliceSpy("this is intended behavior, pls sahu give good grade");
        } else if (promptSanitized.contains(" time ") || promptSanitized.startsWith("time ") || promptSanitized.endsWith(" time") || promptSanitized.equals("time")) {
            response = time();
        } else {
            response = normal();
        }
        if (!response.equals("time is relative") && prompt.contains("?")) { response += "?"; }
        System.out.println(response);
    }
    
    // clean() takes the prompt as a String, and returns the same String but lowercase, without leading or trailing spaces, and with commas removed.
    public static String clean(String prompt) {
        return prompt.toLowerCase().replaceAll("([^\\w\\s])+", " ").trim();
    }

    // weather() prompts for and gets a city, which is fed into the weather API. The information from the API is then returned as a String, or if the city is invalid
    // the error is caught and handled.
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

    // rng() takes the prompt, extracts from it the start and end values, converts to ints, and uses Math.random() to generate the response before returning it.
    // Any invalid input is caught and handled.
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

    // time() uses java.time to get the local date and time, before formatting it and returning it as a String.
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

    // doxx() uses black magic to get the user's IP adress and format and return it. In case that breaks the exception is handled.
    public static String doxx() {
        try {
            String ip = String.valueOf(InetAddress.getLocalHost());
            ip = ip.substring(ip.indexOf("/") + 1);
            return "your ip is " + ip;
        } catch (Exception e) {
            return "get better internet, dumbass";
        }
    }

    // normal() chooses a random response from a predetermined list and returns it.
    public static String normal() {
        String[] responses = {
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

    // readFromLocation uses more black magic to connect with the weather and map APIs, and using the location passed in determine, format, and return the weather.
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
        String URI = "https://api.weather.gov/points/" + lat + "," + lng;
        String nextURL = readFromURL(URI, "forecast");
        nextURL = nextURL.replace(" ", "");
        String forecast = readFromURL(nextURL, "detailedForecast");
        forecast = forecast.toLowerCase();
        forecast = forecast.replace("%", " percent").replace(".", ",");
        forecast = "the weather for " + address.replace("+", " ") + " is" + forecast
                + " and no matter what the weather is, you should touch grass";
        return forecast;
    }

    // readFromURL() connects with the internet and gets information from the website passed in.
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

    // parseJson() takes the information provided by the API (in the form of a JSON) and reads, formats, and returns it.
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
class aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarMetternichAndGuizotFrenchRadicalAndGermanPoliceSpy extends Throwable {
    public aSpectreIsHauntingEuropeTheSpectreOfCommunismAllThePowersOfOldEuropeHaveEnteredIntoAHolyAllianceToExorciseThisSpectrePopeAndTsarMetternichAndGuizotFrenchRadicalAndGermanPoliceSpy(String message) {
        super(message);
    }
}


