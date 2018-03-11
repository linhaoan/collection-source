package source.java.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.RandomAccess;
import java.util.Vector;

/**
 * Resizable-array implementation of the <tt>List</tt> interface.  Implements
 * all optional list operations, and permits all elements, including
 * <tt>null</tt>.  In addition to implementing the <tt>List</tt> interface,
 * this class provides methods to manipulate the size of the array that is
 * used internally to store the list.  (This class is roughly equivalent to
 * <tt>Vector</tt>, except that it is unsynchronized.)
 * 列表接口的resizabl -array实现。实现所有可选的列表操作，并允许所有元素，包括null。
 * 除了实现列表接口之外，这个类还提供了一些方法来操作内部用于存储列表的数组的大小。
 * (这个类大致等同于向量，只不过它是不同步的。)
 * 
 * 
 * <p>The <tt>size</tt>, <tt>isEmpty</tt>, <tt>get</tt>, <tt>set</tt>,
 * <tt>iterator</tt>, and <tt>listIterator</tt> operations run in constant
 * time.  The <tt>add</tt> operation runs in <i>amortized constant time</i>,
 * that is, adding n elements requires O(n) time.  All of the other operations
 * run in linear time (roughly speaking).  The constant factor is low compared
 * to that for the <tt>LinkedList</tt> implementation.
 * size、isEmpty、get、set、iterator和listIterator操作在常量时间内运行。
 * add()操作在平摊常数时间内运行，也就是说，添加n个元素需要O(n)时间。
 * 所有其他操作都在线性时间内运行(粗略地说)。
 * 与LinkedList实现相比，常数因子是低的。
 * 
 * <p>Each <tt>ArrayList</tt> instance has a <i>capacity</i>.  The capacity is
 * the size of the array used to store the elements in the list.  It is always
 * at least as large as the list size.  As elements are added to an ArrayList,
 * its capacity grows automatically.  The details of the growth policy are not
 * specified beyond the fact that adding an element has constant amortized
 * time cost.
 * 每个ArrayList实例都有一个容量。容量是用于存储列表中元素的数组的大小。
 * 它总是至少和列表大小一样大。当元素被添加到ArrayList中时，它的容量会自动增长。
 * 增长策略的细节没有指定，因为添加元素的时间成本是不变的。
 * 
 * <p>An application can increase the capacity of an <tt>ArrayList</tt> instance
 * before adding a large number of elements using the <tt>ensureCapacity</tt>
 * operation.  This may reduce the amount of incremental reallocation.
 * 应用程序可以增加ArrayList实例的容量，然后使用ensureCapacity操作添加大量元素。
 * 这可能会减少增量重新分配的数量。
 * 
 * <p><strong>Note that this implementation is not synchronized.</strong>
 * If multiple threads access an <tt>ArrayList</tt> instance concurrently,
 * and at least one of the threads modifies the list structurally, it
 * <i>must</i> be synchronized externally.  (A structural modification is
 * any operation that adds or deletes one or more elements, or explicitly
 * resizes the backing array; merely setting the value of an element is not
 * a structural modification.)  This is typically accomplished by
 * synchronizing on some object that naturally encapsulates the list.
 * 注意，此实现不同步。
 * 如果多个线程并发访问ArrayList实例，并且至少有一个线程在结构上修改了列表，那么它必须在外部同步。
 * (结构修改是任何添加或删除一个或多个元素，或显式调整支持数组的操作;仅仅设置元素的值不是结构修改。)
 * 这通常是通过在自然封装列表的某个对象上同步完成的。
 * 
 * 
 * If no such object exists, the list should be "wrapped" using the
 * {@link Collections#synchronizedList Collections.synchronizedList}
 * method.  This is best done at creation time, to prevent accidental
 * unsynchronized access to the list:<pre>
 *   List list = Collections.synchronizedList(new ArrayList(...));</pre>
 * 如果不存在这样的对象，则应该使用集合synchronizedList Collections.synchronizedList该列表。
 * 这在创建时是最好的，以防止意外的非同步访问列表:
 * List list = Collections.synchronizedList(new ArrayList(...));
 * 
 * <p><a name="fail-fast">
 * The iterators returned by this class's {@link #iterator() iterator} and
 * {@link #listIterator(int) listIterator} methods are <em>fail-fast</em>:</a>
 * if the list is structurally modified at any time after the iterator is
 * created, in any way except through the iterator's own
 * {@link ListIterator#remove() remove} or
 * {@link ListIterator#add(Object) add} methods, the iterator will throw a
 * {@link ConcurrentModificationException}.  Thus, in the face of
 * concurrent modification, the iterator fails quickly and cleanly, rather
 * than risking arbitrary, non-deterministic behavior at an undetermined
 * time in the future.
 * 这个类的迭代器返回的迭代器和listIterator方法快速失败:
 * 如果列表结构修改创建迭代器后,任何时候以任何方式除非通过迭代器的删除或添加方法,
 * 迭代器将抛出ConcurrentModificationException。
 * 因此，在并发修改的情况下，迭代器会快速而干净地失败，而不是在将来某个未确定的时间冒任意的、不确定的行为。
 * 
 * 
 * <p>Note that the fail-fast behavior of an iterator cannot be guaranteed
 * as it is, generally speaking, impossible to make any hard guarantees in the
 * presence of unsynchronized concurrent modification.  Fail-fast iterators
 * throw {@code ConcurrentModificationException} on a best-effort basis.
 * Therefore, it would be wrong to write a program that depended on this
 * exception for its correctness:  <i>the fail-fast behavior of iterators
 * should be used only to detect bugs.</i>
 * 请注意，迭代器的故障快速行为不能得到保证，一般来说，在不同步的并发修改存在的情况下，不可能做出任何硬的保证。快速失败迭代器抛出ConcurrentModificationException力所能及。
 * 因此，编写一个依赖于此异常的程序是错误的:迭代器的故障快速行为应该只用于检测错误。
 * 
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Collection
 * @see     List
 * @see     LinkedList
 * @see     Vector
 * @since   1.2
 */
