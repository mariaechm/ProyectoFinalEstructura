from app import create_app
from flask import render_template
from jinja2 import TemplateNotFound

app = create_app()

@app.errorhandler(TemplateNotFound)
def template_not_found(error):
    return render_template('template_not_found.html', error=error), 404

@app.errorhandler(404)
def page_not_found(error):
    return render_template('404.html'), 404

if(__name__ == '__main__'):
    app.run(debug=True,host='0.0.0.0')