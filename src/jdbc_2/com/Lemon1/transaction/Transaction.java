package jdbc_2.com.Lemon1.transaction;

import jdbc_2.com.Lemon1.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * 1. 数据库事务的定义：
 * 	事务：一组逻辑操作单元，使数据从一种状态变换到另一种状态
 * 	逻辑操作单元：一个或多个DML操作（CRUD增删改查）
 *
 * 2. 事务处理的原则：
 * 	保证所有事务都作为一个工作单元来执行，即使出现了故障，都不能改变这种执行方式。
 * 	当在一个事务中执行多个操作时，
 * 	（1）要么所有的事务都**被提交(commit)**，那么这些修改就永久地保存下来；
 * 	（2）要么数据库管理系统将放弃所作的所有修改，整个事务**回滚(rollback)**到最初状态。
 *
 * 3. 数据一旦提交，就不可回滚
 *
 * 4. 哪些操作会导致数据的自动提交？（都要避免，否则只能回滚到提交后的数据，提交之前的无法再次得到了）
 * 	（1）DDL操作（库/表的创建、修改、删除）一旦执行，都会自动提交
 * 			set autocommit = false 对DDL操作失效，没有效果
 * 	（2）DML操作（增删改）默认情况下，一旦执行，就会自动提交
 * 			我们可以通过 set autocommit = false 的方式取消DML操作的自动提交
 * 	（3）默认在关闭连接时，会自动提交数据
 * @author Lemon
 *
 */
public class Transaction {

    /**
     * 通用的增删改操作 Version 2.0 TODO 考虑上事务
     *      从外部传进数据库的连接，所有的DML操作都在这一个连接上执行
     * 测试过程中：
     *  1. 创建一个连接，所有的DML操作都在这一个连接上执行
     * 	   且setAutoCommit(false)取消操作的自动提交
     * 	2. 确保操作都成功后，再手动commit提交数据
     * 	3. 若遇到异常则所有DML操作都回滚
     * 	4. 在finally中手动关闭连接
     * 	5. 在关闭资源前，一定要再把setAutoCommit(true)设置回来
     */
    public int update(Connection connection, String sql, Object... args){
        PreparedStatement ps = null;

        try {
            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(null, ps, null);
        }

        return 0;
    }

    @Test
    public void testUpdateWithTransaction(){
        Connection connection = null;

        try {
            // 1. 创建一个连接，所有的DML操作都在这一个连接上执行
            connection = JDBCUtils.getConnection();

            System.out.println(connection.getAutoCommit());

            // 2. 取消操作的自动提交
            connection.setAutoCommit(false);

            String sql1 = "update user_table set balance = balance - 100 where user = ?";

            update(connection, sql1, "AA");

            // 3. 遇到异常则所有DML操作都回滚
//            System.out.println(10 / 0);

            String sql2 = "update user_table set balance = balance + 100 where user = ?";

            update(connection, sql2, "BB");

            System.out.println("转账成功");

            // 4. 确保操作都成功后，再手动commit提交数据

        }catch (Exception e){
            e.printStackTrace();

            try {
                connection.rollback();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }finally {

            // 5. 在关闭资源前，一定要再把setAutoCommit(true)设置回来
            try {
                connection.setAutoCommit(true);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }

            // 6. 在finally中手动关闭连接
            JDBCUtils.closeResource(connection, null, null);

        }
    }

}
