package dao;

import bean.BeanUser;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("userDao")
public class UserDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    添加用户
    public void addUser(BeanUser user){
        String sql2 = "insert into user_t(user_id,user_name,user_pwd,admin) values(?,?,?,?)";
        jdbcTemplate.update(sql2,user.getUser_id(),user.getUser_name(),user.getUser_pwd(),user.getAdmin());
    }

//    获取用户
    public BeanUser getUser(String user_id)  {
        String sql ="select * from user_t where user_id = ?";
        RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
        BeanUser user = jdbcTemplate.queryForObject(sql,rowMapper,user_id);
        return user;
    }

//    更新用户
    public void updateUser(BeanUser beanUser) throws Exception {
        try {

            String sql = "update user_t set user_name = ? , user_pwd = ? where user_id = ?";
            jdbcTemplate.update(sql,beanUser.getUser_name(),beanUser.getUser_pwd(),beanUser.getUser_id());

        } catch (Exception ex) {
            throw new Exception("updateUser failed");
        }
    }

//    删除用户
    public void deleteUser(String userid){
        String sql ="delete from user_t where user_id = ?";
        RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
        jdbcTemplate.update(sql,rowMapper,userid);
    }

//    返回所有用户
    public List<BeanUser> findAllUsers(){
        String sql = "select * from user_t";
        RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
        return jdbcTemplate.query(sql,rowMapper);
    }

//    返回管理员
    public List<BeanUser> findAllAdmins(){
        String sql = "select * from user_t where admin = 1";
        RowMapper<BeanUser> rowMapper = new BeanPropertyRowMapper<BeanUser>(BeanUser.class);
        return jdbcTemplate.query(sql,rowMapper);
    }

}
