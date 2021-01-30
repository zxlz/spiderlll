package spiderpak.spider;

import javafx.concurrent.Task;
import org.apache.commons.logging.LogFactory;
import spiderpak.struct.TaskComponent;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;

public class Server extends ServerSocket {
	private static final Control  control=new Control();
	 private static final int SERVER_PORT =10001;
	 private static ArrayList<ServerThread> threads = new ArrayList<ServerThread>();
	 private static ArrayList<Thread> spiderThreads = new ArrayList<Thread>();
	 private static LinkedList<String> messages =new LinkedList<String>();
	 private static boolean hasPrint =false;
	 private static ArrayList<String> users =new ArrayList<String>();
     private static	PrintWriter logpw=null;
     private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     private static PrintWriter pw=null;

	 public static void main(String[] args)throws IOException {
		 System.out.println("111");
		 new Server();
	 }

     private static void pushMessage(String msg){
         String time=df.format(new Date());
//    	 messages.addLast(time+":"+msg);
//         hasPrint =true;
//         logpw.println(time+":"+msg);
//         logpw.flush();
//         pw.println(time+":"+msg);
         System.out.println(time+":"+msg);
     }


     
     public Server() throws IOException {
		super(SERVER_PORT);
		logpw=new PrintWriter(new FileWriter("/Users/zxl/Documents/log.txt",true));
		  new PrintOutThread();
		while (true) {
			Socket socket = accept();
			  new ServerThread(socket);
		}
		
	}
	
	

    class PrintOutThread extends Thread{
          
        public PrintOutThread(){
            start();
        }
          
        @Override
        public void run() {
            while(true){
                if(hasPrint){
                    String message = messages.getFirst();
                    for (ServerThread thread : threads) {
                        thread.sendMessage(message);
                    }
                    messages.removeFirst();
                    hasPrint = messages.size() >0 ?true :false;
                }
            }
        }
    }
      
	

	class ServerThread extends Thread{
	
		
		 private Socket thSocket;
		 private BufferedReader bfr_in;
	
		 private String cAddr;
		 private String cPort;
		 private int flag=0;
		 public ServerThread(Socket socket)throws IOException {
	            thSocket = socket;
	             cAddr=thSocket.getInetAddress().getHostAddress();
	             cPort=thSocket.getPort()+"";
	             bfr_in = new BufferedReader(new InputStreamReader(thSocket.getInputStream()));
	    		 pw = new PrintWriter(thSocket.getOutputStream(),true);
	    		 pushMessage("连接 "+cAddr+":"+cPort+"!");
	    		 pushMessage(""+cAddr+":"+cPort+":(" + getName() +") 收到...");
	    		
	            start();
	        }
		 @Override
		public void run() {
			 pushMessage(""+cAddr+":"+cPort+":(" + getName() +") run...");
			 try {
				String line=bfr_in.readLine();
				 String config =null;
				 while((line=bfr_in.readLine())!=null && flag==0){
			if(!line.equals("close")){
				pushMessage(cAddr+":"+cPort+"--"+ line);
				
				if(line.contains("user")){
				  	String user=line.split("user")[0];
					users.add(user);
				  	pushMessage(user);
				}
				if(line.contains("start")){

					config = line;
				  spiderThreads.add(control.CreateTh(config));
				}

				pushMessage(line);
               
			}else{
				flag=1;


			}
			}   
			    
				 pushMessage("close, "+cAddr+":"+cPort+"!");
				 pushMessage(""+cAddr+":"+cPort+"exit!");
           		
				} catch (IOException e) {
			}finally {        
		         try {
		        	 pw.close();
		        	 bfr_in.close();
					thSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}    

	        private void sendMessage(String msg){
	        	pw.println(msg);
	        	pw.flush();
	        }
	          

	        private String OnlineUsers() {
	            String s ="--- ����ʹ���� ---\015\012";
	            for (int i =0; i < users.size(); i++) {
	                s +="[" + users.get(i) +"]\015\012";
	            }
	            s +="--------------------";
	            return s;
	        }
	        
		 
	  
	}
	

	
	 
	 
	
}
