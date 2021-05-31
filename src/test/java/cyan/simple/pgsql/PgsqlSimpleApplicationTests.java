package cyan.simple.pgsql;

import cyan.simple.pgsql.dao.entity.ThingEntity;
import cyan.simple.pgsql.dao.mapper.ThingMapper;
import cyan.simple.pgsql.model.Thing;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class PgsqlSimpleApplicationTests {

    @Autowired
    private ThingMapper thingMapper;
    @Test
    void queryAll() {
        List<ThingEntity> allByWhere = thingMapper.findAllByWhere(null);
        List<Thing> thingList = allByWhere.stream().map(ThingEntity::toModel).collect(Collectors.toList());
        System.out.println(thingList.stream().findAny().orElse(null));
    }

}
