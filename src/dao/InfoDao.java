package dao;

import bean.BeanInfo;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository("infoDao")
public class InfoDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

//    新建地址
    public void addInfo(BeanInfo beanInfo){
        String sql = "insert into info_t(user_id,address,i_name,tel) values(?,?,?,?)";
        jdbcTemplate.update(sql,beanInfo.getUser_id(),beanInfo.getAddress(),beanInfo.getI_name(),beanInfo.getTel());
    }

//    获取某用户所有地址
    public List<BeanInfo> loadInfos(String user_id){
        String sql = "select * from info_t where user_id = ?";
        RowMapper<BeanInfo> rowMapper = new BeanPropertyRowMapper<BeanInfo>(BeanInfo.class);
        return jdbcTemplate.query(sql,rowMapper,user_id);
    }

//    获取所有地址
    public List<BeanInfo> loadInfos(){
        String sql = "select * from info_t";
        RowMapper<BeanInfo> rowMapper = new BeanPropertyRowMapper<BeanInfo>(BeanInfo.class);
        return jdbcTemplate.query(sql,rowMapper);
    }
//    按id获取地址
    public BeanInfo loadInfoById(int info_id){
        try{
            String sql = "select * from info_t where info_id = ?";
            RowMapper<BeanInfo> rowMapper = new BeanPropertyRowMapper<BeanInfo>(BeanInfo.class);
            return jdbcTemplate.queryForObject(sql,rowMapper,info_id);
        }
        catch (Exception e){
            return null;
        }
    }

//    更新地址
    public void updateInfo(BeanInfo beanInfo) {

        String sql = "update info_t set address = ? , i_name = ? , tel = ? where info_id = ?";
        jdbcTemplate.update(sql,beanInfo.getAddress(),beanInfo.getI_name(),beanInfo.getTel(),beanInfo.getInfo_id());

    }
//    删除地址
    public void delInfo(int info_id) {
        String sql = "delete from info_t where info_id = ?";
        jdbcTemplate.update(sql,info_id);
    }
}
