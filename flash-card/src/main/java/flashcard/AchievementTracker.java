package flashcard;

import java.util.List;
public class AchievementTracker {
    public static void checkAchievements(List<Card> cards) {
        boolean allCorrect = cards.stream().allMatch(c -> c.getRecentMistake() == 0);
        boolean repeatedCard = cards.stream().anyMatch(c -> c.encounter  > 5);
        boolean confident = cards.stream().anyMatch(c -> c.correct >= 3);
        if (allCorrect) System.out.println("Achievement Unlocked: CORRECT!");
        if (repeatedCard) System.out.println("Achievement Unlocked: REPEAT!");
        if (confident) System.out.println("Achievement Unlocked: CONFIDENT!");
    }
}

