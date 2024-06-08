$(document).on("click", "#btnagregar", function(){
    $("#txtnombre").val("");
    $("#txtapellido").val("");
    $("#txtemail").val("");
    $("#txtemail").prop('readonly', false);
    $("#txtusuario").val("");
    $("#txtusuario").prop('readonly', false);
    $("#txtpassword").val("");
    $("#hddidusuario").val("0");
    $("#switchusuario").hide();
    $("#cbactivo").prop("checked", false);
    $("#divmsgpassword").show();
    $("#btnenviar").hide();
    $("#modalusuario").modal("show");
});

$(document).on("click", ".btnactualizar", function(){
    $.ajax({
        type: "GET",
        url: "/seguridad/usuario/"+$(this).attr("data-usuid"),
        dataType: "json",
        success: function(resultado){
           $("#txtnombre").val(resultado.nombres);
           $("#txtapellido").val(resultado.apellidos);
           $("#txtemail").val(resultado.email);
           $("#txtemail").prop('readonly', true);
           $("#txtusuario").val(resultado.nomusuario);
           $("#txtusuario").prop('readonly', true);
           $("#hddidusuario").val(resultado.idusuario);
           $("#txtpassword").val(""); // Deja el campo de contraseña vacío al actualizar
           $("#switchusuario").show();
           $("#divmsgpassword").hide();
           $("#btnenviar").show();
           if(resultado.activo)
              $("#cbactivo").prop("checked", true);
           else
              $("#cbactivo").prop("checked", false);
        }
    })
    $("#modalusuario").modal("show");
});

$(document).on("click", "#btnguardar", function() {
    $.ajax({
        type: "POST",
        url: "/seguridad/usuario",
        contentType: "application/json",
        data: JSON.stringify({
            idusuario: $("#hddidusuario").val(),
            nomusuario: $("#txtusuario").val(),
            nombres: $("#txtnombre").val(),
            apellidos: $("#txtapellido").val(),
            email: $("#txtemail").val(),
            password: $("#txtpassword").val(), // Agregar el campo de contraseña
            activo: $("#cbactivo").prop("checked")
        }),
        success: function(resultado) {
            if (resultado.respuesta) {
                listarUsuarios();
            }
            alert(resultado.mensaje);
        }
    });
    $("#modalusuario").modal("hide");
});

function listarUsuarios(){
    $.ajax({
        type: "GET",
        url: "/seguridad/usuario/lista",
        dataType: "json",
        success: function(resultado){
            $("#tblusuario > tbody").html("");
            $.each(resultado, function(index, value){
                $("#tblusuario > tbody").append(`<tr>`+
                `<td>${value.nombres}</td>`+
                `<td>${value.apellidos}</td>`+
                `<td>${value.nomusuario}</td>`+
                `<td>${value.email}</td>`+
                `<td>${value.activo}</td>`+
                `<td><button type='button' class='btn btn-primary btnactualizar' `+
                    `data-usuid="${value.idusuario}">Actualizar`+
                `</button></td>`+
                `</tr>`);
            });
        }
    });
}






$(document).ready(function() {
    // Vincular el evento de clic al enlace "Registro de Usuarios"
    $("#registroUsuariosLink").on("click", function(event) {
        event.preventDefault();
        $('#modalusuario').modal('show');
    });
});








$(document).ready(function() {
    // Vincular el evento de clic al enlace "Cambiar Password"
    $("#cambiarPasswordLink").on("click", function(event) {
        event.preventDefault(); // Prevenir el comportamiento predeterminado del enlace

        // Mostrar el modal de cambio de contraseña
        $('#modalCambiarContrasena').modal('show');
    });

    // Vincular el evento de clic al botón "Guardar Cambios"
    $("#btnCambiarContrasena").on("click", function(event) {
        // Prevenir el comportamiento predeterminado del botón
        event.preventDefault();

        // Obtener la nueva contraseña y la confirmación de la nueva contraseña
        var nuevaContrasena = $("#txtNuevaContrasena").val();
        var confirmarContrasena = $("#txtConfirmarContrasena").val();

        // Verificar si las contraseñas coinciden
        if (nuevaContrasena !== confirmarContrasena) {
            // Mostrar un mensaje de error si las contraseñas no coinciden
            $("#divMensajeCambiarContrasena").text("Las contraseñas no coinciden.");
            return;
        }

        // Validar la contraseña con esta validacion La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial
        var passwordPattern = /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)(?=.*[@$!%*?&])[A-Za-z\d@$!%*?&]{8,}$/;
        if (!passwordPattern.test(nuevaContrasena)) {
            // Mostrar un mensaje de error si la contraseña no cumple con los requisitos
            $("#divMensajeCambiarContrasena").text("La contraseña debe tener al menos 8 caracteres, una letra mayúscula, una letra minúscula, un número y un carácter especial.");
            return;
        }

        // Crear un objeto con los datos a enviar al servidor
        var requestData = {
            nuevaContrasena: nuevaContrasena
        };

        // Enviar la solicitud al servidor utilizando AJAX
        $.ajax({
            type: "PUT", // Método HTTP para cambiar la contraseña
            url: "/seguridad/cambiar-contrasena", // Ruta real a tu endpoint de cambio de contraseña
            contentType: "application/json",
            data: JSON.stringify(requestData),
            success: function(response) {
                // Manejar la respuesta del servidor
                alert("Contraseña cambiada exitosamente.");
                $('#modalCambiarContrasena').modal('hide');
            },
            error: function(xhr, status, error) {
                // Manejar el error en caso de que falle la solicitud al servidor
                console.error("Error al cambiar la contraseña:", error);
                $("#divMensajeCambiarContrasena").text(xhr.responseText || "Error al cambiar la contraseña. Inténtalo de nuevo más tarde.");
            }
        });
    });
});