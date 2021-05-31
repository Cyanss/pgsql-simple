package cyan.simple.pgsql.service;

import com.github.pagehelper.Page;
import cyan.simple.pgsql.builder.SqlBuilders;
import cyan.simple.pgsql.dao.entity.ThingEntity;
import cyan.simple.pgsql.dao.mapper.ThingMapper;
import cyan.simple.pgsql.error.ErrorStatus;
import cyan.simple.pgsql.error.PgsqlException;
import cyan.simple.pgsql.filter.JsonbFilter;
import cyan.simple.pgsql.model.RestPage;
import cyan.simple.pgsql.model.Thing;
import cyan.simple.pgsql.util.GeneralUtils;
import cyan.simple.pgsql.util.IdentityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>ThingServiceImpl</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 17:46 2021/5/7
 */
@Service
public class ThingServiceImpl implements ThingService {

    @Autowired
    private ThingMapper thingMapper;

    @Override
    public Thing save(Thing model) throws PgsqlException {
        if (GeneralUtils.isEmpty(model)) {
            return null;
        }
        checkThing(model);
        ThingEntity thingEntity = ThingEntity.toEntity(model);
        Integer result = thingMapper.save(thingEntity);
        if (result == 0) {
            throw new PgsqlException(ErrorStatus.DATA_SAVE_FAILED);
        }
        return model;
    }

    @Override
    public List<Thing> saveAll(Collection<Thing> modelList) throws PgsqlException {
        if (GeneralUtils.isEmpty(modelList)) {
            return null;
        }
        List<ThingEntity> entityList = new ArrayList<>();
        for (Thing model : modelList) {
            checkThing(model);
            ThingEntity thingEntity = ThingEntity.toEntity(model);
            entityList.add(thingEntity);
        }
        Integer result = thingMapper.saveAll(entityList);
        if (result != entityList.size()) {
            throw new PgsqlException(ErrorStatus.DATA_SAVE_ALL_FAILED);
        }
        return new ArrayList<>(modelList);
    }

    @Override
    public void deleteAll(Collection<Long> idList) throws PgsqlException {
        if (GeneralUtils.isEmpty(idList)) {
            return;
        }
        thingMapper.deleteAll(idList);
    }

    @Override
    public void deleteById(Long id) throws PgsqlException {
        if (GeneralUtils.isEmpty(id)) {
            return;
        }
        thingMapper.deleteById(id);
    }

    @Override
    public List<Thing> queryAll(Collection<Long> idList) throws PgsqlException {
        if (GeneralUtils.isEmpty(idList)) {
            return Collections.emptyList();
        }
        List<ThingEntity> entityList = thingMapper.findAll(idList);
        return entityList.stream().map(ThingEntity::toModel).collect(Collectors.toList());
    }

    @Override
    public Thing queryById(Long id) throws PgsqlException {
        if (GeneralUtils.isEmpty(id)) {
            return null;
        }
        ThingEntity entity = thingMapper.findById(id);
        return entity.toModel();
    }

    @Override
    public RestPage<Thing> queryAllWithFilter(JsonbFilter filter) throws PgsqlException {
        Page<ThingEntity> pageRequest = SqlBuilders.page(filter);
        String whereSql = filter.toIdSql("id").toTimeSql("time").toJsonbSql("properties","value").addSorts("update_time").toSql();
        List<ThingEntity> entityList = thingMapper.findAllByWhere(whereSql);
        List<Thing> thingList = entityList.stream().map(ThingEntity::toModel).collect(Collectors.toList());
        return RestPage.result(thingList,pageRequest);
    }

    @Override
    public void deleteAllWithFilter(JsonbFilter filter) throws PgsqlException {
        String whereSql = filter.toIdSql("id").toTimeSql("time").toJsonbSql("properties","value").toSql();
        thingMapper.deleteAllByWhere(whereSql);
    }

    public void checkThing(Thing model) throws PgsqlException {
        Long id = model.getId();
        if (GeneralUtils.isEmpty(id)) {
            model.setId(IdentityUtils.generate());
            String name = model.getName();
            if (GeneralUtils.isEmpty(name)) {
                throw new PgsqlException(ErrorStatus.NAME_IS_NULL);
            } else {
                List<ThingEntity> entityList = thingMapper.findByName(name);
                if (GeneralUtils.isNotEmpty(entityList)) {
                    throw new PgsqlException(ErrorStatus.NAME_REPEATED,"name: ".concat(name));
                }
            }
        } else {
            ThingEntity thingEntity = thingMapper.findById(id);
            if (GeneralUtils.isEmpty(thingEntity)) {
                throw new PgsqlException(ErrorStatus.DATA_NOT_EXIST_ERROR,"id: ".concat(String.valueOf(id)));
            }
            String name = model.getName();
            if (GeneralUtils.isEmpty(name)) {
                throw new PgsqlException(ErrorStatus.NAME_IS_NULL,"id: ".concat(String.valueOf(id)));
            } else {
                List<ThingEntity> entityList = thingMapper.findByNameAndNotId(name,id);
                if (GeneralUtils.isNotEmpty(entityList)) {
                    throw new PgsqlException(ErrorStatus.NAME_REPEATED,"name: ".concat(name));
                }
            }
        }
    }
}
