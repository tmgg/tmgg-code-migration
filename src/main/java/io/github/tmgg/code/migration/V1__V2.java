package io.github.tmgg.code.migration;

import io.github.tmgg.code.migration.utils.FileType;
import io.github.tmgg.code.migration.utils.ProjectUtils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class V1__V2 {
    public static void main(String[] args) {
        Map<String, String> javaMap = new HashMap<>();
        javaMap.put("import io.tmgg.lang.obj.AjaxResult;", "import io.tmgg.dto.AjaxResult;");
        javaMap.put("import io.tmgg.lang.obj.Option;", "import io.tmgg.dto.Option;");
        javaMap.put("import io.tmgg.lang.obj.TreeOption;", "import io.tmgg.dto.TreeOption;");

        javaMap.put("import io.tmgg.web.persistence.BaseDao;", "import io.tmgg.data.repository.BaseDao;");
        javaMap.put("import io.tmgg.web.persistence.BaseService;", "import io.tmgg.data.service.BaseService;");
        javaMap.put("import io.tmgg.web.persistence.specification.JpaQuery;", "import io.tmgg.data.query.JpaQuery;");
        javaMap.put("import io.tmgg.web.persistence.EntityTool;", "import io.tmgg.data.EntityTool;");
        javaMap.put("import io.tmgg.web.persistence.BaseEntity;", "import io.tmgg.data.domain.BaseEntity;");
        javaMap.put("import io.tmgg.web.persistence.id.", "import io.tmgg.data.id.");
        javaMap.put("import io.tmgg.lang.obj.table", "import io.tmgg.dto.table");
        javaMap.put("import io.tmgg.web.persistence.PersistEntity;", "import io.tmgg.data.domain.PersistEntity;");
        javaMap.put("import io.tmgg.web.persistence.StatField;", "import io.tmgg.data.query.StatField;");
        javaMap.put("import io.tmgg.web.persistence.StatType;", "import io.tmgg.data.query.StatType;");
        javaMap.put("import io.tmgg.lang.validator.", "import io.tmgg.validator.");
        javaMap.put("import io.tmgg.web.persistence.converter.", "import io.tmgg.data.converter.");

        javaMap.put("extends BaseJob", "extends BaseJob");

        ProjectUtils.replace(FileType.java, javaMap);


    }
}
