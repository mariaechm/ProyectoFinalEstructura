from .router import *
from .utils.decorator import *
import os
from werkzeug.utils import secure_filename

P_URL = f'{BASE_URL}/persona' 

@router.route('/users/list')
@login_required(roles=['ADMINISTRADOR'])
def persona_list(headers,usr):
    personas = requests.get(f'{P_URL}/list',headers=headers).json()['data']
    cuentas = requests.get(f'{BASE_URL}/cuenta/list',headers=headers).json()['data']
    for i in range(0,len(personas)):
        personas[i]['numero'] = i + 1
        personas[i]['correo'] = cuentas[i]['correoElectronico']

    return render_template('fragmento/users_view/list.html', personas=personas, user=usr)

@router.route('/register/user')
@login_required()
def register_user(headers,usr):
    e = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    return render_template('fragmento/users_view/register.html',e=e, user=usr)

@router.route('/register/user/send',methods=['POST'])
@login_required()
def register_user_send(headers,usr):
    data = request.form.to_dict()   
    response = requests.post(f'{BASE_URL}/auth/register',headers=headers,json=data)
    msg = [response.json()['status'], response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}', category=msg[0])
    return redirect('/users/list')

@router.route('/user/update/<int:id>')
@login_required()
def persona_update(id,headers,usr):
    persona = requests.get(f'{P_URL}/get/{id}',headers=headers).json()['data']
    e = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    return render_template('fragmento/users_view/update.html',persona=persona,e=e, user=usr)

@router.route('/persona/delete/<int:id>')
def persona_delete(id):
    return render_template('fragmento/persona/delete.html',id=id)

@router.route('/persona/delete/send',methods=['POST'])
def persona_delete_send():
    response = requests.delete(f'{P_URL}/delete/{request.form.to_dict()['id']}')
    msg = [response.json()['status'],response.json()['info']]
    flash(f'{msg[0]}: {msg[1]}')
    return redirect('/persona/list')

@router.route('/view_user/<int:id>')
@login_required(roles=['ADMINISTRADOR'])
def persona_view(headers,usr,id):
    response = requests.get(f'{P_URL}/get/{id}',headers=headers)
    persona = response.json()['data']
    cuenta = requests.get(f'{BASE_URL}/cuenta/search/personaId/{persona['id']}',headers=headers).json()['data'][0]
    perfil = requests.get(f'{BASE_URL}/perfil/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    estadistica = requests.get(f'{BASE_URL}/estadistica/get/{cuenta['perfilId']}',headers=headers).json()['data']
    enums = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    return render_template('fragmento/users_view/user/view_user.html',user=usr,persona=persona,cuenta=cuenta,perfil=perfil,estadistica=estadistica, enums=enums, my_profile=False)

UPLOAD_FOLDER = 'static/img/user_profile/'
ALLOWED_EXTENSIONS = {'png', 'jpg', 'jpeg', 'gif', 'webp'}

def allowed_file(filename):
    return '.' in filename and filename.rsplit('.', 1)[1].lower() in ALLOWED_EXTENSIONS

@router.route('/user/update/send',methods=['POST'])
@login_required()
def perfil_update_send(headers,usr):
    data = request.form.to_dict()
    if 'image' in request.files:
        file = request.files['image']

        if file.name == '':
            flash('No se ha seleccionado un archivo')
            return redirect('/users/list')
    
        files = {'image' : (file.filename, file.stream, file.mimetype)}

        if file and allowed_file(file.filename):
            filename = secure_filename(file.filename)
            file.save(os.path.join(UPLOAD_FOLDER, filename)) 
            data['imagen'] = file.filename

    if not 'imagen' in data:
        data['imagen'] = data['current-image']
                                                   
    response = requests.post(f'{BASE_URL}/perfil/update',headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}')
    return redirect('/users/list' if (not 'my-profile' in data) else '/my_profile')

@router.route('/change_password',methods=['POST'])
@login_required()
def change_password(headers,usr):
    data = request.form.to_dict()
    response = requests.post(f'{BASE_URL}/auth/change/password',headers=headers,json=data)
    print(response)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado la contraseña" if ok else "no se ha podido actualizar la contraseña"}')
    return redirect('/users/list')

@router.route('/user/update/persona',methods=['POST'])
@login_required()
def update_persona_send(headers,usr):
    data = request.form.to_dict()
    response = requests.post(f'{P_URL}/update',headers=headers,json=data)
    ok = response.status_code == 200
    flash(f'{"Éxito" if ok else "Error"}: {"Se ha actualizado el registro" if ok else "no se ha podido actualizar el registro"}')
    return redirect('/users/list')

@router.route('/my_profile')
@login_required()
def my_profile(headers,usr):
    response = requests.get(f'{P_URL}/get/{usr["persona"]["id"]}',headers=headers)
    persona = response.json()['data']
    cuenta = requests.get(f'{BASE_URL}/cuenta/search/personaId/{persona["id"]}',headers=headers).json()['data'][0]
    perfil = requests.get(f'{BASE_URL}/perfil/get/{cuenta["perfilId"]}',headers=headers).json()['data']
    estadistica = requests.get(f'{BASE_URL}/estadistica/get/{cuenta['perfilId']}',headers=headers).json()['data']
    enums = requests.get(f'{P_URL}/enumerations',headers=headers).json()['data']
    return render_template('fragmento/users_view/user/view_user.html',user=usr,persona=persona,cuenta=cuenta,perfil=perfil,estadistica=estadistica, enums=enums, my_profile=True)
