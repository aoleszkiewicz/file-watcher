package com.core;

import com.enums.Action;
import com.models.ILogger;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FileLogger implements ILogger {
    private final Path globalLogFile;

    public FileLogger(Path targetDir) {
        this.globalLogFile = targetDir.resolve("global_log.txt");
    }

    public void logEvent(Path filePath, Action action) {
        String logMessage = String.format("[%s] %s: %s",
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                action,
                filePath.getFileName());

        try {
            String fileNameWithoutExt = filePath.getFileName().toString().split("\\.")[0];
            Path individualLogFile = filePath.getParent().resolve(fileNameWithoutExt + "_log.txt");
            try (FileWriter fileWriter = new FileWriter(individualLogFile.toFile(), true)) {
                fileWriter.write(logMessage + System.lineSeparator());
            }

            try (FileWriter globalWriter = new FileWriter(globalLogFile.toFile(), true)) {
                globalWriter.write(logMessage + System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
