$('#addImage').on('click', function (e) {

    e.preventDefault();

    console.log("addImage button works");

    var image = {
        pic: $('#image').val()
    };

    var imageData = JSON.stringify(image);

    $.ajax({
        url: contextRoot + "/image",
        type: "POST",
        data: imageData,
        dataType: "json",
        beforeSend: function (xhr) {
            xhr.setRequestHeader("Accept", "application/json");
            xhr.setRequestHeader("Content-Type", "application/json");
        },
        success: function (data, status) {

            console.log("success");

            tinymce.triggerSave();

        },
        error: function (data, status) {

            console.log(image.pic);
            console.log("error");

        }
    });

});