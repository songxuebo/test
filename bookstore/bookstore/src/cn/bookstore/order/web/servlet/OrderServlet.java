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
     * ��Ӷ���
     * ��session�еĳ���������Order����
     */
    public String add(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.��session�еõ�cart
    	 * 2.ʹ��cart����Order����
    	 * 3.����service���������Ӷ���
    	 * 4.����order��request���У�ת����/jsps/order/desc.jsp
    	 */
     //��session�л��cart
    	Cart cart=(Cart)request.getSession().getAttribute("cart");
     //��cartת����Order����
    	/*
    	 * ����Order���󣬲���������
    	 * cart-->order
    	 */
    	Order order=new Order();
    	order.setOid(CommonUtils.uuid());//���ñ��
    	order.setOrdertime(new Date());//�����µ�ʱ��
    	order.setState(1);//���ö���״̬Ϊ1����ʾδ����
    	User user=(User)request.getSession().getAttribute("session_user");
    	order.setOwner(user);//���ö���������
    	order.setTotal(cart.getTotal());//���ö����ĺϼƣ���cart�л�úϼ�
    	
    	/*
    	 * ����������Ŀ����
    	 * cartItemList-->orderItemList
    	 */
    	List<OrderItem> orderItemList=new ArrayList<OrderItem>();
    	//ѭ������Cart�е�����CartItem,ʹ��ÿһ��CartItem���󴴽�OrderItem���󣬲���ӵ�������
    	for(CartItem cartItem : cart.getCartItems()){
    		OrderItem oi=new OrderItem();//����������Ŀ
    		
    		oi.setIid(CommonUtils.uuid());//������Ŀ��id
    		oi.setCount(cartItem.getCount());//������Ŀ������
    		oi.setBook(cartItem.getBook());//������Ŀ��ͼ��
    		oi.setSubtotal(cartItem.getSubtotal());//������Ŀ��С��
    		oi.setOrder(order);//������������
    		
    		orderItemList.add(oi);//�Ѷ�����ӵ�������
    	}
    	
    	//�����еĶ�����Ŀ��ӵ�������
    	order.setOrderItemList(orderItemList);
    	
    	//��չ��ﳵ
    	cart.clear();
    	
    	/*
    	 * ����orderService��Ӷ���
    	 */
    	orderService.add(order);
    	/*
    	 * ����order��request��ת����/jsps/order/desc.jsp
    	 */
    	request.setAttribute("order", order);
        return "/jsps/order/desc.jsp";
    }
    
    /**
     * �ҵĶ���
     */
    public String myOrders(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.��session�õ���ǰ�û����ٻ�ȡ��uid
    	 * 2.ʹ��uid����orderService#myOrders(uid)�õ����û������ж���List<Order>
    	 * 3.�Ѷ����б��浽request���У�ת����/jsps/order/list.jp
    	 */
    	User user=(User)request.getSession().getAttribute("session_user");
    	List<Order> orderList=orderService.myOrders(user.getUid());
    	request.setAttribute("orderList", orderList);
    	return "f:/jsps/order/list.jsp";
    	}
    
    /**
     * ���ض���
     */
    public String load(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.�õ�oid����
    	 * 2.ʹ��oid����service�����õ�Order
    	 * 3.���浽request��ת����/jsps/order/desc.jsp
    	 */
    	request.setAttribute("order", orderService.load(request.getParameter("oid")));
    	return "f:/jsps/order/desc.jsp";
    }
    
    /**
     * ȷ���ջ�
     */
    public String confirm(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.�õ�oid����
    	 * 2.����service����
    	 *  >������쳣�������쳣��Ϣ��ת����msg.jsp
    	 * 3.����ɹ���Ϣ��ת����msg.jsp
    	 */
    	String oid=request.getParameter("oid");
    	try {
			orderService.confirm(oid);
			request.setAttribute("msg", "��ϲ�����׳ɹ���");
		} catch (OrderException e) {
		request.setAttribute("msg", e.getMessage());
		}
    	return "f:/jsps/msg.jsp";
    }
    
    /**
     * ֱ�ӽ���
     */
    public String pay(HttpServletRequest request, HttpServletResponse response)
    		throws ServletException, IOException {
    	/*
    	 * 1.�õ�oid����
    	 * 2.����service����
    	 * 3.����ɹ���Ϣ��ת����msg.jsp
    	 */
    	String oid=request.getParameter("oid");
        orderService.zhiFu(oid);
	    request.setAttribute("msg", "��ϲ������ɹ�����ȴ��̼ҷ�������");
		return "f:/jsps/msg.jsp";
    }
}
