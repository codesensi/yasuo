package cn.codesensi.yasuo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;

@EnableCaching
@MapperScan("cn.codesensi.yasuo.**.mapper")
@SpringBootApplication
public class YasuoStartApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(YasuoStartApplication.class, args);

        // 从 ApplicationContext 中获取 Environment 对象
        Environment env = context.getEnvironment();

        // 获取配置属性
        String serverPort = env.getProperty("server.port");
        String version = env.getProperty("yasuo.version");

        // 打印配置信息
        System.out.println("""
                 _  _
                | || |  __ _   ___   _  _   ___
                 \\_, | / _` | (_-<  | +| | / _ \\
                _|__/  \\__,_| /__/_  \\_,_| \\___/""");
        System.out.println("http://localhost:" + serverPort + "/doc.html (v" + version + ")");
    }

}
