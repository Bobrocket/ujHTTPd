package co.bobrocket.ujhttpd;

import co.bobrocket.ujhttpd.application.RuntimeParameters;
import co.bobrocket.ujhttpd.server.IWebClient;
import co.bobrocket.ujhttpd.server.InstantiatableWebServer;
import co.bobrocket.ujhttpd.server.WebServer;
import co.bobrocket.ujhttpd.server.data.MIME;

/**
 * Created by Bobrocket on 27/02/2016.
 */
public class Main {

    public static void main(String[] args) {
        //TODO: implement RuntimeParameters
        try {
            WebServer webServer = new InstantiatableWebServer(80);
            webServer.run(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
