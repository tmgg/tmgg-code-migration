package io.github.tmgg.code.migration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DataRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("根据原始表生成菜单配置");
    }
}
