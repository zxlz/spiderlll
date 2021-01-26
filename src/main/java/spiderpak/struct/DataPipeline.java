package spiderpak.struct;

import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import org.omg.CORBA.TIMEOUT;
import spiderpak.utils.BeanFactory;


public class DataPipeline<T1,T2> {
	private LinkedBlockingQueue<T1> originalData=new LinkedBlockingQueue<T1>();//下载数据管道
	private LinkedBlockingQueue<T2> outData=new LinkedBlockingQueue<T2>();//解析数据管道

//	public LinkedList<InfoBean> getInfoBeans() {
//		return infoBeans;
//	}

	public T1 OriginalDataTake() throws InterruptedException {
			//阻塞取出
		return originalData.take();
	}
	public T1 OriginalDataPoll(long timeout, TimeUnit unit) throws InterruptedException {
		//阻塞取出
		return originalData.poll(timeout,unit);
	}
	public boolean OriginalDataPut(T1 info) throws InterruptedException {
		//阻塞放入
		originalData.put(info);
		return true;
	}
	public boolean OutDataPut(T2 info) throws InterruptedException {
//		if(BeanFactory.getRepeatFilter().isExit(url+title)){//�������ǰȫ��ȥ��
//			return false;
//		}else{
			//阻塞放入
			outData.put(info);
			return true;
//		}
	}
	public T2 OutDataTake() throws InterruptedException {
		return outData.take();
	}
	public T2 OutDataPoll(long timeout, TimeUnit unit) throws InterruptedException {
		return outData.poll(timeout,unit);
	}
}
