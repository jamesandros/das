package com.outwit.das.common.page;

import java.util.List;

/**
 * 由于Page对象在hession序列化过程中有问题，从而在使用分页功能时，需要分会MyPage对象
 * @author coderyu
 *
 * @param <T>
 */
public class MyPage<T> implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3506543208996721059L;
	
    private int pageNum;
    private int pageSize;
    private int startRow;
    private int endRow;
    private long total;
    private int pages;
    private List<T> result;
    
    public MyPage(int pageNum, int pageSize){
    	this.pageNum = pageNum;
    	this.pageSize = pageSize;
    }
    
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartRow() {
		return startRow;
	}
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public int getPages() {
		return pages;
	}
	public void setPages(int pages) {
		this.pages = pages;
	}
	public List<T> getResult() {
		return result;
	}
	public void setResult(List<T> result) {
		this.result = result;
	}
    
	public MyPage<T> convertToMyPage(Page<T> page){
		this.pageNum = page.getPageNum();
		this.pageSize = page.getPageSize();
		this.startRow = page.getStartRow();
		this.endRow = page.getEndRow();
		this.total = page.getTotal();
		this.pages = page.getPages();
		this.result = page.getResult();
		return this;
	}
   
}
