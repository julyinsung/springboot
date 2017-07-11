package mysample.boot.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// registry.addResourceHandler("/assets/**").addResourceLocations();
		// registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

	/**
	 * Spring MVC에서 제공하는 LocaleResolver 인터페이스 구현체에는 다음과 같은 것이 있다.
 	 *	● FixedLocaleResolver 구성에서 정의한 지역정보로 고정한다. 한 번 고정하면 변경 할 수 없다.
 	 *	● CookieLocaleResolver 지역정보를 쿠키에서 가져오고 저장할 수 있다.
  	 *	● AcceptHeaderLocaleResolver 사용자 브라우저에서 보낸 HTTP 헤더의 지역정보 를 사용한다.
 	 *	● SessionLocaleResolver HTTP 세션에서 지역정보를 찾고 저장한다.
     * 도서: Spring mvc4 p105
	 * @return
	 */
	@Bean
	public LocaleResolver localeResolver() {
		return new SessionLocaleResolver();
	}

	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
		localeChangeInterceptor.setParamName("lang");
		return localeChangeInterceptor;
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(localeChangeInterceptor());
	}
}
