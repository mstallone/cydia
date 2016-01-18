package com.matthewstallone.thoughtcloud;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;
import com.github.nkzawa.emitter.Emitter;
import java.net.URISyntaxException;

public class MainActivity extends ActionBarActivity {

    private Button test;
    private EditText message;
    private RelativeLayout relativeLayout;
    private int width, height;
    private AlertDialog alertDialog;
    private SharedPreferences options;

    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://137.110.90.192:3000");
        } catch (URISyntaxException e) {}
    }

    private Emitter.Listener onNewMessage = new Emitter.Listener() {

        @Override
        public void call(final Object... args) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    String st = (String)args[0];
                    //text.setText(st);

                    if(st == null) return;
                    final TextView textView = new TextView(MainActivity.this);
                    if(textView == null) return;
                    textView.setText(st);
                    textView.setTextSize(20);

                    int red = (int) (Math.random()*200);
                    int green = (int) (Math.random()*200);
                    int blue =  (int) (Math.random()*200);
                    textView.setTextColor(Color.rgb(red, green, blue));

                    RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
                    int positionX = (int)(Math.random()*(width-55));//35
                    int positionY = (int)(Math.random()*150);
                    lp.setMargins(positionX, positionY, 0, 0);
                    textView.setLayoutParams(lp);

                    relativeLayout.addView(textView);

                    AlphaAnimation animation1 = new AlphaAnimation(0.0f, 1.0f);
                    animation1.setDuration(1000);
                    animation1.setStartOffset(500);
                    animation1.setFillAfter(true);
                    textView.startAnimation(animation1);

                    AlphaAnimation fadeOut = new AlphaAnimation(1.0f, 0.0f);
                    fadeOut.setDuration(1000);
                    fadeOut.setStartOffset(5000);
                    fadeOut.setFillAfter(true);
                    fadeOut.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation arg0) {
                        }

                        @Override
                        public void onAnimationRepeat(Animation arg0) {
                        }

                        @Override
                        public void onAnimationEnd(Animation arg0) {
                            //TODO: Remove TextView from screen
                            //((ViewManager) textView.getParent()).removeView(textView);
                        }
                    });
                    textView.startAnimation(fadeOut);
                }
            });
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        setContentView(R.layout.activity_main);

        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);

        options = getSharedPreferences("MAIN", 0);

        mSocket.connect();
        mSocket.on("new message", onNewMessage);

        relativeLayout = (RelativeLayout) findViewById(R.id.rootLayout);
        test = (Button) findViewById(R.id.testButton);
        message = (EditText) findViewById(R.id.messageEditText);

        Display display = getWindowManager().getDefaultDisplay();
        width = display.getWidth();
        height = display.getHeight();

        test.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String msg = message.getText().toString();
                msg = msg.replace("\n", "").replace("\r", "");
                message.setText("");
                mSocket.emit("new message", msg);
            }
        });

        message.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String msg = message.getText().toString();
                msg = msg.replace("\n", "").replace("\r", "");
                message.setText("");
                mSocket.emit("new message", msg);
                return false;
            }
        });

        alertDialog = new AlertDialog.Builder(MainActivity.this).create();
        alertDialog.setTitle("Welcome to Thought Cloud");
        alertDialog.setMessage("Simply type your thought, and send it away, anonymously. Instantly, see your thought, as well as other thoughts, as they are thought of. \n\nShare your mood or an idea or even a joke!");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                SharedPreferences.Editor editor = options.edit();
                editor.putBoolean("showHelp", true);
                editor.commit();
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Don't show again!", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();

                SharedPreferences.Editor editor = options.edit();
                editor.putBoolean("showHelp", false);
                editor.commit();
            }
        });
        if(options.getBoolean("showHelp", true)) alertDialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            System.out.println("SETTINGS");
            startActivity(new Intent(this, Settings.class));
            return true;
        }

        if (id == R.id.action_help) {
            alertDialog.show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mSocket.disconnect();
        mSocket.off("new message", onNewMessage);
    }
}