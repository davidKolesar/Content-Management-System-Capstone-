/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

tinymce.init({
  selector: "textarea",
  height: 500,
  plugins: [
    "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
    "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
    "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
  ],

  toolbar1: "newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
  toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
  toolbar3: "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",

  menubar: false,
  toolbar_items_size: 'small',

  style_formats: [{
    title: 'Bold text',
    inline: 'b'
  }, {
    title: 'Red text',
    inline: 'span',
    styles: {
      color: '#ff0000'
    }
  }, {
    title: 'Red header',
    block: 'h1',
    styles: {
      color: '#ff0000'
    }
  }, {
    title: 'Example 1',
    inline: 'span',
    classes: 'example1'
  }, {
    title: 'Example 2',
    inline: 'span',
    classes: 'example2'
  }, {
    title: 'Table styles'
  }, {
    title: 'Table row 1',
    selector: 'tr',
    classes: 'tablerow1'
  }],

  templates: [{
    title: 'Test template 1',
    content: 'Test 1'
  }, {
    title: 'Test template 2',
    content: 'Test 2'
  }],
  content_css: [
    '//www.tinymce.com/css/codepen.min.css'
  ]
});


$(document).ready(function () {

    // Edit Page Modal Opening
    $('#editStaticPageModal').on('shown.bs.modal', function (e) {

        $('.errorMessages').text('');

        var link = $(e.relatedTarget);

        var staticPageId = link.data("staticpage-id");

        var mce = tinyMCE.get('content');

        $.ajax({
            url: contextRoot + "/staticpage/" + staticPageId,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {

            console.log(data);

                tinymce.remove();

                $('#editId').val(data.id);
                $('#editTitle').val(data.title);
                $('#htmlContent').val(data.htmlContent);
                $('#editPostDate').val(data.dateToPost);
                $('#editExpirationDate').val(data.expirationDate);
                $('#editUser').val(data.user);
               
                tinymce.init({
                    selector: "textarea",
                    height: 500,
                    plugins: [
                        "advlist autolink autosave link image lists charmap print preview hr anchor pagebreak spellchecker",
                        "searchreplace wordcount visualblocks visualchars code fullscreen insertdatetime media nonbreaking",
                        "table contextmenu directionality emoticons template textcolor paste fullpage textcolor colorpicker textpattern"
                    ],

                    toolbar1: "newdocument fullpage | bold italic underline strikethrough | alignleft aligncenter alignright alignjustify | styleselect formatselect fontselect fontsizeselect",
                    toolbar2: "cut copy paste | searchreplace | bullist numlist | outdent indent blockquote | undo redo | link unlink anchor image media code | insertdatetime preview | forecolor backcolor",
                    toolbar3: "table | hr removeformat | subscript superscript | charmap emoticons | print fullscreen | ltr rtl | spellchecker | visualchars visualblocks nonbreaking template pagebreak restoredraft",

                    menubar: false,
                    toolbar_items_size: 'small',

                    style_formats: [{
                        title: 'Bold text',
                        inline: 'b'
                    }, {
                        title: 'Red text',
                        inline: 'span',
                        styles: {
                            color: '#ff0000'
                        }
                    }, {
                        title: 'Red header',
                        block: 'h1',
                        styles: {
                            color: '#ff0000'
                        }
                    }, {
                        title: 'Example 1',
                        inline: 'span',
                        classes: 'example1'
                    }, {
                        title: 'Example 2',
                        inline: 'span',
                        classes: 'example2'
                    }, {
                        title: 'Table styles'
                    }, {
                        title: 'Table row 1',
                        selector: 'tr',
                        classes: 'tablerow1'
                    }],

                    templates: [{
                        title: 'Test template 1',
                        content: 'Test 1'
                    }, {
                        title: 'Test template 2',
                        content: 'Test 2'
                    }],
                    content_css: [
                        '//www.tinymce.com/css/codepen.min.css'
                    ]
                });


            },
            error: function (data, status) {
                console.log("Error reading StaticPage");
            }
        });

    });

    // Edit Page Functionality
    $('#editStaticPageButton').on('click', function (e) {

        tinymce.triggerSave();

        $('.errorMessages').text('');

        e.preventDefault();

        var staticPage = {
            id: $('#editId').val(),
            title: $('#editTitle').val(),
            dateToPost: $('#editPostDate').val(),
            expirationDate: $('#editExpirationDate').val(),
            htmlContent: $('#htmlContent').val()

        };

        var staticPageData = JSON.stringify(staticPage);

        $.ajax({
            url: contextRoot + "/staticpage/" + staticPage.id,
            type: "PUT",
            data: staticPageData,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data, status) {


                var tableRow = buildStaticPageRow(data);

                $('#staticPage-row-' + data.id).replaceWith($(tableRow));

                $('#editStaticPageModal').modal('hide');

            },
            error: function (data, status) {

                var errors = data.responseJSON.errors;

                $.each(errors, function (index, error) {

                    $('#edit-' + error.fieldName + '-validation-error').append(error.errorMessage);

                });

            }
        });

    });

    // Delete Post Modal Opening
    $('#deleteStaticPageModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);

        var staticPageId = link.data("staticpage-id");

        $('#deletePostId').val(staticPageId);

    });

    // Delete Page Functionality
    $('#deleteStaticPageButton').on('click', function (e) {

        e.preventDefault();

        var staticPageId = $('#deletePostId').val();

        var staticPageIdData = JSON.stringify(staticPageId);

        $.ajax({
            url: contextRoot + "/staticpage/" + staticPageId,
            type: "DELETE",
            data: staticPageId,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (staticPage, status) {

                $('#staticPage-row-' + staticPage.id).remove();

                $('#deleteStaticPageModal').modal('hide');

            },
            error: function (data, status) {



            }
        });


    });

    function buildStaticPageRow(data) {

        var date = new Date(data.dateToPost);


        var tableRow = '\
                <tr id="staticPage-row-' + data.id + '"> \n\
                    <td>' + data.title + '</td> \n\
                    <td>' + data.dateToPost + '</td> \n\
                    <td>' + data.expirationDate + '</td> \n\
                    <td><a data-staticPage-id="' + data.id + '" data-toggle="modal" data-target="#editStaticPageModal" >Edit Post</a></td> \n\
                    <td><a data-staticPage-id="' + data.id + '" data-toggle="modal" data-target="#deleteStaticPageModal" >Delete Post</a></td> \n\
                </tr>';

        return tableRow;

    };
});
