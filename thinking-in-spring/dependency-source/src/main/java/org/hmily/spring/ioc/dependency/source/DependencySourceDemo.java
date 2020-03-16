package org.hmily.spring.ioc.dependency.source;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.annotation.PostConstruct;

public class DependencySourceDemo {

    // 注入在 postProcessProperties 方法执行，早于 setter 注入，也早于 @PostConstruct
    @Autowired
    private BeanFactory beanFactory;

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    @PostConstruct
    public void initByInjection(){
        System.out.println(" beanFactory == applicationContext " + ( beanFactory == applicationContext ));
        System.out.println(" beanFactory == applicationContext.getBeanFactory() " + ( beanFactory == applicationContext.getAutowireCapableBeanFactory() ));
        System.out.println(" resourceLoader == applicationContext " + ( resourceLoader == applicationContext ));
        System.out.println(" ApplicationEventPublisher == applicationContext " + ( applicationEventPublisher == applicationContext ));
    }

    @PostConstruct
    public void initByLookup(){
        getBean(BeanFactory.class);
        getBean(ApplicationContext.class);
        getBean(ResourceLoader.class);
        getBean(ApplicationEventPublisher.class);
    }

    private <T> T getBean(Class<T> beanType){
        try{
            return beanFactory.getBean(beanType);
        }catch (NoSuchBeanDefinitionException e){
            System.err.println("当前类型" + beanType.getName() +" 无法在 BeanFactory 中查找！");
        }
        return null;
    }

    public static void main(String[] args) {

        // 创建 BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(DependencySourceDemo.class);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 DependencySourceDemo Bean
        DependencySourceDemo demo = applicationContext.getBean(DependencySourceDemo.class);


        // 关闭 Spring 应用上下文
        applicationContext.close();

    }

}
