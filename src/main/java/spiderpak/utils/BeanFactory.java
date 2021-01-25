package spiderpak.utils;

import spiderpak.utils.repeatfilter.RepeatFilter;

public class BeanFactory {	   
	static RepeatFilter filter=(RepeatFilter) new BeanFactory();
    // private static final sqlConn sqlconn = new sqlConn();    
	public static  RepeatFilter getRepeatFilter(){
		return filter;
	} 
      
}
