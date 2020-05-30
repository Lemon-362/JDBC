package jdbc_2.com.Lemon3.ConnectionPool;

import com.alibaba.druid.pool.DruidDataSourceFactory;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

/**
 * 通过Druid实现数据库的连接
 * @author Lemon
 *
 */
public class DruidTest {

    @Test
    public void getConnection() throws Exception {
        // 1. 创建DBCP连接池
        Properties pros = new Properties();
        // 2. 加载外部文件
        InputStream is = DruidTest.class.getResourceAsStream("druid.properties");
        pros.load(is);
        // 3. 创建DataSource对象
        DataSource source = DruidDataSourceFactory.createDataSource(pros);
        // 4. 实现连接
        Connection connection = source.getConnection();

        System.out.println(connection);

    }

}
