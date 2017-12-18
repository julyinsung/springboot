package mysample.boot.security.domain.common;

import java.util.List;

/**
 * @작성일   : 2013. 5. 22.
 * @작성자   : sulmin
 * @Class 설명 : 공통 List VO
 */
public class CommonListVO extends BaseVO  {

	private static final long serialVersionUID = 4733507491516652929L;

	/**
	 * 조회된 결과 리스트
	 */
	private List lists = null;
	/**
	 * 조회 기준 시간
	 */
	private String baseTime = null;

	/**
	 * 현재 페이지
	 * ex) 현재 페이지는 1부터 시작한다.
	 */
	private int nowPage = 1;
	/**
	 * 현재 첫번째 row의 넘버
	 */
	private int startNum = 0;
	/**
	 * 게시판 총 페이지
	 */
	private int totalCount = 0;
	/**
	 * 전체 합계
	 */
	private int totalSum = 0;
	/**
	 * 페이지당 보여줄 리스트 갯수
	 */
	private int countPerPage = 0;
	/**
	 * paging page 숫자의 블록 카운트
	 */
	private int blockCount = 10;
	/**
	 * 총 페이지 개수
	 */
	private int totalPage = 0;
	/**
	 * 에러 처리용
	 */

	private String deviceImgUrl;
	private String device_img_link;

	private String errorCode = "";
	private String errorMsg = "";
	private String tab = "";
	public List getLists() {
		return lists;
	}
	public void setLists(List lists) {
		this.lists = lists;
	}
	public String getBaseTime() {
		return baseTime;
	}
	public void setBaseTime(String baseTime) {
		this.baseTime = baseTime;
	}
	public int getNowPage() {
		return nowPage;
	}
	public void setNowPage(int nowPage) {
		this.nowPage = nowPage;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalSum() {
		return totalSum;
	}
	public void setTotalSum(int totalSum) {
		this.totalSum = totalSum;
	}
	public int getCountPerPage() {
		return countPerPage;
	}
	public void setCountPerPage(int countPerPage) {
		this.countPerPage = countPerPage;
	}
	public int getBlockCount() {
		return blockCount;
	}
	public void setBlockCount(int blockCount) {
		this.blockCount = blockCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getTab() {
		return tab;
	}
	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getDeviceImgUrl() {
		return deviceImgUrl;
	}
	public void setDeviceImgUrl(String deviceImgUrl) {
		this.deviceImgUrl = deviceImgUrl;
	}
	public String getDevice_img_link() {
		return device_img_link;
	}
	public void setDevice_img_link(String device_img_link) {
		this.device_img_link = device_img_link;
	}

	public String getDevice_img_full_link() {
		return this.getDeviceImgUrl() + this.getDevice_img_link();
	}



	@Override
	public String toString() {
		return "CommonListVO [lists=" + lists + ", baseTime=" + baseTime
				+ ", nowPage=" + nowPage + ", startNum=" + startNum
				+ ", totalCount=" + totalCount + ", totalSum=" + totalSum
				+ ", deviceImgUrl=" + deviceImgUrl + ", device_img_link=" + device_img_link
				+ ", countPerPage=" + countPerPage + ", blockCount="
				+ blockCount + ", totalPage=" + totalPage + ", errorCode="
				+ errorCode + ", errorMsg=" + errorMsg + ", tab=" + tab + "]";
	}


}
