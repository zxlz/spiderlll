
package spiderpak.service;

import java.sql.SQLException;
import spiderpak.struct.TaskComponent;
import spiderpak.struct.InfoBean;

public abstract class BaseService {
	protected TaskComponent task;
	public void setTask(TaskComponent task) {
		this.task = task;
	}
	public abstract boolean serviceInit()throws SQLException;
	public abstract void serviceDestroy()throws SQLException ;
	public abstract int addArticles(InfoBean info) throws SQLException;
	 
}
