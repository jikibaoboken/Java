import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * @author Maugham
 * 这段程序使用反射来spy on objects
 * 如何编写一个可供任意类使用的toString()方法
 * 
 */
public class ObjectAnalyzer {
	private ArrayList<Object> visited=new ArrayList<Object>();
	public String toString(Object obj){
		if(obj==null)
			return "null";
		if(visited.contains(obj))
			return "...";
		visited.add(obj);
		Class cl=obj.getClass();
		//如果cl是String的话:
		if(cl==String.class)
			return (String)obj;
		//如果cl是数组
		if(cl.isArray()){
			//打印数组所存的对象的类型
			String r=cl.getComponentType()+"[]{";
			for(int i=0;i<Array.getLength(obj);i++){
				if(i>0)
					r+=",";
				Object val=Array.get(obj, i);
				if(cl.getComponentType().isPrimitive())
					r+=val;
				else {
					r+=toString(val);
				}
			}
			return r+"}";
		}
		//如果cl既非String，又非数组，就是普通的java对象
		String r=cl.getName();
		do{
			r+="[";
			//获取所有的域对象
			Field[] fields=cl.getDeclaredFields();
			System.out.println(fields);
			//java的反射机制默认受java的安全管理器控制(可以查看有哪些域，但是不能查看域对象的值)，使用该方法可以覆盖访问控制
			AccessibleObject.setAccessible(fields, true);
			for(Field f:fields){
				
				if(!Modifier.isStatic(f.getModifiers())){
					if(!r.endsWith("["))
						r+=",";
					r+=f.getName()+"=";
					try {
						Class t=f.getType();
						Object val=f.get(obj);
						//此方法主要用来判断Class是否为原始类型（boolean、char、byte、short、int、long、float、double）
						if(t.isPrimitive())
							r+=val;
						else {
							r+=toString(val);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}				
			}r+="]";
				System.out.println(r);
				cl=cl.getSuperclass();
		}
		while(cl!=null);
		return r;
	}
}
