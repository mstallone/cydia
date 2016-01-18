package com.matthewstallone.blindassist;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.os.Handler;
import android.os.Vibrator;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.UUID;

public class MainActivity extends ActionBarActivity implements SensorEventListener, TextToSpeech.OnInitListener {

    static final UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");
    static final boolean debug = false;

    private BluetoothAdapter bluetoothAdapter;
    private BluetoothSocket bluetoothSocket;
    private boolean isConnected = false, isModeOne = true, shouldPlaySound = true;
    String address = null;

    private OutputStream outputStream;
    private InputStream inputStream;

    private TextToSpeech tts;
    private Vibrator vibrator;
    private SensorManager sensorManager;
    private Sensor sensor;
    private long oldTime;
    private float angle;

    private TextView connectionStatus, outputText;
    private GraphView graph;
    private LineGraphSeries<DataPoint> series;
    private ToggleButton modeToggleButton;
    private Button readDataButton;

    private InputHandlerThread inputHandlerThread;
    private ToneThread toneThread;
    private int count = 0;

    private double frequency = 64;
    private int proximityData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        address = "20:15:04:30:59:18";

        connectionStatus = (TextView) findViewById(R.id.deviceStatus);
        outputText = (TextView) findViewById(R.id.outputID);
        graph = (GraphView) findViewById(R.id.graph);
        modeToggleButton = (ToggleButton) findViewById(R.id.modeToggleButton);
        readDataButton = (Button) findViewById(R.id.readData);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);

        tts = new TextToSpeech(this, this);
        tts.setOnUtteranceCompletedListener(new TextToSpeech.OnUtteranceCompletedListener() {
            @Override
            public void onUtteranceCompleted(String utteranceId) {
                shouldPlaySound = true;
                Log.e("g", "sfd");
            }
        });

        final HashMap<String, String> params = new HashMap<String, String>();
        params.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "stringId");

        outputText.setTextColor(Color.rgb(100,100,100));

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        makeConnection();

        if(isConnected) {
            try {
                outputStream = bluetoothSocket.getOutputStream();
                inputStream = bluetoothSocket.getInputStream();
                connectionStatus.setText("Device Status: Connected");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else connectionStatus.setText("Device Status: Disconnected");

        inputHandlerThread = new InputHandlerThread();
        toneThread = new ToneThread();

        final Handler timerHandler = new Handler();
        final Runnable run = new Runnable() {
            @Override
            public void run() {
                inputHandlerThread.run();
                timerHandler.postDelayed(this, 250);
            }
        };

        final Handler toneHandler = new Handler();
        final Runnable toneRunner = new Runnable() {
            @Override
            public void run() {
                frequency = calculateFrequencyFromProximitySensor(proximityData);
                if (!debug && !isModeOne && shouldPlaySound) {
                    Thread t = new Thread(toneThread);
                    t.start();
                }
                toneHandler.postDelayed(this, 100);
            }
        };
        toneRunner.run();

        /*series = new LineGraphSeries<DataPoint>(new DataPoint[]{
            new DataPoint(10, 0), new DataPoint(11, 1), new DataPoint(9, 2),new DataPoint(11, 3),
                new DataPoint(10, 4), new DataPoint(10, 5),new DataPoint(9, 6), new DataPoint(9, 7),
                new DataPoint(11, 8),new DataPoint(9, 9), new DataPoint(10, 10), new DataPoint(10, 11),

                new DataPoint(15, 12), new DataPoint(16, 13), new DataPoint(17, 14),new DataPoint(20, 15),
                new DataPoint(25, 16), new DataPoint(40, 17),new DataPoint(45, 18), new DataPoint(44, 19),
                new DataPoint(45, 20),new DataPoint(46, 21), new DataPoint(50, 22), new DataPoint(51, 23),

                new DataPoint(50, 24), new DataPoint(50, 25), new DataPoint(51, 26),new DataPoint(50, 27),
                new DataPoint(51, 28), new DataPoint(49, 29),new DataPoint(49, 30), new DataPoint(48, 31),
                new DataPoint(50, 32),new DataPoint(50, 33), new DataPoint(50, 34), new DataPoint(51, 35),

                new DataPoint(53, 36), new DataPoint(52, 37), new DataPoint(51, 38),new DataPoint(50, 39),
                new DataPoint(51, 40)


        });*/
        series = new LineGraphSeries<DataPoint>();
        series.setDataPointsRadius(1);
        graph.addSeries(series);
        graph.setVisibility(View.VISIBLE);

        if(isConnected) run.run();

        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(isConnected) outputStream.write("startServo".getBytes("UTF-8"));
                } catch (IOException e) {
                    e.printStackTrace();
                };
                tts.speak("", TextToSpeech.QUEUE_FLUSH, params);
            }
        });

        modeToggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = modeToggleButton.isChecked();
                if(!checked) {
                    tts.speak("Entering mode one", TextToSpeech.QUEUE_FLUSH, params);
                    graph.setVisibility(View.VISIBLE);
                    readDataButton.setVisibility(View.GONE);
                    try {
                        if(isConnected) outputStream.write("startServo".getBytes("UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                    isModeOne = true;
                }
                else {
                    tts.speak("Entering mode two", TextToSpeech.QUEUE_FLUSH, params);
                    graph.setVisibility(View.GONE);
                    readDataButton.setVisibility(View.VISIBLE);
                    try {
                        if(isConnected) outputStream.write("resetServo".getBytes("UTF-8"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    };
                    isModeOne = false;
                }
            }
        });

        readDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shouldPlaySound = false;
                tts.speak(String.valueOf(outputText.getText()), TextToSpeech.QUEUE_FLUSH, params);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        menu.add(0, 101, 0, "Retry connection");
        menu.add(0, 102, 0, "LED");
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == 101){
            makeConnection();
        }else if(id == 102) {
            //Toast.makeText(getApplicationContext(), "AWESOME", Toast.LENGTH_LONG).show();
            try {
                outputStream.write("LED".getBytes("UTF-8"));

            } catch (IOException e) {
                e.printStackTrace();
            };
        }
        return super.onOptionsItemSelected(item);
    }

    private void makeConnection() {
        if (bluetoothSocket == null) {
            try {
                BluetoothDevice bluetoothDevice = bluetoothAdapter.getRemoteDevice(address);
                bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuid);
                BluetoothAdapter.getDefaultAdapter().cancelDiscovery();
                bluetoothSocket.connect();
                Toast.makeText(getApplicationContext(), "Device connected", Toast.LENGTH_LONG).show();
                isConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        try {
            bluetoothSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    //--// TTS INIT
    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(getApplicationContext(), "Language not supported", Toast.LENGTH_LONG).show();
                Log.e("TTS", "Language is not supported");
            }
        } else {
            Toast.makeText(this, "TTS Initilization Failed", Toast.LENGTH_LONG).show();
            Log.e("TTS", "Initilization Failed");
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(isModeOne) {
            Sensor mySensor = event.sensor;

            if (mySensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                long currentTime = 0;

                angle = event.values[1] * 9 + 90;

                boolean changeDirection = false;
                if (angle < 20) {
                    vibrator.vibrate(300);
                    changeDirection = true;
                    currentTime = System.currentTimeMillis();
                } else if (angle > 150) {
                    vibrator.vibrate(300);
                    changeDirection = true;
                    currentTime = System.currentTimeMillis();
                }
                if (!debug) if (changeDirection && currentTime - oldTime > 400)
                    tts.speak("Change Direction", TextToSpeech.QUEUE_FLUSH, null);
                oldTime = currentTime;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class InputHandlerThread extends Thread {
        public void run() {
            if (isConnected) {
                byte[] buffer = new byte[1024];
                int start = 0, end = 0;

                try {
                    inputStream.read(buffer);
                    for (int i = 0; i < buffer.length; i++) {
                        if (buffer[i] == "#".getBytes()[0]) {
                            if (start == 0) start = i;
                            else {
                                end = i;
                                break;
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                start += 3;
                String in = "";

                for (int i = start; i < end; i++) in += String.valueOf((char) ((int) buffer[i]));

                if (in.length() > 0) {
                    proximityData = Integer.parseInt(in);
                    Log.e("LOOK", in);
                    double distance = calculateDistanceFromProximitySensor(proximityData);
                    outputText.setText(distance == 100 ? "> 100 inches" : (double) ((int) (distance * 10)) / 10.0 + " inches");

                    if(distance < 90) series.appendData(new DataPoint(count, 100-distance), true, ++count);
                }
            }
        }

    }

    private double calculateDistanceFromProximitySensor(int data) {
        double distance = Math.pow(data/1064.9, 1/-1.1126);
        return distance > 100 ? 100 : distance;
    }

    private class ToneThread implements Runnable {
        public void run() {
            double duration = 0.100;
            double freqOfTone = frequency;
            int sampleRate = 8000;

            double dnumSamples = duration * sampleRate;
            dnumSamples = Math.ceil(dnumSamples);
            int numSamples = (int) dnumSamples;
            double sample[] = new double[numSamples];
            byte generatedSnd[] = new byte[2 * numSamples];

            for (int i = 0; i < numSamples; ++i)
                sample[i] = Math.sin(freqOfTone * 2 * Math.PI * i / (sampleRate));

            int idx = 0, i = 0, x = 0, ramp = numSamples / 20;


            for (i = 0; i < ramp; ++i) {
                double dVal = sample[i];
                final short val = (short) ((dVal * 32767 * i / ramp));
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }


            for (i = i; i < numSamples - ramp; ++i) {
                double dVal = sample[i];
                final short val = (short) ((dVal * 32767));
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

            for (i = i; i < numSamples; ++i) {
                double dVal = sample[i];
                final short val = (short) ((dVal * 32767 * (numSamples - i) / ramp));
                generatedSnd[idx++] = (byte) (val & 0x00ff);
                generatedSnd[idx++] = (byte) ((val & 0xff00) >>> 8);
            }

            AudioTrack audioTrack = null;
            try {
                audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, (int) numSamples * 2, AudioTrack.MODE_STATIC);
                audioTrack.write(generatedSnd, 0, generatedSnd.length);
                audioTrack.play();
            } catch (Exception e) {
            }

            do {
                if (audioTrack != null) x = audioTrack.getPlaybackHeadPosition();
                else x = numSamples;
            } while (x < numSamples);

            if (audioTrack != null) audioTrack.release();
            Thread.currentThread().isInterrupted();
        }
    }

    private double calculateFrequencyFromProximitySensor(int data) {
        //return 130.31 * Math.pow(Math.E, 0.0046 * data);
        return 3.2758*data + 127.53;
    }
}