package cn.bookstore.order.dao;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//import javax.enterprise.inject.New;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.bookstore.book.domain.Book;
import cn.bookstore.order.domain.Order;
import cn.bookstore.order.domain.OrderItem;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;

public class OrderDao {
	private QueryRunner qr = new TxQueryRunner();

	/**
	 * ��Ӷ���
	 */
	public void addOrder(Order order) {
		try {
			String sql = "insert into orders values(?,?,?,?,?,?)";
			/*
			 * ����util��Dateת����sql��Timestamp
			 */
			Timestamp timestamp = new Timestamp(order.getOrdertime().getTime());
			Object[] params = { order.getOid(), timestamp,order.getTotal()
					,order.getState(),order.getOwner().getUid(), order.getAddress() };
			qr.update(sql, params);

		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ���붩����Ŀ
	 */
	public void addOrderItemList(List<OrderItem> orderItemList) {
		/**
		 * QueryRunner���е�batch(String sql,Object[][] params) ����params�Ƕ��һά���飡
		 * ÿ��һά���鶼��sql��һ��ִ��һ�Σ����һά�����ִ�ж��
		 */
		try {
			String sql = "insert into orderitem values(?,?,?,?,?)";
			/*
			 * ��orderItemListת���ɶ�ά���� 
			 *  ��һ��OrderItem����ת����һ��һά����
			 */
			Object[][] params = new Object[orderItemList.size()][];
			// ѭ������orderItemList,ʹ��ÿ��orderItem����Ϊparams��ÿ��һά���鸳ֵ
			for (int i = 0; i < orderItemList.size(); i++) {
				OrderItem item = orderItemList.get(i);
				params[i] = new Object[] { item.getIid(),item.getCount(),item.getSubtotal(),
						item.getOrder().getOid(), item.getBook().getBid() };
			}
			qr.batch(sql, params);//ִ��������
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * ��uid��ѯ����
	 */
	public List<Order> findByUid(String uid) {
		/*
		 * 1.ͨ��uid��ѯ����ǰ�û�������List<Order>
		 * 2.ѭ������ÿ��Order,Ϊ�������������OrderItem
		 */
		try {
			/*
			 * 1.�õ���ǰ�û����ж���
			 */
			String sql="select * from orders where uid=?";
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class),uid);
			/*
			 * 2.ѭ������ÿ��Order��Ϊ��������Լ����еĶ�����Ŀ
			 */
			for(Order order :orderList){
				loadOrderItems(order);//Ϊorder��������������ж�����Ŀ
			}
			/*
			 * 3.���ض����б�
			 */
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
    
	/**
	 * ����ָ���Ķ������еĶ�����Ŀ
	 * @throws SQLException 
	 */
	private void loadOrderItems(Order order) throws SQLException {
		/*
		 * ��ѯ���ű�orderitem,book
		 */
		String sql="select * from orderitem i, book b where i.bid=b.bid and oid=?";
		/*
		 * ��Ϊһ�н������Ӧ�Ĳ�����һ��javabean�����Բ�����ʹ��BeanListHandler,����MapListHandler
		 */
		List<Map<String,Object>> mapList=qr.query(sql, new MapListHandler(),order.getOid());
		/*
		 * mapList�Ƕ��map��ÿ��map��Ӧһ�н����
		 * ������Ҫʹ��һ��Map������������OrderItem��Book,Ȼ���ٽ������ߵĹ�ϵ����Book���ø�OrderItem��
		 */
	/*
	 * ѭ������ÿ��Map,ʹ��map������������Ȼ������ϵ�����ս��һ��OrderItem��,��OrderItem����
	 */
		List<OrderItem> orderItemList=toOrderItemList(mapList);
		order.setOrderItemList(orderItemList);
		
	}
    /**
     * ��mapList��ÿ��Mapת�����������󣬲�������ϵ
     */
	private List<OrderItem> toOrderItemList(List<Map<String, Object>> mapList) {
	      List<OrderItem> orderItemList=new ArrayList<OrderItem>();
	      for(Map<String,Object>map : mapList){
	    	  OrderItem item=toOrderItem(map);
	    	  orderItemList.add(item);
	      }
	      return orderItemList;
	}
    /**
     * ��һ��Mapת����һ��OrderItem����
     */
	private OrderItem toOrderItem(Map<String, Object> map) {
		OrderItem orderItem=CommonUtils.toBean(map, OrderItem.class);
		Book book=CommonUtils.toBean(map, Book.class);
		orderItem.setBook(book);
		return orderItem;
	}

	/**
	 * ���ض���
	 */
	public Order load(String oid) {
		try {
			/*
			 * �õ���ǰ�û����ж���
			 */
			String sql="select * from orders where oid=?";
			Order order=qr.query(sql, new BeanHandler<Order>(Order.class),oid);
		    /*
		     * Ϊorder��������������Ŀ
		     */
			loadOrderItems(order);
			/*
			 * ���ض����б�
			 */
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * ͨ��oid��ѯ����״̬
	 */
	public int getStateByOid(String oid) {
		try {
		    String sql="select state from orders where oid=? ";
		    return (Integer)qr.query(sql, new ScalarHandler(),oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * �޸Ķ���״̬
	 */
	public void updateState(String oid,int state) {
		try {
		    String sql="update orders set state=? where oid=?";
		    qr.update(sql, state, oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
   
  /**
   * ��ѯ���ж���
   */
	public List<Order> findAll() {
		try {
			String sql="select * from orders ";
			List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class));
			/*
			 * 2.ѭ������ÿ��Order��Ϊ��������Լ����еĶ�����Ŀ
			 */
			for(Order order :orderList){
				loadOrderItems(order);//Ϊorder��������������ж�����Ŀ
			}
			/*
			 * 3.���ض����б�
			 */
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	  /**
	   * ͨ������״̬��ѯ����
	   */
		public List<Order> adoptStateFind(int state) {
			try {
				String sql="select * from orders where state=?  ";
				List<Order> orderList=qr.query(sql, new BeanListHandler<Order>(Order.class),state);
				/*
				 * 2.ѭ������ÿ��Order��Ϊ��������Լ����еĶ�����Ŀ
				 */
				for(Order order :orderList){
					loadOrderItems(order);//Ϊorder��������������ж�����Ŀ
				}
				/*
				 * 3.���ض����б�
				 */
				return orderList;
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		}
}
