package org.hmily.spring.bean.definition;


import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * 注解 BeanDefinition 示例
 */

// 3.通过 @Import 来进行导入
@Import(AnnotationBeanDefinitionDemo.Config.class)
public class AnnotationBeanDefinitionDemo {

    public static void main(String[] args) {
        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class 配合类
        applicationContext.register(AnnotationBeanDefinitionDemo.class);
        //applicationContext.register(Config.class);

        // 通过 BeanDefinition 注册 API 实现
        // 1.命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext,"hmily_user");
        // 2.非命名 Bean 的注册方式
        registerUserBeanDefinition(applicationContext);

        // 启动 Spring 应用上下文
        applicationContext.refresh();
        // 按照类型查找
        System.out.println("Config 类型的所有 Beans " + applicationContext.getBeansOfType(Config.class));
        System.out.println("User 类型的所有 Beans " + applicationContext.getBeansOfType(User.class));
        // 显示地关闭 Spring 应用上下文
        applicationContext.close();

    }

    // 2.定义当前类作为 Spring Bean （组件）
    @Component
    public static class Config{

        /**
         *  通过 Java 注解的方式，定义了一个 Bean
         */
        //  1.通过 @Bean 方式定义
        @Bean
        public User user(){
            User user = new User();
            user.setId(1l);
            user.setName("hmily");
            return user;
        }
    }


    /**
     * 命名 Bean 的注册方式
     * @param beanDefinitionRegistry
     * @param beanName
     */
    public static void registerUserBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry,String beanName){
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder.addPropertyValue("id",1).addPropertyValue("name","hmily");

        // 判断 如果 beanName 参数存在时
        if (StringUtils.hasText(beanName)){
            // 注册 BeanDefinition
            beanDefinitionRegistry.registerBeanDefinition(beanName,beanDefinitionBuilder.getBeanDefinition());
        }else{
            // 非命名 Bean 注册方法
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(),beanDefinitionRegistry);
        }
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry beanDefinitionRegistry){
        registerUserBeanDefinition(beanDefinitionRegistry,null);
    }



    private static BeanDefinitionBuilder genericBeanDefinition(Class<User> userClass) {
        // 通过 BeanDefinitionBuilder 构建
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(userClass);
        return beanDefinitionBuilder;
    }


}
