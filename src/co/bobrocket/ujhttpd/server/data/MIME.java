package co.bobrocket.ujhttpd.server.data;

import co.bobrocket.ujhttpd.server.io.file.WebFileData;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * The MIME class is used as a dictionary for MIME types and whether or not they should be cached as a general rule.
 */
public class MIME {
    private static Map<String, WebFileData> types = new HashMap<>();
    private static final WebFileData PLAINTEXT = new WebFileData("text/plain", false);

    /*
    * Register some default MIME types at the beginning of execution.
    * */
    static {
        /* Text */
        register("html", "text/html");
        register("txt", "text/plain");
        register("css", "text/css");
        register("xml", "text/xml");

        register("json", "application/json");

        /* Image */
        register("png", "image/png");
        register("jpg", "image/jpeg");
        register("jpeg", "image/jpeg");
        register("bmp", "image/bmp");
        register("gif", "image/gif");

        /* Binary/compiled */
        register("exe", "application/octet-stream");
    }

    /**
     * Register a MIME type to an extension.
     *
     * @param extension - The extension of the file (txt, html etc.)
     * @param mime - The MIME type to be given (text/plain, text/html etc.)
     * */
    public static void register(String extension, String mime) {
        types.put(extension, new WebFileData(mime, false));
    }

    /**
     * Get the MIME type for an extension
     *
     * @param extension - The file extension (txt, html etc.)
     *
     * @return The MIME type (text/plain, text/html etc.)
     * */
    public static String get(String extension) {
        return types.getOrDefault(extension.toLowerCase(), PLAINTEXT).getMIME();
    }

    /**
     * Gets the MIME type for a file path
     *
     * @param path - The file path
     *
     * @return The MIME type (text/plain, text/html etc.)
     * */
    public static String getForPath(String path) {
        String[] pathParts = path.split("/");
        String nameAndExtension = pathParts[pathParts.length - 1];
        String[] fileParts = nameAndExtension.split("\\.");
        return get(fileParts[fileParts.length - 1]);
    }
}
