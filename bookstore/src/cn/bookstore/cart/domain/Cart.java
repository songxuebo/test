package cn.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车类
 * 
 */
public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	/**
	 * 计算合计
	 */
	public double getTotal(){
		//合计=所有条目的小计之和
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem : map.values()){
			BigDecimal subtotal=new BigDecimal(""+cartItem.getSubtotal());
			total=total.add(subtotal);
		}
		return total.doubleValue();
	}

	/**
	 * 添加条目到车中
	 */
	public void add(CartItem cartItem) {
		if (map.containsKey(cartItem.getBook().getBid())) {// 判断原来车中是否存在该条目
			CartItem _carItem = map.put(cartItem.getBook().getBid(), cartItem);// 返回原条目，_cartItem是原条目，只是数量要变
			_carItem.setCount(_carItem.getCount() + cartItem.getCount());// 设置老条目的数量为，其自己数量+新条目数量
			map.put(cartItem.getBook().getBid(), _carItem);
		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}

	/**
	 * 清空所有条目
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * 删除指定条目
	 */
	public void delete(String bid) {
		map.remove(bid);
	}

	/**
	 * 获取所有条目
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}
