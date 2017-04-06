#!/usr/bin/python
# Import required libraries
import sys
import threading
import time
import socket
import RPi.GPIO as GPIO
 
# Function to step a full cycle
def step_cycle(direction, StepPins, cycles):
    StepDir = direction

    StepCounter = 0
    WaitTime = 2/float(1000)
    Seq = [[1,0,0,1],
           [1,0,0,0],
           [1,1,0,0],
           [0,1,0,0],
           [0,1,1,0],
           [0,0,1,0],
           [0,0,1,1],
           [0,0,0,1]]

    StepCount = len(Seq)

    for x in range(cycles):
        for x in range(4):
            # Loop that raises/lowers claw
            for pin in range(0,4):
                xpin=StepPins[pin]# Get GPIO
                if Seq[StepCounter][pin]!=0:
                    GPIO.output(xpin, True)
                else:
                    GPIO.output(xpin, False)
             
            StepCounter += StepDir
         
            # If we reach the end of the sequence
            # start again
            if (StepCounter>=StepCount):
                StepCounter = 0
            if (StepCounter<0):
                StepCounter = StepCount+StepDir
         
            # Wait before moving on
            time.sleep(WaitTime)

# Get this machine's ip addr
def get_ip_address():
    s = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
    return s.getsockname()[0]


# Move to first coordinate on chessboard
def move_to_first_coord(coord1):
    delta_x = 35+400+((coord1/8)*200)
    delta_y = (coord1%8)*200
    x_thread = threading.Thread(target=step_cycle, args=(-2, topStepPins, delta_x))
    y_thread = threading.Thread(target=step_cycle, args=(-2, bottomStepPins, delta_y))
    x_thread.start()
    y_thread.start()
    x_thread.join()
    y_thread.join()
    
# Move to second coordinate on chessboard
def move_to_second_coord(coord1, coord2):
    
    # Move back to idle position
    delta_x = 35+400+((coord1/8)*200)
    delta_y = (coord1%8)*200
    x_thread = threading.Thread(target=step_cycle, args=(2, topStepPins, delta_x))
    y_thread = threading.Thread(target=step_cycle, args=(2, bottomStepPins, delta_y))
    x_thread.start()
    y_thread.start()
    x_thread.join()
    y_thread.join()

    delta_x = 35+400+((coord2/8)*200)
    delta_y = (coord2%8)*200
    x_thread = threading.Thread(target=step_cycle, args=(-2, topStepPins, delta_x))
    y_thread = threading.Thread(target=step_cycle, args=(-2, bottomStepPins, delta_y))
    x_thread.start()
    y_thread.start()
    x_thread.join()
    y_thread.join()

# Return to idle from second coord
def return_to_idle(coord2):
    # Move back to idle position
    delta_x = 35+400+((coord2/8)*200)
    delta_y = (coord2%8)*200
    x_thread = threading.Thread(target=step_cycle, args=(2, topStepPins, delta_x))
    y_thread = threading.Thread(target=step_cycle, args=(2, bottomStepPins, delta_y))
    x_thread.start()
    y_thread.start()
    x_thread.join()
    y_thread.join()


# ------------- Start of main ----------------

# Initialize GPIO pins
GPIO.setmode(GPIO.BCM)
topStepPins = [17,22,23,24]
bottomStepPins = [21,20,26,19]

# Set all pins as output
print "Setup pins"
for pin in topStepPins:
    GPIO.setup(pin,GPIO.OUT)
    GPIO.output(pin, False)
for pin in bottomStepPins:
    GPIO.setup(pin,GPIO.OUT)
    GPIO.output(pin, False)

# Setup networking
host_ip = get_ip_address()
host_port = int(sys.argv[1])
server_address = ('192.168.43.169', 2016)
sock = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
sock.bind((host_ip, host_port))

# Main loop
while True:
    # Move to first coord
    data, addr = sock.recvfrom(1024)
    first_coord = int(data)
    move_to_first_coord(first_coord)
    sock.sendto('done'.encode('utf-8'), server_address)

    # Move to second coord
    data, addr = sock.recvfrom(1024)
    sec_coord = int(data)
    move_to_second_coord(first_coord, sec_coord)
    sock.sendto('done'.encode('utf-8'), server_address)

    # Return to idle
    data, addr = sock.recvfrom(1024)
    return_to_idle(sec_coord)
    sock.sendto('done'.encode('utf-8'), server_address)
