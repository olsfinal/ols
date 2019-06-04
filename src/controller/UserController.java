package controller;

import bean.BeanUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import service.UserService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.spi.SyncResolver;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


@org.springframework.stereotype.Controller
public class UserController {
    @Resource(name = "userService")
    private UserService userService;



//      登陆界面
    @RequestMapping("/login")
    public ModelAndView Login(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/login.jsp");
        return mv;
    }
    //      测试界面
//    @RequestMapping("/index2")
//    public void index2(HttpServletRequest req, HttpServletResponse resq) throws Exception {
//        String user_id=req.getParameter("user_id");
//        String user_pwd =req.getParameter("user_pwd");
//        req.getSession().setAttribute("user_id",user_id);
//        req.getSession().setAttribute("user_pwd",user_pwd);
//    }
//    @RequestMapping("/showindex2")
//    public ModelAndView showindex2(HttpServletRequest req, HttpServletResponse resq) throws Exception{
//        ModelAndView mv=new ModelAndView();
//        mv.setViewName("/WEB-INF/jsp/index2.jsp");
//        return mv;
//    }

    @RequestMapping("/logout")
    public ModelAndView logout(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        req.getSession().setAttribute("user_id","");
        mv.setViewName("/WEB-INF/jsp/login.jsp");
        return mv;
    }

//    登陆
    @RequestMapping(value="/checklogin",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Checklogin(HttpServletRequest request, HttpServletResponse resq) {
        HttpSession session = request.getSession();
//        从前端获取输入的id pwd
        String user_id=request.getParameter("user_id");
        String user_pwd =request.getParameter("user_pwd");
        //        登陆成功
        try{
            String userid=userService.checkuser(user_id,user_pwd);
            session.setAttribute("user_id",userid);
//            普通用户
            if(userService.getUserDao().getUser(userid).getAdmin()==0){
                session.setAttribute("user_id",user_id);
                return "1";
            }
//            管理员
            else{
                session.setAttribute("user_id",user_id);
                return "2";
            }
        }
        //        登陆失败
        catch (Exception e){
            return e.getMessage();
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
    @RequestMapping(value="/modifyuser",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object ModifyUser(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

//        从前端获取输入的修改信息
        String user_id= (String)session.getAttribute("user_id");
        String n_name =request.getParameter("user_name");
        String n_pwd =request.getParameter("user_pwd");
        String n_pwd2 =request.getParameter("user_pwd2");
        try {
            BeanUser usr=userService.getUserDao().getUser(user_id);
//        没有修改
            if (n_pwd.equals(usr.getUser_pwd()) && n_name.equals(usr.getUser_name())){
                request.getSession().setAttribute("msg","nothing different");
                return "没有修改";
            }
            if(n_pwd.equals(n_pwd2)){
                usr.setUser_name(n_name);
                usr.setUser_pwd(n_pwd);
                userService.modifyUser(usr);
                return "1";
            }
        }
        catch (Exception e){
            return e.getMessage();
        }
        return "error";

    }



//    跳转至usr注册界面
    @RequestMapping("/showregister")
    public ModelAndView ShowRegister(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/register.jsp");
        return mv;
    }

//    usr注册
    @RequestMapping(value="/register",produces="text/plain;charset=UTF-8")
    @ResponseBody
    public Object Register(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

//        从前端获取输入的信息
        String user_id=request.getParameter("user_id");
        String n_name =request.getParameter("user_name");
        String n_pwd =request.getParameter("user_pwd");
        String n_pwd2 =request.getParameter("user_pwd2");
        try {
            userService.register(user_id, n_name, n_pwd, n_pwd2);
            return "1";
        }
        catch (Exception e){
            return e.getMessage();
        }
    }

}