package com.app.miniapp.designmode.observe;
// ============ 具体实现 ============

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
/**
 * <p>PURPOSE:
 * <p>DESCRIPTION:
 * <p>CALLED BY: wanjian
 * <p>CREATE DATE: 2025/12/4
 * <p>UPDATE DATE: 2025/12/4
 * <p>UPDATE USER:
 * <p>HISTORY: 1.0
 *
 * @author wanjian
 * @version 1.0
 * @see
 * @since java 1.8
 */
// ============ 观察者模式核心接口 ============

// 观察者接口（支持泛型）
interface Observer<T> {
    void update(T event);
}

// 主题接口
interface Subject<T> {
    void attach(Observer<T> observer);
    void detach(Observer<T> observer);
    void notifyObservers(T event);
}



// 订单事件
class OrderEvent {
    private String orderId;
    private String status;
    private double amount;

    public OrderEvent(String orderId, String status, double amount) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
    }

    public String getOrderId() { return orderId; }
    public String getStatus() { return status; }
    public double getAmount() { return amount; }

    @Override
    public String toString() {
        return String.format("订单[%s] 状态:%s 金额:%.2f", orderId, status, amount);
    }
}

// 订单主题（被观察者）
class OrderSubject implements Subject<OrderEvent> {
    // 使用线程安全的集合
    private final List<Observer<OrderEvent>> observers = new CopyOnWriteArrayList<>();

    @Override
    public void attach(Observer<OrderEvent> observer) {
        observers.add(observer);
        System.out.println("注册观察者: " + observer.getClass().getSimpleName());
    }

    @Override
    public void detach(Observer<OrderEvent> observer) {
        observers.remove(observer);
        System.out.println("移除观察者: " + observer.getClass().getSimpleName());
    }

    @Override
    public void notifyObservers(OrderEvent event) {
        System.out.println("\n>>> 事件发生: " + event);
        for (Observer<OrderEvent> observer : observers) {
            observer.update(event);
        }
    }

    // 业务方法：创建订单
    public void createOrder(String orderId, double amount) {
        OrderEvent event = new OrderEvent(orderId, "已创建", amount);
        notifyObservers(event);
    }

    // 业务方法：支付订单
    public void payOrder(String orderId, double amount) {
        OrderEvent event = new OrderEvent(orderId, "已支付", amount);
        notifyObservers(event);
    }
}

// ============ 具体观察者 ============

// 邮件通知观察者
class EmailNotifier implements Observer<OrderEvent> {
    @Override
    public void update(OrderEvent event) {
        System.out.println("  📧 [邮件服务] 发送邮件通知: " + event);
    }
}

// 短信通知观察者
class SmsNotifier implements Observer<OrderEvent> {
    @Override
    public void update(OrderEvent event) {
        if ("已支付".equals(event.getStatus())) {
            System.out.println("  📱 [短信服务] 发送支付成功短信: " + event.getOrderId());
        }
    }
}

// 库存管理观察者
class InventoryManager implements Observer<OrderEvent> {
    @Override
    public void update(OrderEvent event) {
        if ("已支付".equals(event.getStatus())) {
            System.out.println("  📦 [库存系统] 扣减库存: " + event.getOrderId());
        }
    }
}

// 积分管理观察者
class PointsManager implements Observer<OrderEvent> {
    @Override
    public void update(OrderEvent event) {
        if ("已支付".equals(event.getStatus())) {
            int points = (int) (event.getAmount() * 0.1);
            System.out.println("  🎁 [积分系统] 增加积分: " + points + "分");
        }
    }
}

// 日志记录观察者
class LoggingObserver implements Observer<OrderEvent> {
    @Override
    public void update(OrderEvent event) {
        System.out.println("  📝 [日志系统] 记录订单状态变更: " + event);
    }
}

// ============ 测试代码 ============

class ObserverPatternDemo {
    public static void main(String[] args) {
        // 创建主题
        OrderSubject orderSubject = new OrderSubject();

        // 注册多个观察者
        orderSubject.attach(new EmailNotifier());
        orderSubject.attach(new SmsNotifier());
        orderSubject.attach(new InventoryManager());
        orderSubject.attach(new PointsManager());
        orderSubject.attach(new LoggingObserver());

        System.out.println("\n========== 场景1: 创建订单 ==========");
        orderSubject.createOrder("ORD001", 299.99);

        System.out.println("\n========== 场景2: 支付订单 ==========");
        orderSubject.payOrder("ORD001", 299.99);

        // 动态移除观察者
        System.out.println("\n========== 移除邮件通知 ==========");
        orderSubject.detach(new EmailNotifier());

        System.out.println("\n========== 场景3: 再次创建订单 ==========");
        orderSubject.createOrder("ORD002", 599.50);
    }
}
