import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class ClientHandler implements Runnable {
    private ChatServer server;
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private static final Logger logger = Logger.getLogger(ClientHandler.class.getName()); // Logger for client activity
    private String clientName;
 
    // Constructor to initialize client handler
    public ClientHandler(ChatServer server, Socket socket) throws IOException {
        this.server = server;
        this.socket = socket;
 
        // Create input and output streams for the client socket
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
 
        // Optional: Set a client name or identifier
        this.clientName = "Client-" + socket.getPort(); // Example client name based on port
        logger.log(Level.INFO, "{0} connected.", clientName);
    }
 
    // Method to send a message to this client
    public void sendMessage(String message) {
        out.println(message); // Send the message to the client
        logger.log(Level.INFO, "Sent message to {0}: {1}", new Object[]{clientName, message});
    }
 
    // Method to get the client's name (for logging purposes or identification)
    public String getClientName() {
        return clientName;
    }
 
    @Override
    public void run() {
        try {
            // Continuously listen for messages from the client
            String message;
            while ((message = in.readLine()) != null) {
                logger.log(Level.INFO, "Received from {0}: {1}", new Object[]{clientName, message});
                
                // Broadcast the message to other clients
                server.broadcastMessage(message, this);
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "{0} disconnected due to error: {1}", new Object[]{clientName, e.getMessage()});
        } finally {
            closeConnections(); // Close all resources
            server.removeClient(this); // Remove this client from the server's client list
        }
    }
 
    // Method to close the client's socket and streams
    private void closeConnections() {
        try {
            if (in != null) in.close(); // Close input stream
            if (out != null) out.close(); // Close output stream
            if (socket != null && !socket.isClosed()) socket.close(); // Close the socket
            logger.log(Level.INFO, "{0} disconnected.", clientName);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error closing connection for {0}: {1}", new Object[]{clientName, e.getMessage()});
        }
    }
}
