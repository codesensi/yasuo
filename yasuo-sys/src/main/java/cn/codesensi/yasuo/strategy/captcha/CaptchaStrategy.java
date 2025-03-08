package cn.codesensi.yasuo.strategy.captcha;

import cn.codesensi.yasuo.pojo.dto.CaptchaDTO;
import cn.codesensi.yasuo.pojo.vo.CaptchaVO;

/**
 * 验证码策略接口类
 */
public interface CaptchaStrategy {

    /**
     * 生成验证码
     */
    CaptchaVO captcha(CaptchaDTO captchaDTO);
}
