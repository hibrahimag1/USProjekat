'''
main.py je file kojeg pokreće web service za deploy web stranice na usprojekat.onrender.com
'''

from flask import Flask, request, jsonify

app = Flask(__name__)
current_light = 0.0
current_humidity = 0.0

@app.route('/api/data', methods=['POST'])
def post_data():
    global current_light, current_humidity
    data = request.get_json()
    if 'light' in data and 'humidity' in data:
        current_light = data['light']
        current_humidity = data['humidity']
        return {'message': 'Sensor data updated'}, 200
    return {'error': 'Bad data'}, 400

@app.route('/api/data', methods=['GET'])
def get_data():
    return jsonify({
        'light': current_light,
        'humidity': current_humidity
    })

@app.get('/')
def index():
    return 'OK', 200
