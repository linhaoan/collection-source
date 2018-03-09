/**  
 * Project Name:collection-source  
 * File Name:AbstractList.java  
 * Package Name:source.java.util  
 * Date:2018年3月9日上午11:01:26  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;

/**
 * This class provides a skeletal implementation of the {@link List}
 * interface to minimize the effort required to implement this interface
 * backed by a "random access" data store (such as an array).  For sequential
 * access data (such as a linked list), {@link AbstractSequentialList} should
 * be used in preference to this class.<br/>
 * 这个类提供了List接口的一个框架实现，以最小化实现这个接口所需要的工作量，这个接口是由“随机访问”数据存储（如数组）所支持的。
 * 对于顺序访问数据（如链表），应该使用AbstractSequentialList来选择这个类。
 * 
 * <p>To implement an unmodifiable list, the programmer needs only to extend
 * this class and provide implementations for the {@link #get(int)} and
 * {@link List#size() size()} methods.<br/>
 * 为了实现不可修改的列表，程序员只需要扩展这个类并为get（int）和size（）方法提供实现。
 * 
 * <p>To implement a modifiable list, the programmer must additionally
 * override the {@link #set(int, Object) set(int, E)} method (which otherwise
 * throws an {@code UnsupportedOperationException}).  If the list is
 * variable-size the programmer must additionally override the
 * {@link #add(int, Object) add(int, E)} and {@link #remove(int)} methods.<br/>
 * 实现一个可修改的列表,程序员必须另外覆盖set(int,E)方法(否则抛出UnsupportedOperationException)方式。
 * 如果列表是可变的，那么程序员必须另外覆盖add（int，E）和remove（int）方法。
 * 
 * <p>The programmer should generally provide a void (no argument) and collection
 * constructor, as per the recommendation in the {@link Collection} interface
 * specification.<br/>
 * 根据集合接口规范中的建议，程序员通常应该提供一个void（没有参数）和集合构造函数。
 * 
 * <p>Unlike the other abstract collection implementations, the programmer does
 * <i>not</i> have to provide an iterator implementation; the iterator and
 * list iterator are implemented by this class, on top of the "random access"
 * methods:
 * {@link #get(int)},
 * {@link #set(int, Object) set(int, E)},
 * {@link #add(int, Object) add(int, E)} and
 * {@link #remove(int)}.
 * 
 * 与其他抽象的集合实现不同，程序员不必提供迭代器实现;
 * 迭代器和list迭代器是由这个类实现的，在“随机访问”方法之上:get（int）、set（int、E）、add（int、E）和remove（int）。
 * 
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * collection being implemented admits a more efficient implementation.
 * 这个类中的每个非抽象方法的文档详细描述了它的实现。如果正在实现的集合可以实现更有效的实现，那么这些方法中的每一个都可能被覆盖。
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @since 1.2
 */
public abstract class AbstractList<E> extends AbstractCollection<E> implements List<E>{

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     * 唯一的构造函数。（对于子类构造函数的调用，通常是隐式的。）
     */
    protected AbstractList() {
    }
    
    /**
     * Appends the specified element to the end of this list (optional
     * operation).<br/>
     * 将指定的元素附加到该列表的末尾（可选操作）。
     * 
     * <p>Lists that support this operation may place limitations on what
     * elements may be added to this list.  In particular, some
     * lists will refuse to add null elements, and others will impose
     * restrictions on the type of elements that may be added.  List
     * classes should clearly specify in their documentation any restrictions
     * on what elements may be added.<br/>
     * 支持此操作的列表可能会对添加到该列表的元素的限制进行限制。
     * 特别地，一些列表将拒绝添加空元素，而另一些列表将对可能添加的元素的类型加以限制。List类应该清楚地在文档中指定任何对添加元素的限制。
     * 
     * <p>This implementation calls {@code add(size(), e)}.<br/>
     * 这个实现调用add（size（），e）。
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(int, Object) add(int, E)} is overridden.
     * 请注意,此实现抛出UnsupportedOperationException除非方式添加add(int,E)覆盖。
     * 
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     * @throws UnsupportedOperationException if the {@code add} operation
     *         is not supported by this list
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this list
     * @throws NullPointerException if the specified element is null and this
     *         list does not permit null elements
     * @throws IllegalArgumentException if some property of this element
     *         prevents it from being added to this list
     */
    public boolean add(E e) {
        add(size(), e);
        return true;
    }
    
