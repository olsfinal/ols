<%@ page import="bean.BeanCommodity" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Collection" %>
<%@ page import="cart.ShoppingCart" %>
<%@ page import="bean.BeanUser" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户目录</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/table.css" media="screen" type="text/css" />
</head>
<body>
<!-- 侧边栏 -->
<%@include file="aleft.jsp" %>

<!-- 主要部分 -->
<div id="users_root">
    <div style="margin-left: 250px;">
        <br>
        <br>
        <table class="altrowstable">
            <tr>
                <th width="150px">用户id</th>
                <th width="150px">用户姓名</th>
                <th width="150px">用户密码</th>
                <th width="200px">操作</th>
            </tr>
        </table>
        <div v-for="(item,index) of users"  >
            <table class="altrowstable" id="alternatecolor">
                <tr v-if="index%2==0" class="oddrowcolor">
                    <td width="150px">{{item.id}}</td>
                    <td width="150px">{{item.name}}</td>
                    <td width="150px">{{item.pwd}}</td>
                    <td width="200px">
                        <button class="button1" @click="repwd(item.id)" style="width: 150px;"><span>重置密码</span></button>
                    </td>
                </tr>
                <tr v-if="index%2==1" class="evenrowcolor">
                    <td width="150px">{{item.id}}</td>
                    <td width="150px">{{item.name}}</td>
                    <td width="150px">{{item.pwd}}</td>
                    <td width="200px">
                        <button class="button1" @click="repwd(item.id)" style="width: 150px;"><span>重置密码</span></button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</div>
<script>
    new Vue({
        el:"#users_root",
        data:{
            users:[]
        },
        mounted:function () {
            <%
            List<BeanUser> bcs= (List<BeanUser>) session.getAttribute("users");
            for(BeanUser bc:bcs){
		    %>
            var bc = new Object();
            bc.id="<%=bc.getUser_id() %>";
            bc.name="<%=bc.getUser_name() %>";
            bc.pwd="<%=bc.getUser_pwd() %>";
            this.users.push(bc);
            <%
                }
            %>
        },
        methods:{
            repwd:function(cid){
                var params = new Object();
                params.c_id=cid;
                axios.get('rechargeuser' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("密码已成功重置为：123456")
                            location.href = "showusers";
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
