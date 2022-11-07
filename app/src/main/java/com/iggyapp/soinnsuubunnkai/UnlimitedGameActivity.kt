package com.iggyapp.soinnsuubunnkai

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.ads.*
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.android.synthetic.main.activity_time_limit_game.*
import kotlinx.android.synthetic.main.activity_unlimited_game.*
import kotlinx.android.synthetic.main.game_layout.*
import kotlinx.android.synthetic.main.number_input.*
import kotlin.math.pow


class UnlimitedGameActivity : AppCompatActivity() {
    var cnt = 0
    val hnd0= Handler()
    val rnb0=object : Runnable{
        override fun run() {
            cnt = cnt + 1
            timeer_number.text=cnt.toString()
            hnd0.postDelayed(this,1000)

        }
    }
    var firstSymbol = 0
    var secondSymbol = 0
    var firstNumber = 0
    var secondNumber = 0

    var answer1 = 0
    var answer2 = 0

    //答え
    var answerTwoNumber = 0
    var answerThreeNumber = 0
    var answerFiveNumber = 0
    var answerSevenNumber = 0

    var sum_answer_n = 0

    private var mInterstitialAd: InterstitialAd? = null
    private var TAG = "UnlimitedGameActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_unlimited_game)
        clickEvent()
        readyMan.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            readyMan.visibility = View.GONE
            startMan.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                startMan.visibility = View.GONE

