package com.core;

import java.util.Arrays;
import java.util.List;

public class FileBlacklist {
    public List<String> blacklistedFiles = Arrays.asList(".DS_Store", "test123");

    public boolean IsFileBlacklisted(String fileName) {
        return this.blacklistedFiles.contains(fileName);
    }
}
