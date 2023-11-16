import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Scanner;

public class Main {
    static final String key = "9affefXFTg46cZyHejEPCMbOzXS7jXn0";

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String prompt = "";
        while (prompt!="exit"){
            System.out.println("what you want");
            prompt = cleanup(scanner.nextLine());
            respond(prompt,scanner);
            
        }
        }
        

    public static void respond(String prompt,Scanner scanner) throws IOException {
        if (prompt.contains("weather")) {
            try{
                System.out.println("what city");
                String city = scanner.nextLine();
                System.out.println(readFromLocation(city, "temperature"));
            } catch (Exception e) {
                System.out.println("that isnt a real city moron");
            }
        } else if (prompt.length()>8 && prompt.substring(0,7).equals("random(") && prompt.contains(",") && prompt.charAt(prompt.length() - 1) == ')'){
            try {
                random(prompt);
            } catch (Exception e) {
                System.out.println("you stupid idiot input random right moron");
            }
        } else if (prompt.equals("time")) {
            
        }
        
    }
    public static String readFromLocation(String address, String property) throws IOException {
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

    public static void random(String prompt){
        int comma = prompt.indexOf(",");
        int fin = prompt.indexOf(")");
        int start = Integer.parseInt(prompt.substring(7,comma));
        int end = Integer.parseInt(prompt.substring(comma+1,fin));
        System.out.println((int) (start + Math.random() * (end - start + 1)));
    }

    public static String cleanup(String prompt){
        return prompt.toLowerCase().trim();
    }

    public static void time(String prompt) {
        
    }
}
