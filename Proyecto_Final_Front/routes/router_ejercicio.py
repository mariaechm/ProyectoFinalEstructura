from .router import *
from .utils.decorator import *

router_ejercicio = Blueprint('router_ejercicio',__name__)

@router_ejercicio.route('/ejercicios/list')
@login_required()
def list_ejercicio(headers,usr):
    r =requests.get("http://localhost:8080/api/ejercicios/all",headers=headers)
    print(r)
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i += 1
    if usr["persona"]["rol"] == "ADMINISTRADOR":
        return render_template('fragmento/ejercicios/lista.html',user=usr, ejercicios = ejercicios)
    return render_template('fragmento/ejercicios/listaUser.html',user=usr, ejercicios = ejercicios)


@router_ejercicio.route('/ejercicios/register')
@login_required(roles=['ADMINISTRADOR'])
def view_register_ejercicio(headers,usr):
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio", headers=headers)

    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/ejercicios/grupoMuscularObjetivo", headers=headers)
    print(r1.json())
    data1 = r1.json()["data"]
    return render_template('fragmento/ejercicios/registro.html', list = data, list1 = data1, user=usr)


@router_ejercicio.route('/ejercicios/save', methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def save_ejercicio(headers, usr):
    headers["Content-Type"]="application/json"
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
        return redirect("/ejercicios/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/ejercicios/list")
    

@router_ejercicio.route('/ejercicios/edit/<id>')
@login_required(roles=['ADMINISTRADOR'])
def view_edit_ejercicio(id, headers, usr):
    r = requests.get("http://localhost:8080/api/ejercicios/typeEjercicio", headers=headers)
    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/ejercicios/get/"+id, headers=headers)
    print(r1.json())
    data1 = r1.json()["data"]
    r2 = requests.get("http://localhost:8080/api/ejercicios/grupoMuscularObjetivo", headers=headers)
    print(r2.json())
    data2 = r2.json()["data"]
    if(r1.status_code == 200):
        return render_template('fragmento/ejercicios/editar.html', list = data, ejercicio = data1, list2 = data2, user=usr)
    else:
        flash(r1.json()['info'], category='error')
        return redirect("/ejercicios/list")


@router_ejercicio.route('/ejercicios/update', methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def update_ejercicio(headers, usr):
    headers["Content-Type"]="application/json"
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
        return redirect("/ejercicios/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/ejercicios/list")
    

@router_ejercicio.route('/ejercicios/delete/<id>')
@login_required(roles=['ADMINISTRADOR'])
def view_delete_ejercicio(id, headers, usr):
    return render_template('fragmento/ejercicios/eliminar.html', id=id , user=usr)


@router_ejercicio.route('/ejercicios/remove', methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def delete_ejercicio(headers, usr):
    form = request.form
    r = requests.post("http://localhost:8080/api/ejercicios/delete/"+form['idE'], headers=headers)
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/ejercicios/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/ejercicios/list")


@router_ejercicio.route('/ejercicios/sort/<atributo>/<orden>/<metodoOrden>')
@login_required()
def order_ejercicio(atributo, orden, metodoOrden, headers, usr):
    r = requests.get('http://localhost:8080/api/ejercicios/sort/'+ atributo + '/' + orden + '/' + metodoOrden, headers=headers)
    print(r.json())
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i+=1
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios, user=usr)


@router_ejercicio.route('/ejercicios/search/<atributo>/<valor>')
@login_required()
def search_ejercicio(atributo, valor, headers, usr):
    r = requests.get('http://localhost:8080/api/ejercicios/search/'+ atributo + '/' + valor, headers=headers)
    print(r.json())
    ejercicios = r.json()["data"]
    i = 1
    for ejercicio in ejercicios:
        ejercicio['numero'] = i
        i+=1
    return render_template('fragmento/ejercicios/lista.html', ejercicios = ejercicios, user=usr)


@router_ejercicio.route('/ejercicios/informacion/<id>')
@login_required()
def info_ejercicio(headers,usr,id):
    r = requests.get("http://localhost:8080/api/ejercicios/get/"+id, headers=headers)
    print(r.json())
    data = r.json()["data"]
    return render_template('fragmento/ejercicios/informacion.html', ejercicio = data, user=usr)