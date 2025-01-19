from .router import *

SCP_URL = f'{BASE_URL}/suscripcion' 

#LIST
@router.route('/admin/suscripcion/list')
def suscripcion_list():
    suscripciones = requests.get(f'{SCP_URL}/list').json()['data']
    for i in range(0,len(suscripciones)):
        suscripciones[i]['numero'] = i + 1
    return render_template('fragmento/suscripcion/list.html', suscripciones=suscripciones)

#SAVE
@router.route('/admin/suscripcion/save')
def suscripcion_save():
    e = requests.get(f'{SCP_URL}/tipoSuscripcion').json()['data']
    #print(e)
    return render_template('fragmento/suscripcion/save.html',e=e)


@router.route('/admin/suscripcion/save/send',methods=['POST'])
def suscripcion_save_send():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "fechaInicio": form['nameFechaI'],
        "fechaFinalizacion": form['nameFechaF'],
        "tipo": form['nameTipo'],
    }

    r = requests.post("http://localhost:8080/api/suscripcion/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/admin/suscripcion/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/admin/suscripcion/list")


#UPDATE
@router.route('/admin/suscripcion/update/<int:id>')
def suscripcion_update(id):
    suscripcion = requests.get(f'{SCP_URL}/get/{id}').json()['data']
    e = requests.get(f'{SCP_URL}/tipoSuscripcion').json()['data']
    return render_template('fragmento/suscripcion/update.html',suscripcion=suscripcion,e=e)


@router.route('/admin/suscripcion/update/send/', methods=['POST'])
def update_suscripcion_send():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "id": form['idS'],
        "fechaInicio": form['nameFechaI'],
        "fechaFinalizacion": form['nameFechaF'],
        "tipo": form['nameTipo'],
    }

    r = requests.post("http://localhost:8080/api/suscripcion/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/admin/suscripcion/list")
    else:
        flash(str(dat["data"]), category='error')
        return redirect("/admin/suscripcion/list")
    

#DELETE
@router.route('/admin/suscripcion/delete/<int:id>')
def suscripcion_delete(id):
    return render_template('fragmento/suscripcion/delete.html',id=id)

@router.route('/admin/suscripcion/delete', methods=['POST'])
def delete_suscripcion():
    form = request.form.to_dict()
    r = requests.delete("http://localhost:8080/api/suscripcion/delete/"+form['idS'])
    print(r)
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/admin/suscripcion/list")
    else:
        flash("¡No se ha podido eliminar!", category='error')
        return redirect("/admin/suscripcion/list")

#ORDENACION
router.route('/admin/suscricoion/sort/<atributo>/<orden>/<metodoOrden>')
def order_suscripcion(atributo, orden, metodoOrden):
    r = requests.get('http://localhost:8080/api/suscripcion/sort/'+ atributo + '/' + orden + '/' + metodoOrden)
    print(r.json())
    suscripciones = r.json()["data"]
    i = 1
    for suscripcion in suscripciones:
        suscripcion['numero'] = i
        i+=1
    return render_template('fragmento/suscripcion/lista.html', suscripciones = suscripciones)
@router.route('/admin/suscripcion/search/<atributo>/<valor>')
def search_suscripcion(atributo, valor):
    r = requests.get('http://localhost:8080/api/suscripcion/search/'+ atributo + '/' + valor)
    print(r.json())
    suscripciones = r.json()["data"]
    i = 1
    for suscripcion in suscripciones:
        suscripcion['numero'] = i
        i+=1
    return render_template('fragmento/suscripcion/lista.html', suscripciones = suscripciones)

