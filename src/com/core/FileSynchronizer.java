package com.core;

import java.io.IOException;
import java.nio.file.*;

public class FileSynchronizer {
    private final Path sourceDir;
    private final Path targetDir;
    private final FileEventHandler eventHandler;
    private final FileBlacklist _fileBlacklist;


    public FileSynchronizer(String sourceDir, String targetDir) {
        this.sourceDir = Paths.get(sourceDir);
        this.targetDir = Paths.get(targetDir);
        this.eventHandler = new FileEventHandler(this.targetDir);
        this._fileBlacklist = new FileBlacklist();
    }

    public void initialize() {
        startupSynchronize();

        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            WatchKey watchKey = sourceDir.register(watchService,
                    StandardWatchEventKinds.ENTRY_CREATE,
                    StandardWatchEventKinds.ENTRY_MODIFY,
                    StandardWatchEventKinds.ENTRY_DELETE);

            do {
                for (WatchEvent<?> event : watchKey.pollEvents()) {
                    Path filePath = sourceDir.resolve((Path) event.context());
                    eventHandler.handleEvent(event.kind(), filePath);
                }

            } while (watchKey.reset());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void startupSynchronize() {
        try {
            Files.walk(sourceDir)
                    .filter(Files::isRegularFile)
                    .forEach(path -> {
                        String fileName = path.getFileName().toString();

                        if (_fileBlacklist.IsFileBlacklisted(fileName)) {
                            return;
                        }

                        Path targetPath = targetDir.resolve(sourceDir.relativize(path));
                        try {
                            if (!Files.exists(targetPath)) {
                                Files.createDirectories(targetPath.getParent());
                                this.eventHandler.handleEvent(StandardWatchEventKinds.ENTRY_CREATE, path);
                                return;
                            }

                            boolean isModified = Files.getLastModifiedTime(path).compareTo(Files.getLastModifiedTime(targetPath)) > 0;

                            if (isModified) {
                                this.eventHandler.handleEvent(StandardWatchEventKinds.ENTRY_MODIFY, path);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
