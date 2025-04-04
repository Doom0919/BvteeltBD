package flashcard;

import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards) {
        for (int i = 0; i < cards.size() - 1; i++) {
            for (int j = 0; j < cards.size() - i - 1; j++) {
                if (cards.get(j).getRecentMistake() > cards.get(j + 1).getRecentMistake()) {
                    Card temp = cards.get(j);
                    cards.set(j, cards.get(j + 1));
                    cards.set(j + 1, temp);
                }
            }
        }
        return cards;
    }
}

