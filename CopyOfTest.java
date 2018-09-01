import java.lang.reflect.*;
import java.util.*;
/**
 * 使用反射来操作arrays
 * @author Maugham
 *
 */
public class CopyOfTest {
	public static void main(String[] args){
		int[] a={1,2,3};
		a=(int[]) goodCopyOf(a,10);
		System.out.println(Arrays.toString(a));
		
		String[] b={"Tom","Maugham","Harry"};
		b=(String[]) goodCopyOf(b,10);
		System.out.println(Arrays.toString(b));
		
		System.out.println("The following call will generate an exception.");
		b=(String[]) badCopyOf(b,10);
	}
	//这个方法试图通过分配新的array，拷贝a中的值来完成array的扩大
	//但是有问题，数组是Object[]类型的,没办法转回原来a的复杂java类型
	public static Object[] badCopyOf(Object[] a,int newLength){
		System.out.println(a);
		Object[] newArray=new Arrays[newLength];
		System.arraycopy(a,0,newArray,0,10);
		return newArray;
	}
	public static Object goodCopyOf(Object a,int newLength){
		Class cl=a.getClass();
		if(!cl.isArray()){
			return null;
		}
		Class componentType=cl.getComponentType();
		int length=Array.getLength(a);
		Object newArray=Array.newInstance(componentType,newLength);
		System.arraycopy(a, 0, newArray, 0, Math.min(newLength, length));
		return newArray;
	}
}
