/**  
 * Project Name:collection-source  
 * File Name:SortedMap.java  
 * Package Name:source.java.util  
 * Date:2018年3月14日下午3:28:05  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeMap;

/**
 * A {@link Map} that further provides a <em>total ordering</em> on its keys.
 * The map is ordered according to the {@linkplain Comparable natural
 * ordering} of its keys, or by a {@link Comparator} typically
 * provided at sorted map creation time.  This order is reflected when
 * iterating over the sorted map's collection views (returned by the
 * {@code entrySet}, {@code keySet} and {@code values} methods).
 * Several additional operations are provided to take advantage of the
 * ordering.  (This interface is the map analogue of {@link SortedSet}.)
 * 一个map接口，它进一步提供了它的键的总排序。
 * 该映射是根据其键的自然排序，或者是在排序的映射创建时提供的比较器Comparator来排序的。
 * 当遍历排序映射的集合视图时（由entryset、keySet和values方法返回），这个顺序就会反映出来。
 * 还提供了一些额外的操作以利用排序。（这个接口是SortedSet的地图模拟。）
 * 
 * <p>All keys inserted into a sorted map must implement the {@code Comparable}
 * interface (or be accepted by the specified comparator).  Furthermore, all
 * such keys must be <em>mutually comparable</em>: {@code k1.compareTo(k2)} (or
 * {@code comparator.compare(k1, k2)}) must not throw a
 * {@code ClassCastException} for any keys {@code k1} and {@code k2} in
 * the sorted map.  Attempts to violate this restriction will cause the
 * offending method or constructor invocation to throw a
 * {@code ClassCastException}.
 * 插入到排序映射的所有键必须实现可比较的接口（或者被指定的比较器接受）。
 * 此外，所有这些键必须是相互可比的:k1.compareto（k2）或者 comparator.compare(k1, k2)）
 * 不能在排序映射中为任何键k1和k2抛出ClassCastException。
 * 违反此限制的尝试将导致违规方法或构造函数调用抛出ClassCastException。
 * 
 * <p>Note that the ordering maintained by a sorted map (whether or not an
 * explicit comparator is provided) must be <em>consistent with equals</em> if
 * the sorted map is to correctly implement the {@code Map} interface.  (See
 * the {@code Comparable} interface or {@code Comparator} interface for a
 * precise definition of <em>consistent with equals</em>.)  This is so because
 * the {@code Map} interface is defined in terms of the {@code equals}
 * operation, but a sorted map performs all key comparisons using its
 * {@code compareTo} (or {@code compare}) method, so two keys that are
 * deemed equal by this method are, from the standpoint of the sorted map,
 * equal.  The behavior of a tree map <em>is</em> well-defined even if its
 * ordering is inconsistent with equals; it just fails to obey the general
 * contract of the {@code Map} interface.
 * 注意，排序映射维护的顺序（是否提供了显式的比较器）必须保持一致，如果排序的映射是正确地实现映射接口。
 * （请参阅相应的接口或Comparator接口，以获得与equals一致的精确定义。）
 * 这是因为映射接口是按照equals操作定义的，但是一个排序的映射使用它的compareTo（或比较）方法执行所有的键比较，
 * 因此，从排序映射的角度来看，这两个被认为是相等的键是相等的。树映射的行为是定义良好的，即使它的顺序与equals不一致;它只是不遵守映射接口的一般契约。
 * 
 * <p>All general-purpose sorted map implementation classes should provide four
 * "standard" constructors. It is not possible to enforce this recommendation
 * though as required constructors cannot be specified by interfaces. The
 * expected "standard" constructors for all sorted map implementations are:
 * <ol>
 *   <li>A void (no arguments) constructor, which creates an empty sorted map
 *   sorted according to the natural ordering of its keys.</li>
 *   <li>A constructor with a single argument of type {@code Comparator}, which
 *   creates an empty sorted map sorted according to the specified comparator.</li>
 *   <li>A constructor with a single argument of type {@code Map}, which creates
 *   a new map with the same key-value mappings as its argument, sorted
 *   according to the keys' natural ordering.</li>
 *   <li>A constructor with a single argument of type {@code SortedMap}, which
 *   creates a new sorted map with the same key-value mappings and the same
 *   ordering as the input sorted map.</li>
 * </ol>
 *
 *
 *	所有通用排序的映射实现类都应该提供四个“标准”构造函数。不可能强制执行此建议，因为需要的构造函数不能由接口指定。所有排序映射实现的预期“标准”构造函数都是：
 * 1 一个void（没有参数）构造函数，它根据键的自然排序来创建一个空排序的映射。
 * 2 一个带有类型比较器参数的构造函数，它根据指定的比较器创建一个空排序的映射。
 * 3 一个带有类型映射的单一参数的构造函数，它会根据键的自然排序，根据键值映射创建一个新的映射，并根据键值映射来创建一个新的映射。
 * 4 一个带有类型SortedMap的单一参数的构造函数，它创建一个新的排序映射，它具有相同的键值映射和与输入排序映射相同的排序。
 *
 * <p><strong>Note</strong>: several methods return submaps with restricted key
 * ranges. Such ranges are <em>half-open</em>, that is, they include their low
 * endpoint but not their high endpoint (where applicable).  If you need a
 * <em>closed range</em> (which includes both endpoints), and the key type
 * allows for calculation of the successor of a given key, merely request
 * the subrange from {@code lowEndpoint} to
 * {@code successor(highEndpoint)}.  For example, suppose that {@code m}
 * is a map whose keys are strings.  The following idiom obtains a view
 * containing all of the key-value mappings in {@code m} whose keys are
 * between {@code low} and {@code high}, inclusive:<pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low, high+"\0");</pre>
 *
 * A similar technique can be used to generate an <em>open range</em>
 * (which contains neither endpoint).  The following idiom obtains a
 * view containing all of the key-value mappings in {@code m} whose keys
 * are between {@code low} and {@code high}, exclusive:<pre>
 *   SortedMap&lt;String, V&gt; sub = m.subMap(low+"\0", high);</pre>
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 * 注意：一些方法返回带有限制键范围的子映射。
 * 这样的范围是半开的，也就是说，它们包括低端点，而不是它们的高端端点（适用的地方）。
 * 如果您需要一个封闭的范围（包括两个端点），并且键类型允许计算给定键的后续，仅仅请求从low端点到后继（high端点）的子区间。
 * 例如，假设m是一个map，它的键是字符串。下面的习语获得了一个视图，其中包含了m的键值映射，这些键值在低和高之间，包括：
 *    SortedMap<String, V> sub = m.subMap(low, high+"\0");
 *    
 *    可以使用类似的技术生成一个开放范围（其中不包含端点）。下面的习语获得了一个视图，其中包含了m的键值映射，其键值在低和高之间，独占：
 *    SortedMap<String, V> sub = m.subMap(low+"\0", high);
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @see Map
 * @see TreeMap
 * @see SortedSet
 * @see Comparator
 * @see Comparable
 * @see Collection
 * @see ClassCastException
 * @since 1.2
 */
