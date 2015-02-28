/**
 * Created by ilimturan on 23/02/15.
 */
var host = "http://localhost:9000/";

$(function () {

    $("#iconProgress").hide();

    $('#iconImg').fileupload({
        url: $("#iconImg").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#iconProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#iconProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#iconProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="icon" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/icon/'+data.result.path+'"></a></div>';
                $("#iconImgPrv").html(html);
            }else{
                alert(data.result.message)
            }
        }
    });


    $("#futureGraphicProgress").hide();

    $('#futureGraphicImg').fileupload({
        url: $("#futureGraphicImg").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#futureGraphicProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#futureGraphicProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#futureGraphicProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="futureGraphic" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/futureGraphic/'+data.result.path+'"></a></div>';
                $("#futureGraphicImgPrv").html(html);
            }else{
                alert(data.result.message)
            }
        }
    });

    $("#promoGraphicProgress").hide();

    $('#promoGraphicImg').fileupload({
        url: $("#promoGraphicImg").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#promoGraphicProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#promoGraphicProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#promoGraphicProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="promoGraphic" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/promoGraphic/'+data.result.path+'"></a></div>';
                $("#promoGraphicImgPrv").html(html);
            }else{
                alert(data.result.message)
            }
        }
    });


    $("#tvBannerProgress").hide();

    $('#tvBannerImg').fileupload({
        url: $("#tvBannerImg").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#tvBannerProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#tvBannerProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#tvBannerProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="tvBanner" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/tvBanner/'+data.result.path+'"></a></div>';
                $("#tvBannerImgPrv").html(html);
            }else{
                alert(data.result.message)
            }
        }
    });


    $("#imagePhoneProgress").hide();

    $('#imagePhone').fileupload({
        url: $("#imagePhone").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#imagePhoneProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#imagePhoneProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#imagePhoneProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="imagePhone" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/phone/'+data.result.path+'"></a></div>';
                $("#imagePhoneImgPrv").append(html);
            }else{
                alert(data.result.message)
            }
        }
    });



    $("#imageTabletProgress").hide();

    $('#imageTablet').fileupload({
        url: $("#imageTablet").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#imageTabletProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#imageTabletProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#imageTabletProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                var html = '<div data-type="imageTablet" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/tablet/'+data.result.path+'"></a></div>';
                $("#imageTabletImgPrv").append(html);
            }else{
                alert(data.result.message)
            }
        }
    });


    $("#productVideoButton").on("click", function(){

        var videoHtmlCode = encodeURIComponent($("#productVideoInput").val());
        var uploadUrl = $(this).data("url");
        var prvDiv = $("#productVideoImgPrv");

        $.ajax({
            type:'POST',
            data: "videoHtmlCode="+videoHtmlCode,
            dataType:'json',
            url: uploadUrl,
            success:function(data){
                if(data.res == "ok" & data.videoId > 0 & data.videoHtmlCode.length > 2){
                    $("#productVideoInput").val("");
                    var videoHtml = '<div data-type="productVideo" data-id="'+data.videoId+'" class="large-6 medium-6 columns" data-removeurl="">';
                    videoHtml += '<i class="fi-x large fi-delete-video"></i>';
                    videoHtml += '<div class="flex-video">';
                    videoHtml += data.videoHtmlCode;
                    videoHtml += '</div>';
                    videoHtml += '</div>';
                    prvDiv.append(videoHtml);
                }else{
                    alert(data.message)
                }
            }
        });
        return false;

    });

    $(".fi-delete-video").on("click", function(){
        var parent = $(this).parent();
        var type = parent.data("type");
        var id = parent.data("id");
        var removeUrl = parent.data("removeurl");
        var data = "";
        //console.log(removeUrl + "**"+type+"----"+id);
        $.ajax({
            type:'POST',
            data:data,
            dataType:'json',
            url: removeUrl,
            success:function(data){
                if(data.res == "ok" & data.videoId > 0){
                    parent.fadeOut(500);
                }else{
                    alert(data.message)
                }
            }
        });
        return false;

    });





    $(".fi-delete-img").on("click", function(){
        var parent = $(this).parent();
        var type = parent.data("type");
        var id = parent.data("id");
        var removeUrl = parent.data("removeurl");
        var data = "";
        //console.log(removeUrl + "**"+type+"----"+id);
        $.ajax({
            type:'POST',
            data:data,
            dataType:'json',
            url: removeUrl,
            success:function(data){
                if(data.res == "ok" & data.imgId > 0){
                    parent.fadeOut(500);
                }else{
                    alert(data.message)
                }
            }
        });

    });


    $("#zrgFileProgress").hide();
    $('#zrgFile').fileupload({
        url: $("#zrgFile").data("url"),
        dataType: 'json',
        progressall: function (e, data) {
            $("#zrgFileProgress").show();
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#zrgFileProgress .meter').css(
                'width',
                progress + '%'
            );
        },
        done: function (e, data) {
            $("#zrgFileProgress").hide();

            if(data.result.res == "ok" & data.result.id > 0){
                //var html = '<div data-type="icon" data-id="'+data.result.id+'" class="large-4 medium-4 columns fi-cont"><i class="fi-x large fi-delete-img"></i><a class="th three columns" href="#"><img class="" src="/assets/images/product/icon/'+data.result.path+'"></a></div>';
                //$("#iconImgPrv").html(html);
                alert(data.result.message)
            }else{
                alert(data.result.message)
            }
        }
    });

});