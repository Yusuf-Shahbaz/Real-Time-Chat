import java.io.*;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
 
public class ChatServer {
    private static final int PORT = 1234; // Port on which the server will listen
    private Set<ClientHandler> clientHandlers = Collections.newSetFromMap(new ConcurrentHashMap<ClientHandler, Boolean>()); // Thread-safe set for client handlers
    private static final Logger logger = Logger.getLogger(ChatServer.class.getName()); // Logger for server activity
    private Set<ClientHandler> clientHandlerss = new HashSet<>();
    private boolean running = true;
    private ServerSocket serverSocket;
 
    // Method to start the server and listen for incoming client connections
    public void start() throws IOException {
        ServerSocket serverSocket = new ServerSocket(PORT); // Create a server socket bound to the port
        logger.log(Level.INFO, "Server started on port {0}, waiting for clients...", PORT);
 
        try {
            // Continuously accept new client connections
            while (running) {
                Socket clientSocket = serverSocket.accept(); // Accept the connection
                logger.log(Level.INFO, "New client connected from {0}", clientSocket.getInetAddress());
 
                ClientHandler clientHandler = new ClientHandler(this, clientSocket); // Create a new client handler
                synchronized (clientHandlers) {
                    clientHandlers.add(clientHandler); // Add the new client handler to the set
                }
                new Thread(clientHandler).start(); // Start the client handler in a new thread
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error while accepting client connection", e);
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close(); // Ensure the server socket is closed on exit
                logger.log(Level.INFO, "Server socket closed.");
            }
        }
    }
 
    // Method to broadcast messages to all connected clients except the sender
    public void broadcastMessage(String message, ClientHandler sender) {
        logger.log(Level.INFO, "Broadcasting message: {0}", message);
        synchronized (clientHandlers) {
            for (ClientHandler client : clientHandlers) {
                if (client != sender) { // Don't send the message back to the sender
                    client.sendMessage(message);
                }
            }
        }
    }
 
    // Method to remove a client handler when a client disconnects
    public void removeClient(ClientHandler clientHandler) {
        synchronized (clientHandlers) {
            clientHandlers.remove(clientHandler);
        }
        logger.log(Level.INFO, "Client {0} disconnected.", clientHandler.getClientName());
    }
    
    public void stop() throws IOException{
        running=false;
        if(serverSocket != null && ! serverSocket.isClosed()){
            serverSocket.close();
        }
    }
 
    // Main method to start the server
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        try {
            server.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error starting server", e);
        }
    }
}
