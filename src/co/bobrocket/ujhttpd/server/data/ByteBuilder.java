package co.bobrocket.ujhttpd.server.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bobrocket on 27/02/2016.
 *
 * ByteBuilder is a class that will write and manage a byte array, similar to a StringBuilder.
 */
public class ByteBuilder {

    private List<Byte> src = new ArrayList<>();
    private byte[] cachedBytes; //When we call toByteArray(), we should cache our results so that we don't waste computational power on reevaluating the same contents
    private boolean reevaluate = true; //This manages whether or not we should reevaluate our contents

    public ByteBuilder(byte... src) {
        for (byte b : src) this.src.add(b);
    }

    public ByteBuilder(String src) {
        try {
            byte[] srcArray = src.getBytes("UTF-8");
            for (byte b : srcArray) this.src.add(b);
        }
        catch (Exception e) {
            System.out.println("ByteBuilder could not instantiate with string \"" + src + "\"");
        }
    }

    public ByteBuilder append(byte... toAppend) {
        for (byte b : toAppend) this.src.add(b);
        reevaluate = true; //when we operate, our contents are no longer the same
        return this;
    }

    public ByteBuilder append(String toAppend) {
        try {
            return append(toAppend.getBytes("UTF-8"));
        }
        catch (Exception e) {
            System.out.println("ByteBuilder could not append string \"" + toAppend + "\"");
            e.printStackTrace();
            return this;
        }
    }

    public ByteBuilder append(int toAppend) {
        toAppend = (toAppend > 255) ? 255 : (toAppend < 0) ? 0 : toAppend;
        return append((byte)toAppend);
    }

    public byte[] toByteArray() {
        if (reevaluate) {
            byte[] newArray = new byte[src.size()];
            for (int i = 0; i < src.size(); i++) newArray[i] = src.get(i);
            cachedBytes = newArray;
            reevaluate = false;
            return newArray;
        }
        else {
            return cachedBytes;
        }
    }
}
