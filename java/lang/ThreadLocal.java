package source.java.lang;

import java.lang.ThreadLocal.SuppliedThreadLocal;
import java.lang.ThreadLocal.ThreadLocalMap;
import java.lang.ThreadLocal.ThreadLocalMap.Entry;
import java.lang.ref.WeakReference;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

/**
 * This class provides thread-local variables.  These variables differ from
 * their normal counterparts in that each thread that accesses one (via its
 * {@code get} or {@code set} method) has its own, independently initialized
 * copy of the variable.  {@code ThreadLocal} instances are typically private
 * static fields in classes that wish to associate state with a thread (e.g.,
 * a user ID or Transaction ID).
 * 这个类提供了线程局部变量。这些变量与正常对应的变量不同，
 * 因为每个访问一个线程的线程(通过它的get或set方法)都有自己的、独立初始化的变量副本。
 * ThreadLocal实例通常是类中的私有静态字段，希望将状态与线程关联(例如，用户ID或事务ID)。
      例如，下面的类在每个线程中生成惟一的标识符。
      线程的id将在第一次调用ThreadId.get()时被分配，并在后续调用中保持不变。

 * <p>For example, the class below generates unique identifiers local to each
 * thread.
 * A thread's id is assigned the first time it invokes {@code ThreadId.get()}
 * and remains unchanged on subsequent calls.
 * <pre>
 * import java.util.concurrent.atomic.AtomicInteger;
 *
 * public class ThreadId {
 *     // Atomic integer containing the next thread ID to be assigned
 *     private static final AtomicInteger nextId = new AtomicInteger(0);
 *
 *     // Thread local variable containing each thread's ID
 *     private static final ThreadLocal&lt;Integer&gt; threadId =
 *         new ThreadLocal&lt;Integer&gt;() {
 *             &#64;Override protected Integer initialValue() {
 *                 return nextId.getAndIncrement();
 *         }
 *     };
 *
 *     // Returns the current thread's unique ID, assigning it if necessary
 *     public static int get() {
 *         return threadId.get();
 *     }
 * }
 * </pre>
 * <p>Each thread holds an implicit reference to its copy of a thread-local
 * variable as long as the thread is alive and the {@code ThreadLocal}
 * instance is accessible; after a thread goes away, all of its copies of
 * thread-local instances are subject to garbage collection (unless other
 * references to these copies exist).
 * 只要线程是活的，并且ThreadLocal实例是可访问的，
 * 每个线程都包含对线程局部变量的副本的隐式引用;
 * 线程消失后，所有线程本地实例的副本都将被垃圾收集(除非存在其他引用)。
 * 
 * @author  Josh Bloch and Doug Lea
 * @since   1.2
 */
public class ThreadLocal<T> {

    /**
     * ThreadLocals rely on per-thread linear-probe hash maps attached
     * to each thread (Thread.threadLocals and
     * inheritableThreadLocals).  The ThreadLocal objects act as keys,
     * searched via threadLocalHashCode.  This is a custom hash code
     * (useful only within ThreadLocalMaps) that eliminates collisions
     * in the common case where consecutively constructed ThreadLocals
     * are used by the same threads, while remaining well-behaved in
     * less common cases.
     * 
     * threadlocal依赖于每个线程(线程)的每个线程的线性探测散列映射。
     * threadlocal和inheritablethreadlocal)。
     * ThreadLocal对象充当键，通过threadLocalHashCode进行搜索。
     * 这是一个自定义哈希代码(仅在threadlocalmap中有用)，它消除了常见情况下的冲突，
     * 在这种情况下，连续构建的threadlocal被相同的线程使用，而在不常见的情况下保持良好的行为。
     */
    private final int threadLocalHashCode = nextHashCode();
    
    /**
     * The next hash code to be given out. Updated atomically. Starts at
     * zero.
     */
    private static AtomicInteger nextHashCode =
        new AtomicInteger();
    
    /**
     * The difference between successively generated hash codes - turns
     * implicit sequential thread-local IDs into near-optimally spread
     * multiplicative hash values for power-of-two-sized tables.
     * 连续生成的哈希码之间的区别——将隐式序列线程本地id转换成几乎最优的两级表的乘法哈希值。
     * 
     */
    private static final int HASH_INCREMENT = 0x61c88647;
    
    /**
     * Returns the next hash code.
     */
    private static int nextHashCode() {
        return nextHashCode.getAndAdd(HASH_INCREMENT);
    }
    
