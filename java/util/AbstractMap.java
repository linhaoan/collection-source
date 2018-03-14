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

				@Override
				public Iterator<K> iterator() {
					  
					 return new Iterator<K>() {

						private Iterator<Entry<K,V>> i = entrySet().iterator();
						 
						@Override
						public boolean hasNext() {
							return i.hasNext();
						}

						@Override
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
        	keySet = ks ;
        }
        return ks;
    }
    
    public Collection<V> values() {
    	 Collection<V> vals = values;
    	 if(vals == null) {
    		 vals = new AbstractCollection<V>() {

				@Override
				public Iterator<V> iterator() {
					  
					  
					return  new Iterator<V>(){
						private  Iterator<Entry<K,V>> i = entrySet().iterator();
						
						@Override
						public boolean hasNext() {
							   
							return i.hasNext();
						}

						@Override
						public V next() {
							return i.next().getValue();
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

                public boolean contains(Object v) {
                    return AbstractMap.this.containsValue(v);
                }
    			 
    		 };
    		 values = vals;
    	 }
    	 return vals;
    }
    
    
    public abstract Set<Entry<K,V>> entrySet();
    

    // Comparison and hashing

    /**
     * Compares the specified object with this map for equality.  Returns
     * <tt>true</tt> if the given object is also a map and the two maps
     * represent the same mappings.  More formally, two maps <tt>m1</tt> and
     * <tt>m2</tt> represent the same mappings if
     * <tt>m1.entrySet().equals(m2.entrySet())</tt>.  This ensures that the
     * <tt>equals</tt> method works properly across different implementations
     * of the <tt>Map</tt> interface.
     *
     * @implSpec
     * This implementation first checks if the specified object is this map;
     * if so it returns <tt>true</tt>.  Then, it checks if the specified
     * object is a map whose size is identical to the size of this map; if
     * not, it returns <tt>false</tt>.  If so, it iterates over this map's
     * <tt>entrySet</tt> collection, and checks that the specified map
     * contains each mapping that this map contains.  If the specified map
     * fails to contain such a mapping, <tt>false</tt> is returned.  If the
     * iteration completes, <tt>true</tt> is returned.
     *
     * @param o object to be compared for equality with this map
     * @return <tt>true</tt> if the specified object is equal to this map
     */
    public boolean equals(Object o) {
        
    	if (o == this) {
    		return true;
    	} 
    	if (!(o instanceof Map)) {
    		return false;
    	}
    	Map<?,?> m = (Map<?,?>)o;
    	if(m.size() != size()) {
    		return false;
    	}
    	try{
    		Iterator<Entry<K,V>> it = entrySet().iterator();
    		while(it.hasNext()) {
    			Entry<K,V> e = it.next();
                K key = e.getKey();
                V value = e.getValue();
    			if(value == null) {
    				if( !( m.get(key)==null && m.containsKey(key))) {
    					return false;
    				}
    				
    			} else {
    				if( !value.equals(m.get(key))) {
    					return false;
    				}
    			}
    		}
    	} catch (ClassCastException unused) {
            return false;
        } catch (NullPointerException unused) {
            return false;
        }
        return true;
    }
    
    /**
     * Returns the hash code value for this map.  The hash code of a map is
     * defined to be the sum of the hash codes of each entry in the map's
     * <tt>entrySet()</tt> view.  This ensures that <tt>m1.equals(m2)</tt>
     * implies that <tt>m1.hashCode()==m2.hashCode()</tt> for any two maps
     * <tt>m1</tt> and <tt>m2</tt>, as required by the general contract of
     * {@link Object#hashCode}.
     * 返回该映射的散列代码值。映射的散列码被定义为在map的entrySet（）视图中每个条目的散列码的和。
     * 这确保m1.equals（m2）意味着m1.hashcode（）==m2.hashcode（），这是任何两个映射m1和m2的映射。
     * 
     * @implSpec
     * This implementation iterates over <tt>entrySet()</tt>, calling
     * {@link Map.Entry#hashCode hashCode()} on each element (entry) in the
     * set, and adding up the results.
     *
     * @return the hash code value for this map
     * @see Map.Entry#hashCode()
     * @see Object#equals(Object)
     * @see Set#equals(Object)
     */
    public int hashCode() {
        int h = 0;
        Iterator<Entry<K,V>> i = entrySet().iterator();
        while (i.hasNext())
            h += i.next().hashCode();
        return h;
    }
    
    /**
     * Returns a string representation of this map.  The string representation
     * consists of a list of key-value mappings in the order returned by the
     * map's <tt>entrySet</tt> view's iterator, enclosed in braces
     * (<tt>"{}"</tt>).  Adjacent mappings are separated by the characters
     * <tt>", "</tt> (comma and space).  Each key-value mapping is rendered as
     * the key followed by an equals sign (<tt>"="</tt>) followed by the
     * associated value.  Keys and values are converted to strings as by
     * {@link String#valueOf(Object)}.
     * 返回该映射的字符串表示。
     * 字符串表示由map的entryset视图的迭代器返回的键值映射列表组成，该迭代器是用括号括起来的（“”）。
     * 相邻的映射由字符“，”（逗号和空格）分隔。
     * 每个键值映射都被呈现为键，后面是一个等号（“=”），后面是相关的值。键和值被转换为字符串，如字符串.valueof（Object）。
     * 
     * 
     * @return a string representation of this map
     */
    public String toString() {
        Iterator<Entry<K,V>> i = entrySet().iterator();
        if (! i.hasNext())
            return "{}";

        StringBuilder sb = new StringBuilder();
        sb.append('{');
        for (;;) {
            Entry<K,V> e = i.next();
            K key = e.getKey();
            V value = e.getValue();
            sb.append(key   == this ? "(this Map)" : key);
            sb.append('=');
            sb.append(value == this ? "(this Map)" : value);
            if (! i.hasNext())
                return sb.append('}').toString();
            sb.append(',').append(' ');
        }
    }
    
    /**
     * Returns a shallow copy of this <tt>AbstractMap</tt> instance: the keys
     * and values themselves are not cloned.
     * 返回这个AbstractMap实例的一个浅副本：键和值本身没有被克隆。
     * @return a shallow copy of this map
     */
    protected Object clone() throws CloneNotSupportedException {
        AbstractMap<?,?> result = (AbstractMap<?,?>)super.clone();
        result.keySet = null;
        result.values = null;
        return result;
    }
    
    /**
     * Utility method for SimpleEntry and SimpleImmutableEntry.
     * Test for equality, checking for nulls.
     * SimpleEntry和SimpleImmutableEntry的实用方法。
     * 对等式进行测试，检查是否为null。注意：不要用对象代替。直到jdk-8015417被解析。
     * 
     * NB: Do not replace with Object.equals until JDK-8015417 is resolved.
     */
    private static boolean eq(Object o1, Object o2) {
        return o1 == null ? o2 == null : o1.equals(o2);
    }

    
    // Implementation Note: SimpleEntry and SimpleImmutableEntry
    // are distinct unrelated classes, even though they share
    // some code. Since you can't add or subtract final-ness
    // of a field in a subclass, they can't share representations,
    // and the amount of duplicated code is too small to warrant
    // exposing a common abstract class.
    
    /**
	 	实现说明:SimpleEntry和SimpleImmutableEntry
		是截然不同的类，尽管它们共享
		一些代码。因为你不能添加或减去最终的结果
		在子类的一个字段中，它们不能共享表示，
		重复代码的数量太少，无法保证
		公开一个普通的抽象类。
     * 
     * 
     * 
     */
    /**
     * An Entry maintaining a key and a value.  The value may be
     * changed using the <tt>setValue</tt> method.  This class
     * facilitates the process of building custom map
     * implementations. For example, it may be convenient to return
     * arrays of <tt>SimpleEntry</tt> instances in method
     * <tt>Map.entrySet().toArray</tt>.
     * 
     * 
     * 一个条目维护一个键和一个值。
     * 可以使用setValue方法更改值。
     * 这个类简化了构建自定义映射实现的过程。
     * 例如，在方法map.entryset().toArray中返回SimpleEntry实例的数组可能很方便。
     *
     * @since 1.6
     */
    public static class SimpleEntry<K,V> implements Entry<K,V> , java.io.Serializable {

		/**  
		 * serialVersionUID:  
		 */
		private static final long serialVersionUID = -7275992306104447155L;
		
        private final K key;
        private V value;
        
        public SimpleEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }
        
        /**
         * Creates an entry representing the same mapping as the
         * specified entry.
         * 创建一个表示与指定条目相同的映射的条目。
         * @param entry the entry to copy
         */
        public SimpleEntry(Entry< ? extends K, ? extends V> entry) {
        	this.key = entry.getKey();
        	this.value = entry.getValue();
        }
        
		@Override
		public K getKey() {
			return key;
		}

		@Override
		public V getValue() {
			return value;
		}

		@Override
		public V setValue(V value) {
			  
			V oldValue = this.value;
			this.value = value;			
			return oldValue;
		}
		
        /**
         * Compares the specified object with this entry for equality.
         * Returns {@code true} if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries {@code e1} and {@code e2} represent the same mapping
         * if<pre>
         *   (e1.getKey()==null ?
         *    e2.getKey()==null :
         *    e1.getKey().equals(e2.getKey()))
         *   &amp;&amp;
         *   (e1.getValue()==null ?
         *    e2.getValue()==null :
         *    e1.getValue().equals(e2.getValue()))</pre>
         * This ensures that the {@code equals} method works properly across
         * different implementations of the {@code Map.Entry} interface.
         *
         * @param o object to be compared for equality with this map entry
         * @return {@code true} if the specified object is equal to this map
         *         entry
         * @see    #hashCode
         * 
         * 将指定的对象与该条目进行比较。如果给定的对象也是一个映射条目，并且两个条目表示相同的映射，则返回true。
         * 
				   (e1.getKey()==null ?
				    e2.getKey()==null :
				    e1.getKey().equals(e2.getKey()))
				   &&
				   (e1.getValue()==null ?
				    e2.getValue()==null :
				    e1.getValue().equals(e2.getValue()))
         * 
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }
        
        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry {@code e} is defined to be: <pre>
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre>
         * This ensures that {@code e1.equals(e2)} implies that
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries
         * {@code e1} and {@code e2}, as required by the general
         * contract of {@link Object#hashCode}.
         *
         * @return the hash code value for this map entry
         * @see    #equals
         * 
         * 
         * 返回这个映射条目的散列代码值。映射条目e的散列码被定义为：
         * 	   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
   				(e.getValue()==null ? 0 : e.getValue().hashCode())
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }
        

        /**
         * Returns a String representation of this map entry.  This
         * implementation returns the string representation of this
         * entry's key followed by the equals character ("<tt>=</tt>")
         * followed by the string representation of this entry's value.
         * 
         * 返回该映射条目的字符串表示。这个实现返回这个条目的键的字符串表示，后面是相等字符（“=”），后面是这个条目的值的字符串表示。
         * @return a String representation of this map entry
         */
        public String toString() {
            return key + "=" + value;
        }
    	
    }
    
    /**
     * An Entry maintaining an immutable key and value.  This class
     * does not support method <tt>setValue</tt>.  This class may be
     * convenient in methods that return thread-safe snapshots of
     * key-value mappings.
     * 一个条目维护一个不可变的键和值。这个类不支持方法setValue。这个类在返回键值映射的线程安全快照的方法中可能很方便。
     * @since 1.6
     */
    public static class SimpleImmutableEntry<K,V>
        implements Entry<K,V>, java.io.Serializable
    {
        private static final long serialVersionUID = 7138329143949025153L;

        private final K key;
        private final V value;

        /**
         * Creates an entry representing a mapping from the specified
         * key to the specified value.
         *
         * @param key the key represented by this entry
         * @param value the value represented by this entry
         */
        public SimpleImmutableEntry(K key, V value) {
            this.key   = key;
            this.value = value;
        }

        /**
         * Creates an entry representing the same mapping as the
         * specified entry.
         *
         * @param entry the entry to copy
         */
        public SimpleImmutableEntry(Entry<? extends K, ? extends V> entry) {
            this.key   = entry.getKey();
            this.value = entry.getValue();
        }

        /**
         * Returns the key corresponding to this entry.
         *
         * @return the key corresponding to this entry
         */
        public K getKey() {
            return key;
        }

        /**
         * Returns the value corresponding to this entry.
         *
         * @return the value corresponding to this entry
         */
        public V getValue() {
            return value;
        }

        /**
         * Replaces the value corresponding to this entry with the specified
         * value (optional operation).  This implementation simply throws
         * <tt>UnsupportedOperationException</tt>, as this class implements
         * an <i>immutable</i> map entry.
         * 用指定的值替换该条目对应的值（可选操作）。
         * 这个实现简单地抛出UnsupportedOperationException,方式为这个类实现一个不可变的映射条目。
         * @param value new value to be stored in this entry
         * @return (Does not return)
         * @throws UnsupportedOperationException always
         */
        public V setValue(V value) {
            throw new UnsupportedOperationException();
        }

        /**
         * Compares the specified object with this entry for equality.
         * Returns {@code true} if the given object is also a map entry and
         * the two entries represent the same mapping.  More formally, two
         * entries {@code e1} and {@code e2} represent the same mapping
         * if<pre>
         *   (e1.getKey()==null ?
         *    e2.getKey()==null :
         *    e1.getKey().equals(e2.getKey()))
         *   &amp;&amp;
         *   (e1.getValue()==null ?
         *    e2.getValue()==null :
         *    e1.getValue().equals(e2.getValue()))</pre>
         * This ensures that the {@code equals} method works properly across
         * different implementations of the {@code Map.Entry} interface.
         *
         * @param o object to be compared for equality with this map entry
         * @return {@code true} if the specified object is equal to this map
         *         entry
         * @see    #hashCode
         */
        public boolean equals(Object o) {
            if (!(o instanceof Map.Entry))
                return false;
            Map.Entry<?,?> e = (Map.Entry<?,?>)o;
            return eq(key, e.getKey()) && eq(value, e.getValue());
        }

        /**
         * Returns the hash code value for this map entry.  The hash code
         * of a map entry {@code e} is defined to be: <pre>
         *   (e.getKey()==null   ? 0 : e.getKey().hashCode()) ^
         *   (e.getValue()==null ? 0 : e.getValue().hashCode())</pre>
         * This ensures that {@code e1.equals(e2)} implies that
         * {@code e1.hashCode()==e2.hashCode()} for any two Entries
         * {@code e1} and {@code e2}, as required by the general
         * contract of {@link Object#hashCode}.
         *
         * @return the hash code value for this map entry
         * @see    #equals
         */
        public int hashCode() {
            return (key   == null ? 0 :   key.hashCode()) ^
                   (value == null ? 0 : value.hashCode());
        }

        /**
         * Returns a String representation of this map entry.  This
         * implementation returns the string representation of this
         * entry's key followed by the equals character ("<tt>=</tt>")
         * followed by the string representation of this entry's value.
         *
         * @return a String representation of this map entry
         */
        public String toString() {
            return key + "=" + value;
        }

    }
    
}
  
