package co.bobrocket.ujhttpd.server;

import java.io.IOException;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * InstantiatableWebServer is an abstraction of {@link WebServer} to simply allow instantiation of a barebones server.
 */
public class InstantiatableWebServer extends WebServer {
    public InstantiatableWebServer(int port) throws IOException {
        super(port);
    }
}
