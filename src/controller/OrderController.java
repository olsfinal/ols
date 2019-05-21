package controller;

import cart.Orders;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;


@org.springframework.stereotype.Controller
public class OrderController {

    @RequestMapping("/orders")
    public ModelAndView Orders(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        OrderServiceImpl orderService = (OrderServiceImpl) session.getAttribute("orderService");

        String user_id= (String) request.getSession().getAttribute("user_id");
        List<Orders> orders =orderService.loadOrders(user_id);
        request.getSession().setAttribute("ordersList",Orders);
        mv.setViewName("/WEB-INF/jsp/orders.jsp");
        return mv;
    }




}