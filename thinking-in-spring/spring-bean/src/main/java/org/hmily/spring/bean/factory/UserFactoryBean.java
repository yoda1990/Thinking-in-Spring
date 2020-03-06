package org.hmily.spring.bean.factory;

import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.cglib.proxy.Factory;

public class UserFactoryBean implements FactoryBean {


    @Override
    public Object getObject() throws Exception {
        return User.createUser();
    }

    @Override
    public Class<?> getObjectType() {
        return User.class;
    }
}
