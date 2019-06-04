package bean;

public class BeanOrderdetail {
    private int order_id;
    private int c_id;
    private int od_number;
    private float od_price;
    private String c_name;

    public String getC_name() {
        return c_name;
    }

    public void setC_name(String c_name) {
        this.c_name = c_name;
    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public int getOd_number() {
        return od_number;
    }

    public void setOd_number(int od_number) {
        this.od_number = od_number;
    }

    public float getOd_price() {
        return od_price;
    }

    public void setOd_price(float od_price) {
        this.od_price = od_price;
    }
}
