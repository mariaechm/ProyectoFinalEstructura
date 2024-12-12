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
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios)


@router_ejercicio.route('/admin/ejercicios/register')
def view_register_ejercicio():
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio")
    print(r.json())
    data = r.json()["data"]
    return render_template('fragmento/ejercicios/registro.html', list = data)


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
    }

    r = requests.post("http://localhost:8080/api/ejercicios/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/admin/ejercicios/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/admin/ejercicios/list")
    

@router_ejercicio.route('/admin/ejercicios/edit/<id>')
def view_edit_ejercicio(id):
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio")
    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/ejercicios/get/"+id)
    print(r1.json())
    data1 = r1.json()["data"]
    if(r1.status_code == 200):
        return render_template('fragmento/ejercicios/editar.html', list = data, ejercicio=data1)
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
    }

    r = requests.post("http://localhost:8080/api/ejercicios/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/admin/ejercicios/list")
    else:
        flash(str(dat["data"]), category='error')
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
        flash("¡No se ha podido eliminar!", category='error')
        return redirect("/admin/ejercicios/list")