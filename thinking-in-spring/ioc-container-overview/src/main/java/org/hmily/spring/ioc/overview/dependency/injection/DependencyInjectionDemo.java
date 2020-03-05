package org.hmily.spring.ioc.overview.dependency.injection;


import org.hmily.spring.ioc.overview.repository.UserRepository;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.env.Environment;

public class DependencyInjectionDemo {


    public static void main(String[] args) {
        // 配置 XML 配置文件
        // 启动 Spring 应用上下文
        BeanFactory beanFactory = new ClassPathXmlApplicationContext("classpath:/META-INF/dependency-injection-context.xml");

        // 自定义 Bean
        UserRepository userRepository  = beanFactory.getBean("userRepository",UserRepository.class);

        // System.out.println(userRepository.getUsers());

        // 依赖注入（内建依赖）
        System.out.println(userRepository.getBeanFactory());
        // System.out.println(userRepository.getBeanFactory()==beanFactory);

        // 依赖查找（错误）
        //System.out.println(beanFactory.getBean(BeanFactory.class));

        //ObjectFactory objectFactory = userRepository.getUserObjectFactory();
        //System.out.println(objectFactory.getObject());

        // 内建容器 Bean
        Environment environment = beanFactory.getBean(Environment.class);
        System.out.println(environment);

    }


}
