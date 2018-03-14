/**  
 * Project Name:collection-source  
 * File Name:Comparable.java  
 * Package Name:source.java.lang  
 * Date:2018年3月14日下午4:09:10  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.lang;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

/**
 * This interface imposes a total ordering on the objects of each class that
 * implements it.  This ordering is referred to as the class's <i>natural
 * ordering</i>, and the class's <tt>compareTo</tt> method is referred to as
 * its <i>natural comparison method</i>.<p>
 * 这个接口对实现它的每个类的对象强制执行一个总的排序。这种排序被称为类的自然排序，而类的比较方法被称为它的自然比较方法。
 * 
 * Lists (and arrays) of objects that implement this interface can be sorted
 * automatically by {@link Collections#sort(List) Collections.sort} (and
 * {@link Arrays#sort(Object[]) Arrays.sort}).  Objects that implement this
 * interface can be used as keys in a {@linkplain SortedMap sorted map} or as
 * elements in a {@linkplain SortedSet sorted set}, without the need to
 * specify a {@linkplain Comparator comparator}.<p>
 * 实现此接口的对象的列表（和数组）可以根据集合Collections.sort(List)。(和Arrays.sort)。
 * 实现这个接口的对象可以作为排序映射中的键，或者作为一个排序集合中的元素，而不需要指定一个比较器。
 * 
 * The natural ordering for a class <tt>C</tt> is said to be <i>consistent
 * with equals</i> if and only if <tt>e1.compareTo(e2) == 0</tt> has
 * the same boolean value as <tt>e1.equals(e2)</tt> for every
 * <tt>e1</tt> and <tt>e2</tt> of class <tt>C</tt>.  Note that <tt>null</tt>
 * is not an instance of any class, and <tt>e.compareTo(null)</tt> should
 * throw a <tt>NullPointerException</tt> even though <tt>e.equals(null)</tt>
 * returns <tt>false</tt>.<p>
 * 自然排序的类C被认为是符合=当且仅当e1.compareTo(e2)= = 0 . equals(e2)一样的布尔值为每个e1和e2类C的注意空不是任何类的一个实例,
 * 和e.compareTo(null)应该抛出NullPointerException即使e.equals(null)返回false。
 *  
 * It is strongly recommended (though not required) that natural orderings be
 * consistent with equals.  This is so because sorted sets (and sorted maps)
 * without explicit comparators behave "strangely" when they are used with
 * elements (or keys) whose natural ordering is inconsistent with equals.  In
 * particular, such a sorted set (or sorted map) violates the general contract
 * for set (or map), which is defined in terms of the <tt>equals</tt>
 * method.<p>
 * 强烈建议（尽管不需要）自然的顺序是一致的。
 * 这是因为排序集（和排序的映射）没有显式的比较器，当它们与元素（或键）一起使用时，它们的行为“奇怪”，因为它们的自然排序与equals不一致。
 * 特别地，这样一个排序的集合（或排序的映射）违反了集合（或map）的一般契约，它是按照equals方法定义的。
 * 
 * For example, if one adds two keys <tt>a</tt> and <tt>b</tt> such that
 * {@code (!a.equals(b) && a.compareTo(b) == 0)} to a sorted
 * set that does not use an explicit comparator, the second <tt>add</tt>
 * operation returns false (and the size of the sorted set does not increase)
 * because <tt>a</tt> and <tt>b</tt> are equivalent from the sorted set's
 * perspective.<p>
 *  例如,如果a和b,这样增加了两个键(! a.equals(b)& & a.compareTo(b)= = 0)一组排序,不使用显式的比较器,
 *  第二个添加操作返回false(和的大小排序设置不会增加),因为a和b是等价的排序设置的角度来看。
 *  
 * Virtually all Java core classes that implement <tt>Comparable</tt> have natural
 * orderings that are consistent with equals.  One exception is
 * <tt>java.math.BigDecimal</tt>, whose natural ordering equates
 * <tt>BigDecimal</tt> objects with equal values and different precisions
 * (such as 4.0 and 4.00).<p>
 * 几乎所有实现可比的Java核心类都具有与equals一致的自然顺序。
 * 一个例外是java.math。BigDecimal，它的自然顺序等同于BigDecimal对象具有相同的值和不同的精度（例如4.0和4.00）。
 * 
 * For the mathematically inclined, the <i>relation</i> that defines
 * the natural ordering on a given class C is:<pre>
 *       {(x, y) such that x.compareTo(y) &lt;= 0}.
 * </pre> The <i>quotient</i> for this total order is: <pre>
 *       {(x, y) such that x.compareTo(y) == 0}.
 * </pre>
 * 对于数学上的倾斜，定义了给定类C的自然排序的关系是：
 * It follows immediately from the contract for <tt>compareTo</tt> that the
 * quotient is an <i>equivalence relation</i> on <tt>C</tt>, and that the
 * natural ordering is a <i>total order</i> on <tt>C</tt>.  When we say that a
 * class's natural ordering is <i>consistent with equals</i>, we mean that the
 * quotient for the natural ordering is the equivalence relation defined by
 * the class's {@link Object#equals(Object) equals(Object)} method:<pre>
 *     {(x, y) such that x.equals(y)}. </pre><p>
 *
 * This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <T> the type of objects that this object may be compared to
 *
 * @author  Josh Bloch
 * @see java.util.Comparator
 * @since 1.2
 */
public interface Comparable<T> {
    /**
     * Compares this object with the specified object for order.  Returns a
     * negative integer, zero, or a positive integer as this object is less
     * than, equal to, or greater than the specified object.
     * 
     * 将该对象与指定对象进行比较。返回一个负整数，零，或一个正整数，因为这个对象小于，等于或大于指定的对象。
     * 
     * <p>The implementor must ensure <tt>sgn(x.compareTo(y)) ==
     * -sgn(y.compareTo(x))</tt> for all <tt>x</tt> and <tt>y</tt>.  (This
     * implies that <tt>x.compareTo(y)</tt> must throw an exception iff
     * <tt>y.compareTo(x)</tt> throws an exception.)
     *
     * <p>The implementor must also ensure that the relation is transitive:
     * <tt>(x.compareTo(y)&gt;0 &amp;&amp; y.compareTo(z)&gt;0)</tt> implies
     * <tt>x.compareTo(z)&gt;0</tt>.
     *
     * <p>Finally, the implementor must ensure that <tt>x.compareTo(y)==0</tt>
     * implies that <tt>sgn(x.compareTo(z)) == sgn(y.compareTo(z))</tt>, for
     * all <tt>z</tt>.
     *
     * <p>It is strongly recommended, but <i>not</i> strictly required that
     * <tt>(x.compareTo(y)==0) == (x.equals(y))</tt>.  Generally speaking, any
     * class that implements the <tt>Comparable</tt> interface and violates
     * this condition should clearly indicate this fact.  The recommended
     * language is "Note: this class has a natural ordering that is
     * inconsistent with equals."
     *
     * <p>In the foregoing description, the notation
     * <tt>sgn(</tt><i>expression</i><tt>)</tt> designates the mathematical
     * <i>signum</i> function, which is defined to return one of <tt>-1</tt>,
     * <tt>0</tt>, or <tt>1</tt> according to whether the value of
     * <i>expression</i> is negative, zero or positive.
     *
     * @param   o the object to be compared.
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     *
     * @throws NullPointerException if the specified object is null
     * @throws ClassCastException if the specified object's type prevents it
     *         from being compared to this object.
     */
    public int compareTo(T o);
}
  
