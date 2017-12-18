package mysample.boot.security.domain.common;


import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
/**
 * 작성자 : 설  민</br>
 * 작성일  : 2013.05.20.</br> 
 * Class 설명 : Apapche Commons Lang 클래스
 */
public class BaseVO implements Serializable {

	private static final long serialVersionUID = -4811211165987600071L;

	private String baseTimeFormat 	= "%Y-%m-%d %H:%i:%s";
	private String baseTimeFormat2 	= "%Y.%m.%d %H:%i";
	private String baseDateFormat 	= "%Y-%m-%d";
	private String localeString		= "ko";
	
	public BaseVO() {
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	public String getBaseTimeFormat() {
		return baseTimeFormat;
	}

	public void setBaseTimeFormat(String baseTimeFormat) {
		this.baseTimeFormat = baseTimeFormat;
	}

	public String getBaseTimeFormat2() {
		return baseTimeFormat2;
	}

	public void setBaseTimeFormat2(String baseTimeFormat2) {
		this.baseTimeFormat2 = baseTimeFormat2;
	}

	public String getBaseDateFormat() {
		return baseDateFormat;
	}

	public void setBaseDateFormat(String baseDateFormat) {
		this.baseDateFormat = baseDateFormat;
	}

	public String getLocaleString() {
		return localeString;
	}

	public void setLocaleString(String localeString) {
		this.localeString = localeString;
	}
	
}
