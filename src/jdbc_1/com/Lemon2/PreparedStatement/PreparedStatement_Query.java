package jdbc_1.com.Lemon2.PreparedStatement;

import jdbc_1.com.Lemon2.bean.Customer;
import jdbc_1.com.Lemon2.bean.Order;
import jdbc_1.com.Lemon2.utils.JDBCUtils;
import org.junit.Test;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatement_Query {

    /**
     * 通用的查询操作，返回一条记录
     * @param clazz：对象的类型（运行时类），用于创建对象和反射赋值
     * @param sql
     * @param args
     * @param <T>
     * @return：返回一条记录
     */
    public <T> T getInstance(Class<T> clazz, String sql, Object... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConnection();

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            // 1. 获取ResultSet结果集的元数据ResultSetMetaData，从而得到结果集对象中列的类型和属性信息的对象
            ResultSetMetaData metaData = rs.getMetaData();

            // 2. 通过元数据获取结果集的列数
            int columnCount = metaData.getColumnCount();

            // 3. 对一行记录进行处理
            if (rs.next()){
                /**
                 * TODO 使用反射创建对象
                 */
                T t = clazz.newInstance();

                // 处理一条记录上的每一列，为对象t的每个属性赋值
                for (int i = 0; i < columnCount; i++) {
                    // 获取每一列的数据
                    Object columnValue = rs.getObject(i + 1);

                    // 获取列的别名，根据别名通过反射进行赋值
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }

                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, ps, rs);
        }

        return null;
    }

    public <T> List<T> getInstanceForList(Class<T> clazz, String sql, Object... args){
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConnection();

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();

            int columnCount = metaData.getColumnCount();

            // 用于接受多个对象的List
            ArrayList<T> list = new ArrayList<>();

            while (rs.next()){
                T t = clazz.newInstance();

                for (int i = 0; i < columnCount; i++) {
                    Object columnValue = rs.getObject(i + 1);

                    String columnLabel = metaData.getColumnLabel(i + 1);

                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }

                list.add(t);
            }

            return list;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, ps, rs);
        }

        return null;
    }

    @Test
    public void test1(){
        String sql = "select id,name,email from customers where id = ?";
        Customer customer = getInstance(Customer.class, sql, 6);
        System.out.println(customer);

        sql = "select order_id orderId, order_name orderName from `order` where order_id = ?";
        Order order = getInstance(Order.class, sql, 2);
        System.out.println(order);
    }

    @Test
    public void test2(){
        String sql = "select id,name,email from customers where id < ?";
        List<Customer> list = getInstanceForList(Customer.class, sql, 6);
        for (Customer customer : list) {
            System.out.println(customer);
        }

        System.out.println("*******************************");

        sql = "select order_id orderId, order_name orderName from `order`";
        List<Order> list1 = getInstanceForList(Order.class, sql);
        for (Order order : list1) {
            System.out.println(order);
        }
    }
}
