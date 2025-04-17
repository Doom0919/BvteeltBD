package flashcard;


import java.util.List;
import java.util.Scanner;
      // mvn exec:java "-Dexec.args=Day1.txt --invertCards"
      // mvn exec:java "-Dexec.args=Day1.txt --order <random>"
      // mvn exec:java "-Dexec.args=Day1.txt --order <recent-mistakes-first>"
      // mvn exec:java "-Dexec.args=Day1.txt --order <order>"
      // mvn exec:java "-Dexec.args=Day1.txt --repetitions <num>"
public class FlashcardApp {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        List<Card> cards;

        FileControl fileControl = new FileControl();
        cards = fileControl.readCardsFromFile(args[0]);
        int repetitions = fileControl.repetitions;
        boolean invertCards = fileControl.invertCards;

        for (int i = 1; i < args.length; i++) {
            String arg = args[i];

            switch (arg) {
                case "--help":
                    printHelp();
                    break;

                case "--add":
                    addCardsInteractively(cards, sc);
                    break;

                case "--order":
                    if (i + 1 < args.length) {
                        String orderType = args[++i];
                        OrderControl.orderCommand(orderType, cards);
                    } else {
                        System.out.println("Missing argument after --order");
                    }
                    break;

                case "--play":
                    Quiz  quiz = new Quiz(cards, repetitions, invertCards);
                    quiz.start();
                    
                    break;

                case "--repetitions":
                String comm = "";
                int start = args[i+1].indexOf('<');
                int end = args[i+1].indexOf('>');
                if (start != -1 && end != -1 && end > start) {
                  comm = args[i+1].substring(start + 1, end).trim();
                  try {
                     repetitions = Integer.parseInt(comm);
                     } catch (NumberFormatException e) {
                     e.printStackTrace();
                     }
                }
                    break;

                case "--invertCards":
                    invertCards = !invertCards;
                    break;

                default:
                    System.out.println("Unknown argument: " + arg);
                    break;
            }
        }

        fileControl.repetitions = repetitions;
        fileControl.invertCards = invertCards;
        fileControl.writeCardsToFile(args[0], cards);
    }

    private static void printHelp() {
        System.out.println("HELP:");
        System.out.println("--order <random> to shuffle cards randomly.");
        System.out.println("--order <worst-first> to place incorrectly answered cards at the beginning.");
        System.out.println("--order <recent-mistakes-first> to prioritize recent mistakes.");
        System.out.println("--repetitions <num> to set how many times a card will be repeated in a round.");
        System.out.println("--invertCards toggles the question and answer order.");
        System.out.println("--add to interactively add cards.");
        System.out.println("--play to start playing flashcards.");
    }

    private static void addCardsInteractively(List<Card> cards, Scanner sc) {
        System.out.println("Enter flashcards. Type 'done' at any time to finish.");
        while (true) {
            System.out.print("Question (or type 'done'): ");
            String question = sc.nextLine().trim();
            if (question.equalsIgnoreCase("done")) break;
            if (question.isEmpty()) {
                System.out.println("Question can't be empty. Try again.");
                continue;
            }

            System.out.print("Answer (or type 'done'): ");
            String answer = sc.nextLine().trim();
            if (answer.equalsIgnoreCase("done")) break;
            if (answer.isEmpty()) {
                System.out.println("Answer can't be empty. Try again.");
                continue;
            }
            
            cards.add(new Card(question, answer));
            System.out.println("Card added!\n");
        }
    }
}
