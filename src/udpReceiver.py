import socket
import struct
import sys
import os

HOST = sys.argv[1]
PORT = int(sys.argv[2])

s = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
s.bind((HOST, PORT))
s.listen(5)

dir = os.getcwd() + '/ReceivedImage.png'


print('SERVER STARTED RUNNING')

while True:
    client, address = s.accept()
    buf = b''
    while len(buf) < 4:
        buf += client.recv(4 - len(buf))
    size = struct.unpack('!i', buf)[0]
    with open(dir, 'wb') as f:
        while size > 0:
            data = client.recv(1024)
            f.write(data)
            size -= len(data)
    print('Image Saved')
    client.close()
