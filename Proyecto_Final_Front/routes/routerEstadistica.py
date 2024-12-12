from flask import request
from flask import render_template
from flask import Blueprint,flash,redirect

import requests
import json


router_estadistica = Blueprint('router_estadistica',__name__)

@router_estadistica.route('/admin/estadistica')
def list_estadistica():
    r = requests.get('http://localhost:8080/api/estadistica/list')
    print(r.json())
    estadisticas = r.json()["data"]
    i = 1
    for estadistica in estadisticas:
        estadistica["numero"] = i
        i += 1
    return render_template('fragmento/estadistica/lista.html', estadisticas=estadisticas)

@router_estadistica.route('/admin/estadistica/save', methods=['POST'])
def crear_estadistica():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data1 = {"medidaEspalda": form["medidaEspalda"], "medidaPierna": form["medidaPierna"], "medidaBrazo": form["medidaBrazo"], "medidaCintura":form["medidaCintura"], "peso": form["peso"], "altura": form["altura"]}
    r = requests.post('http://localhost:8080/api/estadistica/save', json=data1)
    print("aaa",data1)
    if r.status_code == 200:
        flash('Estadistica creada correctamente', 'success')
    else:
        flash('Error al crear la estadistica', 'danger')
    return redirect('/admin/estadistica')

@router_estadistica.route('/admin/estadistica/registro')
def registro_estadistica():

    return render_template('fragmento/estadistica/guardar.html')

@router_estadistica.route('/admin/estadistica/update', methods=['POST'])
def update_estadistica():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data1 = { "id": form ["id"],"medidaEspalda": form["medidaEspalda"], "medidaPierna": form["medidaPierna"], "medidaBrazo": form["medidaBrazo"], "medidaCintura":form["medidaCintura"], "peso": form["peso"], "altura": form["altura"]}
    r = requests.post('http://localhost:8080/api/estadistica/update', data =json.dumps(data1), headers=headers)
    print("aaa",data1)
    if r.status_code == 200:
        flash('Estadistica actualizada correctamente', 'success')
        return redirect('/admin/estadistica')
    else:
        flash('Error al actualizar la estadistica', 'danger')
        return redirect('/admin/estadistica')

@router_estadistica.route('/admin/estadistica/edit/<id>')
def editar_estadistica(id):
    r = requests.get('http://localhost:8080/api/estadistica/list')
    data = r.json()
    r1 = requests.get('http://localhost:8080/api/estadistica/get/'+id)
    data1= r1.json()
    print ("aaaa",data1)
    if (r.status_code == 200):
        return render_template('fragmento/estadistica/editar.html', estadistica=data1["data"], estadisticas=data["data"])
    else:
        flash(data1["data"],category= 'Error')
    return render_template('fragmento/estadistica/lista.html') 