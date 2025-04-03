package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlashcardApp {
    private List<Card> cards = new ArrayList<>();
    private boolean invertCards = false;
    private int repetitions = 1;

    public void loadCards(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(";");
                if (parts.length == 2) {
                    cards.add(new Card(parts[0], parts[1]));
                }
            }
        }
    }
    public void randomCards(){
     List<Card> cloneCards = new ArrayList<>();
     
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < repetitions; i++) {
            for (Card card : cards) {
                System.out.println(invertCards ? "Answer: " + card.getAnswer() : "Question: " + card.getQuestion());
                String userAnswer = scanner.nextLine();
                if (userAnswer.equalsIgnoreCase(card.getAnswer())) {
                    card.markCorrect();
                    System.out.println("Correct!");
                } else {
                    card.markIncorrect();
                    System.out.println("Incorrect! Correct answer: " + card.getAnswer());
                }
            }
        }
    }
}
