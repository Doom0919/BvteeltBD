package flashcard;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FlashcardApp {
    private List<Folder> folders = new ArrayList<>();
    private String choice;
    private String choice2;
    private Scanner sc = new Scanner(System.in);  
    private long totalTime = 0;
    File file ;
    public void loadCards(String filename) throws IOException {
        file = new File(filename);
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    Folder folder = new Folder(line);
                    folders.add(folder);
                }
            }
        } else {
            file.createNewFile();
            loadCards(filename);  
        }
    }
    public void end() throws FileNotFoundException, IOException{
        if (file.exists()) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                 for (Folder folder : folders) {
                    writer.write(folder.toString());
                    writer.newLine();
                 }
            }
        } else {
            file.createNewFile();
        }
        System.exit(0);  
    }
    public void getFoldersName() {
        int a = 1;
        for (Folder folder : folders) {
            System.out.println(a + " --" + folder.getName());
            a++;
        }
    }

    public boolean isCommand() {
        return choice.startsWith("--");
    }

    public void game(Folder folder) {
        long startTime = System.nanoTime();
          while (folder.checkRepetitions()){
            design("IN GAME");
            folder.getTest();
            input();
            System.out.println(folder.returnAnswer(choice));      
          }
          totalTime = System.nanoTime() - startTime;
          System.out.println("Time: " + totalTime /1000000 + "    , Correct answers: " + folder.getCountCorrect());
          AchievementTracker.checkAchievements(folder.getCards());
          folder.begin();
          forFolder(folder);
    }
     

    public void input() {
        System.out.print("Enter your input here: ");
        choice = sc.nextLine();
    }

    public void forFolder(Folder folder){
        design(folder.getName());
        System.out.println("Flashcard ------------------------------ Subject name " + folder.getName());
        System.out.println("start");
        System.out.println("back");
        System.out.println("end");
        System.out.println("add - Card");
        input();

        switch (choice.toLowerCase()) {
            case "start":
                game(folder);
                break;
            case "back":
                start();  
                break;
            case "end":
                try {
                    end();
                } catch (IOException e) {
                  
                    e.printStackTrace();
                }

                break;

            case "add":
            Folder fr = addCard(folder);
           forFolder(fr);
            break;
            default:
                handleCommandOrError(folder);
              
                break;
        }
    }
    
    public Folder addCard(Folder folder){
        System.out.println("Question : ");
        input();
        String question = choice;
        System.out.println("Answer : ");
        input();
        String answer = choice;
        folder.addCard(answer, question);
        return folder;
    }
    public void design(String Name){
        System.out.println("\n\n\n\n\n\n"+" ------------"+Name+"-------------");
    }
    private void handleCommandOrError(Folder folder) {
        if (isCommand()) {
            folder = commandInterface(folder);
            forFolder(folder);
        } else {
            callError("Error");
        }
    }
    public Folder commandInterface(Folder folder) {
        if (choice.startsWith("--help")) {
            showHelp();
            return folder;
        }
        int start = choice.indexOf('<') + 1;
        int end = choice.indexOf('>');
        String comm = "";
        if (start != -1 && end != -1) {
            comm = choice.substring(start, end).trim();  
        } else {
            comm = choice.substring(choice.indexOf(' ') + 1).trim(); 
        }
    
      
        if (choice.startsWith("--order")) {
            if (comm.equalsIgnoreCase("random")) {
                folder.randomCards(); 
                System.out.println("Successful");
            } else if (comm.equalsIgnoreCase("worst-first")) {
                folder.worstFirst();  
                System.out.println("Successful");
            } else if (comm.equalsIgnoreCase("recent-mistakes-first")) {
                RecentMistakesFirstSorter  recentMistakesFirstSorter = new RecentMistakesFirstSorter();

                folder.setCards(recentMistakesFirstSorter.organize(folder.getCards()));
                System.out.println("Successful");
            } else {
                callError("Invalid option for --order. Valid options are: random, worst-first, recent-mistakes-first.");
            }
        } 
        else if (choice.startsWith("--repetitions")) {
            try {
                int rep = Integer.parseInt(comm);  
                if (rep < 1) {
                    callError("Repetitions must be a positive number.");
                }
                folder.setRepetitions(rep);
                System.out.println("Successful");
            } catch (NumberFormatException e) {
                callError("Invalid repetitions number. Please enter a valid number.");
            }
        } 
        
        else if (choice.startsWith("--invertCards")) {
            folder.setInvertCards(!folder.isInvertCards()); 
        } 
        else {
            callError("Invalid command. Please use --help for assistance.");
        }
        
        choice = choice2; 
        return folder;
    }
    
    
    
    

    private void showHelp() {
        System.out.println("HELP:");
        System.out.println("--order <random> to shuffle cards randomly.");
        System.out.println("--order <worst-first> to place incorrectly answered cards at the beginning.");
        System.out.println("--order <recent-mistakes-first> to prioritize recent mistakes.");
        System.out.println("--repetitions <num> to set how many times a card will be repeated in a round.");
        System.out.println("--invertCards toggles the question and answer order.");
        folderInterface();
    }



    private void folderInterface() {
        Folder folder = getFolderFromChoice();
        if (folder == null) {
            callError("Invalid folder choice.");
        } else {
            forFolder(folder);
        }
    }

    private Folder getFolderFromChoice() {
        try {
            int index = Integer.parseInt(choice) - 1;
            choice2 = choice;
            return folders.get(index);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void callError(String errorMsg) {
        design("Error");
        System.out.println("Error: " + errorMsg);
        System.out.println("1. Report an error");
        System.out.println("2. Restart");
        System.out.println("3. End");
        input();

        switch (choice) {
            case "1":
                reportError();
                break;
            case "2":
                start();
                break;
            case "3":
                try {
                    end();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                break;
            default:
                callError("Invalid input.");
        }
    }

    private void reportError() {
        input();
        Feedback feedback = new Feedback();
        feedback.setMessage(choice);
        System.out.println("Error reported: " + choice);
        callError("Returning to main menu.");
    }
     public void addFolder(){
       System.out.println("Name folder : ");
       input();
       Folder folder = new Folder();
       folder.setName(choice);
       folders.add(folder);
     }
    public void start() {
        design("SUBJECT NAMES");
        getFoldersName();
        System.out.println("add - add folder");
        input();  

        if (choice.equalsIgnoreCase("end")) {
            System.exit(0);  
        }else if(choice.equalsIgnoreCase("add")){
            addFolder();
            start();
        }
         else {
            folderInterface(); 
        }
    }

}
