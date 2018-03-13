/**  
 * Project Name:collection-source  
 * File Name:Map.java  
 * Package Name:source.java.util  
 * Date:2018年3月13日下午3:43:37  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * An object that maps keys to values.  A map cannot contain duplicate keys;
 * each key can map to at most one value.
 * 一个映射键值的对象。地图不能包含重复的键;每个键最多可以映射到一个值。
 * 
 * <p>This interface takes the place of the <tt>Dictionary</tt> class, which
 * was a totally abstract class rather than an interface.
 * 这个接口代替了Dictionary类，它是一个完全抽象的类而不是一个接口。
 * 
 * <p>The <tt>Map</tt> interface provides three <i>collection views</i>, which
 * allow a map's contents to be viewed as a set of keys, collection of values,
 * or set of key-value mappings.  The <i>order</i> of a map is defined as
 * the order in which the iterators on the map's collection views return their
 * elements.  Some map implementations, like the <tt>TreeMap</tt> class, make
 * specific guarantees as to their order; others, like the <tt>HashMap</tt>
 * class, do not.
 * Map接口提供了三个集合视图，它允许将映射的内容视为一组键、值集合或键值映射集。
 * 映射的顺序被定义为在map的集合视图中迭代器返回其元素的顺序。
 * 一些映射实现，如TreeMap类，对它们的顺序作出特定的保证;其他的，比如HashMap类，则不这样做。
 * 
 * <p>Note: great care must be exercised if mutable objects are used as map
 * keys.  The behavior of a map is not specified if the value of an object is
 * changed in a manner that affects <tt>equals</tt> comparisons while the
 * object is a key in the map.  A special case of this prohibition is that it
 * is not permissible for a map to contain itself as a key.  While it is
 * permissible for a map to contain itself as a value, extreme caution is
 * advised: the <tt>equals</tt> and <tt>hashCode</tt> methods are no longer
 * well defined on such a map.
 * 注意：如果使用可变对象作为map键，则必须执行非常小心。
 * 如果对象的值以一种影响等于比较的方式被改变，而对象是映射中的键，那么映射的行为就不会被指定。
 * 这一禁令的一个特例是，map 不允许将自己作为一个关键字来控制。
 * 虽然允许map将自身作为一个值进行限制是允许的，但是建议非常谨慎：在这样的映射中，等号和hashCode方法不再被定义好。
 * 
 * <p>All general-purpose map implementation classes should provide two
 * "standard" constructors: a void (no arguments) constructor which creates an
 * empty map, and a constructor with a single argument of type <tt>Map</tt>,
 * which creates a new map with the same key-value mappings as its argument.
 * In effect, the latter constructor allows the user to copy any map,
 * producing an equivalent map of the desired class.  There is no way to
 * enforce this recommendation (as interfaces cannot contain constructors) but
 * all of the general-purpose map implementations in the JDK comply.
 * 
 * 所有通用映射实现类都应该提供两个“标准”构造函数：一个空的（无参数）构造函数，它创建一个空映射，
 * 一个构造函数带有一个类型映射的参数，该构造函数创建一个带有相同键值映射的新映射。
 * 实际上，后一个构造函数允许用户复制任何映射，生成所需类的等价映射。
 * 没有办法强制执行这个建议（因为接口不能包含构造函数），但是JDK中的所有通用映射实现都是遵从的。
 * 
 * <p>The "destructive" methods contained in this interface, that is, the
 * methods that modify the map on which they operate, are specified to throw
 * <tt>UnsupportedOperationException</tt> if this map does not support the
 * operation.  If this is the case, these methods may, but are not required
 * to, throw an <tt>UnsupportedOperationException</tt> if the invocation would
 * have no effect on the map.  For example, invoking the {@link #putAll(Map)}
 * method on an unmodifiable map may, but is not required to, throw the
 * exception if the map whose mappings are to be "superimposed" is empty.
 * 此接口中包含的“破坏性”方法,即修改地图的方法操作,指定抛出UnsupportedOperationException如果这张地图不支持方式操作。
 * 如果是这样的话,这些方法可能,但不需要,抛出UnsupportedOperationException如果调用方式在地图上没有影响。
 * 例如，在不可修改的映射上调用putAll（Map）方法，但是不需要，如果映射映射为“重叠”的映射是空的，则抛出异常。
 * 
 * <p>Some map implementations have restrictions on the keys and values they
 * may contain.  For example, some implementations prohibit null keys and
 * values, and some have restrictions on the types of their keys.  Attempting
 * to insert an ineligible key or value throws an unchecked exception,
 * typically <tt>NullPointerException</tt> or <tt>ClassCastException</tt>.
 * Attempting to query the presence of an ineligible key or value may throw an
 * exception, or it may simply return false; some implementations will exhibit
 * the former behavior and some will exhibit the latter.  More generally,
 * attempting an operation on an ineligible key or value whose completion
 * would not result in the insertion of an ineligible element into the map may
 * throw an exception or it may succeed, at the option of the implementation.
 * Such exceptions are marked as "optional" in the specification for this
 * interface.
 * 一个未检查的异常，通常是NullPointerException或ClassCastException。
 * 试图查询不符合的键或值的存在可能会抛出一个异常，或者它可能只是返回false;
 * 一些实现将展示前者的行为，而一些实现将展示后者。
 * 更一般的情况是，尝试在不符合资格的键或值上进行操作，其完成不会导致将不合格的元素插入到映射中，
 * 可能会抛出一个异常，或者它可能会成功，在实现的选项中。此类异常在该接口的规范中被标记为“可选”。
 * 
 * <p>Many methods in Collections Framework interfaces are defined
 * in terms of the {@link Object#equals(Object) equals} method.  For
 * example, the specification for the {@link #containsKey(Object)
 * containsKey(Object key)} method says: "returns <tt>true</tt> if and
 * only if this map contains a mapping for a key <tt>k</tt> such that
 * <tt>(key==null ? k==null : key.equals(k))</tt>." This specification should
 * <i>not</i> be construed to imply that invoking <tt>Map.containsKey</tt>
 * with a non-null argument <tt>key</tt> will cause <tt>key.equals(k)</tt> to
 * be invoked for any key <tt>k</tt>.  Implementations are free to
 * implement optimizations whereby the <tt>equals</tt> invocation is avoided,
 * for example, by first comparing the hash codes of the two keys.  (The
 * {@link Object#hashCode()} specification guarantees that two objects with
 * unequal hash codes cannot be equal.)  More generally, implementations of
 * the various Collections Framework interfaces are free to take advantage of
 * the specified behavior of underlying {@link Object} methods wherever the
 * implementor deems it appropriate.
 * 
	集合框架接口中的许多方法都是用equals方法定义的。
	例如，容器键（对象键）方法的规范说：“如果这个映射包含了一个键k的映射，那么返回true，这样（键==null？”k = = null:key.equals(k))。”
	该规范不应被理解为暗示调用映射。
	带有非null参数键的容器键将会导致键.equals（k）被调用，任何键k实现都可以实现优化，从而避免了相等的调用，例如，首先比较两个键的哈希码。
	（hashcode（）规范保证两个具有不相等的散列码的对象不能相等。）
	更一般地说，各种集合框架接口的实现可以自由地利用底层对象方法的指定行为，无论实现者认为它合适。

 * <p>Some map operations which perform recursive traversal of the map may fail
 * with an exception for self-referential instances where the map directly or
 * indirectly contains itself. This includes the {@code clone()},
 * {@code equals()}, {@code hashCode()} and {@code toString()} methods.
 * Implementations may optionally handle the self-referential scenario, however
 * most current implementations do not do so.
 * 一些映射操作的映射操作可能会失败，因为映射直接或间接地包含映射的自引用实例可能会失败。
 * 这包括clone（）、equals（）、hashCode（）和toString（）方法。实现可以有选择地处理自引用场景，但是大多数当前实现都不这么做。
 * 
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see HashMap
 * @see TreeMap
 * @see Hashtable
 * @see SortedMap
 * @see Collection
 * @see Set
 * @since 1.2
 */

