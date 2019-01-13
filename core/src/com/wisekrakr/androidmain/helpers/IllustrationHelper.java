package com.wisekrakr.androidmain.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class IllustrationHelper {
    private static String[] fileHandleNames;
    private static String[] fileHandleNames2;
    private static Pixmap pixmap;

    /**
     *  Converts HSV values to RGBA
     * @param hue The hue input value
     * @param saturation The saturation of the colour
     * @param value the value of the colour
     * @param alpha the alpha to output with RGB
     * @return The RGBA value
     */
    public static Color hsvToRgba(float hue, float saturation, float value, float alpha) {

        int h = (int)(hue * 6);
        float f = hue * 6 - h;
        float p = value * (1 - saturation);
        float q = value * (1 - f * saturation);
        float t = value * (1 - (1 - f) * saturation);

        switch (h) {
            case 0: return new Color(value, t, p,alpha);
            case 1: return new Color(q, value, p,alpha);
            case 2: return new Color(p, value, t,alpha);
            case 3: return new Color(p, q, value,alpha);
            case 4: return new Color(t, p, value,alpha);
            case 5: return new Color(value, p, q,alpha);
            default: throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + hue + ", " + saturation + ", " + value);
        }
    }

    /** converts RGB 0-1 to hex string e.g. FFFFFF
     * @param r red value 0-1
     * @param g green value 0-1
     * @param b blue value 0-1
     * @return RGB in HEX
     */
    public static String rgbToString(float r, float g, float b) {
        String rs = Integer.toHexString((int)(r * 256));
        String gs = Integer.toHexString((int)(g * 256));
        String bs = Integer.toHexString((int)(b * 256));
        return rs + gs + bs;
    }

    /**
     * Generates a random name using 2 text files in the assets folder
     * @return random name
     */
    public static String generateRandomName(){
        String name = "";
        if(fileHandleNames == null){
            FileHandle fileHandle = Gdx.files.internal("fname.txt");
            fileHandleNames = fileHandle.readString().split("\n");

            FileHandle fileHandle2 = Gdx.files.internal("lname.txt");
            fileHandleNames2 = fileHandle2.readString().split("\n");
        }
        int fileHandleNumber = (int)(Math.random() * fileHandleNames.length);
        name = fileHandleNames[fileHandleNumber].trim();

        int fileHandle2Number = (int)(Math.random() * fileHandleNames2.length);
        name += "_"+ fileHandleNames2[fileHandle2Number].trim();

        return name;
    }

    /**
     * Quick access to console logging
     * @param o
     */
    public static void log(Object o){
        System.out.println(o);
    }

    public static Texture makeTexture(int width, int height, String hex){
        if(hex.length() == 6 ){
            hex+="FF";
        }
        return makeTexture(width,height,Color.valueOf(hex));
    }


    public static TextureRegion makeTextureRegion(int width, int height, String hex){
        if(hex.length() == 6 ){
            hex+="FF";
        }
        return makeTextureRegion(width,height,Color.valueOf(hex));
    }

    public static TextureRegion makeTextureRegion(int width, int height, Color color){
        return new TextureRegion(makeTexture(width,height,color));
    }

    public static TextureRegion makeTextureRegion(float f, float g, String hex) {
        int fval = (int)f;
        int gval = (int)g;
        return makeTextureRegion(fval,gval,hex);
    }

    public static Texture makeTexture(int width, int height, Color color){
        Texture texture;
        texture = new Texture(makePixMap(width,height,color));
        disposePixMap();
        return texture;
    }

    private static Pixmap makePixMap(int width, int height, Color fill){
        pixmap = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        pixmap.setColor(fill);
        pixmap.fill();
        return pixmap;
    }

    private static void disposePixMap(){
        pixmap.dispose();
    }

}
