package org.hmily.spring.ioc.dependency.source;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.PostConstruct;

public class ResolvableDependencySourceDemo {

    @Autowired
    private String value;


    @PostConstruct
    public void init(){
        System.out.println(value);
    }



    public static void main(String[] args) {
        // 创建 BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(ResolvableDependencySourceDemo.class);

       // AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();

//        if (beanFactory instanceof ConfigurableListableBeanFactory){
//            ConfigurableListableBeanFactory configurableListableBeanFactory = ConfigurableListableBeanFactory.class.cast(beanFactory);
//            // 注册 Resolvable Denpendency
//            configurableListableBeanFactory.registerResolvableDependency(String.class,"Hello,World");
//        }

        applicationContext.addBeanFactoryPostProcessor(beanFactory -> {
            beanFactory.registerResolvableDependency(String.class,"Hello,World!");
        });


        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 关闭 Spring 应用上下文
        applicationContext.close();

    }

}
