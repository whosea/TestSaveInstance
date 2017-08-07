function appInitImage(){
    var imgs = document.getElementsByTagName("img");
    for (var i=0;i<imgs.length;i++){
        var src = imgs[i].src;
        imgs[i].setAttribute("onClick","appImageClick(\'" + src + "\')");
    }
    document.location = "about:loadOK";
}

function appImageClick(imagesrc){
    var pe = event.target.parentNode;
    if (pe.tagName=="A") {
        return;
    }

    var url="appImageClick:"+imagesrc;
    document.location = url;
}

function appGetAllPageImages() {
	var tmp = [];
    var imgs = document.getElementsByTagName("img");
    for (var i=0;i<imgs.length;i++){
        var src = imgs[i].src;
        tmp.push(src);
    }
	return tmp.join(",");
}

appInitImage();