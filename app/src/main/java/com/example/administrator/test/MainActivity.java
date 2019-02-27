package com.example.administrator.test;


import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;

import okhttp3.FormBody;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";
    private EditText editText_original_text;
    private EditText editText_translation_result;
    private Button button_translate;

    private String phEnMp3Url="";
    private String phAmMp3Url="";
    private Button button_phEnMp3;
    private Button button_phAmMp3;

    private MediaPlayer mediaPlayer; // 媒体播放器

    private Spinner spinner_from;
    public String stringSelectFrom="auto";
    private Spinner spinner_to;
    public String stringSelectTo="auto";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText_original_text=findViewById(R.id.editText_original_text);
        editText_translation_result=findViewById(R.id.editText_translation_result);
        button_translate=findViewById(R.id.button_translate);

        button_phEnMp3=findViewById(R.id.button_phEnMp3);
        button_phAmMp3=findViewById(R.id.button_phAmMp3);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setDataSource(MainActivity.this, Uri.parse("http://res.iciba.com/resource/amp3/oxford/0/e8/7d/e87dcdd7986213c8def8af06571439d9.mp3"));
        }catch (Exception e){
            e.printStackTrace();
        }
        //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);// 设置媒体流类型

        spinner_from=findViewById(R.id.spinner_from);


        ArrayAdapter adapterFrom = ArrayAdapter.createFromResource(MainActivity.this,R.array.planets_from,android.R.layout.simple_spinner_dropdown_item );
        adapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_from.setAdapter(adapterFrom);


        //System.out.println(spinner_from.getSelectedItem());

        spinner_from.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
                stringSelectFrom=getCode((String)spinner_from.getSelectedItem());
                Log.d(TAG,"原文语言编码："+stringSelectFrom);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        spinner_to=findViewById(R.id.spinner_to);
        ArrayAdapter adapterTo = ArrayAdapter.createFromResource(MainActivity.this,R.array.planets_to,android.R.layout.simple_spinner_dropdown_item );
        adapterTo.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_to.setAdapter(adapterTo);

        spinner_to.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent,View view,int position,long id) {
                stringSelectTo=getCode((String)spinner_to.getSelectedItem());
                Log.d(TAG,"翻译后语言编码："+stringSelectTo);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });



        button_phEnMp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    Log.d("a",Uri.parse("http://res.iciba.com/resource/amp3/oxford/0/e8/7d/e87dcdd7986213c8def8af06571439d9.mp3").toString());
                    //mediaPlayer.reset();
                    mediaPlayer.setDataSource(MainActivity.this,Uri.parse("http://res.iciba.com/resource/amp3/oxford/0/e8/7d/e87dcdd7986213c8def8af06571439d9.mp3"));


                    //mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    //mediaPlayer.prepareAsync();// prepare之后异步播放

                    //MusicApplication.getInstance().playingMusiName = playingMusiName;

                } catch (IllegalArgumentException e) {

                    e.printStackTrace();
                } catch (IllegalStateException e) {

                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }



            }

        });




/*
                try {
                    //ediaPlayer.reset();
                    //ediaPlayer.setDataSource("http://res.iciba.com/resource/amp3/1/0/5d/41/5d41402abc4b2a76b9719d911017c592.mp3"); // 设置数据源
                    //ediaPlayer.prepare(); // prepare自动播放





                    String stringExtra1="http://res.iciba.com/resource/amp3/1/0/5d/41/5d41402abc4b2a76b9719d911017c592.mp3";
                    if(stringExtra1!=null){
                         uri1 = Uri.parse(stringExtra1);
                    }else{
                        Uri uri1=Uri.parse(stringExtra1);
                    }
                    try {
                        //ediaPlayer.setDataSource(MediaPlayerService.this, uri1);
                        ediaPlayer.setDataSource("http://res.iciba.com/resource/amp3/1/0/5d/41/5d41402abc4b2a76b9719d911017c592.mp3"); // 设置数据源
                        ediaPlayer.prepareAsync();
                        ediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                            @Override
                            public void onPrepared(MediaPlayer mp) {
                                // TODO Auto-generated method stub
                                Log.e("MusicReceiver", "a");
                                mp.start();
                            }
                        });
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }








                }catch (Exception e) {
                    e.printStackTrace();
                }


            }
*/


        button_translate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request();

                //Log.d(TAG,"哈哈");
            }
        });

    }

    protected String getCode(String stringValue) {
        switch(stringValue){
            case "自动":return "auto";
            case "中文":return "zh";
            case "英语":return"en";
            case "日语":return "ja";
            case "韩语":return "ko";
            case "德语":return "de";
            case "西班牙语":return "es";
            case "法语":return "fr";
            default:return "auto";
        }
    }

    public void request() {

        //步骤4:创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .build();

        // 步骤5:创建 网络请求接口 的实例
        GetRequest_Interface request = retrofit.create(GetRequest_Interface.class);

        //对 发送请求 进行封装
        Call<Translation> call = request.getCall(this.stringSelectFrom,this.stringSelectTo,editText_original_text.getText().toString());//editText_original_text.getText().toString()

        Log.d(TAG,"查看URL：");
        Log.d(TAG,call.request().url().toString());



        Log.d(TAG,"查看方法：");
        Log.d(TAG,call.request().method());

        StringBuilder sb = new StringBuilder();
        if (call.request().body() instanceof FormBody) {
            FormBody body = (FormBody) call.request().body();
            for (int i = 0; i < body.size(); i++) {
                sb.append(body.encodedName(i) + "=" + body.encodedValue(i) + ",");
            }
            sb.delete(sb.length() - 1, sb.length());
            Log.d(TAG, "RequestParams:{"+sb.toString()+"}");
        }

        //步骤6:发送网络请求(异步)
        call.enqueue(new Callback<Translation>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                // 步骤7：处理返回的数据结果
                final String string=response.body().getContent().getTranslationText();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (string != null)
                                editText_translation_result.setText(string);
                        }
                    });
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<Translation> call, Throwable throwable) {
                Log.d(TAG,"连接失败");
            }
        });
    }
}
