package jdbc_2.com.Lemon2.dao.CustomerDAOImpl;

import jdbc_2.com.Lemon2.bean.Customer;
import jdbc_2.com.Lemon2.dao.BaseDAO;
import jdbc_2.com.Lemon2.dao.CustomerDAO;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 	具体的数据库CRUD操作实现类
 * 	extends BaseDAO：可以用来操作数据库（数据库操作）
 * 	implements CustomerDAO：基础方法（实际要做的事）
 * 	在这些重写方法中使用BaseDAO里的CRUD操作
 * @author Lemon
 *
 */
public class CustomerDAOImpl extends BaseDAO implements CustomerDAO {

    @Override
    public void insert(Connection connection, Customer cust) {
        String sql = "insert into customers(name,email,birth) values(?,?,?)";

        update(connection, sql, cust.getName(), cust.getEmail(), cust.getBirth());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from customers where id = ?";

        update(connection, sql, id);
    }

    @Override
    public void update(Connection connection, Customer cust) {
        String sql = "update customers set name = ?, email = ?, birth = ? where id = ?";

        update(connection, sql, cust.getName(), cust.getEmail(), cust.getBirth(), cust.getId());
    }

    @Override
    public Customer getCustomerById(Connection connection, int id) {
        String sql = "select id,name,email,birth from customers where id = ?";

        Customer customer = getInstance(connection, Customer.class, sql, id);

        return customer;
    }

    @Override
    public List<Customer> getAll(Connection connection) {
        String sql = "select id,name,email,birth from customers";

        List<Customer> list = getForList(connection, Customer.class, sql);

        return list;
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from customers";

        return getValue(connection, sql);
    }

    @Override
    public Date getMaxBirth(Connection connection) {
        String sql = "select max(birth) from customers";

        return getValue(connection, sql);
    }
}