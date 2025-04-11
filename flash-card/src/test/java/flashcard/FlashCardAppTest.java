package flashcard;

import org.junit.jupiter.api.*;
import java.io.*;
import java.util.Scanner;
import static org.junit.jupiter.api.Assertions.*;

class FlashcardAppTest {

    private FlashcardApp app;
    private final String testFile = "test_flashcards.txt";

    @BeforeEach
    void setUp() {
        app = new FlashcardApp();
        app.setTestMode(true); // Prevent System.exit during tests

        // When a method calls input (for errors), we supply "3" to pick "End"
        String simulatedInput = "3\n";
        app.setScanner(new Scanner(new ByteArrayInputStream(simulatedInput.getBytes())));
    }

    @AfterEach
    void tearDown() {
        // Cleanup the test file if it was created.
        File f = new File(testFile);
        if (f.exists()) {
            f.delete();
        }
    }

    @Test
    void testLoadAndSaveCards() throws IOException {
        Folder folder = new Folder();
        folder.setName("TestFolder");
        folder.addCard("Answer1", "Question1");
        app.getFolders().add(folder);
        app.loadCards(testFile);
        app.end();
        FlashcardApp reloadedApp = new FlashcardApp();
        reloadedApp.setTestMode(true);
        reloadedApp.loadCards(testFile);
        assertFalse(reloadedApp.getFolders().isEmpty());
        assertEquals("TestFolder", reloadedApp.getFolders().get(0).getName());
    }

    @Test
    void testCommandParsing_orderRandom() {
        Folder folder = new Folder();
        folder.setName("TestFolder");
        folder.addCard("A", "Q1");
        folder.addCard("B", "Q2");
        folder.addCard("C", "Q3");

        app.setChoice("--order <random>");
        Folder result = app.commandInterface(folder);
        assertEquals(3, result.getCards().size());
    }
    @Test
    void worstMistakesFirst() {
        Folder folder = new Folder();
        folder.setName("TestFolder");
        folder.addCard("A", "Q1" , 1 , 3);
        folder.addCard("B", "Q2", 1 , 3);
        folder.addCard("C", "Q3", 3 , 3);
        app.setChoice("--order <worst-first>");
        Folder result = app.commandInterface(folder);
        assertEquals("C", result.getCards().get(0).getAnswer());
    }

    @Test
    void recentMistakesFirstSorterTest(){
        Folder folder = new Folder();
        folder.setName("TestFolder");
        folder.addCard("A", "Q1" , 1 , 3);
        folder.addCard("B", "Q2", 1 , 3);
        folder.addCard("C", "Q3", 3 , 3);
        folder.getCards().get(0).setRecentMistake(1);
        folder.getCards().get(1).setRecentMistake(5);
        app.setChoice("--order <recent-mistakes-first>");
        Folder result = app.commandInterface(folder);
        assertEquals("B", result.getCards().get(0).getAnswer());
    }
}