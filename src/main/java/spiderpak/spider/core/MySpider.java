package spiderpak.spider.core;


import java.sql.SQLException;
import java.util.HashSet;

import com.gargoylesoftware.htmlunit.html.HtmlPage;

import spiderpak.spider.Server;
import spiderpak.struct.TaskComponent;

public class MySpider {
	protected volatile boolean running = false;
	protected volatile boolean paused = false;
	private TaskComponent task ;
	private Download download;
	private Parse parse;
	private Storage storage;
	public MySpider(TaskComponent task){
		this.task=task;
	}
    public void setTask(TaskComponent task){
    	this.task=task;
    }

	//执行爬取方法
	public void start()  {
		// 初始话pageService  配置种子url等操作
		// ==========================================================================================================

			running = true;
			paused = false;
			setName((String) task.getTaskContext().get("name"));
			try {
				task.getPageService().serviceInit();
			} catch (Exception e1) {
				e1.printStackTrace();
			}


			// ==========================================================================================================

			startDowload();
			startParse();
			startStorage();

			//都为空闲 ，设置spider不再运行
			while(!download.isIdle() || !parse.isIdle() || !storage.isIdle()){
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					//ignore
				}
			}
			running=false;
			Server.pushMessage("endding...");
			//等待各部分安全结束
			while(!(download.state== Download.DownloadState.ENDED
					&& parse.state== Parse.ParseState.ENDED
					&& storage.state== Storage.StorageState.ENDED)){
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					//ignore
				}
			}
			// ===================================================================================================

			if (task.getTaskContext().containsKey("downloadErr")){
				HashSet<String> set = (HashSet<String>) task.getTaskContext().get("downloadErr");
				for(String str:set){
					Server.pushMessage("end----downloadErr:"+str);
				}
			}

// Service关闭连接

			try {
				task.getPageService().serviceDestroy();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			Server.pushMessage("ended");

	};
	private void startDowload(){
		Server.pushMessage("start dowloadThread");
		download = new Download(task,this);
		String threadName = getName() + "-download";
		Thread t = new Thread(download, threadName);
		t.start();
	}
	private void startParse(){
		Server.pushMessage("start parseThread");
		parse = new Parse(task,this);
		String threadName = getName() + "-parse";
		Thread t = new Thread(parse, threadName);
		t.start();
	}
	private void startStorage(){
		Server.pushMessage("start storageThread");
		storage = new Storage(task,this);
		String threadName = getName() + "-storage";
		Thread t = new Thread(storage, threadName);
		t.start();
	}

	public void pause() {
		if (running && !paused) {
			paused = true;
		}
	}
	public boolean isRunning() {
		return running;
	}

	public boolean isPaused() {
		return paused;
	}



	private String name = "spider";
	public void setName(String name) { this.name = name; }
	public String getName() { return name; }


}
