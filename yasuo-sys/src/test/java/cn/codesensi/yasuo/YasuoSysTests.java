package cn.codesensi.yasuo;

import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.digest.BCrypt;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@Slf4j
@SpringBootTest(classes = YasuoSysTests.class)
class YasuoSysTests {

    @Test
    void contextLoads() {
    }

    /**
     * 生成密码
     */
    @Test
    public void getPassword() {
        String inputPw = "admin@000";
        // BCrypt加密生成密码
        String pw = BCrypt.hashpw(inputPw, BCrypt.gensalt());
        log.info("生成的密码：{}", pw);
        // 使用checkpw方法检查被加密的字符串是否与原始字符串匹配
        boolean result = BCrypt.checkpw(inputPw, pw);
        log.info("匹配结果：{}", result);
    }

    /**
     * 生成雪花id
     */
    @Test
    public void getId() {
        // 生成id
        long id = IdUtil.getSnowflakeNextId();
        log.info("生成的id：{}", id);
    }

}
