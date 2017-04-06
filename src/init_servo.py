import RPi.GPIO as GPIO
import time

GPIO.setmode(GPIO.BOARD)
GPIO.setup(12, GPIO.OUT)

pwm = GPIO.PWM(12, 50)
pwm.start(9)

try:
    while True:
        pwm.ChangeDutyCycle(9)
        time.sleep(5)
        pwm.ChangeDutyCycle(8)
        time.sleep(5)

except KeyboardInterrupt:
    pwm.stop()
    GPIO.cleanup()
