<%@ page import="bean.BeanInfo" %>
<%@ page import="java.util.List" %><%--
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
    <title>地址选择</title>
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
<br>

<div id="choseinfo_root" style="margin-left: 250px;">
    <br>
    <p style="font-family: '华文隶书'; font-size:50px;">
        &nbsp;&nbsp;我的地址
    </p>
    <table class="altrowstable">
        <tr>
            <th width="120px">姓名</th>
            <th width="400px">地址</th>
            <th width="300px">电话</th>
            <th width="200px">选择</th>
        </tr>
    </table>
    <div v-for="(item,index) of infos">
        <table class="altrowstable">
            <tr v-if="index%2==0" class="oddrowcolor">
                <td width="120px">{{item.name}}</td>
                <td width="400px">{{item.address}}</td>
                <td width="300px">{{item.tel}}</td>
                <td width="200px">
                    <button class="button" @click="cashier(item.id)">选择</button>
                </td>
            </tr>
            <tr v-if="index%2==1" class="evenrowcolor">
                <td width="120px">{{item.name}}</td>
                <td width="400px">{{item.address}}</td>
                <td width="300px">{{item.tel}}</td>
                <td width="200px">
                    <button class="button" @click="cashier(item.id)">选择</button>
                </td>
            </tr>
        </table>
    </div>
    <br>
    <br>
    <button class="button2" @click="addinfo()">新增</button>
</div>
<script>
    new Vue({
        el:"#choseinfo_root",
        data:{
            infos:[]
        },
        mounted:function () {
            <%
            List<BeanInfo> bis=(List<BeanInfo>) session.getAttribute("infosList");
            for(BeanInfo bi:bis){
		    %>
            var bi = new Object();
            bi.id=<%=bi.getInfo_id() %>;
            bi.address="<%=bi.getAddress() %>";
            bi.tel="<%=bi.getTel() %>";
            bi.name="<%=bi.getI_name() %>";
            this.infos.push(bi);
            <%
                }
            %>
        },
        methods:{
            cashier:function(iid){
                var params = new Object();
                params.info_id = iid;
                axios.get('cashier' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("付款成功");
                            //付款成功 跳转至已付款的orders界面
                            var params2 = new Object();
                            params2.o_state = 1;
                            axios.get('orders' , {params:params2})
                                .then(function (res2) {
                                    console.log(res2);
                                    if(res2.data=="1"){
                                        location.href = "showorders";
                                    }
                                    else{
                                        alert(res.data);
                                    }
                                })
                                .catch(function (error) { // 请求失败处理
                                    alert(error);
                                })

                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })

            },
            addinfo:function () {
                <%session.setAttribute("caller","choseinfo");%>
                location.href = "showaddinfos";

            },
        },


    })
</script>
</body>
</html>
