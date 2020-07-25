package cn.bookstore.book.service;

import java.util.List;

import cn.bookstore.book.dao.BookDao;
import cn.bookstore.book.domain.Book;

public class BookService {
	private BookDao bookDao = new BookDao();

	public List<Book> findAll() {
		return bookDao.findAll();
	}

	public List<Book> findByCategory(String cid) {

		return bookDao.findByCategory(cid);
	}

	public Book load(String bid) {
		return bookDao.findByBid(bid);
	}
   
	/**
	 * ÃÌº”Õº È
	 */
	public void add(Book book) {
		bookDao.add(book);
		
	}
	
	/**
	 * …æ≥˝Õº È
	 */
	public void delete(String bid) {
		bookDao.delete(bid);
		
	}
    /**
     * ±‡º≠Õº È
     */
	public void edit(Book book) {
		bookDao.edit(book);
		
	}
}
