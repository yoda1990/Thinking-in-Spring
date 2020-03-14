package org.hmily.spring.ioc.dependency.injection;

import org.hmily.spring.ioc.dependency.injection.annotation.InjectedUser;
import org.hmily.spring.ioc.dependency.injection.annotation.MyAutowired;
import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.springframework.context.annotation.AnnotationConfigUtils.AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME;

public class AnnotationDependencyInjectionResolutionDemo {

    @Autowired
    @Lazy
    private User lazyUser;

    // 依赖查找（处理）
    // DependencyDesccriptor ->
    // 必须（required = true）
    // 实时注入（eager = true）
    // 通过类型（User.class）
    // 字段名称（"user"）
    // 是否首要 （primary = true）
    @Autowired
    private User user;

    // 集合类型依赖注入
    @Autowired
    private Map<String,User> users;

    // superUser
    @MyAutowired
    private Optional<User> userOptional;

    @Inject
    private User injectUser;

    @InjectedUser
    private User injectedUser;

    @Bean(name = AUTOWIRED_ANNOTATION_PROCESSOR_BEAN_NAME)
    public static AutowiredAnnotationBeanPostProcessor beanPostProcessor(){
        AutowiredAnnotationBeanPostProcessor beanPostProcessor = new AutowiredAnnotationBeanPostProcessor();
        // 替换原有注解处理，使用新注解 @InjectedUser
        //  @Autowired + @Inject + @InjectedUser
        Set<Class<? extends Annotation>> autowiredAnnotationTypes = new LinkedHashSet<>(asList(Autowired.class,Inject.class,InjectedUser.class));
        beanPostProcessor.setAutowiredAnnotationTypes(autowiredAnnotationTypes);
        return beanPostProcessor;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(AnnotationDependencyInjectionResolutionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找 AnnotationDependencyFieldInjectionDemo bean
        AnnotationDependencyInjectionResolutionDemo demo = applicationContext.getBean(AnnotationDependencyInjectionResolutionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        System.out.println("demo.injectedUser = " + demo.injectUser);
        // 期待输出 user superUser Bean
        System.out.println("demo.users = " + demo.users);

        // 期待输出 superUser Bean
        System.out.println("demo.userOptional = " + demo.userOptional);

        System.out.println("demo.lazyUser = " + demo.lazyUser);

        System.out.println("demo.myInjectedUser = " + demo.injectedUser);
        // 关闭 Spring 应用上下文
        applicationContext.close();

    }

    @Bean
    public UserHolder userHolder(User user){
        return new UserHolder(user);
    }
}
