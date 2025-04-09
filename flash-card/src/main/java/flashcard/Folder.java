package flashcard;

import java.util.*;

public class Folder {
    private String name;
    private List<Card> cards = new ArrayList<>();
    private Card selectedCard;
    private String[] options;
    private int countCorrect = 0;
    private boolean invertCards = false; 
    private int countQuestions;
    private int repetitions = 1;
    private int index = -1;
    private int tempre = repetitions;
    public Folder() {}
  
    public Folder(String str) {
        String[] parts = str.split(";");
        this.name = parts[0];
        String[] cardStrings = parts[1].split("//");

        for (String cardString : cardStrings) {
            cards.add(new Card(cardString));
        }
    }
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name);
        str.append(";");
        for (Card card : cards) {
            str.append(card.toString());
            str.append("//");
        }
        return str.toString();
    }
    public void randomCards() {
        Random random = new Random();
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
    }
   public void setCards(List<Card> cards) {
       this.cards = cards;
   }
    public void worstFirst(){
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = 0; j < cards.size() - i - 1; j++) {
                if (cards.get(j).getMistakeCount() < cards.get(j + 1).getMistakeCount()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
    }

    public void begin(){
        for (Card card : cards) {
            card.setRecentMistake(0);
        }
    }
    public int getCountCorrect() {
        return countCorrect;
    }
    public void setInvertCards(boolean invert) {
        this.invertCards = invert;
    }

    public boolean isInvertCards() {
        return invertCards;
    }
    
    public String returnAnswer(String answer) {
        char choice = Character.toLowerCase(answer.charAt(0));
        int index = choice - 'a';
        
        if (index >= 0 && index < options.length && options[index].equals(getCorrectAnswer())) {

            countCorrect++;
            selectedCard.markCorrect();
            return " Correct! [" + getCorrectAnswer() + "]";
        }
        selectedCard.markIncorrect();
        return " InCorrect ! Correct is : " + getCorrectAnswer();

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    private void shuffleArray(String[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            String temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }
    public void setRepetitions(int repetitions) {
        this.repetitions = repetitions;
        this.tempre = repetitions;
    }
    
    public boolean checkRepetitions(){
        if(index == cards.size()-1){
            index = -1;
            if(repetitions < 1){
            repetitions = tempre;
           
            return false;
            }
        }
        return true;
    }
    public void getTest() {
        if (cards.isEmpty()) {
            System.out.println("Error: Empty card list!");
            return;
        }
        index++;
        if(index == cards.size()-1){
            repetitions--;
        }
        Random random = new Random();
        selectedCard = cards.get(index);
        Set<Integer> restSet = new HashSet<>();
        while (restSet.size() < 3) {
            int randIndex = random.nextInt(cards.size());
            if (!cards.get(randIndex).equals(selectedCard)) {
                restSet.add(randIndex);
            }
        }
        
        Integer[] rest = restSet.toArray(new Integer[0]);
    
        options = new String[]{
            getCardAnswer(rest[0]),
            getCardAnswer(rest[1]),
            getCardAnswer(rest[2]),
            getCorrectAnswer()
        };
    
        shuffleArray(options);
        countQuestions++;
        System.out.println(countQuestions + ". Question : " + getCardQuestion() + " ?");
        System.out.println("a. " + options[0] + "\nb. " + options[1] + "\nc. " + options[2] + "\nd. " + options[3]);
    }
    
    
    public List<Card> getCards() {
        return cards;
    }
    private String getCardQuestion() {
        return invertCards ? selectedCard.getAnswer() : selectedCard.getQuestion();
    }

    private String getCardAnswer(int index) {
        return invertCards ? cards.get(index).getQuestion() : cards.get(index).getAnswer();
    }

    private String getCorrectAnswer() {
        return invertCards ? selectedCard.getQuestion() : selectedCard.getAnswer();
    }

}
