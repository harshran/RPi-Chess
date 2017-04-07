//Harshan Anton
//Android App for sending pictures to server on command
//07/04/17

package harry.syscchesscamera;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

import android.app.Activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;

import android.os.Bundle;
import android.os.Environment;
import android.text.InputType;
import android.util.Log;

import android.view.SurfaceHolder;
import android.view.SurfaceView;


import android.widget.EditText;
import android.widget.Toast;


public class MainActivity extends Activity implements SurfaceHolder.Callback {
    private static String TAG = "MainActivity";

    @SuppressWarnings("deprecation")
    Camera camera; // camera class variable
    SurfaceView camView; // drawing camera preview using this variable
    SurfaceHolder surfaceHolder; // variable to hold surface for surfaceView which means display
    boolean camCondition = false;  // conditional variable for camera preview checking and set to false
    private String IPaddress = "0";
    private String sendingPort = "0";
    private String receivingPort = "0";


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // getWindow() to get window and set it's pixel format which is UNKNOWN
        getWindow().setFormat(PixelFormat.UNKNOWN);
        // referring the id of surfaceView
        camView = (SurfaceView) findViewById(R.id.camerapreview);
        // getting access to the surface of surfaceView and return it to surfaceHolder
        surfaceHolder = camView.getHolder();
        // adding call back to this context means MainActivity
        surfaceHolder.addCallback(this);
        // to set surface type
        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_NORMAL);


        Log.d(TAG, "task is created");

        //prompt user for network information
        askIP();
        askSendingPort();
        askReceivingPort();

    }

    public void showToast(final String toast)
    {
        runOnUiThread(new Runnable() {
            public void run()
            {
                Toast.makeText(MainActivity.this, toast, Toast.LENGTH_LONG).show();
            }
        });
    }





    public void askIP(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter the IP address of host machine");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                IPaddress = input.getText().toString();
            }
        });

        builder.show();


    }

    public void askSendingPort(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter host machine port number to send images to");


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                sendingPort = input.getText().toString();
            }
        });

        builder.show();


    }

    public void askReceivingPort(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please enter port number to receive messages on");

        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);


        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                receivingPort = input.getText().toString();
                runUdpServer();
            }
        });

        builder.show();

    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){

        // stop the camera
        if (camCondition) {
            camera.stopPreview(); // stop preview using stopPreview() method
            camCondition = false; // setting camera condition to false means stop
        }
        // condition to check whether your device have camera or not
        if (camera != null) {

            try {
                Camera.Parameters parameters = camera.getParameters();
                parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
                parameters.setJpegQuality(100);
                camera.setParameters(parameters); // setting camera parameters
                camera.setPreviewDisplay(surfaceHolder); // setting preview of camera
                camera.startPreview();  // starting camera preview

                camCondition = true; // setting camera to true which means having camera
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        camera = Camera.open();   // opening camera
        camera.setDisplayOrientation(90);   // setting camera preview orientation
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        camera.stopPreview();  // stopping camera preview
        camera.release();       // releasing camera
        camera = null;          // setting camera to null when left
        camCondition = false;   // setting camera condition to false also when exit from application
    }

    private void runUdpServer() {
        new Thread(new Runnable() {
            public void run() {

                String receivedText;
                Log.d(TAG, "receiver is running");
                showToast("Receiver is running");
                Log.d(TAG,receivingPort);

                try {
                    int port = Integer.parseInt(receivingPort);

                    DatagramSocket dsocket = new DatagramSocket(port);
                    byte[] buffer = new byte[2048];
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                    while (true) {
                        dsocket.receive(packet);
                        receivedText = new String(buffer, 0, packet.getLength());
                        Log.d(TAG, receivedText);
                        showToast("Received message: " + receivedText);
                        if (receivedText.equals("take photo"))
                        {
                            takeAndSendPic();
                        }
                        packet.setLength(buffer.length);
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    public void takeAndSendPic() throws IOException {

        showToast("Taking photo");

        camera.takePicture(null, null, null, mPictureCallback);


        File f1 = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picToSend.jpg");
        Bitmap b1 = BitmapFactory.decodeStream(new FileInputStream(f1));
        ByteArrayOutputStream stream1 = new ByteArrayOutputStream();
        b1.compress(Bitmap.CompressFormat.PNG, 100, stream1);


        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picToSend.jpg");
        Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        b.compress(Bitmap.CompressFormat.PNG, 100, stream);


        byte[] byteArray = stream.toByteArray();
        stream.close();


        Socket sock = new Socket(IPaddress, Integer.parseInt(sendingPort));
        DataOutputStream dos = new DataOutputStream(sock.getOutputStream());
        dos.writeInt(byteArray.length);
        dos.write(byteArray);

        showToast("Photo Sent");
        Log.d(TAG, "sending picture");
    }

    @SuppressWarnings("deprecation")
    Camera.PictureCallback mPictureCallback = new Camera.PictureCallback() {
        @Override
        public void onPictureTaken(byte[] data, Camera c) {

            FileOutputStream outStream = null;
            try {

                // Directory and name of the photo
                File imageFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "picToSend.jpg");


                outStream = new FileOutputStream(imageFile);
                outStream.write(data); //save the camera data to image file
                outStream.close();

                showToast("Photo saved locally");

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    };

}

