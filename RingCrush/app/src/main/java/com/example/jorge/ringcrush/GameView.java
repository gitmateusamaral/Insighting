package com.example.jorge.ringcrush;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.os.Handler;

import java.util.ArrayList;
import java.util.Random;

public class GameView extends View implements Runnable{

    Handler handler = new Handler();
    public ArrayList<Ring> rings;
    int[] colors;
    Random random;

    //variáveis utilizadas para os eventos de Touch e troca de Cor
    int tX,tY,tC,tI;

    public GameView(Context c)
    {
        super(c);
        init();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {

        int eventaction = event.getAction();

        switch (eventaction)
        {
            case MotionEvent.ACTION_DOWN:
                tX = (int) event.getX();
                tY = (int) event.getY();
                break;
            case MotionEvent.ACTION_UP:
                tX = 0;
                tY = 0;
                break;

        }
        return true;
    }

    public int selectColor(int a)
    {
        switch (a)
        {
            case 1:
                return Color.YELLOW;
            case 2:
                return Color.RED;
            case 3:
                return Color.GREEN;
            case 4:
                return Color.BLACK;
            default:
                return Color.BLUE;
        }
    }

    public void init()
    {
        rings = new ArrayList<Ring>();
        random = new Random();
        tX = 0;
        tY = 0;
        tC = 0;
        tI = 0;
        for(int i = 0; i < 5; i++)
        {
            for(int n = 0; n < 5; n++)
            {
                rings.add(new Ring(i*100 + 75,n*100 + 150,40,selectColor(random.nextInt(5))));
            }
        }
    }


    @Override
    public void run()
    {
        handler.postDelayed(this,30);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
    {
        for(int i = 0; i < rings.size(); i++)
        {
            rings.get(i).Draw(canvas);
            if(rings.get(i).Collision(tX,tY))
            {
                tX = 0;
                tY = 0;

                if(tC != 0)
                {
                    rings.get(tI).setColor(rings.get(i).color);
                    rings.get(i).setColor(tC);
                    tC = 0;
                    tI = 0;
                }
                else
                {
                    tC = rings.get(i).color;
                    tI = i;
                }
            }
        }
        super.onDraw(canvas);
        invalidate();
    }

}
