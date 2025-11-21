package io.github.tmgg.code.migration.handler;

import java.io.File;
import java.util.List;

public interface FileHandler {

     String process(List<String> lines);

     boolean support(String fileType);
}
