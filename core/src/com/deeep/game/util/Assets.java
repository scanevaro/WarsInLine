package com.deeep.game.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Created with IntelliJ IDEA.
 * User: Elmar
 * Date: 9/29/13
 * Time: 10:17 AM
 */
public class Assets {

    /**
     * instance for singleton
     */
    private static Assets assets;
    /**
     * Just a check to be sure that the assets aren't loaded multiple times
     */
    private static boolean loaded = false;
    public Texture nonSolidTile, solidTile;
    public Texture tileOverlay;
    public Texture startNode, secondaryNode, initialNode, finalNode;
    /**
     * The atlases containing all the images
     */
    private TextureAtlas textureAtlas;
    /**
     * Logger instance
     */
//    private Logger logger = Logger.getInstance();

    /**
     * Standard font
     */
    private BitmapFont font;
    private BitmapFont.BitmapFontData bitmapFontData;

    /**
     * Find a use for this, if there is any TODO
     */
    public Assets() {
    }

    /**
     * Simple singleton
     *
     * @return assets instance
     */
    public static Assets getAssets() {
        if (assets == null) {
            assets = new Assets();
            assets.load();
        }
        return assets;
    }

    public TextureAtlas getTextureAtlas() {
        return textureAtlas;
    }


    /**
     * function to load everything. Nothing special. TODO add more resources here( sound music etc)
     */
    public void load() {
        if (!loaded) {
            bitmapFontData = new BitmapFont.BitmapFontData(Gdx.files.internal("font/font.fnt"), false);
            textureAtlas = new TextureAtlas(Gdx.files.internal("TextureAtlas.txt"));
            solidTile = new Texture(Gdx.files.internal("solidTile.png"));
            nonSolidTile = new Texture(Gdx.files.internal("nonSolidTile.png"));
            tileOverlay = new Texture(Gdx.files.internal("tileOverlay.png"));
            secondaryNode = new Texture(Gdx.files.internal("secondaryNode.png"));
            startNode = new Texture(Gdx.files.internal("startNode.png"));
            finalNode = new Texture(Gdx.files.internal("finalNode.png"));
            initialNode = new Texture(Gdx.files.internal("initialNode.png"));
            loadBitmapFont();
            loaded = true;
        }
    }

    /**
     * Dispose function. should ALWAYS be called. This will get rid of the texture atlas
     */
    public void dispose() {
        if (textureAtlas != null) textureAtlas.dispose();
//        logger.system(Assets.class, "All assets have been disposed");
    }

    /**
     * Returns an texture region based on the name given. Get images using this function!
     *
     * @param name see TextureAtlas.txt
     * @return the texture region connected to the name
     */
    public TextureRegion getRegion(String name) {
        TextureRegion textureRegion = textureAtlas.findRegion(name);
        if (textureRegion == null) {
//            logger.error(Assets.class, "Unkown texture requested: " + name);
        }
        return textureAtlas.findRegion(name);
    }

    /**
     * Loads the bitmap font as BitmapFont object
     *
     * @return null or the font
     */
    public BitmapFont loadBitmapFont() {
        Texture texture = new Texture(Gdx.files.internal("font/font.png"));

        BitmapFont font = new BitmapFont(Gdx.files.internal("font/font.fnt"), new TextureRegion(texture), false);
        if (font != null) return font;
        System.out.println("Couldn't find specified font!");
        return null;
    }

    /**
     * Returns the bitmap font as BitmapFont object
     *
     * @return null or the font
     */
    public BitmapFont getBitmapFont() {
        return font;
    }
}