public class ArrayList<E> extends AbstractList<E>
implements List<E>, RandomAccess, Cloneable, java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5808865424651752322L;

    /**
     * Default initial capacity.
     * 默认初始容量
     */
    private static final int DEFAULT_CAPACITY = 10;
    
    /**
     * Shared empty array instance used for empty instances.
     * 用于空实例的共享空数组实例。
     */
    private static final Object[] EMPTY_ELEMENTDATA = {};
    
    /**
     * Shared empty array instance used for default sized empty instances. We
     * distinguish this from EMPTY_ELEMENTDATA to know how much to inflate when
     * first element is added.
     * 共享空数组实例，用于默认大小的空实例。
     * 我们将它与EMPTY_ELEMENTDATA区分开来，以了解在添加第一个元素时要膨胀多少。
     */
    private static final Object[] DEFAULTCAPACITY_EMPTY_ELEMENTDATA = {};
    
    /**
     * The array buffer into which the elements of the ArrayList are stored.
     * The capacity of the ArrayList is the length of this array buffer. Any
     * empty ArrayList with elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA
     * will be expanded to DEFAULT_CAPACITY when the first element is added.
     * 存储数组元素的数组缓冲区。
     * ArrayList的容量是这个数组缓冲区的长度。
     * 当添加第一个元素时，
     * 任何带有elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA的
     * 空ArrayList将被扩展到DEFAULT_CAPACITY。
     */
    transient Object[] elementData; // non-private to simplify nested class access 非私有来简化嵌套类访问。
    
    /**
     * The size of the ArrayList (the number of elements it contains).
     * ArrayList的大小(它包含的元素的数量)。
     * @serial
     */
    private int size;
    
    /**
     * Constructs an empty list with the specified initial capacity.
     * 构造一个具有指定初始容量的空列表。
     *
     * @param  initialCapacity  the initial capacity of the list
     * @throws IllegalArgumentException if the specified initial capacity
     *         is negative
     */
    public ArrayList(int initialCapacity) {
        if (initialCapacity > 0) {
        	this.elementData = new Object[initialCapacity];
        } else if (initialCapacity == 0) {
        	this.elementData = EMPTY_ELEMENTDATA;
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }
    
    /**
     * Constructs an empty list with an initial capacity of ten.
     * 构造一个初始容量为10的空列表。
     */
    public ArrayList() {
        this.elementData = DEFAULTCAPACITY_EMPTY_ELEMENTDATA;
    }
    
    /**
     * Constructs a list containing the elements of the specified
     * collection, in the order they are returned by the collection's
     * iterator.
     * 构造一个包含指定集合元素的列表，顺序由集合的迭代器返回。
     * @param c the collection whose elements are to be placed into this list
     * c集合，其元素将被放置到这个列表中。
     * @throws NullPointerException if the specified collection is null
     */
    public ArrayList(Collection<? extends E> c) {
    	elementData = c.toArray();
    	if ((size = elementData.length) != 0) {
    		// c.toArray might (incorrectly) not return Object[] (see 6260652)
    		// c.toArray可能(错误地)不返回 Object[](参见6260652)
    		if (elementData.getClass() != Object[].class) {
    			elementData = Arrays.copyOf(elementData, size, Object[].class);
    		} else {
                // replace with empty array.
    			// 替换为空数组。
                this.elementData = EMPTY_ELEMENTDATA;
    		}
    	}
    }
    
    /**
     * Trims the capacity of this <tt>ArrayList</tt> instance to be the
     * list's current size.  An application can use this operation to minimize
     * the storage of an <tt>ArrayList</tt> instance.
     */
    public void trimToSize() {
        modCount++;
        if (size < elementData.length) {
            elementData = (size == 0)
              ? EMPTY_ELEMENTDATA
              : Arrays.copyOf(elementData, size);
        }
    }
    
    
	@Override
	public E get(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

}
