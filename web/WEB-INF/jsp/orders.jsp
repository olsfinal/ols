<%@ page import="bean.BeanInfo" %>
<%@ page import="java.util.List" %>
<%@ page import="bean.BeanOrder" %><%--
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
    <title>我的订单</title>
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
<div id="orders_root" style="margin-left: 250px;">
    <br>
    <p style="font-family: '华文隶书'; font-size:50px;">
        &nbsp;&nbsp;我的订单({{pagename}})
    </p>
    <br>
    <br>
    <table class="htable">
        <tr>
            <th width="103px">订单号</th>
            <th width="203px">订单时间</th>
            <th width="103px">收货人</th>
            <th width="203px">地址</th>
            <th width="123px">电话</th>
            <th width="103px">订单状态</th>
            <th width="283px">操作</th>
        </tr>
    </table>
    <div v-for="(item,index) of orders" class="mtable">
        <table>
            <tr>
                <th width="100px">{{item.id}}</th>
                <th width="200px">{{item.time}}</th>
                <th width="100px">{{item.name}}</th>
                <th width="200px">{{item.address}}</th>
                <th width="120px">{{item.tel}}</th>
                <th width="100px">{{item.state}}</th>
                <th width="280px">
                    <button v-show="item.show" @click="drawback(item.id)">退款</button>
                    <button v-show="item.show" @click="confimget(item.id)">收货</button>
                    <button @click="orderdetail(item.id)">详情</button>
                </th>
            </tr>
        </table>
    </div>
</div>
<script>
    new Vue({
        el:"#orders_root",
        data:{
            pagename:"所有订单",
            orders:[],
        },
        //初始化
        mounted:function () {
            var pageid=<%=session.getAttribute("pageid") %>;
            if(pageid=="1")this.pagename="待收货";
            else if(pageid=="2")this.pagename="交易完成";
            else if(pageid=="3")this.pagename="审核退款中";
            else if(pageid=="4")this.pagename="已退款";
            else if(pageid=="5")this.pagename="退款失败";
            <%
            List<BeanOrder> bos= (List<BeanOrder>) session.getAttribute("ordersList");
            for(BeanOrder bo:bos){
		    %>
                var bo = new Object();
                bo.id=<%=bo.getOrder_id() %>;
                bo.time="<%=bo.getO_time() %>";
                bo.name="<%=bo.getUser_name() %>";
                bo.address="<%=bo.getO_address() %>";
                bo.tel="<%=bo.getO_tel() %>";
                bo.state="<%=bo.getO_state() %>";
                //用于标明是否有退款按钮
                bo.show=false;
                if(bo.state=="1"){
                    bo.state="待收货";
                    bo.show=true;
                }
                else if(bo.state=="2")bo.state="交易完成";
                else if(bo.state=="3")bo.state="审核退款中";
                else if(bo.state=="4")bo.state="已退款";
                else if(bo.state=="5")bo.state="退款失败";
                this.orders.push(bo);
            <%
            }
            %>


        },


        methods:{
            confimget:function(oid){
                var params = new Object();
                params.order_id = oid;
                params.state = 2;
                axios.get('requestchange' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("收货成功");
                            location.href = "showorders";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            },
            drawback:function(oid){
                var params = new Object();
                params.order_id = oid;
                params.state = 3;
                axios.get('requestchange' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("退款成功");
                            location.href = "showorders";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            },
            orderdetail:function(oid){
                var params = new Object();
                params.order_id = oid;
                axios.get('orderdetails' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            location.href = "showorderdetails";
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
