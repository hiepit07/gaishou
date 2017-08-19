var selectedImageElementGlobal;

// Upload button event click processing
$(document).on("click", "#btnUploadUploadForm", function() {
	// application path
	var rootPath = getContextPath();
	// selected file name
	var fileName = $("#file").val();

	// check type of selected file
	if (!checkUploadFile(fileName)) {
		// selected file is not JPG
		// display error message
		alert(UPLOAD_IMAGE_WRONG_FILE_TYPED_MESSAGE);
	} else {
		// selected file is JPG
		// variable definition
		var returnData = [];
		// ajaxを使ったら、サーバーでアップロードファイルを取得できません。
		// submitを使ったら、サーバーでアップロードファイルを取得できますが、親画面も再ロードされます。　→　フォームで入力したデータを保存しないといけない。
		// ⇒　ajaxを使って、サーバーでアップロードファイルを取得できるように、iframeを使います。
		// ※iframeに移動することを指定して、submitしたら、親画面が再ロードされない。
		var iframe = document.createElement("iframe");
		iframe.setAttribute("id", "upload_iframe");
		iframe.setAttribute("name", "upload_iframe");
		iframe.setAttribute("width", "0");
		iframe.setAttribute("height", "0");
		iframe.setAttribute("border", "0");
		iframe.setAttribute("style", "width: 0; height: 0; border: none;");
		$("#uploadForm").parent().append(iframe);
		window.frames['upload_iframe'].name = "upload_iframe";
		var iframeId = document.getElementById("upload_iframe");
		var eventHandler = function() {
			if (iframeId.detachEvent) {
				iframeId.detachEvent("onload", eventHandler);
			} else {
				iframeId.removeEventListener("load", eventHandler, false);
			}
				
			if (iframeId.contentDocument) {
				content = iframeId.contentDocument.body.innerHTML;
			} else if (iframeId.contentWindow) {
				content = iframeId.contentWindow.document.body.innerHTML;
			} else if (iframeId.document) {
				content = iframeId.document.body.innerHTML;
			}
			// remove unnecessary items
			content = content.replace("<PRE>","").replace("</PRE>","");
			// replace"&amp;" to "&"
			content = content.replace(/&amp;/g, '&');
			// get returned information
			if (content == "" || content == UPLOAD_RESULT_FAILED) {
				// upload failed, display error message
				alert(UPLOAD_IMAGE_RESULT_FAILED_MESSAGE);
			} else if (content != "" && content != UPLOAD_RESULT_FAILED) {
				// upload successfully
				// set image to html
				selectedImageElementGlobal.attr("src", rootPath + "/fileUploadForm/image/" + content);
				// set image name to name attr
				selectedImageElementGlobal.attr("name", content);
			}
			// remove iframe
			setTimeout(function() {
				iframeId.parentNode.removeChild(iframeId);
			}, 250);
		}
		if (iframeId.addEventListener) {
			iframeId.addEventListener("load", eventHandler, true);
		}
		if (iframeId.attachEvent) {
			iframeId.attachEvent("onload", eventHandler);
		}

		// append action and target to upload form
		$("#uploadForm").attr("target", "upload_iframe");
		$("#uploadForm").attr("action", rootPath + "/fileUploadForm/upload");
		$("#uploadForm").attr("method", "post");
		$("#uploadForm").attr("enctype", "multipart/form-data");
		$("#uploadForm").attr("encoding", "multipart/form-data");
		$("#uploadForm").submit();

		// hide upload form
		hidePopup2($("#popupUploadFormWrapper"));
	}
});

// Cancel button event click processing
$(document).on("click", "#btnCancelUploadForm", function() {
	// hide upload form
	hidePopup2($("#popupUploadFormWrapper"));
});

// Display fileUploadForm.jsp
function showUploadFileForm(element) {
	// store element
	selectedImageElementGlobal = element;
	// display upload form
	$("#popupUploadFormWrapper").load("../fileUploadForm/ #popupFileUpload", function() {
		showPopup2($("#popupUploadFormWrapper"));
	});
}

// Check if selected file is JPG or not
function checkUploadFile(fileName) {
    var fileName_cmp = fileName.toLowerCase();
	if (!/\.jpg$/.test(fileName_cmp)) {
		return false;
	} else {
		return true;
	}
}