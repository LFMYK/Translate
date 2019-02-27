package com.example.administrator.test;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Translation {
    private static final String TAG="Translation";
    private int status;
    private content content;

    public int getStatus() {
        return status;
    }

    public Translation.content getContent() {
        return content;
    }

    public void setContent(Translation.content content) {
        this.content = content;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public class content {

        @SerializedName("ph_en")
         String phEn;//英【音标】

        @SerializedName("ph_en_mp3")
        private String phEnMp3;//英：MP3网址


        @SerializedName("ph_am")
        private String phAm;//美【音标】

        @SerializedName("ph_am_mp3")
        private String phAmMp3;//美：MP3网址



        @SerializedName("ph_tts_mp3")
        private String phTtsMp3;//全求：MP3网址

        @SerializedName("word_mean")
        private List<String> wordMean;

        public String getMultiWord() {
            return multiWord;
        }

        @SerializedName("out")
        private String multiWord;//全求：MP3网址

        public String getPhEn() {
            return phEn;
        }

        public String getPhEnMp3() {
            return phEnMp3;
        }

        public String getPhAm() {
            return phAm;
        }

        public String getPhAmMp3() {
            return phAmMp3;
        }

        public String getPhTtsMp3() {
            return phTtsMp3;
        }

        public List<String> getWordMean() {
            return wordMean;
        }


        public String getTranslationText() {
            StringBuilder stringBuilder=new StringBuilder("");

            if (wordMean!=null) {//单个字母的翻译结果
                if (phEn!=null)
                    stringBuilder.append("英["+phEn+"]\n");
                if (phAm!=null)
                    stringBuilder.append("美["+phAm+"]\n");



                for (String s : wordMean) {
                    s = s + "\n";
                    stringBuilder.append(s);
                }
            }

            if (multiWord!=null) { //多单词的翻译结果
                    stringBuilder.append(multiWord);
            }


            return stringBuilder.toString();
        }


    }





    //定义 输出返回数据 的方法
    public void show() {
        Log.v(TAG,"test11");
        Log.v(TAG,"----------开始输出结果----------");
        Log.v(TAG,"状态（请求成功时取0）："+status);

        Log.v(TAG,"英["+content.phEn+"]");
        Log.v(TAG,"英MP3网址:"+content.phEnMp3);
        Log.v(TAG,"美["+content.phAm+"]");
        Log.v(TAG,"美：MP3网址："+content.phAmMp3);
        Log.v(TAG,"全求MP3网址："+content.phTtsMp3);

        Log.v(TAG,"内容翻译后：");
        Log.v(TAG,content.getTranslationText());
        /*
        for(String s:content.wordMean)
            Log.v(TAG,s);
        */

        Log.v(TAG,"----------结束输出结果----------");
    }
}

