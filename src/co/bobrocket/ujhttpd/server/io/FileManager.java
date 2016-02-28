package co.bobrocket.ujhttpd.server.io;

import co.bobrocket.ujhttpd.server.data.ByteBuilder;
import co.bobrocket.ujhttpd.server.data.Constants;
import co.bobrocket.ujhttpd.server.IWebServer;
import co.bobrocket.ujhttpd.server.io.file.ErrorWebFile;
import co.bobrocket.ujhttpd.server.io.file.IWebFile;
import co.bobrocket.ujhttpd.server.io.file.WebFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * The FileManager class is a basic implementation of the {@link IFileManager} abstraction, with basic cache support.
 * Its purpose is to simply manage caching and serving of files between instances of {@link co.bobrocket.ujhttpd.server.IWebClient}.
 */
public class FileManager implements IFileManager {
    private String workingDirectory;
    private IWebServer owner;
    private Map<String, IWebFile> cachedFiles = new HashMap<>();
    private List<String> indexFiles = new ArrayList<>();

    /**
     * Instantiates an {@link FileManager}
     *
     * @param owner - The {@link IWebServer} instance that owns this instance
     * */
    public FileManager(IWebServer owner) {
        this.owner = owner;
        this.workingDirectory = owner.getWorkingDirectory();
        cachedFiles.put("ERROR_PAGE", new ErrorWebFile()); //Error page

        //Add any potential index files we may have
        indexFiles.add("index.html");
        indexFiles.add("index.htm");
        indexFiles.add("index.xhtml");
    }

    /**
     * Gets a file as a string.
     *
     * @param path - Relative file path
     *
     * @return The file contents as a UTF-8 string
     * */
    @Override
    public String getFileString(String path) { //Obsolete, removing soon
        try {
            StringBuilder stringBuilder = new StringBuilder();
            for (String line : Files.readAllLines(Paths.get(workingDirectory, path))) stringBuilder.append(line).append(Constants.CLRF);
            return stringBuilder.toString();
        }
        catch (Exception e) {

        }
        return "";
    }

    /**
     * Gets a file as a byte array.
     *
     * @param path - Relative file path
     *
     * @return The file contents as a byte array
     * */
    @Override
    public byte[] getFileBytes(String path) {
        byte[] result = new byte[0];
        File target = new File(workingDirectory, path);

        if (!target.canRead()) return result; //We can't read the file, so we're going to have to return nothing

        //Either list a directory or grab an index file.
        if (target.isDirectory()) {
            //TODO: cache index files per directory/server
            for (String indexFile : indexFiles) { //Iterate through every index file we know of
                File tmpIndex = new File(workingDirectory, path + indexFile);
                if (tmpIndex.exists() && tmpIndex.canRead()) {
                    target = tmpIndex;
                    break;
                }
            }
            //Directory listing here
        }
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(target, "rw")) {
            result = new byte[(int)randomAccessFile.length()];
            randomAccessFile.read(result);
        }
        catch (Exception e) {

        }
        return result;
    }

    /**
     * Gets a file as a {@link IWebFile} instance; retrieves from cache if necessary.
     *
     * @param path - Relative file path
     *
     * @return A {@link IWebFile} which represents a file on the local file system.
     * */
    @Override
    public IWebFile getFile(String path) {
        byte[] contents = getFileBytes(path);
        if (contents.length == 0) return cachedFiles.get("ERROR_PAGE");
        return new WebFile(path, contents);
    }
}
