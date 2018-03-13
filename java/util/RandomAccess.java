/**  
 * Project Name:collection-source  
 * File Name:RandomAccess.java  
 * Package Name:source.java.util  
 * Date:2018年3月13日下午2:19:29  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;  
/**
 * Marker interface used by <tt>List</tt> implementations to indicate that
 * they support fast (generally constant time) random access.  The primary
 * purpose of this interface is to allow generic algorithms to alter their
 * behavior to provide good performance when applied to either random or
 * sequential access lists.
 * 
 * List实现使用的标记接口表示它们支持快速（通常是常量时间）随机访问。
 * 该接口的主要目的是允许通用算法在应用到随机或顺序访问列表时改变它们的行为以提供良好的性能。
 * 
 * <p>The best algorithms for manipulating random access lists (such as
 * <tt>ArrayList</tt>) can produce quadratic behavior when applied to
 * sequential access lists (such as <tt>LinkedList</tt>).  Generic list
 * algorithms are encouraged to check whether the given list is an
 * <tt>instanceof</tt> this interface before applying an algorithm that would
 * provide poor performance if it were applied to a sequential access list,
 * and to alter their behavior if necessary to guarantee acceptable
 * performance.
 * 处理随机访问列表（如ArrayList）的最佳算法可以在应用到顺序访问列表时产生二次行为（如LinkedList）。
 * 建议使用通用列表算法来检查给定列表是否为该接口的instanceof，
 * 然后应用一种算法，如果它被应用到顺序访问列表中，并且在必要时更改它们的行为，以保证可接受的性能。
 * 
 * <p>It is recognized that the distinction between random and sequential
 * access is often fuzzy.  For example, some <tt>List</tt> implementations
 * provide asymptotically linear access times if they get huge, but constant
 * access times in practice.  Such a <tt>List</tt> implementation
 * should generally implement this interface.  As a rule of thumb, a
 * <tt>List</tt> implementation should implement this interface if,
 * for typical instances of the class, this loop:
 * <pre>
 *     for (int i=0, n=list.size(); i &lt; n; i++)
 *         list.get(i);
 * </pre>
 * runs faster than this loop:
 * <pre>
 *     for (Iterator i=list.iterator(); i.hasNext(); )
 *         i.next();
 * </pre>
 * 
 * 人们认识到随机存取和顺序存取之间的区别通常是模糊的。
 * 例如，一些列表实现提供了渐进的线性访问时间，如果它们在实践中获得了巨大的、但持续的访问时间。
 * 这样的列表实现通常应该实现这个接口。根据经验，列表实现应该实现这个接口，如果对于类的典型实例，这个循环：
 * 
 *      for (int i=0, n=list.size(); i < n; i++)
         list.get(i);
 
	runs faster than this loop: 

     for (Iterator i=list.iterator(); i.hasNext(); )
         i.next();


 * 
 *
 * <p>This interface is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @since 1.4
 */
public interface RandomAccess {

}
  
