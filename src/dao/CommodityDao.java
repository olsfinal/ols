package dao;

import bean.BeanCommodity;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.*;

@Repository("commodityDao")
public class CommodityDao {
    @Resource(name = "jdbcTemplate")
    private JdbcTemplate jdbcTemplate;

    public int addCommodity(BeanCommodity commodity) {
        String sql = "insert into commodity_t(c_name,c_price,c_inventory,c_img,c_detail,c_type) value(?,?,?,?,?,?)";
        Object[] objects=new Object[]{
                commodity.getC_name(),
                commodity.getC_price(),
                commodity.getC_inventory(),
                commodity.getC_img(),
                commodity.getC_detail(),
                commodity.getC_type()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int updateCommodity(BeanCommodity commodity) {
        String sql = "update commodity_t set c_name = ? ,c_price = ? ,c_inventory ," +
                "c_img = ? ,c_detail = ? ,c_type = ?" +
                " where c_id = ?";
        Object[] objects=new Object[]{
                commodity.getC_name(),
                commodity.getC_price(),
                commodity.getC_inventory(),
                commodity.getC_img(),
                commodity.getC_detail(),
                commodity.getC_type(),
                commodity.getC_id()
        };
        int num = this.jdbcTemplate.update(sql,objects);
        return num;
    }

    public int deleteCommodity(int c_id) {
        String sql = "delete from commodity_t where c_id = ?";
        int num = this.jdbcTemplate.update(sql,c_id);
        return num;
    }

    public BeanCommodity findCommodityById(int c_id) {
        String sql = "select * from commodity_t where c_id = ?";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,c_id);
    }

    public List<BeanCommodity> findAllCommodity() {
        String sql = "select * from commodity_t";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

}
