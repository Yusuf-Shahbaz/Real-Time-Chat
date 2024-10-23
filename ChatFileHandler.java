import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ArrayList;
 
public class ChatFileHandler {
    private File logFile;
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // For formatting timestamps
    
    // Constructor that initializes the log file based on the given file name
    public ChatFileHandler(String fileName) {
        logFile = new File(fileName);
 
        // Check if file exists; if not, create a new log file
        if (!logFile.exists()) {
            try {
                logFile.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating log file: " + e.getMessage());
            }
        }
    }
 
    // Method to save a single message to the log file
    public void saveMessage(Message message) {
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile, true))) {
            out.println(message.toString()); // Log message in formatted string
        } catch (IOException e) {
            System.out.println("Error saving message: " + e.getMessage());
        }
    }
 
    // Method to log server start information with a timestamp
    public void saveServerStart() {
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile, true))) {
            out.println("Server started at: " + LocalDateTime.now().format(formatter));
        } catch (IOException e) {
            System.out.println("Error saving server start: " + e.getMessage());
        }
    }
 
    // Method to log server shutdown with a timestamp
    public void saveServerShutdown() {
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile, true))) {
            out.println("Server shut down at: " + LocalDateTime.now().format(formatter));
        } catch (IOException e) {
            System.out.println("Error saving server shutdown: " + e.getMessage());
        }
    }
 
    // Method to read all messages from the log file
    public List<String> readLog() {
        List<String> messages = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                messages.add(line); // Add each line to the message list
            }
        } catch (IOException e) {
            System.out.println("Error reading log file: " + e.getMessage());
        }
        return messages;
    }
 
    // Method to clear the log file (e.g., for archival or reset purposes)
    public void clearLog() {
        try (PrintWriter out = new PrintWriter(new FileWriter(logFile))) {
            out.print(""); // Overwrite file with an empty string
        } catch (IOException e) {
            System.out.println("Error clearing log file: " + e.getMessage());
        }
    }
 
    // Method to archive the current log file (optional feature for robustness)
    public void archiveLog() {
        String archiveFileName = "archive_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".txt";
        File archiveFile = new File(archiveFileName);
 
        try (BufferedReader reader = new BufferedReader(new FileReader(logFile));
             PrintWriter out = new PrintWriter(new FileWriter(archiveFile))) {
 
            String line;
            while ((line = reader.readLine()) != null) {
                out.println(line); // Copy each line from the current log file to the archive
            }
 
            // Optionally, clear the log file after archiving
            clearLog();
 
            System.out.println("Log file successfully archived as " + archiveFileName);
        } catch (IOException e) {
            System.out.println("Error archiving log file: " + e.getMessage());
        }
    }
}
