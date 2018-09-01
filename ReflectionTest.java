import java.util.*;
//util���Ǹ�ʲô�ģ�
import java.lang.reflect.*;
//lang���Ǹ�ʲô�ģ�
/**
 * ѧϰĿ�ģ���β鿴�����������������ƺ�����
 * @author Maugham
 * ʹ�÷����ӡһ�����������field��constructor,method
 */
public class ReflectionTest {
	public static void main(String[] args){
		String name;
		if(args.length>0)
			name=args[0];
		else{
			Scanner in=new Scanner(System.in);
			System.out.println("Enter class name(e.g java.util.Date):");
			name=in.next();
		}
		try{
			Class cl=Class.forName(name);
			Class superClass=cl.getSuperclass();
			String modifiers=Modifier.toString(cl.getModifiers());
			if(modifiers.length()>0)
				System.out.print(modifiers+" ");
			System.out.print("class"+name);
			if(superClass!=null && superClass!=Object.class)
				System.out.print("extends "+superClass.getName());
			System.out.println("\n{\n");
			printMyConstructors(cl);
			System.out.println();
			printMyMethods(cl);
			System.out.println();
			printMyFields(cl);
			System.out.println("\n}\n");
		}
		catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		System.exit(0);
	}
	/**
	 * ��ӡ��Ĺ�����
	 */
	public static void printMyConstructors(Class cl){
		Constructor[] constructors=cl.getConstructors();
		for(Constructor c:constructors){
			String name=c.getName();
			System.out.println("   ");
			String modifiers=Modifier.toString(c.getModifiers());
			if(modifiers.length()>0)
				System.out.print(modifiers+" ");
			System.out.print(name+"(");
			Class[] parameterTypes=c.getParameterTypes();
			for(int j=0;j<parameterTypes.length;j++){
				if(j>0){
					System.out.print(",");	
				}
				System.out.print(parameterTypes[j].getName());	
			}
			System.out.println(");");
		}
	}
	/**
	 * ��ӡ������з���
	 */
	public static void printMyMethods(Class cl){
		Method[] methods=cl.getMethods();
		for(Method m:methods){
			Class retType=m.getReturnType();
			String name=m.getName();
			System.out.print("   ");
			String modifiers=Modifier.toString(m.getModifiers());
			if(modifiers.length()>0)
				System.out.print(modifiers+" ");
			System.out.print(retType.getName()+" "+name+"(");
			Class[] parameterTypes=m.getParameterTypes();
			for(int j=0;j<parameterTypes.length;j++){
				if(j>0){
					System.out.print(",");	
				}
				System.out.print(parameterTypes[j].getName());	
			}
			System.out.println(");");
		}
	}
	/**
	 * ��ӡ��Ĺ�����
	 */
	public static void printMyFields(Class cl){
		//getFields()�����������䳬��Ĺ����� getDeclaredFields()����������ȫ����
		Field[] fields=cl.getDeclaredFields();
		for(Field f:fields){
			Class type=f.getType();
			String name=f.getName();
			System.out.println("   ");
			String modifiers=Modifier.toString(f.getModifiers());
			if(modifiers.length()>0)
				System.out.print(modifiers+" ");
			System.out.println(type.getName()+" "+name+";");
		}
	}
}
