package cn.bookstore.order.web.servlet.admin;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.order.domain.Order;
import cn.bookstore.order.service.OrderException;
import cn.bookstore.order.service.OrderService;
import cn.itcast.servlet.BaseServlet;

public class AdminOrderServlet extends BaseServlet {
     private OrderService orderService=new OrderService();
     
     
     /**
 	 * �鿴���ж���
 	 */
 	public String findAll(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.findAll();
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * �鿴δ�����
 	 */
 	public String unpaid(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(1);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * �鿴�Ѹ����
 	 */
 	public String payment(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(2);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * �鿴δ�ջ�����
 	 */
 	public String notReceived(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(3);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * �鿴����ɶ���
 	 */
 	public String Received(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(4);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
 	
    /**
     * ����
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String oid=request.getParameter("oid");
        orderService.confirm2(oid);
        return findAll(request, response);
	}
}
