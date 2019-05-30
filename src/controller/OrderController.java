package controller;

import bean.BeanOrder;

import bean.BeanOrderdetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.OrderService;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@org.springframework.stereotype.Controller
public class OrderController {


//    显示订单信息
    @RequestMapping("/orders")
    public ModelAndView Orders(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        OrderService orderService = (OrderService) session.getAttribute("orderService");

        String user_id= (String) request.getSession().getAttribute("user_id");
        //        加载所有订单
        List<BeanOrder> orders =orderService.loadOrders(user_id);
        request.getSession().setAttribute("ordersList",orders);
        mv.setViewName("/WEB-INF/jsp/orders.jsp");
        return mv;
    }

//    显示订单信息细节
    @RequestMapping("/orderdetails")
    public ModelAndView Orderdetails(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        OrderService orderService = (OrderService) session.getAttribute("orderService");

        String user_id= (String) request.getSession().getAttribute("user_id");
        int order_id= Integer.parseInt(request.getParameter("order_id"));
        //        加载所有订单细节
        List<BeanOrderdetail> orderdetails =orderService.loadOrderDetails(order_id);
        request.getSession().setAttribute("orderdetailsList",orderdetails);
        mv.setViewName("/WEB-INF/jsp/orders.jsp");
        return mv;
    }

//    申请退款
    @RequestMapping("/requestrefund")
    public ModelAndView RequestRefund(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        OrderService orderService = (OrderService) session.getAttribute("orderService");
        int order_id= Integer.parseInt(request.getParameter("order_id"));
        String user_id= (String) request.getSession().getAttribute("user_id");
//        修改订单状态    3:申请退款中
        orderService.changeOrderStatus(order_id,3);
//        加载所有订单
        List <BeanOrder> orders =orderService.loadOrders(user_id);
        request.getSession().setAttribute("ordersList",orders);

        mv.setViewName("/WEB-INF/jsp/orders.jsp");
        return mv;
    }
}