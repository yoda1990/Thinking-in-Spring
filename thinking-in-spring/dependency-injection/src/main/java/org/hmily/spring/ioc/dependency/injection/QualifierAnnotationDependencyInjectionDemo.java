package org.hmily.spring.ioc.dependency.injection;

import org.hmily.spring.ioc.dependency.injection.annotation.UserGroup;
import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;

import javax.annotation.Resource;
import java.util.Collection;

public class QualifierAnnotationDependencyInjectionDemo {

    @Autowired
    private User user; // superUser -> primary = true

    @Autowired
    @Qualifier("user")//指定 Bean 名称或 ID
    private User namedUser;

    // 整体应用上下文存在 4 个 User 类型的 Bean
    // superUser
    // user
    // user1 -> @Qualifier
    // user2 -> @Qualifier

    @Autowired
    private Collection<User> allUsers;// 4 个 Bean

    @Autowired
    private Collection<User> qualifiedUsers;// 2 Beans = user1 + user2

    @Autowired
    private Collection<User> groupedUsers;// 2 Beans = user1 + user2

    @Bean
    @Qualifier // 进行逻辑分组
    public User user1(){
        return createUser(7L);
    }

    @Bean
    @Qualifier // 进行逻辑分组
    public User user2(){
        return createUser(8L);
    }

    @Bean
    @UserGroup
    public User user3(){
        return createUser(9L);
    }

    @Bean
    @UserGroup
    public User user4(){
        return createUser(10L);
    }

    private static User createUser(Long id){
        User user = new User();
        user.setId(id);
        return user;
    }

    public static void main(String[] args) {
        // 创建 BeanFactory
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class （配置类）
        applicationContext.register(QualifierAnnotationDependencyInjectionDemo.class);
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);
        String xmlResourcePath = "classpath:/META-INF/dependency-lookup-context.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();

        // 依赖查找并且创建 Bean
        // 依赖查找 AnnotationDependencyFieldInjectionDemo bean
        QualifierAnnotationDependencyInjectionDemo demo = applicationContext.getBean(QualifierAnnotationDependencyInjectionDemo.class);

        // 期待输出 superUser Bean
        System.out.println("demo.user = " + demo.user);
        // 期待输出 user Bean
        System.out.println("demo.nameUser = " + demo.namedUser);
        // 期待输出 superUser user user1 user2
        System.out.println("demo.allUsers = " + demo.allUsers);
        // 期待输出 user1 user2
        System.out.println("demo.qualifiedUsers = " + demo.qualifiedUsers);

        // 期待输出 user3 user4
        System.out.println("demo.groupedUsers = " + demo.groupedUsers);

        // 关闭 Spring 应用上下文
        applicationContext.close();

    }

    @Bean
    public UserHolder userHolder(User user){
        return new UserHolder(user);
    }
}
