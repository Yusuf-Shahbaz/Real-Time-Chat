import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class Main {
    private static final int DEFAULT_PORT = 1234;
    private static ExecutorService serverThreadPool = Executors.newSingleThreadExecutor(); // Thread pool to handle server start in a separate thread
    
    public static void main() {
        Scanner scanner = new Scanner(System.in); // Scanner for user input
        ChatServer server = new ChatServer(); // Create a new server instance
        ChatFileHandler logHandler = new ChatFileHandler("server_log.txt"); // Log handler to log server events
        
        // Start server in a separate thread for better control
        serverThreadPool.submit(() -> {
            try {
                logHandler.saveServerStart(); // Log server start
                server.start(); // Start the server
            } catch (IOException e) {
                System.out.println("Error starting server: " + e.getMessage());
            }
        });
        
        // Main thread listens for user input to stop the server
        while (true) {
            System.out.println("Type 'shutdown' to stop the server.");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("shutdown")) {
                shutdownServer(server, logHandler); // Graceful server shutdown
                break;
            }
        }
 
        scanner.close(); // Close scanner resource
    }
 
    // Method to handle server shutdown
    private static void shutdownServer(ChatServer server, ChatFileHandler logHandler) {
        System.out.println("Shutting down server...");
        try {
            server.stop(); // Assume a stop method exists in ChatServer to stop listening
        } catch (IOException e) {
            System.out.println("Error during server shutdown: " + e.getMessage());
        }
 
        // Log server shutdown and shutdown thread pool
        logHandler.saveServerShutdown();
        serverThreadPool.shutdown();
        System.out.println("Server successfully shut down.");
    }
}
