import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

import javax.swing.text.StyledEditorKit.ForegroundAction;

/**
 * @author Maugham
 * ��γ���ʹ�÷�����spy on objects
 * ��α�дһ���ɹ�������ʹ�õ�toString()����
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
		//���cl��String�Ļ�:
		if(cl==String.class)
			return (String)obj;
		//���cl������
		if(cl.isArray()){
			//��ӡ��������Ķ��������
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
		//���cl�ȷ�String���ַ����飬������ͨ��java����
		String r=cl.getName();
		do{
			r+="[";
			//��ȡ���е������
			Field[] fields=cl.getDeclaredFields();
			System.out.println(fields);
			//java�ķ������Ĭ����java�İ�ȫ����������(���Բ鿴����Щ�򣬵��ǲ��ܲ鿴������ֵ)��ʹ�ø÷������Ը��Ƿ��ʿ���
			AccessibleObject.setAccessible(fields, true);
			for(Field f:fields){
				
				if(!Modifier.isStatic(f.getModifiers())){
					if(!r.endsWith("["))
						r+=",";
					r+=f.getName()+"=";
					try {
						Class t=f.getType();
						Object val=f.get(obj);
						//�˷�����Ҫ�����ж�Class�Ƿ�Ϊԭʼ���ͣ�boolean��char��byte��short��int��long��float��double��
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
