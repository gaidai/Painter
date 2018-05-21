$(window).on('load',function(){
    var filesToUpload = [];
    $.fn.fileUploader = function ( sectionIdentifier) {
        var fileIdCounter = 0;
        var marker;
      
        this.closest(".files").change(function (evt) {
            //Validation: only images
            var types = ["image/gif","image/jpeg","image/jpg","image/png","image/bmp"];
            var type;
            var tenMbyte =  1024*1024*10;       
         
            var output = [];


            for (var i = 0; i < evt.target.files.length; i++) {
                if(filesToUpload.length >= 5){
                    $( "input[name='files1']").attr('disabled', 'disabled');
                    alert("Checked too many files!");

                    break;
                };


                type = evt.target.files[i].type;
                
                
                marker = false;
                for(var j = 0; j< types.length; j++){
                    if(types[j] == type){
                        marker = true;
                    };
                };
                if ( !marker ){
                    alert("One of the selected files has Inappropriate format ! Choose another!");
                    continue;
                };
                if ( evt.target.files[i].size > tenMbyte ){
                    alert("One of the selected files is too big ! ");

                    console.log(evt.target.files[i].size  +' > ' + tenMbyte);
                    continue;
                };
                fileIdCounter++;
                var file = evt.target.files[i];
                var fileId = sectionIdentifier + fileIdCounter;

                filesToUpload.push({
                    id: fileId,
                    file: file
                });
                var imageName = file.name ;
                if(file.name.length >40){
                    imageName = file.name.substring(1, 30) + "...";
                };
                var fileSize = 0;                   
                    
                if (file.size > 1024 * 1024) {
                    fileSize = (Math.round(file.size*100 / (1024 * 1024)) /100).toString() + ' MB';
                }else {
                    fileSize = (Math.round(file.size  / 1024)  ).toString() + ' KB';
                };
                var tmppath = URL.createObjectURL(file);

                var removeLink = "<a class=\"removeFile\" href=\"#\" data-fileid=\"" + fileId + "\"><i class='fas fa-times' style='color:red' title='Remove'></i></a>";

                output.push("<li ><div> <strong>", imageName, "</strong> - ", fileSize, ". &nbsp; &nbsp; ", removeLink, "<img  class='preview-img' src='"+tmppath+"'></img></div> </li> ");
               
            };
           

            $(this).children(".fileList")
                .append(output.join(""));
            setTimeout(previewForFiles(), 700)

            //reset the input to null - nice little chrome bug!
            evt.target.value = null;
        });

        $(this).on("click", ".removeFile", function (e) {
            e.preventDefault();       
            if( $( "input[name='files1']").attr('disabled') ){
                $( "input[name='files1']").removeAttr('disabled'); 
            };

            var fileId = $(this).parent().children("a").data("fileid");

            // loop through the files array and check if the name of that file matches FileName
            // and get the index of the match
            for (var i = 0; i < filesToUpload.length; ++i) {
                if (filesToUpload[i].id === fileId)
                    filesToUpload.splice(i, 1);
            }

            $(this).parent().parent().remove();
        });

        this.clear = function () {
            filesToUpload=[];

            $(this).children(".fileList").empty();
            if( $( "input[name='files1']").attr('disabled') ){
                $( "input[name='files1']").removeAttr('disabled'); 
            };
        };

        return this;
    };

    (function () {
       

        var filesUploader = $("#files1").fileUploader( "files1");
      

        $("#uploadBtn").click(function (e) {
            e.preventDefault();

            var formData = new FormData();

            for (var i = 0, len = filesToUpload.length; i < len; i++) {
                formData.append("files", filesToUpload[i].file);
            };
            formData.append("effect","negativ");

            $.ajax({
                url: "fileUploadServlet",
                data: formData,
                processData: false,
                contentType: false,
                type: "POST",
                success: function (data) {
                    alert("DONE");
                    filesUploader.clear(); // Clear choosed files
                    filesToUpload = [];
                    filesUploader = $("#files1").fileUploader(filesToUpload, "files1");
                    // var returnedData = JSON.parse(data);
                    var path = data.date + "/" + data.generatedString ;
                    console.log(data);
                    console.log(path);
                    // run function for show the Modal
                    $(".mask").addClass("activeM");
                     $(".myModal").show();
                    $("#resultLink a").attr("href", '/Multiinput/showDownloadedServlet?date='+data.date +'&generated='+
                                                                                     data.generatedString);
                    
                    // window.open('http://localhost:8080/Multiinput/showDownloadedServlet?date='+data.date +'&generated='+
                    //                                                                 data.generatedString, '_blank');
                                                                            
      
                   
                },
                error: function (data) {
                    alert("ERROR - " + data.responseText);
                }
            });
        });

    })();
});

function previewForFiles () { 
    $(".fileList div").each(function(index) {
        $(this ).mouseover(function() {
            $(this).children('img').show();
        });
        $(this ).mouseout(function () {
            $(this ).children('img').hide();
        });
    });
};

function alert (arg) {
    $('.alert-title').html(arg);
    $('.alert-content').html("You can load no more then 5 files,max size: 10 MB ,  only jpg/bmp/png/gif images");
    if( $(".alert").is(':visible') ){
        $(".alert").stop().stop().stop().stop()
                   .fadeOut(100).fadeIn(400).delay(2000).fadeOut(900);
    }else{      
        $(".alert").fadeIn(500).delay(4000).fadeOut(1000);
    };
};
$('.alert-close').click(function(event) {
    $(".alert").stop().stop().stop().fadeOut(200);
    console.log("hhh");
});
// Function for close the Modal

function closeModal(){
  $(".mask").removeClass("activeM");
}

// Call the closeModal function on the clicks/keyboard

$(".closeModal, .mask").on("click", function(){
  closeModal();
});

$(document).keyup(function(e) {
  if (e.keyCode == 27) {
    closeModal();
  }
});
