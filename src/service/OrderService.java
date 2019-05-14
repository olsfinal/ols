package service;

import bean.BeanOrder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Date;

//@Service("orderService")
public class OrderService {


//    public int addBeanOrder(String userid) {
//        Date date=new Date();
//        BeanOrder order = new BeanOrder();
//        order.setOrderid(0);
//        order.setUserid(userid);
//        order.setBeanOrdertime(date);
//        orderDAO.addBeanOrder(order);
//        return orderDAO.findMaxBeanOrderid();
//    }
//
//    public void addBeanOrderDetail(int orderid, String bookid, int quantity) throws BeanOrderException {
//        try{
//            BeanOrderDetail orderDetail = new BeanOrderDetail();
//            orderDetail.setBeanOrderid(orderid);
//            orderDetail.setBookid(bookid);
//            orderDetail.setQuantity(quantity);
//            orderDetailDAO.addBeanOrderDetail(orderDetail);
//        }catch (Exception ex) {
//            throw new BeanOrderException(ex.getMessage());
//        }
//
//    }
//
//    public Collection<BeanOrder> getBeanOrders(String userid) throws BooksNotFoundException {
//        try{
//            return orderDAO.findAllBeanOrder(userid);
//        }catch (Exception ex) {
//            throw new BooksNotFoundException(ex.getMessage());
//        }
//
//    }
//
//    public Collection<BeanOrderDetail> getBeanOrderDetails(int orderid) throws BooksNotFoundException {
//        try{
//            return orderDetailDAO.findAllBeanOrderDetail(orderid);
//        }catch (Exception ex) {
//            throw new BooksNotFoundException(ex.getMessage());
//        }
//    }
}
