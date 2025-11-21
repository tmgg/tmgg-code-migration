package io.github.tmgg.code.migration.handler.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tmgg.code.migration.handler.JsxHandler;

import java.io.File;
import java.util.List;

public class JsxCheckAdminUrlHandler extends JsxHandler {
    @Override
    public String process(List<String> lines) {
        for (int i = 0; i < lines.size(); i++) {
            String line = lines.get(i);
            if (StrUtil.containsAny(line, "HttpUtil.", "<FieldSelect") && !line.contains("admin/")) {
                throw new IllegalStateException("第" + (i + 1) + "行,HttpUtil请求的后台url应该以admin开头");
            }
        }
        return null;
    }
}
