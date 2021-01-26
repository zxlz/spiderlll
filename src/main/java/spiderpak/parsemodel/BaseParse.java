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
	abstract public long parse(Object o)throws Exception ;
}
