<%@ page import="bean.BeanInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="cart.ShoppingCart" %>
<%@ page import="cart.ShoppingCartItem" %><%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/1
  Time: 17:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>购物车</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <%--need    info.css--%>
    <link rel="stylesheet" href="css/table.css" media="screen" type="text/css" />
</head>


<body>
<!-- 侧边栏 -->
<%@include file="left.jsp" %>

<%--主要部分--%>

<div id="cart_root" style="margin-left: 250px;">
    <br>
    <p style="font-family: '华文隶书'; font-size:50px;">
        我的购物车
    </p>
    <table class="altrowstable">
        <tr>
            <th width="150px">数量</th>
            <th width="150px">商品名称</th>
            <th width="150px">商品价格</th>
            <th width="400px">操作</th>
        </tr>
    </table>
    <div v-for="(item,index) of carts" >
        <table class="altrowstable">
            <tr v-if="index%2==0" class="oddrowcolor">
                <td width="150px">{{item.quantity}}</td>
                <td width="150px">{{item.name}}</td>
                <td width="150px">{{item.price}}</td>
                <td width="400px">
                    <button class="button1" @click="show_detail(item.id)"><span>详情</span></button>
                    <button class="button1" @click="addc(item.id)"><span>增加</span></button>
                    <button class="button1" @click="delc(item.id)"><span>减少</span></button>
                </td>
            </tr>
            <tr v-if="index%2==1" class="evenrowcolor">
                <td width="150px">{{item.quantity}}</td>
                <td width="150px">{{item.name}}</td>
                <td width="150px">{{item.price}}</td>
                <td width="400px">
                    <button @click="show_detail(item.id)"><span>详情</span></button>
                    <button @click="addc(item.id)"><span>增加</span></button>
                    <button @click="delc(item.id)"><span>减少</span></button>
                </td>
            </tr>

        </table>
    </div>
    <br>
    <br>
    <p>
        <button class="button2"  @click="choseinfo()"><span>付款</span></button>
    </p>

</div>
<script>
    new Vue({
        el:"#cart_root",
        data:{
            carts:[]
        },
        //初始化
        mounted:function () {
            <%
            ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }
            for(ShoppingCartItem item:cart.getItems()){
		    %>
            var bi = new Object();
            bi.quantity=<%=item.getQuantity() %>;
            bi.name="<%=item.getItem().getC_name() %>";
            bi.price="<%=item.getItem().getC_price() %>";
            bi.id="<%=item.getItem().getC_id() %>";
            this.carts.push(bi);
            <%
                }
            %>
        },

        methods:{
            show_detail:function(iid){
                var params = new Object();
                params.c_id=iid;
                axios.get('commoditydetails' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            location.href = "showcdetails";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })

            },
            addc:function(iid){
                var params = new Object();
                params.c_id=iid;
                axios.get('cartadd' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            location.href = "showcart";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })

            },
            delc:function(iid){
                var params = new Object();
                params.c_id=iid;
                axios.get('cartdel' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            location.href = "showcart";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })

            },
            choseinfo:function () {
                <%
                    if(cart.getItems().isEmpty()){
                %>  alert("购物车为空")
                    return
                <%

                    }
                %>
                axios.get('infos')
                    .then(function (res) {
                        console.log(res);
                        location.href = "showchoseinfo";
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            }
        },


    })
</script>
</body>
</html>
