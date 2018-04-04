package com.javaear.koubeiblog.system.plugin.cache;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.javaear.generalmapper.GeneralMapper;
import com.javaear.koubeiblog.GlobalConstants;
import com.javaear.koubeiblog.system.entity.dto.CacheDTO;
import com.javaear.koubeiblog.system.entity.dto.SettingDTO;
import com.javaear.koubeiblog.system.entity.model.CategoryModel;
import com.javaear.koubeiblog.system.entity.model.ConfigModel;
import com.javaear.koubeiblog.system.entity.model.PermissionModel;
import com.javaear.koubeiblog.system.entity.model.RoleModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * 二级缓存上下文
 *
 * @author aooer
 */
@Component
public class EhCacheContext {

    @Autowired
    private EhCacheCacheManager cacheManager;

    @Autowired
    private GeneralMapper generalMapper;

    /**
     * 初始化缓存数据
     */
    @PostConstruct
    private void init() {
        CacheDTO cacheDTO = new CacheDTO();
        cacheDTO.setSettingDTO(JSON.parseObject(generalMapper.selectById(GlobalConstants.ConfigSettingId, ConfigModel.class).getContent(), SettingDTO.class));
        cacheDTO.setVersion(generalMapper.selectById(GlobalConstants.ConfigVersionId, ConfigModel.class).getContent());
        cacheDTO.setCategoryModelList(generalMapper.selectList(new EntityWrapper<>(new CategoryModel())));
        cacheDTO.setPermissionModelList(generalMapper.selectList(new EntityWrapper<>(new PermissionModel())));
        cacheDTO.setRoleModelList(generalMapper.selectList(new EntityWrapper<>(new RoleModel())));
        cacheManager.getCache(GlobalConstants.Global_Setting).put(GlobalConstants.CacheDTO, cacheDTO);
    }

    /**
     * 获取当前线程中的缓存数据
     */
    public CacheDTO getCacheDTO() {
        Cache.ValueWrapper cacheWrapper = cacheManager.getCache(GlobalConstants.Global_Setting).get(GlobalConstants.CacheDTO);
        // 如果chacheDto为空，初始化缓存数据
        if (cacheWrapper == null) {
            init();
            return (CacheDTO) cacheManager.getCache(GlobalConstants.Global_Setting).get(GlobalConstants.CacheDTO).get();
        }
        return (CacheDTO) cacheWrapper.get();
    }

    /**
     * 清空当前线程缓存数据
     */
    public void clearCacheDTO() {
        //获取线程局部变量，并设置为空
        cacheManager.getCache(GlobalConstants.Global_Setting).evict(GlobalConstants.CacheDTO);
    }

}
