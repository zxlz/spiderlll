package spiderpak.utils.repeatfilter;

public class HashFunc {
	
		 private int cap;  
	    private int sign; 
	   
	    public HashFunc(int cap, int sign){  
	        this.cap = cap;  
	        this.sign = sign;  
	    }  
	    public int hash(String value){  
	        int result = 0;  
	        int length = value.length();  
	        for(int i=0; i<length; i++){  
	            result = sign*result + value.charAt(i);  
	        }  
	          
	        return (cap-1) & result;  
	    }  
	
}
