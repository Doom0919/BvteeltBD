package flashcard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Hello world!
 *
 */
public class App 
{
    
    public static void main(String[] args) throws IOException {
        FlashcardApp app = new FlashcardApp();
        if (args.length == 0 || args[0].equals("--help")) {
            System.out.println("Usage: flashcard <cards-file> [options]");
            return;
        }
        app.loadCards(args[0]);
        app.start();
    }
}
