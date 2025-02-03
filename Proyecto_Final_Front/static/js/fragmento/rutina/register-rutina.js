jQuery(document).ready(function () {

    jQuery.validator.addMethod("onlyNumbers", function (value, element) {
      return this.optional(element) || /^[0-9]+$/.test(value);
    }, "Por favor, ingresar solo números");

    jQuery.validator.addMethod("noInitialSpaces", function (value, element) {
      return this.optional(element) || !/^\s/.test(value);
    }, "No se permiten espacios al inicio");

    jQuery.validator.addMethod("soloLetrasNumeros", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]+$/.test(value);
    }, "El campo solo puede contener letras, números y espacios.");

    jQuery("#rutinaForm").validate({
      rules: {
        nombreR: {
          required: true,
          minlength: 4,
          noInitialSpaces: true,
          soloLetrasNumeros: true,
          maxlength: 90
        },
        nroE: {
          required: true,
          min: 1,
          max: 12,
          onlyNumbers: true
        },
        descrip: {
          required: true,
          minlength: 50,
          maxlength: 600,         
          noInitialSpaces: true,
          soloLetrasNumeros: true
        }
      },
      messages: {
        nombreR: {
          required: "Por favor, ingresar el nombre de la rutina",
          minlength: "El nombre de la rutina debe contener como mínimo 5 caracteres",
          maxlength: "El nombre de la rutina debe contener como máximo 90 caracteres"
        },
        nroE: {
          required: "Por favor, ingresar el número de ejercicios de la rutina",
          min: "El número mínimo de ejercicios para la rutina debe ser 1",
          max: "El número mínimo de ejercicios para la rutina debe ser 12"
        },
        descrip: {
          required: "Por favor, ingresar una descripción de la rutina",
          minlength: "La descripción de la rutina debe contener como mínimo 50 caracteres",
          maxlength: "La descripción de la rutina debe contener como máximo 600 caracteres"
        }
      }
    });
});