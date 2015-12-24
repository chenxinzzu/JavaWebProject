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
  //成功与失败字段
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
    //不应该是这样写，但是这样看起来比较容易理解
    String username = request.getParameter("username");
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
	String time=df.format(new Date());
	int count=0;
	
    User user=new User(username,time);
	    //保存相应的参数，通过ModelAndView返回
	Map<String ,String> model=new HashMap<String,String>();
		
	
	boolean cleanup = false;
    Charset charset = Charset.forName("utf-8");
    String path = "E:/data/leveldb";

    //init
    DBFactory factory = Iq80DBFactory.factory;
    File dir = new File(path);
    //如果数据不需要reload，则每次重启，尝试清理磁盘中path下的旧数据。
    if(cleanup) {
        factory.destroy(dir,null);//清除文件夹内的所有文件。
    }
    Options options = new Options().createIfMissing(true);
    //重新open新的db
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


    //读取当前snapshot，快照，读取期间数据的变更，不会反应出来
    Snapshot snapshot = db.getSnapshot();
    //读选项
    ReadOptions readOptions = new ReadOptions();
    readOptions.fillCache(false);//遍历中swap出来的数据，不应该保存在memtable中。
    readOptions.snapshot(snapshot);//默认snapshot为当前。
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