    /**
     * Returns the current thread's "initial value" for this
     * thread-local variable.  This method will be invoked the first
     * time a thread accesses the variable with the {@link #get}
     * method, unless the thread previously invoked the {@link #set}
     * method, in which case the {@code initialValue} method will not
     * be invoked for the thread.  Normally, this method is invoked at
     * most once per thread, but it may be invoked again in case of
     * subsequent invocations of {@link #remove} followed by {@link #get}.
     * 为这个线程局部变量返回当前线程的“初始值”。
     * 该方法将在第一次使用get方法访问变量时调用该方法，除非该线程之前调用了set方法，
     * 在这种情况下，不会为线程调用initialValue方法。
     * 通常，每个线程最多调用此方法一次，但在随后进行删除操作之后，可能会再次调用该方法。
     * 
     * <p>This implementation simply returns {@code null}; if the
     * programmer desires thread-local variables to have an initial
     * value other than {@code null}, {@code ThreadLocal} must be
     * subclassed, and this method overridden.  Typically, an
     * anonymous inner class will be used.
     * 这个实现只返回null;如果程序员希望线程局部变量的初始值大于null，
     * 那么ThreadLocal必须被子类化，并且这个方法被重写。
     * 通常，将使用匿名内部类。
     * 
     * @return the initial value for this thread-local
     */
    protected T initialValue() {
        return null;
    }
    
    /**
     * Creates a thread local variable. The initial value of the variable is
     * determined by invoking the {@code get} method on the {@code Supplier}.
     * 创建一个线程局部变量。变量的初始值是通过调用供应商的get方法来确定的。
     * @param <S> the type of the thread local's value
     * 线程本地值的类型。
     * @param supplier the supplier to be used to determine the initial value
     * 供应商应被用于确定初始值。
     * @return a new thread local variable
     * 一个新的线程局部变量。
     * @throws NullPointerException if the specified supplier is null
     * @since 1.8
     */
    public static <S> ThreadLocal<S> withInitial(Supplier<? extends S> supplier) {
        return new SuppliedThreadLocal<>(supplier);
    }
    
    /**
     * Creates a thread local variable.
     * @see #withInitial(java.util.function.Supplier)
     */
    public ThreadLocal() {
    }
    
