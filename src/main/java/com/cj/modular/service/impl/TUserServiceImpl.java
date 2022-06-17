package com.cj.modular.service.impl;

import com.cj.modular.entity.TUser;
import com.cj.modular.mapper.TUserMapper;
import com.cj.modular.service.TUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author caoj
 * @since 2021-08-30
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements TUserService {

}
