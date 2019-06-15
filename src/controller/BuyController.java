package controller;

import bean.BeanCommodity;
import cart.ShoppingCart;
import com.sun.org.apache.xerces.internal.util.SynchronizedSymbolTable;
import net.sf.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.CommodityService;
import service.OrderService;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@org.springframework.stereotype.Controller
public class BuyController {
    @Resource(name = "commodityService")
    private CommodityService commodityService;

    @Resource(name = "orderService")
    private OrderService orderService;

//      类别目录
    @RequestMapping("/index")
    public ModelAndView ShowIndex(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/index.jsp");
        return mv;
    }

//      选择商品类型
//left.jsp
    @RequestMapping(value="/catalog",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Catalog(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        获取选择的类别
        int index = Integer.parseInt(request.getParameter("c_type"));
        session.setAttribute("c_type",index);
        String c_type="";
        if (index==1) {
            c_type="all";       //所有
        }
        else if (index==2){
            c_type="1";         //男装
        }
        else if (index==3){
            c_type="hot";       //热销
        }
        else if (index==4){
            c_type="2";         //女装
        }
        else if (index==5){
            c_type="3";         //箱包
        }
        else if (index==6){
            c_type="4";         //鞋靴
        }
        try {
            List<BeanCommodity> ncs= new ArrayList<>();
            List<BeanCommodity> commoditys = commodityService.getCommoditys();
//            选择全部商品c_type则为all
            if (c_type.equals("all")){
                ncs=commoditys;
            }
            else if(c_type.equals("hot")){
                List<Integer> cids=orderService.hotOrderDetails();
                for(Integer cid :cids){
                    ncs.add(commodityService.getCommodity(cid));
                }
            }
            else {
                for(BeanCommodity bc : commoditys){
                    if (bc.getC_type().equals(c_type)){
                        ncs.add(bc);
                    }
                }
            }
            session.setAttribute("commoditys", ncs);
            return "1";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    @RequestMapping(value="/findcatalog",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object findcatalog(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

//        获取选择的类别
        String indexs = String.valueOf(session.getAttribute("c_type"));
        int index= Integer.parseInt(indexs);
        String c_type="";
        String str1=request.getParameter("inputtext");
        if (index==1) {
            c_type="all";       //所有
        }
        else if (index==2){
            c_type="1";         //男装
        }
        else if (index==3){
            c_type="hot";       //热销
        }
        else if (index==4){
            c_type="2";         //女装
        }
        else if (index==5){
            c_type="3";         //箱包
        }
        else if (index==6){
            c_type="4";         //鞋靴
        }
        try {
            List<BeanCommodity> ncs= new ArrayList<>();
            List<BeanCommodity> commoditys = commodityService.getCommoditys();
//            选择全部商品c_type则为all
            if (c_type.equals("all")){
                ncs=commoditys;
            }
            else if(c_type.equals("hot")){
                List<Integer> cids=orderService.hotOrderDetails();
                for(Integer cid :cids){
                    ncs.add(commodityService.getCommodity(cid));
                }
            }
            else {
                for(BeanCommodity bc : commoditys){
                    if (bc.getC_type().equals(c_type)){
                        ncs.add(bc);
                    }
                }
            }
            int flag=0;
            List<BeanCommodity> ncs2= new ArrayList<>();
            for(BeanCommodity bc : ncs){
                if(str1.split(" ").length==1){
                    if (bc.getC_name().contains(str1)){
                        ncs2.add(bc);
                    }
                }
                else {
                    for(String ss:str1.split(" ")){
                        if (!bc.getC_name().contains(ss)){
                            flag=1;
                        }
                    }
                    if (flag==0){
                        ncs2.add(bc);
                    }
                    flag=0;
                }

            }
            session.setAttribute("commoditys", ncs2);
            return "1";
        } catch (Exception ex) {
            return ex.getMessage();
        }
    }

    //      显示商品目录
    @RequestMapping("/showcatalog")
    public ModelAndView ShowCatalog(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/catalog.jsp");
        return mv;

    }
//      在购物车中添加
    @RequestMapping(value="/cartadd",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object cartadd(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
//        从前端获取所要Add的c_id
        String c_id = request.getParameter("c_id");
        try {
            //     获取商品id对应的商品
            BeanCommodity commodity = commodityService.getCommodity(Integer.parseInt(c_id));
            cart.add(Integer.parseInt(c_id), commodity);
            return "1";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    //      在购物车中删除
    @RequestMapping(value="/cartdel",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object cartdel(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();

        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");

//        从前端获取所要Add的c_id
        String c_id = request.getParameter("c_id");
        try {
            //     获取商品id对应的商品
            cart.remove(Integer.parseInt(c_id));
            return "1";
        } catch (Exception e) {
            return e.getMessage();
        }
    }

//    显示购物车
    @RequestMapping("/showcart")
    public ModelAndView Showcart(HttpServletRequest request, HttpServletResponse resq) throws Exception{

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/cart.jsp");
        return mv;
    }


//post商品id
    @RequestMapping(value="/commoditydetails",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Showcommoditydetails(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();
//         从前端获取c_id
        String  c_id= request.getParameter("c_id");

        try {
    //       获取商品id对应的商品
            BeanCommodity commodity = commodityService.getCommodity(Integer.parseInt(c_id));
            session.setAttribute("commodity",commodity);
            return "1";
        } catch (Exception e) {
            return e.getMessage();
        }


    }
    //  展示商品具体内容
    @RequestMapping("/showcdetails")
    public ModelAndView showcdetails(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/commoditydetail.jsp");
        return mv;
    }



//    付款
    @RequestMapping(value="/cashier",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Cashier(HttpServletRequest request, HttpServletResponse resq) throws Exception{
        HttpSession session = request.getSession();


        boolean orderCompleted = true;
//        从前端获取info_id
        int info_id= Integer.parseInt(request.getParameter("info_id"));
        ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
        if (cart.getItems().isEmpty()) {
//            cart = new ShoppingCart();
//            session.setAttribute("cart", cart);
            return "购物车为空";
        }
        String user_id= (String) session.getAttribute("user_id");
        try {
    //      购买
            commodityService.buyCommoditys(user_id,cart,info_id);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return e.getMessage();
        }
        request.getSession().setAttribute("cart",null);
        return "1";

    }
}