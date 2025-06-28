'''
pico_main.py je file koji se nalazi na pico kontroleru i pokreće se pri napajanju. Zadužen za čitanje senzorskih podataka i objavljivanja na server.
'''
import network
import urequests
from machine import Pin, ADC, Timer
from time import sleep


# Wi-Fi credentials
SSID = 'WIFI_NAME'
PASSWORD = 'WIFI_PASS'

# Connect to Wi-Fi
wlan = network.WLAN(network.STA_IF)
wlan.active(True)
wlan.connect(SSID, PASSWORD)
while not wlan.isconnected():
    pass
print("Connected:", wlan.ifconfig())

light_sensor = ADC(Pin(26))
soil_sensor = ADC(Pin(27))

# Server endpoint 
POST_URL = "https://usprojekat.onrender.com/api/data"

def get_light():
    return (light_sensor.read_u16() / 65535) * 100

def get_soil_humidity():
    return (soil_sensor.read_u16() / 65535) * 100

def get_and_post_values(timer=None):
    light = get_light()
    humidity = get_soil_humidity()
    print(f"Light: {light:.1f}%  Humidity: {humidity:.1f}%", end=" ")
    
    try:
        response = urequests.post(POST_URL, json={"light": light, "humidity": humidity})
        print("Posted:", response.status_code)
        response.close()
    except Exception as e:
        print("POST error:", e)

timer = Timer()
timer.init(period=10000, mode=Timer.PERIODIC, callback=get_and_post_values)

while True:
    sleep(0.1)
