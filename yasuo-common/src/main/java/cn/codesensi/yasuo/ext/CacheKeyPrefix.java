package cn.codesensi.yasuo.ext;

import cn.codesensi.yasuo.util.SpringUtil;
import org.springframework.stereotype.Component;

/**
 * 缓存key前缀
 */
@Component
public class CacheKeyPrefix {

    /**
     * 获取缓存key的前缀
     *
     * @return 缓存key的前缀
     */
    public String getCacheKeyPrefix() {
        String applicationName = SpringUtil.getApplicationName();
        String activeProfile = SpringUtil.getActiveProfile();
        return applicationName.concat("_").concat(activeProfile).concat(":");
    }
}
