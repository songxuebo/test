package cn.bookstore.category.service;

import java.util.List;

import cn.bookstore.book.dao.BookDao;
import cn.bookstore.category.dao.CategoryDao;
import cn.bookstore.category.domain.Category;
import cn.bookstore.category.web.servlet.admin.CategoryException;

public class CategoryService {
    private CategoryDao categoryDao=new CategoryDao();
    private BookDao bookDao=new BookDao();
   
    /**
     * ��ѯ���з���
     */
	public List<Category> findAll() {
		return categoryDao.findAll();
	}
   
	/**
	 * ��ӷ���
	 */
	public void add(Category category) {
		categoryDao.add(category);
		
	}
   /**
    * ɾ������
 * @throws CategoryException 
    */
	public void delete(String cid) throws CategoryException {
		//��ȡ�÷�����ͼ��ı���
		int count=bookDao.getCountByCid(cid);
		//����÷����´���ͼ�飬����ɾ���������׳��쳣
		if(count>0) throw new CategoryException("�÷����»���ͼ�飬����ɾ����");
		//ɾ���÷���
		categoryDao.delete(cid);
		
	}
   /**
    * ���ط���
    */
public Category load(String cid) {
	return categoryDao.load(cid);
}
  /**
   * �޸ķ���
   */
public  void edit(Category category) {
	categoryDao.edit(category);
	
}
}