public interface Map<K,V> {
	
    // Query Operations
	// 查询操作

    /**
     * Returns the number of key-value mappings in this map.  If the
     * map contains more than <tt>Integer.MAX_VALUE</tt> elements, returns
     * <tt>Integer.MAX_VALUE</tt>.
     *
     * @return the number of key-value mappings in this map
     */
    int size();
    
    /**
     * Returns <tt>true</tt> if this map contains no key-value mappings.
     *
     * @return <tt>true</tt> if this map contains no key-value mappings
     */
    boolean isEmpty();
    
    /**
     * Returns <tt>true</tt> if this map contains a mapping for the specified
     * key.  More formally, returns <tt>true</tt> if and only if
     * this map contains a mapping for a key <tt>k</tt> such that
     * <tt>(key==null ? k==null : key.equals(k))</tt>.  (There can be
     * at most one such mapping.)
     * 如果该映射包含指定键的映射，那么返回true。
     * 更正式地说，如果且仅当此映射包含一个键k的映射时，返回true，
     * 这样（键==null？k = = null:key.equals(k))。（最多可以有一个这样的映射。）
     * 
     * @param key key whose presence in this map is to be tested
     * @return <tt>true</tt> if this map contains a mapping for the specified
     *         key
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    boolean containsKey(Object key);
    
    
    /**
     * Returns <tt>true</tt> if this map maps one or more keys to the
     * specified value.  More formally, returns <tt>true</tt> if and only if
     * this map contains at least one mapping to a value <tt>v</tt> such that
     * <tt>(value==null ? v==null : value.equals(v))</tt>.  This operation
     * will probably require time linear in the map size for most
     * implementations of the <tt>Map</tt> interface.
     * 如果该映射将一个或多个键映射到指定值，那么返回true。
     * 更正式地说，如果且仅当该映射包含至少一个映射到一个值v的映射（值==null？v = = null:value.equals(v))。
     * 这个操作可能需要在地图界面的大多数实现中需要时间线性。
     * 
     * @param value value whose presence in this map is to be tested
     * @return <tt>true</tt> if this map maps one or more keys to the
     *         specified value
     * @throws ClassCastException if the value is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified value is null and this
     *         map does not permit null values
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    boolean containsValue(Object value);

    /**
     * Returns the value to which the specified key is mapped,
     * or {@code null} if this map contains no mapping for the key.
     * 返回指定键映射的值，如果该映射不包含键的映射，则返回null。
     * 
     * <p>More formally, if this map contains a mapping from a key
     * {@code k} to a value {@code v} such that {@code (key==null ? k==null :
     * key.equals(k))}, then this method returns {@code v}; otherwise
     * it returns {@code null}.  (There can be at most one such mapping.)
     * 更正式地说，如果这个映射包含一个从键k到值v的映射
     * （键==null？k==null:key.equals（k）），然后这个方法返回v;否则返回null。（最多可以有一个这样的映射。）
     * 
     * 
     * <p>If this map permits null values, then a return value of
     * {@code null} does not <i>necessarily</i> indicate that the map
     * contains no mapping for the key; it's also possible that the map
     * explicitly maps the key to {@code null}.  The {@link #containsKey
     * containsKey} operation may be used to distinguish these two cases.
     * 如果这个映射允许null值，那么null的返回值不一定表示映射不包含键的映射;
     * 也有可能，映射将键映射为空。containsKey 操作可用于区分这两种情况。
     * 
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or
     *         {@code null} if this map contains no mapping for the key
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this map
     *         does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    V get(Object key);
    
    // Modification Operations
    
    /**
     * Associates the specified value with the specified key in this map
     * (optional operation).  If the map previously contained a mapping for
     * the key, the old value is replaced by the specified value.  (A map
     * <tt>m</tt> is said to contain a mapping for a key <tt>k</tt> if and only
     * if {@link #containsKey(Object) m.containsKey(k)} would return
     * <tt>true</tt>.)
     * 将指定的值与该映射中的指定键关联起来（可选操作）。
     * 如果映射之前包含了键的映射，那么旧的值将被指定的值所取代。（据说映射m包含一个键k的映射，如果m.容器键（k）返回true。）
     * 
     * @param key key with which the specified value is to be associated
     * 与指定值相关联的键键
     * @param value value to be associated with the specified key
     * 与指定键关联的值值
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         (A <tt>null</tt> return can also indicate that the map
     *         previously associated <tt>null</tt> with <tt>key</tt>,
     *         if the implementation supports <tt>null</tt> values.)
     *         与键关联的前一个值，如果没有键映射，则为null。
     *         （null返回还可以表明，如果实现支持null值，则映射之前与键关联的映射。）
     *         
     * @throws UnsupportedOperationException if the <tt>put</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the class of the specified key or value
     *         prevents it from being stored in this map
     * @throws NullPointerException if the specified key or value is null
     *         and this map does not permit null keys or values
     * @throws IllegalArgumentException if some property of the specified key
     *         or value prevents it from being stored in this map
     */
    V put(K key, V value);
    
