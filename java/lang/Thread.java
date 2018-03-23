package source.java.lang;

import java.lang.Thread.State;
import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.concurrent.locks.LockSupport;

import sun.nio.ch.Interruptible;
import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * A <i>thread</i> is a thread of execution in a program. The Java
 * Virtual Machine allows an application to have multiple threads of
 * execution running concurrently.
 * <p>
 * 线程是程序中的执行线程。Java虚拟机允许应用程序同时运行多个执行线程。
 * 
 * Every thread has a priority. Threads with higher priority are
 * executed in preference to threads with lower priority. Each thread
 * may or may not also be marked as a daemon. When code running in
 * some thread creates a new <code>Thread</code> object, the new
 * thread has its priority initially set equal to the priority of the
 * creating thread, and is a daemon thread if and only if the
 * creating thread is a daemon.
 * <p>
 * 每个线程都有优先级。优先级较高的线程优先于具有较低优先级的线程。
 * 每个线程可能也被标记为一个守护进程。
 * 当在某些线程中运行的代码创建一个新的线程对象时，新线程的优先级设置为创建线程的优先级，
 * 并且只有在创建线程是守护进程的情况下，它是一个守护线程。
 * 
 * When a Java Virtual Machine starts up, there is usually a single
 * non-daemon thread (which typically calls the method named
 * <code>main</code> of some designated class). The Java Virtual
 * Machine continues to execute threads until either of the following
 * occurs:
 * <ul>
 * <li>The <code>exit</code> method of class <code>Runtime</code> has been
 *     called and the security manager has permitted the exit operation
 *     to take place.
 * <li>All threads that are not daemon threads have died, either by
 *     returning from the call to the <code>run</code> method or by
 *     throwing an exception that propagates beyond the <code>run</code>
 *     method.
 * </ul>
 * <p>
 * 当Java虚拟机启动时，通常会有一个非守护线程(通常称为某些指定类的main)。
 * Java虚拟机继续执行线程，直到发生下列任何一种情况:
 * •调用类运行时的退出方法，安全管理器允许进行退出操作。
 * •所有不是守护线程的线程都已经死亡，要么返回到run方法的调用，要么抛出一个在run方法之外传播的异常。
 * 
 * There are two ways to create a new thread of execution. One is to
 * declare a class to be a subclass of <code>Thread</code>. This
 * subclass should override the <code>run</code> method of class
 * <code>Thread</code>. An instance of the subclass can then be
 * allocated and started. For example, a thread that computes primes
 * larger than a stated value could be written as follows:
 * <hr><blockquote><pre>
 *     class PrimeThread extends Thread {
 *         long minPrime;
 *         PrimeThread(long minPrime) {
 *             this.minPrime = minPrime;
 *         }
 *
 *         public void run() {
 *             // compute primes larger than minPrime
 *             &nbsp;.&nbsp;.&nbsp;.
 *         }
 *     }
 * </pre></blockquote><hr>
 * <p>
 * The following code would then create a thread and start it running:
 * <blockquote><pre>
 *     PrimeThread p = new PrimeThread(143);
 *     p.start();
 * </pre></blockquote>
 * <p>
 * The other way to create a thread is to declare a class that
 * implements the <code>Runnable</code> interface. That class then
 * implements the <code>run</code> method. An instance of the class can
 * then be allocated, passed as an argument when creating
 * <code>Thread</code>, and started. The same example in this other
 * style looks like the following:
 * <hr><blockquote><pre>
 *     class PrimeRun implements Runnable {
 *         long minPrime;
 *         PrimeRun(long minPrime) {
 *             this.minPrime = minPrime;
 *         }
 *
 *         public void run() {
 *             // compute primes larger than minPrime
 *             &nbsp;.&nbsp;.&nbsp;.
 *         }
 *     }
 * </pre></blockquote><hr>
 * <p>
 * The following code would then create a thread and start it running:
 * <blockquote><pre>
 *     PrimeRun p = new PrimeRun(143);
 *     new Thread(p).start();
 * </pre></blockquote>
 * <p>
 * Every thread has a name for identification purposes. More than
 * one thread may have the same name. If a name is not specified when
 * a thread is created, a new name is generated for it.
 * <p>
 * Unless otherwise noted, passing a {@code null} argument to a constructor
 * or method in this class will cause a {@link NullPointerException} to be
 * thrown.
 *
 * @author  unascribed
 * @see     Runnable
 * @see     Runtime#exit(int)
 * @see     #run()
 * @see     #stop()
 * @since   JDK1.0
 */
public class Thread implements Runnable{

    /* Make sure registerNatives is the first thing <clinit> does. */
    private static native void registerNatives();
    static {
        registerNatives();
    }
    
    private volatile String name;
    private int            priority;
    private Thread         threadQ;
    private long           eetop;
    
    /* Whether or not to single_step this thread. */
    // 是否要执行这个线程。
    private boolean     single_step;
    
    /* Whether or not the thread is a daemon thread. */
    // 线程是否为守护线程。
    private boolean     daemon = false;
    
    /* JVM state */
    private boolean     stillborn = false;
    
    /* What will be run. */
    private Runnable target;
    
    /* The group of this thread */
    private ThreadGroup group;

    /* The context ClassLoader for this thread */
    private ClassLoader contextClassLoader;

    /* The inherited AccessControlContext of this thread */
    private AccessControlContext inheritedAccessControlContext;
    
    /* For autonumbering anonymous threads. */
    private static int threadInitNumber;
    private static synchronized int nextThreadNum() {
        return threadInitNumber++;
    }
    
    /* ThreadLocal values pertaining to this thread. This map is maintained
     * by the ThreadLocal class. */
    ThreadLocal.ThreadLocalMap threadLocals = null;
    
    /*
     * InheritableThreadLocal values pertaining to this thread. This map is
     * maintained by the InheritableThreadLocal class.
     */
    ThreadLocal.ThreadLocalMap inheritableThreadLocals = null;
    
    /*
     * The requested stack size for this thread, or 0 if the creator did
     * not specify a stack size.  It is up to the VM to do whatever it
     * likes with this number; some VMs will ignore it.
     */
    private long stackSize;
    
