package mysample.boot.security.util;

import java.io.File;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.TimeZone;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.ValidationException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import mysample.boot.security.domain.common.CommonListVO;


/**
 * @작성일   : 2013. 5. 21. 
 * @작성자   : sulmin
 * @Class 설명 : 공통 함수
 */
@Component
public class CommonUtil {
	
	private static final Log logger = LogFactory.getLog(CommonUtil.class);
	
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat(CommonUtil.DATE_TIME_FORMAT_STRING);
	private static SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
	public static final String DATE_TIME_FORMAT_STRING = "yyyyMMddHHmmss";
	public static final String DATE_TIME_FORMAT_STRING_WITH_SPACE = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_TIME_FORMAT_STRING_HH_MM = "yyyy.MM.dd HH:mm";
	
	private static CookieLocaleResolver cookieLocaleResolver;
	private static MessageSource messageSource;
	
	public static final int COUNT_PER_PAGE_BOX = 15;
	public static final int COUNT_PER_PAGE_COMMON = 10;
	public static final int COUNT_PER_PAGE_SMALL = 10;
	public static final int COUNT_PER_PAGE_SEARCH = 20;
	public static final int COUNT_PER_PAGE_SEARCHAPP = 12;
	public static final int COUNT_PER_PAGE_POPUP = 10;
	public static final int COUNT_PER_PAGE_RESOURCE = 6;
	public static final int COUNT_PER_PAGE_START = 8;
	public static final int COUNT_PER_PAGE_DEVICE_IN_APPS = 10;
	public static final int COUNT_PER_PAGE_STAT_IN_TOPIC = 3;
	public static final int COUNT_PER_PAGE_MAIN_CASESTUDY = 3;
	public static final int COUNT_PER_PAGE_MAIN_BLOG = 3;
	public static final String MAIN_BLOG_TAG = "MAIN_BLOG";
	public static final String MAIN_NOTICE_TAG = "S15001";
	public static final int PAGE_BLOCK_COUNT = 10;
	public static final int PAGE_BLOCK_COUNT_SMALL = 5;
	public static final int SAVE_ID_MAX_AGE = 60 * 60 * 24 * 365;//1년
	public static final String GLOBAL_PUBLIC_DASH_MENU = "M010006001";
	public static final String GLOBAL_PUBLIC_TOPIC_CODE = "T010000001";
	public static final String FTP_SERVER_ID = "F020000002";
	public static final String REDIRECT_TO_ERROR_URL = "/IoTPortal/main/showError";
	public static final String REDIRECT_TO_URL = "/IoTPortal/main/redirectToPage";
	public static final String REDIRECT_TO_FUNCTION_PAGE = "iot/common/redirectToFunction";
	public static final String REDIRECT_TO_PAGE = "iot/common/redirectToPage";
	public static final String REDIRECT_TO_ERROR = "iot/common/showError";
	public static final String PPLATFORM_ID = "S010000001";
	
	public static final String LINE_SEPERATOR = "\n";
	
	private static String CertEngineCurrent = "1";
	private static String searchEngineCurrent = "1";
	private static String platformAPICurrent = "1";
	
	private static String sso_remove_key = "";
	public static final HashMap<String,String> cert_types 
	= new HashMap<String,String>(){
		private static final long serialVersionUID = 6261806771750977413L;

	{
		put("device","K01001");
		put("user","K01002");
		put("server","K01003");
	}};	

	public static final HashMap<String,String> user_types 
	= new HashMap<String,String>(){
		private static final long serialVersionUID = -2166035170625230830L;

	{
		put("b2c","U02001");
		put("b2b","U02002");
		put("all","U02003");
	}};
	
	//TMAS 암.복호화 Key
	private static final String skeyString = "31feef6ae9743338cbed6170bd0f5e14";
	//TMAS 암.복호화 Key
	private static final String ivString = "3577b9392eadad680209e17a0b08dba5";
	
	//Long transaction 제한 처리
	public static HashMap<String , Integer> mapLongTrans = new HashMap<String , Integer>();
	
