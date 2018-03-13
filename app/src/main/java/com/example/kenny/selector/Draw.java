package com.example.kenny.selector;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Draw {
    private Context context;
    private Bitmap image;
    private Bitmap bigImage;
    private Bitmap smallImage1;
    private Bitmap smallImage2;
    private Bitmap smallImage3;
    private Bitmap smallImage4;
    private Bitmap smallImage5;
    private Bitmap smallImage6;
    private Bitmap smallImage7;
    private Bitmap smallImage8;
    private Bitmap smallImage9;
    private Bitmap smallImage10;
    private List<Item> peopleList = new ArrayList<>();
    private List<Place> placeList;

    public Draw(Context context, List<Item> peopleList, boolean isExistText) {
        this.context = context;
        this.peopleList = peopleList;
        circleImage(peopleList, isExistText);
    }
    public Draw(Context context, List<Place> placeList) {
        this.context = context;
        this.placeList = placeList;
        circlePlaceText(placeList, false);
    }
    public Draw(Context context, boolean isExistTest) {
        this.context = context;
        circleImage(peopleList, isExistTest);
    }

    public void circlePlaceText(List<Place> placeList, boolean isExistText) {
        String imageText1;
        String imageText2;
        String imageText3;
        String imageText4;
        String imageText5;
        switch (placeList.size()) {
            case 2:
                bigImage = readBitMap(context, R.mipmap.circle_2);
                imageText1 = placeList.get(0).getName();
                imageText2 = placeList.get(1).getName();
                image = create2SingleImageFrom2Texts(bigImage, imageText1, imageText2);
                break;
            case 3:
                bigImage = readBitMap(context, R.mipmap.circle_3);
                imageText1 = placeList.get(0).getName();
                imageText2 = placeList.get(1).getName();
                imageText3 = placeList.get(2).getName();
                image = create3SingleImageFrom3Texts(bigImage, imageText1, imageText2, imageText3);
                break;
            case 4:
                bigImage = readBitMap(context, R.mipmap.circle_4);
                imageText1 = placeList.get(0).getName();
                imageText2 = placeList.get(1).getName();
                imageText3 = placeList.get(2).getName();
                imageText4 = placeList.get(3).getName();
                image = create4SingleImageFrom4Texts(bigImage, imageText1, imageText2, imageText3, imageText4);
                break;
            case 5:
                bigImage = readBitMap(context, R.mipmap.circle_5);
                imageText1 = placeList.get(0).getName();
                imageText2 = placeList.get(1).getName();
                imageText3 = placeList.get(2).getName();
                imageText4 = placeList.get(3).getName();
                imageText5 = placeList.get(4).getName();
                image = create5SingleImageFrom5Texts(bigImage, imageText1, imageText2, imageText3, imageText4, imageText5);
                break;
        }
    }

    public Bitmap create2SingleImageFrom2Texts(Bitmap backgroundBitmap, String imageText1, String imageText2) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(imageText1.charAt(0)), 40, 982, paint);
        canvas.drawText(String.valueOf(imageText2.charAt(0)), 1900, 1090, paint);
        return  result;
    }

    public Bitmap create3SingleImageFrom3Texts(Bitmap backgroundBitmap, String imageText1, String imageText2, String imageText3) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(imageText1.charAt(0)), 990, 102, paint);
        canvas.drawText(String.valueOf(imageText2.charAt(0)), 1830, 772, paint);
        canvas.drawText(String.valueOf(imageText3.charAt(0)), 1420, 1752, paint);
        return  result;
    }

    public Bitmap create4SingleImageFrom4Texts(Bitmap backgroundBitmap, String imageText1, String imageText2, String imageText3, String imageText4) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(imageText1.charAt(0)), 50, 892, paint);
        canvas.drawText(String.valueOf(imageText2.charAt(0)), 1000, 120, paint);
        canvas.drawText(String.valueOf(imageText3.charAt(0)), 1800, 1040, paint);
        canvas.drawText(String.valueOf(imageText4.charAt(0)), 870, 1850, paint);
        return  result;
    }

    public Bitmap create5SingleImageFrom5Texts(Bitmap backgroundBitmap, String imageText1, String imageText2, String imageText3, String imageText4, String imageText5) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        paint.setTextSize(100);
        paint.setColor(Color.WHITE);
        canvas.drawText(String.valueOf(imageText1.charAt(0)), 990, 102, paint);
        canvas.drawText(String.valueOf(imageText2.charAt(0)), 1830, 772, paint);
        canvas.drawText(String.valueOf(imageText3.charAt(0)), 1420, 1752, paint);
        canvas.drawText(String.valueOf(imageText4.charAt(0)), 350, 1682, paint);
        canvas.drawText(String.valueOf(imageText5.charAt(0)), 80, 650, paint);
        return  result;
    }

    public void circleImage(List<Item> peopleList, boolean isExistText) {
        switch (peopleList.size()) {
            case 0:
                bigImage = readBitMap(context, R.mipmap.circle_2);
                image = create2SingleImageFromMultipleImages(bigImage, isExistText);
                break;
            case 4:
                bigImage = readBitMap(context, R.mipmap.circle_4);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                image = create4SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, isExistText);
                break;
            case 5:
                bigImage = readBitMap(context , R.mipmap.circle_5);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                image = create5SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, isExistText);
                break;
            case 6:
                bigImage = readBitMap(context, R.mipmap.circle_6);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                smallImage6 = readBitMap(context, getDrawableID(peopleList.get(5).getImageName()));
                image = create6SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, smallImage6, isExistText);
                break;
            case 7:
                bigImage = readBitMap(context , R.mipmap.circle_7);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                smallImage6 = readBitMap(context, getDrawableID(peopleList.get(5).getImageName()));
                smallImage7 = readBitMap(context, getDrawableID(peopleList.get(6).getImageName()));
                image = create7SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, smallImage6, smallImage7, isExistText);
                break;
            case 8:
                bigImage = readBitMap(context , R.mipmap.circle_8);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                smallImage6 = readBitMap(context, getDrawableID(peopleList.get(5).getImageName()));
                smallImage7 = readBitMap(context, getDrawableID(peopleList.get(6).getImageName()));
                smallImage8 = readBitMap(context, getDrawableID(peopleList.get(7).getImageName()));
                image = create8SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, smallImage6, smallImage7, smallImage8, isExistText);
                break;
            case 9:
                bigImage = readBitMap(context , R.drawable.circle_9);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                smallImage6 = readBitMap(context, getDrawableID(peopleList.get(5).getImageName()));
                smallImage7 = readBitMap(context, getDrawableID(peopleList.get(6).getImageName()));
                smallImage8 = readBitMap(context, getDrawableID(peopleList.get(7).getImageName()));
                smallImage9 = readBitMap(context, getDrawableID(peopleList.get(8).getImageName()));
                image = create9SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, smallImage6, smallImage7, smallImage8, smallImage9, isExistText);
                break;
            case 10:
                bigImage = readBitMap(context, R.drawable.circle_10);
                smallImage1 = readBitMap(context, getDrawableID(peopleList.get(0).getImageName()));
                smallImage2 = readBitMap(context, getDrawableID(peopleList.get(1).getImageName()));
                smallImage3 = readBitMap(context, getDrawableID(peopleList.get(2).getImageName()));
                smallImage4 = readBitMap(context, getDrawableID(peopleList.get(3).getImageName()));
                smallImage5 = readBitMap(context, getDrawableID(peopleList.get(4).getImageName()));
                smallImage6 = readBitMap(context, getDrawableID(peopleList.get(5).getImageName()));
                smallImage7 = readBitMap(context, getDrawableID(peopleList.get(6).getImageName()));
                smallImage8 = readBitMap(context, getDrawableID(peopleList.get(7).getImageName()));
                smallImage9 = readBitMap(context, getDrawableID(peopleList.get(8).getImageName()));
                smallImage10 = readBitMap(context, getDrawableID(peopleList.get(9).getImageName()));
                image = create10SingleImageFromMultipleImages(bigImage, smallImage1, smallImage2, smallImage3, smallImage4, smallImage5, smallImage6, smallImage7, smallImage8, smallImage9, smallImage10, isExistText);
                break;
        }
    }

    public Bitmap create2SingleImageFromMultipleImages(Bitmap backgroundBitmap, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        if(isExistText) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.drawText("yes", 40, 982, paint);
            canvas.drawText("no", 1900, 1090, paint);
        }
        return result;
    }

    public Bitmap create4SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 50, 852, null);
        canvas.drawBitmap(resize2, 1000, 60, null);
        canvas.drawBitmap(resize3, 1800, 980, null);
        canvas.drawBitmap(resize4, 870, 1790, null);
        if(isExistText) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.drawText(peopleList.get(0).getName(), 200, 942, paint);
            canvas.rotate(90, 400, 600);
            canvas.drawText(peopleList.get(1).getName(), 0, 0,paint);
            canvas.restore();
            canvas.drawText(peopleList.get(2).getName(), 1500, 1062, paint);
            canvas.rotate(90);
            canvas.drawText(peopleList.get(3).getName(), 1500, -900, paint);
        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        System.gc();
        return result;
    }

    public Bitmap create5SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 990, 42, null);
        canvas.drawBitmap(resize2, 1800, 732, null);
        canvas.drawBitmap(resize3, 1400, 1692, null);
        canvas.drawBitmap(resize4, 350, 1632, null);
        canvas.drawBitmap(resize5, 80, 600, null);
        if(isExistText) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.rotate(90);
            canvas.drawText(peopleList.get(0).getName(), 200, -1010,paint);
            canvas.restore();
            canvas.rotate(157, 100, 100);
            canvas.drawText(peopleList.get(1).getName(), -1150, -1150,paint);
            canvas.restore();
            canvas.rotate(245, 200, 100);
            canvas.drawText(peopleList.get(2).getName(), -1750, 550,paint);
            canvas.restore();
            canvas.rotate(310, 400, 100);
            canvas.drawText(peopleList.get(3).getName(), -750, 1130,paint);
            canvas.restore();
            canvas.rotate(17, -100, 0);
            canvas.drawText(peopleList.get(4).getName(), 410, 570,paint);
       }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        System.gc();
        return result;
    }

    public Bitmap create6SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap resize6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 40, 982, null);
        canvas.drawBitmap(resize2, 570, 1752, null);
        canvas.drawBitmap(resize3, 1340, 122, null);
        canvas.drawBitmap(resize4, 440, 182, null);
        canvas.drawBitmap(resize5, 1840, 872, null);
        canvas.drawBitmap(resize6, 1440, 1692, null);
        if(isExistText == true) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.drawText(peopleList.get(0).getName(), 180, 1062, paint);
            canvas.rotate(-70, 1800, -400);
            canvas.drawText(peopleList.get(1).getName(), -550, -700, paint);
            canvas.restore();
            canvas.rotate(120, 400, 400);
            canvas.drawText(peopleList.get(2).getName(), -200, -300, paint);
            canvas.restore();
            canvas.rotate(55);
            canvas.drawText(peopleList.get(3).getName(), 550, -200, paint);
            canvas.restore();
            canvas.drawText(peopleList.get(4).getName(), 1500, 952, paint);
            canvas.rotate(-120, 1400, 400);
            canvas.drawText(peopleList.get(5).getName(), 250, -160, paint);
        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        resize6.recycle();
        System.gc();
        return result;
    }

    public Bitmap create7SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap resize6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        Bitmap resize7 = Bitmap.createScaledBitmap(bitmap7, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 40, 1082, null);
        canvas.drawBitmap(resize2, 1280, 1782, null);
        canvas.drawBitmap(resize3, 260, 302, null);
        canvas.drawBitmap(resize4, 1720, 402, null);
        canvas.drawBitmap(resize5, 1020, 42, null);
        canvas.drawBitmap(resize6, 1820, 1202, null);
        canvas.drawBitmap(resize7, 490, 1752, null);
        if(isExistText == true) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.rotate(-15, 400, 0);
            canvas.drawText(peopleList.get(0).getName(), -100, 1050, paint);
            canvas.restore();
            canvas.rotate(68);
            canvas.drawText(peopleList.get(1).getName(), 1800, -500, paint);
            canvas.restore();
            canvas.rotate(40);
            canvas.drawText(peopleList.get(2).getName(), 550, 100, paint);
            canvas.restore();
            canvas.rotate(-45, 800, 0);
            canvas.drawText(peopleList.get(3).getName(), 800, 1000, paint);
            canvas.restore();
            canvas.rotate(90);
            canvas.drawText(peopleList.get(4).getName(), 150, -1040, paint);
            canvas.restore();
            canvas.rotate(10);
            canvas.drawText(peopleList.get(5).getName(), 1700, 940, paint);
            canvas.restore();
            canvas.rotate(-60);
            canvas.drawText(peopleList.get(6).getName(), -1210, 1370, paint);
        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        resize6.recycle();
        resize7.recycle();
        System.gc();
        return result;
    }

    public Bitmap create8SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, Bitmap bitmap8, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap resize6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        Bitmap resize7 = Bitmap.createScaledBitmap(bitmap7, 100, 100, true);
        Bitmap resize8 = Bitmap.createScaledBitmap(bitmap8, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 340, 1582, null);
        canvas.drawBitmap(resize2, 870, 42, null);
        canvas.drawBitmap(resize3, 1800, 852, null);
        canvas.drawBitmap(resize4, 220, 322, null);
        canvas.drawBitmap(resize5, 1000, 1822, null);
        canvas.drawBitmap(resize6, 1520, 242, null);
        canvas.drawBitmap(resize7, 20, 972, null);
        canvas.drawBitmap(resize8, 1600, 1482, null);
        if(isExistText) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.rotate(-45);
            canvas.drawText(peopleList.get(0).getName(), -800, 1450, paint);
            canvas.restore();
            canvas.rotate(90);
            canvas.drawText(peopleList.get(1).getName(), 150, -900, paint);
            canvas.restore();
            canvas.drawText(peopleList.get(2).getName(), 1490, 952, paint);
            canvas.restore();
            canvas.rotate(50);
            canvas.drawText(peopleList.get(3).getName(), 550, 50, paint);
            canvas.restore();
            canvas.rotate(90);
            canvas.drawText(peopleList.get(4).getName(), 1500, -1000, paint);
            canvas.restore();
            canvas.rotate(130);
            canvas.drawText(peopleList.get(5).getName(), -700, -1330, paint);
            canvas.restore();
            canvas.drawText(peopleList.get(6).getName(), 120, 1072, paint);
            canvas.restore();
            canvas.rotate(50);
            canvas.drawText(peopleList.get(7).getName(), 1900, -200, paint);
        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        resize6.recycle();
        resize7.recycle();
        resize8.recycle();
        System.gc();
        return result;
    }

    public Bitmap create9SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, Bitmap bitmap8, Bitmap bitmap9, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap resize6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        Bitmap resize7 = Bitmap.createScaledBitmap(bitmap7, 100, 100, true);
        Bitmap resize8 = Bitmap.createScaledBitmap(bitmap8, 100, 100, true);
        Bitmap resize9 = Bitmap.createScaledBitmap(bitmap9, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 180, 1482, null);
        canvas.drawBitmap(resize2, 50, 862, null);
        canvas.drawBitmap(resize3, 1350, 1830, null);
        canvas.drawBitmap(resize4, 310, 262, null);
        canvas.drawBitmap(resize5, 910, 32, null);
        canvas.drawBitmap(resize6, 1510, 202, null);
        canvas.drawBitmap(resize7, 1900, 702, null);
        canvas.drawBitmap(resize8, 740, 1860, null);
        canvas.drawBitmap(resize9, 1840, 1360, null);
        if(isExistText) {
            paint.setTextSize(100);
            paint.setColor(Color.WHITE);
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.save();
            canvas.rotate(-30);
            canvas.drawText(peopleList.get(0).getName(), -450, 1500, paint);
            canvas.restore();
            canvas.rotate(10);
            canvas.drawText(peopleList.get(1).getName(), 370, 900, paint);
            canvas.restore();
            canvas.rotate(70);
            canvas.drawText(peopleList.get(2).getName(), 1900, -630, paint);
            canvas.restore();
            canvas.rotate(50);
            canvas.drawText(peopleList.get(3).getName(), 550, -50, paint);
            canvas.restore();
            canvas.rotate(90);
            canvas.drawText(peopleList.get(4).getName(), 150, -940, paint);
            canvas.restore();
            canvas.rotate(-50);
            canvas.drawText(peopleList.get(5).getName(), 450, 1400, paint);
            canvas.restore();
            canvas.rotate(-15);
            canvas.drawText(peopleList.get(6).getName(), 1300, 1250, paint);
            canvas.restore();
            canvas.rotate(-80);
            canvas.drawText(peopleList.get(7).getName(), -1600, 1150, paint);
            canvas.restore();
            canvas.rotate(25);
            canvas.drawText(peopleList.get(8).getName(), 1950, 500, paint);
        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        resize6.recycle();
        resize7.recycle();
        resize8.recycle();
        resize9.recycle();
        System.gc();
        return result;
    }

    public Bitmap create10SingleImageFromMultipleImages(Bitmap backgroundBitmap, Bitmap bitmap1, Bitmap bitmap2, Bitmap bitmap3, Bitmap bitmap4, Bitmap bitmap5, Bitmap bitmap6, Bitmap bitmap7, Bitmap bitmap8, Bitmap bitmap9, Bitmap bitmap10, boolean isExistText) {
        Paint paint = new Paint();
        Bitmap result = Bitmap.createBitmap(backgroundBitmap.getWidth(), backgroundBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Bitmap resize1 = Bitmap.createScaledBitmap(bitmap1, 100, 100, true);
        Bitmap resize2 = Bitmap.createScaledBitmap(bitmap2, 100, 100, true);
        Bitmap resize3 = Bitmap.createScaledBitmap(bitmap3, 100, 100, true);
        Bitmap resize4 = Bitmap.createScaledBitmap(bitmap4, 100, 100, true);
        Bitmap resize5 = Bitmap.createScaledBitmap(bitmap5, 100, 100, true);
        Bitmap resize6 = Bitmap.createScaledBitmap(bitmap6, 100, 100, true);
        Bitmap resize7 = Bitmap.createScaledBitmap(bitmap7, 100, 100, true);
        Bitmap resize8 = Bitmap.createScaledBitmap(bitmap8, 100, 100, true);
        Bitmap resize9 = Bitmap.createScaledBitmap(bitmap9, 100, 100, true);
        Bitmap resize10 = Bitmap.createScaledBitmap(bitmap10, 100, 100, true);
        canvas.drawBitmap(backgroundBitmap, 0f, 0f, null);
        canvas.drawBitmap(resize1, 50, 902, null);
        canvas.drawBitmap(resize2, 250, 352, null);
        canvas.drawBitmap(resize3, 1330, 100, null);
        canvas.drawBitmap(resize4, 730, 50, null);
        canvas.drawBitmap(resize5, 1780, 482, null);
        canvas.drawBitmap(resize6, 1940, 1032, null);
        canvas.drawBitmap(resize7, 1700, 1562, null);
        canvas.drawBitmap(resize8, 1190, 1872, null);
        canvas.drawBitmap(resize9, 610, 1852, null);
        canvas.drawBitmap(resize10, 190, 1482, null);
//        if(isExistText == true) {
//            paint.setTextSize(200);
//            paint.setColor(Color.WHITE);
//            canvas.rotate(-18, 3000, 3300);
//            canvas.drawText(peopleList.get(0).getName(), 3450, 1622, paint);
//            canvas.save();
//            canvas.rotate(70, 2000, 2500);
//            canvas.drawText(peopleList.get(1).getName(), 2450, 1822, paint);
//            canvas.save();
//            canvas.rotate(70, 2000, 2500);
//            canvas.drawText(peopleList.get(2).getName(), 2191, 2852, paint);
//            canvas.save();
//            canvas.rotate(-105, 1000, 1500);
//            canvas.drawText(peopleList.get(3).getName(), -2000, 1492, paint);
//            canvas.save();
//            canvas.rotate(70, 1000, 1500);
//            canvas.drawText(peopleList.get(4).getName(), -1200, 2812, paint);
//        }
        resize1.recycle();
        resize2.recycle();
        resize3.recycle();
        resize4.recycle();
        resize5.recycle();
        resize6.recycle();
        resize7.recycle();
        resize8.recycle();
        resize9.recycle();
        resize10.recycle();
        System.gc();
        return result;
    }

    public int getDrawableID(String name) {
        String drawableName = name;
        int resID = context.getResources().getIdentifier(drawableName , "drawable", context.getPackageName());
        return resID;
    }

    public Bitmap getImage() {
        return image;
    }

    public static Bitmap readBitMap(Context context, int resId){
        BitmapFactory.Options opt = new BitmapFactory.Options();
        opt.inPreferredConfig = Bitmap.Config.RGB_565;
        opt.inPurgeable = true;
        opt.inInputShareable = true;
        InputStream is = context.getResources().openRawResource(resId);
        return BitmapFactory.decodeStream(is,null,opt);
    }
}
