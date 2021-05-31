package cyan.simple.pgsql.dao.mapper;

import cyan.simple.pgsql.dao.entity.ThingEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.common.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * <p>ThingMapper</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:28 2021/5/6
 */
@Component
public interface ThingMapper extends Mapper<ThingEntity> {
    /**
     * 实体保存（存在更新不存在插入）
     * @param entity 实体
     * @return Integer SQL影响行数
     */
    Integer save(@Param("entity") ThingEntity entity);

    /**
     * 批量保存（存在更新不存在插入）
     * @param entityList 实体集合
     * @return Integer SQL影响行数
     */
    Integer saveAll(@Param("entityList") Collection<ThingEntity> entityList);

    /**
     * 实体单个删除
     * @param id 实体id集合
     * @return Integer SQL影响行数（成功为1）
     */
    Integer deleteById(@Param("id") Long id);

    /**
     * 实体批量删除
     * @param idList 实体id集合
     * @return Integer SQL影响行数
     */
    Integer deleteAll(@Param("idList") Collection<Long> idList);

    /**
     * 通过id查询实体
     * @param id 实体id
     * @return T 查询的数据
     */
    ThingEntity findById(@Param("id") Long id);

    /**
     * 实体批量查询
     * @param idList 实体集合
     * @return List<T> 查询的数据集合
     */
    List<ThingEntity> findAll(@Param("idList") Collection<Long> idList);

    /**
     * 通过filter查询条件查询
     * @param whereSql 过滤条件
     * @return List<T>
     */
    List<ThingEntity> findAllByWhere(@Param("whereSql") String whereSql);


    /**
     * 通过filter查询条件删除
     * @param whereSql 过滤条件
     */
    Integer deleteAllByWhere(@Param("whereSql") String whereSql);
    /**
     * 根据名称判断是否存在
     * @param name 对象名称
     * @return List<T> 查询的数据集合
     */
    List<ThingEntity> findByName(@Param("name") String name);

    /**
     * 根据id和名称判断是否存在
     * @param name 对象名称
     * @param id 对象id
     * @return List<T> 查询的数据集合
     */
    List<ThingEntity> findByNameAndNotId(@Param("name") String name, @Param("id") Long id);
}
