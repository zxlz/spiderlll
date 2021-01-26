
package spiderpak.service;

import java.sql.SQLException;
import spiderpak.struct.TaskComponent;
import spiderpak.struct.InfoBean;

public abstract class BaseService<E> {
	protected TaskComponent task;
	public void setTask(TaskComponent task) {
		this.task = task;
	}
	public abstract boolean serviceInit()throws SQLException;
	public abstract void serviceDestroy()throws SQLException ;
	public abstract int addData(E info) throws SQLException;
	 
}
