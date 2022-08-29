package com.blogspot.osamatech442.superball.overlays;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.components.Button;
import com.blogspot.osamatech442.superball.screens.AdvancedScreen;
import com.blogspot.osamatech442.superball.screens.MainScreen;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.ButtonInput;
import com.blogspot.osamatech442.superball.utils.Constants;

public class Overlay extends InputAdapter {

    private AdvancedScreen mainScreen;
    private Viewport viewport;
    private BitmapFont bitmapFont;
    private GlyphLayout textLayout;
    private Assets assets;

    private String title;
    private Rectangle overlayBounds;
    private Button closeButton;

    private Vector2 touchDownPos;

    private Array<Item> items;
    private Array<Button> icons;
    private Array<Text> texts;

    public Overlay(final MainScreen mainScreen, String title) {
        this.mainScreen = mainScreen;
        this.bitmapFont = mainScreen.bitmapFont;
        this.textLayout = mainScreen.textLayout;
        this.viewport = mainScreen.viewport;
        this.assets = mainScreen.assets;

        this.title = title;

        this.overlayBounds = new Rectangle(0, 0, Constants.OVERLAY_WIDTH, Constants.OVERLAY_HEIGHT);
        this.closeButton = new Button(
                assets.overlayAssets.overlayCloseIcon,
                Constants.OVERLAY_CLOSE_ICON_SIZE,
                Constants.ICON_INCREASE,
                new ButtonInput() {
                    @Override
                    public void onClicked() {
                        mainScreen.restoreScreen();
                    }
                }
        );

        this.touchDownPos = new Vector2();

        this.items = new Array<Item>();
        this.icons = new Array<Button>();
        this.texts = new Array<Text>();
    }

    public Item addItem(String name, TextureRegion image, ButtonInput buttonInput) {
        Item item = new Item(name, new Button(image, Constants.OVERLAY_ITEM_ICON_SIZE, Constants.ICON_INCREASE, buttonInput));
        items.add(item);
        return item;
    }

    public void addIcon(TextureRegion image, ButtonInput buttonInput) {
        icons.add(new Button(image, Constants.OVERLAY_ICON_SIZE, Constants.ICON_INCREASE, buttonInput));
    }

    public void addText(String name) {
        texts.add(new Text(name));
    }

    public void render(SpriteBatch batch) {
        Vector2 touchPos = viewport.unproject(new Vector2(Gdx.input.getX(), Gdx.input.getY()));
        //Background
        batch.draw(
                assets.overlayAssets.overlay,
                overlayBounds.x,
                overlayBounds.y,
                overlayBounds.width,
                overlayBounds.height
        );

        //Close Icon
        closeButton.render(batch, touchDownPos, touchPos, false);


        bitmapFont.getData().setScale(Constants.OVERLAY_SCALE);
        //Title
        bitmapFont.setColor(Constants.OVERLAY_TITLE_COLOR);
        textLayout.setText(bitmapFont, title);
        bitmapFont.draw(
                batch,
                textLayout,
                overlayBounds.x + Constants.OVERLAY_XMARGIN,
                closeButton.getBounds().y + textLayout.height + (Constants.OVERLAY_CLOSE_ICON_SIZE / 2 - textLayout.height / 2)
        );

        //Items
        bitmapFont.setColor(Constants.OVERLAY_TEXT_COLOR);
        for (Item item : items) {
            item.render(batch, touchDownPos, touchPos, false);
        }

        //Icons
        for (Button icon : icons) {
            icon.render(batch, touchDownPos, touchPos, false);
        }

        //Texts
        bitmapFont.setColor(Constants.OVERLAY_TEXT_COLOR);
        for (Text text : texts) {
            text.render(batch);
        }

    }

