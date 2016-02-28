package co.bobrocket.ujhttpd.server.io.file;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * The {@link IWebFile} interface is used to represent a barebones, potentially cacheable web file.
 */
public interface IWebFile {
    String getExtension();
    String getName();
    byte[] getContents();
    String getMIME();

    boolean isCacheable();
}
