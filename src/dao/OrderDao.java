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

//    添加订单
    public int addOrder(BeanOrder beanOrder) {
        String sql = "insert into order_t(user_id,o_time,o_address,o_tel,o_state) value(?,?,?,?,?)";
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = sdf.format(beanOrder.getO_time());
        Object[] objects=new Object[]{
                beanOrder.getOrder_id(),
                dateStr,
                beanOrder.getO_address(),
                beanOrder.getO_tel(),
                beanOrder.getO_state()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

//    更新订单
    public int updateOrder(BeanOrder beanOrder) {
        String sql = "update order_t set user_id = ? ,o_time = ? ,o_address = ? ,o_tel = ? ,o_state = ?" +
                " where order_id = ?";
        Object[] objects=new Object[]{
                beanOrder.getUser_id(),
                beanOrder.getO_time(),
                beanOrder.getO_address(),
                beanOrder.getO_tel(),
                beanOrder.getO_state(),
                beanOrder.getOrder_id()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

//    删除订单
    public int deleteOrder(int order_id) {
        String sql = "delete from order_t where order_id = ?";
        int num = this.jdbcTemplate.update(sql,order_id);
        return num;
    }

//    按id返回订单
    public BeanOrder findOrderById(int order_id) {
        String sql = "select * from order_t where order_id = ?";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,order_id);
    }

//    返回所有订单
    public List<BeanOrder> findAllOrder() {
        List<BeanOrder> orders;
        String sql = "select * from order_t";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        orders = this.jdbcTemplate.query(sql, rowMapper);
        return orders;
    }

//    返回某用户所有订单
    public List<BeanOrder> findAllOrder(String user_id) {
        List<BeanOrder> orders;
        String sql = "select * from order_t where user_id = ?";
        RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        orders = this.jdbcTemplate.query(sql, rowMapper,user_id);
        return orders;
    }

//    返回当前最大订单id,即新增订单id
    public int findMaxOrderid() {
        String sql = "select max(order_id) from order_t";
        return this.jdbcTemplate.queryForObject(sql, Integer.class);
    }

//    按状态返回订单
    public List<BeanOrder> findOrderByState(int o_state){
        String sql = "select * from order_t where o_state = ?";
        RowMapper<BeanOrder> rowMapper =  new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        return jdbcTemplate.query(sql,rowMapper,o_state);
    }

//    按用户和状态返回订单
    public List<BeanOrder> findOrderByState(String userid ,int o_state){
        String sql = "select * from order_t where user_id = ? and o_state = ?";
        RowMapper<BeanOrder> rowMapper =  new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
        return jdbcTemplate.query(sql,rowMapper,userid,o_state);
    }

//    Orderdetail
//    添加订单细节
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

//    更新订单细节
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

//    删除订单细节
    public int deleteOrderdetail(int order_id, int c_id) {
        String sql = "delete from orderdetail_t where order_id = ? and c_id = ?";
        int num = this.jdbcTemplate.update(sql,order_id,c_id);
        return num;
    }

//    按订单id返回订单细节
    public List<BeanOrderdetail> findOrderdetailByOrderId(int order_id) {
        String sql = "select * from orderdetail_t where order_id = ?";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        return this.jdbcTemplate.query(sql, rowMapper,order_id);
    }

//    按订单id和商品id返回订单细节
    public BeanOrderdetail findOrderdetailById(int order_id, int c_id) {
        String sql = "select * from orderdetail_t where order_id = ? and c_id = ?";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,order_id,c_id);
    }

//    返回所有订单细节
    public List<BeanOrderdetail> findAllOrderdetail() {
        List<BeanOrderdetail> orderDetails;
        String sql = "select * from orderdetail_t";
        RowMapper<BeanOrderdetail> rowMapper = new BeanPropertyRowMapper<BeanOrderdetail>(BeanOrderdetail.class);
        orderDetails = this.jdbcTemplate.query(sql, rowMapper);
        return orderDetails;
    }

}
