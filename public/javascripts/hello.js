/*
function showIconThumbnails() {

    uploadAFile("iconImg", "previewIconPane", "icon");

}

function uploadAFile(fileId, preViewUl, uploadType) {
    var files = document.getElementById(fileId).files;

    $('#' + preViewUl).html('');

    for (var i = 0; i < files.length; i++) {

        var file = files[i];

        var image = document.createElement("img");

        $('#' + preViewUl).append('<li id="photo' + uploadType + i + '">');

        $('#photo' + uploadType + i).append(image);

        $('#photo' + uploadType + i).append('<div id="progress' + uploadType + i + '" class="progress large-12 medium-12 success round"><span class="meter" style="width: 0%"></span></div>');


        var fileReader = new FileReader();

        fileReader.onload = (function (img) {
            return function (e) {
                img.src = e.target.result;
            };
        })(image);

        fileReader.readAsDataURL(file);

        uploadFile(file, i, fileId, preViewUl, uploadType);
    }
}

var xhr;
var formData;
function uploadFile(file, i, fileId, preViewUl, uploadType) {

    xhr = new XMLHttpRequest();
    formData = new FormData();

    formData.append('iconImg', file);

    xhr.upload.addEventListener("progress", function (e) {
        if (e.lengthComputable) {
            var percentage = Math.round((e.loaded * 100) / e.total);
            $("#progress" + uploadType + i).html('<span class="meter" style="width: ' + percentage + '%"></span>');
        }
    }, false);

    xhr.upload.addEventListener("load", function (e, xhrPr) {

        $("#progress" + uploadType + i).html('<span class="meter" style="width: 100%"></span>');

        resuIslemleri();
    }, false);


    if (uploadType == "icon") {
        xhr.open("POST", "/upload/1");
    } else {
        xhr.open("POST", "/upload/2");
    }


    xhr.overrideMimeType('text/plain; charset=x-user-defined-binary');
    xhr.responseType = "application/json";
    xhr.send(formData);

}

function resuIslemleri() {
    console.log(xhr);
    var strRe = xhr.responseText.replace(" ", "");

    var jsonResponse = JSON.parse(strRe);
    console.log(jsonResponse);
    if (jsonResponse.res == "ok") {
        $("#" + uploadType + "Hidden").val("" + jsonResponse.id);
    } else {
        alert("Dosya upload hatalı");
    }
}
*/
