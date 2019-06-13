
<%@ page import="java.util.List" %>
<%@ page import="service.CommodityService" %>
<%@ page import="bean.BeanOrderdetail" %><%--
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
    <title>订单细节</title>
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
<div id="orderdetails_root" style="margin-left: 250px;">
    <br>
    <p style="font-family: '华文隶书'; font-size:50px;">
        &nbsp;&nbsp;订单细节
    </p>
    <br>
    <br>
    <table class="htable">
        <tr>
            <th width="30%">商品名称</th>
            <th width="20%">数量</th>
            <th width="20%">价格</th>
            <th width="30%">操作</th>
        </tr>
    </table>
    <div v-for="(item,index) of orderdetails" >
        <table  class="altrowstable" id="alternatecolor" width="800px">
            <tr v-if="index%2==0" class="oddrowcolor">
                <td width="30%">{{item.name}}</td>
                <td width="20%">{{item.number}}</td>
                <td width="20%">{{item.price}}</td>
                <td width="30%">
                    <button class="button"  @click="cdetails(item.cid)" style="width:100%"><span>商品详情</span></button>
                </td>
            </tr>
            <tr v-if="index%2==1" class="evenrowcolor">
                <td width="30%">{{item.name}}</td>
                <td width="20%">{{item.number}}</td>
                <td width="20%">{{item.price}}</td>
                <td width="30%">
                    <button class="button" @click="cdetails(item.cid)" style="width:100%"><span>商品详情</span></button>
                </td>
            </tr>
        </table>
    </div>
</div>
<script>
    new Vue({
        el:"#orderdetails_root",
        data:{
            orderdetails:[],
        },
        //初始化
        mounted:function () {
            <%
            List<BeanOrderdetail> bods= (List<BeanOrderdetail>) session.getAttribute("orderdetailsList");
            for(BeanOrderdetail bod:bods){
                System.out.println(bod.getC_id());
                System.out.println(bod.getOd_number());
                System.out.println(bod.getC_name());
		    %>
                var bod = new Object();
                bod.cid=<%=bod.getC_id() %>;
                bod.number="<%=bod.getOd_number() %>";
                bod.price="<%=bod.getOd_price() %>";
                bod.name="<%=bod.getC_name()%>";
                this.orderdetails.push(bod);
            <%
            }
            %>


        },


        methods:{
            cdetails:function(cid){
                var params = new Object();
                params.c_id = cid;
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
        },


    })
</script>
</body>
</html>
