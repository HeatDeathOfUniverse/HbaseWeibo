<%--
  Created by IntelliJ IDEA.
  User: Re:K
  Date: 2020/6/27
  Time: 16:57
  Description: ToDo
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>主页</title>
    <script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
    <script type="text/javascript">
        $(function () {
            /* For zebra striping */
            $("table tr:nth-child(odd)").addClass("odd-row");
            /* For cell text alignment */
            $("table td:first-child, table th:first-child").addClass("first");
            /* For removing the last border */
            $("table td:last-child, table th:last-child").addClass("last");
        });
    </script>
    <script>
        // 获取关注的人
        function myAttends() {
            $.post({
                url: "${pageContext.request.contextPath}/myAttends",
                success: function (data) {
                    console.log(data);
                    var obj = JSON.parse(data);//第一个data代表json,第二个data代表json里的数组或对象
                    var str1 = "";   //声明str1，防止产生undefined
                    console.log(obj[0].rowKey);
                    console.log(obj.length);
                    for (var i = 0; i < obj.length; i++) {
                        if (obj[i].attends != null)
                            str1 += "<tr>" +
                                "<td>" + obj[i].attends + "</td>" +
                                "</tr>"
                    }
                    test1.innerHTML = str1;
                },
                error: function () {
                    console.log('Send Request Fail..')
                }
            })
        }

        //获取粉丝
        function myFans() {
            $.post({
                url: "${pageContext.request.contextPath}/myFans",
                success: function (data) {
                    console.log(data);
                    var obj = JSON.parse(data);//第一个data代表json,第二个data代表json里的数组或对象
                    var str1 = "";   //声明str1，防止产生undefined
                    console.log(obj[0].rowKey);
                    console.log(obj.length);
                    for (var i = 0; i < obj.length; i++) {
                        if (obj[i].fans != null)
                            str1 += "<tr>" +
                                "<td id='fansId'>" + obj[i].fans + "</td>" +
                                "</tr>"
                    }
                    test2.innerHTML = str1;

                },
                error: function () {
                    console.log('Send Request Fail..')
                }
            })
        }

        // 关注输入的ID
        function attendPerson() {
            $.ajax({
                type: 'POST',
                url: "${pageContext.request.contextPath}/attendPerson",
                data: {'attendId': $("#attendId").val()},
                success: function (data) {
                    console.log(data);
                    myAttends();
                }

            })

        }

        // 取关输入的id
        function unAttendPerson() {
            $.ajax({
                type: 'POST',
                url: "${pageContext.request.contextPath}/unAttendPerson",
                data: {'unAttendId': $("#unAttendId").val()},
                success: function (data) {
                    console.log(data);
                    myAttends();
                }

            })

        }

        function isApAttendBp() {
            $.post({
                url: "${pageContext.request.contextPath}/isApAttendBp",
                data: {"aid": $("#aid").val(), "bid": $("#bid").val()},
                dataType: 'json',
                success: function (data) {
                    console.log(data);
                    if (data.toString(data) == 'yes!') {
                        $("#aabinfo").css("color", "green");
                    } else {
                        $("#aabinfo").css("color", "red");
                    }
                    $("#aabinfo").html(data);
                },
                error: function (request, options) {
                    console.log('Send Request Fail..')
                }
            })

        }


    </script>
</head>
<body>

<div class="page_head">

    <div class="user_info">
        <div class="uerid" style="text-align: center;height: 50px">${sessionScope.uid}</div>
        <div class="button_all">
            <button class="button1" type="button" onclick="myAttends()">关注的人</button>
            <button class="button1" type="button" onclick="myFans()">粉丝</button>
        </div>
    </div>


</div>
<div class="page_body">
    <div id="isApBp">
        <div id="checkAAB">
            <form action="#" method="post">
                <input class="input1" type="text" id="aid" placeholder="eg:1_002">
                关注了
                <input class="input1" type="text" id="bid" placeholder="eg:1_002">
                ?
                <input type="button" value="查询" onclick="isApAttendBp()">
                <%--原来写的type=submit导致ajax一直获取不到返回的数据,但是后端又执行了--%>
                <span id="aabinfo"></span>
            </form>
        </div>
    </div>
    <!-- 表格实例代码 -->
    <div id="attends">
        <div id="unAttendPerson">
            <form action="#" method="post">
                <input class="input1" type="text" id="unAttendId" placeholder="要取关的的人的id,eg:1_002">
                <input type="button" value="取关" onclick="unAttendPerson()">
            </form>
        </div>

        <table id="listmap1" class="table table-hover">
            <thead>
            <tr>
                <th class="text-center" style="width:100px">关注的人</th>
            </tr>
            </thead>

            <!--存放数据的地方-->
            <tbody style="min-height: 500px;" id="test1">

            </tbody>
        </table>
    </div>
    <div id="fans">
        <div id="attendPerson">
            <form action="#" method="post">
                <input class="input1" type="text" id="attendId" placeholder="要关注的的人的id,eg:1_002">
                <input type="button" value="关注" onclick="attendPerson()">
            </form>
        </div>
        <table id="listmap2" class="table table-hover">
            <thead>
            <tr>
                <th class="text-center" style="width:100px">粉丝</th>
            </tr>
            </thead>

            <!--存放数据的地方-->
            <tbody style="min-height: 500px;" id="test2">
            </tbody>
        </table>
    </div>

</div>

