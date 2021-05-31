package cyan.simple.pgsql.service;

import cyan.simple.pgsql.error.PgsqlException;
import cyan.simple.pgsql.filter.JsonbFilter;
import cyan.simple.pgsql.model.RestPage;
import cyan.simple.pgsql.model.Thing;

import java.util.Collection;
import java.util.List;

/**
 * <p>ThingService</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 17:41 2021/5/7
 */
public interface ThingService {
    /**
     * 单个保存
     * @param model 对象信息
     * @return M 创建的对象
     * @throws PgsqlException 模块异常
     */
    Thing save(Thing model) throws PgsqlException;

    /**
     * 批量保存（存在更新,不存在新增）
     * @param modelList 对象信息集合
     * @return List<M> 更新的对象
     * @throws PgsqlException 模块异常
     */
    List<Thing> saveAll(Collection<Thing> modelList) throws PgsqlException;

    /**
     * 通过id集合批量删除
     * @param idList 对象的id集合
     * @throws PgsqlException 模块异常
     */
    void deleteAll(Collection<Long> idList) throws PgsqlException;

    /**
     * 通过id单个删除
     * @param id 对象的id
     * @throws PgsqlException 模块异常
     */
    void deleteById(Long id) throws PgsqlException;

    /**
     * 通过id集合查询所有
     * @param idList 对象id集合
     * @return List<M> 查询的数据
     * @throws PgsqlException 模块异常
     */
    List<Thing> queryAll(Collection<Long> idList) throws PgsqlException;

    /**
     * 通过id集合查询单个
     * @param id 对象id
     * @return M 查询的对象
     * @throws PgsqlException 模块异常
     */
    Thing queryById(Long id) throws PgsqlException;
    /**
     * 通过过滤器查询
     * @param filter 过滤器
     * @return GxPage<M> 查询的数据（分页）
     * @throws PgsqlException 模块异常
     */
    RestPage<Thing> queryAllWithFilter(JsonbFilter filter) throws PgsqlException;

    /**
     * 通过过滤器删除
     * @param filter 过滤器
     * @throws PgsqlException 模块异常
     */
    void deleteAllWithFilter(JsonbFilter filter) throws PgsqlException;
}
