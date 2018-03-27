package cn.hzstk.securities.util.shiroehcache;

import org.apache.commons.io.IOUtils;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.config.ConfigurationException;
import org.apache.shiro.io.ResourceUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class EhCacheSelf extends EhCacheManager {

    @Override
    protected InputStream getCacheManagerConfigFileInputStream() {
        String configFile = this.getCacheManagerConfigFile();

        InputStream inputStream = null;
        try {
            inputStream = ResourceUtils.getInputStreamForPath(configFile); //原始的输入流
            byte[] b = IOUtils.toByteArray(inputStream);//使用字节数组保存流,实现将流保存到内存中.
            return new ByteArrayInputStream(b);
        } catch (IOException e) {
            throw new ConfigurationException("Unable to obtain input stream for cacheManagerConfigFile [" + configFile + "]", e);
        } finally {
            IOUtils.closeQuietly(inputStream);//关闭打开文件的原始输入流.
        }
    }
}