package org.hmily.spring.dependency.lookup;

import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TypeSafeDependencyLookupDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 当前类 TypeSafeDependencyLookupDemo 作为配置类 （Configuration Class）
        applicationContext.register(TypeSafeDependencyLookupDemo.class);
        // 启动应用上下文
        applicationContext.refresh();
        // 演示 BeanFactory#getBean 方法的安全性
        displayBeanFactoryGetBean(applicationContext);
        // 演示 ObjectFactory#getBean 方法的安全性
        displayObjectFactoryGetObject(applicationContext);

        // 关闭应用上下文
        applicationContext.close();
    }

    public static void displayObjectFactoryGetObject(BeanFactory beanFactory){
        // ObjectProvider is ObjectFactory
        ObjectProvider<User> userObjectProvider = beanFactory.getBeanProvider(User.class);
        printBeansException("displayObjectFactoryGetObject",()-> userObjectProvider.getObject());
    }

    public static void displayBeanFactoryGetBean(BeanFactory beanFactory){
        printBeansException("displayBeanFactoryGetBean",() -> beanFactory.getBean(User.class));
    }

    public static void printBeansException(String source,Runnable runnable){
        System.err.println("Source from : " + source);
        try{
            runnable.run();
        }catch (BeansException e){
            e.printStackTrace();
        }
    }

}
