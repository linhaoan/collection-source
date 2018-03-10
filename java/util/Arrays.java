/**  
 * Project Name:collection-source  
 * File Name:Arrays.java  
 * Package Name:source.java.util  
 * Date:2018年3月8日下午4:33:05  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.util;

import java.lang.reflect.Array;

/**
 * This class contains various methods for manipulating arrays (such as
 * sorting and searching). This class also contains a static factory
 * that allows arrays to be viewed as lists.
 * 这个类包含各种操作数组的方法（例如排序和搜索）。这个类还包含一个静态工厂，允许将数组视为列表。
 * 
 * <p>The methods in this class all throw a {@code NullPointerException},
 * if the specified array reference is null, except where noted.
 * 这个类中的方法都抛出一个NullPointerException，如果指定的数组引用是空的，除了注意到的地方。
 * 
 * <p>The documentation for the methods contained in this class includes
 * briefs description of the <i>implementations</i>. Such descriptions should
 * be regarded as <i>implementation notes</i>, rather than parts of the
 * <i>specification</i>. Implementors should feel free to substitute other
 * algorithms, so long as the specification itself is adhered to. (For
 * example, the algorithm used by {@code sort(Object[])} does not have to be
 * a MergeSort, but it does have to be <i>stable</i>.)
 * 本类中包含的方法的文档包括对实现的简要描述。这样的描述应该被看作是实现说明，而不是规范的一部分。
 * 实现者应该可以自由地替换其他算法，只要规范本身被遵守。（例如，sort（Object）所使用的算法不一定是“合并”，但它必须是稳定的。）
 * 
 * <p>This class is a member of the
 * <a href="{@docRoot}/../technotes/guides/collections/index.html">
 * Java Collections Framework</a>.
 *
 * @author Josh Bloch
 * @author Neal Gafter
 * @author John Rose
 * @since  1.2
 */
public class Arrays {

	
    // Cloning 
	// 克隆
	
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of exactly the same class as the original array.
     * 复制指定的数组，用null（如果需要）截断或填充，这样拷贝就有指定的长度。
     * 对于所有在原始数组和副本中都有效的索引，两个数组将包含相同的值。
     * 对于任何在副本中有效的索引，而不是原始副本，该副本将包含null。
     * 只有当指定长度大于原始数组的长度时，才会存在这样的索引。
     * 所得到的数组与原始数组的类完全相同。
     * 
     * @param <T> the class of the objects in the array
     * 数组中的对象的类
     * 
     * @param original the array to be copied
     * 原始数组被复制
     * @param newLength the length of the copy to be returned
     * newLength返回的副本的长度
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     *     原始数组的副本,截断或垫null来获取指定的长度
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @since 1.6
     */
    @SuppressWarnings("unchecked")
    public static <T> T[] copyOf(T[] original, int newLength) {
        return (T[]) copyOf(original, newLength, original.getClass());
    }
    
    /**
     * Copies the specified array, truncating or padding with nulls (if necessary)
     * so the copy has the specified length.  For all indices that are
     * valid in both the original array and the copy, the two arrays will
     * contain identical values.  For any indices that are valid in the
     * copy but not the original, the copy will contain <tt>null</tt>.
     * Such indices will exist if and only if the specified length
     * is greater than that of the original array.
     * The resulting array is of the class <tt>newType</tt>.
     * 复制指定的数组，用null（如果需要）截断或填充，这样拷贝就有指定的长度。
     * 对于所有在原始数组和副本中都有效的索引，两个数组将包含相同的值。
     * 对于任何在副本中有效的索引，而不是原始副本，该副本将包含null。
     * 只有当指定长度大于原始数组的长度时，才会存在这样的索引。
     * 产生的数组是类newType。
     * @param <U> the class of the objects in the original array
     * 原始数组中的对象的类
     * @param <T> the class of the objects in the returned array
     * 返回数组中的对象的类
     * @param original the array to be copied
     * 原始数组被复制
     * @param newLength the length of the copy to be returned
     * @param newType the class of the copy to be returned
     * @return a copy of the original array, truncated or padded with nulls
     *     to obtain the specified length
     *     原始数组的副本，截断或用null填充，以获得指定的长度
     * @throws NegativeArraySizeException if <tt>newLength</tt> is negative
     * @throws NullPointerException if <tt>original</tt> is null
     * @throws ArrayStoreException if an element copied from
     *     <tt>original</tt> is not of a runtime type that can be stored in
     *     an array of class <tt>newType</tt>
     *     如果从原来复制的元素不是一个可以存储在类newType数组中的运行时类型
     * @since 1.6
     */
    public static <T,U> T[] copyOf(U[] original, int newLength, Class<? extends T[]> newType) {
        @SuppressWarnings("unchecked")
        T[] copy = ((Object)newType == (Object)Object[].class)
            ? (T[]) new Object[newLength]
            : (T[]) Array.newInstance(newType.getComponentType(), newLength);
        System.arraycopy(original, 0, copy, 0,
                         Math.min(original.length, newLength));
        return copy;
    }
}
  
