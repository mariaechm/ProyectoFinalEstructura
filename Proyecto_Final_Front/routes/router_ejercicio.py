from .router import *
router_ejercicio = Blueprint('router_ejercicio',__name__)

@router_ejercicio.route('/admin/ejercicios/list')
def list_ejercicio():
    r =requests.get("http://localhost:8080/api/ejercicios/all")
    print(r.json())
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i += 1
    #TO DO: LÓGICA PARA LA VISTA DE USUARIO
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios)


@router_ejercicio.route('/admin/ejercicios/register')
def view_register_ejercicio():
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio")
    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/ejercicios/grupoMuscularObjetivo")
    print(r1.json())
    data1 = r1.json()["data"]
    return render_template('fragmento/ejercicios/registro.html', list = data, list1 = data1)


@router_ejercicio.route('/admin/ejercicios/save', methods=['POST'])
def save_ejercicio():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "nombreEjercicio": form['nombreE'],
        "descripcion": form['descrip'],
        "imagen": form['img'],
        "tiempoDescanso": form['tiempoD'],
        "nroSeries": form['nroS'],
        "nroRepSerie": form['nroRS'],
        "tipoEjercicio": form['tipoE'],
        "grupoMuscularObjetivo": form['grupoMuscular'],
    }

    r = requests.post("http://localhost:8080/api/ejercicios/save", data = json.dumps(dataF), headers = headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 201:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/admin/ejercicios/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/admin/ejercicios/list")
    

@router_ejercicio.route('/admin/ejercicios/edit/<id>')
def view_edit_ejercicio(id):
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio")
    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/ejercicios/get/"+id)
    print(r1.json())
    data1 = r1.json()["data"]
    r2 = requests.get("http://localhost:8080/api/ejercicios/grupoMuscularObjetivo")
    print(r2.json())
    data2 = r2.json()["data"]
    if(r1.status_code == 200):
        return render_template('fragmento/ejercicios/editar.html', list = data, ejercicio = data1, list2 = data2)
    else:
        flash(data1, category='error')
        return redirect("/admin/ejercicios/list")


@router_ejercicio.route('/admin/ejercicios/update', methods=['POST'])
def update_ejercicio():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "id": form['idE'],
        "nombreEjercicio": form['nombreE'],
        "descripcion": form['descrip'],
        "imagen": form['img'],
        "tiempoDescanso": form['tiempoD'],
        "nroSeries": form['nroS'],
        "nroRepSerie": form['nroRS'],
        "tipoEjercicio": form['tipoE'],
        "grupoMuscularObjetivo": form['grupoMuscular'],
    }

    r = requests.post("http://localhost:8080/api/ejercicios/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/admin/ejercicios/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/admin/ejercicios/list")
    

@router_ejercicio.route('/admin/ejercicios/delete/<id>')
def view_delete_ejercicio(id):
    return render_template('fragmento/ejercicios/eliminar.html', id=id)


@router_ejercicio.route('/admin/ejercicios/remove', methods=['POST'])
def delete_ejercicio():
    form = request.form
    r = requests.post("http://localhost:8080/api/ejercicios/delete/"+form['idE'])
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/admin/ejercicios/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/admin/ejercicios/list")


@router_ejercicio.route('/admin/ejercicios/sort/<atributo>/<orden>/<metodoOrden>')
def order_ejercicio(atributo, orden, metodoOrden):
    r = requests.get('http://localhost:8080/api/ejercicios/sort/'+ atributo + '/' + orden + '/' + metodoOrden)
    print(r.json())
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i+=1
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios)


@router_ejercicio.route('/admin/ejercicios/search/<atributo>/<valor>')
def search_ejercicio(atributo, valor):
    r = requests.get('http://localhost:8080/api/ejercicios/search/'+ atributo + '/' + valor)
    print(r.json())
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i+=1
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios)


@router_ejercicio.route('/admin/ejercicios/informacion/<id>')
def info_ejercicio(id):
    r = requests.get("http://localhost:8080/api/ejercicios/get/"+id)
    print(r.json())
    data = r.json()["data"]
    return render_template('fragmento/ejercicios/informacion.html', ejercicio = data)