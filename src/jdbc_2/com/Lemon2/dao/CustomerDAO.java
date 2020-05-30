package jdbc_2.com.Lemon2.dao;

import jdbc_2.com.Lemon2.bean.Customer;

import java.sql.Connection;
import java.sql.Date;
import java.util.List;

/**
 * 此接口用于规范针对于Customers表的具体操作
 * 1. 将cust对象添加到数据库中
 * 2. 根据指定的id，删除表中的一条记录
 * 3. 根据内存中的cust对象，修改数据表中的记录
 * 4. 根据指定的id，查询对应的Customer对象
 * 5. 查询表中的所有记录，构成一个集合
 * 6. 返回数据表中数据的记录数
 * 7. 返回数据表中最大的生日（只看数据）
 */
public interface CustomerDAO {

    /**
     * 	1将cust对象添加到数据库中
     * @param connection
     * @param cust
     */
    void insert(Connection connection, Customer cust);

    /**
     * 	2根据指定的id，删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection, int id);

    /**
     * 	3根据内存中的cust对象，修改数据表中的记录
     * @param connection
     * @param cust
     */
    void update(Connection connection, Customer cust);

    /**
     * 	4根据指定的id，查询对应的Customer对象
     * @param connection
     * @param id
     */
    Customer getCustomerById(Connection connection, int id);

    /**
     * 	5查询表中的所有记录，构成一个集合
     * @param connection
     * @return
     */
    List<Customer> getAll(Connection connection);

    /**
     * 	6返回数据表中数据的条目数
     * @param connection
     * @return
     */
    Long getCount(Connection connection);

    /**
     * 	7返回数据表中最大的生日（只看数据）
     * @param connection
     * @return
     */
    Date getMaxBirth(Connection connection);
}
