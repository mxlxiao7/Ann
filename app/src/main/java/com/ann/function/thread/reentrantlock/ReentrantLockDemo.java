package com.ann.function.thread.reentrantlock;

import android.util.Log;

import com.ann.function.thread.pc.CConsumer;
import com.ann.function.thread.pc.CInfo;
import com.ann.function.thread.pc.CProducer;
import com.ann.utils.Utils;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 重入锁，分非公平和公平锁,ReentrantLock默认情况下为不公平锁
 * 原理:AQS的实现
 * <p>
 * 注意点:
 * （1）
 * lock 必须在 finally 块中释放。否则，如果受保护的代码将抛出异常，锁就有可能永远得不到释放！这一点区别看起来
 * 可能没什么，但是实际上，它极为重要。忘记在 finally 块中释放锁，可能会在程序中留下一个定时炸弹，当有一天炸弹
 * 爆炸时，您要花费很大力气才有找到源头在哪。而使用同步，JVM 将确保锁会获得自动释放
 * （2）
 * 当 JVM 用 synchronized 管理锁定请求和释放时，JVM 在生成线程转储时能够包括锁定信息。这些对调试非常有价值，
 * 因为它们能标识死锁或者其他异常行为的来源。 Lock 类只是普通的类，JVM 不知道具体哪个线程拥有 Lock 对象。
 */
public class ReentrantLockDemo {


    public static void main() {
        new Thread(() -> new ReentrantLockDemo().doAction()).start();
    }

    public void doAction() {
//        action1();
//        action2();
//        action3();
        action5();
    }


    private ReentrantLock lock = new ReentrantLock();

    /**
     * 场景1：如果发现该操作已经在执行中则不再执行（有状态执行）
     * <p>
     * a、用在定时任务时，如果任务执行时间可能超过下次计划执行时间，确保该有状态任务只有一个正在执行，忽略重复触发。
     * b、用在界面交互时点击执行较长时间请求操作时，防止多次点击导致后台重复执行（忽略重复触发）。
     * 以上两种情况多用于进行非重要任务防止重复执行，（如：清除无用临时文件，检查某些资源的可用性，数据备份操作等）
     */
    private void action1() {
        Utils.msg("action1()");

        Utils.msg("【赵云】吼吼，尝试争锁");
        if (lock.tryLock()) {//如果已经被lock，则会立即返回false不会等待，达到忽略操作的效果
            try {
                //执行逻辑操作
                Utils.msg("【赵云】哈哈，获取到锁");

            } finally {
                lock.unlock();
            }
        } else {
            Utils.msg("【赵云】呜呜，争锁失败");
        }
    }

