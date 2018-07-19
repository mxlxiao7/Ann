####BackpressureStrategy(背压)



####线程调度:
关于线程切换这点，RxJava 1.x 和 RxJava 2.x 的实现思路是一样的。这里简单的说一下

subScribeOn
同 RxJava 1.x 一样，subscribeOn 用于指定 subscribe() 时所发生的线程，从源码角度可以看出，内部线程调度是通过 ObservableSubscribeOn来实现的。

observeOn
observeOn 方法用于指定下游 Observer 回调发生的线程。

线程切换需要注意的
RxJava 内置的线程调度器的确可以让我们的线程切换得心应手，但其中也有些需要注意的地方。

简单地说，subscribeOn() 指定的就是发射事件的线程，observerOn 指定的就是订阅者接收事件的线程。
多次指定发射事件的线程只有第一次指定的有效，也就是说多次调用 subscribeOn() 只有第一次的有效，其余的会被忽略。
但多次指定订阅者接收线程是可以的，也就是说每调用一次 observerOn()，下游的线程就会切换一次。



####操作符：
map         变换
flatMap     它可以把一个发射器 Observable 通过某种方法转换为多个 Observables，然后再把这些分散的
            Observables装进一个单一的发射器 Observable。但有个需要注意的是，flatMap 并不能保证事
            件的顺序，如果需要保证，需要用到我们下面要讲的 ConcatMap。
concat      对于单一的把两个发射器连接成一个发射器
concatMap   功能类似flatMap，但是concatMap的事件是有顺序的
filter      可以接受一个参数，让其过滤掉不符合我们条件的值
take        取前几个数
range(a,b)  取从a开始数b个数
zip:专用于合并事件，该合并不是连接，而是两两配对，也就意味着，最终配对出的 Observable 发射事件数目只和少的那个相同。
timer       相当于一个定时任务。但需要注意的是，timer 和 interval 均默认在新线程。
interval    如同我们上面可说，interval 操作符用于间隔时间执行某个操作，其接受三个参数，分别是第一次发送延迟，间隔时间，时间单位。
skip        接受一个 long 型参数 count ，代表跳过 count 个数目开始接收
single      Single 只会接收一个参数，而 SingleObserver 只会调用 onError() 或者 onSuccess()
distinct    去重操作符，简单的作用就是去重。

debounce    去除发送频率过快的项，看起来好像没啥用处，但你信我，后面绝对有地方很有用武之地。
defer       简单地时候就是每次订阅都会创建一个新的 Observable，并且如果没有被订阅，就不会产生新的 Observable
last        操作符仅取出可观察到的最后一个值，或者是满足某些条件的最后一项。
merge       merge 的作用是把多个 Observable 结合起来，接受可变参数，也支持迭代器集合。
            注意它和 concat 的区别在于，不用等到 发射器 A 发送完所有的事件再进行发射器 B 的发送。
reduce      操作符每次用一个方法处理一个值，可以有一个 seed 作为初始值。
scan        操作符作用和上面的 reduce 一致，唯一区别是 reduce 是个只追求结果的坏人，而 scan 会始终如一地把每一个步骤都输出。
window: 按照实际划分窗口，将数据发送给不同的 Observable















