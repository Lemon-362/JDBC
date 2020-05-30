package jdbc_1.com.Lemon3.blob;

import jdbc_1.com.Lemon3.utils.JDBCUtils;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;

public class BlobUpdate {

    /**
     * 批量操作数据：
     * 1. addBatch() executeBatch() clearBatch()
     * 2. mysql默认关闭批处理的，需要设置参数让mysql开启
     * 	  ?rewriteBatchedStatements=true 放在配置文件的url后面
     * 3. 使用新的mysql驱动 mysql-connector-java-5.1.37-bin.jar
     * 4. 设置为不自动提交数据，执行完后最后再提交数据
     */
    @Test
    public void BlobUpdateTest(){
        Connection connection = null;
        PreparedStatement ps = null;

        try {
            long start = System.currentTimeMillis();

            connection = JDBCUtils.getConnection();

            connection.setAutoCommit(false);

            String sql = "insert into test.goods(name) values(?)";

            ps = connection.prepareStatement(sql);

            for (int i = 1; i <= 10000000; i++) {
                ps.setObject(1, "name_" + i);

                ps.addBatch();

                if (i % 500 == 0){
                    ps.executeBatch();

                    ps.clearBatch();
                }
            }

            connection.commit();

            long end = System.currentTimeMillis();

            System.out.println("花费时间：" + (end - start));

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeResource(connection, ps, null);
        }
    }

}
