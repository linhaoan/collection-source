 
  
package source.java.util;

import java.util.NoSuchElementException;

/**
 * An iterator over a collection.  {@code Iterator} takes the place of
 * {@link Enumeration} in the Java Collections Framework.  Iterators
 * differ from enumerations in two ways:
 *
 * <ul>
 *      <li> Iterators allow the caller to remove elements from the
 *           underlying collection during the iteration with well-defined
 *           semantics.
 *      <li> Method names have been improved.
 * </ul>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <E> the type of elements returned by this iterator
 *
 * @author  Josh Bloch
 * @see Collection
 * @see ListIterator
 * @see Iterable
 * @since 1.2
 */
/**
 * 迭代器的集合。Iterator在Java集合框架中代替枚举。迭代器与枚举的区别有两种：<br/>
 * 1:迭代器允许调用者在具有定义良好的语义的迭代期间从底层集合中删除元素。<br/>
 * 2:方法名得到了改进。<br/>
 * ClassName: Iterator <br/>
 * date: 2018年3月6日 上午11:27:41 <br/>  
 *  
 * @author linhao 
 * @version @param <E>
 */
public interface Iterator<E> {

    /**
     * Returns {@code true} if the iteration has more elements.
     * (In other words, returns {@code true} if {@link #next} would
     * return an element rather than throwing an exception.) <br/>
     * 如果迭代有更多的元素，则返回true。（换句话说，如果next将返回一个元素而不是抛出一个异常，则返回true）。 <br/>
     * @return {@code true} if the iteration has more elements 
     */
    boolean hasNext();
    
    /**
     * Returns the next element in the iteration. <br/>
     * 在迭代中返回下一个元素。
     * @return the next element in the iteration<br/>
     * 下一个迭代的元素
     * @throws NoSuchElementException if the iteration has no more elements<br/>
     * 迭代没有更多的元素
     */
    E next();
    
    /**
     * Removes from the underlying collection the last element returned
     * by this iterator (optional operation).  This method can be called
     * only once per call to {@link #next}.  The behavior of an iterator
     * is unspecified if the underlying collection is modified while the
     * iteration is in progress in any way other than by calling this
     * method.<br/>
     * 从下层集合中删除这个迭代器返回的最后一个元素（可选操作）。这种方法只能在每次调用时调用一次。
     * 迭代器的行为是不确定的，如果在迭代过程中，除了调用这个方法之外，迭代的过程是在进行的。
     * @implSpec
     * The default implementation throws an instance of
     * {@link UnsupportedOperationException} and performs no other action.
     *
     * @throws UnsupportedOperationException if the {@code remove}
     *         operation is not supported by this iterator<br/>
     *		       这个迭代器不支持删除操作
     * @throws IllegalStateException if the {@code next} method has not
     *         yet been called, or the {@code remove} method has already
     *         been called after the last call to the {@code next}
     *         method<br/>
     *         如果下一个方法尚未被调用，或者remove方法已经在对下一个方法的最后一次调用之后被调用了
     */
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }
}
  
