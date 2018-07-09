package com.tengxvincent.transaction.SpringTransaction.service.proxy;

/**
 * @Auther:tengxiao
 * @Description:
 * @Data:Creaded in 17:12 2018/7/9
 */
public class TestServiceImpl implements TestService {
    @Override
    public void test() {
        System.out.println("test:"+this.getClass().getName());
        this.test1();
        System.out.println("---------test----------");
    }

    @Override
    public void test1() {
        System.out.println("---------test1----------");
    }
}
