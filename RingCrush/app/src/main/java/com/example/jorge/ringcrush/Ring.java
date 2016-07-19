package com.example.jorge.ringcrush;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.Log;


public class Ring {

    float x,y,r;
    Paint c;
    public int color;

    public Ring(float X, float Y, float R, int C)
    {
        x = X;
        y = Y;
        r = R;
        c = new Paint();
        c.setColor(C);
        color = C;
    }

    public void setColor(int C)
    {
        c.setColor(C);
        color = C;
    }

    public void Draw(Canvas canvas)
    {
        canvas.drawCircle(x,y,r,c);
    }

    public boolean Collision(int cX, int cY)
    {
        //+20 e -20 são offsets
        return (cX + 20 > x && cX - 20 < x + r && cY + 20 > y && cY - 20 < y + r);
    }

}
