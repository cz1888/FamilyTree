package com.ft.common;

import java.util.List;

/**
 * 鍒嗛〉鏁版嵁瀵硅薄
 * 
 * @author zhangQ
 * @created date:2013-8-7
 */
public class Page<T> {

	/** 褰撳墠椤垫暟 */
	private int index = 1;

	/** 鎬昏褰曟暟 */
	private int totalRow;

	/** 姣忛〉鏄剧ず鏉℃暟锛岄粯璁�0鏉�*/
	private int pageSize = 20;

	/** 褰撳墠椤佃捣濮嬭 */
	private int start;

	/** 褰撳墠椤电粨鏉熻 */
	private int end;

	/** 璁板綍鍒楄〃锛屽垎椤垫暟鎹�*/
	private List<T> records;

	/** 鎬婚〉鏁�*/
	private int totalPage;

	/** 鏄剧ず鐨勯〉鐮佸垪琛ㄧ殑寮�绱㈠紩 */
	private int startPageIndex = 1;

	/** 鏄剧ず鐨勯〉鐮佸垪琛ㄧ殑缁撴潫绱㈠紩 */
	private int endPageIndex = 10;

	public Page() {
	}

	public Page(int index, int pageSize) {
		this.pageSize = pageSize;
		this.setIndex(index);
	}

	public Page(int index, int pageSize, List<T> records, int totalRow) {
		this.setIndex(index);
		this.pageSize = pageSize;
		this.records = records;
		this.totalRow = totalRow;
		buildPageIndex();
	}

	public Page(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		if (index < 1) {
			index = 1; // 褰撳墠椤靛皬浜�鐨勬椂鍊欒嚜鍔ㄨ祴鍊间负绗竴椤�
		} else {
			start = pageSize * (index - 1); // 璁＄畻寮�琛屾暟
		}
		end = start + pageSize > totalRow ? totalRow : start + pageSize; // 璁＄畻缁撴潫琛屾暟
		this.index = index;
	}

	public int getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(int totalRow) {
		totalPage = (totalRow + pageSize - 1) / pageSize;
		this.totalRow = totalRow;
		if (totalPage < index) { // 褰撳墠
			totalPage = index;
			/*start = pageSize * (index - 1);
			end = totalRow;*/
		}
		end = start + pageSize > totalRow ? totalRow : start + pageSize;
		buildPageIndex();
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public List<T> getRecords() {
		return records;
	}

	public void setRecords(List<T> records) {
		this.records = records;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}

	public int getStartPageIndex() {
		return startPageIndex;
	}

	public void setStartPageIndex(int startPageIndex) {
		this.startPageIndex = startPageIndex;
	}

	public int getEndPageIndex() {
		return endPageIndex;
	}

	public void setEndPageIndex(int endPageIndex) {
		this.endPageIndex = endPageIndex;
	}
	
	
	/**
	 * 鏄惁杩樻湁涓婁竴椤�
	 * @return
	 */
	public boolean hasPreviousPage() {
		return getIndex() > 0;
	}
	
	
	/**
	 * 鏄惁涓虹涓�〉
	 * @return
	 */
	public boolean isFirstPage() {
		return !hasPreviousPage();
	}
	
	/**
	 * 鏄惁鏈変笅涓�〉
	 * @return
	 */
	public boolean hasNextPage() {
		return getIndex() + 1 < getTotalPage();
	}
	
	
	/**
	 * 鏄惁涓烘渶鍚庝竴椤�
	 * @return
	 */
	public boolean isLastPage() {
		return !hasNextPage();
	}

	/** 鏋勫缓鏄剧ず鐨勯〉鐮佸垪琛ㄧ殑绱㈠紩 */
	private void buildPageIndex() {
		if (totalPage <= 10) { // a, 鎬婚〉鐮佷笉澶т簬10椤�
			startPageIndex = 1;
			endPageIndex = totalPage;
		} else { // b, 鎬荤爜澶т簬10椤�
			// 鍦ㄤ腑闂达紝鏄剧ず鍓嶉潰4涓紝鍚庨潰5涓�
			startPageIndex = index - 4;
			endPageIndex = index + 5;

			// 鍓嶉潰涓嶈冻4涓椂锛屾樉绀哄墠10涓〉鐮�
			if (startPageIndex < 1) {
				startPageIndex = 1;
				endPageIndex = 10;
			} else if (endPageIndex > totalPage) {// 鍚庨潰涓嶈冻5涓椂锛屾樉绀哄悗10涓〉鐮�
				endPageIndex = totalPage;
				startPageIndex = totalPage - 10 + 1;
			}
		}
	}
}
