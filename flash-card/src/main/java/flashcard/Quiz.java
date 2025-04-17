package flashcard;

import java.util.List;
import java.util.Scanner;

public class Quiz {
    private int repetitions;
    private boolean invertCards;
    private List<Card> cards;

    public Quiz(List<Card> cards, int repetitions, boolean invertCards) {
        this.cards = cards;
        this.repetitions = repetitions;
        this.invertCards = invertCards;
    }

    public void start() {
        Scanner sc = new Scanner(System.in);
        for (int round = 0; round < repetitions; round++) {
            System.out.println("Round " + (round + 1) + " of " + repetitions);
            boolean earlyExit = startQuiz(sc);
            AchievementTracker.checkAchievements(cards);

            if (earlyExit) {
                System.out.println(" Exiting quiz early. Goodbye!");
                break;
            }
        }
        sc.close();
    }

    private boolean startQuiz(Scanner sc) {
        for (int i = 0; i < cards.size(); i++) {
            Card card = cards.get(i);

            String prompt = invertCards ? card.getAnswer() : card.getQuestion();
            String expected = invertCards ? card.getQuestion() : card.getAnswer();

            System.out.println("Question " + (i + 1) + " of " + cards.size());
            System.out.println("promt : " + prompt);
            System.out.print("Your Answer (or type 'done' to quit): ");

            long startTime = System.nanoTime();
            String input = sc.nextLine().trim();

            if (input.equalsIgnoreCase("done")) {
                return true; 
            }

            long elapsedTime = (System.nanoTime() - startTime) / 1_000_000;
            card.time = elapsedTime;

            if (input.equals(expected)) {
                card.correctAnswer();
                System.out.println("Correct!");
            } else {
                card.incorrectAnswer();
                System.out.println("Incorrect.");
                System.out.println("Correct Answer: " + expected);
            }

            AchievementTracker.checkAchievementsforCard(card);
        }
        return false; 
    }
}
