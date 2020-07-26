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
 	 * 查看所有订单
 	 */
 	public String findAll(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.findAll();
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * 查看未付款订单
 	 */
 	public String unpaid(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(1);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * 查看已付款订单
 	 */
 	public String payment(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(2);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * 查看未收货订单
 	 */
 	public String notReceived(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(3);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
    /**
 	 * 查看已完成订单
 	 */
 	public String Received(HttpServletRequest request, HttpServletResponse response)
 			throws ServletException, IOException {
 		List<Order> orderList=orderService.adoptStateFind(4);
    	request.setAttribute("orderList", orderList);
    	return "f:/adminjsps/admin/order/list.jsp";
 	}
 	
 	
    /**
     * 发货
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	String oid=request.getParameter("oid");
        orderService.confirm2(oid);
        return findAll(request, response);
	}
}
