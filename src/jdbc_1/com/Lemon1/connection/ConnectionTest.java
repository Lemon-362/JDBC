package jdbc_1.com.Lemon1.connection;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class ConnectionTest {

    @Test
    public void testConnection() throws Exception {
        // 加载外部文件
        InputStream is = ConnectionTest.class.getResourceAsStream("db.properties");
        Properties pros = new Properties();
        pros.load(is);

        // 读取配置信息
        String driverClass = pros.getProperty("driverClass");
        String url = pros.getProperty("url");
        String username = pros.getProperty("username");
        String password = pros.getProperty("password");

        // 加载驱动
        Class.forName(driverClass);

        // 加载连接信息
        Connection connection = DriverManager.getConnection(url, username, password);

        System.out.println(connection);
    }
}
