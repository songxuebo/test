package cn.bookstore.user.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.servlet.BaseServlet;

public class AdminUserServlet extends BaseServlet {
    
    public String login(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String adminname=request.getParameter("adminname");
    	String password=request.getParameter("password");
    	if(adminname.equals("Admin")&&password.equals("123")){
    		 return "r:/adminjsps/admin/index.jsp";
    	}else{
    		request.setAttribute("msg", "�û��������������������룡");
            return "f:/adminjsps/login.jsp";
    	}
    }
}
