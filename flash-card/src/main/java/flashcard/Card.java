package flashcard;

public class Card {
    private String question;
    private String answer;
    private int mistakeCount = 0;
    private int correctCount = 0;
    private int recentMistake = 0;
    public int correct = 0;
    public int encounter = 0;

    public String toString(){
        return question+"/"+answer+"/"+correctCount+"/"+mistakeCount;
    }
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }
    public int getRecentMistake() {
        return recentMistake;
    }
    public void setRecentMistake(int recentMistake) {
        this.recentMistake = recentMistake;
    }

    public Card(String str){
        String[] temp = str.split("/");
        this.question = temp[0];
        this.answer = temp[1];
        try {
        this.correctCount = Integer.parseInt(temp[2]);
        this.mistakeCount = Integer.parseInt(temp[3]);
        } catch (NumberFormatException e) {
        e.printStackTrace();
        }
    }
   
    public void markCorrect() { correctCount++; correct++; }
    public void markIncorrect() { mistakeCount++ ; recentMistake++; }
    
    public int getMistakeCount() { return mistakeCount; }
    public int getCorrectCount() { return correctCount; }
    
    public String getQuestion() {encounter++; return question; }
    public String getAnswer() { return answer; }
}
