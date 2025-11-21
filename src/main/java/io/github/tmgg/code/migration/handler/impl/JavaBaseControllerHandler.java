package io.github.tmgg.code.migration.handler.impl;

import cn.hutool.core.text.finder.PatternFinder;
import cn.hutool.core.text.finder.StrFinder;
import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import io.github.tmgg.code.migration.handler.JavaHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;

import java.util.List;


@Slf4j
@Component
public class JavaBaseControllerHandler extends JavaHandler {

    @Override
    public String process(List<String> lines) {
        for (String line : lines) {
            if(line.contains("extends BaseController<")){
                log.error(line);
                log.error("不应包含BaseController");

                String entityName = ReUtil.getGroup1("<([^<>]+)>", line);
                log.info("解析实体 {}",entityName);

                String entityNameLowerFirst = StrUtil.lowerFirst(entityName);
                log.info("小写开头 {}",entityNameLowerFirst);



                String code = """
                            @HasPermission("【替换小】:view")
                            @RequestMapping("page")
                            public AjaxResult page(【替换大】 request,@PageableDefault(direction = Sort.Direction.DESC, sort = "updateTime") Pageable pageable) throws Exception {
                                JpaQuery<【替换大】> q = new JpaQuery<>();
                                // 视情况修改
                                q.likeExample(request);
                                Page<【替换大】> page = service.pageByRequest(q, pageable);
                                return AjaxResult.ok().data(page);
                            }
                                            
                            @HasPermission("【替换小】:save")      
                            @PostMapping("save")
                            public AjaxResult save(@RequestBody 【替换大】 input, RequestBodyKeys updateFields) throws Exception {
                                service.saveOrUpdateByRequest(input, updateFields);
                                return AjaxResult.ok().msg("保存成功");
                            }
                                            
                            @HasPermission("【替换小】:delete")         
                            @RequestMapping("delete")
                            public AjaxResult delete(String id) {
                                service.deleteByRequest(id);
                                return AjaxResult.ok().msg("删除成功");
                            }
                        """;

                code = code.replace("【替换小】",entityNameLowerFirst);
                code = code.replace("【替换大】",entityName);

                log.info("1.删除 extends BaseController");
                log.info("2.增加如下内容\n{}",code);

                throw new IllegalStateException();
            }
        }


        return null;
    }
}
