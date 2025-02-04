from .router import *
from .utils.decorator import *
from flask import request

router_rutina = Blueprint('router_rutina',__name__)

@router_rutina.route('/rutinas/list') 
@login_required()
def list_rutina(headers,usr):
    r =requests.get("http://localhost:8080/api/rutinas/all",headers=headers)
    print(r.json())
    rutinas = r.json()["data"]
    i = 1
    for rutina in rutinas:
        rutina['numero'] = i
        i += 1
    if usr["persona"]["rol"] == "ADMINISTRADOR":
        return render_template('fragmento/rutinas/lista.html',user=usr,rutinas=rutinas)
    return render_template('fragmento/rutinas/listaUser.html', user=usr,rutinas=rutinas)


@router_rutina.route('/rutinas/register')
@login_required()
def view_register_rutina(headers,usr):
    r = requests.get("http://localhost:8080/api/rutinas/objetivoRutina", headers=headers)
    print(r.json())
    data = r.json()["data"]
    rutinas = requests.get("http://localhost:8080/api/rutinas/all", headers=headers).json()["data"]
    ejercicios = requests.get("http://localhost:8080/api/ejercicios/all", headers=headers).json()["data"]
    return render_template('fragmento/rutinas/registro.html', list = data, rutinas=rutinas, ejercicios=ejercicios, user=usr)


@router_rutina.route('/rutinas/save', methods=['POST'])
@login_required()
def save_rutina(headers, usr):
    headers["Content-Type"]="application/json"
    form = request.form

    dataF = {
        "nombreRutina": form['nombreR'],
        "descripcion": form['descrip'],
        "nroEjercicios": form['nroE'],
        "idEjercicio": request.form.getlist("seleccionEjercicios"),
        "objetivoRutina": form['objR'],
    }

    print("***********************************************")
    print(request.form.getlist('hola'))
    r = requests.post("http://localhost:8080/api/rutinas/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    print("***SELECCION EJERCICIOS***")
    print(request.form.getlist('seleccionEjercicios'))
    dat = r.json()
    if r.status_code == 201:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/rutinas/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/rutinas/list")
    

@router_rutina.route('/rutinas/edit/<id>')
@login_required()
def view_edit_rutina(id, headers, usr):
    r = requests.get("http://localhost:8080/api/rutinas/objetivoRutina", headers=headers)
    print(r.json())
    data = r.json()["data"]
    r1 = requests.get("http://localhost:8080/api/rutinas/get/"+id, headers=headers)
    print(r1.json())
    data1 = r1.json()["data"]
    rutinas = requests.get("http://localhost:8080/api/rutinas/all", headers=headers).json()["data"]
    ejercicios = requests.get("http://localhost:8080/api/ejercicios/all", headers=headers).json()["data"]
    if(r1.status_code == 200):
        return render_template('fragmento/rutinas/editar.html', list = data, rutina=data1, rutinas=rutinas, ejercicios=ejercicios, user=usr)
    else:
        flash(r1.json()['info'], category='error')
        return redirect("/rutinas/list")


@router_rutina.route('/rutinas/update', methods=['POST'])
@login_required()
def update_rutina(headers, usr):
    headers["Content-Type"]="application/json"
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
        return redirect("/rutinas/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/rutinas/list")
    

@router_rutina.route('/rutinas/delete/<id>')
@login_required()
def view_delete_rutina(id, headers, usr):
    return render_template('fragmento/rutinas/eliminar.html', id=id, user=usr)


@router_rutina.route('/rutinas/remove', methods=['POST'])
@login_required()
def delete_rutina(headers, usr):
    form = request.form
    r = requests.post("http://localhost:8080/api/rutinas/delete/"+form['idR'], headers=headers)
    print(r.json())
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/rutinas/list")
    else:
        flash(r.json()['info'], category='error')
        return redirect("/rutinas/list")
    

@router_rutina.route('/rutinas/sort/<atributo>/<orden>/<metodoOrden>')
@login_required()
def order_rutina(atributo, orden, metodoOrden, headers, usr):
    r = requests.get('http://localhost:8080/api/rutinas/sort/'+ atributo + '/' + orden + '/' + metodoOrden, headers=headers)
    print(r.json())
    rutinas = r.json()["data"]
    i = 1
    for rutina in rutinas:
        rutina['numero'] = i
        i+=1
    return render_template('fragmento/rutinas/lista.html', rutinas = rutinas, user=usr)


@router_rutina.route('/rutinas/search/<atributo>/<valor>')
@login_required()
def search_rutina(atributo, valor, headers, usr):
    r = requests.get('http://localhost:8080/api/rutinas/search/'+ atributo + '/' + valor, headers=headers)
    print(r.json())
    rutinas = r.json()["data"]
    i = 1
    for rutina in rutinas:
        rutina['numero'] = i
        i+=1
    return render_template('fragmento/rutinas/lista.html', rutinas = rutinas, user=usr)


@router_rutina.route('/rutinas/informacion/<id>')
@login_required()
def info_rutina(id, headers, usr):
    r = requests.get("http://localhost:8080/api/rutinas/get/"+id, headers=headers)
    print(r.json())
    data = r.json()["data"]
    lista = []
    for i in data["idEjercicio"]:
        lista.append(requests.get("http://localhost:8080/api/ejercicios/get/"+str(i), headers=headers).json()["data"])
       
    return render_template('fragmento/rutinas/informacion.html', rutina = data, ejercicios=lista, user=usr)