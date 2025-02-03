jQuery(document).ready(function () {

    jQuery.validator.addMethod("onlyNumbers", function (value, element) {
      return this.optional(element) || /^[0-9]+$/.test(value);
    }, "Por favor, ingresar solo números");

    jQuery.validator.addMethod("esFlotante", function(value, element) {
        return this.optional(element) || /^-?\d+(\.\d+)?$/.test(value);
    }, "Por favor, introducir un número decimal válido");

    jQuery.validator.addMethod("noInitialSpaces", function (value, element) {
      return this.optional(element) || !/^\s/.test(value);
    }, "No se permiten espacios al inicio");

    jQuery.validator.addMethod("soloLetrasNumeros", function(value, element) {
        return this.optional(element) || /^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\s]+$/.test(value);
    }, "El campo solo puede contener letras, números y espacios.");

    jQuery("#ejercicioForm").validate({
      rules: {
        nombreE: {
          required: true,
          minlength: 5,
          noInitialSpaces: true,
          soloLetrasNumeros: true,
          maxlength: 90
        },
        tiempoD: {
          required: true,
          min: 0.1,
          max: 6.0,
          esFlotante: true
        },
        nroS: {
          required: true,
          min: 1,
          max: 12,
          onlyNumbers: true
        },
        nroRS: {
          required: true,
          min: 1,
          max: 30,
          onlyNumbers: true
        },
        img: {
          required: true,
          noInitialSpaces: true,
          url: true
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
        nombreE: {
          required: "Por favor, ingresar el nombre del ejercicio",
          minlength: "El nombre del ejercicio debe contener como mínimo 5 caracteres",
          maxlength: "El nombre del ejercicio debe contener como máximo 90 caracteres"
        },
        tiempoD: {
          required: "Por favor, ingresar el tiempo de descanso del ejercicio",
          min: "El tiempo de descanso entre series debe ser superior a 0 minutos",
          max: "El tiempo de descanso entre series debe ser inferior a 6 minutos"
        },
        nroS: {
          required: "Por favor, ingresar el número de series del ejercicio",
          min: "El número mínimo de series para el ejercicio debe ser 1",
          max: "El número máximo de series para el ejercicio debe ser 12"
        },
        nroRS: {
          required: "Por favor, ingresar el número de repeticiones por serie",
          min: "El número mínimo de repeticiones por serie debe ser 1",
          max: "El número mínimo de repeticiones por serie debe ser 30"
        },
        img: {
          required: "Por favor, ingresar una url de imagen del ejercicio",
          url: "Por favor, ingresar una url de imagen válida"
        },
        descrip: {
          required: "Por favor, ingresar una descripción del ejercicio",
          minlength: "La descripción del ejercicio debe contener como mínimo 50 caracteres",
          maxlength: "La descripción del ejercicio debe contener como máximo 600 caracteres"
        }
      }
    });
});