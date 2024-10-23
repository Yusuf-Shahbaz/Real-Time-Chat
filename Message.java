import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
 
public class Message {
    private String content; // The message text
    private String sender; // The sender of the message
    private LocalDateTime timestamp; // The time the message was sent
    
    // Custom formatter for displaying the timestamp
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
 
    // Constructor to initialize the message with content and sender
    public Message(String content, String sender) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        if (sender == null || sender.isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }
        this.content = content;
        this.sender = sender;
        this.timestamp = LocalDateTime.now(); // Automatically set the current timestamp
    }
 
    // Getter for the message content
    public String getContent() {
        return content;
    }
 
    // Setter for the message content
    public void setContent(String content) {
        if (content == null || content.isEmpty()) {
            throw new IllegalArgumentException("Message content cannot be null or empty");
        }
        this.content = content;
    }
 
    // Getter for the sender
    public String getSender() {
        return sender;
    }
 
    // Setter for the sender
    public void setSender(String sender) {
        if (sender == null || sender.isEmpty()) {
            throw new IllegalArgumentException("Sender cannot be null or empty");
        }
        this.sender = sender;
    }
 
    // Getter for the timestamp
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
 
    // Method to format the timestamp for better readability
    public String getFormattedTimestamp() {
        return timestamp.format(formatter);
    }
 
    // Override the toString method to display the message in a readable format
    @Override
    public String toString() {
        return getFormattedTimestamp() + " [" + sender + "]: " + content;
    }
 
    // Override equals to compare messages based on content, sender, and timestamp
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Message message = (Message) obj;
        return content.equals(message.content) &&
               sender.equals(message.sender) &&
               timestamp.equals(message.timestamp);
    }
 
    // Override hashCode to match the logic in equals
    @Override
    public int hashCode() {
        return Objects.hash(content, sender, timestamp);
    }
}
