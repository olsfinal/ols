package controller;


import bean.BeanInfo;
import bean.BeanOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.InfoService;
import service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@org.springframework.stereotype.Controller
public class InfoController {


//    显示该用户所有地址
    @RequestMapping("/infos")
    public ModelAndView Infos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        InfoService infoService = (InfoService) session.getAttribute("infoService");
        String user_id= (String) session.getAttribute("user_id");

        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        mv.setViewName("/WEB-INF/jsp/infos.jsp");
        return mv;
    }


//    跳转至add地址界面
    @RequestMapping("/showaddinfos")
    public ModelAndView ShowAddInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/addinfo.jsp");
        return mv;
    }

//    跳转至modify地址界面
    @RequestMapping("/showupdateinfos")
    public ModelAndView ShowUpdateInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();


        String info_id =  request.getParameter("info_id");
        session.setAttribute("info_id",info_id);

        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/modifyinfo.jsp");
        return mv;
    }


//    add地址
    @RequestMapping("/addinfos")
    public ModelAndView AddInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        InfoService infoService = (InfoService) session.getAttribute("infoService");

        String user_id= (String) request.getSession().getAttribute("user_id");

        String address =  request.getParameter("address");
        String i_name =  request.getParameter("i_name");
        String tel =  request.getParameter("tel");
        if(address.equals("") || i_name.equals("") || tel.equals("")){
            List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
            request.getSession().setAttribute("infosList",infos);
            request.getSession().setAttribute("msg","add failed ,can not be null");
            mv.setViewName("/WEB-INF/jsp/addinfos.jsp");
            return mv;
        }
        //      加载用户所有地址
        else{
            BeanInfo info=new BeanInfo();
            info.setAddress(address);
            info.setI_name(i_name);
            info.setTel(tel);
            info.setUser_id(user_id);
            infoService.addInfo(info);
            request.getSession().setAttribute("msg","add succeed");
            mv.setViewName("/WEB-INF/jsp/infos.jsp");
            return mv;
        }
    }



    //    modify地址
    @RequestMapping("/modifyinfos")
    public ModelAndView UpdateInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        InfoService infoService = (InfoService) session.getAttribute("infoService");

        ModelAndView mv=new ModelAndView();

        String user_id= (String) request.getSession().getAttribute("user_id");
        int info_id = (int) request.getSession().getAttribute("info_id");


        //      获取要修改的info，用于判断是否进行了修改，未修改则通过showupdateinfos.jsp的msg进行提示
        BeanInfo old_info=infoService.getInfos(info_id);

        String address =  request.getParameter("address");
        String i_name =  request.getParameter("i_name");
        String tel =  request.getParameter("tel");
        if(address==old_info.getAddress()
                && i_name==old_info.getI_name()
                && tel==old_info.getTel()){
            session.setAttribute("msg","没有任何修改");
            mv.setViewName("/WEB-INF/jsp/modifyinfo.jsp");
            return mv;
        }

        BeanInfo info=new BeanInfo();
        info.setAddress(address);
        info.setI_name(i_name);
        info.setTel(tel);
        info.setUser_id(user_id);
        infoService.modifyInfo(info);

        //      加载用户所有地址
        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        mv.setViewName("/WEB-INF/jsp/infos.jsp");
        return mv;
    }





//    删除地址
    @RequestMapping("/removeinfos")
    public ModelAndView RemoveInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        InfoService infoService = (InfoService) session.getAttribute("infoService");

        String user_id= (String) request.getSession().getAttribute("user_id");

        int info_id = Integer.parseInt(request.getParameter("Remove"));
        //删除地址
        infoService.deleteInfo(info_id);


        //加载用户所有地址
        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        mv.setViewName("/WEB-INF/jsp/infos.jsp");
        return mv;
    }


//    选择地址（付款时）
    @RequestMapping("/choseinfo")
    public ModelAndView ChoseInfo(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        InfoService infoService = (InfoService) session.getAttribute("infoService");
        String user_id= (String) request.getSession().getAttribute("user_id");

        //跳转至选择info界面
        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        mv.setViewName("/WEB-INF/jsp/choseinfo.jsp");
        return mv;
    }





}