public interface SortedMap<K, V> extends Map<K,V>{
	
    /**
     * Returns the comparator used to order the keys in this map, or
     * {@code null} if this map uses the {@linkplain Comparable
     * natural ordering} of its keys.
     * 返回用于在该映射中排序键的比较器，如果该映射使用其键的自然排序，则返回null。
     * 
     * @return the comparator used to order the keys in this map,
     *         or {@code null} if this map uses the natural ordering
     *         of its keys
     *         比较器用于在这个映射中排序键，如果该映射使用其键的自然排序，则使用null。
     */       
    Comparator<? super K> comparator();
    
    /**
     * Returns a view of the portion of this map whose keys range from
     * {@code fromKey}, inclusive, to {@code toKey}, exclusive.  (If
     * {@code fromKey} and {@code toKey} are equal, the returned map
     * is empty.)  The returned map is backed by this map, so changes
     * in the returned map are reflected in this map, and vice-versa.
     * The returned map supports all optional map operations that this
     * map supports.
     * 返回该地图的部分的视图，它的键范围从fromKey，包含到toKey，（不包括）。
     * （如果fromKey和toKey是相等的，返回的映射是空的。）
     * returned map是由this map支持的，所以returned map的变化反映在this map中，反之亦然。
     * 返回的映射支持该映射支持的所有可选映射操作。
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     * 
     *
     * @param fromKey low endpoint (inclusive) of the keys in the returned map
     * @param toKey high endpoint (exclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys range from
     *         {@code fromKey}, inclusive, to {@code toKey}, exclusive
     * @throws ClassCastException if {@code fromKey} and {@code toKey}
     *         cannot be compared to one another using this map's comparator
     *         (or, if the map has no comparator, using natural ordering).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} or {@code toKey}
     *         cannot be compared to keys currently in the map.
     * @throws NullPointerException if {@code fromKey} or {@code toKey}
     *         is null and this map does not permit null keys
     * @throws IllegalArgumentException if {@code fromKey} is greater than
     *         {@code toKey}; or if this map itself has a restricted
     *         range, and {@code fromKey} or {@code toKey} lies
     *         outside the bounds of the range
     */
    SortedMap<K,V> subMap(K fromKey, K toKey);
    
