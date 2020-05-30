package jdbc_1.com.Lemon2.PreparedStatement;

import jdbc_1.com.Lemon2.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class PreparedStatement_Update {

    /**
     * 通用的增删改操作
     * @param sql：sql语句
     * @param args：填充占位符
     */
    public int update(String sql, Object... args) {
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            connection = JDBCUtils.getConnection();

            ps = connection.prepareStatement(sql);

            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }

            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeResource(connection, ps, null);
        }

        return 0;
    }

    @Test
    public void testUpdate() throws Exception {
//        String sql = "delete from customers where id = ?";
//        update(sql, 22);

        String sql = "update `order` set order_name = ? where order_id = ?";
        int update = update(sql, "BB", 4);
        System.out.println(update);
    }
}
