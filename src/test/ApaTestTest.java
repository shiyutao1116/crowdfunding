import com.atguigu.crowd.entity.Role;
import com.atguigu.crowd.mapper.AdminMapper;
import com.atguigu.crowd.entity.Admin;
import com.atguigu.crowd.mapper.RoleMapper;
import com.atguigu.crowd.mvc.handler.AssignHandler;
import com.atguigu.crowd.service.api.AuthService;
import com.atguigu.crowd.service.impl.AuthServiceImpl;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author shiyutao
 * @create 2022-01-07 20:49
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class ApaTestTest {
    @Autowired
    private DataSource dataSource;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private AuthService authService;

    @Test
    public void  testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }
    @Autowired
    private AdminMapper adminMapper;

    @Test
    public void testInsertAdmin(){
        Admin admin;
        admin = new Admin(null,"Rachel","123123","rui","rui@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println(count);

}
@Test
    public void tset1(){

        for (int i=0;i<256;i++){
            adminMapper.insert(new Admin(null,"login"+i,"userPswd"+i,"userName"+i,"email"+i,null));

}

}
    @Test
    public void tset2(){
        authService.getAuthIdByRoleId(1);
    }


}