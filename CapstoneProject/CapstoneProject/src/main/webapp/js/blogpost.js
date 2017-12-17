/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    tinymce.init({
        selector: "textarea",

        // ===========================================
        // INCLUDE THE PLUGIN
        // ===========================================

        plugins: [
            "advlist autolink lists link image charmap print preview anchor",
            "searchreplace visualblocks code fullscreen",
            "insertdatetime media table contextmenu paste jbimages"
        ],

        // ===========================================
        // PUT PLUGIN'S BUTTON on the toolbar
        // ===========================================

        toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",

        // ===========================================
        // SET RELATIVE_URLS to FALSE (This is required for images to display properly)
        // ===========================================

       

    });



    // Edit Post Modal Opening
    $('#editBlogPostModal').on('shown.bs.modal', function (e) {

        $('.errorMessages').text('');

        var link = $(e.relatedTarget);

        var blogPostId = link.data("blogpost-id");

        var mce = tinyMCE.get('content');

        $.ajax({
            url: contextRoot + "/blogpost/" + blogPostId,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {

                tinymce.remove();

                $('#editId').val(data.id);
                $('#editTitle').val(data.title);
                $('#htmlContent').val(data.htmlContent);
                $('#editPostDate').val(data.publishDate);
                $('#editExpirationDate').val(data.expirationDate);
                $('#editUser').val(data.user);

                var postCategoryList = data.blogPostCategories;

                populateCategoryCheckBoxes(postCategoryList);

                $('#createCategoryModal').on('hidden.bs.modal', function(e) {
                    populateCategoryCheckBoxes(postCategoryList);
                });



                tinymce.init({
                    selector: "textarea",

                    // ===========================================
                    // INCLUDE THE PLUGIN
                    // ===========================================

                    plugins: [
                        "advlist autolink lists link image charmap print preview anchor",
                        "searchreplace visualblocks code fullscreen",
                        "insertdatetime media table contextmenu paste jbimages"
                    ],

                    // ===========================================
                    // PUT PLUGIN'S BUTTON on the toolbar
                    // ===========================================

                    toolbar: "insertfile undo redo | styleselect | bold italic | alignleft aligncenter alignright alignjustify | bullist numlist outdent indent | link image jbimages",

                    // ===========================================
                    // SET RELATIVE_URLS to FALSE (This is required for images to display properly)
                    // ===========================================


                });


            },
            error: function (data, status) {
                console.log("Error reading DVD");
            }
        });

    });

    // Edit Post Functionality
    $('#editBlogPostButton').on('click', function (e) {

        tinymce.triggerSave();

        $('.errorMessages').text('');

        var list = [];

        e.preventDefault();

        // Create a list of all the boxes taht are checked
        $('.checkboxes').each(function(index, checkbox) {

            console.log(checkbox);

            if ( $('#' + checkbox.id ).is(':checked') ) {
                console.log("This is working");
                console.log("In the loop, Array: " + list);
                list.push(checkbox.value);
            }

            console.log(checkbox.value);

        });

        var editBlogPostCommand = {
            id: $('#editId').val(),
            title: $('#editTitle').val(),
            publishDate: $('#editPostDate').val(),
            expirationDate: $('#editExpirationDate').val(),
            htmlContent: $('#htmlContent').val(),
            categoryIds: list

        };

        var editBlogPostCommandData = JSON.stringify(editBlogPostCommand);

        $.ajax({
            url: contextRoot + "/blogpost/" + editBlogPostCommand.id,
            type: "PUT",
            data: editBlogPostCommandData,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (data, status) {


                var tableRow = buildBlogPostRow(data);

                $('#blogPost-row-' + data.id).replaceWith($(tableRow));

                $('#editBlogPostModal').modal('hide');

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
    $('#deleteBlogPostModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);

        var blogPostId = link.data("blogpost-id");

        $('#deletePostId').val(blogPostId);

    });

    // Delete Post Functionality
    $('#deleteBlogPostButton').on('click', function (e) {

        e.preventDefault();

        var blogPostId = $('#deletePostId').val();

        var blogPostIdData = JSON.stringify(blogPostId);

        $.ajax({
            url: contextRoot + "/blogpost/" + blogPostId,
            type: "DELETE",
            data: blogPostId,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function (blogPost, status) {

                $('#blogPost-row-' + blogPost.id).remove();

                $('#deleteBlogPostModal').modal('hide');

            },
            error: function (data, status) {

            }

        });


    });

    $('#createCategoryButton').on('click', function(e) {

        e.preventDefault();

        $('.errorMessages').text('');

        var category = {
          categoryName: $('#createCategoryName').val()
        };

        var categoryData = JSON.stringify(category);

        $.ajax({
            url: contextRoot + "/blogpostcategory" ,
            type: "POST" ,
            data: categoryData ,
            dataType: "json" ,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            } ,
            success: function(viewModel, status) {

                var categoryList = viewModel.fullCategoryList;

                $('.categoryRows').remove();

                var tableRows = buildCategoryRows(categoryList);

                $('#categoryTable').append( $(tableRows) );

                $('#createCategoryModal').modal('hide');

            } ,
            error: function(data, status) {

                var errors = data.responseJSON.errors;

                $.each(errors, function (index, error) {

                    $('#create-' + error.fieldName + '-validation-error').prop(error.errorMessage);

                });

            }
        });

    });


    function buildBlogPostRow(data) {

        var date = new Date(data.dateToPost);


        var tableRow = '\
                <tr id="blogPost-row-' + data.id + '"> \n\
                    <td>' + data.title + '</td> \n\
                    <td>' + data.publishDate + '</td> \n\
                    <td>' + data.expirationDate + '</td> \n\
                    <td><a data-blogPost-id="' + data.id + '" data-toggle="modal" data-target="#editBlogPostModal" >Edit Post</a></td> \n\
                    <td><a data-blogPost-id="' + data.id + '" data-toggle="modal" data-target="#deleteBlogPostModal" >Delete Post</a></td> \n\
                </tr>';

        return tableRow;

    };

    function buildCategoryRows(categoryList) {

        var tableRows = '\
            <tr class="categoryRows"> ';

            $.each(categoryList, function(index, category) {
                if ( (index) % 4 == 0 && (index) != 0) {
                    tableRows += '</tr> <tr class="categoryRows">'
                }

            tableRows += '<td class="borderless" style="text-align: center;">' + category.categoryName + ' <input type="checkbox" id="category-' +category.id + '" name="blogPostCategory" value="' + category.id + '" /></td>';


            });

        return tableRows;

    }

    function populateCategoryCheckBoxes(postCategoryList) {
        $.each(postCategoryList, function (index, category) {

            $('#category-' + category.id).prop("checked", true);

        });
    }

});
