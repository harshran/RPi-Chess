#!/usr/bin/python
# Import required libraries
import sys
import time
import RPi.GPIO as GPIO
 
# Function to step a full cycle
def step_cycle(direction, StepPins):
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
 
# Raise claw
for x in range(500):
    step_cycle(-2, bottomStepPins)
