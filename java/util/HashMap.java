/**  
 * Project Name:collection-source  
 * File Name:HashMap.java  
 * Package Name:source.java.util  
 * Date:2018年3月15日下午2:07:50  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Hashtable;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeMap;
import java.util.HashMap.Node;

/**
 * Hash table based implementation of the <tt>Map</tt> interface.  This
 * implementation provides all of the optional map operations, and permits
 * <tt>null</tt> values and the <tt>null</tt> key.  (The <tt>HashMap</tt>
 * class is roughly equivalent to <tt>Hashtable</tt>, except that it is
 * unsynchronized and permits nulls.)  This class makes no guarantees as to
 * the order of the map; in particular, it does not guarantee that the order
 * will remain constant over time.
 * 基于哈希表的映射接口实现。该实现提供了所有可选映射操作，并允许null值和null键。
 * （HashMap类大致相当于Hashtable，只不过它是不同步的，并且允许nulls）。
 * 这个类不能保证地图的顺序;特别是，它不能保证订单会随时间保持不变。
 * 
 * <p>This implementation provides constant-time performance for the basic
 * operations (<tt>get</tt> and <tt>put</tt>), assuming the hash function
 * disperses the elements properly among the buckets.  Iteration over
 * collection views requires time proportional to the "capacity" of the
 * <tt>HashMap</tt> instance (the number of buckets) plus its size (the number
 * of key-value mappings).  Thus, it's very important not to set the initial
 * capacity too high (or the load factor too low) if iteration performance is
 * important.
 * 这个实现为基本操作（get和put）提供恒定的时间性能，假设散列函数在bucket中适当地分散元素。
 * 反复遍历收集视图需要与HashMap实例（bucket数目）的“容量”和它的大小（键值映射的数量）成比例。
 * 因此，如果迭代性能很重要，那么不将初始容量设置得过高（或者负载系数太低）是非常重要的。
 * 
 * <p>An instance of <tt>HashMap</tt> has two parameters that affect its
 * performance: <i>initial capacity</i> and <i>load factor</i>.  The
 * <i>capacity</i> is the number of buckets in the hash table, and the initial
 * capacity is simply the capacity at the time the hash table is created.  The
 * <i>load factor</i> is a measure of how full the hash table is allowed to
 * get before its capacity is automatically increased.  When the number of
 * entries in the hash table exceeds the product of the load factor and the
 * current capacity, the hash table is <i>rehashed</i> (that is, internal data
 * structures are rebuilt) so that the hash table has approximately twice the
 * number of buckets.
 * HashMap的一个实例有两个影响其性能的参数：初始容量和负载因子。
 * 容量是哈希表中bucket的数量，初始容量只是哈希表创建时的容量。
 * 负载因素是衡量哈希表在容量自动增加之前允许的完整程度的一种度量方法。
 * 当哈希表中的条目数超过负荷因子和当前容量的乘积时，哈希表是重做的（即，重新构建内部数据结构），这样哈希表的bucket数量大约是bucket数目的两倍。
 * 
 * <p>As a general rule, the default load factor (.75) offers a good
 * tradeoff between time and space costs.  Higher values decrease the
 * space overhead but increase the lookup cost (reflected in most of
 * the operations of the <tt>HashMap</tt> class, including
 * <tt>get</tt> and <tt>put</tt>).  The expected number of entries in
 * the map and its load factor should be taken into account when
 * setting its initial capacity, so as to minimize the number of
 * rehash operations.  If the initial capacity is greater than the
 * maximum number of entries divided by the load factor, no rehash
 * operations will ever occur.
 * 一般来说，默认的负载因素（0.75）在时间和空间成本之间提供了一个很好的权衡。
 * 较高的值会减少空间开销，但增加查找成本（反映在HashMap类的大多数操作中，包括get和put）。
 * 在设置初始容量时，应该考虑map及其负载因素的预期数量，以便最小化rehash操作的数量。
 * 如果初始的容量大于最大的条目数除以负载因素，则不会发生rehash操作。
 * 
 * <p>If many mappings are to be stored in a <tt>HashMap</tt>
 * instance, creating it with a sufficiently large capacity will allow
 * the mappings to be stored more efficiently than letting it perform
 * automatic rehashing as needed to grow the table.  Note that using
 * many keys with the same {@code hashCode()} is a sure way to slow
 * down performance of any hash table. To ameliorate impact, when keys
 * are {@link Comparable}, this class may use comparison order among
 * keys to help break ties.
 * 如果许多映射都存储在HashMap实例中，那么创建具有足够大容量的映射将使映射能够更有效地存储，而不是让它在需要的时候执行自动重放，以使表增长。
 * 请注意，使用相同hashCode（）的多个键是一种降低哈希表性能的可靠方法。
 * 为了改善影响，当键具有可比性时，这个类可以使用键之间的比较顺序来帮助断开连接。
 * 
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash map concurrently, and at least one of
 * the threads modifies the map structurally, it <i>must</i> be
 * synchronized externally.  (A structural modification is any operation
 * that adds or deletes one or more mappings; merely changing the value
 * associated with a key that an instance already contains is not a
 * structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 *
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new HashMap(...));</pre>
 *
 *注意，这个实现不是同步的。如果多个线程并发地访问一个散列映射，并且至少有一个线程在结构上修改了映射，那么它必须在外部同步。
 *（结构修改是添加或删除一个或多个映射的任何操作;仅仅改变与实例已经包含的键相关的值不是结构修改。）
 *这通常是通过在一些自然封装映射的对象上同步实现的。如果不存在这样的对象，则该映射应使用集合“包装”。
 *synchronizedMap方法。这在创建时是最好的，以防止意外的不同步的对映射的访问：
 *Map m = Collections.synchronizedMap(new HashMap(...));
 *
 * <p>The iterators returned by all of this class's "collection view methods"
 * are <i>fail-fast</i>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the
 * future.
 *
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 * 
	所有这些返回的迭代器类的“集合视图方法”是快速失败:如果地图结构修改创建迭代器后,
	任何时候以任何方式除非通过迭代器的删除方法,迭代器将抛出ConcurrentModificationException。
	因此，在并发修改的情况下，迭代器会快速而干净地失败，而不是在未来某个时间不确定的时间内冒任意的、不确定的行为。
	
	请注意，迭代器的故障快速行为不能保证，因为一般来说，在不同步的并发修改的情况下，不可能做出任何硬保证。
	快速失败迭代器抛出ConcurrentModificationException力所能及。因此，编写一个依赖于此异常的程序来判断其正确性是错误的：迭代器的故障快速行为应该只用于检测错误。
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Doug Lea
 * @author  Josh Bloch
 * @author  Arthur van Hoff
 * @author  Neal Gafter
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.2
 */
