package dao;

import bean.BeanOrder;
import bean.BeanOrderdetail;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Repository("orderDao")
public class OrderDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int addOrder(BeanOrder beanOrder) {
        String sql = "insert into order_t(user_id,o_time) value(?,?)";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(beanOrder.getO_time());
        Object[] objects=new Object[]{
                beanOrder.getOrder_id(),
                dateStr
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int updateOrder(BeanOrder beanOrder) {
        String sql = "update order_t set user_id = ? ,o_time = ?" +
                " where order_id = ?";
        Object[] objects=new Object[]{
                beanOrder.getUser_id(),
                beanOrder.getO_time(),
                beanOrder.getOrder_id()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int deleteOrder(int order_id) {
        String sql = "delete from order_t where order_id = ?";
        int num = this.jdbcTemplate.update(sql,order_id);
        return num;
    }

    public BeanOrder findOrderById(int order_id) {
        String sql = "select * from order_t where order_id = ?";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,order_id);
    }

    public List<BeanOrder> findAllOrder() {
        List<BeanOrder> orders;
        String sql = "select * from order_t";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        orders = this.jdbcTemplate.query(sql, rowMapper);
        return orders;
    }

    public List<BeanOrder> findAllOrder(String user_id) {
        List<BeanOrder> orders;
        String sql = "select * from order_t where user_id = ?";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        orders = this.jdbcTemplate.query(sql, rowMapper,user_id);
        return orders;
    }

    public int findMaxOrderid() {
        String sql = "select max(order_id) from order_t";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

//    Orderdetail
    public int addOrderdetail(BeanOrderdetail orderDetail) {
        String sql = "insert into orderdetail_t(order_id,c_id,od_number,od_price) value(?,?,?,?)";
        Object[] objects=new Object[]{
                orderDetail.getOrder_id(),
                orderDetail.getC_id(),
                orderDetail.getOd_number(),
                orderDetail.getOd_price()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int updateOrderdetail(BeanOrderdetail orderDetail) {
        String sql = "update orderdetail_t set c_id = ? ,od_number = ? ,od_price = ?" +
                " where order_id = ?";
        Object[] objects=new Object[]{
                orderDetail.getC_id(),
                orderDetail.getOd_number(),
                orderDetail.getOd_price(),
                orderDetail.getOrder_id()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int deleteOrderdetail(int order_id, int c_id) {
        String sql = "delete from orderdetail_t where order_id = ? and c_id = ?";
        int num = this.jdbcTemplate.update(sql,order_id,c_id);
        return num;
    }

    public List<BeanOrderdetail> findOrderdetailByOrderId(int order_id) {
        String sql = "select * from orderdetail_t where order_id = ?";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        return this.jdbcTemplate.query(sql, rowMapper,order_id);
    }

    public BeanOrderdetail findOrderdetailById(int order_id, int c_id) {
        String sql = "select * from orderdetail_t where order_id = ? and c_id = ?";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,order_id,c_id);
    }


    public List<BeanOrderdetail> findAllOrderdetail() {
        List<BeanOrderdetail> orderDetails;
        String sql = "select * from orderdetail_t";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        orderDetails = this.jdbcTemplate.query(sql, rowMapper);
        return orderDetails;
    }

}
