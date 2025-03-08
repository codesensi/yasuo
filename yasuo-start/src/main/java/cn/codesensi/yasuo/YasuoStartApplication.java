package cn.codesensi.yasuo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@EnableCaching
@MapperScan("cn.codesensi.yasuo.**.mapper")
@SpringBootApplication
public class YasuoStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(YasuoStartApplication.class, args);
    }

}
