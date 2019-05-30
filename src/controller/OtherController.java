package controller;

import bean.BeanCommodity;
import cart.ShoppingCart;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.CommodityService;
import service.OrderService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.ResourceBundle;

@org.springframework.stereotype.Controller
public class OtherController {
//      类别目录
    @RequestMapping("/index")
    public ModelAndView ShowIndex(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/index.jsp");
        return mv;
    }

//      商品目录
    @RequestMapping("/catalog")
    public ModelAndView ShowCatalog(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        获取选择的类别
        String c_type = request.getParameter("c_type");
        ModelAndView mv=new ModelAndView();
        try {
            CommodityService commodityService = (CommodityService) session.getAttribute("commodityService");
            Collection<BeanCommodity> commoditys = commodityService.getCommoditys();
//            选择全部商品c_type则为all
            if (c_type.equals("all")){
                request.setAttribute("commoditys", commoditys);
            }
            else {
                List<BeanCommodity> ncs= new ArrayList<>();
                for(BeanCommodity bc : commoditys){
                    if (bc.getC_type().equals(c_type)){
                        ncs.add(bc);
                    }
                }
                request.setAttribute("commoditys", ncs);
            }
            mv.setViewName("/WEB-INF/jsp/catalog.jsp");
            return mv;
        } catch (Exception ex) {
            throw new Exception(ex);
        }
    }




//    显示购物车（包括在目录中添加、删除时）
    @RequestMapping("/showcart")
    public ModelAndView Showcart(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
//        从前端获取所要Remove或Add的c_id
        String c_id = request.getParameter("Remove");
        if (c_id != null) {
            cart.remove(Integer.parseInt(c_id));
        }
        c_id=request.getParameter("Add");
        if(c_id!=null){
            try {
                CommodityService commodityService = (CommodityService) session.getAttribute("commodityService");
            //     获取商品id对应的商品
                BeanCommodity commodity = commodityService.getCommodity(Integer.parseInt(c_id));
                cart.add(Integer.parseInt(c_id), commodity);
                mv.setViewName("/WEB-INF/jsp/cart.jsp");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        else {
            mv.setViewName("/WEB-INF/jsp/cart.jsp");
        }
        return mv;
    }



//  展示商品具体内容
    @RequestMapping("/commoditydetails")
    public ModelAndView Showcommoditydetails(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
//         从前端获取c_id
        String  c_id= request.getParameter("c_id");
        if ( c_id != null) {
            try {
                CommodityService commodityService = (CommodityService) session.getAttribute("commodityService");
        //       获取商品id对应的商品
                BeanCommodity commodity = commodityService.getCommodity(Integer.parseInt(c_id));
                request.setAttribute("commodity",commodity);
                mv.setViewName("/WEB-INF/jsp/commoditydetail.jsp");
            } catch (Exception e) {
                throw new ServletException(e);
            }
        }
        return mv;
    }



//    付款
    @RequestMapping("/cashier")
    public ModelAndView Cashier(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();

        boolean orderCompleted = true;
//        从前端获取info_id
        int info_id= Integer.parseInt(request.getParameter("info_id"));
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart == null) {
            cart = new ShoppingCart();
            session.setAttribute("cart", cart);
        }
        CommodityService commodityService = (CommodityService) session.getAttribute("commodityService");
        String user_id= (String) session.getAttribute("user_id");
        try {
    //      购买
            commodityService.buyCommoditys(user_id,cart,info_id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            orderCompleted = false;
        }

        if (orderCompleted){
            request.getSession().setAttribute("msg","thank you");
            request.getSession().setAttribute("cart",null);
            mv.setViewName("/WEB-INF/jsp/orders.jsp");
            return mv;
        }
        else{
            request.getSession().setAttribute("userid","something error");
            mv.setViewName("/WEB-INF/jsp/msg.jsp");
            return mv;
        }
    }
}