package com.trianguloy.urlchecker.utilities;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Represents an internal file, can be modified
 */
public class InternalFile {
    private final String fileName;
    private Context cntx;

    public InternalFile(String fileName) {
        this.fileName = fileName;
    }

    public InternalFile init(Context cntx) {
        this.cntx = cntx;
        return this;
    }

    /**
     * Returns the content, null if the file doesn't exists or can't be read
     */
    public String get() {
        try {
            return StreamUtils.inputStream2String(cntx.openFileInput(fileName));
        } catch (IOException ignored) {
            return null;
        }
    }

    /**
     * Streams the lines
     */
    public boolean stream(JavaUtilities.Consumer<String> function) {
        try {
            StreamUtils.consumeLines(cntx.openFileInput(fileName), function);
            return true;
        } catch (IOException ignored) {
            // do nothing
            return false;
        }
    }

    /**
     * Sets a new file content
     */
    public boolean set(String content) {

        // the same, already saved
        if (content.equals(get())) {
            return true;
        }

        // store
        try (FileOutputStream fos = cntx.openFileOutput(fileName, Context.MODE_PRIVATE)) {
            fos.write(content.getBytes(StreamUtils.UTF_8));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes the file
     */
    public void delete() {
        cntx.deleteFile(fileName);
    }

}
