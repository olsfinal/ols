<%--
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
    <title>总目录</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
    <link rel="stylesheet" href="css/index.css" media="screen" type="text/css" />
</head>


<body>
<!-- 侧边栏 -->
<%@include file="left.jsp" %>

<%--主要部分--%>
<div id="root">
    <div class="index_class" style="background-color:#666666;
                                            height: 615px;" @click="chose_com(1)">
        <p>所<br>有<br>商<br>品</p>
    </div>

    <div class="index_class" style="background-color:#FF9900;" @click="chose_com(2)">
        <p>类型2</p>
    </div>

    <div class="index_class" style="background-color:#FF6666;
                                            width: 629px;" @click="chose_com(3)">
        <p>热&nbsp;卖&nbsp;商&nbsp;品</p>
    </div>

    <div class="index_class" style="background-color:#99CC66;" @click="chose_com(4)">
        <p>类型4</p>
    </div>

    <div class="index_class" style="background-color:#0066CC;" @click="chose_com(5)">
        <p>类型5</p>
    </div>

    <div class="index_class" style="background-color:#FF0033;" @click="chose_com(6)">
        <p>类型6</p>
    </div>

    <div class="index_class" style="background-color:#0099CC;" @click="chose_com(7)">
        <p>类型7</p>
    </div>

    <div class="index_class" style="background-color:#CCCC99;" @click="chose_com(8)">
        <p>类型8</p>
    </div>
</div>
<script>
    new Vue({
        el:"#root",
        data:{

        },
        methods:{
            chose_com:function(index){
                var params = new Object();
                params.c_type = 0;
                console.log(params);
                axios.get('catalog' , {params:params})
                    .then(function (res) {
                        console.log(res);
                        if (res.data=="1"){
                            location.href = "showcatalog";
                        }
                        else{
                            alert(res.data);
                        }
                    })
                    .catch(function (error) { // 请求失败处理
                        alert(error);
                    })
            }
        },


    })
</script>
</body>
</html>
