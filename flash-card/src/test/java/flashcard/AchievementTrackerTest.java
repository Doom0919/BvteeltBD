package flashcard;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.*;
public class AchievementTrackerTest {
     @Test
    void testAchievementsTrigger() {
        Card perfect = new Card("Q1", "A1");
        perfect.markCorrect();

        Card repeat = new Card("Q2", "A2");
        repeat.correct = 3;

        Card encountered = new Card("Q3", "A3");
        encountered.encounter = 6;

        List<Card> cards = List.of(perfect, repeat, encountered);
        AchievementTracker.checkAchievements(cards);
       assertEquals( "Achievement Unlocked: CORRECT!" + 
                      "Achievement Unlocked: REPEAT!" + 
                      "Achievement Unlocked: CONFIDENT!", AchievementTracker.str.toString());

    }
}
