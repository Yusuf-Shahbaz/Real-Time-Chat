------------------------------------------------------------------------
Real-Time Chat Application
------------------------------------------------------------------------
 
   Overview
This project implements a real-time chat application using Java in BlueJ. The application allows multiple clients to communicate through a server, with support for file transfer and exception handling. It consists of several classes, each responsible for specific functionality.
 
   Class Descriptions
 
    1.  ChatClient 
-   Description  : Represents a client in the chat system. It handles connecting to the  ChatServer , sending messages to the server, and receiving updates from the server.
 
    2.  ChatServer 
-   Description  : Manages communication between multiple  ChatClient  instances. It listens for client connections and ensures messages are broadcast to all connected clients. It also handles file transfers.
 
    3.  ClientHandler 
-   Description  : Manages the communication between the server and individual clients. This class runs in a separate thread for each client and ensures messages are properly sent and received by the correct clients.
 
    4.  Message 
-   Description  : Encapsulates the data of a message exchanged between clients. It stores information such as the sender s details, the message content, and other metadata (like timestamps).
 
    5.  ChatFileHandler 
-   Description  : Handles file transfers between clients through the server. It ensures files are properly transmitted, received, and managed.
 
    6.  ChatException 
-   Description  : A custom exception class that defines errors specific to the chat application. It is used to manage and throw exceptions related to chat operations, such as message delivery issues or file transfer problems.
 
    7.  Main 
-   Description  : The entry point for running the application. This class initializes the server and client instances, ensuring they run together seamlessly.
 
   Features
-   Real-time messaging  : Instant message exchange between clients.
-   Multiple clients  : The server supports multiple clients connecting at the same time.
-   File transfers  : Allows clients to send and receive files.
-   Exception handling  : Custom exception classes handle errors and maintain stability during client-server communication.
 
   Requirements
-   BlueJ IDE  : This project is intended to run in BlueJ. Download BlueJ from [here](https://bluej.org/download) and install it on your machine. 
   Setup and Installation
1.   Download the Project  : Clone or download the source code for the chat application.
2.   Open BlueJ  :
   - Open BlueJ and select  Open Project .
   - Navigate to the folder containing the source code and open the project.
3.   Compile the Code  :
   - Right-click in the class diagram and select  Compile  to compile all classes.
4.   Running the Server  :
   - Right-click on the  ChatServer  class and select  new ChatServer() .
   - After creating the  ChatServer  instance, select  void start()  to start the server.
5.   Running the Client  :
   - For each client, right-click on the  ChatClient  class and select  new ChatClient() .
   - After creating the  ChatClient  instance, select  void start()  to launch the client.
 
   Usage
-   Starting the Server  : First, start the  ChatServer . This will allow clients to connect.
-   Connecting Clients  : After the server is running, start one or more  ChatClient  instances to connect to the server. Clients can then send messages and receive responses from others.
-   Sending Messages  : Once connected, clients can type and send messages to be broadcast to all other connected clients.
-   File Transfers  : Clients can also send files through the server using the  ChatFileHandler .
 
   Known Issues
- The application may experience delays during large file transfers.
- Ensure the server is started before clients attempt to connect.
  
   Future Enhancements
- Add encryption to messages for secure communication.
- Implement a user authentication system for added security.
- Store chat history on the server for later retrieval by clients.
  
