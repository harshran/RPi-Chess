import RPi.GPIO as GPIO
import time
import socket

UDP_IP = "192.168.43.78"
UDP_PORT = 5005
MESSAGE = 'Button pressed on the RPi!'
print "UDP target IP: ", UDP_IP
print "UDP target port: ", UDP_PORT
print "Message to send: ", MESSAGE

sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)

GPIO.setmode(GPIO.BCM)
GPIO.setup(18, GPIO.IN, pull_up_down = GPIO.PUD_UP)

while True:
    input_state = GPIO.input(18)
    if input_state == False:
        print('Button pressed.')
        sock.sendto(MESSAGE, (UDP_IP, UDP_PORT))
        time.sleep(0.2)
