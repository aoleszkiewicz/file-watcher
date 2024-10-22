package com.models;

import java.io.IOException;
import java.nio.file.Path;

public interface IFileOperation {
    void copyFile(Path source, Path target) throws IOException;
    void deleteFile(Path target) throws IOException;
}