public class HashMap<K,V> extends AbstractMap<K,V> implements Map<K,V>,Cloneable, Serializable{

	/**  
	 * serialVersionUID:  
	 */
	private static final long serialVersionUID = -8943799177032558653L;

    /*
     * Implementation notes.
     * 
     * 执行记录。
     *
     * This map usually acts as a binned (bucketed) hash table, but
     * when bins get too large, they are transformed into bins of
     * TreeNodes, each structured similarly to those in
     * java.util.TreeMap. Most methods try to use normal bins, but
     * relay to TreeNode methods when applicable (simply by checking
     * instanceof a node).  Bins of TreeNodes may be traversed and
     * used like any others, but additionally support faster lookup
     * when overpopulated. However, since the vast majority of bins in
     * normal use are not overpopulated, checking for existence of
     * tree bins may be delayed in the course of table methods.
		这个映射通常充当一个binned（桶状）散列表，但是
		当箱子太大时，它们就变成了
		TreeNodes，每一种结构都类似于
		java.util.TreeMap。大多数方法都尝试使用普通的容器，但是
		当适用时，中继到TreeNode方法（简单地通过检查
		实例一个节点)。TreeNodes 可能会被穿越
		和其他的一样使用，但是支持更快的查找
		当人口过剩。然而，由于绝大多数的箱子
		正常的使用不会被过度填充，检查是否存在
		在表方法的过程中，树箱可能会被延迟。
     * 
     * 
     * 
     * 
     *
     *
     * Tree bins (i.e., bins whose elements are all TreeNodes) are
     * ordered primarily by hashCode, but in the case of ties, if two
     * elements are of the same "class C implements Comparable<C>",
     * type then their compareTo method is used for ordering. (We
     * conservatively check generic types via reflection to validate
     * this -- see method comparableClassFor).  The added complexity
     * of tree bins is worthwhile in providing worst-case O(log n)
     * operations when keys either have distinct hashes or are
     * orderable, Thus, performance degrades gracefully under
     * accidental or malicious usages in which hashCode() methods
     * return values that are poorly distributed, as well as those in
     * which many keys share a hashCode, so long as they are also
     * Comparable. (If neither of these apply, we may waste about a
     * factor of two in time and space compared to taking no
     * precautions. But the only known cases stem from poor user
     * programming practices that are already so slow that this makes
     * little difference.)
     *
     * Because TreeNodes are about twice the size of regular nodes, we
     * use them only when bins contain enough nodes to warrant use
     * (see TREEIFY_THRESHOLD). And when they become too small (due to
     * removal or resizing) they are converted back to plain bins.  In
     * usages with well-distributed user hashCodes, tree bins are
     * rarely used.  Ideally, under random hashCodes, the frequency of
     * nodes in bins follows a Poisson distribution
     * (http://en.wikipedia.org/wiki/Poisson_distribution) with a
     * parameter of about 0.5 on average for the default resizing
     * threshold of 0.75, although with a large variance because of
     * resizing granularity. Ignoring variance, the expected
     * occurrences of list size k are (exp(-0.5) * pow(0.5, k) /
     * factorial(k)). The first values are:
     *
     * 0:    0.60653066
     * 1:    0.30326533
     * 2:    0.07581633
     * 3:    0.01263606
     * 4:    0.00157952
     * 5:    0.00015795
     * 6:    0.00001316
     * 7:    0.00000094
     * 8:    0.00000006
     * more: less than 1 in ten million
     *
     * The root of a tree bin is normally its first node.  However,
     * sometimes (currently only upon Iterator.remove), the root might
     * be elsewhere, but can be recovered following parent links
     * (method TreeNode.root()).
     *
     * All applicable internal methods accept a hash code as an
     * argument (as normally supplied from a public method), allowing
     * them to call each other without recomputing user hashCodes.
     * Most internal methods also accept a "tab" argument, that is
     * normally the current table, but may be a new or old one when
     * resizing or converting.
     *
     * When bin lists are treeified, split, or untreeified, we keep
     * them in the same relative access/traversal order (i.e., field
     * Node.next) to better preserve locality, and to slightly
     * simplify handling of splits and traversals that invoke
     * iterator.remove. When using comparators on insertion, to keep a
     * total ordering (or as close as is required here) across
     * rebalancings, we compare classes and identityHashCodes as
     * tie-breakers.
     *
     * The use and transitions among plain vs tree modes is
     * complicated by the existence of subclass LinkedHashMap. See
     * below for hook methods defined to be invoked upon insertion,
     * removal and access that allow LinkedHashMap internals to
     * otherwise remain independent of these mechanics. (This also
     * requires that a map instance be passed to some utility methods
     * that may create new nodes.)
     *
     * The concurrent-programming-like SSA-based coding style helps
     * avoid aliasing errors amid all of the twisty pointer operations.
     */
	
