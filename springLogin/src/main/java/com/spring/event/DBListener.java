package com.spring.event;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
public class DBListener implements ApplicationListener{
public  void onApplicationEvent(ApplicationEvent event){
//	public String status1;
	if(event instanceof DBEvent){
		DBEvent dbEvent=(DBEvent)event;
		dbEvent.print();
		System.out.println("the source is:"+dbEvent.getSource());
		 if(dbEvent.getSource()=="closed")
		dbEvent.status="closed";
	}
}
}