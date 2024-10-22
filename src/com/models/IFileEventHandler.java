package com.models;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

public interface IFileEventHandler {
    void handleEvent(WatchEvent.Kind<?> eventKind, Path sourcePath);
}
