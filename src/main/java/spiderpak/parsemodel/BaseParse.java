package spiderpak.parsemodel;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import spiderpak.struct.TaskComponent;

public abstract class BaseParse {
    protected TaskComponent task;	
	
	public void setTask(TaskComponent task) {
		this.task = task;
	}


	/**
	 *
	 * @param
	 * @return
	 * @throws Exception
	 */
	public abstract long parse(Object o)throws Exception ;
	public  abstract void init()throws Exception;
	public  abstract void destroy()throws Exception;
}
