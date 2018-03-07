package source.demo;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class SimpleHashMap<K,V> extends AbstractMap<K,V> {
	
	private static final int SIZE = 997;
	
	@SuppressWarnings("unchecked")
	LinkedList<Entry<K,V>>[] buckets = new LinkedList[SIZE];
	
	public V put(K key,V value) {
		V oldValue = null;
		//索引
		int index = Math.abs(key.hashCode()) %SIZE;
		
		if(buckets[index] == null) {
			buckets[index] = new LinkedList<Entry<K,V>>();
		}
		LinkedList<Entry<K,V>> bucket = buckets[index];
		Entry<K,V> pair = new SimpleEntry<K,V>(key, value);
		
		boolean found = false;
		ListIterator<Entry<K,V>> it = bucket.listIterator();
		while(it.hasNext()) {
			Entry<K,V> iPair = it.next();
			if(iPair.getKey().equals(key)) {
				oldValue = iPair.getValue();
				it.set(pair);
				found = true;
				break;
			}
		}
		if(!found) {
			buckets[index].add(pair);
		}
		return oldValue;
		
	}
	
	public V get (Object key) {
		int index = Math.abs(key.hashCode()) %SIZE;		
		if(buckets[index] == null) {
			return null;
		}
		for(Entry<K,V> ipair :buckets[index]) {
			if(ipair.getKey().equals(key)) {
				return ipair.getValue();
			}
		}
		return null;		
	}
	

	@Override
	public Set<Entry<K, V>> entrySet() {
		Set<Entry<K, V>> set= new HashSet<Entry<K,V>>();
		for(LinkedList<Entry<K,V>> bucket :buckets) {
			if(bucket == null) {
				continue;
			}
			for(Entry<K,V> pair : bucket) {
				set.add(pair);
			}
		}
		return null;
	}



}
