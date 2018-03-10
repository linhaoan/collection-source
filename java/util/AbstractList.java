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
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.RandomAccess;

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
     * 从这个列表删除的所有元素(可选操作)。这个调用返回后的列表是空的。
     * <p>This implementation calls {@code removeRange(0, size())}.
     * 这个实现调用removeRange(0,size())。
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless {@code remove(int
     * index)} or {@code removeRange(int fromIndex, int toIndex)} is
     * overridden.
     * 请注意,此实现抛出UnsupportedOperationException
     * 除非方式remove(int index)或removeRange(int fromIndex,int toIndex)方法被覆盖。
     * @throws UnsupportedOperationException if the {@code clear} operation
     *         is not supported by this list
     */
    public void clear() {
        removeRange(0, size());
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation gets an iterator over the specified collection
     * and iterates over it, inserting the elements obtained from the
     * iterator into this list at the appropriate position, one at a time,
     * using {@code add(int, E)}.
     * Many implementations will override this method for efficiency.
     * 
     * 这个实现在指定的集合上得到一个迭代器，并在它上迭代，将从迭代器中获得的元素插入到适当位置的列表中，一次使用add（int，E），
     * 许多实现将重写这个方法以提高效率。
     * <p>Note that this implementation throws an
     * {@code UnsupportedOperationException} unless
     * {@link #add(int, Object) add(int, E)} is overridden.
     * 请注意,此实现抛出UnsupportedOperationException除非方法add(int,E)覆盖。
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     * @throws IndexOutOfBoundsException     {@inheritDoc}
     */
    public boolean addAll(int index, Collection<? extends E> c) {
        rangeCheckForAdd(index);
        boolean modified = false;
        for (E e : c) {
            add(index++, e);
            modified = true;
        }
        return modified;
    }
    
    // Iterators

    /**
     * Returns an iterator over the elements in this list in proper sequence.
     *
     * <p>This implementation returns a straightforward implementation of the
     * iterator interface, relying on the backing list's {@code size()},
     * {@code get(int)}, and {@code remove(int)} methods.
     *
     * <p>Note that the iterator returned by this method will throw an
     * {@link UnsupportedOperationException} in response to its
     * {@code remove} method unless the list's {@code remove(int)} method is
     * overridden.
     *
     * <p>This implementation can be made to throw runtime exceptions in the
     * face of concurrent modification, as described in the specification
     * for the (protected) {@link #modCount} field.
     *
     * @return an iterator over the elements in this list in proper sequence
     */
    public Iterator<E> iterator() {
        return new Itr();
    }
    
    /**
     * {@inheritDoc}
     *
     * <p>This implementation returns {@code listIterator(0)}.
     *
     * @see #listIterator(int)
     */
    public ListIterator<E> listIterator() {
        return listIterator(0);
    }
    
    private class Itr implements Iterator<E> {
    	 
        /**
         * Index of element to be returned by subsequent call to next.
         * 索引的元素返回的后续调用。
         */
		int cursor = 0;
		
        /**
         * Index of element returned by most recent call to next or
         * previous.  Reset to -1 if this element is deleted by a call
         * to remove.
         * 最近或以前的调用返回的元素的索引。如果这个元素被删除的调用删除，则重置为-1。
         * 1 初始化时为-1 。
         * 2 删除元素后为 -1。
         */
        int lastRet = -1;
        
        int expectedModCount = modCount;
       
		@Override
		public boolean hasNext() {			
			return cursor!=size();
		}

		@Override
		public E next() {
			checkForComodification();
			try {
				int i = cursor;
				E e = get(i);
				lastRet = i;
				cursor = i + 1;
				return e;
			} catch (IndexOutOfBoundsException e) {
				checkForComodification();
				throw new NoSuchElementException();
			}

		}
		
		public void remove() {
			if (lastRet < 0) {
				throw new IllegalStateException();
			}
			checkForComodification();
			try {				
				AbstractList.this.remove(lastRet);
				if (lastRet < cursor) {
					cursor--;
				}
				lastRet = -1;
				expectedModCount = modCount;
			} catch (IndexOutOfBoundsException e) {
				checkForComodification();
				throw new ConcurrentModificationException();
			}
		}
		
        final void checkForComodification() {
            if (modCount != expectedModCount)
                throw new ConcurrentModificationException();
        }
    	
    }
    
    private class ListItr extends Itr implements ListIterator<E> {

        ListItr(int index) {
            cursor = index;
        }
        
		@Override
		public boolean hasPrevious() {
			return cursor!=0;
		}

		@Override
		public E previous() {
			checkForComodification();
			try {
				int i = cursor - 1;
				E previous = get(i);
				lastRet = cursor = i;
				return previous;
			} catch (IndexOutOfBoundsException e) {
				checkForComodification();
				throw new NoSuchElementException();
			}

		}

		@Override
		public int nextIndex() {
			return cursor;
		}

		@Override
		public int previousIndex() {
			return cursor-1;
		}

		@Override
		public void set(E e) {
            if (lastRet < 0)
                throw new IllegalStateException();
            checkForComodification();

            try {
                AbstractList.this.set(lastRet, e);
                expectedModCount = modCount;
            } catch (IndexOutOfBoundsException ex) {
                throw new ConcurrentModificationException();
            }
		}

		@Override
		public void add(E e) {
	           checkForComodification();

	            try {
	                int i = cursor;
	                AbstractList.this.add(i, e);
	                lastRet = -1;
	                cursor = i + 1;
	                expectedModCount = modCount;
	            } catch (IndexOutOfBoundsException ex) {
	                throw new ConcurrentModificationException();
	            }
		}
    	
    }
    
    /**
     * {@inheritDoc}
     * 
     * <p>This implementation returns a list that subclasses
     * {@code AbstractList}.  The subclass stores, in private fields, the
     * offset of the subList within the backing list, the size of the subList
     * (which can change over its lifetime), and the expected
     * {@code modCount} value of the backing list.  There are two variants
     * of the subclass, one of which implements {@code RandomAccess}.
     * If this list implements {@code RandomAccess} the returned list will
     * be an instance of the subclass that implements {@code RandomAccess}.
     * 这个实现返回一个子类AbstractList的列表。子类存储在私有字段中，在备份列表中的子列表的偏移量，子列表的大小(可以在它的生命周期中改变)，以及支持列表的预期的modCount值。这个子类有两个变种，其中一个实现了随机访问。
     * 如果这个列表实现了随机访问，返回的列表将是实现随机访问的子类的一个实例。
     *
     * <p>The subclass's {@code set(int, E)}, {@code get(int)},
     * {@code add(int, E)}, {@code remove(int)}, {@code addAll(int,
     * Collection)} and {@code removeRange(int, int)} methods all
     * delegate to the corresponding methods on the backing abstract list,
     * after bounds-checking the index and adjusting for the offset.  The
     * {@code addAll(Collection c)} method merely returns {@code addAll(size,
     * c)}.
     * 子类的集合set(int, E)， get(int)， add(int, E)， remove(int)， 
     * addAll(int, Collection)和removeRange(int, int)方法，
     * 所有的方法都委托给后面的抽象列表中相应的方法，
     * 在bounds检查索引和调整偏移量之后。
     * addAll(集合c)方法仅返回addAll(size, c)。
     * 
     * <p>The {@code listIterator(int)} method returns a "wrapper object"
     * over a list iterator on the backing list, which is created with the
     * corresponding method on the backing list.  The {@code iterator} method
     * merely returns {@code listIterator()}, and the {@code size} method
     * merely returns the subclass's {@code size} field.
     * listIterator(int)方法在后备列表上的列表迭代器上返回一个“包装器对象”，该列表是用相应的方法在后备列表中创建的。
     * 迭代器方法只返回listIterator()，而size方法只返回子类的大小字段。
     * 
     * <p>All methods first check to see if the actual {@code modCount} of
     * the backing list is equal to its expected value, and throw a
     * {@code ConcurrentModificationException} if it is not.
     * 先看看所有方法的实际modCount支持列表等于它的期望值,并抛出ConcurrentModificationException如果不是。
     * @throws IndexOutOfBoundsException if an endpoint index value is out of range
     *         {@code (fromIndex < 0 || toIndex > size)}
     * @throws IllegalArgumentException if the endpoint indices are out of order
     *         {@code (fromIndex > toIndex)}
     */
    public List<E> subList(int fromIndex, int toIndex) {
        return (this instanceof RandomAccess ?
                new RandomAccessSubList<>(this, fromIndex, toIndex) :
                new SubList<>(this, fromIndex, toIndex));
    }
    
  // Comparison and hashing
    
    /**
     * Removes from this list all of the elements whose index is between
     * {@code fromIndex}, inclusive, and {@code toIndex}, exclusive.
     * Shifts any succeeding elements to the left (reduces their index).
     * This call shortens the list by {@code (toIndex - fromIndex)} elements.
     * (If {@code toIndex==fromIndex}, this operation has no effect.)
     * 从这个列表中删除所有索引在fromIndex、包含和toIndex之间的元素。
     * 将任何成功的元素转移到左边（减少它们的索引）。
     * 这个调用缩短了列表（toIndex-fromIndex）元素。（如果toIndex==fromIndex，该操作没有效果。）
     * 
     * <p>This method is called by the {@code clear} operation on this list
     * and its subLists.  Overriding this method to take advantage of
     * the internals of the list implementation can <i>substantially</i>
     * improve the performance of the {@code clear} operation on this list
     * and its subLists.
     * 这个方法是通过这个列表及其子列表的清晰操作来调用的。
     * 重写这个方法以利用列表实现的内部机制可以极大地改善这个列表及其子列表中的clear操作的性能。
     * 
     * <p>This implementation gets a list iterator positioned before
     * {@code fromIndex}, and repeatedly calls {@code ListIterator.next}
     * followed by {@code ListIterator.remove} until the entire range has
     * been removed.  <b>Note: if {@code ListIterator.remove} requires linear
     * time, this implementation requires quadratic time.</b>
     * 这个实现得到一个list迭代器在fromIndex之前，并重复调用ListIterator。
     * 下一个ListIterator紧随其后。移除直到整个范围被移除。
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
    
    /**
     * The number of times this list has been <i>structurally modified</i>.
     * Structural modifications are those that change the size of the
     * list, or otherwise perturb it in such a fashion that iterations in
     * progress may yield incorrect results.
     * 这个列表的次数是经过结构修改的。结构修改是改变列表的大小，或者以这样一种方式扰乱它，使迭代中的迭代可能产生不正确的结果。
     * 
     * <p>This field is used by the iterator and list iterator implementation
     * returned by the {@code iterator} and {@code listIterator} methods.
     * If the value of this field changes unexpectedly, the iterator (or list
     * iterator) will throw a {@code ConcurrentModificationException} in
     * response to the {@code next}, {@code remove}, {@code previous},
     * {@code set} or {@code add} operations.  This provides
     * <i>fail-fast</i> behavior, rather than non-deterministic behavior in
     * the face of concurrent modification during iteration.
     * 这个字段由迭代器和list迭代器实现，迭代器和listIterator方法返回。
     * 如果这个字段的值变化出乎意料,迭代器(或列表迭代器)将抛出ConcurrentModificationException以应对下一个,删除之前设置或添加操作。
     * 这提供了故障快速的行为，而不是在迭代过程中进行并发修改时的非确定性行为。
     *
     * <p><b>Use of this field by subclasses is optional.</b> If a subclass
     * wishes to provide fail-fast iterators (and list iterators), then it
     * merely has to increment this field in its {@code add(int, E)} and
     * {@code remove(int)} methods (and any other methods that it overrides
     * that result in structural modifications to the list).  A single call to
     * {@code add(int, E)} or {@code remove(int)} must add no more than
     * one to this field, or the iterators (and list iterators) will throw
     * bogus {@code ConcurrentModificationExceptions}.  If an implementation
     * does not wish to provide fail-fast iterators, this field may be
     * ignored.
     * 在子类中使用这个字段是可选的。如果一个子类希望提供故障快速迭代器（和列表迭代器），
     * 那么它只需要在add（int、E）和remove（int）方法中增加这个字段（以及它覆盖对该列表进行结构修改的任何其他方法）。
     * 一个调用add(int,E)或remove(int)必须不超过一个添加到这个领域,或者迭代器(和列表迭代器)将把虚假concurrentmodificationexceptions。
     * 如果一个实现不希望提供故障快速迭代器，那么这个字段可能会被忽略。
     */
    protected transient int modCount = 0;
    
    private void rangeCheckForAdd(int index) {
        if (index < 0 || index > size())
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
    }
    
    private String outOfBoundsMsg(int index) {
        return "Index: "+index+", Size: "+size();
    }
    
    class SubList<E> extends AbstractList<E> {
        private final AbstractList<E> l;
        private final int offset;
        private int size;

        SubList(AbstractList<E> list, int fromIndex, int toIndex) {
            if (fromIndex < 0)
                throw new IndexOutOfBoundsException("fromIndex = " + fromIndex);
            if (toIndex > list.size())
                throw new IndexOutOfBoundsException("toIndex = " + toIndex);
            if (fromIndex > toIndex)
                throw new IllegalArgumentException("fromIndex(" + fromIndex +
                                                   ") > toIndex(" + toIndex + ")");
            l = list;
            offset = fromIndex;
            size = toIndex - fromIndex;
            this.modCount = l.modCount;
        }

        public E set(int index, E element) {
            rangeCheck(index);
            checkForComodification();
            return l.set(index+offset, element);
        }

        public E get(int index) {
            rangeCheck(index);
            checkForComodification();
            return l.get(index+offset);
        }

        public int size() {
            checkForComodification();
            return size;
        }

        public void add(int index, E element) {
            rangeCheckForAdd(index);
            checkForComodification();
            l.add(index+offset, element);
            this.modCount = l.modCount;
            size++;
        }

        public E remove(int index) {
            rangeCheck(index);
            checkForComodification();
            E result = l.remove(index+offset);
            this.modCount = l.modCount;
            size--;
            return result;
        }

        protected void removeRange(int fromIndex, int toIndex) {
            checkForComodification();
            l.removeRange(fromIndex+offset, toIndex+offset);
            this.modCount = l.modCount;
            size -= (toIndex-fromIndex);
        }

        public boolean addAll(Collection<? extends E> c) {
            return addAll(size, c);
        }

        public boolean addAll(int index, Collection<? extends E> c) {
            rangeCheckForAdd(index);
            int cSize = c.size();
            if (cSize==0)
                return false;

            checkForComodification();
            l.addAll(offset+index, c);
            this.modCount = l.modCount;
            size += cSize;
            return true;
        }

        public Iterator<E> iterator() {
            return listIterator();
        }

        public ListIterator<E> listIterator(final int index) {
            checkForComodification();
            rangeCheckForAdd(index);

            return new ListIterator<E>() {
                private final ListIterator<E> i = l.listIterator(index+offset);

                public boolean hasNext() {
                    return nextIndex() < size;
                }

                public E next() {
                    if (hasNext())
                        return i.next();
                    else
                        throw new NoSuchElementException();
                }

                public boolean hasPrevious() {
                    return previousIndex() >= 0;
                }

                public E previous() {
                    if (hasPrevious())
                        return i.previous();
                    else
                        throw new NoSuchElementException();
                }

                public int nextIndex() {
                    return i.nextIndex() - offset;
                }

                public int previousIndex() {
                    return i.previousIndex() - offset;
                }

                public void remove() {
                    i.remove();
                    SubList.this.modCount = l.modCount;
                    size--;
                }

                public void set(E e) {
                    i.set(e);
                }

                public void add(E e) {
                    i.add(e);
                    SubList.this.modCount = l.modCount;
                    size++;
                }
            };
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new SubList<>(this, fromIndex, toIndex);
        }

        private void rangeCheck(int index) {
            if (index < 0 || index >= size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private void rangeCheckForAdd(int index) {
            if (index < 0 || index > size)
                throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }

        private String outOfBoundsMsg(int index) {
            return "Index: "+index+", Size: "+size;
        }

        private void checkForComodification() {
            if (this.modCount != l.modCount)
                throw new ConcurrentModificationException();
        }
    }
    
    class RandomAccessSubList<E> extends SubList<E> implements RandomAccess {
        RandomAccessSubList(AbstractList<E> list, int fromIndex, int toIndex) {
        	//TODO  疑问
            super(list, fromIndex, toIndex);
        }

        public List<E> subList(int fromIndex, int toIndex) {
            return new RandomAccessSubList<>(this, fromIndex, toIndex);
        }
    }
}
  