    /**
     * Returns the value in the current thread's copy of this
     * thread-local variable.  If the variable has no value for the
     * current thread, it is first initialized to the value returned
     * by an invocation of the {@link #initialValue} method.
     * 返回该线程局部变量的当前线程副本中的值。如果该变量对当前线程没有任何价值，
     * 那么它将首先被初始化到由initialValue方法调用返回的值。
     * 
     * @return the current thread's value of this thread-local
     */
    public T get() {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null) {
            ThreadLocalMap.Entry e = map.getEntry(this);
            if (e != null) {
                @SuppressWarnings("unchecked")
                T result = (T)e.value;
                return result;
            }
        }
        return setInitialValue();
    }
    
    
    /**
     * Variant of set() to establish initialValue. Used instead
     * of set() in case user has overridden the set() method.
     *
     * @return the initial value
     */
    private T setInitialValue() {
        T value = initialValue();
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
        return value;
    }
    
    
    /**
     * Sets the current thread's copy of this thread-local variable
     * to the specified value.  Most subclasses will have no need to
     * override this method, relying solely on the {@link #initialValue}
     * method to set the values of thread-locals.
     *
     * @param value the value to be stored in the current thread's copy of
     *        this thread-local.
     */
    public void set(T value) {
        Thread t = Thread.currentThread();
        ThreadLocalMap map = getMap(t);
        if (map != null)
            map.set(this, value);
        else
            createMap(t, value);
    }
    
    /**
     * Removes the current thread's value for this thread-local
     * variable.  If this thread-local variable is subsequently
     * {@linkplain #get read} by the current thread, its value will be
     * reinitialized by invoking its {@link #initialValue} method,
     * unless its value is {@linkplain #set set} by the current thread
     * in the interim.  This may result in multiple invocations of the
     * {@code initialValue} method in the current thread.
     * 为这个线程局部变量移除当前线程的值。
     * 如果该线程局部变量随后被当前线程读取，
     * 那么它的值将通过调用initialValue方法来重新初始化，除非它的值是由当前线程在过渡期间设置的。
     * 这可能会导致当前线程中的initialValue方法的多个调用。
     * 
     * @since 1.5
     */
     public void remove() {
         ThreadLocalMap m = getMap(Thread.currentThread());
         if (m != null)
             m.remove(this);
     }
     
     /**
      * Get the map associated with a ThreadLocal. Overridden in
      * InheritableThreadLocal.
      * 获取与ThreadLocal关联的映射。InheritableThreadLocal覆盖。
      * @param  t the current thread
      * @return the map
      */
     ThreadLocalMap getMap(Thread t) {
         return t.threadLocals;
     }
     
     /**
      * Create the map associated with a ThreadLocal. Overridden in
      * InheritableThreadLocal.
      *
      * @param t the current thread
      * @param firstValue value for the initial entry of the map
      */
     void createMap(Thread t, T firstValue) {
         t.threadLocals = new ThreadLocalMap(this, firstValue);
     }
     
     /**
      * Factory method to create map of inherited thread locals.
      * Designed to be called only from Thread constructor.
      *
      * @param  parentMap the map associated with parent thread
      * @return a map containing the parent's inheritable bindings
      */
     static ThreadLocalMap createInheritedMap(ThreadLocalMap parentMap) {
         return new ThreadLocalMap(parentMap);
     }
     
     /**
      * Method childValue is visibly defined in subclass
      * InheritableThreadLocal, but is internally defined here for the
      * sake of providing createInheritedMap factory method without
      * needing to subclass the map class in InheritableThreadLocal.
      * This technique is preferable to the alternative of embedding
      * instanceof tests in methods.
      * 方法childValue在子类中被定义为可继承的threadlocal，
      * 但是在这里定义的目的是为了提供createinheritedmapfactory方法，
      * 而不需要子类化继承threadlocal的map类。
      * 该技术优于在方法中嵌入instanceof测试的替代方法。
      * 
      */
     T childValue(T parentValue) {
         throw new UnsupportedOperationException();
     }
     
     /**
      * An extension of ThreadLocal that obtains its initial value from
      * the specified {@code Supplier}.
      */
     static final class SuppliedThreadLocal<T> extends ThreadLocal<T> {

         private final Supplier<? extends T> supplier;

         SuppliedThreadLocal(Supplier<? extends T> supplier) {
             this.supplier = Objects.requireNonNull(supplier);
         }

         @Override
         protected T initialValue() {
             return supplier.get();
         }
     }
     
     /**
      * ThreadLocalMap is a customized hash map suitable only for
      * maintaining thread local values. No operations are exported
      * outside of the ThreadLocal class. The class is package private to
      * allow declaration of fields in class Thread.  To help deal with
      * very large and long-lived usages, the hash table entries use
      * WeakReferences for keys. However, since reference queues are not
      * used, stale entries are guaranteed to be removed only when
      * the table starts running out of space.
      * 
      * 
		ThreadLocalMap是一个定制的哈希映射，只适用于维护线程本地值。
		没有在ThreadLocal类之外导出操作。类是包私有的，允许在类线程中声明字段。
		为了帮助处理非常大的和长期使用的用法，哈希表条目使用了对键的弱引用。
		但是，由于引用队列不被使用，所以只有当表开始耗尽空间时，过期的条目才被保证被删除。
      */
     
     static class ThreadLocalMap {

         /**
          * The entries in this hash map extend WeakReference, using
          * its main ref field as the key (which is always a
          * ThreadLocal object).  Note that null keys (i.e. entry.get()
          * == null) mean that the key is no longer referenced, so the
          * entry can be expunged from table.  Such entries are referred to
          * as "stale entries" in the code that follows.
          * 
          * 弱引用对象，它们不会阻止它们的指示被完成，最终完成，然后被回收。
          * 弱引用通常用于实现规范化映射。
          * 假设垃圾收集器在某个时间点确定一个对象是弱可达的。
          * 在那个时候，它将原子地清除所有对该对象的弱引用，以及对任何其他弱可访问对象的弱引用，
          * 这些对象通过一个强大的软引用链可到达。与此同时，它将声明所有以前弱可访问的对象是可终结的。
          * 与此同时，或者在以后的某个时间，它将会对注册了引用队列的新清除的弱引用进行排队。
          */
         static class Entry extends WeakReference<ThreadLocal<?>> {
             /** The value associated with this ThreadLocal. */
             Object value;

             Entry(ThreadLocal<?> k, Object v) {
                 super(k);
                 value = v;
             }
             
             
             /**
              * The initial capacity -- MUST be a power of two.
              */
             private static final int INITIAL_CAPACITY = 16;
             
             /**
              * The table, resized as necessary.
              * table.length MUST always be a power of two.
              */
             private Entry[] table;
             
             /**
              * The number of entries in the table.
              */
             private int size = 0;

             /**
              * The next size value at which to resize.
              */
             private int threshold; // Default to 0
             
             /**
              * Set the resize threshold to maintain at worst a 2/3 load factor.
              */
             private void setThreshold(int len) {
                 threshold = len * 2 / 3;
             }
             
             /**
              * Increment i modulo len.
              */
             private static int nextIndex(int i, int len) {
                 return ((i + 1 < len) ? i + 1 : 0);
             }
             
             /**
              * Decrement i modulo len.
              */
             private static int prevIndex(int i, int len) {
                 return ((i - 1 >= 0) ? i - 1 : len - 1);
             }
             
             /**
              * Construct a new map initially containing (firstKey, firstValue).
              * ThreadLocalMaps are constructed lazily, so we only create
              * one when we have at least one entry to put in it.
              */
             ThreadLocalMap(ThreadLocal<?> firstKey, Object firstValue) {
                 table = new Entry[INITIAL_CAPACITY];
                 int i = firstKey.threadLocalHashCode & (INITIAL_CAPACITY - 1);
                 table[i] = new Entry(firstKey, firstValue);
                 size = 1;
                 setThreshold(INITIAL_CAPACITY);
             }
             
             /**
              * Construct a new map including all Inheritable ThreadLocals
              * from given parent map. Called only by createInheritedMap.
              *
              * @param parentMap the map associated with parent thread.
              */
             private ThreadLocalMap(ThreadLocalMap parentMap) {
                 Entry[] parentTable = parentMap.table;
                 int len = parentTable.length;
                 setThreshold(len);
                 table = new Entry[len];

                 for (int j = 0; j < len; j++) {
                     Entry e = parentTable[j];
                     if (e != null) {
                         @SuppressWarnings("unchecked")
                         ThreadLocal<Object> key = (ThreadLocal<Object>) e.get();
                         if (key != null) {
                             Object value = key.childValue(e.value);
                             Entry c = new Entry(key, value);
                             int h = key.threadLocalHashCode & (len - 1);
                             while (table[h] != null)
                                 h = nextIndex(h, len);
                             table[h] = c;
                             size++;
                         }
                     }
                 }
             }
             
             /**
              * Get the entry associated with key.  This method
              * itself handles only the fast path: a direct hit of existing
              * key. It otherwise relays to getEntryAfterMiss.  This is
              * designed to maximize performance for direct hits, in part
              * by making this method readily inlinable.
              *
              * @param  key the thread local object
              * @return the entry associated with key, or null if no such
              */
             private Entry getEntry(ThreadLocal<?> key) {
                 int i = key.threadLocalHashCode & (table.length - 1);
                 Entry e = table[i];
                 if (e != null && e.get() == key)
                     return e;
                 else
                     return getEntryAfterMiss(key, i, e);
             }
             
             
             /**
              * Version of getEntry method for use when key is not found in
              * its direct hash slot.
              *
              * @param  key the thread local object
              * @param  i the table index for key's hash code
              * @param  e the entry at table[i]
              * @return the entry associated with key, or null if no such
              */
             private Entry getEntryAfterMiss(ThreadLocal<?> key, int i, Entry e) {
                 Entry[] tab = table;
                 int len = tab.length;

                 while (e != null) {
                     ThreadLocal<?> k = e.get();
                     if (k == key)
                         return e;
                     if (k == null)
                         expungeStaleEntry(i);
                     else
                         i = nextIndex(i, len);
                     e = tab[i];
                 }
                 return null;
             }
             
             
             /**
              * Set the value associated with key.
              *
              * @param key the thread local object
              * @param value the value to be set
              */
             private void set(ThreadLocal<?> key, Object value) {

                 // We don't use a fast path as with get() because it is at
                 // least as common to use set() to create new entries as
                 // it is to replace existing ones, in which case, a fast
                 // path would fail more often than not.

                 Entry[] tab = table;
                 int len = tab.length;
                 int i = key.threadLocalHashCode & (len-1);

                 for (Entry e = tab[i];
                      e != null;
                      e = tab[i = nextIndex(i, len)]) {
                     ThreadLocal<?> k = e.get();

                     if (k == key) {
                         e.value = value;
                         return;
                     }

                     if (k == null) {
                         replaceStaleEntry(key, value, i);
                         return;
                     }
                 }

                 tab[i] = new Entry(key, value);
                 int sz = ++size;
                 if (!cleanSomeSlots(i, sz) && sz >= threshold)
                     rehash();
             }
             
             /**
              * Remove the entry for key.
              */
             private void remove(ThreadLocal<?> key) {
                 Entry[] tab = table;
                 int len = tab.length;
                 int i = key.threadLocalHashCode & (len-1);
                 for (Entry e = tab[i];
                      e != null;
                      e = tab[i = nextIndex(i, len)]) {
                     if (e.get() == key) {
                         e.clear();
                         expungeStaleEntry(i);
                         return;
                     }
                 }
             }
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
             
         }
     }
}
