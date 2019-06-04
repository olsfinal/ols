
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Title</title>
  <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
  <script src="https://cdn.staticfile.org/vue-resource/1.5.1/vue-resource.min.js"></script>
</head>
<body>
hello
<%--<div id="indexroot">--%>
  <%--<button @click="gologin()">go</button>--%>
<%--</div>--%>

<%--<script>--%>
    <%--new Vue({--%>
        <%--el: "#indexroot",--%>
        <%--data :{--%>

        <%--},--%>
        <%--methods: {--%>
            <%--gologin : function() {--%>

                <%--this.$http.get('login').then(function (res) {--%>
                    <%--document.write(res.body);--%>
                    <%--res.clear();--%>
                <%--}, function () {--%>
                    <%--console.log('请求失败处理');--%>
                <%--});--%>
            <%--}--%>
        <%--}--%>
    <%--})--%>
<%--</script>--%>
<script>
    location.href = "login";
</script>

</body>
</html>