	public static CookieLocaleResolver getCookieLocaleResolver() {
		return cookieLocaleResolver;
	}
	public static void setCookieLocaleResolver(
			CookieLocaleResolver cookieLocaleResolver) {
		CommonUtil.cookieLocaleResolver = cookieLocaleResolver;
	}
	public static MessageSource getMessageSource() {
		return messageSource;
	}
	public static void setMessageSource(MessageSource messageSource) {
		CommonUtil.messageSource = messageSource;
	}
	/**
	 * @작성일   : 2013. 7. 31. 
	 * @작성자   : sulmin
	 * @Method 설명 : request 정보 추출
	 * @return
	 */
	public static HttpServletRequest getRequest() {
		if(! isEmpty(RequestContextHolder.getRequestAttributes())){
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
			return request;
		} else{
			return null;
		}
	}
	public static HttpSession getSession() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
		return request.getSession(true);
	}
	
	/**
	 * @작성일   : 2013. 8. 6. 
	 * @작성자   : Noh
	 * @Method 설명 : 새로운 패스워드 생성
	 * @return
	 */
	public static String getNewPasswordJen(){
		Random random = new Random();
		String iGen = 100000 + random.nextInt(999999) + "";
		return iGen;
	}
	
	/**
	 * @작성일   : 2013. 5. 30. 
	 * @작성자   : sulmin
	 * @Method 설명 : Session의 Locale 설정 변경
	 * @param sLocale
	 * @param request
	 * @return
	 */
	public static boolean setLocale(String sLocale) {
	  HttpServletRequest request = getRequest();
	  Locale locale = null;
	  try {
		  logger.debug("###########################################");
		  if ("ko".equals(sLocale)) {
			  locale = Locale.KOREAN;
		  } else if ("en".equals(sLocale)) {
			  locale = Locale.ENGLISH;
//		  } else if ("zh".equals(sLocale)) {
//			  locale = Locale.CHINESE;
		  } else {
			  locale = request.getLocale();
		  }
		  HttpSession session = request.getSession(true);
		  session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
		  

	  } catch (Exception ex){
		  return false;
	  }
	  return true;
	 }
	
	/**
	 * @작성일   : 2013. 6. 12. 
	 * @작성자   : sulmin
	 * @Method 설명 : 현재 설정된 Locale 조회
	 * @param request
	 * @return
	 */
	public static Locale getLocale(){
		HttpServletRequest request = getRequest();
		if(isEmpty(request)){
			return Locale.KOREAN;
		}
		HttpSession session = request.getSession(false);
		Locale locale = null;
		if(session != null){
			locale = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
			if(locale != null){
				logger.debug("######################################");
				logger.debug("Locale From Session");
				logger.debug("######################################");
			}
		} 
		if(locale == null){
			locale = cookieLocaleResolver.resolveLocale(request);
			if(locale != null){
				logger.debug("######################################");
				logger.debug("Locale From Cookie");
				logger.debug("######################################");
				if(session != null){
					session.setAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME, locale);
				}
			}
		}
		
		return locale;
	}
	/**
	 * @작성일   : 2013. 8. 8. 
	 * @작성자   : sulmin
	 * @Method 설명 : 설정된 Locale String으로 변환
	 * @return
	 */
	public static String getLocaleString(){
		String sLocale = "";
		Locale locale = getLocale();
		
		if(locale == null){	//2014.10.28 수정
			sLocale = "ko";
		} else {
			if(locale.getLanguage().equals(Locale.KOREAN.getLanguage())){
				sLocale = "ko";
			} else if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())){
				sLocale = "en";
//			} else if (locale.getLanguage().equals(Locale.CHINESE.getLanguage())){
//				sLocale = "zh";
			} else {
				sLocale = "ko";
			}
		}
		
		return sLocale;
	}
	/**
	 * @작성일   : 2013. 8. 8. 
	 * @작성자   : sulmin
	 * @Method 설명 : request에서 Locale 추출 - 타 시스템 연동용
	 * @param request
	 * @return
	 */
	public static Locale getLocale(HttpServletRequest request){
		HttpSession session = request.getSession(false);
		Locale locale = null;
		if(session != null){
			locale = (Locale)session.getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);
			if(locale != null){
				logger.debug("######################################");
				logger.debug("Locale From Session");
				logger.debug("######################################");
			}
		} 
		if(locale == null){
			locale = cookieLocaleResolver.resolveLocale(request);
			if(locale != null){
				logger.debug("######################################");
				logger.debug("Locale From Cookie");
				logger.debug("######################################");
			}
		}
		
		return locale;
	}
	/**
	 * @작성일   : 2013. 8. 8. 
	 * @작성자   : sulmin
	 * @Method 설명 : 설정된 Locale String으로 변환 - 타 시스템 연동용
	 * @return
	 */
	public static String getLocaleString(HttpServletRequest request){
		String sLocale = "";
		Locale locale = getLocale(request);

		if(locale == null){	//2014.10.28 수정
			sLocale = "ko";
		} else {
			if(locale.getLanguage().equals(Locale.KOREAN.getLanguage())){
				sLocale = "ko";
			} else if (locale.getLanguage().equals(Locale.ENGLISH.getLanguage())){
				sLocale = "en";
//			} else if (locale.getLanguage().equals(Locale.CHINESE.getLanguage())){
//				sLocale = "zh";
			} else {
				sLocale = "ko";
			}
		}
		
		return sLocale;
	}
	
	/**
	 * @작성일   : 2013. 8. 8.
	 * @작성자   : sulmin
	 * @Method 설명 : 메세지 코드로 Locale에 따라 메세지 조회 - 타시스템 연동용
	 * @param sMsgCode
	 * @param request
	 * @return
	 */
	public static String getMessage (String sMsgCode, HttpServletRequest request) {
		Locale locale = getLocale(request);
		if(locale == null){
			locale = Locale.KOREAN;
		}
		String sMessage = "";
		sMessage = messageSource.getMessage(sMsgCode, null, locale);
		return sMessage;
	}
	public static String getMessage (String sMsgCode, String sLocale) {
		Locale locale = Locale.KOREAN;
		if(! isEmpty(sLocale)){
			sLocale = sLocale.toLowerCase();
			if(! "ko".equals(sLocale)){
				if(sLocale.equals("en")){
					locale = Locale.ENGLISH;
//				} else if (sLocale.equals("zh")){
//					locale = Locale.CHINESE;
				}
			}
		}
		String sMessage = "";
		sMessage = messageSource.getMessage(sMsgCode, null, locale);

		return sMessage;
	}
	public static String getMessage (String sMsgCode) {
		Locale locale = getLocale();
		String sMessage = "";
		sMessage = messageSource.getMessage(sMsgCode, null, locale);
		return sMessage;
	}
	
	public static String getMessage (String sMsgCode, Object[] arr) {
		Locale locale = getLocale();
		String sMessage = "";
		sMessage = messageSource.getMessage(sMsgCode, arr, locale);
		
		return sMessage;
	}

	/**
	 * @작성일   : 2013. 6. 4. 
	 * @작성자   : sulmin
	 * @Method 설명 : 리스트페이징 객체 셋팅 - 페이지당 갯수 미지정(default)
	 * @param oListVO
	 * @param iNowPage
	 * @return
	 */
	public static CommonListVO setPaging (CommonListVO oListVO,
										int iNowPage
										) {
		int iCountPerPage = CommonUtil.COUNT_PER_PAGE_COMMON;
		
		return setPaging(oListVO, iNowPage, iCountPerPage);
	}
	
	/**
	 * @작성일   : 2013. 6. 4. 
	 * @작성자   : sulmin
	 * @Method 설명 : 리스트페이징 객체 셋팅 - 페이지당 갯수 지정
	 * @param oListVO
	 * @param iNowPage
	 * @param iCountPerPage
	 * @return
	 */
	public static CommonListVO setPaging (CommonListVO oListVO,
											int iNowPage,
											int iCountPerPage) {
		if(iCountPerPage == 0){
			iCountPerPage = CommonUtil.COUNT_PER_PAGE_COMMON;
		}
		//페이지 설정
		
		int iTotalCount = oListVO.getTotalCount();
		int iTotalPage = (int) Math.ceil((double)iTotalCount / (double)iCountPerPage);
		
		if(iNowPage >= iTotalPage){
			iNowPage = iTotalPage;
		}
		
		int iStartNum = (iNowPage - 1) * iCountPerPage;
		
		oListVO.setNowPage(iNowPage);
		oListVO.setStartNum(iStartNum);
		oListVO.setCountPerPage(iCountPerPage);
		oListVO.setTotalPage(iTotalPage);
	
		return oListVO;
	}
	/**
	 * @작성일   : 2015. 4. 28. 
	 * @작성자   : sulmin
	 * @Method 설명 : 리스트페이징 객체 셋팅 - 페이지당 갯수 미지정(default)
	 * @param oListVO
	 * @param iNowPage
	 * @return
	 */
	public static CommonListVO setPagingBox (CommonListVO oListVO,
										int iNowPage
										) {
		int iCountPerPage = CommonUtil.COUNT_PER_PAGE_BOX;
		
		return setPaging(oListVO, iNowPage, iCountPerPage);
	}
	public static CommonListVO setPagingBox (CommonListVO oListVO,
					int iNowPage,
					int iCountPerPage) {
		if(iCountPerPage == 0){
			iCountPerPage = CommonUtil.COUNT_PER_PAGE_BOX;
		}
		//페이지 설정
		
		int iTotalCount = oListVO.getTotalCount();
		int iTotalPage = (int) Math.ceil((double)iTotalCount / (double)iCountPerPage);
		
		if(iNowPage >= iTotalPage){
			iNowPage = iTotalPage;
		}
		
		int iStartNum = (iNowPage - 1) * iCountPerPage;
		
		oListVO.setNowPage(iNowPage);
		oListVO.setStartNum(iStartNum);
		oListVO.setCountPerPage(iCountPerPage);
		oListVO.setTotalPage(iTotalPage);
		
		return oListVO;
	}
	public static boolean isEmpty(String str){
		if(str == null || str.isEmpty())
			return true;
		
		return false;
	}
	
	public static boolean isEmpty(Object obj){
		if(obj == null)
			return true;
		
		return false;
	}
	
	public static boolean isEmpty(Object[] obj){
		if(obj == null || obj.length == 0)
			return true;
		
		return false;
	}
	
	/**
	 * @작성일   : 2013. 7. 2. 
	 * @작성자   : sulmin
	 * @Method 설명 : Null 값일 경우 대체 문자 처리
	 * @param sTarget
	 * @param sReplace
	 * @return
	 */
	public static String nvl(String sTarget, String sReplace){
		if(sTarget == null || sTarget.isEmpty()){
			return sReplace;
		}
		return sTarget;
	}
	public static int nvl(int iTarget, int iReplace){
		if(isEmpty(iTarget)){
			return iReplace;
		}
		return iTarget;
	}
	
	/**
	 * @작성일   : 2013. 7. 4. 
	 * @작성자   : dckim
	 * @Method 설명 : obj to xml
	 * @param Class, Object
	 * @return
	 */
	public static void objToXml(Class c, Object o) {
		try {
			JAXBContext context = JAXBContext.newInstance(c);

			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(o, System.out);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @작성일   : 2015. 9. 14. 
	 * @작성자   : sulmin
	 * @Method 설명 : Object를 xml로 변환
	 * @param provisioning
	 * @return
	 */

	public static Object xmlFileToObj(Class c ,File fileXML) {
		Object oReturn = null;
		try {
			JAXBContext jaxbContext = JAXBContext.newInstance(c);
			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
			oReturn = unmarshaller.unmarshal(fileXML);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oReturn;
	}
	/**
	 * @작성일   : 2013. 9. 30. 
	 * @작성자   : sulmin
	 * @Method 설명 : 특정 페이지로 rdirect
	 * @param sTargetUrl
	 * @param model
	 * @param request
	 * @return
	 */
	public static String getRedirectToPage( String sTargetUrl,
											Model model) {
		
		Set<Entry<String,Object>> setParam = model.asMap().entrySet();
		Iterator<Entry<String,Object>> iterParam = setParam.iterator(); 
		Map<String, Object> mapParam = new HashMap<String, Object>();
		while(iterParam.hasNext()){
			Entry<String,Object> entryParam = iterParam.next();
			if(entryParam.getKey().indexOf("VO") >= 0){
				Map<String, Object> mapVO = ConverObjectToMap(entryParam.getValue());
				mapParam.putAll(mapVO);
			} else {
				mapParam.put(entryParam.getKey(), entryParam.getValue());
			}
		}
		model.addAttribute("redirectURL", sTargetUrl);
		model.addAttribute("parameterMap", mapParam);
		return CommonUtil.REDIRECT_TO_PAGE;// "pp/common/redirectToPage"
	}
	/**
	 * @작성일   : 2013. 9. 30. 
	 * @작성자   : sulmin
	 * @Method 설명 : VO를 Map으로 변환
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> ConverObjectToMap(Object obj){
		try {
			Field[] fields = obj.getClass().getDeclaredFields();
			Map<String, Object> resultMap = new HashMap<String, Object>();
			for(int i=0; i<=fields.length-1;i++){
				fields[i].setAccessible(true);
				resultMap.put(fields[i].getName(), fields[i].get(obj));
			}
			return resultMap;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void clearLogInInfoCookie(HttpServletRequest request){
		
		Cookie[] cookies = request.getCookies();
		if(cookies != null){
			for(int i = 0; i < cookies.length; i++){
				if(cookies[i].getName().equals("login_user_id")){
					cookies[i].setValue("");
				}
				if(cookies[i].getName().equals("login_user_Type")){
					cookies[i].setValue("");
				}
			}
		}
	}
	
	/**
	 * @작성일   : 2014. 7. 9. 
	 * @작성자   : sulmin
	 * @Method 설명 : 오류메세지 화면에 전달
	 * @param model
	 * @param sErrorMsgCode
	 */
	public static void setResultMsgByCode(Model model, String sErrorMsgCode){
		model.addAttribute("errorCode", "error");		
		model.addAttribute("errorMsg", getMessage(sErrorMsgCode));
	}
	
	/**
	 * @작성일   : 2014. 7. 9. 
	 * @작성자   : sulmin
	 * @Method 설명 : 오류 메세지 화면에 전달
	 * @param model
	 * @param sErrorCode
	 * @param sErrorMsg
	 */
	public static void setResultMsgByCode(Model model, String sErrorCode, String sErrorMsgCode){
		model.addAttribute("errorCode", sErrorCode);		
		model.addAttribute("errorMsg", getMessage(sErrorMsgCode));
	}
	
	/**
	 * @작성일   : 2014. 7. 9. 
	 * @작성자   : sulmin
	 * @Method 설명 : 오류 메세지 화면에 전달
	 * @param model
	 * @param sErrorCode
	 * @param sErrorMsg
	 */
	public static void setResultMsg(Model model, String sErrorCode, String sErrorMsg){
		model.addAttribute("errorCode", sErrorCode);		
		model.addAttribute("errorMsg", sErrorMsg);
	}
	
	
	  
	public static String getTime(){
		return timeFormat.format(new Date());
	}
	  
	public static String getDate(){
		return dateFormat.format(new Date());
	}
	  
	public static String getDateTime(){
		return dateTimeFormat.format(new Date());
	}
	
	/**
	 * @작성일   : 2014. 6. 26. 
	 * @작성자   : LEE YONG JU
	 * @Method 설명 : 현재 날짜 정보
	 * @return
	 */
	public static String getNow(){
		String now = "";
		
		SimpleDateFormat simpleFomat = new SimpleDateFormat(DATE_TIME_FORMAT_STRING);
		Date currentTime = new Date();
		now = simpleFomat.format(currentTime);
		
		return now;
	}
	
	/**
	 * @작성일   : 2014. 6. 26. 
	 * @작성자   : LEE YONG JU
	 * @Method 설명 : 포멧터를 적용한 현재 날짜    
	 * @param format
	 * @return
	 */
	public static String getNow(String format){
		String now = "";
		
		SimpleDateFormat simpleFomat = new SimpleDateFormat(format);
		Date currentTime = new Date();
		now = simpleFomat.format(currentTime);
		
		return now;
	}
	
	/**
	 * @작성일	: 2014. 9. 2.
	 * @작성자	: Noh
	 * @Method 설명 : days 만큼 이동 날짜 조회
	 * @param days (음수, 양수)
	 * @return
	 */
	public static String getMoveDateByDays(int days) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMdd");
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.DAY_OF_MONTH, days);
		
		return sdformat.format(cal.getTime());
	}
	
	/**
	 * @작성일	: 2015. 6. 30.
	 * @작성자	: 이상준
	 * @Method 설명 : second 만큼 이동 날짜 조회
	 * @param seconds (음수, 양수)
	 * @return
	 */
	public static String getMoveDateBySeconds(int seconds) {
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.SECOND, seconds);
		
		return sdformat.format(cal.getTime());
	}
	
	public static String getTodayUploadUrl()
	{
		// 오늘 날짜를 가져온다.
		String uploadUrl = new SimpleDateFormat("/yyyy/MMdd/").format(Calendar.getInstance().getTime());
		return uploadUrl;
	}
	
	/**
	 * @작성일   : 2013. 10. 30. 
	 * @작성자   : yjpark
	 * @설명   	 : 객체를 스트링으로 마샬
	 * @Method 설명 : * @param obj
	 * @Method 설명 : * @return
	 */
	public static String marshalToXmlString(Object obj){
		StringWriter sw = new StringWriter();
		
		try {
			
			JAXBContext context = JAXBContext.newInstance(obj.getClass());
			Marshaller msh = context.createMarshaller();
			msh.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
			msh.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			msh.marshal(obj, sw);
		} catch (Exception e) {
			return null;
		}
		
		return sw.toString();
	}
	
    /**
     * <p>XXX - XXX- XXXX 형식의 전화번호 앞, 중간, 뒤 문자열 3개 입력 받아 유요한 전화번호형식인지 검사.</p>
     * 
     * 
     * @param   전화번호 문자열( 3개 )
     * @return  유효한 전화번호 형식인지 여부 (True/False)
     */
    public static boolean checkFormatTell(String tell1, String tell2, String tell3) {
                 
         String[] check = {"02", "031", "032", "033", "041", "042", "043", "051", "052", "053", "054", "055", "061", 
                                 "062", "063", "070", "080", "0505"};   //존재하는 국번 데이터
         String temp = tell1 + tell2 + tell3;
                 
         for(int i=0; i < temp.length(); i++){
                if (temp.charAt(i) < '0' || temp.charAt(i) > '9')       
                        return false;                   
         }      //숫자가 아닌 값이 들어왔는지를 확인
                 
         for(int i = 0; i < check.length; i++){
                 if(tell1.equals(check[i])) break;                       
                 if(i == check.length - 1) return false;
         }      //국번입력이 제대로 되었는지를 확인
                 
         if(tell2.charAt(0) == '0') return false; 
                
         if(tell1.equals("02")){
                 if(tell2.length() != 3 && tell2.length() !=4) return false;
                 if(tell3.length() != 4) return false;  //서울지역(02)국번 입력때의 전화 번호 형식유효성 체크                         
         }else{
                 if(tell2.length() != 3) return false;
                 if(tell3.length() != 4) return false;
         }      //서울을 제외한 지역(국번 입력때의 전화 번호 형식유효성 체크      
         
         return true;
    }
        
    /**
     * <p>XXX - XXX- XXXX 형식의 전화번호 하나를 입력 받아 유요한 전화번호형식인지 검사.</p>
     * 
     * 
     * @param   전화번호 문자열 (1개)
     * @return  유효한 전화번호 형식인지 여부 (True/False)
     */
    public static boolean checkFormatTell(String tellNumber) {
         
         String temp1;
         String temp2;
         String temp3;
         String tell = tellNumber;
         
         tell = tell.replace("-", "");  
         
         if(tell.length() < 9 || tell.length() > 11  || tell.charAt(0) != '0') return false;    //전화번호 길이에 대한 체크
                 
         if(tell.charAt(1) =='2'){      //서울지역 (02)국번의 경우일때
                 temp1 = tell.substring(0,2);
                 if(tell.length() == 9){
                         temp2 = tell.substring(2,5);
                         temp3 = tell.substring(5,9);
                 }else if(tell.length() == 10){
                         temp2 = tell.substring(2,6);
                         temp3 = tell.substring(6,10);
                 }else
                         return false;  
         } else if(tell.substring(0,4).equals("0505")){ //평생번호(0505)국번의 경우일때
                 if(tell.length() != 11) return false;
                 temp1 = tell.substring(0,4);
                 temp2 = tell.substring(4,7);
                 temp3 = tell.substring(7,11);
         } else {       // 서울지역 및 "0505" 를 제외한 일반적인 경우일때
                 if(tell.length() != 10) return false;
                 temp1 = tell.substring(0,3);
                 temp2 = tell.substring(3,6);
                 temp3 = tell.substring(6,10); 
         }
                                 
         return checkFormatTell(temp1, temp2, temp3);
    }
        
    /**
     * <p>XXX - XXX- XXXX 형식의 휴대폰번호 앞, 중간, 뒤 문자열 3개 입력 받아 유요한 휴대폰번호형식인지 검사.</p>
     * 
     * 
     * @param   휴대폰번호 문자열,(3개)
     * @return  유효한 휴대폰번호 형식인지 여부 (True/False)
     */
    public static boolean checkFormatCell(String cell1, String cell2, String cell3) {
         String[] check = {"010", "011", "016", "017", "018", "019"}; //유효한 휴대폰 첫자리 번호 데이터
         String temp = cell1 + cell2 + cell3;
         
         for(int i=0; i < temp.length(); i++){
                if (temp.charAt(i) < '0' || temp.charAt(i) > '9') 
                        return false;                   
         }      //숫자가 아닌 값이 들어왔는지를 확인
                         
         for(int i = 0; i < check.length; i++){
             if(cell1.equals(check[i])) break;                   
             if(i == check.length - 1) return false;
         }      // 휴대폰 첫자리 번호입력의 유효성 체크
                 
         if(cell2.charAt(0) == '0') return false;
                
         if(cell2.length() != 3 && cell2.length() !=4) return false;
         if(cell3.length() != 4) return false;
                                 
         return true;
    }
         
    /**
     * <p>XXXXXXXXXX 형식의 휴대폰번호 문자열 3개 입력 받아 유요한 휴대폰번호형식인지 검사.</p>
     * 
     * 
     * @param   휴대폰번호 문자열(1개)
     * @return  유효한 휴대폰번호 형식인지 여부 (True/False)
     */
    public static boolean checkFormatCell(String cellNumber) {
                 
         String temp1;
         String temp2;
         String temp3;
        
         String cell = cellNumber;
         cell = cell.replace("-", "");          
         
         if(cell.length() < 10 || cell.length() > 11  || cell.charAt(0) != '0') return false;
         
         if(cell.length() == 10){       //전체 10자리 휴대폰 번호일 경우
                 temp1 = cell.substring(0,3);
                 temp2 = cell.substring(3,6);
                 temp3 = cell.substring(6,10);
         }else{         //전체 11자리 휴대폰 번호일 경우
                 temp1 = cell.substring(0,3);
                 temp2 = cell.substring(3,7);
                 temp3 = cell.substring(7,11);
         }
                 
         return checkFormatCell(temp1, temp2, temp3);
    }
         
    /**
     * <p> 이메일의  앞, 뒤 문자열 2개 입력 받아 유요한 이메일형식인지 검사.</p>
     * 
     * 
     * @param   이메일 문자열 (2개)
     * @return  유효한 이메일 형식인지 여부 (True/False)
     */
    public static boolean checkFormatMail(String mail1, String mail2) {
                 
         int count = 0;
                 
         for(int i = 0; i < mail1.length(); i++){
                 if(mail1.charAt(i) <= 'z' && mail1.charAt(i) >= 'a') continue;
                 else if(mail1.charAt(i) <= 'Z' && mail1.charAt(i) >= 'A') continue;
                 else if(mail1.charAt(i) <= '9' && mail1.charAt(i) >= '0') continue;
                 else if(mail1.charAt(i) == '-' && mail1.charAt(i) == '_') continue;
                 else if(mail1.charAt(i) == '.') continue;
                 else return false;
         }      // 유효한 문자, 숫자인지 체크
                                                 
         for(int i = 0; i < mail2.length(); i++){       
                 if(mail2.charAt(i) <= 'z' && mail2.charAt(i) >= 'a') continue;
                 else if(mail2.charAt(i) == '.'){ count++;  continue;}
                 else return false;
         }      // 메일 주소의 형식 체크(XXX.XXX 형태)             
                 
         if(count == 1) return true;
         else  return false;     
                 
    }
        
    /**
     * <p> 이메일의 전체문자열 1개 입력 받아 유요한 이메일형식인지 검사.</p>
     * 
     * 
     * @param   이메일 문자열 (1개)
     * @return  유효한 이메일 형식인지 여부 (True/False)
     */
    public static boolean checkFormatMail(String mail) {
                 
         String[] temp = mail.split("@");       // '@' 를 기점으로 앞, 뒤 문자열 구분
         
         if(temp.length == 2) return checkFormatMail(temp[0], temp[1]);
         else return false;
    }
    
    /**
     * <p> http 또는 https url 유효성 검사 </p>
     * 
     * 
     * @param   url 문자열 (1개)
     * @return  유효한 url 형식인지 여부 (True/False)
     */
    public static boolean checkUrl(String url) {
    	if (url.startsWith("http://")) {
    		return true;
    	} else if (url.startsWith("https://")) {
    		return true;
    	}
    	
    	return false;
    }
    
    /**
     * @작성일	: 2014. 9. 13.
     * @작성자	: Noh
     * @Method 설명 : 임시패스워드 생성
     * @return
     */
    public static String getTempPassword() {
    	UUID uuid = UUID.randomUUID();
		String password = uuid.toString().substring(0, 9);
		return password;
    }
    
    /**
     * @작성일	: 2014. 9. 16.
     * @작성자	: 
     * @Method 설명 : 소수점 .length f 자리수 제한
     * @return
     */
    public static String getDotUnder2LengthLimit(String value){
    
    	if(value == null || value.compareTo("") == 0) return "";
    	
    	value = value.trim();
    	float conv = -1;
    	try{
    		conv = Float.parseFloat(value);
    		value = String.format("%.2f", conv);
    	}catch(Exception e){
    		logger.debug(e.getMessage());
    		logger.debug("[getStringDotLimit][notConverted]value[" + value + "]conv[" + conv + "]");
    		value = "";
    	}
    	
    	return value;
    }
    
    /**
     * @작성일	: 2014. 9. 17.
     * @작성자	: 
     * @Method 설명 : description html 치환
     * @return
     */
    public static String replaceSecure(String str)
    {
		if(str == null){return "";}

    	String sRet = str;
    	sRet = sRet.replaceAll("&", "&amp;");
    	sRet = sRet.replaceAll("<", "&lt;");
    	sRet = sRet.replaceAll(">", "&gt;");
    	sRet = sRet.replaceAll("'", "&#39;");
    	sRet = sRet.replaceAll("\"", "&quot;");
    	sRet = sRet.replaceAll("\r\n", "<br/>");

        return sRet;
    }
    /**
     * @작성일	: 2014. 9. 17.
     * @작성자	: 
     * @Method 설명 : description html 치환
     * @return
     */
    public static String replaceSecure_nobr(String str)
    {
		if(str == null){return "";}

    	String sRet = str;
    	sRet = sRet.replaceAll("&", "&amp;");
    	sRet = sRet.replaceAll("<", "&lt;");
    	sRet = sRet.replaceAll(">", "&gt;");
    	sRet = sRet.replaceAll("'", "&#39;");
    	sRet = sRet.replaceAll("\"", "&quot;");

        return sRet;
    }
    
    
    /**
     * @작성일	: 2014. 9. 25.
     * @작성자	: 
     * @Method 설명 : 브라우저 종류
     * @return
     */
    public static String getBrowser(HttpServletRequest request) {
        String header =request.getHeader("User-Agent");
        if (header.contains("MSIE")) {
               return "MSIE";
        } else if (header.contains("Trident")){
        		return "Trident";
        } else if(header.contains("Chrome")) {
               return "Chrome";
        } else if(header.contains("Opera")) {
               return "Opera";
        }
        return "Firefox";
    }
    
    /**
   	 * @작성일   : 2013. 10. 30. 
   	 * @작성자   : yjpark
   	 * @설명   	 : 정수 확인
   	 * @Method 설명 : * @param str
   	 * @Method 설명 : * @return
   	 */
   	public static boolean isInteger(String str){
   		try {
   			Integer.parseInt(str);
   		} catch (NumberFormatException e) {
   			return false;
   		}
   		return true;
   		
   	}
   	
	/**
	 * @작성일   : 2014. 10. 30. 
	 * @작성자   : 
	 * @설명   	 : 
	 * @Method 설명 : * @param str
	 * @Method 설명 : * @return
	 */
   	public static String urlEncode(String url) throws UnsupportedEncodingException {
		return URLEncoder.encode(url, "UTF-8");
	}
   	
   	/**
   	 * @작성일	: 2015. 9. 7.
   	 * @작성자	: Noh
   	 * @Method 설명 : url decode
   	 * @param content
   	 * @return
   	 */
   	public static String urlDecode(String content) {
    	if (!CommonUtil.isEmpty(content)) {
    		try {
        		return URLDecoder.decode(content, "UTF-8");
    		}
    		catch (UnsupportedEncodingException e1) {
    			e1.printStackTrace();
    		}
    	}
    	
    	return " ";
    }
 
   	public static String getDataSizeConvDigaByte(String value){

   		if(value == null || value.length() == 0)	return "0";
   		
   		String retFormat = "0";
   		double size = 0;
   		try{
   			size = Double.parseDouble(value)/1024;
   		} catch(Exception e){
   			return "0";
   	    }
   		
        String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };
        
         if (value != "0") {
               int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
               DecimalFormat df = new DecimalFormat("#,###.#");
               double ret = ((size / Math.pow(1024, Math.floor(idx))));
               retFormat = df.format(ret) + " " + s[idx];
          } else {
               retFormat += " " + s[0];
          }
          return retFormat;
   	}
   	
   	public static String getConvGigaByte(String value){
   		if(value == null || value.trim().length() == 0) return "0";
   	    double GIGABYTE = 1073741824;
   	    
   	    double temp = 0;
   	    try{
   	    	temp = Double.parseDouble(value)*1024;
   	    } catch(Exception e){
   	    }
   	    
   	    double b = temp / GIGABYTE;
   	    
   	    value = String.format("%.1f", b);
   	    
   	    if(value.compareTo("0.0") == 0){
   	    	value = "0";
   	    }
   	    
   	    return value;
   	}
   	

   	/**
	 * @작성일   : 2015. 5. 12. 
	 * @작성자   : 
	 * @Method 설명 : Description \n > <br/> 치환
	 * @param userInfo
	 * @return
	 */
	public static String setHtmlReplaceBr(String value){
		
		if(value == null){
			value = "";
		}
		
		if (!isEmpty(value) && value.indexOf("\n") > -1) {
			value = value.replaceAll("\n", "<br/>");
		}
		
		return value;
	}
	
    /**
     * <p> seqID를 resourceID format으로 변환 후 반환 </p>
     * 
     * 
     * @param   seqID
     * @return  resourceID
     */
    public static String seqIDToResourceID(String seqPrefix, Long seqID) {
    	return seqPrefix + String.format("%020d", seqID);
    }
    
    /**
     * <p> timestamp 포멧으로 현재시간 반환  </p>
     * 
     * 
     * @return  timestamp
     */
    public static String getNowTimestamp() {
    	String format = "yyyy-MM-dd'T'HH:mm:ssXXX";
    	String now = "";
		
		SimpleDateFormat simpleFomat = new SimpleDateFormat(format);
		Date currentTime = new Date();
		now = simpleFomat.format(currentTime);
		
		return now;
    }
    
	/** 
	 * @작성일   : 2015. 04. 08. 
	 * @작성자   : 이상준
	 * @Method 설명 : 문자열 시간을 Date 객체 타입으로 반환
	 * @throws RSCException
	public static Date timestampToDate(String timestamp) throws IotException {
		String format = "yyyy-MM-dd'T'HH:mm:ssXXX";
		
		SimpleDateFormat simpleFomat = new SimpleDateFormat(format);
		
		Date date = null;
		
		try {
			date = simpleFomat.parse(timestamp);
		} catch (ParseException e) {
			throw new IotException("600", e.getMessage());
		}
		
		return date;
	}
	 */
	
    /**
     * <p> resource 가 최초 생성될 경우 설정하는 expirationTime  </p>
     * 
     * 
     * @return "9999-12-31T00:00:00+00:00"
     */
    public static String getExpirationTime() {
    	return "9999-12-31T00:00:00+00:00";
    }
	
    /**
     * @작성일	: 2015. 5. 22.
     * @작성자	: Noh
     * @Method 설명 : MD5 hashcode
     * @param pwd
     * @return
     */
    public static String getThingPlusPasswordJen(String pwd) {
    	String MD5 = ""; 
		try{
			MessageDigest md = MessageDigest.getInstance("MD5"); 
			md.update(pwd.getBytes()); 
			byte byteData[] = md.digest();
			StringBuffer sb = new StringBuffer(); 
			for(int i = 0 ; i < byteData.length ; i++){
				sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
			}
			MD5 = sb.toString();
			
		}catch(NoSuchAlgorithmException e){
			e.printStackTrace(); 
			MD5 = null; 
		}
		return MD5;
	}
    
    public static String getKoreaUTC(int year, int month, int day, int hour, int minute, int second) {
    	TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar calendar = Calendar.getInstance(utc);
        calendar.set(year, month, day, hour, minute, second);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss+09:00");
        format.setTimeZone(utc);
		String utcTime = format.format(calendar.getTime());
		
		return utcTime;
    }
    

	public static String getCertEngineCurrent() {
		return CertEngineCurrent;
	}
	public static void setCertEngineCurrent(String certEngineCurrent) {
		CertEngineCurrent = certEngineCurrent;
	}
	public static String getSearchEngineCurrent() {
		return searchEngineCurrent;
	}
	public static void setSearchEngineCurrent(String searchEngineCurrent) {
		CommonUtil.searchEngineCurrent = searchEngineCurrent;
	}
	public static String getPlatformAPICurrent() {
		return platformAPICurrent;
	}
	public static void setPlatformAPICurrent(String platformAPICurrent) {
		CommonUtil.platformAPICurrent = platformAPICurrent;
	}
    
	public static String getSso_remove_key() {
		return sso_remove_key;
	}
	public static void setSso_remove_key(String sso_remove_key) {
		CommonUtil.sso_remove_key = sso_remove_key;
	}
		
	public static void checkValidUserId(String userId) throws Exception {
		
		checkValidUserIdLength(userId);
		
		checkValidUserIdCharacters(userId);
		
	}
	
	public static void checkValidUserIdLength(String userId) throws Exception {
		int idLen = userId.getBytes().length;
		if (isEmpty(userId) || 6 > idLen || idLen > 20) {
			throw new ValidationException(CommonUtil.getMessage("user.error3.userIdLength")); /*아이디는 6자이상 50자 미만만 가능합니다.*/
		}
	}
	
	public static void checkValidUserIdCharacters(String userId) throws Exception {
		
		if(!Pattern.matches("^[a-z0-9@.\\-_]*$", userId)) {
			throw new ValidationException(CommonUtil.getMessage("user.error.onlyengnum2.userId")); /*아이디는 영문 소문자, 숫자, 특수문자(“@”, “.”, “-“, “_”)만 가능합니다.*/
		}
	}
	
	/**
	 * @작성일	: 2015. 11. 10.
	 * @작성자	: Noh
	 * @Method 설명 : 패스워드 유효성 검사 
	 * @param password
	 * @throws Exception
	 */
	public static void checkValidPassword(String password) throws Exception {
		
		if (isEmpty(password)) {			
			throw new ValidationException(CommonUtil.getMessage("omp.label.userReq.passwd.valid3.text")); /* 비밀번호는 영문, 숫자, 특수문자 중 두 가지 조합 : 10자 ~ 20자 ,세 가지 조합 : 8자 ~ 20자 사용 가능합니다. */
		}
		
		if (!isValidPasswordChar(password)) {
			throw new ValidationException(CommonUtil.getMessage("omp.validation.error.user.password")); /* 비밀번호는 영문 대문자, 영문 소문자, 숫자, 특수문자을 조합하여 입력하십시오. */
		}
		
		if (isPasswordContinuation(password)) {
			throw new ValidationException(CommonUtil.getMessage("omp.continuation.character.error.user.password")); /* 비밀번호는 3자이상 연속 영문/숫자 조합이 불가합니다.(ex: abc, cba, 123, 321) */
		}
		
		if (isPasswordRepeat(password)) {
			throw new ValidationException(CommonUtil.getMessage("omp.repeat.character.error.user.password")); /* 비밀 번호는 3자 이상 반복 영문/숫자 조합이 불가합니다.(ex: aaa, 111) */
		}
		
		int pLen = password.getBytes().length;
		int cnt = getValidPasswordCharCount(password);
		if (cnt == 2) {//두 가지 조합 : 10자 ~ 20자
			if( pLen < 10 ||  pLen > 20 ) {
				new ValidationException(CommonUtil.getMessage("omp.Length10.user.password"));/* 비밀번호는 10자에서 20자 사이로 입력하십시오. */
			}
		}
		else if (cnt == 3) {//세 가지 조합 : 8자 ~ 20자
			if( pLen < 8 ||  pLen > 20 ) {
				new ValidationException(CommonUtil.getMessage("omp.Length8.user.password"));/* 비밀번호는 8자에서 20자 사이로 입력하십시오. */
			}
		}
		else {
			throw new ValidationException(CommonUtil.getMessage("omp.label.userReq.passwd.valid3.text")); /* 비밀번호는 영문, 숫자, 특수문자 중 두 가지 조합 : 10자 ~ 20자 ,세 가지 조합 : 8자 ~ 20자 사용 가능합니다. */
		}
	}
	
	/**
	 * @작성일	: 2015. 11. 10.
	 * @작성자	: Noh
	 * @Method 설명 : 비밀번호 유효성 검사(영문, 숫자, 특수문자만 사용가능)
	 * @param password
	 * @return
	 */
	public static boolean isValidPasswordChar(String password) {
		
		if (!Pattern.matches("^[a-zA-Z0-9`~!@#$%^&*()_+\\-=\\[\\];,./{}|:?><'\"\\\\]*$", password)) {//영문 숫자 특수문자 사용
	 		return false;
		}
		
		return true;
	}
	
	/**
	 * @작성일	: 2015. 12. 21.
	 * @작성자	: Noh
	 * @Method 설명 : 패스워드 유효 문자 사용 여부 검사
	 * @param password
	 * @return
	 */
	public static int getValidPasswordCharCount(String password) {
				
		int check = 0;
	 	//적어도 한개의 a-z 확인 or 적어도 한개의 A-Z 확인
	 	if(Pattern.compile("[a-z]").matcher(password).find() || Pattern.compile("[A-Z]").matcher(password).find()){
	 		check++;
	 	}
	 		 	
	 	//적어도 한개의 0-9 확인  
	 	//if(Pattern.matches("^[0-9]*$", password2)){
	 	if(Pattern.compile("[\\d]").matcher(password).find()){	 	
	 		check++;
	 	}
	 	
	 	//적어도 한개의특수문자확인(`~!@#$%^&*()_+-=[]\;',./{}|":?><)
	 	if(Pattern.compile("[`~!@#$%^&*()_+\\-=\\[\\];,./{}|:?><'\"\\\\]").matcher(password).find()){
	 		check++;
	 	}
	
		return check;
	}
	
	/**
	 * @작성일	: 2015. 12. 21.
	 * @작성자	: Noh
	 * @Method 설명 : 연속 문자조합인지 검사
	 * @param password
	 * @return
	 */
	public static boolean isPasswordContinuation(String password) {
		//3자 이상 연속 영문/숫자 조합 불가
	    int samePass1 = 0; //연속성(+) 카운트
		int samePass2 = 0; //연속성(-) 카운트
		char chrPass0;
		char chrPass1;
		char chrPass2;
		for(int i = 0; i < password.length() - 2; i++){
			chrPass0 = password.charAt(i);
			chrPass1 = password.charAt(i + 1);
			chrPass2 = password.charAt(i + 2);
			
	    	//연속성(+) 카운트 (321,cba)
			if(chrPass0 - chrPass1 == 1 && chrPass1 - chrPass2 == 1){
				samePass1 = samePass1 + 1;
			}
			
			//연속성(-) 카운트(123,abc)
			if(chrPass0 - chrPass1 == -1 && chrPass1 - chrPass2 == -1){
				samePass2 = samePass2 + 1;
			}
			
		}
		if(samePass1 > 0 || samePass2 > 0){
			return true;
		}
		
		return false;
	}
	
	/**
	 * @작성일	: 2015. 12. 21.
	 * @작성자	: Noh
	 * @Method 설명 : 반복 패스워드 검사
	 * @param password
	 * @return
	 */
	public static boolean isPasswordRepeat(String password) {
		//3자 이상 연속 영문/숫자 조합 불가
		char chrPass0;
		char chrPass1;
		char chrPass2;
		for(int i = 0; i < password.length() - 2; i++){
			chrPass0 = password.charAt(i);
			chrPass1 = password.charAt(i + 1);
			chrPass2 = password.charAt(i + 2);
			
			if(chrPass0 == chrPass1 && chrPass1 == chrPass2){
				return true;
			}			
			
		}
		
		return false;
	}
	
	/**
	 * @작성일	: 2015. 11. 10.
	 * @작성자	: Noh
	 * @Method 설명 : jdk1.5이상에서 사용됨.
	 * @param inp
	 * @return
	 */
	public static String unescape(String inp) {
	     StringBuffer rtnStr = new StringBuffer();
	     char [] arrInp = inp.toCharArray();
	     int i;
	     for(i=0;i<arrInp.length;i++) {
	         if(arrInp[i] == '%') {
	             String hex;
	             if(arrInp[i+1] == 'u') {    //유니코드.
	                 hex = inp.substring(i+2, i+6);
	                 i += 5;
	             } else {    //ascii
	                 hex = inp.substring(i+1, i+3);
	                 i += 2;
	             }
	             try{
	                 rtnStr.append(Character.toChars(Integer.parseInt(hex, 16)));
	             } catch(NumberFormatException e) {
	              rtnStr.append("%");
	                 i -= (hex.length()>2 ? 5 : 2);
	             }
	         } else {
	          rtnStr.append(arrInp[i]);
	         }
	     }
	     
	     return rtnStr.toString();
	}
	
	/**
	 * escape처리
	 * @*_ - + . / 는 처리 하지 않음.
	 * 회원 가입UI에서 처리하지 않고 넘어와서 저장되므로 여기서도 처리 하지 않음.
	 * @param src
	 * @return
	 */
	public static String escapeJavascript(String src){
		int i;
		char j;
		StringBuffer tmp = new StringBuffer();
		tmp.ensureCapacity(src.length() * 6);
		for (i = 0; i < src.length(); i++) {
			j = src.charAt(i);
			if ("@*_-+./".indexOf(j) > -1) {//이거면 그냥 통과, 회원가입UI의 자바스크립트에서 변환하지 않음.
				tmp.append(j);
				continue;
			}
			if (Character.isDigit(j) || Character.isLowerCase(j)
					|| Character.isUpperCase(j))
				tmp.append(j);
			else if (j < 256) {
				tmp.append("%");
				if (j < 16)
					tmp.append("0");
				tmp.append(Integer.toString(j, 16).toUpperCase());//포탈 화면에서 대문자로 되어서 저장되기 때문에 변환해줌.
			} else {
				tmp.append("%u");
				tmp.append(Integer.toString(j, 16));
			}
		}
		return tmp.toString();
	}
	
	public static String replaceInjection(String sParam){
		logger.debug("### ori:" + sParam);
		if(isEmpty(sParam)){
			return sParam;
		}
		String sRetVal = sParam;
		try {
		
		sRetVal = sRetVal.replaceAll("[\'\";|:\\*\\\\/=<>]","");
		
		sRetVal = sRetVal.replaceAll("(?i)select","");
		sRetVal = sRetVal.replaceAll("(?i)update","");
		sRetVal = sRetVal.replaceAll("(?i)delete","");
		sRetVal = sRetVal.replaceAll("(?i)insert","");
		sRetVal = sRetVal.replaceAll("(?i)where","");
		sRetVal = sRetVal.replaceAll("(?i)from","");
		logger.debug("### replace:" + sRetVal);
		} catch (Exception ex) {
			ex.printStackTrace();
			return sParam;
		}
		return sRetVal;
	}
	
	/**
	 * 오늘 날짜를 이용한 경로 반환
	 * @return
	 */
	public static String getImgSubPath() {
				
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM");
		String sub1 = formatter.format(cal.getTime());
		formatter = new SimpleDateFormat("dd");
		String sub2 = formatter.format(cal.getTime());
		return String.format("/%s/%s/", sub1, sub2);
	}
	
	/**
	 * 사용자 access token 생성
	 * @return
	 */
	public static String getUserAccessToken() {
		return UUID.randomUUID().toString();
	}
}
