package bean;

import java.util.Date;

public class BeanOrder {
    private int order_id;
    private Date time;
    private String user_id;
    private String o_address;
    private String o_tel;

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getO_address() {
        return o_address;
    }

    public void setO_address(String o_address) {
        this.o_address = o_address;
    }

    public String getO_tel() {
        return o_tel;
    }

    public void setO_tel(String o_tel) {
        this.o_tel = o_tel;
    }
}
