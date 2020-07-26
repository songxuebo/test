package cn.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ���ﳵ��
 * 
 */
public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	/**
	 * ����ϼ�
	 */
	public double getTotal(){
		//�ϼ�=������Ŀ��С��֮��
		BigDecimal total=new BigDecimal("0");
		for(CartItem cartItem : map.values()){
			BigDecimal subtotal=new BigDecimal(""+cartItem.getSubtotal());
			total=total.add(subtotal);
		}
		return total.doubleValue();
	}

	/**
	 * �����Ŀ������
	 */
	public void add(CartItem cartItem) {
		if (map.containsKey(cartItem.getBook().getBid())) {// �ж�ԭ�������Ƿ���ڸ���Ŀ
			CartItem _carItem = map.put(cartItem.getBook().getBid(), cartItem);// ����ԭ��Ŀ��_cartItem��ԭ��Ŀ��ֻ������Ҫ��
			_carItem.setCount(_carItem.getCount() + cartItem.getCount());// ��������Ŀ������Ϊ�����Լ�����+����Ŀ����
			map.put(cartItem.getBook().getBid(), _carItem);
		} else {
			map.put(cartItem.getBook().getBid(), cartItem);
		}
	}

	/**
	 * ���������Ŀ
	 */
	public void clear() {
		map.clear();
	}

	/**
	 * ɾ��ָ����Ŀ
	 */
	public void delete(String bid) {
		map.remove(bid);
	}

	/**
	 * ��ȡ������Ŀ
	 */
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}
