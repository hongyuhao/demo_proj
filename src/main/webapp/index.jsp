<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
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

                var last=JSON.stringify(returndata);

                alert(last);
            },
            error: function (returndata) {

                var last=JSON.stringify(returndata);

                alert(last);
            }
        });
    }
</script>

<form id= "uploadForm" enctype="multipart/form-data">
    <p >????? <input type="file" name="file" id="file"/></p>
    <input type="button" value="????" onclick="doUpload()"/>
</form>

</body>
</html>