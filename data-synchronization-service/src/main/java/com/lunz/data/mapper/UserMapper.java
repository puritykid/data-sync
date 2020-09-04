package com.lunz.data.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lunz.data.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper  extends BaseMapper<User> {

}
