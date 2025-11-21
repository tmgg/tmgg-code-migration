package io.github.tmgg.code.migration.handler;

import io.github.tmgg.code.migration.handler.FileHandler;

public abstract class JavaHandler implements FileHandler {
    @Override
    public boolean support(String fileType) {
        return fileType.equals("java");
    }
}
