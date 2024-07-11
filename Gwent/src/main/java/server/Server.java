package server;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Server {
    static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(Server.class);
    public static final int PORT = 1020; // Server port
    private static final int POOL_SIZE = 10; // Thread pool size
    private static final ExecutorService executorService = Executors.newFixedThreadPool(POOL_SIZE);
    private static final ConcurrentHashMap<String, ClientHandler> onlineUsers = new ConcurrentHashMap<>();
    public static ConcurrentHashMap<String, ClientHandler> getOnlineUsers() {
        return onlineUsers;
    }

    public static void main(String[] args) {

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (!serverSocket.isClosed()) {
                Socket clientSocket = serverSocket.accept();
                executorService.submit(new ClientHandler(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Could not listen on port " + PORT, e);
        } finally {
            executorService.shutdown();
        }
    }

}

