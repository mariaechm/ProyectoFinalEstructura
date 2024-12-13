from flask import request
from flask import render_template
from flask import Blueprint,flash,redirect

import requests
import json


router_perfil = Blueprint('router_perfil',__name__)

@router_perfil.route('/admin/perfil')
def list_perfil():
    r = requests.get('http://localhost:8080/api/perfil/list')
    print(r.json())
    perfiles = r.json()["data"]
    i = 1
    for perfil in perfiles:
        perfil["numero"] = i
        i += 1
    return render_template('fragmento/perfil/lista_perfiles.html', perfiles=perfiles)

@router_perfil.route('/admin/perfil/save', methods=['POST'])
def crear_perfil():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data1 = {
        "peso": form["peso"], 
        "altura": form["altura"], 
        "imagen": form["imagen"],
        "objetivoCliente": form["objetivoCliente"]
    }
    r = requests.post('http://localhost:8080/api/perfil/save', json=data1)
    print("Datos enviados", data1)
    if r.status_code == 200:
        flash('Perfil creado correctamente', 'success')
    else:
        flash('Error al crear el perfil', 'danger')
    return redirect('/admin/perfil')

@router_perfil.route('/admin/perfil/registro')
def registro_perfil():
    return render_template('fragmento/perfil/guardar_perfil.html')

@router_perfil.route('/admin/perfil/update', methods=['POST'])
def update_perfil():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data1 = {
        "peso": form["peso"], 
        "altura": form["altura"], 
        "imagen": form["imagen"],
        "objetivoCliente": form["objetivoCliente"]
    }
    r = requests.post('http://localhost:8080/api/perfil/update', data=json.dumps(data1), headers=headers)
    print("Datos actualizados", data1)
    if r.status_code == 200:
        flash('Perfil actualizado correctamente', 'success')
        return redirect('/admin/perfil')
    else:
        flash('Error al actualizar el perfil', 'danger')
        return redirect('/admin/perfil')

@router_perfil.route('/admin/perfil/edit/<id>')
def editar_perfil(id):
    r = requests.get('http://localhost:8080/api/perfil/list')
    data = r.json()
    r1 = requests.get(f'http://localhost:8080/api/perfil/get/{id}')
    data1 = r1.json()
    print("Datos de perfil", data1)
    if r.status_code == 200:
        return render_template('fragmento/perfil/editar_perfil.html', perfil=data1["data"], perfiles=data["data"])
    else:
        flash(data1["data"], category='Error')
    return render_template('fragmento/perfil/lista_perfiles.html')