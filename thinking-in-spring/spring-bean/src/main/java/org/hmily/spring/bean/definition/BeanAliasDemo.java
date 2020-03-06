package org.hmily.spring.bean.definition;

import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanAliasDemo {

    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/bean-definitions-context.xml");

        // 通过 user1 获取曾用 user 的 bean
        User user = beanFactory.getBean("user",User.class);
        User user1 = beanFactory.getBean("user1",User.class);
        System.out.println("user1 是否与 user Bean 相同 " + (user == user1));

    }

}
