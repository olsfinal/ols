package controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@org.springframework.stereotype.Controller
public class UserController {

    @RequestMapping("/login")
    public ModelAndView Login(HttpServletRequest req, HttpServletResponse resq) throws Exception{
        ModelAndView mv=new ModelAndView();
        mv.setViewName("/WEB-INF/jsp/login.jsp");
        return mv;
    }

    @RequestMapping("/modifyuser")
    public ModelAndView ModifyUser(HttpServletRequest req, HttpServletResponse resq) throws Exception {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        UserServiceImpl userService = (UserServiceImpl) request.getSession().getAttribute("userService");
        String user_id=request.getParameter("user_id");
        BeanUser usr=userService.getUser(user_id);
        String n_name =request.getParameter("user_name");
        String n_pwd =request.getParameter("user_pwd");
        String n_pwd2 =request.getParameter("user_pwd2");
        if(n_pwd.equals(n_pwd2)){
            usr.setUser_name=n_name;
            usr.setUser_pwd=n_pwd;
            userService.updateUser(usr);
            mv.setViewName("/WEB-INF/jsp/index.jsp");
            return mv;
        }
        else {
            throw new Exception("pwd formal error or inconformity");
        }
    }

    @RequestMapping("/checklogin")
    public ModelAndView Checklogin(HttpServletRequest req, HttpServletResponse resq) {
        HttpServletRequest request = (HttpServletRequest)req;
        HttpSession session = request.getSession();

        ModelAndView mv=new ModelAndView();
        UserServiceImpl userService = (UserServiceImpl) request.getSession().getAttribute("userService");
        String user_id=request.getParameter("user_id");
        String user_pwd =request.getParameter("user_pwd");
        try{
            String userid=userService.checkuser(user_id);
            request.getSession().setAttribute("user_id",userid);
            mv.setViewName("/WEB-INF/jsp/index.jsp");
            return mv;
        }
        catch (Exception e){
            request.getSession().setAttribute("errmsg","your id or pwd error");
            mv.setViewName("/WEB-INF/jsp/login.jsp");
            return mv;
        }
    }


}