    /*
     * JVM-private state that persists after native thread termination.
     */
    private long nativeParkEventPointer;
    
    /*
     * Thread ID
     */
    private long tid;
    
    /* For generating thread ID */
    private static long threadSeqNumber;
    
    
    /* Java thread status for tools,
     * initialized to indicate thread 'not yet started'
     */

    private volatile int threadStatus = 0;
    
    private static synchronized long nextThreadID() {
        return ++threadSeqNumber;
    }
    
    /**
     * The argument supplied to the current call to
     * java.util.concurrent.locks.LockSupport.park.
     * Set by (private) java.util.concurrent.locks.LockSupport.setBlocker
     * Accessed using java.util.concurrent.locks.LockSupport.getBlocker
     */
    volatile Object parkBlocker;
    
    /* The object in which this thread is blocked in an interruptible I/O
     * operation, if any.  The blocker's interrupt method should be invoked
     * after setting this thread's interrupt status.
     */
    private volatile Interruptible blocker;
    private final Object blockerLock = new Object();
    
    /* Set the blocker field; invoked via sun.misc.SharedSecrets from java.nio code
     */
    void blockedOn(Interruptible b) {
        synchronized (blockerLock) {
            blocker = b;
        }
    }
    
    /**
     * The minimum priority that a thread can have.
     */
    public final static int MIN_PRIORITY = 1;

   /**
     * The default priority that is assigned to a thread.
     */
    public final static int NORM_PRIORITY = 5;

    /**
     * The maximum priority that a thread can have.
     */
    public final static int MAX_PRIORITY = 10;
    
    /**
     * Returns a reference to the currently executing thread object.
     *
     * @return  the currently executing thread.
     */
    public static native Thread currentThread();
    
    /**
     * A hint to the scheduler that the current thread is willing to yield
     * its current use of a processor. The scheduler is free to ignore this
     * hint.
     * 对调度程序的提示，当前线程愿意放弃当前使用的处理器。调度器可以忽略这个提示。
     * 
     * <p> Yield is a heuristic attempt to improve relative progression
     * between threads that would otherwise over-utilise a CPU. Its use
     * should be combined with detailed profiling and benchmarking to
     * ensure that it actually has the desired effect.
     * Yield是一种启发式的尝试，以改进在其他情况下会过度使用CPU的线程之间的相对进展。
     * 它的使用应该与详细的分析和基准测试相结合，以确保它实际上有预期的效果。
     * 
     * <p> It is rarely appropriate to use this method. It may be useful
     * for debugging or testing purposes, where it may help to reproduce
     * bugs due to race conditions. It may also be useful when designing
     * concurrency control constructs such as the ones in the
     * {@link java.util.concurrent.locks} package.
     * 使用这种方法是不合适的。它可能对调试或测试的目的很有用，
     * 在那里它可以帮助复制由于竞争条件而产生的错误。
     * 在设计类似java.util.concurrent中的并发控制结构时，它可能也很有用。锁方案。
     */
    public static native void yield();
    
    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds, subject to
     * the precision and accuracy of system timers and schedulers. The thread
     * does not lose ownership of any monitors.
     * 由于系统定时器和调度器的精度和准确性，
     * 导致当前执行的线程(暂时停止执行)的时间为指定的毫秒数。
     * 线程不会失去任何监视器的所有权。
     * 
     * @param  millis
     *         the length of time to sleep in milliseconds
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public static native void sleep(long millis) throws InterruptedException;
    
    /**
     * Causes the currently executing thread to sleep (temporarily cease
     * execution) for the specified number of milliseconds plus the specified
     * number of nanoseconds, subject to the precision and accuracy of system
     * timers and schedulers. The thread does not lose ownership of any
     * monitors.
     *
     * @param  millis
     *         the length of time to sleep in milliseconds
     *
     * @param  nanos
     *         {@code 0-999999} additional nanoseconds to sleep
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative, or the value of
     *          {@code nanos} is not in the range {@code 0-999999}
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public static void sleep(long millis, int nanos)
    throws InterruptedException {
        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        sleep(millis);
    }
    
    /**
     * Initializes a Thread with the current AccessControlContext.
     * 使用当前AccessControlContext初始化一个线程。
     * @see #init(ThreadGroup,Runnable,String,long,AccessControlContext,boolean)
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize) {
        init(g, target, name, stackSize, null, true);
    }
    
    /**
     * Initializes a Thread.
     *
     * @param g the Thread group
     * @param target the object whose run() method gets called
     * @param name the name of the new Thread
     * @param stackSize the desired stack size for the new thread, or
     *        zero to indicate that this parameter is to be ignored.
     * @param acc the AccessControlContext to inherit, or
     *            AccessController.getContext() if null
     * @param inheritThreadLocals if {@code true}, inherit initial values for
     *            inheritable thread-locals from the constructing thread
     *            
     * g the Thread group
     * target the object whose run() method gets called
     * name the name of the new Thread
     * stackSize the desired stack size for the new thread, or zero to indicate that this parameter is to be ignored.
     * acc the AccessControlContext to inherit, or AccessController.getContext() if null
     * inheritThreadLocals if true, inherit initial values for inheritable thread-locals from the constructing thread
     */
    private void init(ThreadGroup g, Runnable target, String name,
                      long stackSize, AccessControlContext acc,
                      boolean inheritThreadLocals) {
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;

        Thread parent = currentThread();
        SecurityManager security = System.getSecurityManager();
        if (g == null) {
            /* Determine if it's an applet or not */
        	// 确定它是否为applet。

            /* If there is a security manager, ask the security manager
               what to do. */
            if (security != null) {
                g = security.getThreadGroup();
            }

            /* If the security doesn't have a strong opinion of the matter
               use the parent thread group. */
            if (g == null) {
                g = parent.getThreadGroup();
            }
        }

        /* checkAccess regardless of whether or not threadgroup is
           explicitly passed in. */
        g.checkAccess();

        /*
         * Do we have the required permissions?
         */
        // 我们有必需的权限吗?
        if (security != null) {
            if (isCCLOverridden(getClass())) {
                security.checkPermission(SUBCLASS_IMPLEMENTATION_PERMISSION);
            }
        }

        g.addUnstarted();

        this.group = g;
        this.daemon = parent.isDaemon();
        this.priority = parent.getPriority();
        if (security == null || isCCLOverridden(parent.getClass()))
            this.contextClassLoader = parent.getContextClassLoader();
        else
            this.contextClassLoader = parent.contextClassLoader;
        this.inheritedAccessControlContext =
                acc != null ? acc : AccessController.getContext();
        this.target = target;
        setPriority(priority);
        if (inheritThreadLocals && parent.inheritableThreadLocals != null)
            this.inheritableThreadLocals =
                ThreadLocal.createInheritedMap(parent.inheritableThreadLocals);
        /* Stash the specified stack size in case the VM cares */
        this.stackSize = stackSize;

        /* Set thread ID */
        tid = nextThreadID();
    }
    
