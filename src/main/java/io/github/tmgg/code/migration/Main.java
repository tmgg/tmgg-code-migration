package io.github.tmgg.code.migration;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tmgg.code.migration.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        System.out.println(ProjectUtils.getDir());
        java();
     //   pkg();


    }

    private static void java() {
        Map<String, String> replaceMap = new HashMap<>();

        String s = ResourceUtil.readUtf8Str("replace-java.txt").trim();
        List<String> lines = StrUtil.splitTrim(s, "\n");
        for (int i = 0; i < lines.size(); i++) {
            String key = lines.get(i);
            String value = lines.get(++i);
            replaceMap.put(key, value);
        }

        ProjectUtils.replace(".java", replaceMap);
    }

    private static void pkg() {
        String file = ProjectUtils.getDir() + "/web/package.json";
        List<String> lines = FileUtil.readUtf8Lines(file);

        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            if (StrUtil.containsAny(line, "@types/react-dom", "@types/react","\"react\"","react-dom")
            && line.contains("18.")) {
                String newLine = line.replaceAll("\\^18[^\\s,}\"]*", "^19.0.0");
                System.err.println("react版本修改为19:" + line + "-->" + newLine);
                newLines.add(newLine);
                continue;
            }
            newLines.add(line);
        }
        FileUtil.writeUtf8Lines(newLines, file);

        String content = FileUtil.readUtf8String(file);
        if(!content.contains("@ant-design/v5-patch-for-react")){
            // "@ant-design/v5-patch-for-react-19": "^1.0.3"
            throw new IllegalStateException("请手动添加 \"@ant-design/v5-patch-for-react-19\": \"^1.0.3\"");
        }
    }
}
