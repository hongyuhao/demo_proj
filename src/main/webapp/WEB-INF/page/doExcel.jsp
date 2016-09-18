<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
<script type="text/javascript">
    function doUpload() {
        var formData = new FormData($( "#file" )[0]);
        var fileObj = document.getElementById("file").files[0];
        formData.append("file", fileObj);
        $.ajax({
            url: '/regionExcel/upload.json' ,
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,

            success: function (returndata) {
//                $('#download').setProperty("")
                $('#download').attr('href',returndata.info );
//                $('#download').setAttribute("href", returndata.info);
                alert('上传成功，请点击下载链接下载处理后的文件');
            },
            error: function (returndata) {

                var last=JSON.stringify(returndata);

                alert(last);
            }
        });
    }
</script>

<form id= "uploadForm" enctype="multipart/form-data">
    <p >选择要处理的文件：<input type="file" name="file" id="file"/></p>
    <input type="button" value="上传文件" onclick="doUpload()"/>
    <a href="" id = 'download'>下载文件</a>
</form>

</body>
</html>