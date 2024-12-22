from flask import session, redirect, flash
import requests

SW_URL = 'http://localhost:8080/api'

def isTokenValid(in_login=None):
    if not 'token' in session:
        return False
    
    headers = {'Authorization' : f'Bearer {session['token']}'}
    response = requests.get(f'{SW_URL}/auth/usr/info', headers=headers)

    unauthorized = response.status_code == 401

    if unauthorized and not in_login:
        flash('Ha expirado la sesión',category='info')

    return not unauthorized


def login_required(funct):
    def getHeaders():
        if not isTokenValid():
            return redirect('/login')
        headers = {'Authorization' : f'Bearer {session['token']}'}
        usr = requests.get(f'{SW_URL}/auth/usr/info', headers=headers).json()['data']

        return funct(headers,usr)
    getHeaders.__name__ = funct.__name__
    return getHeaders

def login_path(funct):
    def login():
        if isTokenValid(in_login=True):
            return redirect('/admin')
        return funct()
    login.__name__ = funct.__name__
    return login
        
def logout():
    if 'token' in session:
        session.pop('token')
        