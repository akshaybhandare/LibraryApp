package com.example.libraryapp;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.io.IOException;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;



public class code_detect extends AppCompatActivity {

    String ServerURL;

    String TempName, TempUsn ;

    private Button scancard1,connectip1;
    private Button nexttoface1,goto1;
    SurfaceView cameraView;
    private TextView nametext1,usntext1,ipview1;
    CameraSource cameraSource;
    TextView preview1;
    final int RequestCameraPermissionID = 1001;
    String out;
    int count=1;



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
            break;
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_code_detect);

        scancard1 = (Button) findViewById(R.id.scancard);
        nexttoface1 = (Button) findViewById(R.id.nexttoface);

        cameraView = (SurfaceView) findViewById(R.id.surface_view);
        nametext1 = (TextView) findViewById(R.id.nametext);
        usntext1 = (TextView) findViewById(R.id.usntext);
        preview1 =  (TextView) findViewById(R.id.preview);
        ipview1 = (TextView) findViewById(R.id.ipview);
        goto1 = (Button) findViewById(R.id.gonext);


        //ip1.setText(message);

        ServerURL = "http://"+"13.126.46.142"+"/get_data.php";
        ipview1.setText(ServerURL);

        final TextRecognizer textRecognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        if (!textRecognizer.isOperational()) {
            Log.w("MainActivity", "Detector dependencies are not yet available");
        } else {

            cameraSource = new CameraSource.Builder(getApplicationContext(), textRecognizer)
                    .setFacing(CameraSource.CAMERA_FACING_BACK)
                    .setRequestedPreviewSize(1280, 1024)
                    .setRequestedFps(2.0f)
                    .setAutoFocusEnabled(true)
                    .build();
            cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
                @Override
                public void surfaceCreated(SurfaceHolder surfaceHolder) {

                    try {
                        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {

                            ActivityCompat.requestPermissions(code_detect.this,
                                    new String[]{Manifest.permission.CAMERA},
                                    RequestCameraPermissionID);
                            return;
                        }
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

                }

                @Override
                public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                    cameraSource.stop();
                }
            });

            textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                @Override
                public void release() {

                }

                @Override
                public void receiveDetections(Detector.Detections<TextBlock> detections) {

                    final SparseArray<TextBlock> items = detections.getDetectedItems();
                    if(items.size() != 0)
                    {
                        preview1.post(new Runnable() {
                            @Override
                            public void run() {
                                StringBuilder stringBuilder = new StringBuilder();
                                for(int i =0;i<items.size();++i)
                                {
                                    TextBlock item = items.valueAt(i);
                                    stringBuilder.append(item.getValue());
                                    stringBuilder.append("\n");
                                }
                                preview1.setText(stringBuilder.toString());


                            }
                        });
                    }
                }
            });


        }



        scancard1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textRecognizer.setProcessor(new Detector.Processor<TextBlock>() {
                    @Override
                    public void release() {

                    }

                    @Override
                    public void receiveDetections(Detector.Detections<TextBlock> detections) {

                        final SparseArray<TextBlock> items = detections.getDetectedItems();
                        if(items.size() != 0)
                        {
                            preview1.post(new Runnable() {
                                @Override
                                public void run() {
                                    StringBuilder stringBuilder = new StringBuilder();
                                    for(int i =0;i<items.size();++i)
                                    {
                                        TextBlock item = items.valueAt(i);
                                        stringBuilder.append(item.getValue());
                                        stringBuilder.append("\n");
                                    }
                                    preview1.setText(stringBuilder.toString());
                                    out= stringBuilder.toString();
                                    count--;
                                    if(out!="" && count==0)
                                    {
                                        String u = out.substring(0,12);
                                       String n =out.substring(13,out.length());

                                        nametext1.setText(n);
                                        usntext1.setText(u);
                                    }

                                }
                            });
                        }
                    }
                });

            }
        });

        nexttoface1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData();

                InsertData(TempName, TempUsn);
            }



            private void GetData() {


                    TempName = nametext1.getText().toString();

                    TempUsn = usntext1.getText().toString();

            }


            private void InsertData(final String name, final String usn) {
                class SendPostReqAsyncTask extends AsyncTask<String, Void, String> {
                    @SuppressLint("WrongThread")
                    @Override
                    protected String doInBackground(String... params) {

                        String NameHolder = name ;
                        String UsnHolder = usn ;

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();

                        nameValuePairs.add(new BasicNameValuePair("name", NameHolder));
                        nameValuePairs.add(new BasicNameValuePair("usn", UsnHolder));

                        try {
                            HttpClient httpClient = new DefaultHttpClient();

                            HttpPost httpPost = new HttpPost(ServerURL);

                            httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                            HttpResponse httpResponse = httpClient.execute(httpPost);

                            HttpEntity httpEntity = httpResponse.getEntity();


                        } catch (ClientProtocolException e) {

                        } catch (IOException e) {

                        }


                        return "Data Inserted Successfully";
                    }

                    @Override
                    protected void onPostExecute(String result) {

                        super.onPostExecute(result);

                        Toast.makeText(code_detect.this, "Data Submit Successfully", Toast.LENGTH_LONG).show();

                    }
                }
                SendPostReqAsyncTask sendPostReqAsyncTask = new SendPostReqAsyncTask();

                sendPostReqAsyncTask.execute(name, usn);
            }
        });

        goto1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(code_detect.this, Fingureprint.class);
                startActivity(intent);
            }
        });



        }




}





