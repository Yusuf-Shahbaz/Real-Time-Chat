import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
 
public class ChatException extends Exception {
    private LocalDateTime timestamp; // To track when the exception occurred
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Timestamp format
 
    // Constructor to initialize exception with a message
    public ChatException(String message) {
        super(message);
        this.timestamp = LocalDateTime.now(); // Set the timestamp when the exception is thrown
        logException(); // Log the exception when created
    }
 
    // Constructor to initialize exception with a message and a cause
    public ChatException(String message, Throwable cause) {
        super(message, cause);
        this.timestamp = LocalDateTime.now(); // Set the timestamp when the exception is thrown
        logException(); // Log the exception when created
    }
 
    // Method to retrieve the timestamp of when the exception occurred
    public String getFormattedTimestamp() {
        return timestamp.format(formatter); // Return the timestamp in a readable format
    }
 
    // Override toString() method to include the timestamp in the exception message
    @Override
    public String toString() {
        return getFormattedTimestamp() + " - Exception: " + super.toString();
    }
 
    // Method to log the exception details (this could be extended to log to a file or database)
    private void logException() {
        System.err.println(toString()); // Log the exception to the console (could be replaced by file or DB logging)
    }
 
    // Static utility method to wrap and throw custom ChatException from other exceptions
    public static void handleException(String message, Throwable cause) throws ChatException {
        throw new ChatException(message, cause); // Throw a new ChatException with the given cause
    }
}
