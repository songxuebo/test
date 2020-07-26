package cn.bookstore.user.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import cn.bookstore.user.domain.User;


public class LoginFilter implements Filter {

  
	public void destroy() {
		
	}

	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * 1.��session�л���û���Ϣ
		 * 2.�ж����session�д����û���Ϣ�����У�
		 * 3.���򣬱��������Ϣ��ת����login��jsp
		 */
		HttpServletRequest httpServletRequest=(HttpServletRequest) request;
		User user=(User)httpServletRequest.getSession().getAttribute("session_user");
		if(user!=null){
		chain.doFilter(request, response);
		}else{
			httpServletRequest.setAttribute("msg", "����û�е�¼��");
			httpServletRequest.getRequestDispatcher("/jsps/user/login.jsp").forward(httpServletRequest, response);
		}
	}

	
	public void init(FilterConfig fConfig) throws ServletException {
		
	}

}
