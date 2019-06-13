<%--
  Created by IntelliJ IDEA.
  User: lenovo
  Date: 2019/6/1
  Time: 19:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 侧边栏 -->

<div id="myleft">
    <p style="font-size: 1.5em; font-family:'楷体';">&nbsp;菜单</p>
    <ul>
        <!-- <li v-for="(item,index) of leftlist" > -->
        <li @click="leftclick(1)">注销</li>
        <li>
            退款处理
            <ul>
                <li @click="leftclick(2)">待审核</li>
                <li @click="leftclick(3)">已审核</li>
            </ul>
        </li>
        <li @click="leftclick(4)">商品处理</li>
        <li @click="leftclick(5)">用户处理</li>
    </ul>
</div>
<script>
    new Vue({
        el:"#myleft",
        data:{

        },
        methods:{
            leftclick:function(index){
                if(index==1){
                    location.href = "logout";
                }
                else if(index==2) {
                    var params = new Object();
                    params.o_state = 3;
                    axios.get('checkrefund' , {params:params})
                        .then(function (res) {
                            console.log(res);
                            if(res.data=="1"){
                                location.href = "showcheckrefund";
                            }
                            else {
                                alert(res.data);
                            }
                        })
                        .catch(function (error) { // 请求失败处理
                            alert(error);
                        })

                }
                else if(index==3) {
                    var params = new Object();
                    params.o_state = 6;
                    axios.get('checkrefund' , {params:params})
                        .then(function (res) {
                            console.log(res);
                            if(res.data=="1"){
                                location.href = "showcheckrefund";
                            }
                            else {
                                alert(res.data);
                            }
                        })
                        .catch(function (error) { // 请求失败处理
                            alert(error);
                        })

                }
                else if(index==4) {
                    var params = new Object();
                    params.c_type = 1;
                    axios.get('catalog' , {params:params})
                        .then(function (res) {
                            console.log(res);
                            if(res.data=="1"){
                                location.href = "acommoditys";
                            }
                            else {
                                alert(res.data);
                            }
                        })
                        .catch(function (error) { // 请求失败处理
                            alert(error);
                        })
                }
                else if(index==5) {
                    location.href = "showusers";
                }
            }
        }
    })
</script>
