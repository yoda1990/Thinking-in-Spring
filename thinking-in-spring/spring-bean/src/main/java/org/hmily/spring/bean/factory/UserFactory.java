package org.hmily.spring.bean.factory;

import org.hmily.spring.ioc.overview.domain.User;


/**
 *  工厂类
 */
public interface UserFactory {


    default User createUser(){
        return User.createUser();
    }

}
