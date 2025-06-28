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

# Light sensor setup
light_sensor = ADC(Pin(26))
soil_sensor = ADC(Pin(27))

# Server endpoint (adjust this to your server URL)
POST_URL = "https://usprojekat.onrender.com/api/data"

def get_light():
    return (light_sensor.read_u16() / 65535) * 100

def get_soil_humidity():
    return (soil_sensor.read_u16() / 65535) * 100

def get_and_post_values(timer=None):
    light = get_light()
    humidity = get_soil_humidity()
    print(f"Light: {light:.1f}%  Humidity: {humidity:.1f}%")
    
    try:
        response = urequests.post(POST_URL, json={"light": light, "humidity": humidity})
        print("Posted:", response.status_code)
        response.close()
        pass
    except Exception as e:
        print("POST error:", e)

timer = Timer()
timer.init(period=10000, mode=Timer.PERIODIC, callback=get_and_post_values)

# Main loop
while True:
    sleep(0.1)
