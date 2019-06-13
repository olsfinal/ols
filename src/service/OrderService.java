package service;

import bean.BeanInfo;
import bean.BeanOrder;
import bean.BeanOrderdetail;
import dao.InfoDao;
import dao.OrderDao;
import dao.UserDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service("orderService")
public class OrderService {
    @Resource(name = "orderDao")
    private OrderDao orderDao;

    @Resource(name = "infoDao")
    private InfoDao infoDao;

    @Resource(name = "userDao")
    private UserDao userDao;

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

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    //    生成订单，返回order_id
    public int generateOrder(String user_id,int info_id) throws Exception{
        int order_id=0;
    //    添加条目
        Date date = new Date(System.currentTimeMillis());
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
        List<BeanOrder> orders = orderDao.findAllOrder(user_id);
        for(BeanOrder order:orders){
            order.setUser_name(userDao.getUser(order.getUser_id()).getUser_name());
        }
        return orders;
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
        List<BeanOrder> orders = orderDao.findOrderByState(o_state);
        for(BeanOrder order:orders){
            order.setUser_name(userDao.getUser(order.getUser_id()).getUser_name());
        }
        return orders;
    }
    //  根据用户和状态
    public List<BeanOrder> loadOrdersByState(String userid,int o_state){
        List<BeanOrder> orders = orderDao.findOrderByState(userid,o_state);
        for(BeanOrder order:orders){
            order.setUser_name(userDao.getUser(order.getUser_id()).getUser_name());
        }
        return orders;
    }
    //  热门商品id
    public List<Integer> hotOrderDetails(){
        List<BeanOrderdetail> bods = orderDao.findAllOrderdetail();
        HashMap<Integer,Integer> mapc=new HashMap<>();
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>();
        for(BeanOrderdetail bod:bods){
            if(!mapc.containsKey(bod.getC_id())){
                mapc.put(bod.getC_id(),bod.getOd_number());
            }
            else {
                int num= mapc.get(bod.getC_id());
                mapc.put(bod.getC_id(),bod.getOd_number()+num);
            }
        }
        for(Map.Entry<Integer, Integer> entry : mapc.entrySet()){
            list.add(entry); //将map中的元素放入list中
        }

        list.sort(new Comparator<Map.Entry<Integer, Integer>>(){
            @Override
            public int compare(Map.Entry<Integer, Integer> o1, Map.Entry<Integer, Integer> o2) {
                return o2.getValue()-o1.getValue();}
            //逆序（从大到小）排列，正序为“return o1.getValue()-o2.getValue”
        });
        List<Integer> result=new ArrayList<>();
        for(Map.Entry<Integer, Integer> entry: list){
            result.add(entry.getKey());
        }
        return result;
    }
}
