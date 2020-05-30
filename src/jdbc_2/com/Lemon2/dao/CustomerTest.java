package jdbc_2.com.Lemon2.dao;

import JDBCUtils.JDBCUtils;
import jdbc_2.com.Lemon2.bean.Customer;
import jdbc_2.com.Lemon2.dao.CustomerDAOImpl.CustomerDAOImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class CustomerTest {

    private CustomerDAO dao = new CustomerDAOImpl();

    @Test
    public void test() {
        Connection connection = null;

        try {
            connection = JDBCUtils.getConnection();

            Customer customer = new Customer(1, "小蓝", "xiaolan@126.com", new Date(342453425345L));

            dao.insert(connection, customer);

            System.out.println("添加成功");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testDeleteById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            dao.deleteById(connection, 13);

            System.out.println("删除成功");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testUpdate() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            Customer cust = new Customer(18, "贝多芬", "beiduofen@126.com", new Date(48456464645L));

            dao.update(connection, cust);

            System.out.println("修改成功");

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testGetCustomerById() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            Customer customer = dao.getCustomerById(connection, 19);

            System.out.println(customer);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testGetAll() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            List<Customer> list = dao.getAll(connection);

            for (Customer customer : list) {
                System.out.println(customer);
            }


        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testGetCount() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            Long count = dao.getCount(connection);

            System.out.println("表中的记录数为：" + count);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }

    @Test
    public void testGetMaxBirth() {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            Date maxBirth = dao.getMaxBirth(connection);

            System.out.println("最大的生日为：" + maxBirth);

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }finally {

            JDBCUtils.closeResource(connection, null, null);

        }
    }
}

