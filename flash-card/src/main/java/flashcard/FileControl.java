package flashcard;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileControl {
    int repetitions = 1;
    boolean invertCards = false;
    public List<Card> readCardsFromFile(String filename) {
        List<Card> cards = new ArrayList<>();
        
        try {
            File file = new File(filename);

            if (!file.exists()) {
                boolean created = file.createNewFile();
                if (created) {
                    System.out.println("File not found. A new file was created: " + filename);
                }
                return cards; // empty list
            }

            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                boolean set = true;
                while ((line = reader.readLine()) != null) {
                   if(set){
                     setter(line);
                     set = false;
                     continue;
                   }
                   if (line.trim().isEmpty()) continue;
                   Card card = new Card(line);
                   cards.add(card);
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading or creating the file: " + e.getMessage());
            e.printStackTrace();
        }

        return cards;
    }

    public void setter(String line){
        String str[] = line.split("/");
        try {
            repetitions = Integer.parseInt(str[0]);
            invertCards = Boolean.parseBoolean(str[1]);
            } catch (NumberFormatException e) {
            System.err.println("Invalid settings line, using defaults.");
            repetitions = 1;
            invertCards = false;
            }
    }
    public void writeCardsToFile(String filename, List<Card> cards) {
        try {
            File file = new File(filename);

            if (!file.exists()) {
                file.createNewFile();
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
         
                 writer.write(repetitions+"/"+invertCards);
                 writer.newLine();
                for (Card card : cards) {
                    writer.write(card.toString()); 
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
