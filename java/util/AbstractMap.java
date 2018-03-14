/**  
 * Project Name:collection-source  
 * File Name:AbstractMap.java  
 * Package Name:source.java.util  
 * Date:2018年3月14日上午9:02:07  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.util.AbstractSet;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * This class provides a skeletal implementation of the <tt>Map</tt>
 * interface, to minimize the effort required to implement this interface.
 * 这个类提供了Map接口的一个框架实现，以最小化实现这个接口所需的工作量。
 * 
 * <p>To implement an unmodifiable map, the programmer needs only to extend this
 * class and provide an implementation for the <tt>entrySet</tt> method, which
 * returns a set-view of the map's mappings.  Typically, the returned set
 * will, in turn, be implemented atop <tt>AbstractSet</tt>.  This set should
 * not support the <tt>add</tt> or <tt>remove</tt> methods, and its iterator
 * should not support the <tt>remove</tt> method.
 * 
	为了实现不可修改的映射，程序员只需要扩展这个类并为入口yset方法提供一个实现，该方法返回映射映射的一个集合视图。
	通常，返回的集合将依次在AbstractSet上实现。这个集合不应该支持add或remove方法，它的迭代器不应该支持remove方法。

 * <p>To implement a modifiable map, the programmer must additionally override
 * this class's <tt>put</tt> method (which otherwise throws an
 * <tt>UnsupportedOperationException</tt>), and the iterator returned by
 * <tt>entrySet().iterator()</tt> must additionally implement its
 * <tt>remove</tt> method.
 * 实现一个可修改的地图,程序员必须另外覆盖这个类的方法(否则抛出UnsupportedOperationException)方式,
 * 和返回的迭代器entrySet().iterator()必须另外执行其去除方法。
 * 
 * <p>The programmer should generally provide a void (no argument) and map
 * constructor, as per the recommendation in the <tt>Map</tt> interface
 * specification.
 * 程序员通常应该提供一个void（没有参数）和映射构造函数，就像地图接口规范中的建议那样。
 * 
 * <p>The documentation for each non-abstract method in this class describes its
 * implementation in detail.  Each of these methods may be overridden if the
 * map being implemented admits a more efficient implementation.
 * 这个类中的每个非抽象方法的文档详细描述了它的实现。如果正在实现的映射实现了更有效的实现，那么这些方法中的每一个都可能被覆盖。
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 *
 * @author  Josh Bloch
 * @author  Neal Gafter
 * @see Map
 * @see Collection
 * @since 1.2
 */
public abstract class AbstractMap<K, V> implements Map<K,V>{

