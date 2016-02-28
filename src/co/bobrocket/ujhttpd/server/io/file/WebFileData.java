package co.bobrocket.ujhttpd.server.io.file;

/**
 * Created by Bobrocket on 27/02/2016.
 */
public class WebFileData {
    private boolean cacheable;
    private String mime;

    public WebFileData(String mime, boolean cacheable) {
        this.cacheable = cacheable;
        this.mime = mime;
    }

    public boolean isCacheable() {
        return cacheable;
    }

    public String getMIME() {
        return mime;
    }

    public void setCacheable(boolean val) {
        this.cacheable = val;
    }
}
