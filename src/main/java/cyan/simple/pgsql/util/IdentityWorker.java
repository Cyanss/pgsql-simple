package cyan.simple.pgsql.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.lang.NonNull;

/**
 * <p>IdentityWorker</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 10:49 2021/4/6
 */
public class IdentityWorker {

    private static Logger logger = LoggerFactory.getLogger(IdentityWorker.class);
    
    public static final Long EPOCH = 1288834974657L;

    private static final Long SEQUENCE = 0L;
    private static final Long TIMESTAMP = -1L;
    private static final Long SEQUENCE_BIT = 12L;
    private static final Long WORKER_ID_BIT = 5L;
    private static final Long CENTER_ID_BIT = 5L;
    private static final Long SEQUENCE_MASK = ~(TIMESTAMP << SEQUENCE_BIT);
    private static final Long MIN_WORKER_ID = 0L;
    private static final Long MIN_CENTER_ID = 0L;
    private static final Long MAX_WORKER_ID = ~(TIMESTAMP << WORKER_ID_BIT);
    private static final Long MAX_CENTER_ID = ~(TIMESTAMP << CENTER_ID_BIT);
    private static final Long WORKER_ID_SHIFT = SEQUENCE_BIT;
    private static final Long CENTER_ID_SHIFT = SEQUENCE_BIT + WORKER_ID_BIT;
    private static final Long TIMESTAMP_SHIFT = SEQUENCE_BIT + WORKER_ID_BIT + CENTER_ID_BIT;

    private static final Integer TIMESTAMP_BIT_SIZE = 42;
    private static final Integer REGION_BIT_SIZE = 10;
    private static final Integer MIN_REGION_SIZE = 1;
    private static final Integer MAX_REGION_SIZE = MIN_REGION_SIZE << REGION_BIT_SIZE;
    private static final Integer SEQUENCE_BIT_SIZE = 12;
    private static final Integer ALL_BIT_SIZE = TIMESTAMP_BIT_SIZE + REGION_BIT_SIZE + SEQUENCE_BIT_SIZE;

    private static final Long DEFAULT_TAG = 1L;
    
    public static Long identityWorkerTime() {
        return System.currentTimeMillis() - EPOCH;
    }

    public static Long next(Long lastTime) {
        Long time = identityWorkerTime();
        while (time <= lastTime) {
            time = identityWorkerTime();
        }
        return time;
    }

    private Long lastTime = TIMESTAMP;
    private Long sequence = SEQUENCE;
    private Long workerId;
    private Long centerId;

    private Long cacheId = 0L;

    public Long getWorkerId() {
        return workerId;
    }

    public void setWorkerId(Long workerId) {
        this.workerId = workerId;
    }

    public Long getCenterId() {
        return centerId;
    }

    public void setCenterId(Long centerId) {
        this.centerId = centerId;
    }

    public IdentityWorker(@NonNull Long workerId, @NonNull Long centerId) {
        if (workerId > MAX_WORKER_ID || workerId < MIN_WORKER_ID) {
            logger.error("worker Id can't be greater than {} or less than {}",MAX_WORKER_ID,MIN_WORKER_ID);
            throw new RuntimeException("workerId无效");
        }
        if (centerId > MAX_CENTER_ID || centerId < MIN_CENTER_ID) {
            logger.error("center Id can't be greater than {} or less than {}",MAX_CENTER_ID,MIN_CENTER_ID);
            throw new RuntimeException("centerId无效");
        }
        this.workerId = workerId;
        this.centerId = centerId;
    }

    public synchronized Long generate() {
        Long time = identityWorkerTime();
        
        if (time < this.lastTime) {
            this.sequence ++;
            time = identityWorkerTime() + Math.abs(this.lastTime - time) * this.sequence;
            logger.error("clock is moving backwards. Rejecting requests until {}", this.lastTime);
        }
        if (this.lastTime.equals(time)) {
            this.sequence = (this.sequence + DEFAULT_TAG) & SEQUENCE_MASK;
            if (this.sequence.equals(SEQUENCE)) {
                time = next(this.lastTime);
            }
        } else {
            this.sequence = SEQUENCE;
        }
        this.lastTime = time;

        long generateId = (time << TIMESTAMP_SHIFT)
                | (centerId << CENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | this.sequence;
        if (this.cacheId == generateId) {
            return generate();
        } else {
            this.cacheId = generateId;
            return generateId;
        }
    }
}
