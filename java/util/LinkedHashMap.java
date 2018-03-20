/**  
 * Project Name:collection-source  
 * File Name:LinkHashMap.java  
 * Package Name:source.java.util  
 * Date:2018年3月20日上午10:01:35  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Spliterator;
import java.util.TreeMap;

/**
 * <p>Hash table and linked list implementation of the <tt>Map</tt> interface,
 * with predictable iteration order.  This implementation differs from
 * <tt>HashMap</tt> in that it maintains a doubly-linked list running through
 * all of its entries.  This linked list defines the iteration ordering,
 * which is normally the order in which keys were inserted into the map
 * (<i>insertion-order</i>).  Note that insertion order is not affected
 * if a key is <i>re-inserted</i> into the map.  (A key <tt>k</tt> is
 * reinserted into a map <tt>m</tt> if <tt>m.put(k, v)</tt> is invoked when
 * <tt>m.containsKey(k)</tt> would return <tt>true</tt> immediately prior to
 * the invocation.)
 * 映射接口的哈希表和链表实现，具有可预测的迭代顺序。
 * 这个实现与HashMap的区别在于，它维护一个双链表，它在所有的条目中运行。
 * 这个链表定义了迭代顺序，这通常是将键插入到map（插入顺序）中的顺序。
 * 注意，如果一个键被重新插入到映射中，插入顺序不会受到影响。
 * （如果m是m，那么k就会被重新插入到地图m中。put（k，v）被调用，此时m.集装箱（k）将在调用之前立即返回true）。
 * 
 * <p>This implementation spares its clients from the unspecified, generally
 * chaotic ordering provided by {@link HashMap} (and {@link Hashtable}),
 * without incurring the increased cost associated with {@link TreeMap}.  It
 * can be used to produce a copy of a map that has the same order as the
 * original, regardless of the original map's implementation:
 * <pre>
 *     void foo(Map m) {
 *         Map copy = new LinkedHashMap(m);
 *         ...
 *     }
 * </pre>
 * This technique is particularly useful if a module takes a map on input,
 * copies it, and later returns results whose order is determined by that of
 * the copy.  (Clients generally appreciate having things returned in the same
 * order they were presented.)
 * 
 * 该实现使其客户不必从HashMap（和Hashtable）提供的未指明的、通常混乱的排序中，而不会导致与TreeMap相关的成本增加。
 * 它可以用于生成与原始地图具有相同顺序的地图副本，而不考虑原始地图的实现：
 * 
 * 果模块对输入进行映射，对其进行复制，并随后返回由该副本所决定的顺序的结果，则该技术尤其有用。（客户通常会很欣赏以相同的顺序返回的东西。）
 *
 * <p>A special {@link #LinkedHashMap(int,float,boolean) constructor} is
 * provided to create a linked hash map whose order of iteration is the order
 * in which its entries were last accessed, from least-recently accessed to
 * most-recently (<i>access-order</i>).  This kind of map is well-suited to
 * building LRU caches.  Invoking the {@code put}, {@code putIfAbsent},
 * {@code get}, {@code getOrDefault}, {@code compute}, {@code computeIfAbsent},
 * {@code computeIfPresent}, or {@code merge} methods results
 * in an access to the corresponding entry (assuming it exists after the
 * invocation completes). The {@code replace} methods only result in an access
 * of the entry if the value is replaced.  The {@code putAll} method generates one
 * entry access for each mapping in the specified map, in the order that
 * key-value mappings are provided by the specified map's entry set iterator.
 * <i>No other methods generate entry accesses.</i>  In particular, operations
 * on collection-views do <i>not</i> affect the order of iteration of the
 * backing map.
 * 提供了一个特殊的构造函数来创建一个链接的散列映射，它的迭代顺序是最后一次访问的顺序，从最近的访问到最近的（accessorder）。
 * 这种映射非常适合构建LRU缓存。调用put、putifab发送、get、getOrDefault、compute、computeifab发送、computeIfPresent或merge方法会导致对相应条目的访问（假设在调用完成后存在）
 * 。替换的方法只会在值被替换时导致条目的访问。
 * pu高个方法为指定映射中的每个映射生成一个入口访问，顺序是由指定的map的入口集迭代器提供的键-值映射。
 * 没有其他方法生成入口访问。特别地，在集合视图上的操作不会影响备份映射的迭代顺序。
 * 
 * <p>The {@link #removeEldestEntry(Map.Entry)} method may be overridden to
 * impose a policy for removing stale mappings automatically when new mappings
 * are added to the map.
 * removeEldestEntry（map.entry）方法可能被覆盖，以便在将新的映射添加到地图时自动删除过时的映射。
 * 
 * <p>This class provides all of the optional <tt>Map</tt> operations, and
 * permits null elements.  Like <tt>HashMap</tt>, it provides constant-time
 * performance for the basic operations (<tt>add</tt>, <tt>contains</tt> and
 * <tt>remove</tt>), assuming the hash function disperses elements
 * properly among the buckets.  Performance is likely to be just slightly
 * below that of <tt>HashMap</tt>, due to the added expense of maintaining the
 * linked list, with one exception: Iteration over the collection-views
 * of a <tt>LinkedHashMap</tt> requires time proportional to the <i>size</i>
 * of the map, regardless of its capacity.  Iteration over a <tt>HashMap</tt>
 * is likely to be more expensive, requiring time proportional to its
 * <i>capacity</i>.
 * 该类提供了所有可选映射操作，并允许空元素。
 * 像HashMap一样，它为基本操作（添加、包含和删除）提供了常量时间性能，假设散列函数在bucket中适当地分散元素。
 * 由于维护链表的成本增加，性能可能会略低于HashMap，但有一个例外：在LinkedHashMap的集合视图上的迭代需要时间与地图的大小成比例，不管它的容量是多少。
 * 在HashMap上的迭代可能更昂贵，需要时间与容量成比例。
 * 
 * <p>A linked hash map has two parameters that affect its performance:
 * <i>initial capacity</i> and <i>load factor</i>.  They are defined precisely
 * as for <tt>HashMap</tt>.  Note, however, that the penalty for choosing an
 * excessively high value for initial capacity is less severe for this class
 * than for <tt>HashMap</tt>, as iteration times for this class are unaffected
 * by capacity.
 * 一个链接的哈希映射有两个参数影响它的性能：初始容量和负载系数。
 * 它们被精确地定义为HashMap。然而，请注意，对于该类来说，选择过高的初始容量的代价比HashMap要轻，因为该类的迭代时间不受容量的影响。
 * 
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a linked hash map concurrently, and at least
 * one of the threads modifies the map structurally, it <em>must</em> be
 * synchronized externally.  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the map.
 *
 * If no such object exists, the map should be "wrapped" using the
 * {@link Collections#synchronizedMap Collections.synchronizedMap}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the map:<pre>
 *   Map m = Collections.synchronizedMap(new LinkedHashMap(...));</pre>
 *
 * A structural modification is any operation that adds or deletes one or more
 * mappings or, in the case of access-ordered linked hash maps, affects
 * iteration order.  In insertion-ordered linked hash maps, merely changing
 * the value associated with a key that is already contained in the map is not
 * a structural modification.  <strong>In access-ordered linked hash maps,
 * merely querying the map with <tt>get</tt> is a structural modification.
 * </strong>)
 * 注意，这个实现不是同步的。如果多个线程并发地访问相连的散列映射，并且至少有一个线程在结构上修改了映射，那么它必须在外部同步。这通常是通过在一些自然封装映射的对象上同步实现的。
 * 如果不存在这样的对象，则该映射应使用集合“包装”。synchronizedMap方法。这在创建时是最好的，以防止意外的不同步的对映射的访问：
 *  Map m = Collections.synchronizedMap(new LinkedHashMap(...));
 *  
 *  
 * <p>The iterators returned by the <tt>iterator</tt> method of the collections
 * returned by all of this class's collection view methods are
 * <em>fail-fast</em>: if the map is structurally modified at any time after
 * the iterator is created, in any way except through the iterator's own
 * <tt>remove</tt> method, the iterator will throw a {@link
 * ConcurrentModificationException}.  Thus, in the face of concurrent
 * modification, the iterator fails quickly and cleanly, rather than risking
 * arbitrary, non-deterministic behavior at an undetermined time in the future.
 * 结构修改是添加或删除一个或多个映射的任何操作，或者，在access-order链接散列映射的情况下，会影响迭代顺序。
 * 在插入排序的链接散列映射中，仅仅更改与已包含在映射中的键相关的值不是结构修改。在access-有序的链接哈希映射中，仅仅用get来查询映射是一个结构修改。）
 * 
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:   <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 * 返回的迭代器返回的集合的迭代器方法这门课的所有集合视图方法快速失败:如果结构修改地图创建迭代器后,任何时候以任何方式除非通过迭代器的删除方法,
 * 迭代器将抛出ConcurrentModificationException。
 * 因此，在并发修改的情况下，迭代器会快速而干净地失败，而不是在未来某个时间不确定的时间内冒任意的、不确定的行为。
 * 
 * 请注意，迭代器的故障快速行为不能保证，因为一般来说，在不同步的并发修改的情况下，不可能做出任何硬保证。快速失败迭代器抛出ConcurrentModificationException力所能及。
 * 因此，编写一个依赖于此异常的程序来判断其正确性是错误的：迭代器的故障快速行为应该只用于检测错误。
 * 
 * <p>The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are
 * <em><a href="Spliterator.html#binding">late-binding</a></em>,
 * <em>fail-fast</em>, and additionally report {@link Spliterator#ORDERED}.
 *
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implNote
 * The spliterators returned by the spliterator method of the collections
 * returned by all of this class's collection view methods are created from
 * the iterators of the corresponding collections.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see     Object#hashCode()
 * @see     Collection
 * @see     Map
 * @see     HashMap
 * @see     TreeMap
 * @see     Hashtable
 * @since   1.4
 */
