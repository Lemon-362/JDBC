package jdbc_1.com.Lemon3.blob;

import jdbc_1.com.Lemon2.bean.Customer;
import jdbc_1.com.Lemon3.utils.JDBCUtils;
import org.junit.Test;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.*;

public class BlobQuery {

    /**
     * 查询表中Blob类型的字段
     */
    @Test
    public void BlobQueryTest() {
        Connection connection = null;
        PreparedStatement ps = null;
        InputStream is = null;
        FileOutputStream fos = null;
        ResultSet rs = null;

        try {
            connection = JDBCUtils.getConnection();

            String sql = "select id,name,email,birth,photo from test.customers where id = ?";

            ps = connection.prepareStatement(sql);

            ps.setObject(1, 16);

            rs = ps.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("id");
                String name = rs.getString("name");
                String email = rs.getString("email");
                Date birth = rs.getDate("birth");

                Customer customer = new Customer(id, name, email, birth);

                System.out.println(customer);

                /**
                 * 将Blob类型的字段下载并以文件的形式保存到本地
                 */
                Blob photo = rs.getBlob("photo");

                is = photo.getBinaryStream();

                fos = new FileOutputStream("1.jpg");

                byte[] bytes = new byte[1024];

                int len;

                while ((len = is.read(bytes)) != -1) {
                    fos.write(bytes, 0, len);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            JDBCUtils.closeResource(connection, ps, rs);
        }
    }

}

