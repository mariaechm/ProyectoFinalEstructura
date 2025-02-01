from flask import session, redirect, flash, request, url_for, render_template
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

def role_allowed(_role, roles):
    for role in roles:
        if role == _role:
            return True
        
    return False

def login_required(roles=None):
    def log_in_req(funct):
        def getHeaders(*args,**kwargs):
            if not isTokenValid():
                return redirect(url_for('router.login',next=request.url))
            headers = {'Authorization' : f'Bearer {session['token']}'}
            usr = requests.get(f'{SW_URL}/auth/usr/info', headers=headers).json()['data']
            c_role = usr['persona']['rol']
            kwargs['headers'] = headers
            kwargs['usr'] = usr

            if roles:
                if role_allowed(c_role,roles):
                    return funct(*args,**kwargs)
                else: 
                    return render_template('404.html'), 404
            else:   
                return funct(*args,**kwargs)
            

        getHeaders.__name__ = funct.__name__
        return getHeaders
    return log_in_req

def login_path(funct):
    def login():
        if isTokenValid(in_login=True):
            return redirect('/')
        return funct()
    login.__name__ = funct.__name__
    return login
        
def logout():
    if 'token' in session:
        session.pop('token')
        