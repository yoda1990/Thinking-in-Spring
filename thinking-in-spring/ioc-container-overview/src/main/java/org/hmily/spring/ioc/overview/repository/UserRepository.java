package org.hmily.spring.ioc.overview.repository;

import org.hmily.spring.ioc.overview.domain.User;
import org.omg.CORBA.Object;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.ApplicationContext;

import java.util.Collection;

public class UserRepository {

    private Collection<User> users;

    private BeanFactory beanFactory;// 内建非Bean 对象 （依赖）

    private ObjectFactory<ApplicationContext> objectFactory;



    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public ObjectFactory<ApplicationContext> getObjectFactory() {
        return objectFactory;
    }

    public void setObjectFactory(ObjectFactory<ApplicationContext> objectFactory) {
        this.objectFactory = objectFactory;
    }

}
