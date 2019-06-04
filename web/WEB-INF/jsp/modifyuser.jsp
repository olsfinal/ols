<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/1
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>修改信息</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <style type="text/css">
        body{
            text-align:center;
            background: url('https://unsplash.imgix.net/44/9s1lvXLlSbCX5l3ZaYWP_hdr-1.jpg?q=75&fm=jpg&s=fd39ab9358b1aec7746ee67168ccf268') no-repeat center center fixed;
            background-size: cover;

        }
        #modifyuser_root{
            font-family:"Microsoft YaHei";
            font-size:1em;
            color:  #34495e;
            background-color:rgba(255, 255, 255, 0.801);
            margin:auto auto;
            width: 250px;
            height: 320px;
            border: 0.05em solid #34495e;
            border-radius: 0.5em;
        }
        #modifyuser_root button{
            width: 163px;
            background-color: rgba(2, 108, 194, 0.322);
        }
    </style>
</head>



<body>
<!-- 侧边栏 -->
<%@include file="left.jsp" %>

<%--主要部分--%>
<div style="height: 200px;"></div>
<div id="modifyuser_root">
    <br>
    <br>
    <p>
        <span>新的姓名: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_name" type="text">
    </p>
    <p>
        <span>新的密码: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_pwd" type="password">
    </p>
    <p>
        <span>确认密码: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_pwd2" type="password">
    </p>
    <button @click="confim()">确认</button>
</div>

<script>
    new Vue({
        el: "#modifyuser_root",
        data :{
            user_name: "",
            user_pwd: "",
            user_pwd2: ""
        },
        methods: {
            confim : function() {
                var params = new Object();
                params.user_name=this.user_name;
                params.user_pwd=this.user_pwd;
                params.user_pwd2=this.user_pwd2;
                axios.get('modifyuser' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("修改成功");
                            location.href = "index";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            },
        }
    })
</script>

</body>
</html>
