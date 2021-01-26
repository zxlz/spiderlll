package spiderpak.struct;

import java.util.concurrent.TimeUnit;

public interface UrlManager {

	// 添加已访问地址
	public  void addVisitedUrl(String url) ;

	// 移除已访问地址
	public  void removeVisitedUrl(String url);

	// 取未访问地址
	public  String unVisitedUrlDeQueue() ;
	public  String unVisitedUrlDeQueue(long timeout, TimeUnit unit) throws InterruptedException;
	


	// 添加未访问地址
	public  boolean addUnvisitedUrl(String url) throws InterruptedException;

	
	// 获取已访问数量
	public  long getVisitedUrlNum();

	// 未访问是否空
	public  boolean unVisitedUrlIsEmpty();
	
	// 获取未访问数量
	public  long unVisitedUrlNum();
	//
	public boolean vistiedContains(String url);
}
