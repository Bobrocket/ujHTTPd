package co.bobrocket.ujhttpd.server.io;

import co.bobrocket.ujhttpd.server.io.file.IWebFile;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * The {@link IFileManager} interface is used as a barebones, passable framework for managing and caching files.
 */
public interface IFileManager {
    String getFileString(String path);
    byte[] getFileBytes(String path);
    IWebFile getFile(String path);
}
