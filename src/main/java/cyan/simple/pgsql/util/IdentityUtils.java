package cyan.simple.pgsql.util;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * <p>IdentityUtils 单机使用</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 10:49 2021/4/6
 */
@Component
public class IdentityUtils implements InitializingBean {

    public static Long workerId = 1L;
    public static Long centerId = 2L;
    public static final IdentityWorker IDENTITY_MAKER = new IdentityWorker(workerId, centerId);

    private static IdentityUtils instance = null;

    public static IdentityUtils getInstance() {
        return instance;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        instance = this;
        random();
    }

    public static void random() {
        workerId = ((Double) (Math.random() * 10 + 10)).longValue();
        centerId = ((Double) (Math.random() * 10 + 30)).longValue();
        IDENTITY_MAKER.setWorkerId(workerId);
        IDENTITY_MAKER.setCenterId(centerId);
    }

    public static void reset(Long workerId, Long centerId) {
        IDENTITY_MAKER.setWorkerId(workerId);
        IDENTITY_MAKER.setCenterId(centerId);
    }

    public static Long generate() {
        return IDENTITY_MAKER.generate();
    }

}