public class LinkedHashMap<K,V>  extends HashMap<K,V> implements Map<K,V>{

	/**  
	 * serialVersionUID:  
	 */
	private static final long serialVersionUID = -2107438080363804332L;
	
    /*
     * Implementation note.  A previous version of this class was
     * internally structured a little differently. Because superclass
     * HashMap now uses trees for some of its nodes, class
     * LinkedHashMap.Entry is now treated as intermediary node class
     * that can also be converted to tree form. The name of this
     * class, LinkedHashMap.Entry, is confusing in several ways in its
     * current context, but cannot be changed.  Otherwise, even though
     * it is not exported outside this package, some existing source
     * code is known to have relied on a symbol resolution corner case
     * rule in calls to removeEldestEntry that suppressed compilation
     * errors due to ambiguous usages. So, we keep the name to
     * preserve unmodified compilability.
     *
     * The changes in node classes also require using two fields
     * (head, tail) rather than a pointer to a header node to maintain
     * the doubly-linked before/after list. This class also
     * previously used a different style of callback methods upon
     * access, insertion, and removal.
     */

    /**
     * HashMap.Node subclass for normal LinkedHashMap entries.
     */
    static class Entry<K,V> extends HashMap.Node<K,V> {
        Entry<K,V> before, after;
        Entry(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
    
    /**
     * The head (eldest) of the doubly linked list.
     */
    transient LinkedHashMap.Entry<K,V> head;

    /**
     * The tail (youngest) of the doubly linked list.
     */
    transient LinkedHashMap.Entry<K,V> tail;
    
    /**
     * The iteration ordering method for this linked hash map: <tt>true</tt>
     * for access-order, <tt>false</tt> for insertion-order.
     * 这个链接的哈希映射的迭代排序方法：对于accessorder来说是正确的，对于插入顺序来说是错误的。
     * @serial
     */
    final boolean accessOrder;
    
    private void linkNodeLast(LinkedHashMap.Entry<K,V> p) {
        LinkedHashMap.Entry<K,V> last = tail;
        tail = p;
        if (last == null)
            head = p;
        else {
            p.before = last;
            last.after = p;
        }
    }
}
  
