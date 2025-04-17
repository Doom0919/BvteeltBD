package flashcard;

import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AchievementTrackerTest {

    private List<Card> cards;

    @BeforeEach
    void setUp() {
        cards = new ArrayList<>();
    }

    @Test
    void testAllCorrectAchievement() {
        cards.add(new Card("Q1", "A1"));
        cards.add(new Card("Q2", "A2"));
        cards.forEach(Card::correctAnswer); // all marked correct

        // Capture printed output
        Assertions.assertDoesNotThrow(() -> AchievementTracker.checkAchievements(cards));
    }

    @Test
    void testFastThinkerAchievement() {
        Card c1 = new Card("Q1", "A1");
        Card c2 = new Card("Q2", "A2");
        c1.time = 2000;
        c2.time = 3000;
        cards.add(c1);
        cards.add(c2);

        Assertions.assertDoesNotThrow(() -> AchievementTracker.checkAchievements(cards));
    }

    @Test
    void testRepeatedLearnerAchievement() {
        Card c = new Card("Q", "A");
        for (int i = 0; i < 6; i++) c.incorrectAnswer();
        AchievementTracker.checkAchievementsforCard(c);

        assertTrue(c.getTotalAttempts() > 5);
    }

    @Test
    void testConfidentMasterAchievement() {
        Card c = new Card("Q", "A");
        for (int i = 0; i < 4; i++) c.correctAnswer();
        AchievementTracker.checkAchievementsforCard(c);

        assertTrue(c.getCorrectCount() > 3);
    }

    @Test
    void testNoAchievementsUnlocked() {
        Card c = new Card("Q", "A");
        c.time = 10000;
        c.incorrectAnswer();
        cards.add(c);

        Assertions.assertDoesNotThrow(() -> AchievementTracker.checkAchievements(cards));
        AchievementTracker.checkAchievementsforCard(c);
    }
}
