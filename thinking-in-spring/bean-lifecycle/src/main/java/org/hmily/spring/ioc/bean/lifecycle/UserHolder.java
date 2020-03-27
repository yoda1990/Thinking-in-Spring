package org.hmily.spring.ioc.bean.lifecycle;

import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.*;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

public class UserHolder implements BeanNameAware, BeanClassLoaderAware, BeanFactoryAware ,
        EnvironmentAware, InitializingBean ,SmartInitializingSingleton,DisposableBean{

    private final User user;

    private Integer number;

    private String description;

    public UserHolder(User user) {
        this.user = user;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "UserHolder{" +
                "user=" + user +
                ", number=" + number +
                ", description='" + description + '\'' +
                '}';
    }

    private ClassLoader classLoader;

    private BeanFactory beanFactory;

    private String beanName;

    private Environment environment;

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
       this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        this.beanName = beanName;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }


    @PostConstruct
    public void initPostConstruct(){
        // postProcessorBeforeInitialization V3 -> initPostConstruct V4
        this.description = "The user holder V4";
        System.out.println("initPostConstruct() = " +description);
    }

    @Override
    public void afterPropertiesSet(){
        // initPostConstruct V4 -> afterPropertiesSet V5
        this.description = "The user holder V5";
        System.out.println("afterPropertiesSet() = " +description);
    }

    public void init(){
        // afterPropertiesSet V5 -> init V6
        this.description = "The user holder V6";
        System.out.println("init() = " + description );
    }

    @Override
    public void afterSingletonsInstantiated() {
        // postProcessAfterInitialization V7 -> afterSingletonsInstantiated V8
        this.description = "The user holder V8";
        System.out.println("afterSingletonsInstantiated() = " + description);
    }

    @PreDestroy
    public void preDestory(){
        // postProcessBeforeDestruction V9 -> preDestory V10
        this.description = "The user holder V10";
        System.out.println("preDestory() = " + description);
    }

    @Override
    public void destroy() throws Exception {
        // preDestory V10 -> destroy V11
        this.description = "The user holder V11";
        System.out.println("destroy() = " + description);
    }

    public void doDestroy(){
        // destroy V11 -> doDestroy V12
        this.description = "The user holder V12";
        System.out.println("doDestroy() = " + description);
    }
}