    /**
     * Throws CloneNotSupportedException as a Thread can not be meaningfully
     * cloned. Construct a new Thread instead.
     * 抛出CloneNotSupportedException作为一个线程不能克隆的意义。构建一个新的线程。
     * @throws  CloneNotSupportedException
     *          always
     */
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
    
    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, null, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     * 分配一个新的线程对象。这个构造函数与Thread (null, null, gname)具有相同的效果，
     * 其中gname是一个新生成的名称。
     * 自动生成的名称是“线程”+n的形式，其中n是一个整数。
     * 
     */
    public Thread() {
        init(null, null, "Thread-" + nextThreadNum(), 0);
    }
    
    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, target, gname)}, where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     * 分配一个新的线程对象。这个构造函数与Thread (null, target, gname)相同，
     * 其中gname是一个新生成的名称。
     * 自动生成的名称是“线程”+n的形式，其中n是一个整数。
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this classes {@code run} method does
     *         nothing.
     */
    public Thread(Runnable target) {
        init(null, target, "Thread-" + nextThreadNum(), 0);
    }
    
    /**
     * Creates a new Thread that inherits the given AccessControlContext.
     * This is not a public constructor.
     * 创建一个新线程，该线程继承给定的AccessControlContext。这不是一个公共构造函数。
     */
    Thread(Runnable target, AccessControlContext acc) {
        init(null, target, "Thread-" + nextThreadNum(), 0, acc, false);
    }
    
    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (group, target, gname)} ,where {@code gname} is a newly generated
     * name. Automatically generated names are of the form
     * {@code "Thread-"+}<i>n</i>, where <i>n</i> is an integer.
     *
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *			组线程组。
     *			如果空有一个安全管理器,该组织是由SecurityManager.getThreadGroup()。
     *			如果没有一个安全经理或SecurityManager.getThreadGroup()返回null,
     *			集团是集当前线程的线程组。
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *			在启动此线程时调用运行方法的对象。如果null，则调用此线程的run方法。
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     */
    public Thread(ThreadGroup group, Runnable target) {
        init(group, target, "Thread-" + nextThreadNum(), 0);
    }
    
    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (null, null, name)}.
     *
     * @param   name
     *          the name of the new thread
     */
    public Thread(String name) {
        init(null, null, name, 0);
    }
    
    /**
     * Allocates a new {@code Thread} object. This constructor has the same
     * effect as {@linkplain #Thread(ThreadGroup,Runnable,String) Thread}
     * {@code (group, null, name)}.
     *
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  name
     *         the name of the new thread
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     */
    public Thread(ThreadGroup group, String name) {
        init(group, null, name, 0);
    }
    
    /**
     * Allocates a new {@code Thread} object so that it has {@code target}
     * as its run object, has the specified {@code name} as its name,
     * and belongs to the thread group referred to by {@code group}.
     * 分配一个新的线程对象，以便它将目标作为它的运行对象，
     * 并将指定的名称作为它的名称，并且属于group所引用的线程组。
     * 
     * <p>If there is a security manager, its
     * {@link SecurityManager#checkAccess(ThreadGroup) checkAccess}
     * method is invoked with the ThreadGroup as its argument.
     * 如果有一个安全管理器，它的checkAccess方法将被ThreadGroup作为其参数调用。
     * 
     * <p>In addition, its {@code checkPermission} method is invoked with
     * the {@code RuntimePermission("enableContextClassLoaderOverride")}
     * permission when invoked directly or indirectly by the constructor
     * of a subclass which overrides the {@code getContextClassLoader}
     * or {@code setContextClassLoader} methods.
     * 
		此外,它的checkPermission方法调用的RuntimePermission(“enableContextClassLoaderOverride”)
		许可时直接或间接调用子类的构造函数覆盖getContextClassLoader或setContextClassLoader方法。
     * 
     * <p>The priority of the newly created thread is set equal to the
     * priority of the thread creating it, that is, the currently running
     * thread. The method {@linkplain #setPriority setPriority} may be
     * used to change the priority to a new value.
     * 新创建的线程的优先级设置为创建它的线程的优先级，即当前运行的线程。
     * 方法setPriority可用于将优先级更改为新值
     * 
     * <p>The newly created thread is initially marked as being a daemon
     * thread if and only if the thread creating it is currently marked
     * as a daemon thread. The method {@linkplain #setDaemon setDaemon}
     * may be used to change whether or not a thread is a daemon.
     * 新创建的线程最初被标记为一个守护线程，如果且仅当创建它的线程当前被标记为守护线程。
     * 方法setDaemon可以用来改变线程是否为守护进程。
     * 
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @param  name
     *         the name of the new thread
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group or cannot override the context class loader methods.
     */
    public Thread(ThreadGroup group, Runnable target, String name) {
        init(group, target, name, 0);
    }
    
    /**
     * Allocates a new {@code Thread} object so that it has {@code target}
     * as its run object, has the specified {@code name} as its name,
     * and belongs to the thread group referred to by {@code group}, and has
     * the specified <i>stack size</i>.
     * 分配一个新的线程对象，以便它将目标作为它的运行对象，并将指定的名称作为它的名称，
     * 并且属于group所引用的线程组，并且具有指定的堆栈大小。
     * 
     * <p>This constructor is identical to {@link
     * #Thread(ThreadGroup,Runnable,String)} with the exception of the fact
     * that it allows the thread stack size to be specified.  The stack size
     * is the approximate number of bytes of address space that the virtual
     * machine is to allocate for this thread's stack.  <b>The effect of the
     * {@code stackSize} parameter, if any, is highly platform dependent.</b>
     * 此构造函数与线程(ThreadGroup、Runnable、String)相同，
     * 但它允许指定线程堆栈大小。堆栈大小是虚拟机为该线程的堆栈分配的地址空间的大约字节数。
     * stackSize参数(如果有的话)的影响是高度依赖平台的。
     * 
     * <p>On some platforms, specifying a higher value for the
     * {@code stackSize} parameter may allow a thread to achieve greater
     * recursion depth before throwing a {@link StackOverflowError}.
     * Similarly, specifying a lower value may allow a greater number of
     * threads to exist concurrently without throwing an {@link
     * OutOfMemoryError} (or other internal error).  The details of
     * the relationship between the value of the <tt>stackSize</tt> parameter
     * and the maximum recursion depth and concurrency level are
     * platform-dependent.  <b>On some platforms, the value of the
     * {@code stackSize} parameter may have no effect whatsoever.</b>
     * 在某些平台上，为stackSize参数指定更高的值可以允许线程在抛出
     * StackOverflowError之前获得更大的递归深度。
     * 类似地，指定一个较低的值可以允许更多的线程同时存在，
     * 而不会抛出OutOfMemoryError(或其他内部错误)。
     * stackSize参数值与最大递归深度和并发级别之间的关系的细节是平台相关的。
     * 在某些平台上，stackSize参数的值可能没有任何影响。
     * 
     * <p>The virtual machine is free to treat the {@code stackSize}
     * parameter as a suggestion.  If the specified value is unreasonably low
     * for the platform, the virtual machine may instead use some
     * platform-specific minimum value; if the specified value is unreasonably
     * high, the virtual machine may instead use some platform-specific
     * maximum.  Likewise, the virtual machine is free to round the specified
     * value up or down as it sees fit (or to ignore it completely).
     * 虚拟机可以将stackSize参数视为一个建议。如果平台的指定值不合理，
     * 则虚拟机可以使用特定于平台的最小值;如果指定的值过高，
     * 虚拟机可能会使用特定于平台的最大值。
     * 同样地，虚拟机可以自由地在指定的值上下浮动，因为它认为合适(或者完全忽略它)。
     * 
     * <p>Specifying a value of zero for the {@code stackSize} parameter will
     * cause this constructor to behave exactly like the
     * {@code Thread(ThreadGroup, Runnable, String)} constructor.
     * 为stackSize参数指定一个值为0会导致该构造函数的行为与线程(ThreadGroup、Runnable、String)构造函数完全相同。
     * 
     * <p><i>Due to the platform-dependent nature of the behavior of this
     * constructor, extreme care should be exercised in its use.
     * The thread stack size necessary to perform a given computation will
     * likely vary from one JRE implementation to another.  In light of this
     * variation, careful tuning of the stack size parameter may be required,
     * and the tuning may need to be repeated for each JRE implementation on
     * which an application is to run.</i>
     * 由于该构造函数的行为具有平台依赖性，所以在使用时应该格外小心。
     * 执行给定计算所需的线程堆栈大小可能因JRE实现而异。根据这种变化，
     * 可能需要对堆栈大小参数进行仔细的调优，对于应用程序要运行的每个JRE实现，可能需要重复调优。
     * 
     * <p>Implementation note: Java platform implementers are encouraged to
     * document their implementation's behavior with respect to the
     * {@code stackSize} parameter.
     * 实现说明:鼓励Java平台实现者记录他们的实现对stackSize参数的行为。
     *
     * @param  group
     *         the thread group. If {@code null} and there is a security
     *         manager, the group is determined by {@linkplain
     *         SecurityManager#getThreadGroup SecurityManager.getThreadGroup()}.
     *         If there is not a security manager or {@code
     *         SecurityManager.getThreadGroup()} returns {@code null}, the group
     *         is set to the current thread's thread group.
     *
     * @param  target
     *         the object whose {@code run} method is invoked when this thread
     *         is started. If {@code null}, this thread's run method is invoked.
     *
     * @param  name
     *         the name of the new thread
     *
     * @param  stackSize
     *         the desired stack size for the new thread, or zero to indicate
     *         that this parameter is to be ignored.
     *
     * @throws  SecurityException
     *          if the current thread cannot create a thread in the specified
     *          thread group
     *
     * @since 1.4
     */
    public Thread(ThreadGroup group, Runnable target, String name,
                  long stackSize) {
        init(group, target, name, stackSize);
    }
    
    
    /**
     * Causes this thread to begin execution; the Java Virtual Machine
     * calls the <code>run</code> method of this thread.
     * <p>
     * 导致该线程开始执行;Java虚拟机调用此线程的运行方法。
     * 
     * The result is that two threads are running concurrently: the
     * current thread (which returns from the call to the
     * <code>start</code> method) and the other thread (which executes its
     * <code>run</code> method).
     * <p>
     * 结果是两个线程同时运行:当前线程(从调用到start方法返回)和另一个线程(执行它的run方法)。
     * 
     * It is never legal to start a thread more than once.
     * In particular, a thread may not be restarted once it has completed
     * execution.
     * 多次启动一个线程是不合法的。特别地，线程在完成执行之后可能不会重新启动。
     * @exception  IllegalThreadStateException  if the thread was already
     *               started.
     * @see        #run()
     * @see        #stop()
     */
    public synchronized void start() {
        /**
         * This method is not invoked for the main method thread or "system"
         * group threads created/set up by the VM. Any new functionality added
         * to this method in the future may have to also be added to the VM.
         *
         * A zero status value corresponds to state "NEW".
         */
        if (threadStatus != 0)
            throw new IllegalThreadStateException();

        /* Notify the group that this thread is about to be started
         * so that it can be added to the group's list of threads
         * and the group's unstarted count can be decremented. */
        /*
         * 通知组该线程即将启动。
			这样它就可以添加到组的线程列表中。
			而且，该组织的未开始计数也会减少。
         */
        group.add(this);

        boolean started = false;
        try {
            start0();
            started = true;
        } finally {
            try {
                if (!started) {
                    group.threadStartFailed(this);
                }
            } catch (Throwable ignore) {
                /* do nothing. If start0 threw a Throwable then
                  it will be passed up the call stack */
            }
        }
    }
    
    private native void start0();
    
    /**
     * If this thread was constructed using a separate
     * <code>Runnable</code> run object, then that
     * <code>Runnable</code> object's <code>run</code> method is called;
     * otherwise, this method does nothing and returns.
     * <p>
     * Subclasses of <code>Thread</code> should override this method.
     *
     * @see     #start()
     * @see     #stop()
     * @see     #Thread(ThreadGroup, Runnable, String)
     */
    @Override
    public void run() {
        if (target != null) {
            target.run();
        }
    }
    
    
    /**
     * This method is called by the system to give a Thread
     * a chance to clean up before it actually exits.
     */
    private void exit() {
        if (group != null) {
            group.threadTerminated(this);
            group = null;
        }
        /* Aggressively null out all reference fields: see bug 4006245 */
        target = null;
        /* Speed the release of some of these resources */
        threadLocals = null;
        inheritableThreadLocals = null;
        inheritedAccessControlContext = null;
        blocker = null;
        uncaughtExceptionHandler = null;
    }
    
    
    /**
     * Interrupts this thread.
     *
     * <p> Unless the current thread is interrupting itself, which is
     * always permitted, the {@link #checkAccess() checkAccess} method
     * of this thread is invoked, which may cause a {@link
     * SecurityException} to be thrown.
     * 除非当前线程正在中断自己(这是允许的)，
     * 否则将调用该线程的checkAccess方法，这可能导致抛出SecurityException。
     * 
     * <p> If this thread is blocked in an invocation of the {@link
     * Object#wait() wait()}, {@link Object#wait(long) wait(long)}, or {@link
     * Object#wait(long, int) wait(long, int)} methods of the {@link Object}
     * class, or of the {@link #join()}, {@link #join(long)}, {@link
     * #join(long, int)}, {@link #sleep(long)}, or {@link #sleep(long, int)},
     * methods of this class, then its interrupt status will be cleared and it
     * will receive an {@link InterruptedException}.
     * 
     * 如果该线程在调用wait()、wait(long)或wait(long, int)方法时被阻塞，
     * 或者是join()、join(long)、join(long、int)、sleep(long)、sleep(long, int)、这个类的方法，
     * 那么它的中断状态将被清除，它将接收一个InterruptedException。
     * 
     * <p> If this thread is blocked in an I/O operation upon an {@link
     * java.nio.channels.InterruptibleChannel InterruptibleChannel}
     * then the channel will be closed, the thread's interrupt
     * status will be set, and the thread will receive a {@link
     * java.nio.channels.ClosedByInterruptException}.
     * 如果这个线程被阻塞的I / O操作在一个InterruptibleChannel通道将被关闭,
     * 线程的中断状态将被设置,线程会收到java.nio.channels.ClosedByInterruptException。
     * 
     * <p> If this thread is blocked in a {@link java.nio.channels.Selector}
     * then the thread's interrupt status will be set and it will return
     * immediately from the selection operation, possibly with a non-zero
     * value, just as if the selector's {@link
     * java.nio.channels.Selector#wakeup wakeup} method were invoked.
     * 
		如果该线程被java.nio.channels阻塞。选择器之后，线程的中断状态将被设置，
		它将立即从选择操作返回，可能有一个非零值，就像调用选择器的唤醒方法一样。
     * <p> If none of the previous conditions hold then this thread's interrupt
     * status will be set. </p>
     * 如果之前的条件都不成立，那么这个线程的中断状态将被设置。
     * <p> Interrupting a thread that is not alive need not have any effect.
     * 中断不存在的线程不需要任何效果。
     * @throws  SecurityException
     *          if the current thread cannot modify this thread
     *
     * @revised 6.0
     * @spec JSR-51
     */
    public void interrupt() {
        if (this != Thread.currentThread())
            checkAccess();

        synchronized (blockerLock) {
            Interruptible b = blocker;
            if (b != null) {
                interrupt0();           // Just to set the interrupt flag
                b.interrupt(this);
                return;
            }
        }
        interrupt0();
    }
    
    /**
     * Tests whether the current thread has been interrupted.  The
     * <i>interrupted status</i> of the thread is cleared by this method.  In
     * other words, if this method were to be called twice in succession, the
     * second call would return false (unless the current thread were
     * interrupted again, after the first call had cleared its interrupted
     * status and before the second call had examined it).
     * 测试当前线程是否被中断。该方法清除了线程的中断状态。
     * 换句话说，如果这个方法连续被调用两次，第二个调用将返回false
     * (除非当前线程再次被中断，在第一次调用清除了它的中断状态之后，在第二个调用检查它之前)。
     * 
     * <p>A thread interruption ignored because a thread was not alive
     * at the time of the interrupt will be reflected by this method
     * returning false.
     * 一个线程中断被忽略，因为在中断时线程没有存活，这个方法返回false。
     * 
     * @return  <code>true</code> if the current thread has been interrupted;
     *          <code>false</code> otherwise.
     * @see #isInterrupted()
     * @revised 6.0
     */
    public static boolean interrupted() {
        return currentThread().isInterrupted(true);
    }
    
    /**
     * Tests whether this thread has been interrupted.  The <i>interrupted
     * status</i> of the thread is unaffected by this method.
     * 测试该线程是否被中断。线程的中断状态不受此方法的影响。
     * <p>A thread interruption ignored because a thread was not alive
     * at the time of the interrupt will be reflected by this method
     * returning false.
     * 一个线程中断被忽略，因为在中断时线程没有存活，这个方法返回false。
     * @return  <code>true</code> if this thread has been interrupted;
     *          <code>false</code> otherwise.
     * @see     #interrupted()
     * @revised 6.0
     */
    public boolean isInterrupted() {
        return isInterrupted(false);
    }
    
    /**
     * Tests if some Thread has been interrupted.  The interrupted state
     * is reset or not based on the value of ClearInterrupted that is
     * passed.
     */
    private native boolean isInterrupted(boolean ClearInterrupted);
    
    /**
     * Tests if this thread is alive. A thread is alive if it has
     * been started and has not yet died.
     *
     * @return  <code>true</code> if this thread is alive;
     *          <code>false</code> otherwise.
     */
    public final native boolean isAlive();
    
    
    /**
     * Changes the priority of this thread.
     * <p>
     * First the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException</code>.
     * <p>
     * Otherwise, the priority of this thread is set to the smaller of
     * the specified <code>newPriority</code> and the maximum permitted
     * priority of the thread's thread group.
     *
     * @param newPriority priority to set this thread to
     * @exception  IllegalArgumentException  If the priority is not in the
     *               range <code>MIN_PRIORITY</code> to
     *               <code>MAX_PRIORITY</code>.
     * @exception  SecurityException  if the current thread cannot modify
     *               this thread.
     * @see        #getPriority
     * @see        #checkAccess()
     * @see        #getThreadGroup()
     * @see        #MAX_PRIORITY
     * @see        #MIN_PRIORITY
     * @see        ThreadGroup#getMaxPriority()
     */
    public final void setPriority(int newPriority) {
        ThreadGroup g;
        checkAccess();
        if (newPriority > MAX_PRIORITY || newPriority < MIN_PRIORITY) {
            throw new IllegalArgumentException();
        }
        if((g = getThreadGroup()) != null) {
            if (newPriority > g.getMaxPriority()) {
                newPriority = g.getMaxPriority();
            }
            setPriority0(priority = newPriority);
        }
    }
    
    /**
     * Returns this thread's priority.
     *
     * @return  this thread's priority.
     * @see     #setPriority
     */
    public final int getPriority() {
        return priority;
    }
    
    
    /**
     * Changes the name of this thread to be equal to the argument
     * <code>name</code>.
     * <p>
     * First the <code>checkAccess</code> method of this thread is called
     * with no arguments. This may result in throwing a
     * <code>SecurityException</code>.
     *
     * @param      name   the new name for this thread.
     * @exception  SecurityException  if the current thread cannot modify this
     *               thread.
     * @see        #getName
     * @see        #checkAccess()
     */
    public final synchronized void setName(String name) {
        checkAccess();
        if (name == null) {
            throw new NullPointerException("name cannot be null");
        }

        this.name = name;
        if (threadStatus != 0) {
            setNativeName(name);
        }
    }
    

    /**
     * Returns this thread's name.
     *
     * @return  this thread's name.
     * @see     #setName(String)
     */
    public final String getName() {
        return name;
    }
    
    /**
     * Returns the thread group to which this thread belongs.
     * This method returns null if this thread has died
     * (been stopped).
     *
     * @return  this thread's thread group.
     */
    public final ThreadGroup getThreadGroup() {
        return group;
    }
    
    /**
     * Returns an estimate of the number of active threads in the current
     * thread's {@linkplain java.lang.ThreadGroup thread group} and its
     * subgroups. Recursively iterates over all subgroups in the current
     * thread's thread group.
     * 返回当前线程组及其子组中活动线程数的估计值。递归地遍历当前线程组中的所有子组。
     * 
     * <p> The value returned is only an estimate because the number of
     * threads may change dynamically while this method traverses internal
     * data structures, and might be affected by the presence of certain
     * system threads. This method is intended primarily for debugging
     * and monitoring purposes.
     * 返回的值只是一个估计值，因为当这个方法遍历内部数据结构时，
     * 线程的数量可能会发生动态变化，并且可能会受到某些系统线程的影响。
     * 此方法主要用于调试和监视目的。
     * 
     * @return  an estimate of the number of active threads in the current
     *          thread's thread group and in any other thread group that
     *          has the current thread's thread group as an ancestor
     */
    public static int activeCount() {
        return currentThread().getThreadGroup().activeCount();
    }
    
    /**
     * Copies into the specified array every active thread in the current
     * thread's thread group and its subgroups. This method simply
     * invokes the {@link java.lang.ThreadGroup#enumerate(Thread[])}
     * method of the current thread's thread group.
     * 将当前线程组及其子组中的每个活动线程复制到指定的数组中。
     * 该方法简单地调用java.lang.ThreadGroup.enumerate(Thread[])当前线程组的方法。
     * 
     * <p> An application might use the {@linkplain #activeCount activeCount}
     * method to get an estimate of how big the array should be, however
     * <i>if the array is too short to hold all the threads, the extra threads
     * are silently ignored.</i>  If it is critical to obtain every active
     * thread in the current thread's thread group and its subgroups, the
     * invoker should verify that the returned int value is strictly less
     * than the length of {@code tarray}.
     * 应用程序可能会使用activeCount方法来估计数组的大小，
     * 但是如果数组太短而无法容纳所有线程，那么额外的线程将被静静地忽略。
     * 如果在当前线程的线程组及其子组中获取每个活动线程非常关键，
     * 那么调用者应该验证返回的int值是否严格小于tarray的长度。
     * 
     * <p> Due to the inherent race condition in this method, it is recommended
     * that the method only be used for debugging and monitoring purposes.
     * 由于该方法的固有竞争条件，建议该方法仅用于调试和监控。
     * @param  tarray
     *         an array into which to put the list of threads
     *
     * @return  the number of threads put into the array
     *
     * @throws  SecurityException
     *          if {@link java.lang.ThreadGroup#checkAccess} determines that
     *          the current thread cannot access its thread group
     */
    public static int enumerate(Thread tarray[]) {
        return currentThread().getThreadGroup().enumerate(tarray);
    }
    
    
    /**
     * Waits at most {@code millis} milliseconds for this thread to
     * die. A timeout of {@code 0} means to wait forever.
     * 等待该线程死亡的时间最多为毫秒。0的超时意味着永远等待。
     * 
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     * 这个实现使用了一个循环。等待的电话条件是这样的。当一个线程终止了这个。调用notifyAll方法。
     * 建议应用程序不要在线程实例上使用wait、notify或notifyAll。
     * 
     * @param  millis
     *         the time to wait in milliseconds
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final synchronized void join(long millis)
    throws InterruptedException {
        long base = System.currentTimeMillis();
        long now = 0;

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (millis == 0) {
            while (isAlive()) {
                wait(0);
            }
        } else {
            while (isAlive()) {
                long delay = millis - now;
                if (delay <= 0) {
                    break;
                }
                wait(delay);
                now = System.currentTimeMillis() - base;
            }
        }
    }
    
    /**
     * Waits at most {@code millis} milliseconds plus
     * {@code nanos} nanoseconds for this thread to die.
     * 等待的时间最多是毫秒，再加上纳米秒，让这条线死亡。
     * 
     * <p> This implementation uses a loop of {@code this.wait} calls
     * conditioned on {@code this.isAlive}. As a thread terminates the
     * {@code this.notifyAll} method is invoked. It is recommended that
     * applications not use {@code wait}, {@code notify}, or
     * {@code notifyAll} on {@code Thread} instances.
     * 这个实现使用了一个循环。等待的电话条件是这样的。当一个线程终止了这个。调用notifyAll方法。
     * 建议应用程序不要在线程实例上使用wait、notify或notifyAll。
     * 
     * @param  millis
     *         the time to wait in milliseconds
     *
     * @param  nanos
     *         {@code 0-999999} additional nanoseconds to wait
     *
     * @throws  IllegalArgumentException
     *          if the value of {@code millis} is negative, or the value
     *          of {@code nanos} is not in the range {@code 0-999999}
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final synchronized void join(long millis, int nanos)
    throws InterruptedException {

        if (millis < 0) {
            throw new IllegalArgumentException("timeout value is negative");
        }

        if (nanos < 0 || nanos > 999999) {
            throw new IllegalArgumentException(
                                "nanosecond timeout value out of range");
        }

        if (nanos >= 500000 || (nanos != 0 && millis == 0)) {
            millis++;
        }

        join(millis);
    }
    
    /**
     * Waits for this thread to die.
     *
     * <p> An invocation of this method behaves in exactly the same
     * way as the invocation
     *
     * <blockquote>
     * {@linkplain #join(long) join}{@code (0)}
     * </blockquote>
     *
     * @throws  InterruptedException
     *          if any thread has interrupted the current thread. The
     *          <i>interrupted status</i> of the current thread is
     *          cleared when this exception is thrown.
     */
    public final void join() throws InterruptedException {
        join(0);
    }
    
    /**
     * Marks this thread as either a {@linkplain #isDaemon daemon} thread
     * or a user thread. The Java Virtual Machine exits when the only
     * threads running are all daemon threads.
     * 将此线程标记为守护线程或用户线程。当仅运行的线程都是守护线程时，Java虚拟机退出。
     * <p> This method must be invoked before the thread is started.
     * 在线程启动之前，必须调用此方法。
     * @param  on
     *         if {@code true}, marks this thread as a daemon thread
     *
     * @throws  IllegalThreadStateException
     *          if this thread is {@linkplain #isAlive alive}
     *
     * @throws  SecurityException
     *          if {@link #checkAccess} determines that the current
     *          thread cannot modify this thread
     */
    public final void setDaemon(boolean on) {
        checkAccess();
        if (isAlive()) {
            throw new IllegalThreadStateException();
        }
        daemon = on;
    }
    
    /**
     * Tests if this thread is a daemon thread.
     *
     * @return  <code>true</code> if this thread is a daemon thread;
     *          <code>false</code> otherwise.
     * @see     #setDaemon(boolean)
     */
    public final boolean isDaemon() {
        return daemon;
    }
    
    
    /**
     * Determines if the currently running thread has permission to
     * modify this thread.
     * <p>
     * 确定当前运行的线程是否有修改此线程的权限。
     * If there is a security manager, its <code>checkAccess</code> method
     * is called with this thread as its argument. This may result in
     * throwing a <code>SecurityException</code>.
     * 如果有一个安全管理器，它的checkAccess方法将以该线程作为其参数调用。
     * 这可能导致抛出SecurityException。
     * 
     * @exception  SecurityException  if the current thread is not allowed to
     *               access this thread.
     * @see        SecurityManager#checkAccess(Thread)
     */
    public final void checkAccess() {
        SecurityManager security = System.getSecurityManager();
        if (security != null) {
            security.checkAccess(this);
        }
    }
    
    /**
     * Returns a string representation of this thread, including the
     * thread's name, priority, and thread group.
     *
     * @return  a string representation of this thread.
     */
    public String toString() {
        ThreadGroup group = getThreadGroup();
        if (group != null) {
            return "Thread[" + getName() + "," + getPriority() + "," +
                           group.getName() + "]";
        } else {
            return "Thread[" + getName() + "," + getPriority() + "," +
                            "" + "]";
        }
    }
    
    /**
     * Returns the context ClassLoader for this Thread. The context
     * ClassLoader is provided by the creator of the thread for use
     * by code running in this thread when loading classes and resources.
     * If not {@linkplain #setContextClassLoader set}, the default is the
     * ClassLoader context of the parent Thread. The context ClassLoader of the
     * primordial thread is typically set to the class loader used to load the
     * application.
     * 返回该线程的上下文类加载器。上下文类加载器由线程的创建者提供，
     * 用于在加载类和资源时在该线程中运行的代码。如果没有设置，默认值是父线程的类加载器上下文。
     * 原始线程的上下文类加载器通常被设置为用于加载应用程序的类装入器。
     * 
     * <p>If a security manager is present, and the invoker's class loader is not
     * {@code null} and is not the same as or an ancestor of the context class
     * loader, then this method invokes the security manager's {@link
     * SecurityManager#checkPermission(java.security.Permission) checkPermission}
     * method with a {@link RuntimePermission RuntimePermission}{@code
     * ("getClassLoader")} permission to verify that retrieval of the context
     * class loader is permitted.
     * 如果安全管理器存在,和调用者的类加载器不是零,并不等于或上下文类加载器的祖先,
     * 那么这个方法调用安全管理器的checkPermission方法RuntimePermission(“getClassLoader”)
     * 许可验证检索上下文类加载器是允许的。
     * 
     * @return  the context ClassLoader for this Thread, or {@code null}
     *          indicating the system class loader (or, failing that, the
     *          bootstrap class loader)
     *
     * @throws  SecurityException
     *          if the current thread cannot get the context ClassLoader
     *
     * @since 1.2
     */
    @CallerSensitive
    public ClassLoader getContextClassLoader() {
        if (contextClassLoader == null)
            return null;
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            ClassLoader.checkClassLoaderPermission(contextClassLoader,
                                                   Reflection.getCallerClass());
        }
        return contextClassLoader;
    }
    
    /**
     * Sets the context ClassLoader for this Thread. The context
     * ClassLoader can be set when a thread is created, and allows
     * the creator of the thread to provide the appropriate class loader,
     * through {@code getContextClassLoader}, to code running in the thread
     * when loading classes and resources.
     *
     * <p>If a security manager is present, its {@link
     * SecurityManager#checkPermission(java.security.Permission) checkPermission}
     * method is invoked with a {@link RuntimePermission RuntimePermission}{@code
     * ("setContextClassLoader")} permission to see if setting the context
     * ClassLoader is permitted.
     *
     * @param  cl
     *         the context ClassLoader for this Thread, or null  indicating the
     *         system class loader (or, failing that, the bootstrap class loader)
     *
     * @throws  SecurityException
     *          if the current thread cannot set the context ClassLoader
     *
     * @since 1.2
     */
    public void setContextClassLoader(ClassLoader cl) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            sm.checkPermission(new RuntimePermission("setContextClassLoader"));
        }
        contextClassLoader = cl;
    }
    
    /**
     * Returns <tt>true</tt> if and only if the current thread holds the
     * monitor lock on the specified object.
     *
     * <p>This method is designed to allow a program to assert that
     * the current thread already holds a specified lock:
     * <pre>
     *     assert Thread.holdsLock(obj);
     * </pre>
     *
     * @param  obj the object on which to test lock ownership
     * @throws NullPointerException if obj is <tt>null</tt>
     * @return <tt>true</tt> if the current thread holds the monitor lock on
     *         the specified object.
     * @since 1.4
     */
    public static native boolean holdsLock(Object obj);
    
    
    /**
     * Returns the identifier of this Thread.  The thread ID is a positive
     * <tt>long</tt> number generated when this thread was created.
     * The thread ID is unique and remains unchanged during its lifetime.
     * When a thread is terminated, this thread ID may be reused.
     *
     * @return this thread's ID.
     * @since 1.5
     */
    public long getId() {
        return tid;
    }

    /**
     * A thread state.  A thread can be in one of the following states:
     * <ul>
     * <li>{@link #NEW}<br>
     *     A thread that has not yet started is in this state.
     *     </li>
     * <li>{@link #RUNNABLE}<br>
     *     A thread executing in the Java virtual machine is in this state.
     *     </li>
     * <li>{@link #BLOCKED}<br>
     *     A thread that is blocked waiting for a monitor lock
     *     is in this state.
     *     </li>
     * <li>{@link #WAITING}<br>
     *     A thread that is waiting indefinitely for another thread to
     *     perform a particular action is in this state.
     *     </li>
     * <li>{@link #TIMED_WAITING}<br>
     *     A thread that is waiting for another thread to perform an action
     *     for up to a specified waiting time is in this state.
     *     </li>
     * <li>{@link #TERMINATED}<br>
     *     A thread that has exited is in this state.
     *     </li>
     * </ul>
     *
     * <p>
     * A thread can be in only one state at a given point in time.
     * These states are virtual machine states which do not reflect
     * any operating system thread states.
     *
     * @since   1.5
     * @see #getState
     */
    public enum State {
        /**
         * Thread state for a thread which has not yet started.
         */
        NEW,

        /**
         * Thread state for a runnable thread.  A thread in the runnable
         * state is executing in the Java virtual machine but it may
         * be waiting for other resources from the operating system
         * such as processor.
         */
        RUNNABLE,

        /**
         * Thread state for a thread blocked waiting for a monitor lock.
         * A thread in the blocked state is waiting for a monitor lock
         * to enter a synchronized block/method or
         * reenter a synchronized block/method after calling
         * {@link Object#wait() Object.wait}.
         * 线程状态，用于阻塞等待监视器锁的线程。
         * 阻塞状态的线程正在等待监视器锁进入同步块/方法，
         * 或在调用object后重新输入同步块/方法。
         */
        BLOCKED,

        /**
         * Thread state for a waiting thread.
         * A thread is in the waiting state due to calling one of the
         * following methods:
         * <ul>
         *   <li>{@link Object#wait() Object.wait} with no timeout</li>
         *   <li>{@link #join() Thread.join} with no timeout</li>
         *   <li>{@link LockSupport#park() LockSupport.park}</li>
         * </ul>
         *
         * <p>A thread in the waiting state is waiting for another thread to
         * perform a particular action.
         *
         * For example, a thread that has called <tt>Object.wait()</tt>
         * on an object is waiting for another thread to call
         * <tt>Object.notify()</tt> or <tt>Object.notifyAll()</tt> on
         * that object. A thread that has called <tt>Thread.join()</tt>
         * is waiting for a specified thread to terminate.
         */
        WAITING,

        /**
         * Thread state for a waiting thread with a specified waiting time.
         * A thread is in the timed waiting state due to calling one of
         * the following methods with a specified positive waiting time:
         * <ul>
         *   <li>{@link #sleep Thread.sleep}</li>
         *   <li>{@link Object#wait(long) Object.wait} with timeout</li>
         *   <li>{@link #join(long) Thread.join} with timeout</li>
         *   <li>{@link LockSupport#parkNanos LockSupport.parkNanos}</li>
         *   <li>{@link LockSupport#parkUntil LockSupport.parkUntil}</li>
         * </ul>
         */
        TIMED_WAITING,

        /**
         * Thread state for a terminated thread.
         * The thread has completed execution.
         */
        TERMINATED;
    }
    
    /**
     * Returns the state of this thread.
     * This method is designed for use in monitoring of the system state,
     * not for synchronization control.
     *
     * @return this thread's state.
     * @since 1.5
     */
    public State getState() {
        // get current thread state
        return sun.misc.VM.toThreadState(threadStatus);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
