package co.bobrocket.ujhttpd.server;

import java.io.IOException;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by Bobrocket on 27/02/2016.
 */
public interface IWebClient {

    String getAddress();
    String readAll();
    String readLine();

    long getConnectTime();
    long getLastAccessedTime();

    IWebServer getParent();

    void handle(boolean multithreaded) throws IOException;

    UUID getId();

    void setSocket(Socket socket) throws IOException;
}
