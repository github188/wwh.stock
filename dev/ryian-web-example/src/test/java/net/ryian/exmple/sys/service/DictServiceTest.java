package net.ryian.exmple.sys.service;

import net.ryian.example.sys.domain.Dict;
import net.ryian.example.sys.service.DictService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by allenwc on 15/9/11.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@ContextConfiguration(locations = { "classpath*:spring/*.xml" })
public class DictServiceTest {

    @Autowired
    private DictService dictService;

    @Before
    public void init() {
        Dict d = new Dict();
        d.setKeyName("test");
        d.setValue("测试1");
        d.setOrderBy(1);
        dictService.saveOrUpdate(d);
        d.setValue("测试2");
        d.setOrderBy(2);
        dictService.saveOrUpdate(d);
    }

    @Test
    public void query() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("keyName", "test");
        List<Dict> dicts = dictService.query(map);
        assertThat(dicts.size()).isEqualTo(2);
        map.put("value", "1");
        dicts = dictService.query(map);
        assertThat(dicts.size()).isEqualTo(1);
    }

    @Test
    public void getDictsByKey() {
        Map<String,Dict> dicts = dictService.getDictsByKey("test");
        assertThat(dicts.size()).isEqualTo(2);
    }

    @Test
    public void getKeyNames() {
        List<String> keyNames = dictService.getKeyNames();
        assertThat(keyNames.size()).isGreaterThanOrEqualTo(2);
    }

}