    /**
     * The default initial capacity - MUST be a power of two.
     * 默认的初始容量-必须是2的幂。
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4; // aka 16
    
    /**
     * The maximum capacity, used if a higher value is implicitly specified
     * by either of the constructors with arguments.
     * MUST be a power of two <= 1<<30.
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * The load factor used when none specified in constructor.
     * 在构造函数中没有指定的负载因素。
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;
    
    /**
     * The bin count threshold for using a tree rather than list for a
     * bin.  Bins are converted to trees when adding an element to a
     * bin with at least this many nodes. The value must be greater
     * than 2 and should be at least 8 to mesh with assumptions in
     * tree removal about conversion back to plain bins upon
     * shrinkage.
     * 
     * 用于使用树的bin计数阈值，而不是用于垃圾箱的列表。在至少有这么多节点的情况下，将一个元素添加到垃圾箱时，将把箱子转换为树。
     * 这个值必须大于2，并且应该至少有8个与树移除的假设相吻合，即在收缩时将其转换回普通的箱子。
     */
    static final int TREEIFY_THRESHOLD = 8;
    
    
    /**
     * The bin count threshold for untreeifying a (split) bin during a
     * resize operation. Should be less than TREEIFY_THRESHOLD, and at
     * most 6 to mesh with shrinkage detection under removal.
     * 在重新调整大小的操作过程中，不使一个（分割）的容器不被清除的数量阈值。应该小于treeify阀值，最多6个与收缩检测相啮合。
     */
    static final int UNTREEIFY_THRESHOLD = 6;
    
    /**
     * The smallest table capacity for which bins may be treeified.
     * (Otherwise the table is resized if too many nodes in a bin.)
     * Should be at least 4 * TREEIFY_THRESHOLD to avoid conflicts
     * between resizing and treeification thresholds.
     * 最小的表容量，可以将箱子进行处理。（否则，如果一个容器中有太多节点，那么该表就会被调整。）
     * 至少应该有4个treeify阈值，以避免调整大小和重新调整阈值之间的冲突。
     * 
     */
    static final int MIN_TREEIFY_CAPACITY = 64;
    
    /**
     * Basic hash bin node, used for most entries.  (See below for
     * TreeNode subclass, and in LinkedHashMap for its Entry subclass.)
     */
    static class Node<K,V> implements Map.Entry<K,V> {
    	final int hash;
    	final K key;
    	V value;
    	Node<K,V> next;
    	
    	Node(int hash,K key,V value,Node<K,V> next) {
    		this.hash = hash;
    		this.key = key;
    		this.value = value;
    		this.next = next;
    	}
		@Override
		public final K getKey() {
			return key;
		}

