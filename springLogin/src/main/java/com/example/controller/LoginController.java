package com.example.controller;

import java.util.Date;
import java.text.SimpleDateFormat;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.spring.event.DBEvent;


import org.iq80.leveldb.*;
import org.iq80.leveldb.impl.Iq80DBFactory;
import static org.iq80.leveldb.impl.Iq80DBFactory.*;
import java.io.*;


import java.nio.charset.Charset;


import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

import com.example.entity.User;


public class LoginController extends AbstractController {
  //�ɹ���ʧ���ֶ�
  private String successView;
  private String failView;
  public String getSuccessView() {
    return successView;
  }

  public void setSuccessView(String successView) {
    this.successView = successView;
  }
	public String getFailView() {
		return failView;
	}

	public void setFailView(String failView) {
		this.failView = failView;
	}
   
  
	@Override
  protected ModelAndView handleRequestInternal(HttpServletRequest request,
      HttpServletResponse response) throws Exception {
    //��Ӧ��������д�����������������Ƚ��������
    String username = request.getParameter("username");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//�������ڸ�ʽ
	String time=df.format(new Date());
	int count=0;
	
    User user=new User(username,time);
	    //������Ӧ�Ĳ�����ͨ��ModelAndView����
	Map<String ,String> model=new HashMap<String,String>();
		
	
	boolean cleanup = false;
    Charset charset = Charset.forName("utf-8");
    String path = "E:/data/leveldb";

    //init
    DBFactory factory = Iq80DBFactory.factory;
    File dir = new File(path);
    //������ݲ���Ҫreload����ÿ���������������������path�µľ����ݡ�
    if(cleanup) {
        factory.destroy(dir,null);//����ļ����ڵ������ļ���
    }
    Options options = new Options().createIfMissing(true);
    //����open�µ�db
    DB db = factory.open(dir,options);
//listener event test
	ApplicationContext context=new ClassPathXmlApplicationContext("applicationContext.xml");
	DBEvent event=new DBEvent("opened");
	context.publishEvent(event);
    //write
    if(username !=null && !username.equals("")){
    	db.put(username.getBytes(charset),time.getBytes(charset));
    	model.put(username, time);

    }
    else{
		model.put("error", "Try again");
		return new ModelAndView(getFailView(),model);
    }


    //��ȡ��ǰsnapshot�����գ���ȡ�ڼ����ݵı�������ᷴӦ����
    Snapshot snapshot = db.getSnapshot();
    //��ѡ��
    ReadOptions readOptions = new ReadOptions();
    readOptions.fillCache(false);//������swap���������ݣ���Ӧ�ñ�����memtable�С�
    readOptions.snapshot(snapshot);//Ĭ��snapshotΪ��ǰ��
    DBIterator iterator = db.iterator(readOptions);
    while (iterator.hasNext()) {
        Map.Entry<byte[],byte[]> item = iterator.next();
        String key = new String(item.getKey(),charset);
        String value = new String(item.getValue(),charset);//null,check.
        System.out.println(key + ":" + value);
      	model.put(key, value);
        count++;
    }
    iterator.close();//must be
 
    //
    db.close();

    
	ModelAndView mav=new ModelAndView();
	mav.addObject("model",model);
	mav.addObject("user",user);
	mav.addObject("count",count);
	mav.setViewName("showUser");

    return mav;
	
		//return new ModelAndView(getSuccessView(),model);
  
	}
		
  }	

