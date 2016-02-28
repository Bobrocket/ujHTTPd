package co.bobrocket.ujhttpd.server;

import co.bobrocket.ujhttpd.server.io.IFileManager;

/**
 * Created by Bobrocket on 27/02/2016.
 */
public interface IWebServer {

    String getWorkingDirectory();

    int getPort();
    int getMaxClients();

    void run(boolean multithreaded);
    void handleClient(IWebClient client);

    IFileManager getFileManager();
}
