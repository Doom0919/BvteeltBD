package flashcard;

public class Card {
    private String question;
    private String answer;
    private int mistakeCount = 0;
    private int correctCount = 0;

    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public void markCorrect() { correctCount++; }
    public void markIncorrect() { mistakeCount++; }
    
    public int getMistakeCount() { return mistakeCount; }
    public int getCorrectCount() { return correctCount; }
    
    public String getQuestion() { return question; }
    public String getAnswer() { return answer; }
}
