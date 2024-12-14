from .router import *

SCP_URL = f'{BASE_URL}/suscripcion' 


@router.route('/suscripcion/list')
def suscripcion_list():
    suscripciones = requests.get(f'{SCP_URL}/list').json()['data']
    for i in range(0,len(suscripciones)):
        suscripciones[i]['numero'] = i + 1
    return render_template('fragmento/suscripcion/list.html', suscripciones=suscripciones)

@router.route('/suscripcion/save')
def suscripcion_save():
    e = requests.get(f'{SCP_URL}/enumerations').json()['data']
    return render_template('fragmento/suscripcion/save.html',e=e)

@router.route('/suscripcion/save/send',methods=['POST'])
def suscripcion_save_send():
    response = requests.post(f'{SCP_URL}/save',json=request.form.to_dict(),headers={'Content Type':'application/json'})
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/suscripcion/list')

@router.route('/suscripcion/update/<int:id>')
def suscripcion_update(id):
    suscripcion = requests.get(f'{SCP_URL}/get/{id}').json()['data']
    e = requests.get(f'{SCP_URL}/enumerations').json()['data']
    return render_template('fragmento/suscripcion/update.html',suscripcion=suscripcion,e=e)

@router.route('/suscripcion/update/send',methods=['POST'])
def suscripcion_update_send():
    response = requests.post(f'{SCP_URL}/update',json=request.form.to_dict(),headers={'Content Type':'application/json'})
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/suscripcion/list')

@router.route('/suscripcion/delete/<int:id>')
def suscripcion_delete(id):
    return render_template('fragmento/suscripcion/delete.html',id=id)

@router.route('/suscripcion/delete/send',methods=['POST'])
def suscripcion_delete_send():
    response = requests.delete(f'{SCP_URL}/delete/{request.form.to_dict()['id']}')
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/suscripcion/list')
