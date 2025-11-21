package io.github.tmgg.code.migration;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingUtil;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import io.github.tmgg.code.migration.handler.FileHandler;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class MainCommandRunner implements CommandLineRunner {

    public static final String[] FILE_TYPES = {"java", "jsx"};
    public static final String[] IGNORE = {"node_modules"};

    private final Multimap<String, File> fileMap = ArrayListMultimap.create();

    @Resource
    List<FileHandler> handlers;

    @Override
    public void run(String... args) throws Exception {
        init();
        start();
        invokeHandler();
    }

    public void init() {
        File dir = getDir();
        for (String fileType : FILE_TYPES) {
            List<File> files = FileUtil.loopFiles(dir, pathname -> pathname.getName().endsWith(fileType));
            for (File file : files) {
                if (StrUtil.containsAny(file.getAbsolutePath(), IGNORE)) {
                    continue;
                }
                fileMap.put(fileType, file);
            }
        }
    }

    public void invokeHandler() {
        for (Map.Entry<String, File> e : fileMap.entries()) {
            String key = e.getKey();
            File file = e.getValue();

            List<String> lines = FileUtil.readUtf8Lines(file);
            for (FileHandler handler : handlers) {
                if (handler.support(key)) {
                    try {
                        String newContent = handler.process(lines);
                        if (newContent != null) {
                            FileUtil.writeUtf8String(newContent, file);
                        }
                    } catch (Exception ex) {
                        log.info("处理文件失败 {}" , file.getAbsolutePath());
                        log.error(ex.getMessage());
                        System.exit(1);
                    }
                }
            }
        }
    }


    public void start() {
        System.out.println(getDir());

        for (String fileType : FILE_TYPES) {
            Map<String, String> replaceMap = new LinkedHashMap<>();

            String s = ResourceUtil.readUtf8Str("replace-" + fileType + ".txt").trim();
            List<String> lines = StrUtil.splitTrim(s, "\n");
            for (int i = 0; i < lines.size(); i++) {
                String key = lines.get(i);
                String value = lines.get(++i);
                replaceMap.put(key, value);
            }
            replace(fileType, replaceMap);
        }

        pkg();
    }


    private void pkg() {
        String file = getDir() + "/web/package.json";
        List<String> lines = FileUtil.readUtf8Lines(file);

        List<String> newLines = new ArrayList<>();
        for (String line : lines) {
            if (StrUtil.containsAny(line, "@types/react-dom", "@types/react", "\"react\"", "react-dom")
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
        if (!content.contains("@ant-design/v5-patch-for-react")) {
            // "@ant-design/v5-patch-for-react-19": "^1.0.3"
            throw new IllegalStateException("请手动添加 \"@ant-design/v5-patch-for-react-19\": \"^1.0.3\"");
        }
    }

    public File getDir() {
        Setting setting = SettingUtil.get("config");
        String dir = setting.get("dir");
        File file = new File(dir);
        Assert.state(file.exists(), "目录不存在" + dir);
        return file;
    }


    public void replace(String fileType, Map<String, String> replaceMap) {
        File dir = getDir();

        List<File> files = FileUtil.loopFiles(dir, pathname -> pathname.getName().endsWith(fileType));

        for (File file : files) {
            if (StrUtil.containsAny(file.getAbsolutePath(), IGNORE)) {
                continue;
            }

            String content = FileUtil.readUtf8String(file);
            String newContent = content;
            for (Map.Entry<String, String> e : replaceMap.entrySet()) {
                newContent = StrUtil.replace(newContent, e.getKey(), e.getValue());
            }

            if (!content.equals(newContent)) {
                System.out.println("修改文件 " + file.getAbsolutePath());
                FileUtil.writeUtf8String(newContent, file);
            }
        }
    }


}