<style>
    .page_head {
        display: flex;
        flex-direction: row;
        justify-content: center;
        flex-wrap: wrap;
        align-content: space-evenly;
        width: 100%;
        height: 36%;
        margin: 0 auto;
        box-shadow: 1px 2px 3px 4px #ccc;
        background-image: url("https://gitee.com/renxuw/PicCloud/raw/master/img/20200521125820.png");
        background-repeat: no-repeat;
        background-position: center;
        background-size: 100%; /* 背景图片宽度为容器宽度的100%，高度为容器高度的100% */
    }

    .page_body {
        display: flex;
        flex-direction: row;
        justify-content: space-evenly;
        flex-wrap: wrap;
        align-content: flex-start;
        width: 69%;
        margin: 0 auto;
        box-shadow: 1px 2px 3px 4px #ccc;
        margin-top: 20px;
    }

    .user_info {
        /*display: flex;*/
        /*flex-direction: column;*/
        /*justify-content: space-between;*/

        font-size: 50px;
        color: #FFFFFF;
        font-weight: bolder;

    }

    .weibo_info {
        font-size: 50px;
        font-weight: bolder;
    }

    .button1 {
        background: #f2a010;
        background-image: -webkit-linear-gradient(top, #f2a010, #f7a601);
        background-image: -moz-linear-gradient(top, #f2a010, #f7a601);
        background-image: -ms-linear-gradient(top, #f2a010, #f7a601);
        background-image: -o-linear-gradient(top, #f2a010, #f7a601);
        background-image: linear-gradient(to bottom, #f2a010, #f7a601);
        border-radius: 6px;
        text-shadow: 0px 1px 0px #2f6627;
        font-family: 微软雅黑;
        color: #ffffff;
        font-size: 17px;
        padding: 12px 30px 12px 30px;
        border: solid #ffffff 1px;
        text-decoration: none;
    }

    .input1 {

        text-align: center;
        border: #ccc 1px solid;
        border-radius: 5px; /* css 3标准 */
        -moz-border-radius: 5px; /* mozilla */
        -webkit-border-radius: 5px; /* webkit */
    }


    html, body, div, span, object, iframe,
    h1, h2, h3, h4, h5, h6, p, blockquote, pre,
    abbr, address, cite, code,
    del, dfn, em, img, ins, kbd, q, samp,
    small, strong, sub, sup, var,
    b, i,
    dl, dt, dd, ol, ul, li,
    fieldset, form, label, legend,
    table, caption, tbody, tfoot, thead, tr, th, td {
        margin: 0;
        padding: 0;
        border: 0;
        outline: 0;
        font-size: 100%;
        vertical-align: baseline;
        background: transparent;
    }

    body {
        margin: 0;
        padding: 0;
        font: 12px/15px "Helvetica Neue", Arial, Helvetica, sans-serif;
        color: #555;
    }

    a {
        color: #666;
    }

    #content {
        width: 65%;
        max-width: 690px;
        margin: 6% auto 0;
    }

    /*
    Pretty Table Styling
    CSS Tricks also has a nice writeup: http://css-tricks.com/feature-table-design/
    */

    table {
        overflow: hidden;
        border: 1px solid #d3d3d3;
        background: #fefefe;
        width: 70%;
        margin: 5% auto 0;
        -moz-border-radius: 5px; /* FF1+ */
        -webkit-border-radius: 5px; /* Saf3-4 */
        border-radius: 5px;
        -moz-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
        -webkit-box-shadow: 0 0 4px rgba(0, 0, 0, 0.2);
    }

    th, td {
        padding: 18px 28px 18px;
        text-align: center;
    }

    th {
        padding-top: 22px;
        text-shadow: 1px 1px 1px #fff;
        background: #e8eaeb;
    }

    td {
        border-top: 1px solid #e0e0e0;
        border-right: 1px solid #e0e0e0;
    }

    tr.odd-row td {
        background: #f6f6f6;
    }

    td.first, th.first {
        text-align: left
    }

    td.last {
        border-right: none;
    }

    /*
    Background gradients are completely unnecessary but a neat effect.
    */

    td {

        background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f9f9f9), to(#fefefe));
    }

    tr.odd-row td {

        background: -webkit-gradient(linear, 0% 0%, 0% 25%, from(#f1f1f1), to(#f6f6f6));
    }

    th {

        background: -webkit-gradient(linear, 0% 0%, 0% 20%, from(#ededed), to(#e8eaeb));
    }

    /*
    I know this is annoying, but we need additional styling so webkit will recognize rounded corners on background elements.
    Nice write up of this issue: http://www.onenaught.com/posts/266/css-inner-elements-breaking-border-radius

    And, since we've applied the background colors to td/th element because of IE, Gecko browsers also need it.
    */

    tr:first-child th.first {
        -moz-border-radius-topleft: 5px;
        -webkit-border-top-left-radius: 5px; /* Saf3-4 */
    }

    tr:first-child th.last {
        -moz-border-radius-topright: 5px;
        -webkit-border-top-right-radius: 5px; /* Saf3-4 */
    }

    tr:last-child td.first {
        -moz-border-radius-bottomleft: 5px;
        -webkit-border-bottom-left-radius: 5px; /* Saf3-4 */
    }

    tr:last-child td.last {
        -moz-border-radius-bottomright: 5px;
        -webkit-border-bottom-right-radius: 5px; /* Saf3-4 */
    }


</style>
</body>
</html>