    /**
     * Removes the mapping for a key from this map if it is present
     * (optional operation).   More formally, if this map contains a mapping
     * from key <tt>k</tt> to value <tt>v</tt> such that
     * <code>(key==null ?  k==null : key.equals(k))</code>, that mapping
     * is removed.  (The map can contain at most one such mapping.)
     * 如果存在，则从该映射中删除该映射的映射（可选操作）。
     * 更正式地说，如果这个映射包含从键k到值v的映射（键==null？k==null:key.equals（k）），该映射被删除。（地图最多可以包含这样的映射。）
     * 
     * <p>Returns the value to which this map previously associated the key,
     * or <tt>null</tt> if the map contained no mapping for the key.
     * 返回该映射之前关联键的值，如果映射不包含键的映射，则返回null。
     * 
     * <p>If this map permits null values, then a return value of
     * <tt>null</tt> does not <i>necessarily</i> indicate that the map
     * contained no mapping for the key; it's also possible that the map
     * explicitly mapped the key to <tt>null</tt>.
     * 如果这个映射允许null值，那么null的返回值并不一定表示映射没有映射键的映射;也有可能，映射将键映射为空。
     * <p>The map will not contain a mapping for the specified key once the
     * call returns.
     * 一旦调用返回，映射将不包含指定键的映射。
     * 
     * @param key key whose mapping is to be removed from the map
     * @return the previous value associated with <tt>key</tt>, or
     *         <tt>null</tt> if there was no mapping for <tt>key</tt>.
     *         与键关联的前一个值，如果没有键映射，则为null。
     * @throws UnsupportedOperationException if the <tt>remove</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the key is of an inappropriate type for
     *         this map
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     * @throws NullPointerException if the specified key is null and this
     *         map does not permit null keys
     * (<a href="{@docRoot}/java/util/Collection.html#optional-restrictions">optional</a>)
     */
    V remove(Object key);
    
