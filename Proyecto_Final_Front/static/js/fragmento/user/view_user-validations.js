function togglePassword(eyeButtonId, passwordFieldId) {
  const eyeButton = document.getElementById(eyeButtonId);
  const passwordField = document.getElementById(passwordFieldId);

  const type = passwordField.type === 'password' ? 'text' : 'password';
  passwordField.type = type;

  eyeButton.classList.toggle('fa-eye-slash')
}

jQuery('#document').ready(function() {

    jQuery.validator.addMethod("onlyNumbers", function (value, element) {
      return this.optional(element) || /^[0-9]+$/.test(value);
    }, "Por favor ingresa solo números");

    jQuery.validator.addMethod("noInitialSpaces", function (value, element) {
      return this.optional(element) || !/^\s/.test(value);
    }, "No se permiten espacios al inicio");

    jQuery('#perfil-update-form').validate({
      rules: {
        nickName: {
          required: true,
          minlength: 3,
          noInitialSpaces: true

        },
        objetivoCliente: {
          required: true,
          minlength: 3,
          noInitialSpaces: true
          
        }
      },
      messages: {
        nickName: {
          required: "Por favor, ingrese un nickName",
          minlength: "El nickname debe tener al menos 3 caracteres"
        },
        objetivoCliente: {
          required: "Por favor, ingrese el/los objetivo/s",
          minlength: "El objetivo debe contener al menos 3 caracteres"
        }
      }
    });

    jQuery('#persona-update-form').validate({
      rules: {
        nombre: {
          required: true,
          minlength: 3,
          noInitialSpaces: true
        },
        apellido: {
          required: true,
          minlength: 3,
          noInitialSpaces: true
        },
        celular: {
          required: true,
          onlyNumbers: true,
          minlength: 10,
          maxlength: 10,
        },
        direccion: {
          required: true,
          minlength: 3,
          noInitialSpaces: true
        },
        identificacion: {
          required: true,
          minlength: 6,
          maxlength: 14, // REVISAR BIEN POR FAVOOOOOOOOR y en el registro de paso!!!
          onlyNumbers: true
        },
        fechaNacimiento: {
          required: true
        }
      },
      messages: {
        nombre: {
          required: "Por favor, escriba el/los nombre/s",
          minlength: "El nombre debe contener al menos 3 caracteres",
        },
        apellido: {
          required: "Por favor, escriba el/los apellido/s",
          minlength: "El apellido debe contener al menos 3 caracteres",
        },
        celular: {
          required: "Por favor, escriba el número de celular",
          minlength: "El número de celular debe contener 10 caracteres",
          maxlength: "El número de celular debe contener 10 caracteres"
        },
        direccion: {
          required: "Por favor, escriba la dirección",
          minlength: "la dirección debe contener al menos 3 caracteres",
        },
        identificacion: {
          required: "Por favor escriba la identificación",
          minlength: "La identificación debe contener al menos 6 caracteres",
          maxlength: "La identificación debe contener como máximo 14 caracteres", // REVISAR BIEN POR FAVOOOOOOOOR y en el registro de paso!!!
        },
        fechaNacimiento: {
          required: "Por favor, ingrese la fecha de nacimiento"
        }
      }
    });

    jQuery('#change-password-form').validate({
      rules: {
        contrasena: {
          required: true,
          minlength: 8,
        },
        nuevaContrasena1: {
          required: true,
          minlength: 8
        },
        newContrasena: {
          required: true,
          minlength: 8,
          equalTo: '#nueva-contrasena-1'
        }
      },
      messages: {
        contrasena: {
          required: "Por favor, escriba la contraseña actual",
          minlength: "La contraseña debería tener como mínimo 8 caracteres",
        },
        nuevaContrasena1: {
          required: "Por favor, ingrese la nueva contraseña",
          minlength: "La contraseña nueva debe contener al menos 8 caracteres"
        },
        newContrasena: {
          required: "Repita la nueva contraseña",
          minlength: "La contraseña está incompleta",
          equalTo: "Las contraseñas no coinciden"
        }
      }
    });

    flatpickr("#fechaNacimiento-persona", {
          dateFormat: "Y-m-d",
    });

  });

  