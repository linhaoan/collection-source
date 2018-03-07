/**  
 * Project Name:collection-source  
 * File Name:Collection.java  
 * Package Name:source.java.util  
 * Date:2018年3月6日下午2:13:47  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
 * “如果这个集合包含至少一个元素e，那么返回true，这样（o==null？”e == null:o.equals(e))。”该规范不应被理解为调用集合。
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
	
	// Query Operations
	// 查询操作
    /**
     * Returns the number of elements in this collection.  If this collection
     * contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.<br/>
     * 返回这个集合中元素的数量。如果这个集合包含多于整数.MAX_VALUE元素,返回Integer.MAX_VALUE。
     * @return the number of elements in this collection
     */
    int size();
    
    /**
     * Returns <tt>true</tt> if this collection contains no elements.<br/>
     * 如果这个集合不包含元素，那么返回true。
     * @return <tt>true</tt> if this collection contains no elements
     */
    boolean isEmpty();
    
    /**
     * Returns <tt>true</tt> if this collection contains the specified element.
     * More formally, returns <tt>true</tt> if and only if this collection
     * contains at least one element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>.<br/>
     * 如果该集合包含指定的元素，则返回true。更正式地说，如果这个集合包含至少一个元素e，那么返回true，这样（o==null？e = = null:o.equals(e))。
     * @param o element whose presence in this collection is to be tested <br/>
     * 在这个集合中存在的元素将被测试
     * @return <tt>true</tt> if this collection contains the specified 
     *         element <br/>
     *         如果这个集合包含指定的元素，则返回true
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this collection
     *         如果指定元素的类型与此集合不兼容
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     *         如果指定的元素为null，这个集合不允许空元素
     *         (<a href="#optional-restrictions">optional</a>)
     */
    boolean contains(Object o);
    
    /**
     * Returns an iterator over the elements in this collection.  There are no
     * guarantees concerning the order in which the elements are returned
     * (unless this collection is an instance of some class that provides a
     * guarantee).<br/>
     * 在这个集合中返回一个迭代器。对于返回元素的顺序没有任何保证（除非这个集合是提供保证的类的实例）。
     * 
     * @return an <tt>Iterator</tt> over the elements in this collection
     */
    Iterator<E> iterator();
    
    /**
     * Returns an array containing all of the elements in this collection.
     * If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.<br/>
     * 返回包含该集合中所有元素的数组。如果该集合对其迭代器返回的元素顺序有任何保证，那么该方法必须以相同的顺序返回元素。
     * 
     * <p>The returned array will be "safe" in that no references to it are
     * maintained by this collection.  (In other words, this method must
     * allocate a new array even if this collection is backed by an array).
     * The caller is thus free to modify the returned array.<br/>
     * 返回的数组将是“安全的”，因为这个集合没有对它进行任何引用。（换句话说，这个方法必须分配一个新的数组，即使这个集合是由数组支持的）。
     * 因此，调用者可以自由地修改返回的数组。
     * 
     * <p>This method acts as bridge between array-based and collection-based
     * APIs.<br/>
     * 该方法充当基于数组的和基于集合的api之间的桥梁。
     *
     * @return an array containing all of the elements in this collection <br/>
     * 包含该集合中的所有元素的数组
     */
    Object[] toArray();
    
    /**
     * Returns an array containing all of the elements in this collection;
     * the runtime type of the returned array is that of the specified array.
     * If the collection fits in the specified array, it is returned therein.
     * Otherwise, a new array is allocated with the runtime type of the
     * specified array and the size of this collection.<br/>
     * 返回包含该集合中所有元素的数组;返回的数组的运行时类型是指定的数组。
     * 如果集合适合于指定的数组，则返回该集合。否则，将使用指定数组的运行时类型和该集合的大小来分配一个新数组。
     * 
     * <p>If this collection fits in the specified array with room to spare
     * (i.e., the array has more elements than this collection), the element
     * in the array immediately following the end of the collection is set to
     * <tt>null</tt>.  (This is useful in determining the length of this
     * collection <i>only</i> if the caller knows that this collection does
     * not contain any <tt>null</tt> elements.)<br/>
     * 如果这个集合在指定的数组中有空闲空间（即：这个数组比这个集合有更多的元素），在集合结束后的数组中的元素被设置为null。
     * （只有在调用者知道该集合不包含任何空元素时，才能确定该集合的长度。）
     *
     * <p>If this collection makes any guarantees as to what order its elements
     * are returned by its iterator, this method must return the elements in
     * the same order.<br/>
     * 如果该集合对其迭代器返回的元素顺序有任何保证，那么该方法必须以相同的顺序返回元素。
     *
     * <p>Like the {@link #toArray()} method, this method acts as bridge between
     * array-based and collection-based APIs.  Further, this method allows
     * precise control over the runtime type of the output array, and may,
     * under certain circumstances, be used to save allocation costs.<br/>
     * 与toArray（）方法一样，该方法充当基于数组的和基于集合的api之间的桥梁。
     * 此外，该方法允许对输出数组的运行时类型进行精确控制，并且在某些情况下可以使用它来节省分配成本。
     *
     * <p>Suppose <tt>x</tt> is a collection known to contain only strings.
     * The following code can be used to dump the collection into a newly
     * allocated array of <tt>String</tt>:
     *
     * <pre>
     *     String[] y = x.toArray(new String[0]);</pre>
     *
     * Note that <tt>toArray(new Object[0])</tt> is identical in function to
     * <tt>toArray()</tt>.<br/>
     * 
     * 假设x是只包含字符串的集合。下面的代码可用于将集合转储为新分配的字符串数组：
     * 
     *    String[] y = x.toArray(new String[0]);
     * 
     * 注意，toArray(new Object[0])在函数中与toArray()是相同的。
     *
     * @param <T> the runtime type of the array to contain the collection
     * 包含该集合的数组的运行时类型
     * @param a the array into which the elements of this collection are to be
     *        stored, if it is big enough; otherwise, a new array of the same
     *        runtime type is allocated for this purpose.<br/>
     *        如果数据足够大，则存储该集合元素的数组;否则，将为这个目的分配相同的运行时类型的新数组。
     * @return an array containing all of the elements in this collection
     * 		     包含该集合中的所有元素的数组
     * @throws ArrayStoreException if the runtime type of the specified array
     *         is not a supertype of the runtime type of every element in
     *         this collection
     *         如果指定数组的运行时类型不是该集合中每个元素的运行时类型的超类型
     * @throws NullPointerException if the specified array is null
     * 		       如果指定的数组为空
     */
    <T> T[] toArray(T[] a);
    
    
    // Modification Operations
    // 修改操作
    
    /**
     * Ensures that this collection contains the specified element (optional
     * operation).  Returns <tt>true</tt> if this collection changed as a
     * result of the call.  (Returns <tt>false</tt> if this collection does
     * not permit duplicates and already contains the specified element.)<p>
     * 保该集合包含指定的元素（可选操作）。如果该集合由于调用而更改，那么返回true。
     * （如果这个集合不允许重复，并且已经包含了指定的元素，则返回false。）<br/>
     *
     * Collections that support this operation may place limitations on what
     * elements may be added to this collection.  In particular, some
     * collections will refuse to add <tt>null</tt> elements, and others will
     * impose restrictions on the type of elements that may be added.
     * Collection classes should clearly specify in their documentation any
     * restrictions on what elements may be added.<p>
     * 支持此操作的集合可能对将添加到该集合的元素进行限制。
     * 特别地，一些集合将拒绝添加空元素，而另一些集合将对可能添加的元素的类型施加限制。
     * 集合类应该清楚地在它们的文档中指定任何可能添加的元素的限制。<br/>
     * 
     * If a collection refuses to add a particular element for any reason
     * other than that it already contains the element, it <i>must</i> throw
     * an exception (rather than returning <tt>false</tt>).  This preserves
     * the invariant that a collection always contains the specified element
     * after this call returns.<br/>
     * 如果一个集合拒绝为任何原因添加特定元素，除了它已经包含该元素，它必须抛出一个异常（而不是返回false）。
     * 这保留了在调用返回之后集合中始终包含指定元素的不变式。
     * @param e element whose presence in this collection is to be ensured
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call
     *         如果这个集合因为调用而改变了
     * @throws UnsupportedOperationException if the <tt>add</tt> operation
     *         is not supported by this collection
     *         如果这个集合不支持add操作
     * @throws ClassCastException if the class of the specified element
     *         prevents it from being added to this collection
     *         如果指定元素的类阻止它被添加到这个集合中
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     *         如果指定的元素为null，这个集合不允许空元素
     * @throws IllegalArgumentException if some property of the element
     *         prevents it from being added to this collection
     *         如果元素的某个属性阻止它被添加到这个集合中
     * @throws IllegalStateException if the element cannot be added at this
     *         time due to insertion restrictions
     *         如果由于插入限制而不能在此时添加元素
     */
    boolean add(E e);
    
    /**
     * Removes a single instance of the specified element from this
     * collection, if it is present (optional operation).  More formally,
     * removes an element <tt>e</tt> such that
     * <tt>(o==null&nbsp;?&nbsp;e==null&nbsp;:&nbsp;o.equals(e))</tt>, if
     * this collection contains one or more such elements.  Returns
     * <tt>true</tt> if this collection contained the specified element (or
     * equivalently, if this collection changed as a result of the call).
     * 从这个集合中删除指定元素的一个实例，如果它存在（可选操作）。
     * 更正式地，删除元素e这样的元素（o==null？如果这个集合包含一个或多个这样的元素，那么e==null:o.equals（e））。
     * 如果这个集合包含指定的元素（或者，如果该集合由于调用的结果而改变），则返回true。
     *
     * @param o element to be removed from this collection, if present
     * @return <tt>true</tt> if an element was removed as a result of this call
     * 如果一个元素因为这个调用而被移除
     * @throws ClassCastException if the type of the specified element
     *         is incompatible with this collection
     *         (<a href="#optional-restrictions">optional</a>)
     *         如果指定元素的类型与此集合不兼容
     *         
     * @throws NullPointerException if the specified element is null and this
     *         collection does not permit null elements
     *         (<a href="#optional-restrictions">optional</a>)
     *         如果指定的元素为null，这个集合不允许空元素
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this collection
     *         如果这个集合不支持删除操作
     */
    boolean remove(Object o);
    
    // Bulk Operations
    // 批量操作
    
    /**
     * Returns <tt>true</tt> if this collection contains all of the elements
     * in the specified collection.
     * 如果该集合包含指定集合中的所有元素，那么返回true。
     * 
     * @param  c collection to be checked for containment in this collection
     * 在这个集合中，要检查包含容器的c集合
     * @return <tt>true</tt> if this collection contains all of the elements
     *         in the specified collection
     *         如果这个集合包含指定集合中的所有元素，那么返回true
     * @throws ClassCastException if the types of one or more elements
     *         in the specified collection are incompatible with this
     *         collection
     *         如果指定集合中的一个或多个元素的类型与此集合不兼容
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified collection contains one
     *         or more null elements and this collection does not permit null
     *         elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null.
     *         如果指定的集合包含一个或多个空元素，这个集合不允许空元素（可选），或者指定的集合是null。
     * @see    #contains(Object)
     */
    boolean containsAll(Collection<?> c);
    
    /**
     * Adds all of the elements in the specified collection to this collection
     * (optional operation).  The behavior of this operation is undefined if
     * the specified collection is modified while the operation is in progress.
     * (This implies that the behavior of this call is undefined if the
     * specified collection is this collection, and this collection is
     * nonempty.)
     * 将指定集合中的所有元素添加到这个集合中（可选操作）。如果在操作过程中修改了指定的集合，那么该操作的行为是未定义的。
     * （这意味着如果指定的集合是这个集合，那么这个调用的行为是未定义的，并且这个集合是非空的。）
     *
     * @param c collection containing elements to be added to this collection
     * 包含要添加到这个集合中的元素的c集合
     * @return <tt>true</tt> if this collection changed as a result of the call
     * 如果这个集合因为调用而改变了
     * @throws UnsupportedOperationException if the <tt>addAll</tt> operation
     *         is not supported by this collection
     * @throws ClassCastException if the class of an element of the specified
     *         collection prevents it from being added to this collection
     * @throws NullPointerException if the specified collection contains a
     *         null element and this collection does not permit null elements,
     *         or if the specified collection is null
     * @throws IllegalArgumentException if some property of an element of the
     *         specified collection prevents it from being added to this
     *         collection
     * @throws IllegalStateException if not all the elements can be added at
     *         this time due to insertion restrictions
     * @see #add(Object)
     */
    boolean addAll(Collection<? extends E> c);
    
    /**
     * Removes all of this collection's elements that are also contained in the
     * specified collection (optional operation).  After this call returns,
     * this collection will contain no elements in common with the specified
     * collection.
     * 删除在指定集合中包含的所有这些集合的元素（可选操作）。
     * 在这个调用返回之后，这个集合将不包含与指定集合相同的元素。
     * @param c collection containing elements to be removed from this collection
     * @return <tt>true</tt> if this collection changed as a result of the
     *         call
     * @throws UnsupportedOperationException if the <tt>removeAll</tt> method
     *         is not supported by this collection
     * @throws ClassCastException if the types of one or more elements
     *         in this collection are incompatible with the specified
     *         collection
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if this collection contains one or more
     *         null elements and the specified collection does not support
     *         null elements
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean removeAll(Collection<?> c);
    
    /**
     * Retains only the elements in this collection that are contained in the
     * specified collection (optional operation).  In other words, removes from
     * this collection all of its elements that are not contained in the
     * specified collection.
     * 只保留该集合中包含在指定集合中的元素（可选操作）。
     * 换句话说，从这个集合中删除所有未包含在指定集合中的元素。
     * @param c collection containing elements to be retained in this collection
     * @return <tt>true</tt> if this collection changed as a result of the call
     * @throws UnsupportedOperationException if the <tt>retainAll</tt> operation
     *         is not supported by this collection
     *         
     * @throws ClassCastException if the types of one or more elements
     *         in this collection are incompatible with the specified
     *         collection
     *         如果保留所有操作不受此集合的支持
     *         (<a href="#optional-restrictions">optional</a>)
     * @throws NullPointerException if this collection contains one or more
     *         null elements and the specified collection does not permit null
     *         elements
     *         如果这个集合包含一个或多个空元素，并且指定的集合不允许空元素（可选），或者指定的集合是null
     *         (<a href="#optional-restrictions">optional</a>),
     *         or if the specified collection is null
     * @see #remove(Object)
     * @see #contains(Object)
     */
    boolean retainAll(Collection<?> c);
    
    /**
     * Removes all of the elements from this collection (optional operation).
     * The collection will be empty after this method returns.
     * 从这个集合中删除所有的元素（可选操作）。在该方法返回之后，集合将是空的。
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this collection
     */
    void clear();
    
    // Comparison and hashing
    // 比较和散列

    /**
     * Compares the specified object with this collection for equality. <p>
     * 将指定的对象与该集合进行比较，以获得相等性。
     * 
     * While the <tt>Collection</tt> interface adds no stipulations to the
     * general contract for the <tt>Object.equals</tt>, programmers who
     * implement the <tt>Collection</tt> interface "directly" (in other words,
     * create a class that is a <tt>Collection</tt> but is not a <tt>Set</tt>
     * or a <tt>List</tt>) must exercise care if they choose to override the
     * <tt>Object.equals</tt>.  It is not necessary to do so, and the simplest
     * course of action is to rely on <tt>Object</tt>'s implementation, but
     * the implementor may wish to implement a "value comparison" in place of
     * the default "reference comparison."  (The <tt>List</tt> and
     * <tt>Set</tt> interfaces mandate such value comparisons.)<p>
     * 尽管集合接口没有为对象的一般契约添加任何规定。
     * 等于，实现集合接口的程序员“直接”（换句话说，创建一个集合，但不是集合或列表），如果他们选择覆盖对象.equals，就必须小心谨慎。
     * 没有必要这样做，最简单的操作方法是依赖对象的实现，但是实现者可能希望实现一个“值比较”，代替默认的“引用比较”。（列表和设置接口要求进行这样的值比较。）
     * 
     * The general contract for the <tt>Object.equals</tt> method states that
     * equals must be symmetric (in other words, <tt>a.equals(b)</tt> if and
     * only if <tt>b.equals(a)</tt>).  The contracts for <tt>List.equals</tt>
     * and <tt>Set.equals</tt> state that lists are only equal to other lists,
     * and sets to other sets.  Thus, a custom <tt>equals</tt> method for a
     * collection class that implements neither the <tt>List</tt> nor
     * <tt>Set</tt> interface must return <tt>false</tt> when this collection
     * is compared to any list or set.  (By the same logic, it is not possible
     * to write a class that correctly implements both the <tt>Set</tt> and
     * <tt>List</tt> interfaces.)
     * 对象的一般契约。相等的方法说明，等号必须是对称的（换句话说，a等于a（b）如果且仅当b等于（a））。合同列表。
     * 等于和set0。等于状态列表仅等于其他列表，并设置为其他集合。
     * 因此,自定义等于一个集合类实现方法列表和设置界面必须返回假当这个集合是任何列表或组相比。(同样的逻辑,它是不可能正确地编写一个类,实现了集和列表界面。)
     * @param o object to be compared for equality with this collection
     * @return <tt>true</tt> if the specified object is equal to this
     * collection
     *
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     * @see List#equals(Object)
     */
    boolean equals(Object o);

    /**
     * Returns the hash code value for this collection.  While the
     * <tt>Collection</tt> interface adds no stipulations to the general
     * contract for the <tt>Object.hashCode</tt> method, programmers should
     * take note that any class that overrides the <tt>Object.equals</tt>
     * method must also override the <tt>Object.hashCode</tt> method in order
     * to satisfy the general contract for the <tt>Object.hashCode</tt> method.
     * In particular, <tt>c1.equals(c2)</tt> implies that
     * <tt>c1.hashCode()==c2.hashCode()</tt>.
     * 返回这个集合的散列码值。尽管集合接口没有为对象的一般契约添加任何规定。
     * hashCode方法，程序员应该注意到任何覆盖该对象的类。equals方法也必须覆盖对象。
     * hashCode方法是为了满足对象的一般契约。hashCode方法。特别地，c1.equals（c2）意味着c1.hashcode（）==c2.hashcode（）。
     * @return the hash code value for this collection
     *
     * @see Object#hashCode()
     * @see Object#equals(Object)
     */
    int hashCode();
}
  
