package co.bobrocket.ujhttpd.server;

import co.bobrocket.ujhttpd.server.data.ByteBuilder;
import co.bobrocket.ujhttpd.server.data.Constants;
import co.bobrocket.ujhttpd.server.io.file.IWebFile;
import co.bobrocket.ujhttpd.server.io.file.WebFile;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.UUID;

/**
 * Created by Bobrocket on 27/02/2016.
 */
public class WebClient implements IWebClient {

    private UUID id;
    private IWebServer parent;
    private InputStream in;
    private BufferedReader lineReader;
    private DataOutputStream out;
    private InetAddress address;
    private Socket socket;
    private long connectTime;

    public WebClient(Socket socket, IWebServer parent) throws IOException {
        this.id = UUID.randomUUID();
        this.in = socket.getInputStream();
        this.lineReader = new BufferedReader(new InputStreamReader(this.in));
        this.out = new DataOutputStream(socket.getOutputStream());
        this.parent = parent;
        this.address = socket.getInetAddress();
        this.connectTime = System.currentTimeMillis();
        this.socket = socket;
    }

    @Override
    public String getAddress() {
        return address.toString();
    }

    @Override
    public String readAll() {
        StringBuilder stringBuilder = new StringBuilder();
        String input = "";
        int count;
        byte[] buffer = new byte[1024];
        try {
            while ((count = in.read(buffer)) >= 0) stringBuilder.append(new String(buffer, "UTF-8"));
        }
        catch (Exception e) {

        }
        return stringBuilder.toString();
    }

    @Override
    public String readLine() {
        try {
            return lineReader.readLine();
        }
        catch (Exception e) {
            return "";
        }
    }

    @Override
    public long getConnectTime() {
        return connectTime;
    }

    @Override
    public long getLastAccessedTime() {
        return 0;
    }

    @Override
    public IWebServer getParent() {
        return parent;
    }

    @Override
    public void handle(boolean multithreaded) throws IOException { //TODO: headers
        String firstLine = readLine();
        String[] firstParts = firstLine.split(" ");
        String method = firstParts[0];
        String httpVersion = firstParts[firstParts.length - 1];
        StringBuilder filePathBuilder = new StringBuilder(firstParts[1]);
        for (int i = 2; i < firstParts.length - 2; i++) filePathBuilder.append(firstParts[i]);

        IWebFile requestedFile = parent.getFileManager().getFile(filePathBuilder.toString());

        ByteBuilder resp = new ByteBuilder((httpVersion + " 200 OK").getBytes("UTF-8")).append(Constants.CLRF);
        resp.append("Server: ujHTTPD/1.0.0").append(Constants.CLRF);
        resp.append("Content-Type: " + requestedFile.getMIME()).append(Constants.CLRF);
        resp.append("Content-Length: " + requestedFile.getContents().length).append(Constants.CLRF);

        resp.append(Constants.CLRF);
        resp.append(requestedFile.getContents());

        out.write(resp.toByteArray());
        socket.close();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setSocket(Socket socket) throws IOException {
        this.in = socket.getInputStream();
        this.lineReader = new BufferedReader(new InputStreamReader(this.in));
        this.out = new DataOutputStream(socket.getOutputStream());
        this.address = socket.getInetAddress();
        this.socket = socket;
    }
}
