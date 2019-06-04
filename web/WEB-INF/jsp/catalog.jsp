<%@ page import="bean.BeanCommodity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="cart.ShoppingCart" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>商品目录</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/catalog.css" media="screen" type="text/css" />
</head>
<body>
<!-- 侧边栏 -->
<%@include file="left.jsp" %>

<!-- 主要部分 -->
<div id="catalog_root">
    <div style="margin-left: 250px;">
        <div v-for="(item,index) of commoditys" class="catalog_class" >
            <img :src="item.img" @click="show_c(item.id)">
            <strong>￥{{item.price}}</strong><br>
            <span>{{item.name}}</span>
            <br><br><br><br><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
            <button @click="chose_c(item.id)" style="font-family: '宋体';font-size: 22px;
                font-weight:500;color: white; border: 0.5em groove rgba(19, 135, 4, 0);cursor: pointer;
                background-color: rgba(255,7,3,0.78);height:40px;width: 150px;">
                加入购物车
            </button>
        </div>
    </div>
</div>
<script>
    new Vue({
        el:"#catalog_root",
        data:{
            commoditys:[]
        },
        mounted:function () {
            <%
            List<BeanCommodity> bcs= (List<BeanCommodity>) session.getAttribute("commoditys");
            for(BeanCommodity bc:bcs){
		    %>
            var bc = new Object();
            bc.id=<%=bc.getC_id() %>;
            bc.price=<%=bc.getC_price() %>;
            bc.name="<%=bc.getC_name() %>";
            bc.img="<%=bc.getC_img() %>";
            this.commoditys.push(bc);
            <%
                }
            %>
        },
        methods:{
            show_c :function(cid){
                var params = new Object();
                params.c_id=cid;
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

            chose_c :function(cid) {
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
