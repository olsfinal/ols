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
