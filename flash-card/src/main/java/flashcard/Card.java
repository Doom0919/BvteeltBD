package flashcard;

public class Card {
    private final String question;
    private final String answer;
    private int correctCount = 0;
    private int incorrectCount = 0;
    private int totalAttempts = 0;
    private boolean previous = true;
    public long time;
    
    public Card(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public Card(String str){
        String[] temp = str.split("/");
        this.question = temp[0];
        this.answer = temp[1];
        try {
        this.correctCount = Integer.parseInt(temp[2]);
        this.incorrectCount = Integer.parseInt(temp[3]);
        this.totalAttempts = Integer.parseInt(temp[4]);
        this.previous = Boolean.parseBoolean(temp[5]);
        } catch (NumberFormatException e) {
        e.printStackTrace();
        }
    }
    public String toString(){
        return question+"/"+answer+"/"+correctCount+"/"+incorrectCount+"/"+totalAttempts+"/"+previous;
    }
    public String getQuestion() { 
      
        return question;
     }
    public String getAnswer() { return answer; }

    public boolean getPrevious(){
        return previous;
    }
    
    public void setPrevious(boolean previous){
        this.previous = previous;
    }
    public int getCorrectCount() { return correctCount; }
    public int getIncorrectCount() { return incorrectCount; }
    public int getTotalAttempts() { return totalAttempts; }
    public void incorrectAnswer(){
        totalAttempts++;
        incorrectCount++;
        previous = false;
    }    
    public void correctAnswer(){
        totalAttempts++;
        previous = true;
        correctCount++;
    }
}
