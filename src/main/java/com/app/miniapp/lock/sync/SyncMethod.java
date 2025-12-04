package com.app.miniapp.lock.sync;

/**
 * <p>PURPOSE:
 * <p>DESCRIPTION: 同步方法
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/11/12
 * <p>UPDATE DATE: 2025/11/12
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
public class SyncMethod {

    private final Object lock = new Object();


    public void syncMethod1() {
        synchronized (lock) {
            System.out.println("syncMethod1获取锁");
            // 同步方法的代码逻辑
            try {
                lock.notifyAll();
                Thread.sleep(10000); // 模拟耗时操作
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("1、syncMethod");
        }
    }

    public void syncMethod2() {
        synchronized (lock) {
            // 同步方法的代码逻辑
            System.out.println("2、syncMethod");
        }
    }

    public void syncMethod3() {
        synchronized (lock) {
            // 同步方法的代码逻辑
            System.out.println("3、syncMethod");
        }
    }

    public void syncMethod4() {
        synchronized (lock) {
            // 同步方法的代码逻辑
            System.out.println("4、syncMethod");
        }
    }

    public void syncMethodThis() {
        synchronized (this) {
            // 同步方法的代码逻辑
            System.out.println("4、syncMethod");
        }
    }


    public static void main(String[] args) {
        SyncMethod syncMethod = new SyncMethod();
        // 初始化线程池 测试对象锁和实例锁
        for (int i = 0; i < 10; i++) {
            final int index = i;
            new Thread(() -> {
                switch (index % 5) {
                    case 0:
                        System.out.println("线程" + index + "调用syncMethod1");
                        syncMethod.syncMethod1();
                        break;
                    case 4:
                        System.out.println("线程" + index + "调用syncMethod4");
                        syncMethod.syncMethodThis();
                        break;
                }
            }).start();
        }

    }
}
