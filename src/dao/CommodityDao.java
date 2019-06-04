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

//    添加商品
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

//    更新商品
    public int updateCommodity(BeanCommodity commodity) {
        String sql = "update commodity_t set c_name = ? ,c_price = ? ,c_inventory =?," +
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

//    删除商品
    public int deleteCommodity(int c_id) {
        String sql = "delete from commodity_t where c_id = ?";
        int num = this.jdbcTemplate.update(sql,c_id);
        return num;
    }

//    根据id返回商品
    public BeanCommodity findCommodityById(int c_id) {
        String sql = "select * from commodity_t where c_id = ?";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return this.jdbcTemplate.queryForObject(sql, rowMapper,c_id);
    }


//    返回所有商品
    public List<BeanCommodity> findAllCommodity() {
        String sql = "select * from commodity_t";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return this.jdbcTemplate.query(sql, rowMapper);
    }

//    根据类别返回商品
    public List<BeanCommodity> findCommodityByType(String c_type) {
        String sql = "select * from commodity_t where c_type = ?";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return this.jdbcTemplate.query(sql, rowMapper,c_type);
    }

}

