package cyan.simple.pgsql.controller;

import cyan.simple.pgsql.error.PgsqlException;
import cyan.simple.pgsql.filter.JsonbFilter;
import cyan.simple.pgsql.model.RestPage;
import cyan.simple.pgsql.model.Thing;
import cyan.simple.pgsql.result.RestResult;
import cyan.simple.pgsql.service.ThingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>ThingController</p>
 * @author Cyan (snow22314@outlook.com)
 * @version V.0.0.1
 * @group cyan.tool.kit
 * @date 14:47 2021/5/10
 */
@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/rest/v0.1.0/pgsql/thing")
public class ThingController {
    @Autowired
    private ThingService thingService;

    @PostMapping("/create")
    public ResponseEntity create(@RequestBody Thing thing) throws PgsqlException {
        Thing create = thingService.save(thing);
        return RestResult.ok(create);
    }

    @PostMapping("/update")
    public ResponseEntity update(@RequestBody Thing thing) throws PgsqlException {
        Thing update = thingService.save(thing);
        return RestResult.ok(update);
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody List<Thing> things) throws PgsqlException {
        List<Thing> save = thingService.saveAll(things);
        return RestResult.ok(save);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable(name = "id") Long id) throws PgsqlException {
        thingService.deleteById(id);
        return RestResult.ok("删除成功！");
    }

    @PostMapping("/query")
    public ResponseEntity postQuery(@RequestBody JsonbFilter filter) throws PgsqlException {
        RestPage<Thing> thingPage = thingService.queryAllWithFilter(filter);
        return RestResult.ok(thingPage);
    }

    @GetMapping("/query/{id}")
    public ResponseEntity query(@PathVariable("id") Long id, HttpServletRequest request) throws PgsqlException {
        String requestToken= request.getHeader("token");
        log.info("request token: {}", requestToken);
        Thing thing = thingService.queryById(id);
        return RestResult.ok(thing);
    }
}
