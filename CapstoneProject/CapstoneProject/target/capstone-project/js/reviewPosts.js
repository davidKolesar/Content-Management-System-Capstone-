/**
 * Created by paulharding on 10/17/16.
 */

$(document).ready(function () {

    $('.approveButtons').on('click', function(e) {

        e.preventDefault();

        var $this = $(this);

        var blogPostId = $(this).data('blogpost-id');

        var blogPostIdData = JSON.stringify(blogPostId);

        $.ajax({
            url: contextRoot + "/blogpost/approve/" + blogPostId,
            type: "POST",
            data: blogPostIdData,
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(blogPost, status) {

                $('#blogPost-table-' + blogPost.id).remove();

            },
            error: function(data, status) {

            }
        });

    });


    $('.rejectButtons').on('click', function(e) {

        e.preventDefault();

        var $this = $(this);

        var blogPostId = $(this).data('blogpost-id');

        var blogPostIdData = JSON.stringify(blogPostId);

        $.ajax({
            url: contextRoot + "/blogpost/reject/" + blogPostId,
            type: "POST",
            data: blogPostIdData,
            dataType: "json",
            beforeSend: function(xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-Type", "application/json");
            },
            success: function(blogPost, status) {
                $('#blogPost-table-' + blogPost.id).remove();
            },
            error: function(data, status) {

            }
        });

    });



});