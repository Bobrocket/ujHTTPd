package co.bobrocket.ujhttpd.server.io.file;

import co.bobrocket.ujhttpd.server.data.MIME;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * A WebFile is an abstraction of the {@link IWebFile} interfact that is merely to be used as a (potentially) cacheable wrapper
 * for a file on the local file system.
 */
public class WebFile implements IWebFile {

    private String fullPath, name, extension, mime;
    private byte[] contents;

    /**
     * Instantiates a {@link WebFile}
     *
     * @param path - The relative file path of the file
     * @param contents - A byte array of the contents of the file
     * */
    public WebFile(String path, byte[] contents) {
        this.fullPath = path;
        this.contents = contents;

        if (path.endsWith("/")) path = path + "index.html"; //TODO: not rely on this!

        String[] pathParts = path.split("/");
        String nameAndExtension = pathParts[pathParts.length - 1];
        String[] fileParts = nameAndExtension.split("\\.");
        this.extension = fileParts[fileParts.length - 1];

        StringBuilder nameBuilder = new StringBuilder(fileParts[0]);
        for (int i = 1; i < fileParts.length - 2; i++) nameBuilder.append(fileParts[i]).append((i != fileParts.length - 3) ? "." : "");

        this.name = nameBuilder.toString();
        this.mime = MIME.get(this.extension);
    }

    /**
     * Get the file extension of the cached file
     *
     * @return The file extension (html, txt etc.)
     * */
    @Override
    public String getExtension() {
        return extension;
    }

    /**
     * Get the name of the cached file as displayed on the local file system
     *
     * @return The name (index, profile etc.)
     * */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Get the contents of a file as a byte array (strings encoded w/ UTF-8)
     *
     * @return The contents of the file
     * */
    @Override
    public byte[] getContents() {
        return contents;
    }

    /**
     * Get the associated MIME type from #getExtension()
     *
     * @return The MIME type, given by MIME#get(#getExtension())
     * */
    @Override
    public String getMIME() {
        return mime;
    }

    /**
     * Get whether or not the file is cacheable (TODO)
     *
     * @return False
     * */
    @Override
    public boolean isCacheable() {
        return false;
    }

    /**
     * "Safe" method to turn a UTF-8 string into a byte array for WebFile abstractions
     *
     * @return Byte array of the UTF-8 encoded string
     * */
    protected static byte[] getBytesSafe(String input) {
        byte[] res = new byte[1];
        try {
            res = input.getBytes("UTF-8");
        }
        catch (Exception e) {

        }
        return res;
    }
}
