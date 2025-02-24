from flask import render_template, redirect, request, flash, Blueprint
import requests
import json
from .utils.decorator import *

BASE_URL = 'http://localhost:8080/api'

router = Blueprint('router',__name__)

@router.route('/')
def index():
    return redirect('/inicio')

@router.route('/inicio')
@login_required()
def home(headers,usr):
    return render_template('inicio.html',user=usr)

@router.route('/not_found')
def not_found():
    return render_template('404.html')

# AUTH ROUTES ====================================================

@router.route('/login')
@login_path
def login():
    correo = request.args.get('correo')
    next = request.args.get('next')
    return render_template('signin.html',next=next or '', correo=correo or '')

@router.route('/login/send',methods=['POST'])
def login_send():
    response = requests.post(f'{BASE_URL}/auth/login',json=request.form.to_dict())
    ok = response.status_code == 200
    if not ok:
        flash('Credenciales inválidas',category='info')
        return redirect(url_for('router.login',correo=request.form.get('correoElectronico')))
    
    session['token'] = response.json()['data']
    next = request.form.to_dict()['next']
    next = next if next != '' else None
    return redirect(next or '/inicio')

@router.route('/logout')
def logout_site():
    logout()
    return redirect('/login')

@router.route('/forgot_password')
def forgot_password():
    return render_template('forgot_password.html')

