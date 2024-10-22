package com.models;

import com.enums.Action;

import java.nio.file.Path;

public interface ILogger {
    void logEvent(Path filePath, Action action);
}
