package io.github.tmgg.code.migration.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.setting.Setting;
import cn.hutool.setting.SettingUtil;

import java.io.File;

public class ProjectUtils {


    public static File getDir() {
        Setting setting = SettingUtil.get("config");
        String dir = setting.get("dir");
        System.out.println("项目目录为：" + dir);
        File file = new File(dir);
        Assert.state(file.exists(), "目录不存在");
        return file;
    }
}
