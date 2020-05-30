package jdbc_2.com.Lemon4.dbutils;

import JDBCUtils.JDBCUtils;
import jdbc_2.com.Lemon2.bean.Customer;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.*;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;
import java.util.Map;

/**
 * commons-dbutils ：
 * 	开源JDBC工具类库，封装了针对数据库的增删改查操作
 * 	TODO 原来是要自己定义update方法，填充占位符；而现在直接调用
 * 	  DAO中自己写的update方法可以用这里的QueryRunner代替
 * @author Lemon
 * 1. QueryRunner
 * 2. BeanHandler：用于封装表中的一条记录
 * 3. BeanListHandler：用于封装表中的多条记录
 * 4. MapHandler：将字段及相应字段的值作为map的key和value
 * 5. MapListHandler：将字段及相应字段的值作为map的key和value，将这些map添加到List中
 * 6. ScalarHandler：用于查询特殊值
 */
public class QueryRunnerTest {

    /**
     * 插入：
     * 1. 创建QueryRunner对象
     * 2. 调用QueryRunner.update(Connection conn, String sql, Object... params)方法
     */
    @Test
    public void testInsert(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "insert into test.customers(name,email,birth) values(?,?,?)";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 调用QueryRunner.update(Connection conn, String sql, Object... params)方法
            int insertCount = runner.update(connection, sql, "路易", "luyi@qq.com", "1996-04-09");

            System.out.println("添加了" + insertCount +"记录");

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 查询：BeanHandler：是ResultSetHandler接口的实现类，用于封装表中的一条记录
     * 1. 创建QueryRunner对象
     * 2. 创建BeanHandler对象，用于封装表中的一条记录
     * 3. 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
     */
    @Test
    public void testQuery1(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth from test.customers where id = ?";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建BeanHandler对象，用于封装表中的一条记录
            BeanHandler<Customer> handler = new BeanHandler<>(Customer.class);

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
            Customer customer = runner.query(connection, sql, handler, 24);

            System.out.println(customer);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 多条记录的查询：BeanListHandler：是ResultSetHandler接口的实现类，用于封装表中的多条记录
     * 1. 创建QueryRunner对象
     * 2. 创建BeanListHandler对象，用于封装表中的一条记录
     * 3. 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
     */
    @Test
    public void testQuery2(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth from test.customers where id < ?";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建BeanListHandler对象，用于封装表中的一条记录
            BeanListHandler<Customer> handler = new BeanListHandler<>(Customer.class);

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
            List<Customer> list = runner.query(connection, sql, handler, 24);

            for (Customer customer : list) {
                System.out.println(customer);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 查询：MapHandler：是ResultSetHandler接口的实现类，将字段及相应字段的值作为map的key和value
     * 1. 创建QueryRunner对象
     * 2. 创建MapHandler对象，将字段及相应字段的值作为map的key和value
     * 3. 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
     */
    @Test
    public void testQuery3(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth from test.customers where id < ?";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建MapHandler对象，将字段及相应字段的值作为map的key和value
            MapHandler handler = new MapHandler();

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
            Map<String, Object> map = runner.query(connection, sql, handler, 24);

            System.out.println(map);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 查询：MapListHandler：是ResultSetHandler接口的实现类，将字段及相应字段的值作为map的key和value，将这些map添加到List中
     * 1. 创建QueryRunner对象
     * 2. 创建MapListHandler对象，将字段及相应字段的值作为map的key和value，将这些map添加到List中
     * 3. 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
     */
    @Test
    public void testQuery4(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth from test.customers where id < ?";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建MapHandler对象，将字段及相应字段的值作为map的key和value
            MapListHandler handler = new MapListHandler();

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
            List<Map<String, Object>> list = runner.query(connection, sql, handler, 24);

            for (Map<String, Object> map : list) {
                System.out.println(map);
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    /**
     * 查询：ScalarHandler：是ResultSetHandler接口的实现类，用于查询特殊值
     * 1. 创建QueryRunner对象
     * 2. 创建ScalarHandler对象，用于查询特殊值
     * 3. 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
     */
    @Test
    public void testQuery5(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select count(*) from test.customers";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建ScalarHandler对象，将字段及相应字段的值作为map的key和value
            ScalarHandler handler = new ScalarHandler();

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
            Long count = (Long) runner.query(connection, sql, handler);

            System.out.println(count);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }

    @Test
    public void testQuery6(){
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select max(birth) from test.customers";

            // 创建QueryRunner对象
            QueryRunner runner = new QueryRunner();

            // 创建ScalarHandler对象，将字段及相应字段的值作为map的key和value
            ScalarHandler handler = new ScalarHandler();

            // 调用QueryRunner.query(Connection conn, String sql, ResultSetHandler<T> rsh, Object... params)方法
           Date maxBirth = (Date) runner.query(connection, sql, handler);

            System.out.println(maxBirth);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, null, null);
        }
    }
}
