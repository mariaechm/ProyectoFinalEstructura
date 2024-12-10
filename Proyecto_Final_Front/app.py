from flask import Flask

def create_app():
    app = Flask(__name__,instance_relative_config=False)
    app.secret_key = 'sistema_gestion_usuarios_rutinas_gimnasio'
    with app.app_context():
        from routes.router import router
        app.register_blueprint(router)
    return app