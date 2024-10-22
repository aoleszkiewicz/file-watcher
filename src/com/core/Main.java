package com.core;

public class Main {
    public static void main(String[] args)
    {
        String sourceDir = "/Users/aoleszkiewicz/IdeaProjects/FileWatcher/monitored_files";
        String targetDir = "/Users/aoleszkiewicz/IdeaProjects/FileWatcher/backuped_files";

        FileSynchronizer fileSynchronizer = new FileSynchronizer(sourceDir, targetDir);
        fileSynchronizer.initialize();
    }
}