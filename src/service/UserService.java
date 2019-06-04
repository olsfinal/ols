package service;

import bean.BeanUser;
import dao.UserDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserService {
    @Resource(name = "userDao")
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

//    登录
    public String checkuser(String userid, String pwd) throws Exception {
        if (userid == null || userid.length() == 0) throw new Exception("请输入用户名");
        if (pwd == null) throw new Exception("请输入密码");

        BeanUser user = userDao.getUser(userid);
        if(!user.getUser_pwd().equals(pwd)) throw new Exception("密码错误");

        return user.getUser_name();
    }

//    注册
    @Transactional
        public void register(String userid, String username, String pwd ,String pwd2) throws Exception{
        if (userid == null || userid.length() == 0) throw new Exception("请输入用户名");
        if (username == null || username.length() == 0) throw new Exception("请输入昵称");
        if (pwd == null || pwd.length() == 0) throw new Exception("请输入密码");
        if (!pwd.equals(pwd2)) throw new Exception("两次输入的密码不一致");

        BeanUser user = userDao.getUser(userid);

        if(user!=null) throw new Exception("用户已存在");

        BeanUser user1 = new BeanUser();
        user1.setUser_id(userid);
        user1.setUser_name(username);
        user1.setUser_pwd(pwd);
        user1.setAdmin(0);
        userDao.addUser(user1);
    }

//    任命管理员
    public void appointAdmin(String userid) throws Exception {
        if (userid == null || userid.length() == 0) throw new Exception("没有该用户");
        BeanUser user = userDao.getUser(userid);
        if(user.getAdmin()==1) throw new Exception("该用户已是管理员");
        user.setAdmin(1);
        userDao.updateUser(user);
    }

//    修改用户
    public void modifyUser(BeanUser user) throws Exception {
        if (user.getUser_id() == null || user.getUser_id().length() == 0) throw new Exception("没有该用户");
        BeanUser user1 = userDao.getUser(user.getUser_id());
        if (user1 == null) throw new Exception("没有该用户");
        if(user.getUser_name()!=null)user1.setUser_name(user.getUser_name());
        if(user.getUser_pwd()!=null)user1.setUser_pwd(user.getUser_pwd());
        userDao.updateUser(user1);
    }

//    删除用户
    public void deleteUser(String userid){
        userDao.deleteUser(userid);
    }

//    重置密码
    public void resetPasswd(String usrid) throws Exception {
        BeanUser user = userDao.getUser(usrid);
        user.setUser_pwd("123456");
        userDao.updateUser(user);
    }

//    展示所有用户
    public List<BeanUser> loadAllUsers(){
        return userDao.findAllUsers();
    }
}
