package com.hegdeapps.simonsays;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class SimonCell extends ImageView
{
    private final Context mContext;

    final static int ON = 1;
    final static int OFF = 2;
    public final static  int CELL_TYPE_RED = 1;
    public final static  int CELL_TYPE_BLUE = 2;
    public final static  int CELL_TYPE_GREEN = 3;
    public final static  int CELL_TYPE_YELLOW = 4;
    public  int onDrawable;
    public int offDrawable;


    int onColor;
    int offColor;
    int color;
    int state;

    int cellType;

    int soundRawFile;
    void setOn()
    {
        //color = onColor;
      //  CommonMethods.playSound(mContext,mSoundPool,soundId);
      //  state = ON;
        this.setImageDrawable(getResources().getDrawable(onDrawable));
        this.refreshDrawableState();

    }


    public SimonCell(Context ctx,AttributeSet attr){
        super(ctx,attr);
        mContext =ctx;
     //   mSoundPool =  new SoundPool(10, AudioManager.STREAM_MUSIC, 0);

    }
    public SimonCell(Context ctx){
        super(ctx);
        mContext =ctx;
      //  mSoundPool =  new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
    }
    /*SimonCell(Context ctx, SoundPool sndPool, int cellType, int soundId) {
        super(ctx);
        mContext = ctx;
        mSoundPool = sndPool;
        this.cellType = cellType;
        this.soundId = soundId;
        switch (this.cellType) {
            case CELL_TYPE_BLUE:
                this.onDrawable = R.drawable.blueon;

                offDrawable = R.drawable.bluoff;
                break;
            case CELL_TYPE_GREEN:
                onDrawable = R.drawable.greenon;
                offDrawable = R.drawable.greenoff;
                break;
            case CELL_TYPE_RED:
                onDrawable = R.drawable.redon;
                offDrawable = R.drawable.redoff;
                break;
            case CELL_TYPE_YELLOW:
                onDrawable = R.drawable.yellowon;
                offDrawable = R.drawable.yellowoff;
                break;
        }
        this.setImageDrawable(mContext.getResources().getDrawable(offDrawable));
    }*/
    void onCellClicked()
    {
        if(getState()!=ON) {
            setOn();


        }

     }

    public int getState() {
        return state;
    }
    void setOff() {
        state = OFF;
        this.setImageDrawable(getResources().getDrawable(offDrawable));

        this.refreshDrawableState();
    }

    public void setCellType(int cellType) {
        this.cellType = cellType;
        switch (this.cellType) {
            case CELL_TYPE_BLUE:
                this.onDrawable = R.drawable.bluebuttonon;

                offDrawable = R.drawable.bluebutton;
              /* .. soundRawFile =    R.raw.s1 ;*/
                break;
            case CELL_TYPE_GREEN:
                onDrawable = R.drawable.greenbuttonon;

                offDrawable = R.drawable.greenbutton;
             /*  soundRawFile =   R.raw.s2 ;*/
                break;
            case CELL_TYPE_RED:
                onDrawable = R.drawable.redbuttonon;
                offDrawable = R.drawable.redbutton;
            /*    soundRawFile =    R.raw.s3 ;*/
                break;
            case CELL_TYPE_YELLOW:
                onDrawable = R.drawable.yellowbuttonon;
                offDrawable = R.drawable.yellowbutton;
             /*   soundRawFile =    R.raw.s4 ;*/
                break;
        }

    }
}

