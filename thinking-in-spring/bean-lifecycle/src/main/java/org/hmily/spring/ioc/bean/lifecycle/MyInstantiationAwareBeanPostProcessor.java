package org.hmily.spring.ioc.bean.lifecycle;

import org.hmily.spring.ioc.overview.domain.SuperUser;
import org.hmily.spring.ioc.overview.domain.User;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;
import org.springframework.util.ObjectUtils;

public class MyInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {

    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass,String beanName) throws BeansException{
        if (ObjectUtils.nullSafeEquals("superUser",beanName)&& SuperUser.class.equals(beanClass)){
            // 把配置完成 superUser Bean 覆盖
            return new SuperUser();
        }
        return null;
    }

    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("user",beanName)&& User.class.equals(bean)){
            // "user" 对象不允许属性赋值（配置元信息 -> 属性值）
            User user = (User) bean;
            user.setId(2L);
            user.setName("hmily");
            return false;
        }
        return true;
    }


    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder",beanName)&& UserHolder.class.equals(bean)){
            MutablePropertyValues propertyValues = null;
            // 假设 <property name="number" value = "1" /> 配置的话，那么在 PropertyValues 就包含一个 PropertyValue(number=1)
            if (pvs instanceof MutablePropertyValues){
                propertyValues = (MutablePropertyValues) pvs;
            }else {
                propertyValues = new MutablePropertyValues();
            }
            // 等价于 <property name = "number" value = "1" />
            propertyValues.addPropertyValue("number","1");
            if (propertyValues.contains("description")){
                propertyValues.removePropertyValue("description");
                propertyValues.addPropertyValue("description","The user holder v2");
            }
            return propertyValues;
        }
        return null;
    }

    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder",beanName)&&UserHolder.class.equals(bean.getClass())){
            UserHolder userHolder = (UserHolder) bean;
            // UserHolder description = "The user holder V2"
            userHolder.setDescription("The user holder V3");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (ObjectUtils.nullSafeEquals("userHolder",beanName)&&UserHolder.class.equals(bean.getClass())){
            UserHolder userHolder = (UserHolder) bean;
            // init() = The user holder V6
            // UserHolder description = "The user holder V6"
            userHolder.setDescription("The user holder V7");
        }
        return bean;
    }

}
