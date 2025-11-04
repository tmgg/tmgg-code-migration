package io.github.tmgg.code.migration.utils;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.StrUtil;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingUtil;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ProjectUtils {


    public static File getDir() {
        Setting setting = SettingUtil.get("config");
        String dir = setting.get("dir");
        System.out.println("项目目录为：" + dir);
        File file = new File(dir);
        Assert.state(file.exists(), "目录不存在");
        return file;
    }


    public static void replace(FileType fileType, Map<String,String> replaceMap) {
        File dir = getDir();

        if (fileType == FileType.java) {
            dir = new File(dir, "src");
        }

        List<File> files = FileUtil.loopFiles(dir, pathname -> pathname.getName().endsWith(fileType.name()));

        for (File file : files) {
            String content = FileUtil.readUtf8String(file);
            String newContent = content;
            for (Map.Entry<String, String> e : replaceMap.entrySet()) {
                 newContent = StrUtil.replace(newContent, e.getKey(), e.getValue());
            }

            if(!content.equals(newContent)){
                System.out.println("修改文件 " + file.getName());
                FileUtil.writeUtf8String(newContent,file);
            }
        }
    }
}
