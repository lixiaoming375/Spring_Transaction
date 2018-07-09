package com.tengxvincent.transaction.SpringTransaction.service.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Auther:tengxiao
 * @Description:
 * @Data:Creaded in 17:14 2018/7/9
 */
public class MyHander implements InvocationHandler {

    private Object target;//代理对象目标

    public MyHander(Object target){
        this.target=target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //处理额外的事情
        if(method.getName().startsWith("test")){
           System.out.println("加入额外的业务处理。。。。。。。。");
        }

        return method.invoke(target,args);//调用真实对象的方法
    }

    public static void main(String[] args){
        MyHander hander=new MyHander(new TestServiceImpl());

        TestService  proxy=(TestService)Proxy.newProxyInstance(hander.getClass().getClassLoader(),new Class[]{TestService.class},hander);

        System.out.println("proxy: "+proxy.getClass().getName());
        //只有被代理对象之间调用的方法才能被代理
        proxy.test();

        //proxy.test1();

    }
}
