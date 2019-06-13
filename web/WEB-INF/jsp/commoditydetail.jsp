
<%@ page import="java.util.List" %>
<%@ page import="service.CommodityService" %>
<%@ page import="bean.BeanOrderdetail" %>
<%@ page import="bean.BeanCommodity" %>
<%@ page import="cart.ShoppingCart" %><%--
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
    <title>商品细节</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <%--need    info.css--%>
    <link rel="stylesheet" href="css/commoditydetail.css" media="screen" type="text/css" />
</head>


<body>
<!-- 侧边栏 -->
<%@include file="left.jsp" %>

<%--主要部分--%>
<div id="cdroot" class="wrap">
    <div class="prosum_box">
        <dl class="prosum_left">
            <img alt="" class="big_img" :src="commodity.img">
        </dl>
        <div class="prosum_right">
            <p class="pros_title">{{commodity.name}}</p>
            <%--<p class="hot">1-2月出发，网付立享￥1099/2人起！爆款位置有限，抢完即止！</p>--%>
            <div class="pros_other">
                <p>经营商家  ：地猫🐱</p>
                <p>咨询电话 : 131313131313</p>
                <p>存货 ： {{commodity.inventory}}</p>
            </div>
            <div class="pros_price">
                <p class="price">抢购价：<strong>¥{{commodity.price}}</strong></p>
                <p class="collect">
						   <span>
								<a @click="addtocart(commodity.id)" class="btn">
                                    <i class="glyphicon glyphicon-heart-empty"></i>加入购物车</a>
							</span>
                </p>
            </div>
        </div>
    </div>
    <div class="you_need_konw">
        <span>详细介绍</span>
        <div v-for="(item,index) of details" class="notice" >
            <p>•&nbsp;&nbsp;&nbsp;&nbsp;{{item}}</p>
            <br>
        </div>
    </div>
</div>
<script>
    new Vue({
        el:"#cdroot",
        data:{
            commodity:Object,
            details:[],
        },
        //初始化
        mounted:function () {
            <%
                BeanCommodity bc = (BeanCommodity) session.getAttribute("commodity");
            %>
            var commodity = new Object();
            commodity.id=<%=bc.getC_id()%>;
            commodity.name="<%=bc.getC_name()%>";
            commodity.price="<%=bc.getC_price()%>";
            commodity.inventory=<%=bc.getC_inventory()%>;
            commodity.img="pic/"+"<%=bc.getC_img()%>";
            commodity.detail="<%=bc.getC_detail()%>";
            this.details=commodity.detail.split(",");
            this.commodity=commodity;
        },


        methods:{
            addtocart :function(cid) {
                <%
                    ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");
                    if (cart == null) {
                        cart = new ShoppingCart();
                        session.setAttribute("cart", cart);
                    }
                %>
                var params = new Object();
                params.c_id=cid;
                axios.get('cartadd' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("添加至购物车成功");
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            },
        },


    })
</script>
</body>
</html>
