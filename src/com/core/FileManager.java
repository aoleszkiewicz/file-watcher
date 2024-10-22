package com.core;

import com.models.IFileOperation;

import java.io.IOException;
import java.nio.file.*;

public class FileManager implements IFileOperation {

    public void copyFile(Path source, Path target) throws IOException {
        Files.createDirectories(target.getParent());
        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
    }

    public void deleteFile(Path target) throws IOException {
        Files.deleteIfExists(target);
    }
}
