function fileQueueError(file, errorCode, message) {
	try {
		var imageName = "error.gif";
		var errorName = "超过最大文件上传数量限制或超过图片大小";
		if (errorCode === SWFUpload.errorCode_QUEUE_LIMIT_EXCEEDED) {
			errorName = "超过最大文件上传数量限制";
		}

		if (errorName !== "") {
			alert(errorName);
			return;
		}

		switch (errorCode) {
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
				imageName = "zerobyte.gif";
				break;
			case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
				imageName = "toobig.gif";
				break;
			case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			default:
				alert(message);
				break;
		}

		// addImage("/swfupload/images/" + imageName);

	} catch (ex) {
		this.debug(ex);
	}

}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		if (numFilesQueued > 0) {
			this.startUpload();
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadProgress(file, bytesLoaded) {

	try {
		var percent = Math.ceil((bytesLoaded / file.size) * 100);

		var progress = new FileProgress(file,  this.customSettings.upload_target);
		progress.setProgress(percent);
		if (percent === 100) {
			progress.setStatus("正在创建缩略图...");
			progress.toggleCancel(false, this);
		} else {
			progress.setStatus("正在上传中...");
			progress.toggleCancel(true, this);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadSuccess(file, serverData,responseReceived) {
	try {
		var progress = new FileProgress(file,  this.customSettings.upload_target);
		if (serverData) {
			var dataObj = eval("(" + serverData + ")");
			addImage(dataObj.url);
			progress.setStatus("缩略图已创建.");
			progress.toggleCancel(false);
		} else {
			addImage("/swfupload/images/error.gif");
			progress.setStatus("错误.");
			progress.toggleCancel(false);
			alert(serverData);

		}


	} catch (ex) {
		this.debug(ex);
	}
}

function uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			var progress = new FileProgress(file,  this.customSettings.upload_target);
			progress.setComplete();
			progress.setStatus("上传文件已接收完毕.");
			progress.toggleCancel(false);
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	var imageName =  "error.gif";
	var progress;
	try {
		switch (errorCode) {
			case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
				try {
					progress = new FileProgress(file,  this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("取消");
					progress.toggleCancel(false);
				}
				catch (ex1) {
					this.debug(ex1);
				}
				break;
			case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
				try {
					progress = new FileProgress(file,  this.customSettings.upload_target);
					progress.setCancelled();
					progress.setStatus("停止");
					progress.toggleCancel(true);
				}
				catch (ex2) {
					this.debug(ex2);
				}
			case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
				imageName = "uploadlimit.gif";
				break;
			default:
				alert(message);
				break;
		}

		addImage("/swfupload/images/" + imageName);

	} catch (ex3) {
		this.debug(ex3);
	}

}


Function.prototype.bind = function(){
	var self = this;
	var arg  = arguments;
	return function(){
		self.apply(null,arg);
	}
}

function addImage(src) {
	src = src.replace(/^\s+|\s+$/g, "");
	var id = src.replace(/thumb\./g,"");
	src = src.substring(src.indexOf('/img/'));
	id = id.substring(id.lastIndexOf("/")+1,id.length);
	id = id.substring(0,id.lastIndexOf("."));

	var imgDiv = document.createElement("div");
	imgDiv.style.height = "101px";
	imgDiv.style.width = "160px";
	imgDiv.style.float = "left";


	// imgDiv.setAttribute('style','width:240px;height:101px;float:left;');


	var newImg = document.createElement("img");
	// newImg.style.margin = "2px";
	newImg.style.height = "100px";
	newImg.style.width = "100px";
	newImg.style.float = "left";
	newImg.setAttribute("id", "img"+id);
	newImg.setAttribute("path", imgBasePath+src);
	// newImg.setAttribute('style','float:left;');

	var imgClick = function(i){
		showBigImg('/img/'+i+'.img');
	}
	var f = function(i){
		deleteImg(i);
	};
	var f2 = function(i){
		setUpMainImg(i);
	};
	newImg.onclick= imgClick.bind(id);
	var newEm = document.createElement("div");
	newEm.setAttribute("id", "em"+id);
	newEm.setAttribute("onclick", "deleteImg('"+id+"')");
	newEm.setAttribute("style", "cursor:pointer;color:red;");
	newEm.innerHTML = "[删除]";
	var newEm2 = document.createElement("div");
	newEm2.setAttribute("id", "em2"+id);
	newEm2.setAttribute("onclick", "setUpMainImg('"+id+"')");
	newEm2.setAttribute("style", "cursor:pointer;color:green;line-height:55px;");
	newEm2.innerHTML = "[设为主图]";
	if(src.indexOf("/temp/")>0){
		newEm.onclick= f.bind(id);
		newEm2.onclick= f2.bind(id);
	}else{
		newEm.onclick= f.bind(id);
		newEm2.onclick= f2.bind(id);
	}
	var thumb = document.getElementById("thumbnails");

	imgDiv.appendChild(newImg);
	imgDiv.appendChild(newEm2);
	imgDiv.appendChild(newEm);

	thumb.appendChild(imgDiv);
	try{
		//add to imagePath filed
		document.getElementById("imagePath").value= document.getElementById("imagePath").value+src.replace(".thumb","")+";";
	}catch(e){

	}
	if (newImg.filters) {
		try {
			newImg.filters.item("DXImageTransform.Microsoft.Alpha").opacity = 0;
		} catch (e) {
			// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
			newImg.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + 0 + ')';
		}
	} else {
		newImg.style.opacity = 0;
	}

	newImg.onload = function () {
		fadeIn(newImg, 0);
	};
	newImg.src = imgBasePath + src;
}

function fadeIn(element, opacity) {
	var reduceOpacityBy = 5;
	var rate = 30;	// 15 fps


	if (opacity < 100) {
		opacity += reduceOpacityBy;
		if (opacity > 100) {
			opacity = 100;
		}

		if (element.filters) {
			try {
				element.filters.item("DXImageTransform.Microsoft.Alpha").opacity = opacity;
			} catch (e) {
				// If it is not set initially, the browser will throw an error.  This will set it if it is not set yet.
				element.style.filter = 'progid:DXImageTransform.Microsoft.Alpha(opacity=' + opacity + ')';
			}
		} else {
			element.style.opacity = opacity / 100;
		}
	}

	if (opacity < 100) {
		setTimeout(function () {
			fadeIn(element, opacity);
		}, rate);
	}
}



/* ******************************************
 *	FileProgress Object
 *	Control object for displaying file info
 * ****************************************** */

function FileProgress(file, targetID) {
	this.fileProgressID = "divFileProgress";

	this.fileProgressWrapper = document.getElementById(this.fileProgressID);
	if (!this.fileProgressWrapper) {
		this.fileProgressWrapper = document.createElement("div");
		this.fileProgressWrapper.className = "progressWrapper";
		this.fileProgressWrapper.id = this.fileProgressID;

		this.fileProgressElement = document.createElement("div");
		this.fileProgressElement.className = "progressContainer";

		var progressCancel = document.createElement("a");
		progressCancel.className = "progressCancel";
		progressCancel.href = "#";
		progressCancel.style.visibility = "hidden";
		progressCancel.appendChild(document.createTextNode(" "));

		var progressText = document.createElement("div");
		progressText.className = "progressName";
		progressText.appendChild(document.createTextNode(file.name));

		var progressBar = document.createElement("div");
		progressBar.className = "progressBarInProgress";

		var progressStatus = document.createElement("div");
		progressStatus.className = "progressBarStatus";
		progressStatus.innerHTML = "&nbsp;";

		this.fileProgressElement.appendChild(progressCancel);
		this.fileProgressElement.appendChild(progressText);
		this.fileProgressElement.appendChild(progressStatus);
		this.fileProgressElement.appendChild(progressBar);

		this.fileProgressWrapper.appendChild(this.fileProgressElement);

		document.getElementById(targetID).appendChild(this.fileProgressWrapper);
		fadeIn(this.fileProgressWrapper, 0);

	} else {
		this.fileProgressElement = this.fileProgressWrapper.firstChild;
		this.fileProgressElement.childNodes[1].firstChild.nodeValue = file.name;
	}

	this.height = this.fileProgressWrapper.offsetHeight;

}
FileProgress.prototype.setProgress = function (percentage) {
	this.fileProgressElement.className = "progressContainer green";
	this.fileProgressElement.childNodes[3].className = "progressBarInProgress";
	this.fileProgressElement.childNodes[3].style.width = percentage + "%";
};
FileProgress.prototype.setComplete = function () {
	this.fileProgressElement.className = "progressContainer blue";
	this.fileProgressElement.childNodes[3].className = "progressBarComplete";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setError = function () {
	this.fileProgressElement.className = "progressContainer red";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setCancelled = function () {
	this.fileProgressElement.className = "progressContainer";
	this.fileProgressElement.childNodes[3].className = "progressBarError";
	this.fileProgressElement.childNodes[3].style.width = "";

};
FileProgress.prototype.setStatus = function (status) {
	this.fileProgressElement.childNodes[2].innerHTML = status;
};

FileProgress.prototype.toggleCancel = function (show, swfuploadInstance) {
	this.fileProgressElement.childNodes[0].style.visibility = show ? "visible" : "hidden";
	if (swfuploadInstance) {
		var fileID = this.fileProgressID;
		this.fileProgressElement.childNodes[0].onclick = function () {
			swfuploadInstance.cancelUpload(fileID);
			return false;
		};
	}
};
