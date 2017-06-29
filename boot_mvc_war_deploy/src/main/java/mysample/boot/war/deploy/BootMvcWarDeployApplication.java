package mysample.boot.war.deploy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class BootMvcWarDeployApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(BootMvcWarDeployApplication.class);
	}


	public static void main(String[] args) {
		SpringApplication.run(BootMvcWarDeployApplication.class, args);
	}
}
