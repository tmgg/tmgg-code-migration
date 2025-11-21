package io.github.tmgg.code.migration.handler.impl;

import io.github.tmgg.code.migration.handler.JavaHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
public class JavaBaseControllerHandler extends JavaHandler {

    @Override
    public String process(List<String> lines) {
        for (String line : lines) {
            if(line.contains("BaseController")){
                log.error("不应包含BaseController,请适用如下代码替换");

                String code = """
                        @HasPermission("【替换】:view")
                        @RequestMapping("page")
                        public AjaxResult page(   @PageableDefault(direction = Sort.Direction.DESC, sort = "updateTime") Pageable pageable) throws Exception {
                            JpaQuery<T> q = new JpaQuery<>();
                                        
                                        
                            Page<T> page = service.pageByRequest(q, pageable);
                                        
                                        
                                        
                            return AjaxResult.ok().data(page);
                        }
                                        
                        @HasPermission("【替换】:save")      
                        @PostMapping("save")
                        public AjaxResult save(@RequestBody T input, RequestBodyKeys updateFields) throws Exception {
                            service.saveOrUpdateByRequest(input, updateFields);
                            return AjaxResult.ok().msg("保存成功");
                        }
                                        
                        @HasPermission("【替换】:delete")         
                        @RequestMapping("delete")
                        public AjaxResult delete(String id) {
                            service.deleteByRequest(id);
                            return AjaxResult.ok().msg("删除成功");
                        }
                    """;

                System.out.println( code);

                throw new IllegalStateException();
            }
        }


        return null;
    }
}
