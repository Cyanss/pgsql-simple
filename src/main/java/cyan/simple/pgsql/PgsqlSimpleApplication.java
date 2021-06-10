package cyan.simple.pgsql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = "cyan.simple")
public class PgsqlSimpleApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PgsqlSimpleApplication.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(PgsqlSimpleApplication.class);
    }
}
