from .router import *
router_rutina = Blueprint('router_rutina',__name__)

@router_rutina.route('/admin/rutinas/list')
def list_rutina():
    r =requests.get('http://localhost:8080/api/rutinas/all')
    print(r.json())
    rutinas = r.json()["data"]
    i = 1
    for rutina in rutinas:
        rutina['numero'] = i
        i += 1
    return render_template('fragmento/rutinas/lista.html', rutinas = rutinas)