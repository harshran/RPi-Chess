#Harshan Anton - Positioning System Stepper Motor
import socket, sys, time
import RPi.GPIO as GPIO

#gpio pins for stepper motor is connected to
GPIO_INA = 5
GPIO_INB = 6
GPIO_INC = 12
GPIO_IND = 13

#how many steps equal one square on chess board
STEPS_PER_SQUARE = 1000

MOTOR_SPEED = 0.002

#sequence repeated every 4 steps.
#p of 0 deactivates all phases
def step_4 (p):
    if p==0:
            GPIO.output(GPIO_INA,0)
            GPIO.output(GPIO_INB,0)
            GPIO.output(GPIO_INC,0)
            GPIO.output(GPIO_IND,0)

    if p==1:
            GPIO.output(GPIO_INA,1)
            GPIO.output(GPIO_INB,1)
            GPIO.output(GPIO_INC,0)
            GPIO.output(GPIO_IND,0)
             
    if p==2:
            GPIO.output(GPIO_INA,0)
            GPIO.output(GPIO_INB,1)
            GPIO.output(GPIO_INC,1)
            GPIO.output(GPIO_IND,0)
             
    if p==3:          
            GPIO.output(GPIO_INA,0)
            GPIO.output(GPIO_INB,0)
            GPIO.output(GPIO_INC,1)
            GPIO.output(GPIO_IND,1)
    if p==4:
            GPIO.output(GPIO_INA,1)
            GPIO.output(GPIO_INB,0)
            GPIO.output(GPIO_INC,0)
            GPIO.output(GPIO_IND,1)

#input value is the number of steps, and positive and negative values indicate motor direction
#positive value is anticlockwise
            
def steps_4(value):
    global pas
    if(value<0):
        for i in range (0,abs(value)):
            step_4(pas)
            time.sleep(MOTOR_SPEED)
            pas+=1
            if(pas>=5):
               pas=1;
   
    else:
        for i in range (0,abs(value)):
            step_4(pas)
            time.sleep(MOTOR_SPEED)
            if(pas==1):
               pas=5;
            pas-=1
    step_4(0)        

 


if __name__ == "__main__":
	
	#argument is port number to listen on
	textport = sys.argv[1]
	port = int(textport)

        def get_ip_address():
        	s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        	return s.getsockname()[0]

        UDP_IP = get_ip_address()
        UDP_PORT = port

        sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
        sock.bind((UDP_IP, UDP_PORT))

        while True:
		print("Waiting for server")
	        data, addr = sock.recvfrom(1024) # buffer size is 1024 bytes
	     	
	        GPIO.setmode(GPIO.BCM)
	        GPIO.setwarnings(False)
		
		#setup pi pins as output pins
	        GPIO.setup(GPIO_INA, GPIO.OUT)
	        GPIO.setup(GPIO_INB, GPIO.OUT)
	        GPIO.setup(GPIO_INC, GPIO.OUT)
	        GPIO.setup(GPIO_IND, GPIO.OUT)
		     
	        step_4(0)
	        pas=1          
		steps_4(int(data)*STEPS_PER_SQUARE)

    