    /**
     * Returns a view of the portion of this map whose keys are
     * strictly less than {@code toKey}.  The returned map is backed
     * by this map, so changes in the returned map are reflected in
     * this map, and vice-versa.  The returned map supports all
     * optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param toKey high endpoint (exclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys are strictly
     *         less than {@code toKey}
     * @throws ClassCastException if {@code toKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code toKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code toKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code toKey} is null and
     *         this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code toKey} lies outside the
     *         bounds of the range
     */
    SortedMap<K,V> headMap(K toKey);

    /**
     * Returns a view of the portion of this map whose keys are
     * greater than or equal to {@code fromKey}.  The returned map is
     * backed by this map, so changes in the returned map are
     * reflected in this map, and vice-versa.  The returned map
     * supports all optional map operations that this map supports.
     *
     * <p>The returned map will throw an {@code IllegalArgumentException}
     * on an attempt to insert a key outside its range.
     *
     * @param fromKey low endpoint (inclusive) of the keys in the returned map
     * @return a view of the portion of this map whose keys are greater
     *         than or equal to {@code fromKey}
     * @throws ClassCastException if {@code fromKey} is not compatible
     *         with this map's comparator (or, if the map has no comparator,
     *         if {@code fromKey} does not implement {@link Comparable}).
     *         Implementations may, but are not required to, throw this
     *         exception if {@code fromKey} cannot be compared to keys
     *         currently in the map.
     * @throws NullPointerException if {@code fromKey} is null and
     *         this map does not permit null keys
     * @throws IllegalArgumentException if this map itself has a
     *         restricted range, and {@code fromKey} lies outside the
     *         bounds of the range
     */
    SortedMap<K,V> tailMap(K fromKey);

    /**
     * Returns the first (lowest) key currently in this map.
     *
     * @return the first (lowest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    K firstKey();

    /**
     * Returns the last (highest) key currently in this map.
     *
     * @return the last (highest) key currently in this map
     * @throws NoSuchElementException if this map is empty
     */
    K lastKey();

    /**
     * Returns a {@link Set} view of the keys contained in this map.
     * The set's iterator returns the keys in ascending order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation), the results of
     * the iteration are undefined.  The set supports element removal,
     * which removes the corresponding mapping from the map, via the
     * {@code Iterator.remove}, {@code Set.remove},
     * {@code removeAll}, {@code retainAll}, and {@code clear}
     * operations.  It does not support the {@code add} or {@code addAll}
     * operations.
     *
     * @return a set view of the keys contained in this map, sorted in
     *         ascending order
     */
    Set<K> keySet();

    /**
     * Returns a {@link Collection} view of the values contained in this map.
     * The collection's iterator returns the values in ascending order
     * of the corresponding keys.
     * The collection is backed by the map, so changes to the map are
     * reflected in the collection, and vice-versa.  If the map is
     * modified while an iteration over the collection is in progress
     * (except through the iterator's own {@code remove} operation),
     * the results of the iteration are undefined.  The collection
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Collection.remove}, {@code removeAll},
     * {@code retainAll} and {@code clear} operations.  It does not
     * support the {@code add} or {@code addAll} operations.
     *
     * @return a collection view of the values contained in this map,
     *         sorted in ascending key order
     */
    Collection<V> values();

    /**
     * Returns a {@link Set} view of the mappings contained in this map.
     * The set's iterator returns the entries in ascending key order.
     * The set is backed by the map, so changes to the map are
     * reflected in the set, and vice-versa.  If the map is modified
     * while an iteration over the set is in progress (except through
     * the iterator's own {@code remove} operation, or through the
     * {@code setValue} operation on a map entry returned by the
     * iterator) the results of the iteration are undefined.  The set
     * supports element removal, which removes the corresponding
     * mapping from the map, via the {@code Iterator.remove},
     * {@code Set.remove}, {@code removeAll}, {@code retainAll} and
     * {@code clear} operations.  It does not support the
     * {@code add} or {@code addAll} operations.
     *
     * @return a set view of the mappings contained in this map,
     *         sorted in ascending key order
     */
    Set<Map.Entry<K, V>> entrySet();
    
    
}
  
