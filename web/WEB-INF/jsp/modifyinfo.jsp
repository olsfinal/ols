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
    <title>修改地址</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <style type="text/css">
        body{
            text-align:center;
            background: url('https://unsplash.imgix.net/44/9s1lvXLlSbCX5l3ZaYWP_hdr-1.jpg?q=75&fm=jpg&s=fd39ab9358b1aec7746ee67168ccf268') no-repeat center center fixed;
            background-size: cover;

        }
        #addinfos_root{
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
        #addinfos_root button{
            width: 75px;
            background-color: rgba(2, 108, 194, 0.322);
        }
    </style>
</head>



<body>
<div id="addinfos_root">
    <p>
        <span>新的姓名: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="i_name" type="text">
    </p>
    <p>
        <span>新的地址: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="address" type="text">
    </p>
    <p>
        <span>新的电话: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
        <input v-model="tel" type="text">
    </p>
    <button @click="confim()">确认</button>
    <button @click="goback()">返回</button>
</div>

<script>
    new Vue({
        el: "#addinfos_root",
        data :{
            address: "",
            i_name: "",
            tel: "",
        },
        mounted: function(){
            this.address="<%=(String) session.getAttribute("address") %>";
            this.i_name="<%=(String) session.getAttribute("i_name") %>";
            this.tel="<%=(String) session.getAttribute("tel") %>";
        },
        methods: {
            confim : function() {
                alert("confirm");
                var params = new Object();
                params.address = this.address;
                params.i_name=this.i_name;
                params.tel=this.tel;
                axios.get('modifyinfos' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("修改成功");
                            location.href = "showinfos";
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
                location.href = "showinfos";
            },
        }
    })
</script>

</body>
</html>
