package controller;

import bean.BeanCommodity;
import bean.BeanOrder;
import bean.BeanOrderdetail;
import bean.BeanUser;
import com.sun.xml.internal.messaging.saaj.packaging.mime.util.BEncoderStream;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
        mv.setViewName("/WEB-INF/jsp2/aindex.jsp");
        return mv;
    }

    @RequestMapping("/checkrefund")
    @ResponseBody
    public Object checkrefund(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

    try {
        //        获取所需要显示的订单状态
        int o_state= Integer.parseInt(request.getParameter("o_state"));
        List<BeanOrder> orders;
//        已审核
        if (o_state==6){
            orders=orderService.loadOrdersByState(4);
            List<BeanOrder> orders2=orderService.loadOrdersByState(5);
            orders.addAll(orders2);
            session.setAttribute("pageid","2");
        }
//        待审核
        else {
            orders=orderService.loadOrdersByState(o_state);
            session.setAttribute("pageid","1");
        }

        session.setAttribute("ordersList",orders);
        return "1";
    }
    catch (Exception e){
        return e.getMessage();
    }
    }

//    显示退款处理界面
    @RequestMapping("/showcheckrefund")
    public ModelAndView showcheckrefund(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp2/checkrefund.jsp");
        return mv;
    }


//    确认退款
    @RequestMapping("/confirmrefund")
    @ResponseBody
    public Object Confirmrefund(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        try{
            int order_id= Integer.parseInt(request.getParameter("order_id"));

            int o_state= Integer.parseInt(request.getParameter("o_state"));
            orderService.changeOrderStatus(order_id,o_state);
//            更新退款界面
            List<BeanOrder> orders=orderService.loadOrdersByState(4);
            List<BeanOrder> orders2=orderService.loadOrdersByState(5);
            orders.addAll(orders2);
            session.setAttribute("ordersList",orders);
            session.setAttribute("pageid","2");
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

//    显示所有商品
    @RequestMapping("/acommoditys")
    public ModelAndView Commoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp2/acommoditys.jsp");
        return mv;
    }

//    显示add商品界面
    @RequestMapping("/showaddcommoditys")
    public ModelAndView showaddcommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp2/addcommoditys.jsp");
        return mv;
    }

//    add商品
    @RequestMapping("/addcommoditys")
    @ResponseBody
    public Object addcommoditys(HttpServletRequest request, HttpServletResponse resq) {

        HttpSession session = request.getSession();
        try {
            //        从前端获取
            float c_price = Float.parseFloat(request.getParameter("c_price"));
            int c_inventory = Integer.parseInt(request.getParameter("c_inventory"));
            String c_name = request.getParameter("c_name");
            String c_img = request.getParameter("c_img");
            String c_detail = request.getParameter("c_detail");
            String c_type = request.getParameter("c_type");
//            +判断商品类型
            if (c_name.equals("") || c_img.equals("") || c_detail.equals("") || c_type.equals("")) {
                throw new Exception("所有项都不得为空");
            }
            BeanCommodity bc = new BeanCommodity();
            bc.setC_name(c_name);
            bc.setC_price(c_price);
            bc.setC_inventory(c_inventory);
            bc.setC_img(c_img);
            bc.setC_detail(c_detail);
            bc.setC_type(c_type);
            commodityService.addCommodity(bc);
            List<BeanCommodity> commoditys = commodityService.getCommoditys();
            session.setAttribute("commoditys", commoditys);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


    //    显示modify商品界面
    @RequestMapping("/getcid")
    @ResponseBody
    public Object getcid(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        BeanCommodity commodity=commodityService.getCommodity(Integer.parseInt(request.getParameter("c_id")));
        session.setAttribute("c_id",request.getParameter("c_id"));
        session.setAttribute("c_name",commodity.getC_name());
        session.setAttribute("c_price",commodity.getC_price());
        session.setAttribute("c_inventory",commodity.getC_inventory());
        session.setAttribute("c_type",commodity.getC_type());
        session.setAttribute("c_detail",commodity.getC_detail());
        session.setAttribute("c_img",commodity.getC_img());
        return "1";
    }
//    显示modify商品界面
    @RequestMapping("/showupdatecommoditys")
    public ModelAndView showupdatecommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp2/modifyicommoditys.jsp");
        return mv;
    }

//    modify商品
    @RequestMapping("/modifyicommoditys")
    @ResponseBody
    public Object modifyicommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//        从前端获取
        try{
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
            String cid=(String)session.getAttribute("c_id");
            bc.setC_id(Integer.parseInt(cid));
            commodityService.modifyCommodity(bc);
            List<BeanCommodity> commoditys = commodityService.getCommoditys();
            session.setAttribute("commoditys", commoditys);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


//    remove商品
    @RequestMapping("/removecommoditys")
    @ResponseBody
    public Object removecommoditys(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//        从前端获取
        int c_id= Integer.parseInt(request.getParameter("c_id"));

        try{
            commodityService.deleteCommodity(c_id);
            List<BeanCommodity> commoditys = commodityService.getCommoditys();
            session.setAttribute("commoditys", commoditys);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

//    显示users界面
    @RequestMapping("/showusers")
    public ModelAndView showusers(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        List<BeanUser> users = userService.loadAllUsers();
        List<BeanUser> nus= new ArrayList<>();
        for(BeanUser user : users){
            if (user.getAdmin()==0){
                nus.add(user);
            }
        }

        session.setAttribute("users", nus);
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp2/users.jsp");
        return mv;
    }

//    重置用户密码
    @RequestMapping("/rechargeuser")
    @ResponseBody
    public Object rechargeuser(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        从前端获取
        String user_id= request.getParameter("c_id");

        try{
            BeanUser usr=userService.getUserDao().getUser(user_id);
            usr.setUser_pwd("123456");
            userService.modifyUser(usr);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
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
            mv.setViewName("/WEB-INF/jsp2/users.jsp");
            return mv;
        }
        catch (Exception e){
            ModelAndView mv=new ModelAndView();
            session.setAttribute("msg","failed:"+e.getMessage());
            mv.setViewName("/WEB-INF/jsp2/users.jsp");
            return mv;
        }
    }

}