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
    <title>修改商品信息</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <style type="text/css">
        body{
            text-align:-moz-left;
            background: url('https://unsplash.imgix.net/44/9s1lvXLlSbCX5l3ZaYWP_hdr-1.jpg?q=75&fm=jpg&s=fd39ab9358b1aec7746ee67168ccf268') no-repeat center center fixed;
            background-size: cover;

        }
        #modifycom_root{
            font-family:"Microsoft YaHei";
            font-size:1em;
            color:  #34495e;
            background-color:rgba(255, 255, 255, 0.801);
            margin:170px auto;
            width: 550px;
            height: 480px;
            border: 0.05em solid #34495e;
            border-radius: 0.5em;
        }
        #modifycom_root button{
            width: 75px;
            background-color: rgba(2, 108, 194, 0.322);
        }
        #main_root{
            margin-left: 30px;
            /*margin:170px auto;*/

        }
        #main_root textarea{
            height:60px;
            width: 400px;
        }
    </style>
</head>



<body>
<div id="modifycom_root">
    <div id="main_root">
        <p>
            <span>商品名称: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="c_name" type="text">
        </p>
        <p>
            <span>商品价格: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="c_price" type="text">
        </p>
        <p>
            <span>商品库存: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="c_inventory" type="text">
        </p>
        <p>
            <span>商品类型: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="c_type" type="text">
        </p>
        <p>
            <span>商品细节: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <textarea  v-model="c_detail" type="text"></textarea>
        </p>
        <p>
            <span>图片路径: &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span><br>
            <input v-model="c_img" type="text">
        </p>
        <button @click="confim()">确认</button>
        <button @click="goback()">返回</button>
    </div>
</div>

<script>
    new Vue({
        el: "#modifycom_root",
        data :{
            c_name: "",
            c_price: "",
            c_inventory: "",
            c_type: "",
            c_detail: "",
            c_img: "",
        },
        mounted: function(){
            this.c_name="<%=(String) session.getAttribute("c_name") %>";
            this.c_price="<%= session.getAttribute("c_price") %>";
            this.c_inventory="<%= session.getAttribute("c_inventory") %>";
            this.c_type="<%= session.getAttribute("c_type") %>";
            this.c_detail="<%=(String) session.getAttribute("c_detail") %>";
            this.c_img="<%=(String) session.getAttribute("c_img") %>";
        },
        methods: {
            confim : function() {
                var params = new Object();
                params.c_name = this.c_name;
                params.c_price=this.c_price;
                params.c_inventory=this.c_inventory;
                params.c_type = this.c_type;
                params.c_detail=this.c_detail;
                params.c_img=this.c_img;
                axios.get('modifyicommoditys' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if(res.data=="1"){
                            alert("修改成功");
                            location.href = "acommoditys";
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
                location.href = "acommoditys";
            },
        }
    })
</script>

</body>
</html>