    // Bulk Operations

    /**
     * Copies all of the mappings from the specified map to this map
     * (optional operation).  The effect of this call is equivalent to that
     * of calling {@link #put(Object,Object) put(k, v)} on this map once
     * for each mapping from key <tt>k</tt> to value <tt>v</tt> in the
     * specified map.  The behavior of this operation is undefined if the
     * specified map is modified while the operation is in progress.
     * 将所有映射从指定映射复制到这个映射（可选操作）。
     * 这个调用的效果相当于在这个映射上调用put（k，v）在指定的映射中从键k到值v的映射。
     * 如果在操作过程中修改了指定的映射，那么该操作的行为是未定义的。
     * 
     * @param m mappings to be stored in this map
     * @throws UnsupportedOperationException if the <tt>putAll</tt> operation
     *         is not supported by this map
     * @throws ClassCastException if the class of a key or value in the
     *         specified map prevents it from being stored in this map
     * @throws NullPointerException if the specified map is null, or if
     *         this map does not permit null keys or values, and the
     *         specified map contains null keys or values
     * @throws IllegalArgumentException if some property of a key or value in
     *         the specified map prevents it from being stored in this map
     */
    void putAll(Map<? extends K, ? extends V> m);
    
    /**
     * Removes all of the mappings from this map (optional operation).
     * The map will be empty after this call returns.
     *
     * @throws UnsupportedOperationException if the <tt>clear</tt> operation
     *         is not supported by this map
     */
    void clear();
    
    // Views
    // 视图

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * <tt>Iterator.remove</tt>, <tt>Set.remove</tt>,
     * <tt>removeAll</tt>, <tt>retainAll</tt>, and <tt>clear</tt>
     * operations.  It does not support the <tt>add</tt> or <tt>addAll</tt>
     * operations.
     * 返回该映射中包含的键的集合视图。
     * 该集合是由映射支持的，因此映射的变化反映在集合中，反之亦然。
     * 如果映射被修改，而在集合上的迭代正在进行中（除了迭代器自己的删除操作），迭代的结果是未定义的。
     * 该集合支持元素删除，通过迭代器从映射中删除对应的映射。Iterator.remove，set.remove，removeAll，retainAll和clear操作。它不支持add或addAll操作。
     * @return a set view of the keys contained in this map
     */
    Set<K> keySet();
    
    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own <tt>remove</tt> operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Collection.remove</tt>, <tt>removeAll</tt>,
     * <tt>retainAll</tt> and <tt>clear</tt> operations.  It does not
     * support the <tt>add</tt> or <tt>addAll</tt> operations.
     * 返回该映射中包含的值的集合视图。
     * 收集地图支持的,所以更改地图反映在收集,反之亦然。
     * 如果映射被修改，而在集合上的迭代正在进行中（除了迭代器自己的删除操作），迭代的结果是未定义的。
     * 收集支持元素移除,这从地图中删除相应的映射,通过迭代器。删除收藏。remove，removeAll,retainAll和clear操作。它不支持add或addAll操作。
     * @return a collection view of the values contained in this map
     */
    Collection<V> values();
    
    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own <tt>remove</tt> operation, or through the
     * <tt>setValue</tt> operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the <tt>Iterator.remove</tt>,
     * <tt>Set.remove</tt>, <tt>removeAll</tt>, <tt>retainAll</tt> and
     * <tt>clear</tt> operations.  It does not support the
     * <tt>add</tt> or <tt>addAll</tt> operations.
     *
     * @return a set view of the mappings contained in this map
     */
    Set<Map.Entry<K, V>> entrySet();
    
