package net.ryian.exmple.sys.service;

import net.ryian.example.sys.domain.User;
import net.ryian.example.sys.service.UserService;
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
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Before
    public void init() {
        User user = new User();
        user.setUserName("aa");
        user.setName("aa");
        user.encryptUserPassword("111111");
        userService.saveOrUpdate(user);
        user.setUserName("ab");
        user.setName("ab");
        user.encryptUserPassword("222222");
        userService.saveOrUpdate(user);
    }

    @Test
    public void query() {
        Map<String,String> map = new HashMap<String,String>();
        map.put("userName", "b");
        List<User> users = userService.query(map);
        assertThat(users.size()).isEqualTo(1);
    }

    @Test
    public void findUserByUserName() {
        User user = userService.findUserByUserName("ab");
        assertThat(user.getName()).isEqualTo("ab");
    }

    @Test
    public void saveOrUpdate() {
        User u = new User();
        u.setName("cc");
        u.setUserName("cc");
        Long userId = userService.saveOrUpdate(u);
        assertThat(userId).isNotNull();
        u = userService.get(userId);
        assertThat(u.getPassword()).isNotNull();
    }

}
