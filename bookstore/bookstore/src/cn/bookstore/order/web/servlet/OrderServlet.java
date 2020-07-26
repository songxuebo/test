package cn.bookstore.order.web.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.cart.domain.Cart;
import cn.bookstore.cart.domain.CartItem;
import cn.bookstore.order.domain.Order;
import cn.bookstore.order.domain.OrderItem;
import cn.bookstore.order.service.OrderException;
import cn.bookstore.order.service.OrderService;
import cn.bookstore.user.domain.User;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
     private OrderService orderService=new OrderService();
     
    /**
     * 添加订单
     * 把session中的车用来生成Order对象
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.从session中得到cart
    	 * 2.使用cart生成Order对象
    	 * 3.调用service方法完成添加订单
    	 * 4.保存order到request域中，转发到/jsps/order/desc.jsp
    	 */
     //从session中获得cart
    	Cart cart=(Cart)request.getSession().getAttribute("cart");
     //把cart转换成Order对象
    	/*
    	 * 创建Order对象，并设置属性
    	 * cart-->order
    	 */
    	Order order=new Order();
    	order.setOid(CommonUtils.uuid());//设置编号
    	order.setOrdertime(new Date());//设置下单时间
    	order.setState(1);//设置订单状态为1，表示未付款
    	User user=(User)request.getSession().getAttribute("session_user");
    	order.setOwner(user);//设置订单所有者
    	order.setTotal(cart.getTotal());//设置订单的合计，从cart中获得合计
    	
    	/*
    	 * 创建订单条目集合
    	 * cartItemList-->orderItemList
    	 */
    	List<OrderItem> orderItemList=new ArrayList<OrderItem>();
    	//循环遍历Cart中的所有CartItem,使用每一个CartItem对象创建OrderItem对象，并添加到集合中
    	for(CartItem cartItem : cart.getCartItems()){
    		OrderItem oi=new OrderItem();//创建订单条目
    		
    		oi.setIid(CommonUtils.uuid());//设置条目的id
    		oi.setCount(cartItem.getCount());//设置条目的数量
    		oi.setBook(cartItem.getBook());//设置条目的图书
    		oi.setSubtotal(cartItem.getSubtotal());//设置条目的小计
    		oi.setOrder(order);//设置所属订单
    		
    		orderItemList.add(oi);//把订单添加到集合中
    	}
    	
    	//把所有的订单条目添加到订单中
    	order.setOrderItemList(orderItemList);
    	
    	//清空购物车
    	cart.clear();
    	
    	/*
    	 * 调用orderService添加订单
    	 */
    	orderService.add(order);
    	/*
    	 * 保存order到request域，转发到/jsps/order/desc.jsp
    	 */
    	request.setAttribute("order", order);
        return "/jsps/order/desc.jsp";
    }
    
    /**
     * 我的订单
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.从session得到当前用户，再获取其uid
    	 * 2.使用uid调用orderService#myOrders(uid)得到该用户的所有订单List<Order>
    	 * 3.把订单列表保存到request域中，转发到/jsps/order/list.jp
    	 */
    	User user=(User)request.getSession().getAttribute("session_user");
    	List<Order> orderList=orderService.myOrders(user.getUid());
    	request.setAttribute("orderList", orderList);
    	return "f:/jsps/order/list.jsp";
    	}
    
    /**
     * 加载订单
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.得到oid参数
    	 * 2.使用oid调用service方法得到Order
    	 * 3.保存到request域，转发到/jsps/order/desc.jsp
    	 */
    	request.setAttribute("order", orderService.load(request.getParameter("oid")));
    	return "f:/jsps/order/desc.jsp";
    }
    
    /**
     * 确认收货
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.得到oid参数
    	 * 2.调用service方法
    	 *  >如果有异常，保存异常信息，转发到msg.jsp
    	 * 3.保存成功信息，转发到msg.jsp
    	 */
    	String oid=request.getParameter("oid");
    	try {
			orderService.confirm(oid);
			request.setAttribute("msg", "恭喜，交易成功！");
		} catch (OrderException e) {
		request.setAttribute("msg", e.getMessage());
		}
    	return "f:/jsps/msg.jsp";
    }
    
    /**
     * 直接结算
     */
    public String pay(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.得到oid参数
    	 * 2.调用service方法
    	 * 3.保存成功信息，转发到msg.jsp
    	 */
    	String oid=request.getParameter("oid");
        orderService.zhiFu(oid);
	    request.setAttribute("msg", "恭喜，付款成功！请等待商家发货！！");
		return "f:/jsps/msg.jsp";
    }
}
