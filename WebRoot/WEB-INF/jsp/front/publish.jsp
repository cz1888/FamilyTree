 <%@ page contentType="text/html;charset=UTF-8" language="java"%>
 <form method="post" action="${pageContext.request.contextPath}/cms/publish">
	<table>
	<tr>
		<td>标题</td>
		<td><input type="text" name="title" style="width:160px"></td>
	</tr>
	<tr>
		<td colspan="2">内容</td>
	</tr>
	<tr>
	<td  colspan="2"><textarea name="content" id="editor1" style="height: 350px"></textarea>
	</td>
	</tr>
	<tr>
		<td><input type="button" id="subbtn"></td>
		<td></td>
	</tr>
	</table>
  </form>
<script type="text/javascript" src="${pageContext.request.contextPath}/asserts/js/jquery-1.11.3.min.js"></script>
<script src="${pageContext.request.contextPath}/asserts/ckeditor/ckeditor.js"></script>
<script src="${pageContext.request.contextPath}/asserts/ckfinder/ckfinder.js"></script>
<script type="text/javascript">
var editor = CKEDITOR.replace('editor1');
	CKFinder.setupCKEditor(editor,'${pageContext.request.contextPath}/asserts/ckfinder/');
	
	$("#subbtn").click(function(){
	for ( instance in CKEDITOR.instances )
     {
      CKEDITOR.instances[instance].updateElement();
     };
    $("form").submit();
	})
</script> 
  
  