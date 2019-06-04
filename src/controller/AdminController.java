package controller;

import bean.BeanCommodity;
import bean.BeanOrder;
import bean.BeanOrderdetail;
import bean.BeanUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.CommodityService;
import service.OrderService;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;


@org.springframework.stereotype.Controller
public class AdminController {
    @Resource(name = "orderService")
    private OrderService orderService;

    @Resource(name = "commodityService")
    private CommodityService commodityService;

    @Resource(name = "userService")
    private UserService userService;

//    显示管理员主目录
    @RequestMapping("/aindex")
    public ModelAndView Aindex(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/aindex.jsp");
        return mv;
    }

//    显示退款处理界面
    @RequestMapping("/checkrefund")
    public ModelAndView Checkrefund(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        获取所需要显示的订单状态
        int o_state= Integer.parseInt(request.getParameter("o_state"));
//        根据订单状态o_state获得订单
        List<BeanOrder> orders=orderService.loadOrdersByState(o_state);

        session.setAttribute("orders",orders);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/checkrefund.jsp");
        return mv;
    }


//    确认退款
    @RequestMapping("/confirmrefund")
    public ModelAndView Confirmrefund(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        int order_id= Integer.parseInt(request.getParameter("order_id"));

        int o_state= Integer.parseInt(request.getParameter("o_state"));
        orderService.changeOrderStatus(order_id,o_state);

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/checkrefund.jsp");
        return mv;
    }

//    显示所有商品
    @RequestMapping("/commoditys")
    public ModelAndView Commoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/commoditys.jsp");
        return mv;
    }

//    显示add商品界面
    @RequestMapping("/showaddcommoditys")
    public ModelAndView showaddcommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/addcommoditys.jsp");
        return mv;
    }

//    add商品
    @RequestMapping("/addcommoditys")
    public ModelAndView addcommoditys(HttpServletRequest request, HttpServletResponse resq) {

        HttpSession session = request.getSession();
//        从前端获取
        String c_name= request.getParameter("c_name");
        float c_price= Float.parseFloat(request.getParameter("c_price"));
        int c_inventory = Integer.parseInt(request.getParameter("c_inventory"));
        String c_img =request.getParameter("c_img");
        String c_detail =request.getParameter("c_detail");
        String c_type =request.getParameter("c_type");
        BeanCommodity bc =new BeanCommodity();
        bc.setC_name(c_name);
        bc.setC_price(c_price);
        bc.setC_inventory(c_inventory);
        bc.setC_img(c_img);
        bc.setC_detail(c_detail);
        bc.setC_type(c_type);

        try{
            commodityService.addCommodity(bc);
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","add succeed");
            mv.setViewName("/WEB-INF/jsp/commoditys.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp/addcommoditys.jsp");
            return mv;
        }
    }


//    显示modify商品界面
    @RequestMapping("/showupdatecommoditys")
    public ModelAndView showupdatecommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/modifyicommoditys.jsp");
        return mv;
    }

//    modify商品
    @RequestMapping("/modifyicommoditys")
    public ModelAndView modifyicommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//        从前端获取
        String c_name= request.getParameter("c_name");
        float c_price= Float.parseFloat(request.getParameter("c_price"));
        int c_inventory = Integer.parseInt(request.getParameter("c_inventory"));
        String c_img =request.getParameter("c_img");
        String c_detail =request.getParameter("c_detail");
        String c_type =request.getParameter("c_type");
        BeanCommodity bc =new BeanCommodity();
        bc.setC_name(c_name);
        bc.setC_price(c_price);
        bc.setC_inventory(c_inventory);
        bc.setC_img(c_img);
        bc.setC_detail(c_detail);
        bc.setC_type(c_type);
        try{
            commodityService.modifyCommodity(bc);
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","modify succeed");
            mv.setViewName("/WEB-INF/jsp/commoditys.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp/addcommoditys.jsp");
            return mv;
        }
    }

//    remove商品
    @RequestMapping("/removecommoditys")
    public ModelAndView removecommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//        从前端获取
        int c_id= Integer.parseInt(request.getParameter("Remove"));

        try{
            commodityService.deleteCommodity(c_id);
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","del succeed");
            mv.setViewName("/WEB-INF/jsp/commoditys.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp/addcommoditys.jsp");
            return mv;
        }
    }

//    显示users界面
    @RequestMapping("/users")
    public ModelAndView users(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/users.jsp");
        return mv;
    }

//    重置用户密码
    @RequestMapping("/rechargeuser")
    public ModelAndView rechargeuser(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        从前端获取
        String user_id= request.getParameter("Recharge");

        try{
            BeanUser usr=userService.getUserDao().getUser(user_id);
            usr.setUser_pwd("123456");
            userService.modifyUser(usr);
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","recharge succeed");
            mv.setViewName("/WEB-INF/jsp/users.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp/users.jsp");
            return mv;
        }
    }
//     提升用户为管理员
    @RequestMapping("/appointadmin")
    public ModelAndView appointadmin(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//        从前端获取
        String user_id= request.getParameter("Appoint");

        try{
            userService.appointAdmin(user_id);
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","appoint succeed");
            mv.setViewName("/WEB-INF/jsp/users.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp/users.jsp");
            return mv;
        }
    }

}