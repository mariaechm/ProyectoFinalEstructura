jQuery(document).ready(function () {

  jQuery.validator.addMethod("onlyNumbers", function (value, element) {
    return this.optional(element) || /^[0-9]+$/.test(value);
  }, "Por favor ingresa solo números");

  jQuery.validator.addMethod("noInitialSpaces", function (value, element) {
    return this.optional(element) || !/^\s/.test(value);
  }, "No se permiten espacios al inicio");

  jQuery.validator.addMethod("soloLetras", function (value, element) {
    return this.optional(element) || /^[a-zA-ZáéíóúÁÉÍÓÚñÑ\s]+$/.test(value);
  }, "El campo solo puede contener letras y espacios.");

  jQuery.validator.addMethod("direccionValida", function (value, element) {
    return this.optional(element) || /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s,.-]+$/.test(value);
  }, "La dirección solo puede contener letras, números, espacios y algunos caracteres especiales (.,-).");

  jQuery.validator.addMethod("mayorDe15", function (value, element) {
    if (this.optional(element)) return true;
    var fechaLimite = new Date();
    fechaLimite.setFullYear(fechaLimite.getFullYear() - 15);
    var fechaNacimiento = new Date(value);
    return fechaNacimiento <= fechaLimite;
  }, "La fecha debe ser anterior a 15 años desde la fecha actual.");

  jQuery.validator.addMethod("noMasDe100Anios", function (value, element) {
    var inputDate = new Date(value);
    var currentDate = new Date();
    var date100YearsAgo = new Date();
    date100YearsAgo.setFullYear(currentDate.getFullYear() - 100);

    return this.optional(element) || (inputDate >= date100YearsAgo);
  }, "La fecha no puede ser anterior a 100 años desde la fecha actual.");

  jQuery("#registrar-usuario").validate({
    rules: {
      nombre: {
        required: true,
        minlength: 3,
        noInitialSpaces: true,
        soloLetras: true,
        maxlength: 50
      },
      apellido: {
        required: true,
        minlength: 3,
        noInitialSpaces: true,
        soloLetras: true,
        maxlength: 50
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
        noInitialSpaces: true,
        direccionValida: true,
        maxlength: 100
      },
      fechaNacimiento: {
        required: true,
        date: true,
        mayorDe15: true,
        noMasDe100Anios: true
      },
      correoElectronico: {
        required: true,
        email: true,
        noInitialSpaces: true,
        maxlength: 50,
        minlength: 3
      },
      contrasena: {
        required: true,
        minlength: 8
      }
    },
    messages: {
      nombre: {
        required: "Por favor, ingresa el/los nombre/s",
        minlength: "El nombre debe contener al menos 3 caracteres",
        maxlength: "El nombre debe contener como máximo 50 caracteres"
      },
      apellido: {
        required: "Por favor, ingresa el/los apellido/s",
        minlength: "El apellido debe contener al menos 3 caracteres",
        maxlength: "El apellido debe contener como máximo 50 caracteres"
      },
      identificacion: {
        required: "Por favor, ingresa una identificación válida",
        minlength: "La identificación debe contener al menos 6 caracteres",
        maxlength: "La identificación debe como máximo 13 caracteres"
      },
      celular: {
        required: "Por favor, ingresa un número de celular",
        minlength: "El número de celular debe contener 10 caracteres",
        maxlength: "El número de celular debe contener 10 caracteres"
      },
      direccion: {
        required: "Por favor, ingresa una dirección",
        minlength: "La dirección debe contener al menos 3 caracteres",
        maxlength: "La dirección debe contener como máximo 100 caracteres"
      },
      fechaNacimiento: {
        required: "Por favor, ingresa una fecha Válida",
        date: "Por favor, ingresa una fecha Válida"
      },
      correoElectronico: {
        required: "Por favor, ingresa un correo Electrónico",
        email: "Por favor, ingresa un correo electrónico con el formato correcto",
        minlength: "El correo electrónico debe contener al menos 3 caracteres",
        maxlength: "El correo electrónico debe contener como máximo 50 caracteres"
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