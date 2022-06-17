package com.cj.modular.service.impl;

import com.cj.modular.entity.Movies;
import com.cj.modular.mapper.MoviesMapper;
import com.cj.modular.service.MoviesService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Caoj
 * @since 2022-01-16
 */
@Service
public class MoviesServiceImpl extends ServiceImpl<MoviesMapper, Movies> implements MoviesService {

}
