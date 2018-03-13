/**  
 * Project Name:collection-source  
 * File Name:AbstractSet.java  
 * Package Name:source.java.util  
 * Date:2018年3月13日上午11:10:58  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * This class provides a skeletal implementation of the <tt>Set</tt>
 * interface to minimize the effort required to implement this
 * interface. <p>
 * 这个类提供了集合接口的一个框架实现，以最小化实现该接口所需的工作。
 * The process of implementing a set by extending this class is identical
 * to that of implementing a Collection by extending AbstractCollection,
 * except that all of the methods and constructors in subclasses of this
 * class must obey the additional constraints imposed by the <tt>Set</tt>
 * interface (for instance, the add method must not permit addition of
 * multiple instances of an object to a set).<p>
 * 通过扩展这个类实现一组的过程是相同的,实现通过扩展AbstractCollection集合,
 * 除了所有的方法和构造函数在这个类的子类必须遵守额外设置界面所施加的约束(例如,添加方法必须不允许添加一组对象)的多个实例。
 * 
 * Note that this class does not override any of the implementations from
 * the <tt>AbstractCollection</tt> class.  It merely adds implementations
 * for <tt>equals</tt> and <tt>hashCode</tt>.<p>
 * 注意，这个类没有覆盖AbstractCollection类中的任何实现。它只是为equals和hashCode添加了实现。
 * 
 * This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> the type of elements maintained by this set
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Collection
 * @see AbstractCollection
 * @see Set
 * @since 1.2
 */

public abstract class AbstractSet<E> extends AbstractCollection<E> implements Set<E> {

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected AbstractSet() {
    }
    
    // Comparison and hashing

    /**
     * Compares the specified object with this set for equality.  Returns
     * <tt>true</tt> if the given object is also a set, the two sets have
     * the same size, and every member of the given set is contained in
     * this set.  This ensures that the <tt>equals</tt> method works
     * properly across different implementations of the <tt>Set</tt>
     * interface.<p>
     * 将指定的对象与该集合进行比较。
     * 如果给定的对象也是一个集合，那么返回true，这两个集合的大小相同，并且给定集合中的每个成员都包含在这个集合中，这确保了相等方法在不同的集合接口的实现中正确地工作。
     * 
     * This implementation first checks if the specified object is this
     * set; if so it returns <tt>true</tt>.  Then, it checks if the
     * specified object is a set whose size is identical to the size of
     * this set; if not, it returns false.  If so, it returns
     * <tt>containsAll((Collection) o)</tt>.
     * 这个实现首先检查指定的对象是否是这个集合;如果是这样，它返回true。
     * 然后，它检查指定的对象是否为一组大小与该集合的大小相同的集合;如果不是，则返回false。如果是这样，它将返回容器所有（（集合）o）。
     * @param o object to be compared for equality with this set
     * @return <tt>true</tt> if the specified object is equal to this set
     */
    public boolean equals(Object o) {
       if ( o == this) {
    	   return true;
       }
       if( !(o instanceof Set)) {
    	   return false;
       }
       Collection<?> c = (Collection<?>) o;
       if (c.size() != size()) {
    	   return false;
       }
       try {
           return containsAll(c);
       } catch (ClassCastException unused)   {
           return false;
       } catch (NullPointerException unused) {
           return false;
       }
       
    }

    /**
     * Returns the hash code value for this set.  The hash code of a set is
     * defined to be the sum of the hash codes of the elements in the set,
     * where the hash code of a <tt>null</tt> element is defined to be zero.
     * This ensures that <tt>s1.equals(s2)</tt> implies that
     * <tt>s1.hashCode()==s2.hashCode()</tt> for any two sets <tt>s1</tt>
     * and <tt>s2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     * 返回这个集合的散列码值。集合的散列码被定义为集合中元素的散列码的和，其中一个空元素的散列码被定义为0。
     * 这可以确保s1.equals（s2）意味着对于任意两个集合s1和s2的s1.hashcode（）==s2.hashcode（），这是由object.hashcode的一般契约所要求的。
     * 
     * <p>This implementation iterates over the set, calling the
     * <tt>hashCode</tt> method on each element in the set, and adding up
     * the results.
     * 这个实现在集合上迭代，调用集合中的每个元素上的hashCode方法，并添加结果。
     * 
     * @return the hash code value for this set
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     */
    public int hashCode() {
        int h = 0;
        Iterator<E> i = iterator();
        while (i.hasNext()) {
            E obj = i.next();
            if (obj != null)
                h += obj.hashCode();
        }
        return h;
    }

    /**
     * Removes from this set all of its elements that are contained in the
     * specified collection (optional operation).  If the specified
     * collection is also a set, this operation effectively modifies this
     * set so that its value is the <i>asymmetric set difference</i> of
     * the two sets.
     * 从这个集合中删除包含在指定集合中的所有元素（可选操作）。
     * 如果指定的集合也是一个集合，那么这个操作将有效地修改这个集合，使它的值是这两个集合的不对称集差。
     * 
     * <p>This implementation determines which is the smaller of this set
     * and the specified collection, by invoking the <tt>size</tt>
     * method on each.  If this set has fewer elements, then the
     * implementation iterates over this set, checking each element
     * returned by the iterator in turn to see if it is contained in
     * the specified collection.  If it is so contained, it is removed
     * from this set with the iterator's <tt>remove</tt> method.  If
     * the specified collection has fewer elements, then the
     * implementation iterates over the specified collection, removing
     * from this set each element returned by the iterator, using this
     * set's <tt>remove</tt> method.
     * 这个实现通过调用每个集合中的size方法来确定这个集合和指定的集合的哪个更小。
     * 如果这个集合有更少的元素，那么实现在这个集合上迭代，检查迭代器返回的每个元素，看看它是否包含在指定的集合中。
     * 如果它被包含了，那么它就会被迭代器的remove方法从这个集合中删除。
     * 如果指定的集合有更少的元素，那么实现就会迭代指定的集合，从这个集合中删除迭代器返回的每个元素，使用这个集合的remove方法。
     * 
     * 
     * <p>Note that this implementation will throw an
     * <tt>UnsupportedOperationException</tt> if the iterator returned by the
     * <tt>iterator</tt> method does not implement the <tt>remove</tt> method.
     * 注意,这个实现将抛出UnsupportedOperationException如果方式迭代器返回的迭代器没有实现方法去除方法。
     * 
     * @param  c collection containing elements to be removed from this set
     * @return <tt>true</tt> if this set changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> operation
     *         is not supported by this set
     * @throws ClassCastException if the class of an element of this set
     *         is incompatible with the specified collection
     * (<a href="Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if this set contains a null element and the
     *         specified collection does not permit null elements
     * (<a href="Collection.html#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    public boolean removeAll(Collection<?> c) {
        Objects.requireNonNull(c);
        boolean modified = false;

        // 如果指定的集合有更少的元素，那么实现就会迭代指定的集合，从这个集合中删除迭代器返回的每个元素，使用这个集合的remove方法。
        if (size() > c.size()) {
            for (Iterator<?> i = c.iterator(); i.hasNext(); )
                modified |= remove(i.next());
        } else {
        
        // 如果这个集合有更少的元素，那么实现在这个集合上迭代，检查迭代器返回的每个元素，看看它是否包含在指定的集合中。
        // 如果它被包含了，那么它就会被迭代器的remove方法从这个集合中删除。
            for (Iterator<?> i = iterator(); i.hasNext(); ) {
                if (c.contains(i.next())) {
                    i.remove();
                    modified = true;
                }
            }
        }
        
        return modified;
    }


}
  
