package spiderpak.parsemodel;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import spiderpak.struct.TaskComponent;

public abstract class BaseParse {
    protected TaskComponent task;	
	
	public void setTask(TaskComponent task) {
		this.task = task;
	}



abstract public long go(HtmlPage page,String visitUrl)throws Exception ;
}
