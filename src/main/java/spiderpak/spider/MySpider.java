package spiderpak.spider;


import java.sql.SQLException;
import java.util.HashSet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import spiderpak.struct.TaskComponent;

public class MySpider {
	private long errCoun = 0;//错误数
	private long tryCoun = 0;//尝试次数
	private long coun = 0;
	private TaskComponent task ;
    
	MySpider(TaskComponent task){
		this.task=task;
	}
    public void setTask(TaskComponent task){
    	this.task=task;
    }
	//执行爬取方法
	public void runspider()  {
		// 初始话pageService  用于执行sql
		// ==========================================================================================================
		
		try {
			task.getPageService().serviceInit();
		} catch (Exception e1) {

			e1.printStackTrace();
		}
		Server.pushMessage("开始");
		// 从urlManager取出url
		// ==========================================================================================================
		int delaytime = 2500;
		//取延迟配置
		if(task.getTaskContext().get("delaytime")!=null && !"".equals(task.getTaskContext().get("delaytime"))){
			delaytime= (int) task.getTaskContext().get("delaytime");
		}
		while (!task.getUrlManager().unVisitedUrlIsEmpty()) {
			//检查状态
			if("over".equals(task.getTaskContext().get("state"))){
				Server.pushMessage("quick over");
				break;
			}
			// 取出一个未访问url
			String visitUrl = (String)task.getUrlManager().unVisitedUrlDeQueue();
			task.getUrlManager().addVisitedUrl(visitUrl);
			try {
				Server.pushMessage("获取中"+visitUrl);
				HtmlPage page=task.getBrowser().download(visitUrl);
				task.getParse().go(page, visitUrl);
				Thread.sleep(delaytime);
				coun++;
			} catch (Exception e) {
				Server.pushMessage("!!!!connect--error：" + visitUrl + "Number of try: "+ ++tryCoun);
					coun--;
					if(tryCoun<11){//继续重试
						task.getUrlManager().removeVisitedUrl(visitUrl);
						task.getUrlManager().enfiQueue(visitUrl);
					}else{
						errCoun++;
						tryCoun=0;
						//记录错误，不再重试
						if(task.getTaskContext().containsKey("err")){
							HashSet<String> errSet= (HashSet<String>) task.getTaskContext().get("err");
							errSet.add(visitUrl);
						}else{
							HashSet<String> errSet= new HashSet<>();
							errSet.add(visitUrl);
							task.getTaskContext().put("err",errSet);
						}
					}
//				    task.getBrowser().proxy(true);//open proxy
				try {
					Thread.sleep(10000);
				} catch (InterruptedException interruptedException) {
					interruptedException.printStackTrace();
				}

				e.printStackTrace();
			}
			
		}

		// paseService关闭连接
		// ===================================================================================================

		if (task.getTaskContext().containsKey("err")){
			HashSet<String> set = (HashSet<String>) task.getTaskContext().get("err");
			for(String str:set){
				Server.pushMessage("end----err:"+str);
			}
		}

			try {
				task.getPageService().serviceDestroy();
			} catch (SQLException e) {
				e.printStackTrace();
			}


	};


}
