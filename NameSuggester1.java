import java.io.*;
import java.util.*;

public class NameSuggester1 {
    private static final String FILE_PATH = "C:\\Users\\ayush\\OneDrive\\Desktop\\neccesary\\gitProjects\\names.txt";
    private static Set<String> existingNames = new HashSet<>();

    static {
        // Load existing names from the file
        loadNamesFromFile();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean continueProgram = true;

        while (continueProgram) {
            System.out.print("Enter a name: ");
            String enteredName = scanner.nextLine().trim();

            if (enteredName.isEmpty()) {
                System.out.println("Name cannot be empty!");
                continue;
            }

            if (existingNames.stream().anyMatch(name -> name.equalsIgnoreCase(enteredName))) {
                System.out.println("Name already exists! Here are some suggestions:");
                List<String> shuffledNames = shuffleName(enteredName);
                if (shuffledNames.isEmpty()) {
                    System.out.println("No suggestions available for this name.");
                } else {
                    for (String suggestion : shuffledNames) {
                        System.out.println(suggestion);
                    }
                }
            } else {
                existingNames.add(enteredName);
                System.out.println("Name is available and added to the list!");
                saveNamesToFile(); // Save the updated list to the file
            }

            System.out.print("Do you want to continue? (yes/no): ");
            String choice = scanner.nextLine().trim();
            if (!choice.equalsIgnoreCase("yes")) {
                if (!choice.equalsIgnoreCase("no")) {
                    System.out.println("Invalid choice. Please enter 'yes' or 'no'.");
                } else {
                    continueProgram = false;
                }
            }
        }
        System.out.println("Program ended. Thank you!");
    }

    private static List<String> shuffleName(String name) {
        List<String> shuffledNames = new ArrayList<>();
        char[] chars = name.toCharArray();
        Set<String> uniqueShuffles = new HashSet<>();

        for (int i = 0; i < 5; i++) { // Generate shuffled name suggestions
            List<Character> charList = new ArrayList<>();
            for (char c : chars) {
                charList.add(c);
            }
            Collections.shuffle(charList);
            StringBuilder sb = new StringBuilder();
            for (char c : charList) {
                sb.append(c);
            }
            if (uniqueShuffles.add(sb.toString())) {
                shuffledNames.add(sb.toString());
            }
        }
        return shuffledNames;
    }

    private static void loadNamesFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return; // If file doesn't exist, do nothing
        }
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                existingNames.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Error loading names from file: " + e.getMessage());
        }
    }

    private static void saveNamesToFile() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String name : existingNames) {
                bw.write(name);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("Error saving names to file: " + e.getMessage());
        }
    }
}
