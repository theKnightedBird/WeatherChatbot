import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Main {
    static final String key = "9affefXFTg46cZyHejEPCMbOzXS7jXn0";

    public static void main(String[] args) {
        System.out.println("what you want");
        while (true) {
            try {
                respond();
                System.out.println("what else you want?");
            } catch (Exception e) {
                break;
            }
        }
    }

    public static void respond() throws IOException {
        Scanner scanner = new Scanner(System.in);
        String prompt = scanner.nextLine();
        String promptSanitized = prompt.toLowerCase().replaceAll("([^\\w\\s])+", "").trim();
        String response = "";
        if (promptSanitized.contains("weather")) {
            try{
                System.out.println("what city");
                String city = scanner.nextLine();
                response = readFromLocation(city);
            } catch (Exception e) {
                System.out.println("that isnt a real city moron");
            }
        } else if (promptSanitized.startsWith("random(") && promptSanitized.contains(",") && promptSanitized.endsWith(")")){
            try {
                int comma = promptSanitized.indexOf(",");
                int fin = promptSanitized.indexOf(")");
                int start = Integer.parseInt(promptSanitized.substring(7,comma));
                int end = Integer.parseInt(promptSanitized.substring(comma+1,fin));
                response = Integer.toString((int)( start + Math.random() * (end - start + 1)));
            } catch (Exception e) {
                response = "you stupid idiot input random right moron";
            }
        } else if (promptSanitized.equals("stop")) {
            throw new IOException("Escape real");
        } else if (promptSanitized.contains(" time ") || promptSanitized.startsWith("time ") || promptSanitized.endsWith(" time") || promptSanitized.equals("time")) {
            response = "time is relative";
        } else {
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
            response = responses[(int)(Math.random() * responses.length)];
        }
        if (!response.equals("time is relative") && prompt.contains("?")) { response += "?"; }
        System.out.println(response);
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
        int status = connection.getResponseCode();
        if (status > 299) { return " get better internet"; }
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
