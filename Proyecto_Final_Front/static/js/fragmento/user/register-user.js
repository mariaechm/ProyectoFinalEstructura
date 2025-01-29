jQuery(document).ready(function () {

    jQuery.validator.addMethod("onlyNumbers", function (value, element) {
      return this.optional(element) || /^[0-9]+$/.test(value);
    }, "Por favor ingresa solo números");

    jQuery.validator.addMethod("noInitialSpaces", function (value, element) {
      return this.optional(element) || !/^\s/.test(value);
    }, "No se permiten espacios al inicio");

    jQuery("#registrar-usuario").validate({
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
        identificacion: {
          required: true,
          minlength: 6,
          maxlength: 13,
          onlyNumbers: true
        },
        celular: {
          required: true,
          minlength: 10,
          maxlength: 10,
          onlyNumbers: true
        },
        direccion: {
          required: true,
          minlength: 3,
          noInitialSpaces: true
        },
        fechaNacimiento: {
          required: true,
          date: true
        },
        correoElectronico: {
          required: true,
          email: true,
          noInitialSpaces: true
        },
        contrasena: {
          required: true,
          minlength: 8
        }
      },
      messages: {
        nombre: {
          required: "Por favor, ingresa el/los nombre/s",
          minlength: "El nombre debe contener al menos 3 caracteres"
        },
        apellido: {
          required: "Por favor, ingresa el/los apellido/s",
          minlength: "El apellido debe contener al menos 3 caracteres"
        },
        identificacion: {
          required: "Por favor, ingresa una identificación válida",
          minlength: "La identificación debe contener al menos 6 caracteres",
          maxlength: "La identificación debe como máximo 14 caracteres"
        },
        celular: {
          required: "Por favor, ingresa un número de celular",
          minlength: "El número de celular debe contener 10 caracteres",
          maxlength: "El número de celular debe contener 10 caracteres"
        },
        direccion: {
          required: "Por favor, ingresa una dirección",
          minlength: "La dirección debe contener al menos 3 caracteres"
        },
        fechaNacimiento: {
          required: "Por favor, ingresa una fecha Válida",
          date: "Por favor, ingresa una fecha Válida"
        },
        correoElectronico: {
          required: "Por favor, ingresa un correo Electrónico",
          email: "Por favor, ingresa un correo electrónico con el formato correcto"
        },
        contrasena: {
          required: "Por favor, ingresa la contraseña",
          minlength: "La contraseña debe contener al menos 8 caracteres"
        }
      }
    });

    flatpickr("#fechaNacimiento", {
      dateFormat: "Y-m-d",
    });
  });

  const togglePassword = document.getElementById('togglePassword');
  const passwordField = document.getElementById('contrasena');

  togglePassword.addEventListener('click', function () {

    const type = passwordField.type === 'password' ? 'text' : 'password';
    passwordField.type = type;

    this.classList.toggle('fa-eye-slash');
  });