    /**
     * 场景2：
     * 如果发现该操作已经在执行，等待一个一个执行（同步执行，类似synchronized）
     * 这种比较常见大家也都在用，主要是防止资源使用冲突，保证同一时间内只有一个操作可以使用该资源。
     * 但与synchronized的明显区别是性能优势（伴随jvm的优化这个差距在减小）。同时Lock有更灵活的锁定方式，
     * 公平锁与不公平锁，而synchronized永远是公平的。
     * <p>
     * 这种情况主要用于对资源的争抢（如：文件操作，同步消息发送，有状态的操作等）
     */
    private void action2() {
        Utils.msg("action2()");
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                Utils.msg(Thread.currentThread().getName() + " 吼吼，尝试争锁");
                try {
                    lock.lock();

                    //执行逻辑操作
                    Utils.msg(Thread.currentThread().getName() + " 哈哈，获取到锁");

                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Utils.msg(Log.getStackTraceString(e));
                } finally {
                    lock.unlock();
                }
            }, "【死侍】" + i).start();
        }
    }

    /**
     * 场景3：
     * 如果发现该操作已经在执行，则尝试等待一段时间，等待超时则不执行（尝试等待执行）
     * 这种其实属于场景2的改进，等待获得锁的操作有一个时间的限制，如果超时则放弃执行。
     * 用来防止由于资源处理不当长时间占用导致死锁情况（大家都在等待资源，导致线程队列溢出）。
     */
    public void action3() {
        Utils.msg("action3()");

        try {
            Utils.msg("【关羽】吼吼，尝试争锁，等待5s");
            if (lock.tryLock(6, TimeUnit.SECONDS)) {
                try {
                    //执行逻辑操作
                    Utils.msg("【关羽】哈哈，获取到锁");
                } finally {
                    lock.unlock();
                }
            } else {
                Utils.msg("【关羽】呜呜，争锁失败");
            }

        } catch (InterruptedException e) {
            Utils.msg(Log.getStackTraceString(e));
        }
    }

    /**
     * 读写锁
     */
    private ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    /**
     * 缓存是否有效
     */
    private volatile boolean cacheValid;

    /**
     * 模拟缓存数据
     */
    private String data = "我是缓存数据";

    /**
     * Sync：继承于经典的AbstractQueuedSynchronizer（传说中的AQS）,是一个抽象类，包含2个抽象方法readerShouldBlock()；writerShouldBlock()
     * FairSync和NonfairSync：继承于Sync,分别实现了公平/非公平锁。
     * ReadLock和WriteLock：都是Lock实现类，分别实现了读、写锁。ReadLock是共享的，而WriteLock是独占的。于是Sync类覆盖了AQS中独占和共享模式的抽象方法(tryAcquire/tryAcquireShared等)，用同一个等待队列来维护读/写排队线程，而用一个32位int state标示和记录读/写锁重入次数--Doug Lea把状态的高16位用作读锁，记录所有读锁重入次数之和，低16位用作写锁，记录写锁重入次数。所以无论是读锁还是写锁最多只能被持有65535次。
     * 性能和建议：适用于读多写少的情况。性能较高。
     * <p>
     * <p>
     * 公平性
     * 非公平锁（默认）,为了防止写线程饿死，规则是：当等待队列头部结点是独占模式（即要获取写锁的线程）时，只有获取独占锁线程可以抢占，而试图获取共享锁的线程必须进入队列阻塞；当队列头部结点是共享模式（即要获取读锁的线程）时，试图获取独占和共享锁的线程都可以抢占。
     * 公平锁，利用AQS的等待队列，线程按照FIFO的顺序获取锁，因此不存在写线程一直等待的问题。
     * 重入性：读写锁均是可重入的，读/写锁重入次数保存在了32位int state的高/低16位中。而单个读线程的重入次数，则记录在ThreadLocalHoldCounter类型的readHolds里。
     * 锁降级：写线程获取写入锁后可以获取读取锁，然后释放写入锁，这样就从写入锁变成了读取锁，从而实现锁降级。
     * 锁获取中断：读取锁和写入锁都支持获取锁期间被中断。
     * 条件变量：写锁提供了条件变量(Condition)的支持，这个和独占锁ReentrantLock一致，但是读锁却不允许，调用readLock().newCondition()会抛出UnsupportedOperationException异常。
     */
    public void action4() {
        /**
         * 模拟一个缓存读写场景
         */

        //1、首先获取读锁
        rwLock.readLock().lock();
        //2、读操作，如果数据无效(在读操作之前有线程修改缓存数据)
        if (!cacheValid) {
            //3、释放读锁
            rwLock.readLock().unlock();
            //4、获取写锁
            rwLock.writeLock().lock();
            try {
                //5、再次校验缓存有效性，如果无效，更新缓存
                if (!cacheValid) {
                    data = "";
                    cacheValid = true;
                }
                //6、释放写锁前，写锁降级为读锁（存在写锁的情况下，获取读锁，写锁降级为读锁）
                rwLock.readLock().lock();
            } finally {
                //7、释放写锁，保持读锁
                rwLock.writeLock().unlock();
            }
        }
        try {
            //8、使用缓存数据
            Utils.msg("当前数据为：" + data);
        } finally {
            //9、释放读锁
            rwLock.readLock().unlock();
        }
    }

    /**
     * 终点学习Condition的用法
     */
    public void action5() {
        ProductQueue<String> queue = new ProductQueue(); // 实例化Info对象
        P pro = new P(queue); // 生产者
        C con = new C(queue); // 消费者
        new Thread(pro).start();
        //启动了生产者线程后，再启动消费者线程
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(con).start();
    }
}
