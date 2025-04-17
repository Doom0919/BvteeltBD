package flashcard;

import org.junit.jupiter.api.Test;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;

class FlashcardAppTest {

    // Sample test data
    List<Card> getSampleCards() {
        return new ArrayList<>(Arrays.asList(
            new Card("qcard1/card1/3/2/5/true"),
            new Card("qcard2/card2/1/3/4/true"),
            new Card("qcard3/card3/3/4/7/false"),  // most incorrect
            new Card("qcard4/card4/2/2/4/true"),
            new Card("qcard5/card5/3/1/4/false"),
            new Card("qcard6/card6/2/3/5/true"),
            new Card("qcard7/card7/6/0/6/false"),
            new Card("qcard8/card8/0/2/2/true")   // least attempts
        ));
    }

    @Test
    void testRecentMistakesFirst() {
        
        List<Card> temp = getSampleCards();
        List<Card> cards =  OrderControl.orderCommand("<recent-mistakes-first>",getSampleCards());

       assertEquals(temp.get(6).getQuestion() , cards.get(0).getQuestion());
       assertEquals(temp.get(4).getQuestion()  , cards.get(1).getQuestion() );
       assertEquals(temp.get(2).getQuestion()  , cards.get(2).getQuestion() );
       assertEquals(temp.get(0).getQuestion()  , cards.get(3).getQuestion() );
       assertEquals(temp.get(1).getQuestion()  , cards.get(4).getQuestion() );
       assertEquals(temp.get(3).getQuestion()  , cards.get(5).getQuestion() );
    }

    @Test
    void testWorstFirst() {
        List<Card> cards = getSampleCards();
        cards = OrderControl.orderCommand("<worst-first>", cards);

        for (int i = 1; i < cards.size(); i++) {
            int prev = cards.get(i - 1).getIncorrectCount();
            int curr = cards.get(i).getIncorrectCount();
            assertTrue(prev >= curr, "Worst cards should come first by incorrect count.");
        }
    }

    @Test
    void testRandomOrder() {
        List<Card> original = getSampleCards();
        List<Card> shuffled = getSampleCards();
        OrderControl.orderCommand("<random>", shuffled);

       
        boolean different = false;
        for (int i = 0; i < original.size(); i++) {
            if (!original.get(i).equals(shuffled.get(i))) {
                different = true;
                break;
            }
        }

        assertTrue(different, "Cards should be randomized and differ from the original order.");
    }
}