                createQuestion()
                countStart()

            }, 700)
        }, 1500)
        videoAds()
    }
    //答え合わせ
    fun checkTheAnswer(){

        val answerA = answer_2.getText().toString().toInt()
        val answerB = answer_3.getText().toString().toInt()
        val answerC = answer_5.getText().toString().toInt()
        val answerD = answer_7.getText().toString().toInt()

        //答え合わせ
        if(answerA == answerTwoNumber && answerB == answerThreeNumber && answerC == answerFiveNumber && answerD == answerSevenNumber){
            correctAnswer()
        }else{
            batu.visibility = View.VISIBLE
            Handler(Looper.getMainLooper()).postDelayed({
                clear()
                batu.visibility = View.GONE
            }, 500)
        }
    }

    //問題作成
    fun createQuestion(){
        answerTwoNumber = (0..2).random()
        answerThreeNumber = (0..2).random()
        answerFiveNumber = (0..2).random()
        answerSevenNumber = (0..1).random()

        val question = 2.0.pow(answerTwoNumber) * 3.0.pow(answerThreeNumber) * 5.0.pow(answerFiveNumber) * 7.0.pow(answerSevenNumber)
        x2_number.text = question.toInt().toString()
    }

    //入力制御
    fun setNumber(number:Int){
        if(answer_2.getText().toString() == "" && answer_3.getText().toString() == "" && answer_5.getText().toString() == "" && answer_7.getText().toString() == ""){
            answer_2.text = number.toString()
        }else if( answer_3.getText().toString() == "" && answer_5.getText().toString() == "" && answer_7.getText().toString() == ""){
            answer_3.text = number.toString()
        }else if(answer_5.getText().toString() == "" && answer_7.getText().toString() == ""){
            answer_5.text = number.toString()
        }else{
            answer_7.text = number.toString()
        }
    }
    fun correctAnswer(){
        maru.visibility = View.VISIBLE
        Handler(Looper.getMainLooper()).postDelayed({
            // ここに0.5秒後に実行したい処理
            sum_answer_n++
            sum_answer_number.text = sum_answer_n.toString()
            clear()
            maru.visibility = View.GONE
            createQuestion()
            catController()
        }, 500)

    }

    fun countStart(){
        hnd0.post(rnb0)
    }
    fun countEnd(){
        hnd0.removeCallbacks(rnb0)
    }

    fun clickEvent(){
        zero.setOnClickListener {
            setNumber(0)
        }
        one.setOnClickListener {
            setNumber(1)
        }
        two.setOnClickListener {
            setNumber(2)
        }
        three.setOnClickListener {
            setNumber(3)
        }
        four.setOnClickListener {
            setNumber(4)
        }
        five.setOnClickListener {
            setNumber(5)
        }
        six.setOnClickListener {
            setNumber(6)
        }
        seven.setOnClickListener {
            setNumber(7)
        }
        eight.setOnClickListener {
            setNumber(8)
        }
        nine.setOnClickListener {
            setNumber(9)
        }
        enter.setOnClickListener {
            if(answer_2.getText().toString() != ""&& answer_3.getText().toString() != "" && answer_5.getText().toString() != "" && answer_7.getText().toString() != ""){
                checkTheAnswer()
            }else{
                batu.visibility = View.VISIBLE
                Handler(Looper.getMainLooper()).postDelayed({
                    clear()
                    batu.visibility = View.GONE
                }, 500)
            }
        }
        back.setOnClickListener {
            clear()
        }
        home.setOnClickListener {
            SaveInt()
            startActivity(Intent(this, TopActivity::class.java))
            overridePendingTransition(0, 0)
            finishAffinity()
        }
    }

    fun clear(){
        firstSymbol = 0
        secondSymbol = 0
        firstNumber = 0
        secondNumber = 0
        answer1 = 0
        answer2 = 0
        answer_2.text = ""
        answer_3.text = ""
        answer_5.text = ""
        answer_7.text = ""
    }

    override fun onStop() {
        super.onStop()

    }

    fun SaveInt(){
        val pref = getSharedPreferences("my_settings", Context.MODE_PRIVATE)
        val saveInt = pref.getInt("trofy", 0)
        val newInt = saveInt + sum_answer_n
        getSharedPreferences("my_settings", Context.MODE_PRIVATE).edit().apply {
            putInt("trofy", newInt)
            apply()
        }
    }

    fun catController(){

        when(sum_answer_n){
            10->{
                cat1.visibility = View.VISIBLE
            }
            20->{
                cat2.visibility = View.VISIBLE
            }
            30->{
                cat3.visibility = View.VISIBLE
            }
            40->{
                cat4.visibility = View.VISIBLE
            }
            50->{
                cat5.visibility = View.VISIBLE
            }
            60->{
                cat6.visibility = View.VISIBLE
            }
            70->{
                cat7.visibility = View.VISIBLE
            }
            80->{
                cat8.visibility = View.VISIBLE
            }
            90->{
                cat9.visibility = View.VISIBLE
            }
            100->{
                cat10.visibility = View.VISIBLE
            }
            110->{
                cat11.visibility = View.VISIBLE
            }
            120->{
                cat12.visibility = View.VISIBLE
            }
            130->{
                cat13.visibility = View.VISIBLE
            }
            140->{
                cat14.visibility = View.VISIBLE
            }
            else->{ }
        }

        if(sum_answer_n % 10 == 0){
            if (mInterstitialAd != null) {
                mInterstitialAd?.show(this)
            } else {
                Log.d("TAG", "The interstitial ad wasn't ready yet.")
            }
        }
    }

    fun videoAds(){
        MobileAds.initialize(this) {}

        val adRequest = AdRequest.Builder().build()
        adView_un.loadAd(adRequest)
        InterstitialAd.load(this,"ca-app-pub-5047266594533407/8282945398", adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.d(TAG, adError.message)
                mInterstitialAd = null
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d(TAG, "Ad was loaded.")
                mInterstitialAd = interstitialAd
            }
        })
        mInterstitialAd?.fullScreenContentCallback = object: FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                Log.d(TAG, "Ad was dismissed.")
            }

            override fun onAdFailedToShowFullScreenContent(adError: AdError?) {
                Log.d(TAG, "Ad failed to show.")
            }

            override fun onAdShowedFullScreenContent() {
                Log.d(TAG, "Ad showed fullscreen content.")
                mInterstitialAd = null;
            }
        }
    }
}