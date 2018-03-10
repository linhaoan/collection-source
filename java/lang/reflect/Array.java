/**  
 * Project Name:collection-source  
 * File Name:Array.java  
 * Package Name:source.java.reflect  
 * Date:2018年3月8日下午5:19:40  
 * Copyright (c) 2018, linhao@fenla.net All Rights Reserved.  
 *  
*/  
  
package source.java.lang.reflect;  
/**
 * The {@code Array} class provides static methods to dynamically create and
 * access Java arrays.
 * 数组类提供静态方法来动态创建和访问Java数组。
 * <p>{@code Array} permits widening conversions to occur during a get or set
 * operation, but throws an {@code IllegalArgumentException} if a narrowing
 * conversion would occur.
 * 数组允许在get或set操作期间进行更大范围的转换，但是如果发生了缩小的转换，则抛出一个非法的参数异常。
 * @author Nakul Saraiya
 */
public final class Array {

    /**
     * Creates a new array with the specified component type and
     * length.
     * Invoking this method is equivalent to creating an array
     * as follows:
     * 创建一个带有指定组件类型和长度的新数组。调用这个方法等价于创建一个数组，如下：
     *  int[] x = {length};
 		Array.newInstance(componentType, x);

     * <blockquote>
     * <pre>
     * int[] x = {length};
     * Array.newInstance(componentType, x);
     * </pre>
     * </blockquote>
     *
     * <p>The number of dimensions of the new array must not
     * exceed 255.
     * 新数组的维数不能超过255。
     * @param componentType the {@code Class} object representing the
     * component type of the new array
     * 组件类型类对象，表示新数组的组件类型
     * @param length the length of the new array
     * 长度是新数组的长度
     * @return the new array
     * @exception NullPointerException if the specified
     * {@code componentType} parameter is null
     * 如果指定的组件类型参数为null
     * @exception IllegalArgumentException if componentType is {@link
     * Void#TYPE} or if the number of dimensions of the requested array
     * instance exceed 255.
     * 如果组件类型无效。类型或如果请求的数组实例的维数超过255
     * @exception NegativeArraySizeException if the specified {@code length}
     * is negative 如果指定的长度是负的
     */
    public static Object newInstance(Class<?> componentType, int length)
        throws NegativeArraySizeException {
        return newArray(componentType, length);
    }
    
    /**
     * Creates a new array
     * with the specified component type and dimensions.
     * If {@code componentType}
     * represents a non-array class or interface, the new array
     * has {@code dimensions.length} dimensions and
     * {@code componentType} as its component type. If
     * {@code componentType} represents an array class, the
     * number of dimensions of the new array is equal to the sum
     * of {@code dimensions.length} and the number of
     * dimensions of {@code componentType}. In this case, the
     * component type of the new array is the component type of
     * {@code componentType}.
     * 创建一个带有指定组件类型和维度的新数组。
     * 如果组件类型表示一个非数组类或接口，那么新数组有维度。
     * 。长度维度和组件类型作为其组件类型。如果组件类型表示一个数组类，那么新数组的维数就等于维度的和。
     * 长度和组件类型的维数。在这种情况下，新数组的组件类型是组件类型的组件类型。
     * <p>The number of dimensions of the new array must not
     * exceed 255.
     *  新数组的维数不能超过255。
     * @param componentType the {@code Class} object representing the component
     * type of the new array
     * @param dimensions an array of {@code int} representing the dimensions of
     * the new array
     * @return the new array
     * @exception NullPointerException if the specified
     * {@code componentType} argument is null
     * @exception IllegalArgumentException if the specified {@code dimensions}
     * argument is a zero-dimensional array, if componentType is {@link
     * Void#TYPE}, or if the number of dimensions of the requested array
     * instance exceed 255.
     * @exception NegativeArraySizeException if any of the components in
     * the specified {@code dimensions} argument is negative.
     */
    public static Object newInstance(Class<?> componentType, int... dimensions)
        throws IllegalArgumentException, NegativeArraySizeException {
        return multiNewArray(componentType, dimensions);
    }
    
    /*
     * Private
     */

    private static native Object newArray(Class<?> componentType, int length)
        throws NegativeArraySizeException;
    
    private static native Object multiNewArray(Class<?> componentType,
            int[] dimensions)
            throws IllegalArgumentException, NegativeArraySizeException;
}
  
