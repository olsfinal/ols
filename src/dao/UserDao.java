package dao;

import bean.BeanUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDao")
public class UserDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    普通用户注册
//    public void user_register(String user_id, String user_name, String user_pwd, String confirmPwd) throws Exception {
//        if (user_id.equals(""))
//            throw new Exception("用户名不能为空");
//        if (user_pwd.equals("") || confirmPwd.equals(""))
//            throw new Exception("密码不能为空");
//        if (!user_pwd.equals(confirmPwd))
//            throw new Exception("两次密码输入不一致");
//        if (user_name.equals(""))
//            throw new Exception("用户姓名不能为空");
//        try {
//            String sql = "select * from user_t where user_id=?";
//            RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
//            List<BeanUser> users = jdbcTemplate.query(sql,rowMapper,user_id);
//            if (users.size() != 0)
//                throw new Exception();
//            else{
//                String sql2 = "insert into user_t(user_id,user_name,user_pwd,admin) values(?,?,?,?)";
//                jdbcTemplate.update(sql2,user_id,user_name,user_pwd,0);
//            }
//        }catch (Exception e){
//            throw new Exception("用户名已存在");
//        }
//    }

//    用户密码正确则返回user_id
//    public String checkuser(String user_id, String user_pwd) throws Exception {
//        if (user_id == null || user_id.equals(""))
//            throw new Exception("请输入用户名");
//        String username = null;
//        if (user_pwd == null)
//            user_pwd = "";
//        try {
//            String sql ="select * from user_t where user_id = ?";
//            RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
//            BeanUser user = jdbcTemplate.queryForObject(sql,rowMapper,user_id);
//            if (user != null){
//                if (!user_pwd.equals(user.getUser_pwd())){
//                    throw new Exception("密码错误");
//                }
//            }
//            else
//                throw new Exception("用户不存在");
//
//        }catch (Exception e){
//            throw new Exception("用户不存在");
//        }
//        return user_id;
//    }

    public void addUser(BeanUser user){
        String sql2 = "insert into user_t(user_id,user_name,user_pwd,admin) values(?,?,?,?)";
        jdbcTemplate.update(sql2,user.getUser_id(),user.getUser_name(),user.getUser_pwd(),user.getAdmin());
    }

    public BeanUser getUser(String user_id) throws Exception {
        String sql ="select * from user_t where user_id = ?";
        RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
        BeanUser user = jdbcTemplate.queryForObject(sql,rowMapper,user_id);
        return user;
    }

    public void updateUser(BeanUser beanUser) throws Exception {
        try {

            String sql = "update user_t set user_name = ? , user_pwd = ? where user_id = ?";
            jdbcTemplate.update(sql,beanUser.getUser_name(),beanUser.getUser_pwd(),beanUser.getUser_id());

        } catch (Exception ex) {
            throw new Exception("updateUser failed");
        }
    }

}
