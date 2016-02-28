package co.bobrocket.ujhttpd.server.io.file;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * ErrorWebFile is an implementation of {@link WebFile} for when errors occur.
 */
public class ErrorWebFile extends WebFile {
    public ErrorWebFile() {
        super("error.html", getBytesSafe("<html><b>Error!</b></html>"));
    }

    @Override
    public boolean isCacheable() {
        return true;
    }
}
