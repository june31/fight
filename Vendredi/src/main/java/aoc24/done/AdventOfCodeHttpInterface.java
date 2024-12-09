package aoc24.done;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCodeHttpInterface {

	private static String AOC_FILE_URL = "https://adventofcode.com/%d/day/%d/input";
	private static String AOC_ANSWER_URL = "https://adventofcode.com/%d/day/%d/answer";
	private static final String FOLDER_PREFIX = "resources/adventOfCode";
	private static final String FILE_PREFIX = "day";
	
	public static boolean DEBUG = false;

	public static void retrieveFile(int year, int day) {
		String filePath = FOLDER_PREFIX + year + "/" + FILE_PREFIX + day + ".txt";
		if (!Files.exists(Path.of(filePath))) {
			try {
				downloadFileFromAOC(filePath, year, day);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static void downloadFileFromAOC(String filePath, int year, int day) throws MalformedURLException, IOException {
		HttpURLConnection connection = getHttpConnection(String.format(AOC_FILE_URL,year,day), "GET", year, day);
		try (InputStream inputStream = connection.getInputStream(); FileOutputStream fileOutputStream = new FileOutputStream(filePath)) {
               byte[] buffer = new byte[4096];
               int bytesRead;
               while ((bytesRead = inputStream.read(buffer)) != -1) {
                   fileOutputStream.write(buffer, 0, bytesRead);
               }
               System.out.println("File Retrieved from AOC to "+filePath);
           }
        connection.disconnect();
	}
	
	private static HttpURLConnection getHttpConnection(String URL, String method, int year, int day) throws MalformedURLException, IOException {
		HttpURLConnection connection = (HttpURLConnection) new URL(URL).openConnection();
		connection.setRequestMethod(method);
		connection.setRequestProperty("Cookie", "session=...");
		connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		connection.setConnectTimeout(5000);
		connection.setReadTimeout(5000);
		
		return connection;
	}
	
	public static void sendAnswer(String level, String answer, int year, int day) throws MalformedURLException, IOException {
		if(DEBUG)
			return;
				
		HttpURLConnection connection = getHttpConnection(String.format(AOC_ANSWER_URL,year,day), "POST", year, day);
		connection.setDoOutput(true);
		
		String postBody = "level="+level+"&answer="+answer;
		try (OutputStream os = connection.getOutputStream()) {
            byte[] input = postBody.getBytes("utf-8");
            os.write(input, 0, input.length);
            os.flush();
        }
		
		String responseString = "";
		try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String line;
            boolean right = false;
            while ((line = reader.readLine()) != null) {
                response.append(line);
                if(line.contains("That's the right answer!")) {
                	right = true;
                }
            }
            if(right) {
            	System.out.println("That's the right answer!");
            	if(level.equals("1")) {
            		System.out.println("Switching to Part 2");
            		switchToPart2(year, day);
            	}
            }
            responseString = response.toString();
        }
        System.out.println("Réponse reçue : " + responseString);
	}
	
	private static void switchToPart2(int year, int day) throws IOException {
		if(DEBUG)
			return;
		
		Path filePath = Paths.get("src/aoc_"+year+"/Day"+day+".java");
		List<String> lines = Files.readAllLines(filePath);
        List<String> modifiedLines = new ArrayList<>();

        for (String line : lines) {
        	modifiedLines.add(line.replace("System.out.println(part1());", "//System.out.println(part1());").replace("//System.out.println(part2());", "System.out.println(part2());"));
        }

        Files.write(filePath, modifiedLines);
	}
}