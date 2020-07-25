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
	 * 添加购物条目
	 */
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.得到车
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*表单传递的只有bid和数量
		 * 2.得到条目
		 *  >得到图书和数量
		 *  >先得到图书的bid，然后我们需要通过bid查询数据库得到Book
		 *  >数量表单中有
		 */
		String bid=request.getParameter("bid");
		Book book=new BookService().load(bid);
		int count=Integer.parseInt(request.getParameter("count"));
		CartItem cartItem=new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		/*
		 * 3.把条目添加到车中
		 */
		cart.add(cartItem);
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * 清空购物条目
	 */
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.得到车
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*
		 * 2.设置车的clear
		 */
		cart.clear();
		return "f:/jsps/cart/list.jsp";
	}

	/**
	 * 删除购物条目
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1.得到车
		 */
		Cart cart=(Cart)request.getSession().getAttribute("cart");
		/*
		 * 2.得到要删除的bid
		 */
		String bid=request.getParameter("bid");
		cart.delete(bid);
		return "f:/jsps/cart/list.jsp";
	}
}
