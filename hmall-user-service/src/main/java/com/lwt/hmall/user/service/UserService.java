package com.lwt.hmall.user.service;

import com.lwt.hmall.api.bean.UmsUser;
import com.lwt.hmall.api.bean.UmsUserReceiveAddress;
import com.lwt.hmall.api.constant.RoleEnum;
import com.lwt.hmall.api.constant.UserStatusEnum;
import com.lwt.hmall.redis.cache.CacheFuzzyRemove;
import com.lwt.hmall.user.constant.CacheName;
import com.lwt.hmall.user.mapper.UmsUserMapper;
import com.lwt.hmall.user.mapper.UmsUserReceiveAddressMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author lwt
 * @Date 2020/2/3 16:21
 * @Description
 */
@Service
@CacheConfig(cacheNames = CacheName.CACHE_NAME)
public class UserService {

    @Autowired
    private UmsUserMapper umsUserMapper;
    @Autowired
    private UmsUserReceiveAddressMapper umsUserReceiveAddressMapper;

    /**
     * 获取用户信息
     * @param username
     * @param password
     * @return
     */
    @Cacheable
    public UmsUser getUserByUsernameAndPassword(String username,String password){
        UmsUser umsUserParam = new UmsUser();
        umsUserParam.setUsername(username);
        umsUserParam.setPassword(password);
        UmsUser result = umsUserMapper.selectOne(umsUserParam);
        return result;
    }

    /**
     * 获取用户信息
     * @param username
     * @return
     */
    @Cacheable
    public UmsUser getUserByUsername(String username){
        UmsUser umsUserParam = new UmsUser();
        umsUserParam.setUsername(username);
        UmsUser result = umsUserMapper.selectOne(umsUserParam);
        return result;
    }

    /**
     * 获取用户信息
     * @param userId
     * @return
     */
    @Cacheable
    public UmsUser getUserById(long userId){
        UmsUser umsUserParam = new UmsUser();
        umsUserParam.setId(userId);
        UmsUser result = umsUserMapper.selectOne(umsUserParam);
        return result;
    }

    /**
     * 添加用户
     * @param umsUser
     * @return
     */
    public boolean saveUser(UmsUser umsUser){
        umsUser.setRoleLevel((long) RoleEnum.USER.getRoleLevel());
        umsUser.setStatus((long) UserStatusEnum.ENABLE.getStatus());
        int i = umsUserMapper.insertSelective(umsUser);
        return i>0;
    }

    /**
     * 获取用户收货地址列表
     * @param userId
     * @return
     */
    @Cacheable
    public List<UmsUserReceiveAddress> listUserReceiveAddress(long userId){
        UmsUserReceiveAddress umsUserReceiveAddress = new UmsUserReceiveAddress();
        umsUserReceiveAddress.setUserId(userId);
        return umsUserReceiveAddressMapper.select(umsUserReceiveAddress);
    }

    /**
     * 获取用户及用户收货地址信息
     * @param userId
     * @return
     */
    @Cacheable
    public UmsUser getUserAndReceiveAddress(long userId){
        UmsUser umsUser = getUserById(userId);
        List<UmsUserReceiveAddress> umsUserReceiveAddresses = listUserReceiveAddress(userId);
        umsUser.setUserReceiveAddresses(umsUserReceiveAddresses);
        return umsUser;
    }

    /**
     * 获取收货地址消息
     * @param userId
     * @param receiveId
     * @return
     */
    @Cacheable
    public UmsUserReceiveAddress getUserReceiveAddressByUserIdAndReceiveId(long userId,long receiveId){
        UmsUserReceiveAddress umsUserReceiveAddressParam = new UmsUserReceiveAddress();
        umsUserReceiveAddressParam.setUserId(userId);
        umsUserReceiveAddressParam.setId(receiveId);
        UmsUserReceiveAddress umsUserReceiveAddress = umsUserReceiveAddressMapper.selectOne(umsUserReceiveAddressParam);
        return umsUserReceiveAddress;
    }

    /**
     * 保存用户收货地址
     * @param umsUserReceiveAddress
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+':get*'",
            "#targetName+':list*'"
    })
    @Transactional
    public boolean saveUserReceiveAddress(UmsUserReceiveAddress umsUserReceiveAddress){
        if (umsUserReceiveAddress.getDefaultStatus()==1){
            Example example = new Example(UmsUserReceiveAddress.class);
            example.createCriteria().andEqualTo("userId",umsUserReceiveAddress.getUserId());
            UmsUserReceiveAddress umsUserReceiveAddressParam = new UmsUserReceiveAddress();
            umsUserReceiveAddressParam.setDefaultStatus(0L);
            umsUserReceiveAddressMapper.updateByExampleSelective(umsUserReceiveAddressParam,example);
        }
        return umsUserReceiveAddressMapper.insertSelective(umsUserReceiveAddress)>0;
    }

    /**
     * 删除用户收货地址
     * @param receiveAddressId
     * @param userId
     * @return
     */
    @CacheFuzzyRemove(cacheName = CacheName.CACHE_NAME,value = {
            "#targetName+':get*'",
            "#targetName+':list*'"
    })
    public boolean removeUserReceiveAddressByIdAndUserId(long receiveAddressId,long userId){
        UmsUserReceiveAddress umsUserReceiveAddressParam = new UmsUserReceiveAddress();
        umsUserReceiveAddressParam.setId(receiveAddressId);
        umsUserReceiveAddressParam.setUserId(userId);
        return umsUserReceiveAddressMapper.delete(umsUserReceiveAddressParam)>0;
    }

}
