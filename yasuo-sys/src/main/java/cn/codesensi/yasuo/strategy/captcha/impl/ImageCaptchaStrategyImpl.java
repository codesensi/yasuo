package cn.codesensi.yasuo.strategy.captcha.impl;

import cn.codesensi.yasuo.constants.CacheConst;
import cn.codesensi.yasuo.exception.SysException;
import cn.codesensi.yasuo.pojo.dto.CaptchaDTO;
import cn.codesensi.yasuo.pojo.vo.CaptchaVO;
import cn.codesensi.yasuo.properties.CaptchaProperties;
import cn.codesensi.yasuo.strategy.captcha.CaptchaStrategy;
import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.TimeUnit;

/**
 * 图形验证码策略实现类
 */
@RequiredArgsConstructor
@Slf4j
@Service("imageCaptchaStrategy")
public class ImageCaptchaStrategyImpl implements CaptchaStrategy {

    private final CaptchaProperties captchaProperties;
    private final StringRedisTemplate stringRedisTemplate;

    /**
     * 生成图形验证码
     */
    @Override
    public CaptchaVO captcha(CaptchaDTO captchaDTO) {
        CaptchaProperties.ImageType imageType = captchaProperties.getImageType();
        String name = imageType.name();
        // 构建类名
        name = name.toLowerCase();
        name = StrUtil.upperFirst(name);
        String className = name + "Captcha";
        CaptchaVO captchaVO = new CaptchaVO();
        try {
            Class<?> clazz = Class.forName("com.wf.captcha." + className);
            Captcha captcha = (Captcha) clazz.getDeclaredConstructor().newInstance();
            // 算术验证码
            if (captcha instanceof ArithmeticCaptcha) {
                String arithmeticString = ((ArithmeticCaptcha) captcha).getArithmeticString();
                log.info("算术验证码运算公式：{}", arithmeticString);
            }
            // 验证码结果
            String key = "image:" + UUID.fastUUID().toString(true);
            String text = captcha.text();
            log.info("图形验证码唯一标识：{}，验证码内容：{}", key, text);
            // 放入缓存
            stringRedisTemplate.opsForValue().set(key, text, CacheConst.EXPIRE_5_MINUTES, TimeUnit.MINUTES);
            // 返回结果
            captchaVO.setKey(key);
            captchaVO.setResult(captcha.toBase64());
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            log.error("图形验证码类型错误：{}", name);
            throw new SysException("图形验证码生成失败");
        }
        return captchaVO;
    }

}
