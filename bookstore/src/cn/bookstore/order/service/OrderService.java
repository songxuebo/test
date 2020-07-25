package cn.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.bookstore.order.dao.OrderDao;
import cn.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
	private OrderDao orderDao = new OrderDao();

	/**
	 * ��Ӷ��� ��Ҫ��������
	 */
	public void add(Order order) {
		try {
			// ��������
			JdbcUtils.beginTransaction();

			orderDao.addOrder(order);// ���붩��
			orderDao.addOrderItemList(order.getOrderItemList());// ���붩���е�������Ŀ
			// �ύ����
			JdbcUtils.commitTransaction();
		} catch (Exception e) {
			// �ع�����
			try {
				JdbcUtils.rollbackTransaction();
			} catch (SQLException e1) {

			}
			throw new RuntimeException(e);
		}
	}

	/**
	 * �ҵĶ���
	 */
	public List<Order> myOrders(String uid) {
		return orderDao.findByUid(uid);
	}

	/**
	 * ���ض���
	 */
	public Order load(String oid) {
		return orderDao.load(oid);
	}
	
	/**
	 * ȷ���ջ�
	 */
	public void confirm(String oid) throws OrderException{
		/*
		 * 1.У�鶩��״̬���������3���׳��쳣
		 */
		int state=orderDao.getStateByOid(oid);//��ö���״̬
		if(state!=3)throw new OrderException("����ȷ��ʧ�ܣ�����Υ�棡");
		/*
		 * 2.�޸Ķ���״̬Ϊ4,��ʾ���׳ɹ�
		 */
		orderDao.updateState(oid, 4);
	}
	
	/**
	 * ����
	 */
	public void confirm2(String oid) {
		/*
		 * 1.�޸Ķ���״̬Ϊ3,��ʾ�ѷ���
		 */
		orderDao.updateState(oid, 3);
	}
	
	/**
	 * ֧������
	 */
	public void zhiFu(String oid) {
		/*
		 * 1.��ȡ����״̬
		 *  ���״̬��1����ôִ���������
		 *  ���״̬����1����ô������ʲô������
		 */
		int state=orderDao.getStateByOid(oid);
		if(state==1){
			//�޸Ķ���״̬Ϊ2
			orderDao.updateState(oid, 2);
		}
		
	}
    
	/**
	 * �鿴���ж���
	 */
	public List<Order> findAll() {
		return orderDao.findAll();
	}
  
	/**
	 * ͨ������״̬��ѯ����
	 */
	public List<Order> adoptStateFind(int state) {
		return orderDao.adoptStateFind(state);
	}
}
