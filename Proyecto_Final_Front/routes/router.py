from flask import render_template, redirect, request, flash, Blueprint
import requests
import json
router = Blueprint('router',__name__)

@router.route('/admin')
def home():
    return render_template('blank.html')

@router.route('/not_found')
def not_found():
    return render_template('404.html')

