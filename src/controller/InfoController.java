package controller;


import bean.BeanInfo;
import bean.BeanOrder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.InfoService;
import service.OrderService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@org.springframework.stereotype.Controller
public class InfoController {
    @Resource(name = "infoService")
    private InfoService infoService;

    @RequestMapping(value="/infos",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Infos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        String user_id= (String) session.getAttribute("user_id");

        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        return "1";
    }
    //    显示该用户所有地址
    @RequestMapping("/showinfos")
    public ModelAndView showinfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/infos.jsp");
        return mv;
    }

//    付款时的选择地址界面
    @RequestMapping("/showchoseinfo")
    public ModelAndView showchoseinfo(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/choseinfo.jsp");
        return mv;
    }


//    跳转至add地址界面
    @RequestMapping("/showaddinfos")
    public ModelAndView ShowAddInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/addinfo.jsp");
        return mv;
    }

//    add地址
    @RequestMapping(value="/addinfos",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object AddInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();
        System.out.println(request.getContextPath());
        String user_id= (String) session.getAttribute("user_id");

        String address =  request.getParameter("address");
        String i_name =  request.getParameter("i_name");
        String tel =  request.getParameter("tel");

        String caller = (String) session.getAttribute("caller");
        if(address.equals("") || i_name.equals("") || tel.equals("")){
            return "所有项都不得为空";
        }
        //      加载用户所有地址
        else{
            try{
                BeanInfo info=new BeanInfo();
                info.setAddress(address);
                info.setI_name(i_name);
                info.setTel(tel);
                info.setUser_id(user_id);
                infoService.addInfo(info);
                List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
                request.getSession().setAttribute("infosList",infos);
                if(!caller.isEmpty()&&caller.equals("choseinfo")){
                    return "2";
                }
                return "1";
            }
            catch (Exception e){
                return e.getMessage();
            }
        }
    }

    @RequestMapping(value="/chosemodifyinfo",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object chosemodifyinfo(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        String info_id =  request.getParameter("Modify");
        session.setAttribute("info_id",info_id);

        BeanInfo old_info=infoService.getInfo(Integer.parseInt(info_id));
        session.setAttribute("address",old_info.getAddress());
        session.setAttribute("i_name",old_info.getI_name());
        session.setAttribute("tel",old_info.getTel());

        return "1";
    }
    //    跳转至modify地址界面
    @RequestMapping("/showmodifyinfos")
    public ModelAndView showmodifyinfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/modifyinfo.jsp");
        return mv;
    }

    //    modify地址
    @RequestMapping(value="/modifyinfos",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object modifyinfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        String user_id= (String) session.getAttribute("user_id");
        String info_id= (String) session.getAttribute("info_id");

        //      获取要修改的info，用于判断是否进行了修改
        try{
            BeanInfo old_info=infoService.getInfo(Integer.parseInt(info_id));
            //          前端获取
            String address =  request.getParameter("address");
            String i_name =  request.getParameter("i_name");
            String tel =  request.getParameter("tel");
            if(address==old_info.getAddress()
                    && i_name==old_info.getI_name()
                    && tel==old_info.getTel()){
                return "没有任何修改";
            }

            BeanInfo info=new BeanInfo();
            info.setAddress(address);
            info.setI_name(i_name);
            info.setTel(tel);
            info.setUser_id(user_id);
            info.setInfo_id(Integer.parseInt(info_id));
            infoService.modifyInfo(info);
            //      加载用户所有地址
            List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
            request.getSession().setAttribute("infosList",infos);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }


//    删除地址
    @RequestMapping(value="/removeinfos",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object RemoveInfos(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        try{
            String user_id= (String) session.getAttribute("user_id");

            int info_id = Integer.parseInt(request.getParameter("Remove"));
            //删除地址
            infoService.deleteInfo(info_id);

            //加载用户所有地址
            List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
            request.getSession().setAttribute("infosList",infos);
            return "1";
        }
        catch (Exception e){
            return (e.getMessage());
        }
    }


//    选择地址（付款时）
    @RequestMapping("/choseinfo")
    public ModelAndView ChoseInfo(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        String user_id= (String) request.getSession().getAttribute("user_id");

        //跳转至选择info界面
        List<BeanInfo> infos = (List<BeanInfo>) infoService.getInfos(user_id);
        request.getSession().setAttribute("infosList",infos);
        mv.setViewName("/WEB-INF/jsp/choseinfo.jsp");
        return mv;
    }





}
