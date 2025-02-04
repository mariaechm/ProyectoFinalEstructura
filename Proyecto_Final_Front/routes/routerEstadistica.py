from flask import request
from flask import render_template
from flask import Blueprint,flash,redirect
from flask import Response
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
import io
from reportlab.lib.pagesizes import letter
from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader
import requests
import matplotlib.pyplot as plt
import requests
from .utils.decorator import *
from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader
import requests
import json


router_estadistica = Blueprint('router_estadistica',__name__)


@router_estadistica.route('/estadistica')
@login_required()
def list_estadistica(headers,usr):
    r = requests.get('http://localhost:8080/api/estadistica/list', headers=headers)
    persona = requests.get('http://localhost:8080/api/persona/list/', headers=headers).json()['data']
    print(r.json())
    estadisticas = r.json()["data"]
    for i in range(0,len(persona)):
        if persona[i]['id'] == estadisticas[i]['id']: estadisticas[i]['propietario'] = persona[i]['nombre']
    return render_template('fragmento/estadistica/lista.html', estadisticas=estadisticas, user=usr)


@router_estadistica.route('/estadistica/save', methods=['POST'])
@login_required()
def crear_estadistica(headers,usr):
    headers["Content-Type"] = "application/json"
    form = request.form
    data1 = {"medidaEspalda": form["medidaEspalda"], "medidaPierna": form["medidaPierna"], "medidaBrazo": form["medidaBrazo"], "medidaCintura":form["medidaCintura"], "medidaPecho":form["medidaPecho"],"peso": form["peso"], "altura": form["altura"]}
    r = requests.post('http://localhost:8080/api/estadistica/save', json=data1, headers=headers)

    print("aaa",data1)
    if r.status_code == 200:
        flash('Estadistica creada correctamente', 'success')
    else:
        flash('Error al crear la estadistica', 'danger')
    return redirect('/estadistica')
 

@router_estadistica.route('/estadistica/registro')
@login_required()
def registro_estadistica(headers,usr):

    return render_template('fragmento/estadistica/guardar.html',user=usr)


@router_estadistica.route('/estadistica/update', methods=['POST'])
@login_required()
def update_estadistica(headers,usr):
    headers["Content-Type"] = "application/json"
    form = request.form
    data1 = { "id": form ["id"],"medidaEspalda": form["medidaEspalda"], "medidaPierna": form["medidaPierna"], "medidaBrazo": form["medidaBrazo"], "medidaCintura":form["medidaCintura"],"medidaPecho":form["medidaPecho"], "peso": form["peso"], "altura": form["altura"]}
    r = requests.post('http://localhost:8080/api/estadistica/update', data = json.dumps(data1), headers=headers)
    if r.status_code == 200:
        flash('Estadistica actualizada correctamente', 'success')
        return redirect('/estadistica')
    else:
        flash('Error al actualizar la estadistica', 'danger')
        return redirect('/estadistica')


@router_estadistica.route('/estadistica/edit/<id>')
@login_required()
def editar_estadistica(id,headers,usr):
  
    r1 = requests.get("http://localhost:8080/api/estadistica/get/"+ id, headers=headers)
    data1= r1.json()
    print ("aaaa",data1)
    if (r1.status_code== 200):
        return render_template('fragmento/estadistica/editar.html', estadistica=data1["data"],user=usr)
    else:
        flash(data1["data"],category= 'Error')
    return render_template('fragmento/estadistica/lista.html',user=usr)




@router_estadistica.route('/estadistica/pdf/<int:id>', methods=['GET'])
@login_required()
def generar_pdf(id, headers, usr):
    # Obtén los datos de la estadística
    r = requests.get(f'http://localhost:8080/api/estadistica/get/{id}', headers=headers)
    if r.status_code != 200:
        flash('Error al obtener la estadística', 'danger')
        return redirect('/estadistica')
    
    estadistica = r.json()["data"]

    # Crear un gráfico con matplotlib
    fig, ax = plt.subplots(figsize=(10, 5))
    categorias = ['Espalda', 'Pierna', 'Brazo', 'Cintura', 'Pecho', 'Peso', 'Altura']
    valores = [
        estadistica['medidaEspalda'],
        estadistica['medidaPierna'],
        estadistica['medidaBrazo'],
        estadistica['medidaCintura'],
        estadistica['medidaPecho'],
        estadistica['peso'],
        estadistica['altura']
    ]
    
    ax.bar(categorias, valores, color='skyblue')
    ax.set_title('Medidas de Estadística')
    ax.set_ylabel('Valores')
    ax.set_xlabel('Categorías')

    # Guardar el gráfico en memoria
    img_buffer = io.BytesIO()
    plt.savefig(img_buffer, format='png')
    img_buffer.seek(0)
    plt.close(fig)  # Cerrar la figura después de guardarla
    image = ImageReader(img_buffer)

    # Crear el PDF
    buffer = io.BytesIO()
    pdf = canvas.Canvas(buffer, pagesize=letter)
    pdf.setTitle("Reporte de Estadística")

    # Título
    pdf.setFont("Helvetica-Bold", 20)
    pdf.drawString(100, 750, "Reporte de Estadística")

    # Datos de la estadística
    
    pdf.setFont("Helvetica", 12)
    pdf.drawString(100, 700, f"Medida Espalda: {estadistica['medidaEspalda']} cm")
    pdf.drawString(100, 680, f"Medida Pierna: {estadistica['medidaPierna']} cm")
    pdf.drawString(100, 660, f"Medida Brazo: {estadistica['medidaBrazo']} cm")
    pdf.drawString(100, 640, f"Medida Cintura: {estadistica['medidaCintura']} cm")
    pdf.drawString(100, 620, f"Medida Pecho: {estadistica['medidaPecho']} cm")
    pdf.drawString(100, 600, f"Peso: {estadistica['peso']} kg")  
    pdf.drawString(100, 580, f"Altura: {estadistica['altura']}cm") 

    # Insertar el gráfico en el PDF
    pdf.drawImage(image, 100, 300, width=300, height=200)

    # Finaliza y guarda el PDF
    pdf.save()
    buffer.seek(0)

    # Devuelve el PDF como respuesta
    return Response(buffer, mimetype='application/pdf',
                    headers={"Content-Disposition": "attachment;filename=estadistica.pdf"})
