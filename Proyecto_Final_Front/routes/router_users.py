from .router import *
from .utils.decorator import *

P_URL = f'{BASE_URL}/persona' 


@router.route('/admin/users/list')
@login_required
def persona_list(headers,usr):
    personas = requests.get(f'{P_URL}/list',headers=headers).json()['data']
    cuentas = requests.get(f'{BASE_URL}/cuenta/list',headers=headers).json()['data']
    for i in range(0,len(personas)):
        personas[i]['numero'] = i + 1
        personas[i]['correo'] = cuentas[i]['correoElectronico']
    return render_template('fragmento/users_view/list.html', personas=personas, user=usr)

@router.route('/register/user')
@login_required
def register_user(headers,usr):
    e = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    return render_template('fragmento/users_view/register.html',e=e, user=usr)

@router.route('/register/user/send',methods=['POST'])
def register_user_send():
    response = requests.post(f'{P_URL}/save',json=request.form.to_dict(),headers={'Content Type':'application/json'})
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/persona/list')

@router.route('/persona/update/<int:id>')
def persona_update(id):
    persona = requests.get(f'{P_URL}/get/{id}').json()['data']
    e = requests.get(f'{P_URL}/enumerations').json()['data']
    return render_template('fragmento/persona/update.html',persona=persona,e=e)

@router.route('/persona/update/send',methods=['POST'])
def persona_update_send():
    response = requests.post(f'{P_URL}/update',json=request.form.to_dict(),headers={'Content Type':'application/json'})
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/persona/list')

@router.route('/persona/delete/<int:id>')
def persona_delete(id):
    return render_template('fragmento/persona/delete.html',id=id)

@router.route('/persona/delete/send',methods=['POST'])
def persona_delete_send():
    response = requests.delete(f'{P_URL}/delete/{request.form.to_dict()['id']}')
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/persona/list')

