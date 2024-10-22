package com.core;

import com.enums.Action;
import com.models.IFileEventHandler;

import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;

public class FileEventHandler implements IFileEventHandler {
    private final Path _targetDir;
    private final FileManager _fileManager;
    private final FileLogger _fileLogger;
    private final FileBlacklist _fileBlacklist;

    public FileEventHandler(Path targetDir) {
        this._targetDir = targetDir;
        this._fileManager = new FileManager();
        this._fileLogger = new FileLogger(targetDir);
        this._fileBlacklist = new FileBlacklist();
    }

    public void handleEvent(WatchEvent.Kind<?> eventKind, Path sourcePath) {
        Path targetPath = _targetDir.resolve(sourcePath.getFileName());
        String fileName = sourcePath.getFileName().toString();

        if (_fileBlacklist.IsFileBlacklisted(fileName)) {
            return;
        }

        try {
            if (eventKind == StandardWatchEventKinds.ENTRY_CREATE) {
                _fileManager.copyFile(sourcePath, targetPath);
                _fileLogger.logEvent(targetPath, Action.CREATE);
            } else if (eventKind == StandardWatchEventKinds.ENTRY_MODIFY) {
                _fileManager.copyFile(sourcePath, targetPath);
                _fileLogger.logEvent(targetPath, Action.MODIFY);
            } else if (eventKind == StandardWatchEventKinds.ENTRY_DELETE) {
                _fileManager.deleteFile(targetPath);
                _fileLogger.logEvent(targetPath, Action.DELETE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
