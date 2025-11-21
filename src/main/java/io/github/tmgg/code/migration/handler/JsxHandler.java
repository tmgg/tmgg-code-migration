package io.github.tmgg.code.migration.handler;

import io.github.tmgg.code.migration.handler.FileHandler;

public abstract class JsxHandler implements FileHandler {
    @Override
    public boolean support(String fileType) {
        return fileType.equals("jsx");
    }
}
