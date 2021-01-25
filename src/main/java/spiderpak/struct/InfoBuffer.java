package spiderpak.struct;

import java.util.LinkedList;
import spiderpak.utils.BeanFactory;


public class InfoBuffer {
 
	private LinkedList<InfoBean> infoBeans=new LinkedList<InfoBean>();//�������Ҫ����

	public LinkedList<InfoBean> getInfoBeans() {
		return infoBeans;
	}

	public boolean InfoBeanAdd(InfoBean info){
		if(BeanFactory.getRepeatFilter().isExit(info.getArticleUrl()+info.getArticletitle())){//�������ǰȫ��ȥ��
			return false;
		}else{
			infoBeans.add(info);
			return true;
		}
	}	
}
