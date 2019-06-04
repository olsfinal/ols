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
        <li>
            我的信息
            <ul>
                <li @click="leftclick(1)">注销</li>
                <li @click="leftclick(2)">信息修改</li>
                <li @click="leftclick(3)">我的地址</li>
            </ul>
        </li>
        <li @click="leftclick(4)">我的购物车</li>
        <li>
            我的订单
            <ul>
                <li @click="leftclick(5)">所有订单</li>
                <li @click="leftclick(6)">待收货</li>
                <li @click="leftclick(7)">交易完成</li>
                <li @click="leftclick(8)">审核退款中</li>
                <li @click="leftclick(9)">已退款</li>
                <li @click="leftclick(10)">退款失败</li>
            </ul>
        </li>
        <li @click="leftclick(11)">开始购物</li>
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
                else if(index==2) location.href = "showmodifyuser";
                else if(index==3) {
                    axios.get('infos' )
                        .then(function (res) {
                            console.log(res);
                            location.href = "showinfos";
                        })
                        .catch(function (error) { // 请求失败处理
                            alert(error);
                        })
                }
                else if(index==4) location.href = "showcart";
                else if(index==11) {
                    location.href = "index";
                }
                else{
                    var params = new Object();
                    params.o_state = index-5;
                    axios.get('orders' , {params:params})
                        .then(function (res) {
                            console.log(res);
                            if(res.data=="1"){
                                location.href = "showorders";
                            }
                            else {
                                alert(res.data);
                            }
                        })
                        .catch(function (error) { // 请求失败处理
                            alert(error);
                        })
                }

            }
        }
    })
</script>