    /**
     * A map entry (key-value pair).  The <tt>Map.entrySet</tt> method returns
     * a collection-view of the map, whose elements are of this class.  The
     * <i>only</i> way to obtain a reference to a map entry is from the
     * iterator of this collection-view.  These <tt>Map.Entry</tt> objects are
     * valid <i>only</i> for the duration of the iteration; more formally,
     * the behavior of a map entry is undefined if the backing map has been
     * modified after the entry was returned by the iterator, except through
     * the <tt>setValue</tt> operation on the map entry.
     * 映射条目（键-值对）。map 。entrySet方法返回一个映射视图，该视图的元素是这个类的。
     * 获取对映射条目的引用的惟一方法是来自这个集合视图的迭代器。
     * 这些Map.Entry对象仅在迭代期间有效;
     * 更正式地说，如果在迭代器返回该条目之后修改了映射条目的行为，那么 map entry的行为是没有定义的，除非通过在映射条目上的setValue操作。
     * @see Map#entrySet()
     * @since 1.2
     */
    interface Entry<K,V> {
        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        K getKey();
        
        /**
         * Returns the value corresponding to this entry.  If the mapping
         * has been removed from the backing map (by the iterator's
         * <tt>remove</tt> operation), the results of this call are undefined.
         *
         * @return the value corresponding to this entry
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        V getValue();
        
        
        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  (Writes through to the map.)  The
         * behavior of this call is undefined if the mapping has already been
         * removed from the map (by the iterator's <tt>remove</tt> operation).
         *
         * @param value new value to be stored in this entry
         * @return old value corresponding to the entry
         * @throws UnsupportedOperationException if the <tt>put</tt> operation
         *         is not supported by the backing map
         * @throws ClassCastException if the class of the specified value
         *         prevents it from being stored in the backing map
         * @throws NullPointerException if the backing map does not permit
         *         null values, and the specified value is null
         * @throws IllegalArgumentException if some property of this value
         *         prevents it from being stored in the backing map
         * @throws IllegalStateException implementations may, but are not
         *         required to, throw this exception if the entry has been
         *         removed from the backing map.
         */
        V setValue(V value);
        
        /**
         * Compares the specified object with this entry for equality.
         * Returns <tt>true</tt> if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries <tt>e1</tt> and <tt>e2</tt> represent the same mapping
         * if<pre>
         *     (e1.getKey()==null ?
         *      e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &amp;&amp;
         *     (e1.getValue()==null ?
         *      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
         * </pre>
         * This ensures that the <tt>equals</tt> method works properly across
         * different implementations of the <tt>Map.Entry</tt> interface.
         * 将指定的对象与该条目进行比较。如果给定的对象也是一个映射条目，并且两个条目表示相同的映射，则返回true。
         * 更正式地说，两个条目e1和e2表示相同的映射 如果
         * 
		 *    (e1.getKey()==null ?
		      e2.getKey()==null : e1.getKey().equals(e2.getKey()))  &&
		     (e1.getValue()==null ?
		      e2.getValue()==null : e1.getValue().equals(e2.getValue()))
 		
 		 	这确保了equals方法能够在不同的映射实现中正常工作
         * @param o object to be compared for equality with this map entry
         * @return <tt>true</tt> if the specified object is equal to this map
         *         entry
         */
        boolean equals(Object o);
        
        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry <tt>e</tt> is defined to be: <pre>
         *     (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *     (e.getValue()==null ? 0 : e.getValue().hashCode())
         * </pre>
         * This ensures that <tt>e1.equals(e2)</tt> implies that
         * <tt>e1.hashCode()==e2.hashCode()</tt> for any two Entries
         * <tt>e1</tt> and <tt>e2</tt>, as required by the general
         * contract of <tt>Object.hashCode</tt>.
         * 返回这个映射条目的散列代码值。映射条目e的散列码被定义为：
         *  ^  亦或操作
         *    (e.getKey()==null ? 0 : e.getKey().hashCode()) ^ (e.getValue()==null ? 0 : e.getValue().hashCode())
         * 
         * 这确保e1.equals（e2）意味着对于任何两个条目e1和e2，e1.hashcode（）=e2.hashcode（），这是object.hashcode的一般契约所要求的。
         * @return the hash code value for this map entry
         * @see Object#hashCode()
         * @see Object#equals(Object)
         * @see #equals(Object)
         */
        int hashCode();
    }
    

}
  
