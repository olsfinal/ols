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
    <title>注册</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <style type="text/css">
        body{
            text-align:center;
            background: url('https://unsplash.imgix.net/44/9s1lvXLlSbCX5l3ZaYWP_hdr-1.jpg?q=75&fm=jpg&s=fd39ab9358b1aec7746ee67168ccf268') no-repeat center center fixed;
            background-size: cover;

        }
        #register_root{
            font-family:"Microsoft YaHei";
            font-size:1em;
            color:  #34495e;
            background-color:rgba(255, 255, 255, 0.801);
            margin:170px auto;
            width: 250px;
            height: 320px;
            border: 0.05em solid #34495e;
            border-radius: 0.5em;
        }
        #register_root button{
            width: 75px;
            background-color: rgba(2, 108, 194, 0.322);
        }
    </style>
</head>



<body>
<div id="register_root">
    <p>
        <span>账户: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_id" type="text">
    </p>
    <p>
        <span>姓名: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_name" type="text">
    </p>
    <p>
        <span>密码: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_pwd" type="password">
    </p>
    <p>
        <span>确认密码:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="user_pwd2" type="password">
    </p>
    <button @click="confim()">确认</button>
    <button @click="goback()">返回</button>
</div>

<script>
    new Vue({
        el: "#register_root",
        data :{
            user_id: "",
            user_name: "",
            user_pwd: "",
            user_pwd2: ""
        },
        methods: {
            confim : function() {
                var params = new Object();
                params.user_id = this.user_id;
                params.user_name=this.user_name;
                params.user_pwd=this.user_pwd;
                params.user_pwd2=this.user_pwd2;
                axios.get('register' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            location.href = "login";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            },
            goback : function () {
                location.href = "login";
            },
        }
    })
</script>

</body>
</html>
