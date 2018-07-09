package com.tengxvincent.transaction.SpringTransaction.service.impl;

import com.tengxvincent.transaction.SpringTransaction.dao.CityDao;
import com.tengxvincent.transaction.SpringTransaction.domain.City;
import com.tengxvincent.transaction.SpringTransaction.service.CityService;
import javafx.application.Application;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;

/**
 * 城市业务逻辑实现类
 *
 * Created by bysocket on 07/02/2017.
 */
@Service
public class CityServiceImpl implements CityService {

    private final static Logger LOGGER = LoggerFactory.getLogger(CityServiceImpl.class);

    @Autowired
    private CityDao cityDao;

    /**
     * 解决方案2：
     *通过spring应用上下文 获取 代理对象
     * ApplicationContext 在ioc容器中是单例的
     */

    private CityServiceImpl proxy;
    @Autowired
    private ApplicationContext context;
    @PostConstruct
    public  void init(){
        proxy=context.getBean(CityServiceImpl.class);
    }


    /**
     *需求：
     * 1：parent()是核心方法，在执行Parent业务之前需要执行Child子业务
     * 2:child方法是一个不重要的业务方法，child方法无论是否抛出异常，对parent的执行都不能有影响
     *
     *
     * 使用try、catch 自己处理不去而抛出异常
     * 预测结果：
     * 1：child 失败
     * 2：parent 成功
     *实际结果：
     * 1：child 成功
     * 2：parent 成功
     *    这种情况child事务失效  【原因：动态代理】
     *
     *    事物的执行者：AopProxy  aop的代理类  通过AopProxy调用parent(）方法
     *    spring的事务是基于AOP的 通过aop 的环绕增强在 源代码执行之后 对数据进行commit或rollback
     *
     * 这种情况child事务失效
     * 根据动态代理分析 此处的child()不是由 AopProxy调用的 而是 this对象
     *
     * 解决方案 此处的child方法需是动态代理对象调用的 事务才不会失效
     * funcation 1: 从当前线程的AopContext中获取
     * CityServiceImpl proxy=(CityServiceImpl)AopContext.currentProxy();
     * proxy.child()
     *
     * funcation 2： 通过spring应用上下文 获取 代理对象
     * ApplicationContext 在ioc容器中是单例的
     */
    @Override
    @Transactional
    public void parent() {
        LOGGER.info("======================insertParent()===================");

        //这种情况child事务失效
        //根据动态代理分析 此处的child()不是由 AopProxy调用的 而是 this对象
        try {
            //com.tengxvincent.transaction.SpringTransaction.service.impl.CityServiceImpl
            System.out.println("child invoked object :"+this.getClass().getName());
            //child();

            /**
             * 解决方案 此处的child方法需是动态代理对象调用的 事务才不会失效
             * funcation 1: 从当前线程的AopContext中获取
             */
            //CityServiceImpl proxy=(CityServiceImpl)AopContext.currentProxy();
            proxy.child();

        }catch (Exception e){
            LOGGER.error("parent catch child execption ",e);
        }

        //以下代码为Parent业务
        City city= new City();
        city.setProvinceId(Long.valueOf(99));
        city.setCityName("parent");
        city.setDescription("parentparentparentparent");
        cityDao.insertCity(city);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void child() {
        LOGGER.info("======================insertChild()===================");
        City city= new City();
        city.setProvinceId(Long.valueOf(99));
        city.setCityName("Child");
        city.setDescription("ChildChildChildChildChildChild");
        cityDao.insertCity(city);
        int a=1/0;//此处异常
    }

}
