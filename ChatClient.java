import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class ChatClient {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static final Logger logger = Logger.getLogger(ChatClient.class.getName()); // Logger for debugging
 
    // Constructor to initialize the client connection to the server
    public ChatClient(String serverAddress, int serverPort) throws IOException {
        try {
            socket = new Socket(serverAddress, serverPort);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
 
            // Start a new thread to handle incoming messages
            new Thread(new ReadMessages()).start();
            logger.log(Level.INFO, "Connected to server at {0}:{1}", new Object[]{serverAddress, serverPort});
        } catch (UnknownHostException e) {
            logger.log(Level.SEVERE, "Unknown host: " + serverAddress, e);
            throw e;
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Unable to establish connection to server", e);
            throw e;
        }
    }
 
    // Method to send a message to the server
    public void sendMessage(String message) {
        if (message != null && !message.trim().isEmpty()) {
            out.println(message);
            logger.log(Level.INFO, "Message sent: {0}", message);
        } else {
            logger.log(Level.WARNING, "Attempted to send an empty or null message");
        }
    }
 
    // Method to check if the socket is still connected
    public boolean isConnected() {
        return socket != null && socket.isConnected() && !socket.isClosed();
    }
 
    // Method to close the client connection and clean up resources
    public void close() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            logger.log(Level.INFO, "Connection closed");
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing connection", e);
        }
    }
 
    // Inner class to handle reading incoming messages from the server
    private class ReadMessages implements Runnable {
        public void run() {
            String message;
            try {
                while ((message = in.readLine()) != null) {
                    System.out.println("Received: " + message);
                    logger.log(Level.INFO, "Received message: {0}", message);
                }
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Error reading messages", e);
            } finally {
                close();
            }
        }
    }
 
    // Main method to launch the ChatClient and handle user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ChatClient client = null;
 
        try {
            // Replace localhost and port with actual server details
            client = new ChatClient("localhost", 1234);
 
            while (client.isConnected()) {
                String message = scanner.nextLine();
 
                if (message.equalsIgnoreCase("exit")) {
                    client.close();
                    break;
                }
 
                client.sendMessage(message);
            }
 
        } catch (IOException e) {
            logger.log(Level.SEVERE, "An error occurred during client execution", e);
        } finally {
            if (client != null && client.isConnected()) {
                client.close();
            }
            scanner.close();
        }
    }
}
