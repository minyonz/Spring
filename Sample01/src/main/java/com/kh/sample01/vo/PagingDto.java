package com.kh.sample01.vo;

// 페이지 관련된 작업 처리할 페이지
public class PagingDto {
	// 현재페이지
	private int page = 1;
	
	// 1~10까지 표현(화면에 표시 될 게시물 갯수. 여기선 10개)
	private int startRow = 1;
	private int endRow = 10;
	private int count;
	
	// 다음페이지, 이전페이지를 사용하기 위함
	private int startPage;
	private int endPage;
	
	// 총 페이지 수를 구해서 마지막 페이지를 나타내기 위함
	private int totalPage;
	
	// 한번에 볼 목록의 개수(5줄, 10줄..)
	private int perPage = 10;
	
	private final int PAGE_BLOCK = 10;
	
	// 검색
	private String searchType;
	private String keyword;
	
	public PagingDto() {
		super();
	}
	
	public PagingDto(int page) {
		this.page = page;
	}
	
	public PagingDto(int page, int startRow, int endRow, int count) {
		super();
		this.page = page;
		this.startRow = startRow;
		this.endRow = endRow;
		this.count = count;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
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
	public int getCount() {
		return count;
	}
	// 컨트롤러에서 setCount값 받아와서 밑에 것도 설정
	public void setCount(int count) {
		this.count = count;
		
		this.endRow = page * this.perPage;
		this.startRow = endRow - (this.perPage - 1);
		this.endPage = ((int)((page - 1) / PAGE_BLOCK) + 1) * PAGE_BLOCK;
		this.startPage = endPage - 9;
		
		this.totalPage = count / perPage;
		if (count % perPage != 0) {
			this.totalPage += 1;
		}
		// 끝 페이지가 최대 페이지를 넘지 않도록 수식설정
		if (this.endPage > this.totalPage) {
			this.endPage = this.totalPage;
		}
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getPerPage() {
		return perPage;
	}

	public void setPerPage(int perPage) {
		this.perPage = perPage;
	}

	public String getSearchType() {
		return searchType;
	}

	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "PagingDto [page=" + page + ", startRow=" + startRow + ", endRow=" + endRow + ", count=" + count
				+ ", startPage=" + startPage + ", endPage=" + endPage + ", totalPage=" + totalPage + ", perPage="
				+ perPage + ", PAGE_BLOCK=" + PAGE_BLOCK + ", searchType=" + searchType + ", keyword=" + keyword + "]";
	}
}
