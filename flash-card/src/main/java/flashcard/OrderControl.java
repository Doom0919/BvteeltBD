package flashcard;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class OrderControl {
    OrderControl(){

    }
    public static List<Card> orderCommand(String choice , List<Card> cards){
        String comm = "";
        int start = choice.indexOf('<');
        int end = choice.indexOf('>');
    
        if (start != -1 && end != -1 && end > start) {
            comm = choice.substring(start + 1, end).trim();
         switch (comm) {
            case "random":
                cards = random(cards);
                break;
            case "worst-first":
                cards = worstFirst(cards);
                break;
            case "recent-mistakes-first":
                cards = recentMistakes(cards);
                break;
            default:
              System.out.println("incorrect is order");
                break;
         }
    }
    return cards;
}

    public static List<Card> random(List<Card> cards){
   Random random = new Random();
        if(cards == null){

        }
        else{
        for (int i = cards.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = cards.get(i);
            cards.set(i, cards.get(j));
            cards.set(j, temp);
        }
      }
      return cards;
    }
    public static List<Card> worstFirst(List<Card> cards){
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = 0; j < cards.size() - i - 1; j++) {
                if (cards.get(j).getIncorrectCount() < cards.get(j + 1).getIncorrectCount()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
        return cards;
    }
    public static List<Card> recentMistakes(List<Card> cards){
       List<Card> tempCards = new ArrayList<>();
       for(int i = cards.size()-1 ; i >= 0 ;i--){
        if(!cards.get(i).getPrevious())
        tempCards.add(cards.get(i));
       }
       for(int i =0 ; i < cards.size() ;i++){
        if(cards.get(i).getPrevious())
        tempCards.add(cards.get(i));
       }
       for (int i = 0; i < cards.size(); i++) {
        cards.set(i, tempCards.get(i));
       }
       return tempCards;
    }
}
