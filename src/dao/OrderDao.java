package dao;

import database.OrderDetails;
import database.Orders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class OrderDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    生成订单，返回order_id
    public int generateOrder(String user_id) throws Exception{
        int order_id=0;
        try {
            String sql = "insert into order_t(user_id, o_time) values(?,?)";
            java.util.Date utilDate=new java.util.Date();
            Timestamp stp=new Timestamp(utilDate.getTime());
            jdbcTemplate.update(sql, user_id,stp);

            String sql2 = "select * from order_t where order_id = (select max(order_id) from order_t where user_id = ?)";
            RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
            BeanOrder order = jdbcTemplate.queryForObject(sql2,rowMapper,user_id);
            order_id = order.getOrderid();
        }catch (Exception e){
            e.printStackTrace();
        }
        return order_id;
    }
//  生成订单细节
    public void generateOrderDetail(int order_id, String c_id, int od_number ,float od_price) throws Exception{
        try {
            String sql = "insert into orderdetail_t(order_id,c_id,od_number,od_price) values(?,?,?,?)";
            jdbcTemplate.update(sql, order_id,c_id,od_number,od_price);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
//  返回某用户所有订单
    public List<BeanOrder> loadOrders(String user_id) throws Exception{
        List<BeanOrder> orders =  new ArrayList<>();
        try {
            String sql = "select * from order_t where user_id = ?";
            RowMapper<BeanOrder> rowMapper = new BeanPropertyRowMapper<BeanOrder>(BeanOrder.class);
            orders.addAll(jdbcTemplate.query(sql,rowMapper,user_id));

        }catch (Exception e){
            e.printStackTrace();
        }
        return orders;
    }
//  返回某订单所有订单细节
    public List<BeanOrderDetails> loadOrderDetails(int order_id) throws Exception{
        List<BeanOrderDetails> orderDetails = new ArrayList<>();
        try{
            String sql = "select * from orderdetail_t where order_id = ?";
            RowMapper<BeanOrderDetails> rowMapper = new BeanPropertyRowMapper<BeanOrderDetails>(BeanOrderDetails.class);
            orderDetails.addAll(jdbcTemplate.query(sql,rowMapper,order_id));

        }catch (Exception e){
            e.printStackTrace();
        }
        return orderDetails;
    }

}
