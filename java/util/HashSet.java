/**  
 * Project Name:collection-source  
 * File Name:HashSet.java  
 * Package Name:source.java.util  
 * Date:2018年3月13日下午1:57:10  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 * This class implements the <tt>Set</tt> interface, backed by a hash table
 * (actually a <tt>HashMap</tt> instance).  It makes no guarantees as to the
 * iteration order of the set; in particular, it does not guarantee that the
 * order will remain constant over time.  This class permits the <tt>null</tt>
 * element.
 * 这个类实现了由散列表（实际上是HashMap实例）支持的Set接口。
 * 它不能保证集合的迭代顺序;特别是，它不能保证顺序会随时间而保持不变。这个类允许空元素。
 * 
 * <p>This class offers constant time performance for the basic operations
 * (<tt>add</tt>, <tt>remove</tt>, <tt>contains</tt> and <tt>size</tt>),
 * assuming the hash function disperses the elements properly among the
 * buckets.  Iterating over this set requires time proportional to the sum of
 * the <tt>HashSet</tt> instance's size (the number of elements) plus the
 * "capacity" of the backing <tt>HashMap</tt> instance (the number of
 * buckets).  Thus, it's very important not to set the initial capacity too
 * high (or the load factor too low) if iteration performance is important.
 * 
 * 这个类为基本操作提供了持续的时间性能（add、remove、contains和size），假设哈希函数在bucket中正确地分散了元素。
 * 迭代这个集合需要时间与HashSet实例的大小（元素的数量）和支持HashMap实例的“容量”（桶数）的总和成比例。
 * 因此，如果迭代性能很重要，那么不将初始容量设置得太高（或者负载因素过低）是非常重要的。
 * 
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access a hash set concurrently, and at least one of
 * the threads modifies the set, it <i>must</i> be synchronized externally.
 * This is typically accomplished by synchronizing on some object that
 * naturally encapsulates the set.
 *
 * If no such object exists, the set should be "wrapped" using the
 * {@link Collections#synchronizedSet Collections.synchronizedSet}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the set:<pre>
 *   Set s = Collections.synchronizedSet(new HashSet(...));</pre>
 * 注意，这个实现不是同步的。
 * 如果多个线程并发地访问一个散列集，并且至少有一个线程修改了集合，那么它必须在外部同步。
 * 这通常是通过在某些对象上同步完成的，这些对象自然地封装了集合。
 * 如果不存在这样的对象，则应该使用集合来“包装”集合。synchronizedSet方法。这最好在创建时完成，以防止意外的不同步访问设置：
 *  Set s = Collections.synchronizedSet(new HashSet(...));
 *  
 * <p>The iterators returned by this class's <tt>iterator</tt> method are
 * <i>fail-fast</i>: if the set is modified at any time after the iterator is
 * created, in any way except through the iterator's own <tt>remove</tt>
 * method, the Iterator throws a {@link ConcurrentModificationException}.
 * Thus, in the face of concurrent modification, the iterator fails quickly
 * and cleanly, rather than risking arbitrary, non-deterministic behavior at
 * an undetermined time in the future.
 * 这返回的迭代器类的迭代器方法是快速失败:如果设置修改创建迭代器后,任何时候以任何方式除非通过迭代器的删除方法,迭代器抛出ConcurrentModificationException。
 * 因此，在并发修改的情况下，迭代器会快速而干净地失败，而不是在未来的不确定的时间冒着不确定的、不确定的行为的风险。
 * 
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw <tt>ConcurrentModificationException</tt> on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness: <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 * 
	注意，迭代器的故障快速行为无法得到保证，一般来说，在不同步的并发修改的情况下，不可能做出任何硬的保证。
	快速失败迭代器抛出ConcurrentModificationException力所能及。
	因此，编写一个依赖于此异常的程序的程序是错误的：迭代器的故障快速行为应该只用于检测错误。
 * 
 * 
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     Set
 * @see     TreeSet
 * @see     HashMap
 * @since   1.2
 */

public class HashSet<E> 
	extends AbstractSet<E>
	implements Set<E>, Cloneable, java.io.Serializable
{

	/**  
	 * serialVersionUID:  
	 */
	private static final long serialVersionUID = 4429519926898563831L;

	@Override
	public Iterator<E> iterator() {
		  
		//  Auto-generated method stub  
		return null;
	}

	@Override
	public int size() {
		  
		//  Auto-generated method stub  
		return 0;
	}

}
  
