package com.example.controller;

import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
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
    User user=new User(username,time);
    //������Ӧ�Ĳ�����ͨ��ModelAndView����
	Map<String ,Object> model=new HashMap<String,Object>();
	if(username !=null){
		model.put("user", user);
		return new ModelAndView(getSuccessView(),model);
	}else{
		model.put("error", "Try again");
		return new ModelAndView(getFailView(),model);
	}		
  }	
}