    public void recalculateButtonPositions() {
        overlayBounds.setPosition(
                viewport.getWorldWidth() / 2 - Constants.OVERLAY_WIDTH / 2,
                viewport.getWorldHeight() / 2 - Constants.OVERLAY_HEIGHT / 2
        );
        closeButton.recalculateButtonPosition(
                overlayBounds.x + overlayBounds.width - Constants.OVERLAY_XMARGIN - Constants.OVERLAY_CLOSE_ICON_SIZE,
                overlayBounds.y + overlayBounds.height - Constants.OVERLAY_CLOSE_ICON_SIZE - (Constants.OVERLAY_UPPER_HEIGHT - Constants.OVERLAY_CLOSE_ICON_SIZE) / 2
        );

        //Items
        float itemXPos = overlayBounds.x + overlayBounds.width - Constants.OVERLAY_XMARGIN - Constants.OVERLAY_ITEM_ICON_SIZE;
        float itemInitialYPos = overlayBounds.y + overlayBounds.height - Constants.OVERLAY_UPPER_HEIGHT - Constants.OVERLAY_ITEM_YMARGIN - Constants.OVERLAY_ITEM_ICON_SIZE;

        for (int i = 0; i < items.size; i++) {
            Item item = items.get(i);
            item.recalculateButtonPosition(itemXPos, itemInitialYPos - i * (Constants.OVERLAY_ITEM_YMARGIN + Constants.OVERLAY_ITEM_ICON_SIZE));
        }

        //Icons
        float n = icons.size;
        float margin = (overlayBounds.width - n * Constants.OVERLAY_ICON_SIZE) / (n + 1);
        float iconYPos = overlayBounds.y + Constants.OVERLAY_ICON_YMARGIN;
        float iconInitialXPos = overlayBounds.x + margin;
        for (int i = 0; i < n; i++) {
            Button icon = icons.get(i);
            icon.recalculateButtonPosition(iconInitialXPos + i * (margin + Constants.OVERLAY_ICON_SIZE), iconYPos);
        }

        //Texts
        float textInitialYPos = overlayBounds.y + overlayBounds.height - Constants.OVERLAY_UPPER_HEIGHT - Constants.OVERLAY_TEXT_YMARGIN;
        for (int i = 0;i < texts.size; i++) {
            Text text = texts.get(i);
            text.recalculateButtonPosition(overlayBounds.x + overlayBounds.width / 2 - text.bounds.width / 2, textInitialYPos - i * (Constants.OVERLAY_TEXT_YMARGIN + text.bounds.height));
        }

    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos.set(viewport.unproject(new Vector2(screenX, screenY)));
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));
        //handling items events
        Sound sound = assets.soundAssets.clickSound;
        for (Item item : items) {
            if (item.icon.onClicked(touchDownPos, touchUpPos, sound)) return true;
        }

        //handling icons events
        for (Button icon : icons) {
            if (icon.onClicked(touchDownPos, touchUpPos, sound)) return true;
        }

        //handling closing
        if (closeButton.onClicked(touchDownPos, touchUpPos, sound)) return true;

        if(!overlayBounds.contains(touchUpPos) && !overlayBounds.contains(touchDownPos)) {
            mainScreen.restoreScreen();
            return true;
        }

        return false;
    }

    public class Text {
        public String name;
        public Rectangle bounds;

        public Text(String name) {
            this.name = name;
            bitmapFont.getData().setScale(Constants.OVERLAY_SCALE);
            bitmapFont.setColor(Constants.OVERLAY_TEXT_COLOR);
            textLayout.setText(bitmapFont, name);
            this.bounds = new Rectangle(0, 0, textLayout.width, textLayout.height);
        }

        public void render(SpriteBatch batch) {
            textLayout.setText(bitmapFont, name);
            bitmapFont.draw(
                    batch,
                    textLayout,
                    bounds.x,
                    bounds.y
            );
        }

        public Rectangle getBounds() {
            return bounds;
        }

        public void recalculateButtonPosition(float x, float y) {
            this.bounds.setPosition(x, y);
        }

    }

    public class Item {

        public String name;
        public Button icon;

        public Item(String name, Button icon) {
            this.name = name;
            this.icon = icon;
        }

        public void render(SpriteBatch batch, Vector2 touchDownPos, Vector2 touchUpPos, boolean isPaused) {
            icon.render(batch, touchDownPos, touchDownPos, isPaused);
            textLayout.setText(bitmapFont, name);
            bitmapFont.draw(
                    batch,
                    textLayout,
                    overlayBounds.x + Constants.OVERLAY_XMARGIN,
                    icon.getBounds().y + textLayout.height / 2 + icon.getBounds().height / 2
            );
        }

        public void setButtonInput(ButtonInput buttonInput) {
            icon.setButtonInput(buttonInput);
        }

        public void recalculateButtonPosition(float x, float y) {
            icon.recalculateButtonPosition(x, y);
        }

    }

}
