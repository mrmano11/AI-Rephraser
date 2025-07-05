package airephraser;

import java.io.OutputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("🔤 Enter a sentence to rephrase:\n");
        String input = scanner.nextLine();

        try {
            String response = rephraseWithCohere(input);
            displayResult(response);
        } catch (Exception e) {
            System.out.println("❌ Error: " + e.getMessage());
        }

        scanner.close();
    }

    public static String rephraseWithCohere(String input) throws Exception {
        String apiKey = "hO67FqdgqU47zGcYDY4U21ToUK7HJWoZIOtdADLV";
        String endpoint = "https://api.cohere.ai/v1/generate";

        String prompt = "Rephrase this sentence to be grammatically correct and plagiarism-free.\n"
                      + "Also, estimate the plagiarism level (as a percentage based on originality).\n\n"
                      + "Respond in this exact format:\n"
                      + "Rephrased: <your version>\n"
                      + "Plagiarism Estimate: <number>%\n\n"
                      + "Sentence: \"" + input + "\"";

        String jsonInput = "{"
                + "\"model\": \"command\","
                + "\"prompt\": \"" + prompt.replace("\"", "\\\"").replace("\n", "\\n") + "\","
                + "\"max_tokens\": 120,"
                + "\"temperature\": 0.7,"
                + "\"k\": 0,"
                + "\"stop_sequences\": [],"
                + "\"return_likelihoods\": \"NONE\""
                + "}";

        URL url = new URL(endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Authorization", "Bearer " + apiKey);
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        OutputStream os = connection.getOutputStream();
        os.write(jsonInput.getBytes("UTF-8"));
        os.flush();
        os.close();

        int responseCode = connection.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Server returned HTTP response code: " + responseCode);
        }

        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
        StringBuilder fullResponse = new StringBuilder();
        String inputLine;
        while ((inputLine = in.readLine()) != null) {
            fullResponse.append(inputLine);
        }
        in.close();

        // Extract the "text" value from JSON
        String json = fullResponse.toString();
        int start = json.indexOf("\"text\":\"") + 8;
        int end = json.indexOf("\"", start);
        if (start == -1 || end == -1 || start >= end) {
            throw new Exception("Failed to parse AI response.");
        }

        return json.substring(start, end).replace("\\n", "\n").replace("\\\"", "\"").trim();
    }

    public static void displayResult(String aiResponse) {
        String[] lines = aiResponse.split("\n");
        String rewritten = "";
        String plagiarism = "";

        for (String line : lines) {
            if (line.startsWith("Rephrased:")) {
                rewritten = line.substring("Rephrased:".length()).trim();
            } else if (line.startsWith("Plagiarism Estimate:")) {
                plagiarism = line.substring("Plagiarism Estimate:".length()).trim();
            }
        }

        System.out.println("\n✅ Rephrased Output:");
        System.out.println(rewritten);
        System.out.println("\n📉 Estimated Plagiarism Level: " + plagiarism);
    }
}
