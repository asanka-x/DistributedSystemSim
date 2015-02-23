package assignments.assigment1;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by asankanissanka on 2/22/15.
 */
public class RiddleResponseGenerator {
    private JSONObject riddlesJson;
    private Random random;

    public RiddleResponseGenerator() {
        JSONParser jsonParser = new JSONParser();
        random = new Random();
        try {
            Object object = jsonParser.parse(new FileReader("riddles.json"));
            riddlesJson = (JSONObject) object;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public String generateRiddleResponse(String command) {
        String reply;
        if (command.equals("bye")) {
            reply = command;
        } else {
            if (command.equals("hello")) {
            reply = Integer.toString(riddlesJson.size());
            } else {
                if (command.equals("riddle")) {
                    int randomInt = random.nextInt(9 - 1) + 1;
                    JSONObject riddleAnswer = (JSONObject) riddlesJson.get(Integer.toString(randomInt));
                    reply = riddleAnswer.get("riddle").toString();
                } else {
                    if (command.matches("^(riddle [1-9])$")) {
                        JSONObject riddleAnswer = (JSONObject) riddlesJson.get(command.split(" ")[1]);
                        reply = riddleAnswer.get("riddle").toString();
                    } else {
                        if (command.matches("^(riddle [1-9]) (.*)$")) {
                            String[] splittedCommand = command.split(" ");
                            JSONObject riddleAnswer = (JSONObject) riddlesJson.get(splittedCommand[1]);
                            reply = riddleAnswer.get("answer").toString().equals(splittedCommand[2]) ? "correct" : "incorrect";
                        } else {
                            reply = "unknown message";
                        }
                    }
                }
            }
        }
        return reply;
    }
}
