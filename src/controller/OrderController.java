package controller;

import bean.BeanOrder;

import bean.BeanOrderdetail;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CommodityService;
import service.OrderService;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class OrderController {
    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "commodityService")
    private CommodityService commodityService;

    @Resource(name = "userService")
    private UserService userService;

//    订单类型
    @RequestMapping(value="/orders",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Orders(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        try{
            String user_id= (String) session.getAttribute("user_id");
            session.setAttribute("pageid",request.getParameter("o_state"));
            List<BeanOrder> orders;
            //      加载所有订单
            if(Integer.parseInt(request.getParameter("o_state"))==0){
                orders =orderService.loadOrders(user_id);
            }
            //      加载部分订单
            else{
                orders =orderService.loadOrdersByState
                        (user_id, Integer.parseInt(request.getParameter("o_state")));
            }
            List<BeanOrder> bods=new ArrayList<>();
            for(BeanOrder bod:orders) {
                bod.setUser_name(userService.getUserDao().getUser(bod.getUser_id()).getUser_name());
                bods.add(bod);
            }
            request.getSession().setAttribute("ordersList",bods);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
//    显示订单信息
    @RequestMapping("/showorders")
    public ModelAndView showorders(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/orders.jsp");
        return mv;
    }

//    订单细节信息
    @RequestMapping(value="/orderdetails",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Orderdetails(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        try{
            String user_id= (String) request.getSession().getAttribute("user_id");
//            前端获取
            int order_id= Integer.parseInt(request.getParameter("order_id"));
            //        加载所有订单细节
            List<BeanOrderdetail> orderdetails =orderService.loadOrderDetails(order_id);
            List<BeanOrderdetail> bods=new ArrayList<>();
            for(BeanOrderdetail bod:orderdetails){
                bod.setC_name(commodityService.getCommodity(bod.getC_id()).getC_name());
                bods.add(bod);
            }
            request.getSession().setAttribute("orderdetailsList",bods);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }
    //    显示订单信息细节
    @RequestMapping("/showorderdetails")
    public ModelAndView showorderdetails(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/orderdetails.jsp");
        return mv;
    }

//    改变订单状态
    @RequestMapping(value="/requestchange",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object requestchange(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
//          从前端获取
        int order_id= Integer.parseInt(request.getParameter("order_id"));
        int state= Integer.parseInt(request.getParameter("state"));
        session.setAttribute("pageid",state);
        String user_id= (String) request.getSession().getAttribute("user_id");
        try{
//        修改订单状态
            orderService.changeOrderStatus(order_id,state);
//        加载所有订单
            List <BeanOrder> orders =orderService.loadOrdersByState(user_id,state);
            List<BeanOrder> bods=new ArrayList<>();
            for(BeanOrder bod:orders) {
                bod.setUser_name(userService.getUserDao().getUser(bod.getUser_id()).getUser_name());
                bods.add(bod);
            }
            request.getSession().setAttribute("ordersList",bods);

            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}