package io.github.tmgg.code.migration.handler.impl;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tmgg.code.migration.handler.JavaHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@Component
public class JavaImportHandler extends JavaHandler {

    @Override
    public String process(List<String> lines) {
        boolean changed = false;
        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            if(line.startsWith("import io.tmgg") ){
                changed = true;
                continue;
            }
            newLines.add(line);
        }

        if(changed){
            return StrUtil.join("\n", newLines);
        }
        return null;
    }
}
