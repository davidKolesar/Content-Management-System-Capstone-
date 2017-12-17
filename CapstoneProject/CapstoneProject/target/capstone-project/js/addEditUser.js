/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    console.log("true test");

    $('#CreateUserButton').on('click', function (e) {

        e.preventDefault();

        var myUser = {
            userName: $('#userNameCreate').val(),
            password: $('#passwordCreate').val(),
            roleName: $('#roleNameCreate').val()
        };

        var myUserData = JSON.stringify(myUser);

        $.ajax({
            url: contextRoot + "/user",
            type: "POST",
            data: myUserData,
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {

                var tableRow = buildUserRow(data);



                $('#userTable').append($(tableRow));


                $('#userNameCreate').val('');
                $('#roleNameCreate').val('');

            },
            error: function (data, status) {

                if (data.id == null) {
                    alert("Username is already taken. Please try another username.")
                }

                console.log("ERROR");

//                var errors = data.responseJSON.errors;
//
////                        responseJSON.errors;
//
//                $.each(errors, function (index, error) {
//
//
//                 
//                });
            }

        });

    });

    $('#viewUserModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);

        var userId = link.data('allusers-id');

        console.log("do you see ID" + userId);
        $.ajax({
            url: contextRoot + "/user/" + userId,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json")
            },
            success: function (data, status) {

                $('#viewUserName').text(data.userName);
                $('#viewroleName').text(data.roleName);


            },
            error: function (data, status) {
                alert("CONTACT NOT FOUND");
            }
        });


    });

 $('#editUserModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);

        var userId = link.data('allusers-id');

        $.ajax({
            url: contextRoot + "/user/" + userId,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json")
            },
            success: function (data, status) {
                $('#editId').val(data.id);
                $('#editUserName').val(data.userName);
                $('#editRoleName').val(data.roleName);
               
            },
            error: function (data, status) {
                alert("USER NOT ON FILE");
            }
        });


    });
    $('#editUserButton').on('click', function (e) {

        e.preventDefault();

        var myUser = {
            id: $('#editId').val(),
            userName: $('#editUserName').val(),
            password: $('#editPassword').val(),
            roleName: $('#editRoleName').val(),
        };

        var myUserData = JSON.stringify(myUser);
        console.log(contextRoot);
        //check to see if this prints out in the console before you proceed\.

        $.ajax({
            url: contextRoot + "/user/" + myUser.id,
            type: "PUT",
            data: myUserData,
            dataType: "json",
            beforeSend: function (xhr) {

                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-type", "application/json");

            },
            success: function (data, status) {

                var tableRow = buildUserRow(data);



                $('#users-row-' + data.id).replaceWith($(tableRow));
                $('#editUserModal').modal('hide');
                console.log(data.firstName);
            },
            error: function (data, status) {
                console.log("ERROR OCCURED!");
            }

        });

    });

    $('#deleteUserModal').on('shown.bs.modal', function (e) {

        var link = $(e.relatedTarget);

        var userId = link.data('allusers-id');

        $.ajax({
            url: contextRoot + "/user/" + userId,
            type: "GET",
            dataType: "json",
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
            },
            success: function (data, status) {
                $('#deleteId').val(data.id);
                $('#deleteUserName').text(data.userName);
                $('#deletePassword').text(data.password);
                $('#deleteroleName').text(data.roleName);
            },
            error: function (data, status) {
                alert("CONTACT NOT FOUND");
            }
        });


    });

    $('#deleteUserButton').on('click', function (e) {

        e.preventDefault();

        var userId = $('#deleteId').val();

        var myUser = {
            id: $('#deleteId').val(),
            userName: $('#deleteUserName').text(),
            password: $('#deletePassword').text(),
            roleName: $('#deleteroleName').text(),
        };

        var myUserData = JSON.stringify(myUser);
        console.log(contextRoot);

        $.ajax({
            url: contextRoot + "/user/" + userId,
            type: "DELETE",
            data: myUserData,
            beforeSend: function (xhr) {
                xhr.setRequestHeader("Accept", "application/json");
                xhr.setRequestHeader("Content-type", "application/json");
            },
            success: function (data, status) {


                $('#allusers-row-' + data.id).remove();
                $('#deleteUserModal').modal('hide');

            },
            error: function (data, status) {

                console.log("UNABLE TO DELETE DATA");
            }

        });

    });

    $('#viewUserModal').on('hidden.bs.modal', function () {
        $('#viewUserName').text('');
        $('#viewroleName').text('');

    });

    function buildUserRow(data) {
        var tableRow = '\
                    <tr id="allusers-row-' + data.id + '"> \n\\n\
                    <td>' + data.id + ' </td>\n\
                    <td><a data-allusers-id="' + data.id + '" data-toggle="modal" data-target="#viewUserModa">' + data.userName + '</td> \n\\n\
                    <td><a data-allusers-id="' + data.id + '" data-toggle="modal" data-target="#editUserModal">Edit user details</a></td> \n\
                    <td><a data-allusers-id="' + data.id + '" data-toggle="modal" data-target="#deleteUserModal">Remove user</a></td> \n\
                    </tr>';

        return tableRow;

    }


});

