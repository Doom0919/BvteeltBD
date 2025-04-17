package flashcard;

import java.util.List;

public class AchievementTracker {

    private static final int FAST_TIME_THRESHOLD_MS = 5000;
    private static final int REPEAT_THRESHOLD = 5;
    private static final int CONFIDENT_THRESHOLD = 3;

    public static void checkAchievements(List<Card> cards) {
        boolean allCorrect = cards.stream().allMatch(Card::getPrevious);

        long totalTime = 0;
        for (Card card : cards) {
            totalTime += card.time;
        }

        long avgTime = cards.size() > 0 ? totalTime / cards.size() : Long.MAX_VALUE;
        boolean fast = avgTime < FAST_TIME_THRESHOLD_MS;

        if (fast) unlock("FAST THINKER");
        if (allCorrect) unlock("ALL CORRECT");
    }

    public static void checkAchievementsforCard(Card card) {
        if (card.getTotalAttempts() > REPEAT_THRESHOLD) unlock("REPEATED LEARNER");
        if (card.getCorrectCount() > CONFIDENT_THRESHOLD) unlock("CONFIDENT MASTER");
    }

    private static void unlock(String name) {
        System.out.println(" Achievement Unlocked: " + name + "!");
    }
}
