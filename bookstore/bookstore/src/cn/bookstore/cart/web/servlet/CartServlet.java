package cn.bookstore.cart.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.book.domain.Book;
import cn.bookstore.book.service.BookService;
import cn.bookstore.cart.domain.Cart;
import cn.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

public class CartServlet extends BaseServlet {

	/**
	 * ��ӹ�����Ŀ
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.�õ���
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*�����ݵ�ֻ��bid������
		 * 2.�õ���Ŀ
		 *  >�õ�ͼ�������
		 *  >�ȵõ�ͼ���bid��Ȼ��������Ҫͨ��bid��ѯ���ݿ�õ�Book
		 *  >����������
		 */
		String bid=request.getParameter("bid");
		Book book=new BookService().load(bid);
		int count=Integer.parseInt(request.getParameter("count"));
		CartItem cartItem=new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		/*
		 * 3.����Ŀ��ӵ�����
		 */
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * ��չ�����Ŀ
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.�õ���
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*
		 * 2.���ó���clear
		 */
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * ɾ��������Ŀ
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.�õ���
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*
		 * 2.�õ�Ҫɾ����bid
		 */
		String bid=request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
}