		@Override
		public final V getValue() {
			return value;
		}

		public final String toString() {
			return key + "=" + value;
		}

		@Override
		public V setValue(V newValue) {
			  
			V oldValue = value;
			value = newValue;
			return oldValue;
		}
		
        public final boolean equals(Object o) {
        	if (o == this) {
        		return true;
        	}
        	if (o instanceof Map.Entry) {
        		Map.Entry<?,?> e = (Map.Entry<?,?>)o;
        		if (Objects.equals(key, e.getKey())  && Objects.equals(value, e.getValue()) ) {
        			return true;
        		}
        	}
            return false;
        }
    	
    }
    
    /**
     * Computes key.hashCode() and spreads (XORs) higher bits of hash
     * to lower.  Because the table uses power-of-two masking, sets of
     * hashes that vary only in bits above the current mask will
     * always collide. (Among known examples are sets of Float keys
     * holding consecutive whole numbers in small tables.)  So we
     * apply a transform that spreads the impact of higher bits
     * downward. There is a tradeoff between speed, utility, and
     * quality of bit-spreading. Because many common sets of hashes
     * are already reasonably distributed (so don't benefit from
     * spreading), and because we use trees to handle large sets of
     * collisions in bins, we just XOR some shifted bits in the
     * cheapest possible way to reduce systematic lossage, as well as
     * to incorporate impact of the highest bits that would otherwise
     * never be used in index calculations because of table bounds.
     */
    static final int hash(Object key) {
        int h;
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }
    
    static Class<?> comparableClassFor(Object x) {
        if (x instanceof Comparable) {
            Class<?> c; 
            Type[] ts, as; 
            Type t; 
            ParameterizedType p;
            if ((c = x.getClass()) == String.class)  {
            	return c; // bypass checks
            }              
            if ((ts = c.getGenericInterfaces()) != null) {
                for (int i = 0; i < ts.length; ++i) {
                    if (((t = ts[i]) instanceof ParameterizedType) &&
                        ((p = (ParameterizedType)t).getRawType() ==
                         Comparable.class) &&
                        (as = p.getActualTypeArguments()) != null &&
                        as.length == 1 && as[0] == c) // type arg is c
                        return c;
                }
            }
        }
        return null;
    }
    
    /**
     * Returns k.compareTo(x) if x matches kc (k's screened comparable
     * class), else 0.
     */
    @SuppressWarnings({"rawtypes","unchecked"}) // for cast to Comparable
    static int compareComparables(Class<?> kc, Object k, Object x) {
        return (x == null || x.getClass() != kc ? 0 :
                ((Comparable)k).compareTo(x));
    }
    
    /**
     * Returns a power of two size for the given target capacity.
     */
    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }
    
    /* ---------------- Fields -------------- */

    /**
     * The table, initialized on first use, and resized as
     * necessary. When allocated, length is always a power of two.
     * (We also tolerate length zero in some operations to allow
     * bootstrapping mechanics that are currently not needed.)
     * 
     * 在第一次使用时初始化的表，并根据需要调整大小。当分配时，长度总是2的幂。
     * (在某些操作中，我们还容忍长度为零，以允许目前不需要的自举机制。)
     * 
     */
    transient Node<K,V>[] table;

    /**
     * Holds cached entrySet(). Note that AbstractMap fields are used
     * for keySet() and values().
     * 保存缓存entrySet()。注意，AbstractMap字段用于keySet()和values()。
     */
    transient Set<Map.Entry<K,V>> entrySet;

    /**
     * The number of key-value mappings contained in this map.
     * 此映射中包含的键值映射的数量。
     */
    transient int size;

    /**
     * The number of times this HashMap has been structurally modified
     * Structural modifications are those that change the number of mappings in
     * the HashMap or otherwise modify its internal structure (e.g.,
     * rehash).  This field is used to make iterators on Collection-views of
     * the HashMap fail-fast.  (See ConcurrentModificationException).
     * 这个HashMap在结构上修改结构修改的次数是那些改变HashMap中的映射数量
     * 或修改其内部结构(例如rehash)的那些。
     * 该字段用于使迭代器对HashMap的集合视图快速失效。
     * (见ConcurrentModificationException)。
     */
    transient int modCount;

    /**
     * The next size value at which to resize (capacity * load factor).
     *要调整大小的下一个大小值(容量*负载系数)。
     * @serial
     */
    // (The javadoc description is true upon serialization.
    // Additionally, if the table array has not been allocated, this
    // field holds the initial array capacity, or zero signifying
    // DEFAULT_INITIAL_CAPACITY.)
    int threshold;

    /**
     * The load factor for the hash table.
     *
     * @serial
     */
    final float loadFactor;
}
  
