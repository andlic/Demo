package com.nizouba.service.required_new.impl;

import com.nizouba.domain.po.TxUser;
import com.nizouba.mapper.TxUserMapper;
import com.nizouba.service.UserService1;
import com.nizouba.service.UserService2;
import com.nizouba.service.UserService3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by zwxbest on 2018/8/11.
 */
@Profile(value = "required_new")
@Transactional(propagation = Propagation.REQUIRED)
@Service
public class UserService1Impl implements UserService1 {

    @Autowired
    private TxUserMapper txUserMapper;

    @Autowired
    private UserService2 userService2;

    @Autowired
    private UserService3 userService3;


    public void insert(TxUser user)
    {
        txUserMapper.insert(user);
    }

    @Override
    public void insertException(TxUser user) {
        txUserMapper.insert(user);
        throw new RuntimeException("UserService1的insertException（）抛出异常");
    }

       @Override
    public void sameClassInsertInInsertWithInsideException(TxUser user) {
        txUserMapper.insert(user);
        System.out.println("小丽的妹妹来了");
        insertException(new TxUser("小丽的妹妹"));

    }
    @Override
    public void sameClassInsertInInsertWithOutterException(TxUser user) {
        txUserMapper.insert(user);
        System.out.println("小丽的妹妹来了");
        insert(new TxUser("小丽的妹妹"));
        throw new RuntimeException("UserService1的外层异常");

    }

    @Override
    public void otherClassInsertInInsertWithInnnerException(TxUser user) {
        txUserMapper.insert(user);
        System.out.println("小丽的姐姐来了");
        userService3.insert(new TxUser("小丽的姐姐"));
        System.out.println("小丽的妹妹来了");
        userService2.insertException(new TxUser("小丽的妹妹"));
//
//        try {
//            userService2.insertException(new TxUser("小丽的妹妹"));
//        }
//        catch (Exception e)
//        {
//            System.out.println("小丽的妹妹抛出异常");
//        }
    }

    @Override
    public void otherClassInsertInInsertWithOutterException(TxUser user) {
        txUserMapper.insert(user);

        System.out.println("小丽的姐姐来了");
        userService3.insert(new TxUser("小丽的姐姐"));
        System.out.println("小丽的妹妹来了");
        userService2.insert(new TxUser("小丽的妹妹"));
        throw new RuntimeException("UserService1的外层异常");
    }

}
