package com.hegdeapps.simonsays

import android.app.Dialog
import android.content.Context
import android.graphics.Typeface
import android.media.AudioManager
import android.media.Image
import android.media.SoundPool
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.preference.PreferenceManager
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.hegdeapps.simonsays.SimonCell
import java.util.*


/**
 * Simon or repeat after me. Glowing cells with sounds in random pattern User shd repeat pattern.
 * Created by hegde on 07-06-2016.
 */
class SimonActivity : AppCompatActivity(), View.OnClickListener {
    private val mCellList: ArrayList<SimonCell>? = null
    var soundPool: SoundPool? = null
        private set
    private val mSoundIDC = 0
    private val mSoundIDD = 0
    private val mSoundIDE = 0
    private val mSoundIDF = 0
    private var mSoundPoolLoaded = false
    private var mLevel = 1
    private var mCellOnList: ArrayList<Int>? = null
    val mImgView = arrayOfNulls<SimonCell>(4)
    private var count = 0
    private val mCellNum = 0
    private var mCurrentCellIndex = 0
    private var mTimer: Timer? = null
    private var mTimerTask: TimerTask? = null
    private val mSoundID = IntArray(4)
    private var mSndVolume = 0f
    private var mPrevSoundStreamID = 0
    private var mScore = 0
    private var mTimerForClick: CountDownTimer? = null
    private var btnSound: ImageButton? = null
    private var mSoundOn = true
    private val mHandler: Handler? = null
    private val mFirstCell = true
    private var mCountdownTimer: CountDownTimer? = null
    private var mTimerTaskCompleted = true
    private var mInstancesSaved = false
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val arr = IntArray(mCellOnList!!.size)
        for (i in mCellOnList!!.indices) {
            arr[i] = mCellOnList!![i]
        }
        outState.putIntArray(STR_CELL_ON_LIST, arr)
        outState.putInt(STR_LEVEL, mLevel)
        outState.putInt(STR_SCORE, mScore)
        outState.putBoolean(STR_TIMER_COMPLETED, mTimerTaskCompleted)
    }

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.simonactvt2)
        mImgView[0] = findViewById<View>(R.id.cell1) as SimonCell
        mImgView[1] = findViewById<View>(R.id.cell2) as SimonCell
        mImgView[2] = findViewById<View>(R.id.cell3) as SimonCell
        mImgView[3] = findViewById<View>(R.id.cell4) as SimonCell
        mImgView[0]!!.setCellType(SimonCell.CELL_TYPE_GREEN)
        mImgView[1]!!.setCellType(SimonCell.CELL_TYPE_RED)
        mImgView[2]!!.setCellType(SimonCell.CELL_TYPE_YELLOW)
        mImgView[3]!!.setCellType(SimonCell.CELL_TYPE_BLUE)
        for (i in 0..3) {
            mImgView[i]!!.setOnClickListener(this)
            mImgView[i]!!.setOff()
        }

        if (savedInstanceState != null) {
            mInstancesSaved = true
            val arr =
                savedInstanceState.getIntArray(STR_CELL_ON_LIST)
            mCellOnList = ArrayList()
            for (i in arr!!.indices) {
                mCellOnList!!.add(arr[i])
            }
            mScore = savedInstanceState.getInt(STR_SCORE)
            mLevel = savedInstanceState.getInt(STR_LEVEL)
            mTimerTaskCompleted =
                savedInstanceState.getBoolean(STR_TIMER_COMPLETED)

            initializeSoundPools()

        } else {
            initializeSoundPools()

            startGame()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater:MenuInflater = menuInflater
        inflater.inflate(R.menu.gamemenu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.action_help-> showHowToDialog();
            R.id.action_sound->processSoundButton()

        }
        return true;
    }

    override fun onResume() {
        super.onResume()
        // mSoundPool.autoResume();
    }

    override fun onDestroy() {
        super.onDestroy()
        if (soundPool != null) {
            soundPool!!.release()
            soundPool = null
        }
    }

    override fun onStop() {
        super.onStop()
        if (mPrevSoundStreamID != 0) {
            soundPool!!.stop(mPrevSoundStreamID)
        }
        if (mCountdownTimer != null) {
            mCountdownTimer!!.cancel()
        }
        if (mTimer != null) {
            mTimer!!.cancel()
        }
    }

    private fun initializeSoundPools() {
        if (soundPool == null) {
            soundPool = SoundPool(50, AudioManager.STREAM_MUSIC, 0)
            mSoundID[0] = soundPool!!.load(this, R.raw.dnote, 1)
            mSoundID[1] = soundPool!!.load(this, R.raw.enote, 1)
            mSoundID[2] = soundPool!!.load(this, R.raw.fnote, 1)
            mSoundID[3] = soundPool!!.load(this, R.raw.gnote, 1)
            soundPool!!.setOnLoadCompleteListener { soundPool, sampleId, status ->
                mSoundPoolLoaded = true
                if (!mTimerTaskCompleted && mInstancesSaved) {
                    glowCells2()
                }
                if (!mInstancesSaved && sampleId == mSoundID[3]) {
                    glowCells2()
                }
            }
            val audioManager =
                this.getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val actualVolume = audioManager
                .getStreamVolume(AudioManager.STREAM_MUSIC).toFloat()
            val maxVolume = audioManager
                .getStreamMaxVolume(AudioManager.STREAM_MUSIC).toFloat()
            mSndVolume = actualVolume / maxVolume
            volumeControlStream = AudioManager.STREAM_MUSIC
        }
    }

    private fun startGame() {

        val rnd =
            Random(Calendar.getInstance().timeInMillis)
        if (mCellOnList == null) {
            mCellOnList = ArrayList()
        }

        val num = rnd.nextInt(4)
        mCellOnList!!.add(num)


        if (mSoundPoolLoaded) {
            glowCells2()
        }
    }


    private fun startTimer(cell: SimonCell?) {
        val timer: CountDownTimer = object : CountDownTimer(300, 400) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                cell!!.setOff()
            }
        }
        timer.start()
    }


    override fun onClick(view: View) {
        val id = view.id

        when (id) {
            R.id.cell1 -> processCellClicked(0, view as SimonCell)
            R.id.cell2 -> processCellClicked(1, view as SimonCell)
            R.id.cell3 -> processCellClicked(2, view as SimonCell)
            R.id.cell4 -> processCellClicked(3, view as SimonCell)

        }
    }

    private fun showHowToDialog() {
        val d = Dialog(this, R.style.DialogTheme)
        d.setContentView(R.layout.simon_help_dialog)
        val tv = d.findViewById<View>(R.id.dialogmessage) as TextView
        val howTo = resources.getString(R.string.simon_how_to_play)
        tv.text = howTo
        val okButton =
            d.findViewById<View>(R.id.okbutton) as Button
        okButton.setOnClickListener { d.dismiss() }
        d.show()
    }


    private fun processSoundButton() {
        if (mSoundOn) {
            mSoundOn = false
        } else {
            mSoundOn = true
        }
        invalidateOptionsMenu()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        val item:MenuItem = menu!!.findItem(R.id.action_sound)
        if(!mSoundOn)
          item.setTitle("SoundOn")
        else
            item.setTitle("Mute")
        return true
    }


    private fun showToast() {
        val numCellsLeft = mLevel - count
        if (numCellsLeft > 0) {
            Toast.makeText(
                this,
                "$numCellsLeft more cells to click",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun processCellClicked(cellNum: Int, cell: SimonCell) {
        if (mTimerForClick != null) {
            mTimerForClick!!.cancel()
        }
        cell.setOn()
        if (mPrevSoundStreamID != 0) {
            soundPool!!.stop(mPrevSoundStreamID)
        }
        if (mSoundOn) {
            mPrevSoundStreamID =
                soundPool!!.play(mSoundID[cellNum], mSndVolume, mSndVolume, 1, 0, 1f)
        }
        startTimer(cell)
        if (mCellOnList!![count] != cellNum) {
            showAlert("Game Over\n\nScore $mScore", GAME_OVER)
            return
        }
        count++
        if (count == mLevel) {
            mLevel++
            count = 0
            mScore += 1000
            showScore()
            saveScoreInPref()
            showAlert(
                "LEVEL " + (mLevel - 1).toString() + " COMPLETED\n\nSCORE " + mScore,
                LEVEL_COMPLETED
            )
        }
    }

    private fun showScore() {
        val tvScore = findViewById<View>(R.id.score) as TextView
        if (tvScore != null) {
            tvScore.text = mScore.toString()
        }
    }

    fun glowCells2() {
        mInstancesSaved = false
        mCurrentCellIndex = 0
        // int duration = 1000 * mCellOnList.size();
        if (mTimer != null) {
            mTimer!!.cancel()
        }
        mTimer = Timer()
        mTimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    mTimerTaskCompleted = false
                    if (mCurrentCellIndex < mCellOnList!!.size) {
                        glowOneCell(mCellOnList!![mCurrentCellIndex++])
                        /*if(mCurrentCellIndex==mCellOnList.size()){
                                            showTitle();
                                        }*/
                    } else {
                        mTimerTaskCompleted = true
                    }
                }
            }
        }
        mTimer!!.schedule(mTimerTask, 0, 700)
    }


    private fun glowOneCell(celIndex: Int) {
        val cell = mImgView[celIndex]
        if (mPrevSoundStreamID != 0) {
            soundPool!!.stop(mPrevSoundStreamID)
        }
        if (mSoundOn) {
            mPrevSoundStreamID =
                soundPool!!.play(mSoundID[celIndex], mSndVolume, mSndVolume, 1, 0, 1f)
        }
        cell!!.setOn()
        startTimer(cell)
    }

    private fun showAlert(s: String, dlgCode: Int) {
        val d = Dialog(this, android.R.style.Theme_Light_NoTitleBar)
        d.setContentView(R.layout.simondialog)
        val tv = d.findViewById<View>(R.id.dialogmessage) as TextView
        val tpEvilDead = Typeface.createFromAsset(assets, "evildead.ttf")
        tv.setTypeface(tpEvilDead)
        tv.textSize = 24f
        tv.text = s
        mCountdownTimer = object : CountDownTimer(1000, 1000) {
            override fun onTick(l: Long) {}
            override fun onFinish() {
                d.dismiss()
                if (dlgCode == LEVEL_COMPLETED) {
                    startGame()
                } else if (dlgCode == GAME_OVER) {
                    finish()
                }
            }
        }
        (mCountdownTimer as CountDownTimer).start()
        d.show()
    }

    private fun saveScoreInPref(): Boolean {
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()
        val maxScore = pref.getInt(Constants.SIMON_GAME_MAX_SCORE, -10)
        return if (mScore > maxScore) {
            editor.putInt(Constants.SIMON_GAME_MAX_SCORE, mScore)
            editor.commit()
            true
        } else {
            false
        }
    }

    companion object {
        private const val LEVEL_COMPLETED = 1
        private const val GAME_OVER = 2
        private const val STR_CELL_ON_LIST = "CellOnList"
        private const val STR_SCORE = "Score"
        private const val STR_LEVEL = "Level"
        private const val STR_TIMER_COMPLETED = "GameDisplayTimerCompleted"
    }
}