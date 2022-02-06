import com.atguigu.crowd.CrowdUtil;
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
 * @create 2022-01-07 19:31
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-persist-mybatis.xml", "classpath:spring-persist-tx.xml"})
public class Tset1Test {
    @Autowired
    private DataSource dataSource;

    @Test
    public void  testConnection() throws SQLException {
        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }


}