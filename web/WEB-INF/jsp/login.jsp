<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>登陆</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <style type="text/css">
        body{
            text-align:center;
            background: url('https://unsplash.imgix.net/44/9s1lvXLlSbCX5l3ZaYWP_hdr-1.jpg?q=75&fm=jpg&s=fd39ab9358b1aec7746ee67168ccf268') no-repeat center center fixed;
            background-size: cover;

        }
        #login_root{
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
        #login_root button{
            width: 75px;
            background-color: rgba(2, 108, 194, 0.322);
        }
    </style>
</head>



<body>
    <div id="login_root">
        <br>
        <br>
        <br>
        <p>
            <span>账户: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="user_id" type="text">
        </p>
        <p>
            <span>密码: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="user_pwd" type="password">
        </p>
        <button @click="lconfim()">确认</button>
        <button @click="register()">注册</button>
    </div>

    <script>
        new Vue({
            el: "#login_root",
            data :{
                user_id: "",
                user_pwd: ""
            },
            methods: {
                lconfim : function() {
                    var params = new Object();
                    params.user_id = this.user_id;
                    params.user_pwd=this.user_pwd;
                    console.log(params);
                    axios.get('checklogin' , {params:params})
                        .then(function (res) {
                            console.log(res);
                            if(res.data=="1"){
                                //总商品目录
                                location.href = "index";
                            }
                            else if(res.data=="2"){
                                //管理员目录
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
                register : function() {
                    location.href = "showregister";
                }
            }
        })
    </script>

</body>
</html>