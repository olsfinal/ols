package controller;

import bean.BeanCommodity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Collection;


@org.springframework.stereotype.Controller
public class OtherController {

    @RequestMapping("/catalog")
    public ModelAndView ShowCatalog(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        try {
            CommodityServiceImpl commodityService = (CommodityServiceImpl) session.getAttribute("commodityService");
            Collection<BeanCommodity> commoditys = CommodityServiceImpl.getCommoditys();
            request.setAttribute("commoditys", commoditys);
            mv.setViewName("/WEB-INF/jsp/catalog.jsp");
            return mv;
        } catch (Exception ex) {
            throw new Exception(ex);
        }

    }

}