<html>
<head>
<meta charset="UTF-8">
<title></title>
<base href="http://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/"/>
    <script type="text/javascript" src="jquery/jquery-2.1.1.min.js"></script>
    <script type="text/javascript" src="layer/layer.js"></script>
    <script type="text/javascript">

   $(function () {
       $("#btn1").click(function () {
           $.ajax({
                "url":"send/array.html",
                "type":"post",
                "data":
                       {
                           "array":[5,8,12]
                       },
               "dataType":"text",
               "success":function (response) {
                    alert(response);
               },
                "error":function (response) {
                    alert(response);
                },

           });
       })
   })
</script>
</head>
<body>

<h2>Hello World!</h2>
<a href="admin/to/login/page.html">go</a>
<button  id="btn1">send</button>

</body>
</html>