    /**
     * Sole constructor.  (For invocation by subclass constructors, typically
     * implicit.)
     */
    protected AbstractMap() {
    }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns <tt>entrySet().size()</tt>.
     */
    public int size() {
        return entrySet().size();
    }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns <tt>size() == 0</tt>.
     */
    public boolean isEmpty() {
        return size() == 0;
    }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified value.  If such an entry is found,
     * <tt>true</tt> is returned.  If the iteration terminates without
     * finding such an entry, <tt>false</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map.
     * 如果该映射将一个或多个键映射到指定值，那么返回true。
     * 更正式地说，如果且仅当该映射包含至少一个映射到一个值v的映射（值==null？v = = null:value.equals(v))。
     * 
     * 这个操作可能需要在地图界面的大多数实现中需要时间线性。
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsValue(Object value) {
		Iterator<Entry<K, V>> i = entrySet().iterator();
		if (value == null) {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (e.getValue() == null) {
					return true;
				}
			}
		} else {
			while (i.hasNext()) {
				Entry<K, V> e = i.next();
				if (value.equals(e.getValue())) {
					return true;
				}
			}
		}
		return false;
    }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified key.  If such an entry is found,
     * <tt>true</tt> is returned.  If the iteration terminates without
     * finding such an entry, <tt>false</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     * 如果该映射包含指定键的映射，那么返回true。
     * 更正式地说，如果且仅当此映射包含一个键k的映射时，返回true，这样（键==null？k = = null:key.equals(k))。（最多可以有一个这样的映射。）
     * 
     * @throws ClassCastException   {@inheritDoc}
     * @throws NullPointerException {@inheritDoc}
     */
    public boolean containsKey(Object key) {
        Iterator<Map.Entry<K,V>> i = entrySet().iterator();
        if(key == null) {
        	while(i.hasNext()) {
        		Entry<K,V> e = i.next();
        		if(e.getKey() == null) {
        			return true;
        		}
        	}
        } else {
        	while(i.hasNext()) {
        		Entry<K,V> e = i.next();
        		if(key.equals(e.getKey())) {
        			return true;
        		}
        	}
        }
        return false;
    }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching
     * for an entry with the specified key.  If such an entry is found,
     * the entry's value is returned.  If the iteration terminates without
     * finding such an entry, <tt>null</tt> is returned.  Note that this
     * implementation requires linear time in the size of the map; many
     * implementations will override this method.
     *
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public V get(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (key==null) {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (e.getKey()==null)
                    return e.getValue();
            }
        } else {
            while (i.hasNext()) {
                Entry<K,V> e = i.next();
                if (key.equals(e.getKey()))
                    return e.getValue();
            }
        }
        return null;
    }
    
    // Modification Operations

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation always throws an
     * <tt>UnsupportedOperationException</tt>.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public V put(K key, V value) {
        throw new UnsupportedOperationException();
    }
    
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt> searching for an
     * entry with the specified key.  If such an entry is found, its value is
     * obtained with its <tt>getValue</tt> operation, the entry is removed
     * from the collection (and the backing map) with the iterator's
     * <tt>remove</tt> operation, and the saved value is returned.  If the
     * iteration terminates without finding such an entry, <tt>null</tt> is
     * returned.  Note that this implementation requires linear time in the
     * size of the map; many implementations will override this method.
     * 如果存在，则从该映射中删除该映射的映射（可选操作）。
     * 更正式地说，如果这个映射包含从键k到值v的映射（键==null？k==null:key.equals（k）），该映射被删除。（地图最多可以包含这样的映射。）
     * 返回该映射之前关联键的值，如果映射不包含键的映射，则返回null。
     * 如果这个映射允许null值，那么null的返回值并不一定表示映射没有映射键的映射;也有可能，映射将键映射为空。
     * 一旦调用返回，映射将不包含指定键的映射。
     * 
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * iterator does not support the <tt>remove</tt> method and this map
     * contains a mapping for the specified key.
     * 
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     */
    public V remove(Object key) {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        Entry<K,V> correctEntry = null;
        if (key == null) {
        	Entry<K,V> e = i.next();
        	while (correctEntry == null && i.hasNext()) {
        		if(e.getKey() == null ) {
        			correctEntry = e;
        		}
        	}
        } else {
        	Entry<K,V> e = i.next();
        	while (correctEntry == null && i.hasNext()) {
        		if (key.equals(e.getKey())) {
        			correctEntry = e;
        		}
        	}
        }

        V oldValue = null;
        if(correctEntry != null) {
        	oldValue =correctEntry.getValue();
        	i.remove();
        }
        return oldValue;
    }
    
    // Bulk Operations

    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation iterates over the specified map's
     * <tt>entrySet()</tt> collection, and calls this map's <tt>put</tt>
     * operation once for each entry returned by the iteration.
     *
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if this map does not support
     * the <tt>put</tt> operation and the specified map is nonempty.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     * @throws ClassCastException            {@inheritDoc}
     * @throws NullPointerException          {@inheritDoc}
     * @throws IllegalArgumentException      {@inheritDoc}
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
        	put(e.getKey(),e.getValue());
        }
     }
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation calls <tt>entrySet().clear()</tt>.
     * 从这个映射中删除所有映射（可选操作）。在调用返回之后，映射将是空的。
     * <p>Note that this implementation throws an
     * <tt>UnsupportedOperationException</tt> if the <tt>entrySet</tt>
     * does not support the <tt>clear</tt> operation.
     *
     * @throws UnsupportedOperationException {@inheritDoc}
     */
    public void clear() {
        entrySet().clear();
    }
    
    // Views

    /**
     * Each of these fields are initialized to contain an instance of the
     * appropriate view the first time this view is requested.  The views are
     * stateless, so there's no reason to create more than one of each.
     * 
     * <p>Since there is no synchronization performed while accessing these fields,
     * it is expected that java.util.Map view classes using these fields have
     * no non-final fields (or any fields at all except for outer-this). Adhering
     * to this rule would make the races on these fields benign.
     *
     * <p>It is also imperative that implementations read the field only once,
     * as in:
     *
     * <pre> {@code
     * public Set<K> keySet() {
     *   Set<K> ks = keySet;  // single racy read
     *   if (ks == null) {
     *     ks = new KeySet();
     *     keySet = ks;
     *   }
     *   return ks;
     * }
     *}</pre>
     */
    transient Set<K>        keySet;
    transient Collection<V> values;
    
    /**
     * {@inheritDoc}
     *
     * @implSpec
     * This implementation returns a set that subclasses {@link AbstractSet}.
     * The subclass's iterator method returns a "wrapper object" over this
     * map's <tt>entrySet()</tt> iterator.  The <tt>size</tt> method
     * delegates to this map's <tt>size</tt> method and the
     * <tt>contains</tt> method delegates to this map's
     * <tt>containsKey</tt> method.
     *
     * <p>The set is created the first time this method is called,
     * and returned in response to all subsequent calls.  No synchronization
     * is performed, so there is a slight chance that multiple calls to this
     * method will not all return the same set.
     */
    public Set<K> keySet() {
        Set<K> ks = keySet;
        if (ks == null) {
            ks = new AbstractSet<K>() {
                public Iterator<K> iterator() {
                    return new Iterator<K>() {
                        private Iterator<Entry<K,V>> i = entrySet().iterator();

                        public boolean hasNext() {
                            return i.hasNext();
                        }

                        public K next() {
                            return i.next().getKey();
                        }

                        public void remove() {
                            i.remove();
                        }
                    };
                }

                public int size() {
                    return AbstractMap.this.size();
                }

                public boolean isEmpty() {
                    return AbstractMap.this.isEmpty();
                }

                public void clear() {
                    AbstractMap.this.clear();
                }

                public boolean contains(Object k) {
                    return AbstractMap.this.containsKey(k);
                }
            };
            keySet = ks;
        }
        return ks;
    }
}
  
