package cyan.simple.pgsql.configure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * <p>PgsqlAutoConfigure</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 9:27 2021/5/6
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"cyan.simple.pgsql"})
@MapperScan(basePackages = {"cyan.simple.pgsql.dao.mapper"})
public class PgsqlAutoConfigure {
    public PgsqlAutoConfigure() {
        log.debug("================= pgsql-simple-configure initiated ！ ===================");
    }
}
