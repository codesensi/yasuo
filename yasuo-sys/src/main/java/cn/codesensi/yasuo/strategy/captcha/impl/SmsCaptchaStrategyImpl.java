package cn.codesensi.yasuo.strategy.captcha.impl;

import cn.codesensi.yasuo.constants.CacheConst;
import cn.codesensi.yasuo.exception.SysException;
import cn.codesensi.yasuo.ext.CacheKeyPrefix;
import cn.codesensi.yasuo.pojo.dto.CaptchaDTO;
import cn.codesensi.yasuo.pojo.vo.CaptchaVO;
import cn.codesensi.yasuo.strategy.captcha.CaptchaStrategy;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 短信验证码策略实现类
 */
@RequiredArgsConstructor
@Slf4j
@Service("smsCaptchaStrategy")
public class SmsCaptchaStrategyImpl implements CaptchaStrategy {

    private final StringRedisTemplate stringRedisTemplate;
    private final CacheKeyPrefix cacheKeyPrefix;

    /**
     * 生成短信验证码
     */
    @Override
    public CaptchaVO captcha(CaptchaDTO captchaDTO) {
        String phone = captchaDTO.getPhone();
        if (StrUtil.isBlank(phone)) {
            throw new SysException("手机号不能为空");
        }
        // 生成验证码
        String key = cacheKeyPrefix.getCacheKeyPrefix() + "sms:" + phone;
        String result = RandomUtil.randomNumbers(6);
        log.info("短信验证码手机号：{}，验证码内容：{}", phone, result);

        // TODO 发短信

        // 放入缓存
        stringRedisTemplate.opsForValue().set(key, result, CacheConst.EXPIRE_5_MINUTES, TimeUnit.MINUTES);
        // 返回结果
        CaptchaVO captchaVO = new CaptchaVO();
        captchaVO.setKey(key);
        captchaVO.setResult(result);
        return captchaVO;
    }
}
