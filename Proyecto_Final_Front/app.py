from flask import Flask

def create_app():
    app = Flask(__name__,instance_relative_config=False)
    app.secret_key = 'sistema_gestion_usuarios_rutinas_gimnasio'
    with app.app_context():
        from routes.router import router
        from routes.router_ejercicio import router_ejercicio
        from routes.router_rutina import router_rutina
        from routes.routerEstadistica import router_estadistica
        from routes.routerEstadistica import router_estadistica
        from routes.router_persona import router
        from routes.router_suscripcion import router
        
        app.register_blueprint(router)
        app.register_blueprint(router_ejercicio)
        app.register_blueprint(router_rutina)         
        app.register_blueprint(router_estadistica)       

    return app
