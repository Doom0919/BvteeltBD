package flashcard;

import java.io.IOException;

/**
 * Hello world!
 *
 */
public class App 
{
    
    public static void main(String[] args) throws IOException {
        FlashcardApp app = new FlashcardApp();
        app.loadCards("Day1.txt");
        app.start();
    }
}