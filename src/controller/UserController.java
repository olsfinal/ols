package controller;

import bean.BeanUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@org.springframework.stereotype.Controller
public class UserController {

//      登陆界面
    @RequestMapping("/login")
    public ModelAndView Login(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/login.jsp");
        return mv;
    }

//    登陆
    @RequestMapping("/checklogin")
    public ModelAndView Checklogin(HttpServletRequest req, HttpServletResponse resq) {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        UserService userService = (UserService) request.getSession().getAttribute("userService");
//        从前端获取输入的id pwd
        String user_id=request.getParameter("user_id");
        String user_pwd =request.getParameter("user_pwd");
        //        登陆成功
        try{
            String userid=userService.checkuser(user_id,user_pwd);
            request.getSession().setAttribute("user_id",userid);
            request.getSession().setAttribute("errmsg","");
//            普通用户
            if(userService.getUserDao().getUser(userid).getAdmin()==0){
                mv.setViewName("/WEB-INF/jsp/index.jsp");
                return mv;
            }
//            管理员
            else{
                mv.setViewName("/WEB-INF/jsp/aindex.jsp");
                return mv;
            }

        }
        //        登陆失败
        catch (Exception e){
            request.getSession().setAttribute("errmsg","your id or pwd error");
            mv.setViewName("/WEB-INF/jsp/login.jsp");
            return mv;
        }
    }


//    跳转至usr信息修改界面
    @RequestMapping("/showmodifyuser")
    public ModelAndView ShowModifyUser(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/modifyuser.jsp");
        return mv;
    }

//    usr信息修改
    @RequestMapping("/modifyuser")
    public ModelAndView ModifyUser(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        UserService userService = (UserService) request.getSession().getAttribute("userService");
//        从前端获取输入的修改信息
        String user_id=request.getParameter("user_id");
        String n_name =request.getParameter("user_name");
        String n_pwd =request.getParameter("user_pwd");
        String n_pwd2 =request.getParameter("user_pwd2");

        BeanUser usr=userService.getUserDao().getUser(user_id);
//        没有修改
        if (n_pwd.equals(usr.getUser_pwd()) && n_name.equals(usr.getUser_name())){
            request.getSession().setAttribute("msg","nothing different");
            mv.setViewName("/WEB-INF/jsp/modifyuser.jsp");
            return mv;
        }
        if(n_pwd.equals(n_pwd2)){
            usr.setUser_name(n_name);
            usr.setUser_pwd(n_pwd);
            userService.modifyUser(usr);

//            修改提示信息（成功）
            request.getSession().setAttribute("msg","modify succeed");
            mv.setViewName("/WEB-INF/jsp/modifyuser.jsp");
            return mv;
        }

        else {
//            修改提示信息（失败）
            request.getSession().setAttribute("msg","pwd formal error or inconformity");
            mv.setViewName("/WEB-INF/jsp/modifyuser.jsp");
            return mv;
        }
    }



//    跳转至usr注册界面
    @RequestMapping("/showregister")
    public ModelAndView ShowRegister(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/register.jsp");
        return mv;
    }

//    usr注册
    @RequestMapping("/register")
    public ModelAndView Register(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        UserService userService = (UserService) request.getSession().getAttribute("userService");
//        从前端获取输入的信息
        String user_id=request.getParameter("user_id");
        String n_name =request.getParameter("user_name");
        String n_pwd =request.getParameter("user_pwd");
        String n_pwd2 =request.getParameter("user_pwd2");
        try {
            userService.register(user_id, n_name, n_pwd, n_pwd2);
            request.getSession().setAttribute("msg","register succeed");
            mv.setViewName("/WEB-INF/jsp/login.jsp");
            return mv;
        }
        catch (Exception e){
            request.getSession().setAttribute("msg",e.getMessage());
            mv.setViewName("/WEB-INF/jsp/register.jsp");
            return mv;
        }
    }

}