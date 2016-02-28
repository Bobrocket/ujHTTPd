package co.bobrocket.ujhttpd.server;

import co.bobrocket.ujhttpd.server.io.FileManager;
import co.bobrocket.ujhttpd.server.io.IFileManager;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * The {@link WebServer} class is a basic abstract implementation of the {@link IWebServer} interface, with basic
 * multithreading support
 */
public abstract class WebServer implements IWebServer {

    private int port = 80;
    private int maxClients = 50;
    private String workingDirectory = "/ujhttpd";
    private ServerSocket serverSocket;
    private IFileManager fileManager;
    private Map<InetAddress, IWebClient> clientList = new HashMap<>();

    /**
     * Instantiates a {@link WebServer}
     *
     * @param port - The server port
     * */
    public WebServer(int port) throws IOException {
        this.port = port;
        this.fileManager = new FileManager(this);
        this.serverSocket = new ServerSocket(port);
    }

    /**
     * Instantiates a {@link WebServer}
     *
     * @param port - The server port
     * @param maxClients - The maximum number of clients that will be cached on the server instance
     * */
    public WebServer(int port, int maxClients) {
        this.port = port;
        this.maxClients = maxClients;
        this.fileManager = new FileManager(this);
    }

    /**
     * Instantiates a {@link WebServer}
     *
     * @param port - The server port
     * @param maxClients - The maximum number of clients that will be cached on the server instance
     * @param workingDirectory - The working (root) directory of the server instance
     * */
    public WebServer(int port, int maxClients, String workingDirectory) {
        this.port = port;
        this.maxClients = maxClients;
        this.workingDirectory = workingDirectory;
        this.fileManager = new FileManager(this);
    }

    /**
     * Get the working directory of the instance.
     *
     * @return The working (root) directory of the server instance.
     * */
    @Override
    public String getWorkingDirectory() {
        return workingDirectory;
    }

    /**
     * Get the port that the server is running on
     *
     * @return The server port
     * */
    @Override
    public int getPort() {
        return port;
    }

    /**
     * Get the maximum number of clients that will be cached on the server instance.
     *
     * @return The maximum number of clients cached on the instance
     * */
    @Override
    public int getMaxClients() {
        return maxClients;
    }

    /**
     * Get the {@link IFileManager} associated with the server instance
     *
     * @return The file manager which handles serving & caching files
     * */
    @Override
    public IFileManager getFileManager() {
        return fileManager;
    }

    /**
     * Run the web server.
     *
     * @param multithreaded - Whether or not to run on the main thread (FALSE) or on another thread (TRUE)
     * */
    @Override
    public void run(boolean multithreaded) {
        System.out.println("Running WebServer on port " + port);
        WebServer server = this;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Socket socket = serverSocket.accept();
                        if (!clientList.containsKey(socket.getInetAddress())) {
                            IWebClient client = new WebClient(socket, server);
                            client.handle(multithreaded);
                            clientList.put(socket.getInetAddress(), client);
                        }
                        else {
                            IWebClient cachedClient = clientList.get(socket.getInetAddress());
                            cachedClient.setSocket(socket);
                            clientList.put(socket.getInetAddress(), cachedClient);
                            cachedClient.handle(multithreaded);
                        }
                    }
                    catch (Exception e) {
                        System.out.println("Exception: " + e.getMessage());
                        e.printStackTrace();
                    }
                }
            }
        };
        if (!multithreaded) runnable.run();
        else new Thread(runnable).start();

    }

    /**
     * TODO.
     * */
    @Override
    public void handleClient(IWebClient client) {

    }
}
