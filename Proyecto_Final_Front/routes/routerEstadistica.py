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

from reportlab.pdfgen import canvas
from reportlab.lib.utils import ImageReader
import requests


router_estadistica = Blueprint('router_estadistica',__name__)


@router_estadistica.route('/estadistica')
def list_estadistica():
    r = requests.get('http://localhost:8080/api/estadistica/list')
    print(r.json())
    estadisticas = r.json()["data"]
    i = 1
    for estadistica in estadisticas:
        estadistica["numero"] = i
        i += 1
    return render_template('fragmento/estadistica/lista.html', estadisticas=estadisticas)


@router_estadistica.route('/estadistica/save', methods=['POST'])
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
    return redirect('/estadistica')


@router_estadistica.route('/estadistica/registro')
def registro_estadistica():

    return render_template('fragmento/estadistica/guardar.html')


@router_estadistica.route('/estadistica/update', methods=['POST'])
def update_estadistica():
    headers = {'Content-Type': 'application/json'}
    form = request.form
    data1 = { "id": form ["id"],"medidaEspalda": form["medidaEspalda"], "medidaPierna": form["medidaPierna"], "medidaBrazo": form["medidaBrazo"], "medidaCintura":form["medidaCintura"], "peso": form["peso"], "altura": form["altura"]}
    r = requests.post('http://localhost:8080/api/estadistica/update', data =json.dumps(data1), headers=headers)
    print("aaa",data1)
    if r.status_code == 200:
        flash('Estadistica actualizada correctamente', 'success')
        return redirect('/estadistica')
    else:
        flash('Error al actualizar la estadistica', 'danger')
        return redirect('/estadistica')


@router_estadistica.route('/estadistica/edit/<id>')
def editar_estadistica(id):
    r = requests.get('http://localhost:8081/api/estadistica/list')
    data = r.json()
    r1 = requests.get('http://localhost:8081/api/estadistica/get/'+id)
    data1= r1.json()
    print ("aaaa",data1)
    if (r.status_code == 200):

        return render_template('fragmento/estadistica/editar.html', estadistica=data1["data"], estadisticas=data["data"])
    else:

        flash(data1["data"],category= 'Error')

    return render_template('fragmento/estadistica/lista.html') 







@router_estadistica.route('/estadistica/pdf/<int:id>', methods=['GET'])
def generar_pdf(id):
    # Obtén los datos de la estadística
    r = requests.get(f'http://localhost:8081/api/estadistica/get/{id}')
    if r.status_code != 200:
        flash('Error al obtener la estadística', 'danger')
        return redirect('/estadistica')
    
    estadistica = r.json()["data"]

    # Crear un gráfico con matplotlib
    fig, ax = plt.subplots(figsize=(6, 4))
    categorias = ['Espalda', 'Pierna', 'Brazo', 'Cintura', 'Peso', 'Altura']
    valores = [
        estadistica['medidaEspalda'],
        estadistica['medidaPierna'],
        estadistica['medidaBrazo'],
        estadistica['medidaCintura'],
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
    plt.close(fig)
    img_buffer.seek(0)
    image = ImageReader(img_buffer)

    # Crear el PDF
    buffer = io.BytesIO()
    pdf = canvas.Canvas(buffer, pagesize=letter)
    pdf.setTitle("Reporte de Estadística")

    # Título
    pdf.setFont("Helvetica-Bold", 18)
    pdf.drawString(100, 750, "Reporte de Estadística")

    # Datos de la estadística
    pdf.setFont("Helvetica", 12)
    pdf.drawString(100, 700, f"Medida Espalda: {estadistica['medidaEspalda']}")
    pdf.drawString(100, 680, f"Medida Pierna: {estadistica['medidaPierna']}")
    pdf.drawString(100, 660, f"Medida Brazo: {estadistica['medidaBrazo']}")
    pdf.drawString(100, 640, f"Medida Cintura: {estadistica['medidaCintura']}")
    pdf.drawString(100, 620, f"Peso: {estadistica['peso']}")
    pdf.drawString(100, 600, f"Altura: {estadistica['altura']}")

    # Insertar el gráfico en el PDF
    pdf.drawImage(image, 100, 400, width=400, height=200)

    # Finaliza y guarda el PDF
    pdf.save()
    buffer.seek(0)

    # Devuelve el PDF como respuesta
    return Response(buffer, mimetype='application/pdf',
                    headers={"Content-Disposition": "attachment;filename=estadistica.pdf"})
