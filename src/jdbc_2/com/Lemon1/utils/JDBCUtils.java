package jdbc_2.com.Lemon1.utils;

import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Properties;

/**
 * 操作数据库的工具类：
 * 1. 连接数据库
 * 2. 关闭数据库和资源
 */
public class JDBCUtils {

    public static Connection getConnection() throws Exception {
        InputStream is = JDBCUtils.class.getResourceAsStream("db.properties");
        Properties pros = new Properties();
        pros.load(is);

        String driverClass = pros.getProperty("driverClass");
        String url = pros.getProperty("url");
        String username = pros.getProperty("username");
        String password = pros.getProperty("password");

        Class.forName(driverClass);

        Connection connection = DriverManager.getConnection(url, username, password);

        return connection;
    }

    public static void closeResource(Connection connection, Statement ps, ResultSet rs){
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            if (connection != null){
                connection.close();
            }
        }catch (Exception e){
            e.printStackTrace();;
        }

        try {
            if (rs != null){
                rs.close();
            }
        }catch (Exception e){
            e.printStackTrace();;
        }
    }

    @Test
    public void test() throws Exception {
        Connection connection = getConnection();
        System.out.println(connection);
    }
}
