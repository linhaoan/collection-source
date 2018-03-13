package source.java.util;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;
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
     * 将这个ArrayList实例的容量作为列表的当前大小。
     * 应用程序可以使用此操作最小化ArrayList实例的存储。
     */
    public void trimToSize() {
    	modCount++;
		if (size < elementData.length) {
			elementData = (size == 0) ? 
					EMPTY_ELEMENTDATA : 
				Arrays.copyOf(elementData, size);

		}
    }
    
    /**
     * Increases the capacity of this <tt>ArrayList</tt> instance, if
     * necessary, to ensure that it can hold at least the number of elements
     * specified by the minimum capacity argument.
     * 如果需要，增加这个ArrayList实例的容量，
     * 以确保它至少可以容纳最小容量参数指定的元素的数量。
     *
     * @param   minCapacity   the desired minimum capacity
     */
    public void ensureCapacity(int minCapacity) {
        int minExpand = (elementData != DEFAULTCAPACITY_EMPTY_ELEMENTDATA)
            // any size if not default element table
            ? 0
            // larger than default for default empty table. It's already
            // supposed to be at default size.
            : DEFAULT_CAPACITY;

        if (minCapacity > minExpand) {
            ensureExplicitCapacity(minCapacity);
        }
    }
    
    private void ensureCapacityInternal(int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            minCapacity = Math.max(DEFAULT_CAPACITY, minCapacity);
        }

        ensureExplicitCapacity(minCapacity);
    }
    
    private static int calculateCapacity(Object[] elementData, int minCapacity) {
        if (elementData == DEFAULTCAPACITY_EMPTY_ELEMENTDATA) {
            return Math.max(DEFAULT_CAPACITY, minCapacity);
        }
        return minCapacity;
        }
    
    private void ensureExplicitCapacity(int minCapacity) {
        modCount++;

        // overflow-conscious code
        // 如果最小需要空间比elementData的内存空间要大，则需要扩容 
        if (minCapacity - elementData.length > 0)
            grow(minCapacity);
    }
    
    /**
     * The maximum size of array to allocate.
     * Some VMs reserve some header words in an array.
     * Attempts to allocate larger arrays may result in
     * OutOfMemoryError: Requested array size exceeds VM limit
     */
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;
    
    /**
     * Increases the capacity to ensure that it can hold at least the
     * number of elements specified by the minimum capacity argument.
     *
     * @param minCapacity the desired minimum capacity
     */
    private void grow(int minCapacity) {
        // overflow-conscious code
        int oldCapacity = elementData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);
        // 判断旧容量的1.5倍小 还是 minCapacity小，取小值
        // 扩容后的新容量小于最小的容量
        if (newCapacity - minCapacity < 0) {
        	newCapacity = minCapacity;
        }    
        
        if (newCapacity - MAX_ARRAY_SIZE > 0)
            newCapacity = hugeCapacity(minCapacity);
        // minCapacity is usually close to size, so this is a win:
        elementData = Arrays.copyOf(elementData, newCapacity);
    }
    
    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) // overflow
            throw new OutOfMemoryError();
        return (minCapacity > MAX_ARRAY_SIZE) ?
            Integer.MAX_VALUE :
            MAX_ARRAY_SIZE;
    }
    
    /**
     * Returns the number of elements in this list.
     * 返回列表中元素的数量。
     * @return the number of elements in this list
     */
    public int size() {
        return size;
    }
    
    /**
     * Returns <tt>true</tt> if this list contains no elements.
     *
     * @return <tt>true</tt> if this list contains no elements
     */
    public boolean isEmpty() {
        return size == 0;
    }
    
    /**
     * Returns <tt>true</tt> if this list contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this list contains
     * at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.
     *
     * @param o element whose presence in this list is to be tested
     * @return <tt>true</tt> if this list contains the specified element
     */
    public boolean contains(Object o) {
        return indexOf(o) >= 0;
    }
    
    /**
     * Returns the index of the first occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the lowest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int indexOf(Object o) {
    	if (o == null) {
    		for (int i = 0;i<size;i++) {
    			if(elementData[i] == null) {
    				return i;
    			}
    		}
    	}else {
    		for (int i=0;i<size;i++) {
    			if(o.equals(elementData[i])) {
    				return i;
    			}
    		}
    	}
        return -1;
    }
    
    /**
     * Returns the index of the last occurrence of the specified element
     * in this list, or -1 if this list does not contain the element.
     * More formally, returns the highest index <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>,
     * or -1 if there is no such index.
     */
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (elementData[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(elementData[i]))
                    return i;
        }
        return -1;
    }
    
    /**
     * Returns a shallow copy of this <tt>ArrayList</tt> instance.  (The
     * elements themselves are not copied.)
     * 返回这个ArrayList实例的一个浅拷贝。(这些元素本身并没有被复制。)
     * @return a clone of this <tt>ArrayList</tt> instance
     */
    public Object clone() {
        try {
            ArrayList<?> v = (ArrayList<?>) super.clone();
            v.elementData = Arrays.copyOf(elementData, size);
            v.modCount = 0;
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }
    
    
    /**
     * Returns an array containing all of the elements in this list
     * in proper sequence (from first to last element).
     *
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this list.  (In other words, this method must allocate
     * a new array).  The caller is thus free to modify the returned array.
     *
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.
     *
     * @return an array containing all of the elements in this list in
     *         proper sequence
     */
    public Object[] toArray() {
        return Arrays.copyOf(elementData, size);
    }
    
    
    /**
     * Returns an array containing all of the elements in this list in proper
     * sequence (from first to last element); the runtime type of the returned
     * array is that of the specified array.  If the list fits in the
     * specified array, it is returned therein.  Otherwise, a new array is
     * allocated with the runtime type of the specified array and the size of
     * this list.
     * 返回包含该列表中所有元素的数组(从第一个元素到最后一个元素);
     * 返回数组的运行时类型是指定数组的运行时类型。如果列表符合指定的数组，则返回其中。
     * 否则，将使用指定数组的运行时类型和该列表的大小分配一个新数组。
     * 
     * <p>If the list fits in the specified array with room to spare
     * (i.e., the array has more elements than the list), the element in
     * the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of the
     * list <i>only</i> if the caller knows that the list does not contain
     * any null elements.)
     * 如果列表与指定的数组相匹配，则有剩余空间
     * (例如:，数组元素比列表元素多，在集合结束后，数组中的元素被设置为null。
     * (只有当调用方知道该列表不包含任何空元素时，才可以确定列表的长度。)
     * 
     * @param a the array into which the elements of the list are to
     *          be stored, if it is big enough; otherwise, a new array of the
     *          same runtime type is allocated for this purpose.
     * @return an array containing the elements of the list
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this list
     * @throws NullPointerException if the specified array is null
     */
    @SuppressWarnings("unchecked")
    public <T> T[] toArray(T[] a) {
    	// 如果a的长度小于ArrayList的长度，直接调用Arrays类的copyOf，
    	// 返回一个比a数组长度要大的新数组，里面元素就是ArrayList里面的元素；
        if (a.length < size) {
            // Make a new array of a's runtime type, but my contents:
            return (T[]) Arrays.copyOf(elementData, size, a.getClass());
        }
        // 如果a的长度比ArrayList的长度大，那么就调用System.arraycopy，
        // 将ArrayList的elementData数组赋值到a数组，然后把a数组的size位置赋值为空。
        System.arraycopy(elementData, 0, a, 0, size);
        if (a.length > size)
            a[size] = null;
        return a;
    }
    
    // Positional Access Operations
    // 位置访问操作
    @SuppressWarnings("unchecked")
    E elementData(int index) {
        return (E) elementData[index];
    }
    
    /**
     * Returns the element at the specified position in this list.
     *
     * @param  index index of the element to return
     * @return the element at the specified position in this list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        rangeCheck(index);
        return elementData(index);
    }
    

    /**
     * Replaces the element at the specified position in this list with
     * the specified element.
     *
     * @param index index of the element to replace
     * @param element element to be stored at the specified position
     * @return the element previously at the specified position
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E set(int index, E element) {
        rangeCheck(index);

        E oldValue = elementData(index);
        elementData[index] = element;
        return oldValue;
    }
    
    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return <tt>true</tt> (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);  // Increments modCount!!
        elementData[size++] = e;
        return true;
    }
    
    /**
     * Inserts the specified element at the specified position in this
     * list. Shifts the element currently at that position (if any) and
     * any subsequent elements to the right (adds one to their indices).
     * 将指定的元素插入该列表中的指定位置。
     * 将当前位置的元素(如果有)和任何后续元素移到右边(将一个元素添加到它们的索引中)。
     * 
     * @param index index at which the specified element is to be inserted
     * @param element element to be inserted
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public void add(int index, E element) {
        rangeCheckForAdd(index);

        ensureCapacityInternal(size + 1);  // Increments modCount!!
        System.arraycopy(elementData, index, elementData, index + 1,
                         size - index);
        elementData[index] = element;
        size++;
    }
    
    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).
     * 在这个列表中删除指定位置的元素。将任何后续元素移到左边(从它们的索引中减去一个)。
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        rangeCheck(index);

        modCount++;
        E oldValue = elementData(index);
        
        int numMoved = size - index - 1;
        // 不是最后一个元素，需要改变位置
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work

        return oldValue;
    }
    
    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If the list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * <tt>i</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;get(i)==null&nbsp;:&nbsp;o.equals(get(i)))</tt>
     * (if such an element exists).  Returns <tt>true</tt> if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     * 从这个列表中删除指定元素的第一次出现，如果它存在的话。
     * 如果列表中不包含元素，那么它是不变的。
     * 更正式地说，删除索引中最低的元素(o==null ?get(i)==null: o = (get(i)))(如果存在这样的元素)。
     * 如果该列表包含指定的元素(或等效地，如果此列表因调用而更改)，则返回true。
     * 
     * @param o element to be removed from this list, if present
     * @return <tt>true</tt> if this list contained the specified element
     */
    public boolean remove(Object o) {

		if (o == null) {
			for (int index = 0; index < size; index++) {
				if (elementData[index] == null) {
					fastRemove(index);
					return true;
				}
			}
		} else {
			for (int index = 0; index < size; index++) {
				if (o.equals(elementData[index])) {
					fastRemove(index);
					return true;
				}
			}
		}
        return false;
    }
    
    
    /*
     * Private remove method that skips bounds checking and does not
     * return the value removed.
     */
    private void fastRemove(int index) {
        modCount++;
        int numMoved = size - index - 1;
        if (numMoved > 0)
            System.arraycopy(elementData, index+1, elementData, index,
                             numMoved);
        elementData[--size] = null; // clear to let GC do its work
    }
    
    /**
     * Removes all of the elements from this list.  The list will
     * be empty after this call returns.
     */
    public void clear() {
        modCount++;

        // clear to let GC do its work
        for (int i = 0; i < size; i++)
            elementData[i] = null;

        size = 0;
    }
    
    /**
     * Checks if the given index is in range.  If not, throws an appropriate
     * runtime exception.  This method does *not* check if the index is
     * negative: It is always used immediately prior to an array access,
     * which throws an ArrayIndexOutOfBoundsException if index is negative.
     */
    private void rangeCheck(int index) {
        if (index >= size)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    /**
     * Appends all of the elements in the specified collection to the end of
     * this list, in the order that they are returned by the
     * specified collection's Iterator.  The behavior of this operation is
     * undefined if the specified collection is modified while the operation
     * is in progress.  (This implies that the behavior of this call is
     * undefined if the specified collection is this list, and this
     * list is nonempty.)
     * 将指定集合中的所有元素追加到该列表的末尾，顺序是由指定的集合的迭代器返回它们。
     * 如果在运行过程中修改指定的集合，则该操作的行为是未定义的。
     * (这意味着如果指定的集合是这个列表，这个调用的行为是未定义的，并且这个列表是非空的。)
     * 
     * @param c collection containing elements to be added to this list
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(Collection<? extends E> c) {
    	Object[] a = c.toArray();
    	int numNew = a.length;
    	ensureCapacityInternal(size + numNew);  // Increments modCount
    	
    	System.arraycopy(a, 0, elementData, size, numNew);
    	size += numNew;
    	return numNew !=0;
    }
    
    /**
     * Inserts all of the elements in the specified collection into this
     * list, starting at the specified position.  Shifts the element
     * currently at that position (if any) and any subsequent elements to
     * the right (increases their indices).  The new elements will appear
     * in the list in the order that they are returned by the
     * specified collection's iterator.
     * 将指定集合中的所有元素插入到这个列表中，从指定的位置开始。
     * 将当前位置的元素(如果有)和任何后续元素移到右边(增加它们的索引)。
     * 新元素将出现在列表中，顺序是由指定的集合的迭代器返回的。
     * @param index index at which to insert the first element from the
     *              specified collection
     * @param c collection containing elements to be added to this list
     * 从指定集合中插入第一个元素的索引。
     * @return <tt>true</tt> if this list changed as a result of the call
     * @throws IndexOutOfBoundsException {@inheritDoc}
     * @throws NullPointerException if the specified collection is null
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);  // Increments modCount
        int numMoved = size - index;
        //如果 size -index = 0; 表明扩容后的index 从原来结尾处开始
        if(numMoved > 0) {
        	//先将ArrayList中
        	//从index开始的numMoved个元素移动到起始位置为index+numNew的后面去
            System.arraycopy(elementData, index, elementData, index + numNew,
                    numMoved);
        }
        // 再将c中的numNew个元素复制到起始位置为index的存储空间中去
        System.arraycopy(a, 0, elementData, index, numNew);
        size += numNew;
        return numNew != 0 ;
    }
    
    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     * 从这个列表中删除所有索引位于fromIndex(包含)和toIndex(不包含)之间的元素。
     * 将任何后续元素移到左边(减少它们的索引)。
     * 这个调用通过(toIndex - fromIndex)元素来缩短列表。
     * (如果toIndex==fromIndex，则此操作无效。)
     * @throws IndexOutOfBoundsException if {@code fromIndex} or
     *         {@code toIndex} is out of range
     *         ({@code fromIndex < 0 ||
     *          fromIndex >= size() ||
     *          toIndex > size() ||
     *          toIndex < fromIndex})
     */
    protected void removeRange(int fromIndex, int toIndex) {

    	modCount++;
    	int numMoved = size - toIndex;
    	System.arraycopy(elementData, toIndex, elementData, fromIndex, numMoved);
    	
    	int newSize = size - (toIndex - fromIndex) ;
    	for (int i = newSize ; i < size;i++) {
    		elementData[i] = null;
    	}
    	size = newSize;
    }
    
    /**
     * A version of rangeCheck used by add and addAll.
     */
    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }

    /**
     * Constructs an IndexOutOfBoundsException detail message.
     * Of the many possible refactorings of the error handling code,
     * this "outlining" performs best with both server and client VMs.
     */
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size;
    }

    
    /**
     * Removes from this list all of its elements that are contained in the
     * specified collection.
     * 从这个列表中移除所有包含在指定集合中的元素。
     * @param c collection containing elements to be removed from this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     * Specified by: removeAll(...) in List, Overrides: removeAll(...) in AbstractCollection
     * 由:removeAll(…)在列表中，重写:在AbstractCollection中进行removeAll(…)。
     * 
     *     public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<?> it = iterator();
        while (it.hasNext()) {
            if (c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, false);
    }
    
    /**
     * Retains only the elements in this list that are contained in the
     * specified collection.  In other words, removes from this list all
     * of its elements that are not contained in the specified collection.
     * 只保留该列表中包含在指定集合中的元素。
     * 换句话说，从这个列表中移除所有未包含在指定集合中的元素。
     * @param c collection containing elements to be retained in this list
     * @return {@code true} if this list changed as a result of the call
     * @throws ClassCastException if the class of an element of this list
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this list contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see Collection#contains(Object)
     * 由:retainAll(…)在列表中，重写:在AbstractCollection中进行retainAll(…)。
     *     public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;
        Iterator<E> it = iterator();
        while (it.hasNext()) {
            if (!c.contains(it.next())) {
                it.remove();
                modified = true;
            }
        }
        return modified;
    }
     */
    public boolean retainAll(Collection<?> c) {
        Objects.requireNonNull(c);
        return batchRemove(c, true);
    }
    
    private boolean batchRemove(Collection<?> c, boolean complement) {
        final Object[] elementData = this.elementData;
        int r = 0, w = 0; // w是重新存元素时的索引，r是原来的索引
        boolean modified = false;
        try {
        	
        	// removeAll 求差集 .complement 为false ，        	
        	// 1,如果集合c中包含elementData的元素e，
        	// 则c.contains(elementData[r])为true，complement 为false ,if不成立，if结束；
        	       	
        	// 2,如果c不包含elementData的元素e，则if成立，将此元素e赋值给elementData[w++]
        	// 即elementData保留了c中没有的元素，也就是删除了C中存在的所有元素】。
            for (; r < size; r++)
                if (c.contains(elementData[r]) == complement)
                    elementData[w++] = elementData[r];
        } finally {
            // Preserve behavioral compatibility with AbstractCollection,
            // even if c.contains() throws.
            if (r != size) {
            	// if (r != size)，则将elementData未参加比较的元素arraycopy到elementData后面；
            	// 新索引w加上刚arraycopy的数目
                System.arraycopy(elementData, r,
                                 elementData, w,
                                 size - r);
                w += size - r;
            }
            if (w != size) {
            	// 此时w还不等于size，则将w后的元素移除，为什么移除（调用了arraycopy，详见fastRemove）。
            	// 只有执行了if (w != size)（事实上只要c中含有elementData的元素，w肯定不等于size），
            	// 才令modified = true，
            	// 才说明remove成功，返回true，否则返回false。
                // clear to let GC do its work
                for (int i = w; i < size; i++)
                    elementData[i] = null;
                modCount += size - w;
                size = w;
                modified = true;
            }
        }
        return modified;
    }
    
    /**
     * Save the state of the <tt>ArrayList</tt> instance to a stream (that
     * is, serialize it).
     * 将ArrayList实例的状态保存到一个流（也就是序列化它）。
     * @serialData The length of the array backing the <tt>ArrayList</tt>
     *             instance is emitted (int), followed by all of its elements
     *             (each an <tt>Object</tt>) in the proper order.
     *             数组支持ArrayList实例的数组的长度被释放（int），然后以适当的顺序执行所有的元素（每个对象）。
     */
    private void writeObject(java.io.ObjectOutputStream s)
        throws java.io.IOException{
        // Write out element count, and any hidden stuff
    	// 写入元素计数，以及任何隐藏的内容
        int expectedModCount = modCount;
        s.defaultWriteObject();

        // Write out size as capacity for behavioural compatibility with clone()
        // 把大小作为与克隆（）行为兼容性的能力
        s.writeInt(size);

        // Write out all elements in the proper order.
        // 把所有的元素都按正确的顺序写出来。
        for (int i=0; i<size; i++) {
            s.writeObject(elementData[i]);
        }

        if (modCount != expectedModCount) {
            throw new ConcurrentModificationException();
        }
    }
    
    /**
     * Reconstitute the <tt>ArrayList</tt> instance from a stream (that is,
     * deserialize it).
     */
    private void readObject(java.io.ObjectInputStream s)
        throws java.io.IOException, ClassNotFoundException {
        elementData = EMPTY_ELEMENTDATA;

        // Read in size, and any hidden stuff
        s.defaultReadObject();

        // Read in capacity
        s.readInt(); // ignored

        if (size > 0) {
            // be like clone(), allocate array based upon size not capacity
            int capacity = calculateCapacity(elementData, size);
            SharedSecrets.getJavaOISAccess().checkArray(s, Object[].class, capacity);
            ensureCapacityInternal(size);

            Object[] a = elementData;
            // Read in all elements in the proper order.
            for (int i=0; i<size; i++) {
                a[i] = s.readObject();
            }
        }
    }
}
