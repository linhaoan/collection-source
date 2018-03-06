/**  
 * Project Name:collection-source  
 * File Name:Collection.java  
 * Package Name:source.java.util  
 * Date:2018年3月6日下午2:13:47  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

/**
 * The root interface in the <i>collection hierarchy</i>.  A collection
 * represents a group of objects, known as its <i>elements</i>.  Some
 * collections allow duplicate elements and others do not.  Some are ordered
 * and others unordered.  The JDK does not provide any <i>direct</i>
 * implementations of this interface: it provides implementations of more
 * specific subinterfaces like <tt>Set</tt> and <tt>List</tt>.  This interface
 * is typically used to pass collections around and manipulate them where
 * maximum generality is desired.<br/>
 * 集合层次结构中的根接口。集合表示一组对象，即所谓的元素。一些集合允许重复的元素，而另一些则不允许。
 * 有些是有序的，有些是无序的。JDK并没有提供该接口的任何直接实现：它提供了诸如Set和List之类更具体的子接口的实现。
 * 该接口通常用于传递集合，并在需要最大限度通用性的情况下操作它们。
 *
 * <p><i>Bags</i> or <i>multisets</i> (unordered collections that may contain
 * duplicate elements) should implement this interface directly.<br/>
 * 包或多集（可能包含重复元素的无序集合）应该直接实现这个接口。<br/>
 *
 * <p>All general-purpose <tt>Collection</tt> implementation classes (which
 * typically implement <tt>Collection</tt> indirectly through one of its
 * subinterfaces) should provide two "standard" constructors: a void (no
 * arguments) constructor, which creates an empty collection, and a
 * constructor with a single argument of type <tt>Collection</tt>, which
 * creates a new collection with the same elements as its argument.  In
 * effect, the latter constructor allows the user to copy any collection,
 * producing an equivalent collection of the desired implementation type.
 * There is no way to enforce this convention (as interfaces cannot contain
 * constructors) but all of the general-purpose <tt>Collection</tt>
 * implementations in the Java platform libraries comply.<br/>
 * 所有通用集合实现类(通常实现收集间接通过它的一个type)应提供“标准”两个构造函数:一个空构造函数(没有参数),它创建了一个空集合,
 * 和一个参数的构造函数类型集合,它创建一个新的集合相同的元素作为其参数。
 * 实际上，后者的构造函数允许用户复制任何集合，从而产生所需的实现类型的等价集合。
 * 没有办法强制执行这个约定（因为接口不能包含构造函数），但是Java平台库中的所有通用集合实现都遵从。
 *
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the collection on which they operate, are specified to
 * throw <tt>UnsupportedOperationException</tt> if this collection does not
 * support the operation.  If this is the case, these methods may, but are not
 * required to, throw an <tt>UnsupportedOperationException</tt> if the
 * invocation would have no effect on the collection.  For example, invoking
 * the {@link #addAll(Collection)} method on an unmodifiable collection may,
 * but is not required to, throw the exception if the collection to be added
 * is empty.<br/>
 * 此接口中包含的“破坏性”方法,即修改集合的方法操作,指定抛出UnsupportedOperationException如果这个集合不支持方式操作。
 * 如果是这样的话,这些方法可能,但不需要,抛出UnsupportedOperationException如果调用方式集合上的没有影响。
 * 例如，在不可修改的集合上调用addAll（托收）方法，但是不需要，如果要添加的集合是空的，抛出异常。
 * 
 * <p><a name="optional-restrictions">
 * Some collection implementations have restrictions on the elements that
 * they may contain.</a>  For example, some implementations prohibit null elements,
 * and some have restrictions on the types of their elements.  Attempting to
 * add an ineligible element throws an unchecked exception, typically
 * <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.  Attempting
 * to query the presence of an ineligible element may throw an exception,
 * or it may simply return false; some implementations will exhibit the former
 * behavior and some will exhibit the latter.  More generally, attempting an
 * operation on an ineligible element whose completion would not result in
 * the insertion of an ineligible element into the collection may throw an
 * exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.<br/>
 * 一些集合实现对它们可能对包含的元素有限制。例如，有些实现禁止零元素，有些实现对其元素的类型有限制。
 * 试图添加一个不合格的元素会抛出一个未经检查的异常，通常是NullPointerException或ClassCastException。
 * 试图查询不合格元素的存在可能会抛出异常，或者它可能只是返回false;一些实现将展示前者的行为，而有些实现将展示后者。
 * 更一般的情况是，尝试对一个不符合条件的元素进行操作，其完成不会导致将不合格的元素插入到集合中，可能会抛出异常，或者在实现的选项中可能成功。
 * 在该接口的规范中，这些异常被标记为“可选”。
 * 
 * 
 * <p>It is up to each collection to determine its own synchronization
 * policy.  In the absence of a stronger guarantee by the
 * implementation, undefined behavior may result from the invocation
 * of any method on a collection that is being mutated by another
 * thread; this includes direct invocations, passing the collection to
 * a method that might perform invocations, and using an existing
 * iterator to examine the collection.<br/>
 * 
 * 由每个集合决定它自己的同步策略。
 * 在缺乏实现的更强保证的情况下，未定义的行为可能是由于在一个集合上的任何方法被另一个线程发生了突变而导致的;
 * 这包括直接调用，将收集传递给一个可能执行调用的方法，并使用现有的迭代器检查集合。
 * 
 * <p>Many methods in Collections Framework interfaces are defined in
 * terms of the {@link Object#equals(Object) equals} method.  For example,
 * the specification for the {@link #contains(Object) contains(Object o)}
 * method says: "returns <tt>true</tt> if and only if this collection
 * contains at least one element <tt>e</tt> such that
 * <tt>(o==null ? e==null : o.equals(e))</tt>."  This specification should
 * <i>not</i> be construed to imply that invoking <tt>Collection.contains</tt>
 * with a non-null argument <tt>o</tt> will cause <tt>o.equals(e)</tt> to be
 * invoked for any element <tt>e</tt>.  Implementations are free to implement
 * optimizations whereby the <tt>equals</tt> invocation is avoided, for
 * example, by first comparing the hash codes of the two elements.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.<br/>
 *
 * 集合框架接口中的许多方法都是用equals方法定义的。例如，包含（Object o）方法的规范说：
 * “如果这个集合包含至少一个元素e，那么返回true，这样（o==null？”e = = null:o.equals(e))。”该规范不应被理解为调用集合。
       包含一个非null的参数o将会为任何元素调用o.equals（e）。实现可以自由地实现对平等调用的优化，例如，首先比较这两个元素的散列码。
     （hashcode（）规范保证两个具有不相等的散列码的对象不能相等。）
        更一般地说，各种集合框架接口的实现可以自由地利用底层对象方法的指定行为，无论实现者认为它合适。
        
 * <p>Some collection operations which perform recursive traversal of the
 * collection may fail with an exception for self-referential instances where
 * the collection directly or indirectly contains itself. This includes the
 * {@code clone()}, {@code equals()}, {@code hashCode()} and {@code toString()}
 * methods. Implementations may optionally handle the self-referential scenario,
 * however most current implementations do not do so.<br/>
 * 一些对集合进行递归遍历的收集操作可能会失败，因为这些集合直接或间接地包含了自己的自引用实例。这包括克隆（）、equals（）、hashCode（）和toString（）方法。
 * 实现可以有选择地处理自引用场景，但是大多数当前实现都不这么做。
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @implSpec
 * The default method implementations (inherited or otherwise) do not apply any
 * synchronization protocol.  If a {@code Collection} implementation has a
 * specific synchronization protocol, then it must override default
 * implementations to apply that protocol.<br/>
 * 默认的方法实现（继承的或其他的）不应用任何同步协议。
 * 如果一个集合实现有一个特定的同步协议，那么它必须覆盖缺省实现来应用该协议。
 *
 * @param <E> the type of elements in this collection
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see     Set
 * @see     List
 * @see     Map
 * @see     SortedSet
 * @see     SortedMap
 * @see     HashSet
 * @see     TreeSet
 * @see     ArrayList
 * @see     LinkedList
 * @see     Vector
 * @see     Collections
 * @see     Arrays
 * @see     AbstractCollection
 * @since 1.2
 */
public interface Collection<E> extends Iterable<E>{

}
  
