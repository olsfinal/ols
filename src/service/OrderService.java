package service;

import bean.BeanInfo;
import bean.BeanOrder;
import bean.BeanOrderdetail;
import dao.InfoDao;
import dao.OrderDao;
import dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service("orderService")
public class OrderService {
    @Resource(name = "orderDao")
    private OrderDao orderDao;

    @Resource(name = "infoDao")
    private InfoDao infoDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public InfoDao getInfoDao() {
        return infoDao;
    }

    public void setInfoDao(InfoDao infoDao) {
        this.infoDao = infoDao;
    }

    //    生成订单，返回order_id
    public int generateOrder(String user_id,int info_id) throws Exception{
        int order_id=0;
    //    添加条目
        Date date = new Date();
        BeanOrder order = new BeanOrder();
        BeanInfo info = infoDao.loadInfoById(info_id);
        order.setUser_id(user_id);
        order.setO_time(date);
        order.setO_state(1);
        order.setO_address(info.getAddress());
        order.setO_tel(info.getTel());
        orderDao.addOrder(order);
    //    返回最大order_id
        order_id=orderDao.findMaxOrderid();

        return order_id;
    }
    //  生成订单细节
    public void generateOrderDetail(int order_id, int c_id, int od_number ,float od_price) throws Exception{
        BeanOrderdetail orderdetail = new BeanOrderdetail();
        orderdetail.setOrder_id(order_id);
        orderdetail.setC_id(c_id);
        orderdetail.setOd_number(od_number);
        orderdetail.setOd_price(od_price);
        orderDao.addOrderdetail(orderdetail);
    }
    //  返回某用户所有订单
    public List<BeanOrder> loadOrders(String user_id) throws Exception{
        return orderDao.findAllOrder(user_id);
    }
    //  返回某订单所有订单细节
    public List<BeanOrderdetail> loadOrderDetails(int order_id) throws Exception{
        return orderDao.findOrderdetailByOrderId(order_id);
    }

    //  修改订单状态
    public void changeOrderStatus(int order_id, int o_state){
        BeanOrder order = orderDao.findOrderById(order_id);
        order.setO_state(o_state);
        orderDao.updateOrder(order);
    }

    //  根据状态返回订单
    public List<BeanOrder> loadOrdersByState(int o_state){
        return orderDao.findOrderByState(o_state);
    }
    //  根据用户和状态
    public List<BeanOrder> loadOrdersByState(String userid,int o_state){
        return orderDao.findOrderByState(userid,o_state);
    }
}
