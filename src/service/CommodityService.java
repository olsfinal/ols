package service;

import bean.BeanCommodity;
import dao.CommodityDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

@Service("commodityService")
public class CommodityService {
    @Resource(name = "commodityDao")
    private CommodityDao commodityDao;

    private List<BeanCommodity> commoditys;

    public CommodityDao getCommodityDao() {
        return commodityDao;
    }

    public void setCommodityDao(CommodityDao commodityDao) {
        this.commodityDao = commodityDao;
    }

    //  获取所有商品的集合
    public Collection<BeanCommodity> getCommoditys() throws Exception {
        commoditys = new ArrayList<>();
        String sql = "select * from commodity_t";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        List<BeanCommodity> commodityDetails = jdbcTemplate.query(sql,rowMapper);
        for (BeanCommodity beanCommodity:commodityDetails)
            commoditys.add(beanCommodity);
        Collections.sort(commoditys);
        return commoditys;
    }
    //  获取一件商品
    public BeanCommodity getCommodity(int c_id) throws Exception {
        String sql = "select * from commodity_t where c_id = ?";
        RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
        return jdbcTemplate.queryForObject(sql,rowMapper,c_id);
    }
    //   购买购物车内所有商品
    public void buyCommoditys(String user_id, ShoppingCart cart, OrderServiceImpl orderService) throws Exception {
        Collection items = cart.getItems();
        Iterator i = items.iterator();
        try {
//        生成订单
            int order_id = orderService.getOrderDao().generateOrder(user_id);
            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                BeanCommodity bc = (BeanCommodity) sci.getItem();
                String c_id = bc.getC_id();
                int od_number = sci.getQuantity();
                float od_price = bc.getPrice();
                buyCommodity(c_id, od_number);
//            生成订单细节
                orderService.getOrderDAO().generateOrderDetail(order_id,c_id,od_number,od_price);
            }

        } catch (Exception ex) {
            throw new Exception("Transaction failed: "
                    + ex.getMessage());
        }
    }
    //  更新商品存货（购买商品时）
    private void buyCommodity(String c_id, int od_number) throws Exception {
        try {
            String sql = "select * from commodity_t where c_id = ? ";
            RowMapper<BeanCommodity> rowMapper = new BeanPropertyRowMapper<BeanCommodity>(BeanCommodity.class);
            BeanCommodity beanCommodity = jdbcTemplate.queryForObject(sql,rowMapper,c_id);
            if (beanCommodity != null){
                int inventory = beanCommodity.getC_inventory();
                if ((inventory - od_number) >= 0){
                    String sql2 = "update commodity_t set c_inventory = c_inventory - ? where c_id = ?";
                    jdbcTemplate.update(sql2,od_number,c_id);
                }else
                    throw new Exception("Not enough of " + c_id);
            }
        } catch (Exception ex) {
            throw new Exception("Couldn't purchase commodity: " + c_id + ex.getMessage());
        }
    }
}
