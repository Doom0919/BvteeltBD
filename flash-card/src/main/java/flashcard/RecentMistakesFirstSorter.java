package flashcard;

import java.util.List;

public class RecentMistakesFirstSorter implements CardOrganizer {
    @Override
    public List<Card> organize(List<Card> cards) {
        cards.sort((a, b) -> Integer.compare(b.getMistakeCount(), a.getMistakeCount()));
        return cards;
    }
}

