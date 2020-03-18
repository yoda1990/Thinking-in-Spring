package org.hmily.spring.ioc.bean.scope;


import org.omg.CORBA.PRIVATE_MEMBER;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;

public class ThreadLocalScope implements Scope {

    public static final String SCOPE_NAME = "THREAD-LOCAL";

    private final NamedThreadLocal<Map<String,Object>> threadLocal = new NamedThreadLocal<Map<String,Object>>("THREAD-LOCAL-SCOPE"){
        public Map<String,Object> initialValue(){
            return new HashMap<>();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {

        // 非空
        Map<String,Object> context = getContext();
        Object object = context.get(name);

        if (object == null){
            object = objectFactory.getObject();
            context.put(name,object);
        }

        return object;
    }

    @Override
    public Object remove(String name) {
        Map<String,Object> context = getContext();
        return context.remove(name);
    }

    private Map<String,Object> getContext(){
        return threadLocal.get();
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        Map<String,Object> context = getContext();
        return context.get(key);
    }

    @Override
    public String getConversationId() {
        Thread thread = Thread.currentThread();
        return String.valueOf(thread.getId());
    }
}
