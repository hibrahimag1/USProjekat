from flask import Flask, request, jsonify

app = Flask(__name__)
current_light = 0.0

@app.route('/api/light', methods=['POST'])
def post_light():
    global current_light
    data = request.get_json()
    if 'light' in data:
        current_light = data['light']
        return {'message': 'Light updated'}, 200
    return {'error': 'Bad data'}, 400

@app.route('/api/light', methods=['GET'])
def get_light():
    return jsonify({'light': current_light})

@app.get('/')
def index():
    return 'OK', 200
