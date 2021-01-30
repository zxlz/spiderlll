
package spiderpak.service;

import java.sql.SQLException;
import spiderpak.struct.TaskComponent;


public abstract class BaseService<E> {
	protected TaskComponent task;
	public void setTask(TaskComponent task) {
		this.task = task;
	}
	public abstract void init()throws Exception;
	public abstract void destroy()throws Exception ;
	public abstract int addData(E info) throws Exception;
	 
}
