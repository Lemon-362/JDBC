package jdbc_2.com.Lemon2.dao;

import JDBCUtils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 * 封装对数据表的通用操作（抽象类）
 * 1. 通用的增删改操作 Version 2.0 考虑上事务
 * 2. 通用的查询插操作，用于返回数据表中的一条记录 Version 2.0 考虑上事务
 * 3. 通用的查询操作，返回表中多条记录 Version 2.0 考虑上事务
 * 4. 通用的查询特殊值的方法 考虑上事务
 */
public abstract class BaseDAO {

    /**
     * 通用的增删改操作 Version 2.0（考虑上事务）
     * 	1. 从外部传进数据库的连接，所有的DML操作都在这一个连接上执行，
     * 	    且setAutoCommit(false)取消操作的自动提交
     * 	2. 确保操作都成功后，再手动commit提交数据
     * 	3. 若遇到异常则所有DML操作都回滚
     * 	4. 在finally中手动关闭连接
     * 	5. 在关闭资源前，一定要再把setAutoCommit(true)设置回来
     */
    public int update(Connection connection, String sql, Object... args) {
        PreparedStatement ps = null;
        try {

            // 1. 预编译sql语句，返回statement实例
            ps = connection.prepareStatement(sql);

            /**
             * 2. 填充占位符：sql中占位符的个数和可变形参args的长度相同 注意：索引从1开始，所以第一个位置要i+1，而args数组是从0开始的
             */
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            // 3. 执行操作
            return ps.executeUpdate();

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            // 4. 关闭资源
            JDBCUtils.closeResource(null, ps, null);
        }

        return 0;
    }

    /**
     * 	通用的查询插操作，用于返回数据表中的一条记录
     * 	Version 2.0 考虑上事务
     *
     */
    public <T> T getInstance(Connection connection, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            // 获取结果集的元数据ResultSetMetaData
            ResultSetMetaData metaData = rs.getMetaData();

            // 通过ResultSetMetaData获取结果集的列数
            int columnCount = metaData.getColumnCount();

            if (rs.next()) {
                // 先用空参构造器创建对象，然后用setXxx()方法赋值
                /**
                 * 使用反射newInstance来创建对象
                 */
                T t = clazz.newInstance();

                // 处理结果集一行数据中的每一列
                for (int i = 0; i < columnCount; i++) {
                    // 获取一行上的每一列的数据，索引从1开始
                    Object columnValue = rs.getObject(i + 1);

                    /**
                     * 获取每一列的列名：getColumnName 不推荐
                     * 	改进：获取列的别名：getColumnLabel
                     */
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    // 给t对象的某个属性，赋值为coloumValue：反射！！！
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, columnValue);
                }

                return t;
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }

        return null;
    }

    /**
     * 	针对于不同表的通用的查询操作，返回表中多条记录
     * 	Version 2.0 考虑上事务
     */
    public <T> List<T> getForList(Connection connection, Class<T> clazz, String sql, Object... args) {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            // 获取结果集的元数据ResultSetMetaData
            ResultSetMetaData metaData = rs.getMetaData();

            // 通过ResultSetMetaData获取结果集的列数
            int columnCount = metaData.getColumnCount();

            /**
             * 创建集合对象
             */
            ArrayList<T> list = new ArrayList<T>();
            while (rs.next()) {
                // 先用空参构造器创建对象，然后用setXxx()方法赋值
                /**
                 * 使用反射newInstance来创建对象
                 */
                T t = clazz.newInstance();

                // 处理结果集一行数据中的每一列：给t对象指定的属性赋值
                for (int i = 0; i < columnCount; i++) {
                    // 获取一行上的每一列的数据，索引从1开始
                    Object coloumValue = rs.getObject(i + 1);

                    /**
                     * 获取每一列的列名：getColumnName 不推荐
                     * 	改进：获取列的别名：getColumnLabel
                     */
                    String columnLabel = metaData.getColumnLabel(i + 1);

                    // 给t对象的某个属性，赋值为coloumValue：反射！！！
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t, coloumValue);
                }

                list.add(t);
            }

            return list;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(null, ps, rs);
        }

        return null;
    }

    /**
     * 	用于查询特殊值的通用方法（例如：count(*)）
     * 	返回类型是一个泛型E
     */
    public <E> E getValue(Connection connection, String sql, Object... args){
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            rs = ps.executeQuery();

            if (rs.next()) {
                return (E) rs.getObject(1);
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(null, ps, rs);
        }

        return null;
    }
}
