from .router import *
from .utils.decorator import *

SCP_URL = f'{BASE_URL}/suscripcion' 

#LIST
@router.route('/suscripcion/list')

def suscripcion_list(headers,users):
    suscripciones = requests.get(f'{SCP_URL}/list').json()['data']
    for i in range(0,len(suscripciones)):
        suscripciones[i]['numero'] = i + 1
    return render_template('fragmento/suscripcion/list.html', suscripciones=suscripciones)

#SAVE
@router.route('/suscripcion/save')
@login_required (roles=['ADMINISTRADOR'])
def suscripcion_save(headers):
    headers["Content-Type"] = "application/json"
    e = requests.get(f'{SCP_URL}/tipoSuscripcion').json()['data']
    #print(e)
    return render_template('fragmento/suscripcion/save.html',e=e)


@router.route('suscripcion/save/send',methods=['POST'])
def suscripcion_save_send():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "fechaInicio": form['nameFechaI'],
        #"fechaFinalizacion": form['nameFechaF'],
        "tipo": form['nameTipo'],
    }

    r = requests.post("http://localhost:8080/api/suscripcion/save", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 201:
        flash("¡Se ha guardado correctamente!", category='info')
        return redirect("/suscripcion/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/suscripcion/list")

#UPDATE
@router.route('/suscripcion/update/<int:id>')
@login_required (roles=['ADMINISTRADOR'])
def suscripcion_update(id):
    headers["Content-Type"] = "application/json"
    suscripcion = requests.get(f'{SCP_URL}/get/{id}').json()['data']
    e = requests.get(f'{SCP_URL}/tipoSuscripcion').json()['data']
    return render_template('fragmento/suscripcion/update.html',suscripcion=suscripcion,e=e)


@router.route('/suscripcion/update/send/', methods=['POST'])
def update_suscripcion_send():
    headers = {'Content-Type': 'application/json'}
    form = request.form

    dataF = {
        "id": form['idS'],
        "fechaInicio": form['nameFechaI'],
        #"fechaFinalizacion": form['nameFechaF'],
        "tipo": form['nameTipo'],
    }

    r = requests.post("http://localhost:8080/api/suscripcion/update", data=json.dumps(dataF), headers=headers)
    print(r.json())
    dat = r.json()
    if r.status_code == 200:
        flash("¡Se ha actualizado correctamente!", category='info')
        return redirect("/suscripcion/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/suscripcion/list")
    

#DELETE
@router.route('/suscripcion/delete/<int:id>')
@login_required (roles=['ADMINISTRADOR'])
def suscripcion_delete(id):
    return render_template('fragmento/suscripcion/delete.html',id=id)


@router.route('/suscripcion/delete', methods=['POST'])
def delete_suscripcion():
    form = request.form.to_dict()
    r = requests.delete("http://localhost:8080/api/suscripcion/delete/"+form['idS'])
    print(r)
    if r.status_code == 200:
        flash("¡Se ha eliminado correctamente!", category='info')
        return redirect("/suscripcion/list")
    else:
        flash("¡No se ha podido completar la acción!", category='error')
        return redirect("/suscripcion/list")

#ORDENACION

router.route('/suscripcion/sort/<atributo>/<orden>/<metodoOrden>')
def order_suscripcion(atributo, orden, metodoOrden):
    r = requests.get('http://localhost:8080/api/suscripcion/sort/'+ atributo + '/' + orden + '/' + metodoOrden)
    print(r.json())
    suscripciones = r.json()["data"]
    i = 1
    for suscripcion in suscripciones:
        suscripcion['numero'] = i
        i+=1
    return render_template('fragmento/suscripcion/list.html', suscripciones = suscripciones)


@router.route('/suscripcion/search/<atributo>/<valor>')
def search_suscripcion(atributo, valor):
    r = requests.get('http://localhost:8080/api/suscripcion/search/'+ atributo + '/' + valor)
    print(r.json())
    suscripciones = r.json()["data"]
    i = 1
    for suscripcion in suscripciones:
        suscripcion['numero'] = i
        i+=1
    return render_template('fragmento/sucripcion/lista.html', suscripciones = suscripciones)

@router.route('/suscripcion/delete/send',methods=['POST'])
def suscripcion_delete_send():
    response = requests.delete(f'{SCP_URL}/delete/{request.form.to_dict()["id"]}')
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/suscripcion/list')
