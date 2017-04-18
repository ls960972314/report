<%@ page language="java" pageEncoding="utf-8"%>
<head>
</head>
<body>
	<div>
		报表标志:
		<span style='margin-left: 10px;'></span>
		<input id="toolFlag" type='text' class='input' /> 
		<span style='margin-left: 10px;'></span>
		<input type="button" class="constr borders" onclick="findComment()" value="查找文本" />
		<input type="button" class="constr borders" onclick="saveComment()" value="保存文本" />
	</div>
	<div style="margin-top: 10px;">
		<textarea id="text-input" oninput="this.editor.update()"
			style="width: 45%; height: 500px; float: left;">Type **Markdown** here.</textarea>
		<div id="preview"
			style="width: 45%; height: 500px; float: right; overflow-y: scroll; overflow-x: scroll;"></div>
	</div>
</body>

<script type="text/javascript">
	$("#text-input").scroll(function() {
		$("#preview").scrollTop($("#text-input").scrollTop());
	});
	function Editor(input, preview) {
		this.update = function() {
			preview.innerHTML = marked(input.value);
		};
		input.editor = this;
		this.update();
	}
	
	new Editor(document.getElementById("text-input"), document
			.getElementById("preview"));
	/**
	 *  保存文本内容
	 */
	function saveComment() {
		if ($("#toolFlag").val() == "") {
			jAlert("报表标志为空", "警告");
			return;
		}

		$.ajax({
			url : "reportComment/saveComment",
			type : "POST",
			data : {
				toolFlag : $("#toolFlag").val(),
				comment : document.getElementById("text-input").value
			},
			dataType : "json",
			async : false,
			success : function(data) {
				if (data.status == "0") {
					jAlert("保存成功", "提示");
				} else {
					jAlert("保存失败", "警告");
				}
			}
		});
	}
	
	/**
	 *  查找文本内容
	 */
	function findComment() {
		if ($("#toolFlag").val() == "") {
			jAlert("报表标志为空", "警告");
			return;
		}

		$.ajax({
			url : "reportComment/getComment",
			type : "POST",
			data : {
				toolFlag : $("#toolFlag").val()
			},
			dataType : "json",
			success : function(data) {
				if (data.code == "0") {
					$("#text-input").val(data.data);
					$("#preview").html(marked(data.data));
				} else {
					$("#preview").html("系统有错误,请联系管理员");
				}
			}
		});
	}
</script>