    /**
     * {@inheritDoc}
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    abstract public E get(int index);
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E set(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public void add(int index, E element) {
        throw new UnsupportedOperationException();
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation always throws an
     * {@code UnsupportedOperationException}.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public E remove(int index) {
        throw new UnsupportedOperationException();
    }
    
    // Search Operations
    // 搜索操作
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator (with
     * {@code listIterator()}).  Then, it iterates over the list until the
     * specified element is found or the end of the list is reached.
     * 返回该列表中指定元素的第一次出现的索引，如果该列表不包含元素，则返回-1。
     * 更正式地，返回我这样的最低索引（o==null？get（i）==null:o.equals（get（i）），或-1如果没有这样的索引。
     * 这个实现首先获得一个列表迭代器（使用listIterator（））。然后，它在列表中迭代，直到找到指定的元素，或者到达列表的末尾。
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int indexOf(Object o) {
		ListIterator<E> it = listIterator();
		if (o == null) {
			while (it.hasNext()) {
				if (it.next() == null) {
					return it.previousIndex();
				}
			}
		} else {
			while (it.hasNext()) {
				if (o.equals(it.next())) {
					return it.previousIndex();
				}
			}
		}
		return -1;
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation first gets a list iterator that points to the end
     * of the list (with {@code listIterator(size())}).  Then, it iterates
     * backwards over the list until the specified element is found, or the
     * beginning of the list is reached.
     * 返回该列表中指定元素的最后一次出现的索引，如果该列表不包含元素，则返回-1。
     * 更正式地说，返回最高的索引i（o==null？get（i）==null:o.equals（get（i）），或-1如果没有这样的索引。
     * 该实现首先获得指向列表末尾的列表迭代器（使用listIterator（size（）））。然后，它在列表的后面迭代，直到找到指定的元素，或者到达列表的开始。
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public int lastIndexOf(Object o) {
		ListIterator<E> it = listIterator(size());
		if (o == null) {
			while (it.hasPrevious()) {
				if (it.previous() == null) {
					return it.nextIndex();
				}
			}
		} else {
			while (it.hasPrevious()) {
				if (o.equals(it.previous())) {
					return it.nextIndex();
				}
			}
		}
		return -1;
    }
    
    // Bulk Operations
    // 批量操作
    
    /**
     * Removes all of the elements from this list (optional operation).
     * The list will be empty after this call returns.
     *
     * <p>This implementation calls {@code removeRange(0, size())}.
     *
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless {@code remove(int
     * index)} or {@code removeRange(int fromIndex, int toIndex)} is
     * overridden.
     *
     * @throws UnsupportedOperationException if the {@code clear} operation
     *         is not supported by this list
     */
    public void clear() {
        removeRange(0, size());
    }
    
    
  // Comparison and hashing
    
    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     *
     * <p>This method is called by the {@code clear} operation on this list
     * and its subLists.  Overriding this method to take advantage of
     * the internals of the list implementation can <i>substantially</i>
     * improve the performance of the {@code clear} operation on this list
     * and its subLists.
     *
     * <p>This implementation gets a list iterator positioned before
     * {@code fromIndex}, and repeatedly calls {@code ListIterator.next}
     * followed by {@code ListIterator.remove} until the entire range has
     * been removed.  <b>Note: if {@code ListIterator.remove} requires linear
     * time, this implementation requires quadratic time.</b>
     *
     * @param fromIndex index of first element to be removed
     * @param toIndex index after last element to be removed
     */
    protected void removeRange(int fromIndex, int toIndex) {
        ListIterator<E> it = listIterator(fromIndex);
        for (int i=0, n=toIndex-fromIndex; i<n; i++) {
            it.next();
            it.remove();
        }
    }
}
  
