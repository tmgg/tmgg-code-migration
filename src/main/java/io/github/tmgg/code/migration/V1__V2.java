package io.github.tmgg.code.migration;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tmgg.code.migration.utils.ProjectUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class V1__V2 {
    public static void main(String[] args) {
        System.out.println(ProjectUtils.getDir());
        java();
        pkg();


    }

    private static void java() {
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("import io.tmgg.lang.obj.AjaxResult;", "import io.tmgg.dto.AjaxResult;");
        replaceMap.put("import io.tmgg.lang.obj.Option;", "import io.tmgg.dto.Option;");
        replaceMap.put("import io.tmgg.lang.obj.TreeOption;", "import io.tmgg.dto.TreeOption;");

        replaceMap.put("import io.tmgg.web.persistence.BaseDao;", "import io.tmgg.data.repository.BaseDao;");
        replaceMap.put("import io.tmgg.web.persistence.BaseService;", "import io.tmgg.data.service.BaseService;");
        replaceMap.put("import io.tmgg.web.persistence.specification.JpaQuery;", "import io.tmgg.data.query.JpaQuery;");
        replaceMap.put("import io.tmgg.web.persistence.EntityTool;", "import io.tmgg.data.EntityTool;");
        replaceMap.put("import io.tmgg.web.persistence.BaseEntity;", "import io.tmgg.data.domain.BaseEntity;");
        replaceMap.put("import io.tmgg.web.persistence.id.", "import io.tmgg.data.id.");
        replaceMap.put("import io.tmgg.lang.obj.table", "import io.tmgg.dto.table");
        replaceMap.put("import io.tmgg.web.persistence.PersistEntity;", "import io.tmgg.data.domain.PersistEntity;");
        replaceMap.put("import io.tmgg.web.persistence.StatField;", "import io.tmgg.data.query.StatField;");
        replaceMap.put("import io.tmgg.web.persistence.StatType;", "import io.tmgg.data.query.StatType;");
        replaceMap.put("import io.tmgg.lang.validator.", "import io.tmgg.validator.");
        replaceMap.put("import io.tmgg.web.persistence.converter.", "import io.tmgg.data.converter.");

        replaceMap.put("extends BaseJob", "extends BaseJob");
        replaceMap.put("saveOrUpdateByClient", "saveOrUpdateByRequest");
        replaceMap.put("deleteByClient", "deleteByRequest");
        replaceMap.put("findAllByClient", "findAllByRequest");
        replaceMap.put("findOneByClient", "findOneByRequest");


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

    }
}
