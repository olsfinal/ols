<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
    <script src="https://cdn.staticfile.org/axios/0.18.0/axios.min.js"></script>
    <link rel="stylesheet" href="css/left.css" media="screen" type="text/css" />
</head>


<body>
<%--左侧栏--%>
<div id="myleft">
    <p>&nbsp;菜单</p>
    <ul>
        <li v-for="(item,index) of leftlist" ><span @click="leftclick(index)">{{item}}</span></li>
    </ul>
</div>
<script>
    new Vue({
        el:"#myleft",
        data:{
            leftlist:["第一","第二","第三",]
        },
        methods:{
            leftclick:function(index){
                this.leftlist.push(index)
            }
        }
    })
</script>

<div id="root">
    index2
    <p>{{user_id}}</p>
    <p>{{user_pwd}}</p>
</div>

<script>

    new Vue({
        el: "#root",
        data :{
            user_id: "your user_id",
            user_pwd: "your user_pwd"
        },
        mounted :function(){
            // alert(4);
            this.user_id="<%=session.getAttribute("user_id")%>";
            this.user_pwd="<%=session.getAttribute("user_pwd")%>";
        },

    })


</script>



</body>
</html>