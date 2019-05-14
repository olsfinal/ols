/*
 * 
 * Copyright 2001 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc. Use is
 * subject to license terms.
 *  
 */

package cart;

import bean.BeanCommodity;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class ShoppingCart {
    HashMap<Integer, ShoppingCartItem> items = null;

    int numberOfItems = 0;

    public ShoppingCart() {
        items = new HashMap<Integer, ShoppingCartItem>();
    }

    public synchronized void add(int c_id, BeanCommodity commodity) {
        if (items.containsKey(c_id)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(c_id);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(commodity);
            items.put(c_id, newItem);
        }

        numberOfItems++;
    }

    public synchronized void remove(int c_id) {
        if (items.containsKey(c_id)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(c_id);
            scitem.decrementQuantity();

            if (scitem.getQuantity() <= 0)
                items.remove(c_id);

            numberOfItems--;
        }
    }

    public synchronized Collection<ShoppingCartItem> getItems() {
        return items.values();
    }

    protected void finalize() throws Throwable {
        items.clear();
    }

    public synchronized int getNumberOfItems() {
        return numberOfItems;
    }

    public synchronized double getTotal() {
        double amount = 0.0;

        for (Iterator i = getItems().iterator(); i.hasNext();) {
            ShoppingCartItem item = (ShoppingCartItem) i.next();
            BeanCommodity commodity = (BeanCommodity) item.getItem();

            amount += item.getQuantity() * commodity.getC_price();
        }
        return roundOff(amount);
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); // cents
        return val / 100.0;
    }

    public synchronized void clear() {
        items.clear();
        numberOfItems = 0;
    }
}
