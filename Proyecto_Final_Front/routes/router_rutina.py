from .router import *
router_rutina = Blueprint('router_rutina',__name__)

@router_rutina.route('/admin/rutinas/list')
def list_rutina():
    r =requests.get("http://localhost:8080/api/rutinas/all")
    print(r.json())
    rutinas = r.json()["data"]
    i = 1
    for rutina in rutinas:
        rutina['numero'] = i
        i += 1
    return render_template('fragmento/rutinas/lista.html', rutinas = rutinas)


@router_rutina.route('/admin/rutinas/register')
def view_register_rutina():
    r = requests.get("http://localhost:8080/api/rutinas/objetivoRutina")
    print(r.json())
    data = r.json()["data"]
    ejercicios = requests.get("http://localhost:8080/api/ejercicios/all").json()["data"]
    return render_template('fragmento/rutinas/registro.html', list = data, ejercicios=ejercicios)


@router_rutina.route('/admin/rutinas/save', methods=['POST'])
def save_rutina():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "nombreRutina": form['nombreR'],
        "descripcion": form['descrip'],
        "nroEjercicios": form['nroE'],
        "idEjercicio": request.form.getlist("seleccionEjercicios"),
        "objetivoRutina": form['objR'],
    }

    r = requests.post("http://localhost:8080/api/rutinas/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    print("***SELECCION EJERCICIOS***")
    print(request.form.getlist('seleccionEjercicios'))
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/admin/rutinas/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/admin/rutinas/list")
    

@router_rutina.route('/admin/rutinas/edit/<id>')
def view_edit_rutina(id):
    r = requests.get("http://localhost:8080/api/rutinas/objetivoRutina")
    print(r.json())
    data = r.json()["data"]
    r2 = requests.get("http://localhost:8080/api/rutinas/get/"+id)
    print(r2.json())
    data2 = r2.json()["data"]
    ejercicios = requests.get("http://localhost:8080/api/ejercicios/all").json()["data"]
    if(r1.status_code == 200):
        return render_template('fragmento/rutinas/editar.html', list = data, rutina=data2, ejercicios=ejercicios)
    else:
        flash(data1, category='error')
        return redirect("/admin/rutinas/list")


@router_rutina.route('/admin/rutinas/update', methods=['POST'])
def update_rutina():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "id": form['idR'],
        "nombreRutina": form['nombreR'],
        "descripcion": form['descrip'],
        "nroEjercicios": form['nroE'],
        "idEjercicio": request.form.getlist("seleccionEjercicios"),
        "objetivoRutina": form['objR'],
    }

    r = requests.post("http://localhost:8080/api/rutinas/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    print("***SELECCION EJERCICIOS***")
    print(request.form.getlist('seleccionEjercicios'))
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/admin/rutinas/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/admin/rutinas/list")
    

@router_rutina.route('/admin/rutinas/delete/<id>')
def view_delete_rutina(id):
    return render_template('fragmento/rutinas/eliminar.html', id=id)


@router_rutina.route('/admin/rutinas/remove', methods=['POST'])
def delete_rutina():
    form = request.form
    r = requests.post("http://localhost:8080/api/rutinas/delete/"+form['idR'])
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/admin/rutinas/list")
    else:
        flash("¡No se ha podido eliminar!", category='error')
        return redirect("/admin/rutinas/list")