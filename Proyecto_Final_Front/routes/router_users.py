from .router import *
from flask import render_template, request, flash, redirect
from .utils.decorator import *
import os
from werkzeug.utils import secure_filename


UPLOAD_FOLDER = 'static/img/user_profile/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'webp'}

P_URL = f'{BASE_URL}/persona' 



@router.route('/users/client/list')
@login_required(roles=['ADMINISTRADOR'])
def users_client_list(headers,usr):
    personas = requests.get(f'{P_URL}/list',headers=headers).json()['data']
    cuentas = requests.get(f'{BASE_URL}/cuenta/list',headers=headers).json()['data']
    tabla = []
    for i in range(0,len(personas)):
        if personas[i]['rol'] == 'CLIENTE':
            personas[i]['numero'] = i + 1
            personas[i]['correo'] = cuentas[i]['correoElectronico']
            tabla.append(personas[i])
    personas = tabla
    return render_template('fragmento/users_view/list.html', personas=personas, user=usr, admin=False)


@router.route('/users/admin/list')
@login_required(roles=['ADMINISTRADOR'])
def admin_client_list(headers,usr):
    personas = requests.get(f'{P_URL}/list',headers=headers).json()['data']
    cuentas = requests.get(f'{BASE_URL}/cuenta/list',headers=headers).json()['data']
    tabla = []
    for i in range(0,len(personas)):
        if personas[i]['rol'] == 'ADMINISTRADOR':
            personas[i]['numero'] = i + 1
            personas[i]['correo'] = cuentas[i]['correoElectronico']
            tabla.append(personas[i])
    personas = tabla
    return render_template('fragmento/users_view/list.html', personas=personas, user=usr, admin=True)

@router.route('/register/user/<role>')
@login_required(roles=['ADMINISTRADOR'])
def register_user(headers,usr,role):
    e = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    role = 'ADMINISTRADOR' if role == 'admin' else 'CLIENTE'
    print(usr)
    return render_template('fragmento/users_view/register.html',e=e, user=usr, role=role)

@router.route('/register/user/send',methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def register_user_send(headers,usr):
    data = request.form.to_dict()   
    response = requests.post(f'{BASE_URL}/auth/register',headers=headers,json=data)
    msg = [response.json()['status'], response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}', category=msg[0])
    return redirect('/users/admin/list' if data['rol'] == 'ADMINISTRADOR' else '/users/client/list')

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

def delete_unreferenced_images(headers):
    personas = requests.get(f'{BASE_URL}/perfil/list', headers=headers).json()['data']
    referenced_images = {persona['imagen'] for persona in personas if 'imagen' in persona}

    referenced_images.add('user.png')

    stored_images = set(os.listdir(UPLOAD_FOLDER))

    unreferenced_images = stored_images - referenced_images

    for image in unreferenced_images:
        os.remove(os.path.join(UPLOAD_FOLDER, image))

    return f"Deleted {len(unreferenced_images)} unreferenced images."

@router.route('/user/update/send',methods=['POST'])
@login_required()
def perfil_update_send(headers,usr):
    data = request.form.to_dict()
    if 'image' in request.files:
        file = request.files['image']

        if file.name == '':
            flash('No se ha seleccionado un archivo')
            return redirect('/users/list')

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename)) 
            data['imagen'] = file.filename

    if not 'imagen' in data:
        data['imagen'] = data['current-image']
                                                   
    response = requests.post(f'{BASE_URL}/perfil/update',headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    print(delete_unreferenced_images(headers=headers))
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data['userId']}/{eval(data['admins'])}') 

@router.route('/change_password',methods=['POST'])
@login_required()
def change_password(headers,usr):
    data = request.form.to_dict()
    response = requests.post(f'{BASE_URL}/auth/change/password',headers=headers,json=data)
    ok = response.status_code == 200
    print(data)
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado la contraseña" if ok else "no se ha podido actualizar la contraseña"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data['id']}/{eval(data['admins'])}')

@router.route('/user/update/persona',methods=['POST'])
@login_required()
def update_persona_send(headers,usr):
    data = request.form.to_dict()
    response = requests.post(f'{P_URL}/update',headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}',category='success' if ok else 'error')
    return redirect('/my_profile' if (eval(data['my-profile'])) else f'/view_user/{data['userId']}/{eval(data['admins'])}')

@router.route('/view_user/<int:id>/<admins>')
@login_required(roles=['ADMINISTRADOR'])
def persona_view(headers,usr,id,admins:bool):
    persona = requests.get(f'{P_URL}/get/{id}',headers=headers).json()['data']
    cuenta = requests.get(f'{BASE_URL}/cuenta/search/personaId/{persona['id']}',headers=headers).json()['data'][0]
    perfil = requests.get(f'{BASE_URL}/perfil/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    estadistica = requests.get(f'{BASE_URL}/estadistica/get/{cuenta['perfilId']}',headers=headers).json()['data']
    suscripcion = requests.get(f'{BASE_URL}/suscripcion/get/{cuenta['personaId']}',headers=headers).json()['data']

    enums = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']

    full_user_info = {'persona': persona, 'cuenta': cuenta, 'perfil': perfil, 'estadistica': estadistica, 'my_profile': False, 'suscripcion': suscripcion, 'admins': eval(admins) }

    return render_template('fragmento/users_view/user/view_user.html', enums=enums, user=usr, full_user_info=full_user_info)

@router.route('/my_profile')
@login_required()
def my_profile(headers,usr):
    persona = requests.get(f'{P_URL}/get/{usr["persona"]["id"]}',headers=headers).json()['data']
    cuenta = requests.get(f'{BASE_URL}/cuenta/search/personaId/{persona["id"]}',headers=headers).json()['data'][0]
    perfil = requests.get(f'{BASE_URL}/perfil/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    estadistica = requests.get(f'{BASE_URL}/estadistica/get/{cuenta['perfilId']}',headers=headers).json()['data']
    suscripcion = requests.get(f'{BASE_URL}/suscripcion/get/{cuenta['personaId']}',headers=headers).json()['data']


    enums = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']

    full_user_info = {'persona': persona, 'cuenta': cuenta, 'perfil': perfil, 'estadistica': estadistica, 'suscripcion': suscripcion, 'my_profile': True }

    return render_template('fragmento/users_view/user/view_user.html',user=usr, full_user_info=full_user_info, enums=enums ,my_profile=True)

@router.route('/user/delete/<int:id>/<admins>')
@login_required(roles=['ADMINISTRADOR'])
def persona_delete(id,headers,usr,admins):
    return render_template('fragmento/users_view/delete.html', id=id, user=usr, admins=eval(admins))

@router.route('/user/delete/send',methods=['POST'])
@login_required(roles=['ADMINISTRADOR'])
def delete_user(headers,usr):
    data = request.form.to_dict()
    response = requests.delete(f'{BASE_URL}/auth/delete/{data['id']}',headers=headers)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha eliminado el registro" if ok else "no se ha podido eliminar el registro"}',category='success' if ok else 'error')
    return redirect('/users/admin/list' if eval(data['admins']) else '/users/client/list')
