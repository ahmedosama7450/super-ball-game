package com.blogspot.osamatech442.superball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.entities.Ball;
import com.blogspot.osamatech442.superball.entities.Coin;
import com.blogspot.osamatech442.superball.entities.Key;
import com.blogspot.osamatech442.superball.entities.Platform;
import com.blogspot.osamatech442.superball.entities.RotationalBall;
import com.blogspot.osamatech442.superball.entities.Spike;
import com.blogspot.osamatech442.superball.overlays.AdRewardedVideoOverlay;
import com.blogspot.osamatech442.superball.overlays.Hud;
import com.blogspot.osamatech442.superball.overlays.PauseOverlay;
import com.blogspot.osamatech442.superball.utils.Assets;
import com.blogspot.osamatech442.superball.utils.Constants;
import com.blogspot.osamatech442.superball.utils.PlatformCreator;

public class PlayScreen implements AdvancedScreen {

    //Constants
    private static final int SPIKES_SHAPES_NUMBER = 4;
    private static final int ITEMS_NUMBER = 3;
    private static final float DEFAULT_YPOS = 400;
    private static final float DEFAULT_XPOS = -50;

    //Screen objects
    public SpriteBatch batch;
    public BitmapFont bitmapFont;
    public GlyphLayout textLayout;
    public Preferences preferences;
    public Assets assets;
    public SuperBallGame superBallGame;

    private OrthographicCamera camera;
    public Viewport viewport;
    public Viewport hudViewPort;

    //Input
    InputMultiplexer inputMultiplexer;

    //Entities
    Ball ball;
    private Array<Platform> platforms;
    private Platform currentPortal;
    private Array<Key> keys;
    private Array<Coin> coins;
    private Array<Spike> spikes;

    //Overlays
    private Hud hud;
    private PauseOverlay pauseOverlay;
    private AdRewardedVideoOverlay adRewardedVideoOverlay;

    //Spawning amounts
    private int keysPerPortal;
    private int spikesPerPortal;
    private int coinsPerPortal;

    //Camera tracking variables
    private float cameraSpeed;
    private float portalDis;
    private float keyDis;
    private float spikeDis;
    private float coinDis;

    //Obtained items
    private int virtualObtainedKeys;
    private int obtainedCoins;

    //Others
    private boolean isPaused;
    private boolean isAdOverlay;
    private boolean isFirstResized;

    //Tracking
    private float elapsedTime;
    private int lastSpikeChosen;

    private int currentSpawnIndex;
    private Vector2[] lastSpawnPositions;
    private float lastLeftWallSpikeYPos;
    private float lastRightWallSpikeYPos;
    private float halfPortalYPos;

    private boolean isAdWatched;

    private float initialYPos;

    //Properties
    private TextureAtlas.AtlasRegion platformRegion;
    private int platformIndex;

    private Color backgroundColor;
    private int backgroundIndex;

    //utils
    PlatformCreator platformCreator;

    public PlayScreen(SuperBallGame superBallGame) {
        //initialize screen objects
        this.superBallGame = superBallGame;
        this.batch = superBallGame.batch;
        this.bitmapFont = superBallGame.bitmapFont;
        this.textLayout = superBallGame.textLayout;
        this.preferences = superBallGame.preferences;
        this.assets = superBallGame.assets;

        this.camera = new OrthographicCamera();
        this.viewport = new ExtendViewport(Constants.PLAY_SCREEN_SIZE, Constants.PLAY_SCREEN_SIZE, camera);
        this.hudViewPort = new ExtendViewport(Constants.HUD_SIZE, Constants.HUD_SIZE);

        //Initializing camera tracking variables
        this.cameraSpeed = Constants.CAMERA_INITIAL_SPEED;
        this.portalDis = 0;
        this.keyDis = 0;
        this.spikeDis = 0;
        this.coinDis = 0;

        //Obtained items
        this.virtualObtainedKeys = 0;
        this.obtainedCoins = 0;

        //others
        this.isPaused = false;
        this.isFirstResized = true;

        //Spawning amounts
        this.keysPerPortal = Constants.INITIAL_KEYS_PER_PORTAL;
        this.spikesPerPortal = Constants.INITIAL_SPIKES_PER_PORTAL;
        this.coinsPerPortal = Constants.COINS_PER_PORTAL;

        //Tracking
        this.halfPortalYPos = DEFAULT_YPOS;
        this.elapsedTime = 0;
        this.lastSpikeChosen = 0;

        this.currentSpawnIndex = 0;
        this.lastSpawnPositions = new Vector2[]{
                new Vector2(DEFAULT_XPOS, DEFAULT_YPOS),
                new Vector2(DEFAULT_XPOS, DEFAULT_YPOS),
                new Vector2(DEFAULT_XPOS, DEFAULT_YPOS)
        };
        this.lastLeftWallSpikeYPos = DEFAULT_YPOS;
        this.lastRightWallSpikeYPos = DEFAULT_YPOS;

        this.isAdWatched = false;

        //Initialize entities
        platforms = new Array<Platform>();
        currentPortal = null;
        keys = new Array<Key>();
        spikes = new Array<Spike>();
        coins = new Array<Coin>();
        int ballNumber = preferences.getInteger(Constants.CURRENT_BALL_KEY, Constants.BALL_DEFAULT);
        switch (ballNumber) {
            case 2:
            case 4:
            case 7:
            case 8:
            case 12:
            case 13:
            case 14:
                //Normal Ball
                ball = new Ball(assets.ballAssets.balls[ballNumber], this);
            default:
                //Rotational Ball
                ball = new RotationalBall(assets.ballAssets.balls[ballNumber], this);
        }

        //initializing overlays
        pauseOverlay = new PauseOverlay(this);
        hud = new Hud(this);
        adRewardedVideoOverlay = new AdRewardedVideoOverlay(
                2, this,
                assets.overlayAssets.lifeRewardedVideoOverlay,
                assets.overlayAssets.overlayYesButton,
                assets.overlayAssets.overlayNoButton,
                hudViewPort
        );

        //Initializing utils
        platformCreator = new PlatformCreator(this);

        //setting the input processor
        inputMultiplexer = new InputMultiplexer(platformCreator, hud);
        Gdx.input.setInputProcessor(inputMultiplexer);

        //Getting the preferences
        platformIndex = preferences.getInteger(Constants.PLATFORM_IMAGE_KEY, Constants.DEFAULT_PLATFORM_INDEX);
        platformRegion = assets.platformAssets.platforms[platformIndex];

        backgroundIndex = SuperBallGame.backgroundIndex;
        backgroundColor = Color.valueOf(Constants.BACKGROUNDS_COLORS[backgroundIndex]);

    }

    @Override
    public void show() {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, isFirstResized);
        hudViewPort.update(width, height, true);
        resizeButtons();
        if (isFirstResized) {
            initialYPos = viewport.getWorldHeight() * Constants.BALL_INITIAL_YPOS_MULTIPLIER;
            ball.init(
                    viewport.getWorldWidth() / 2 - Constants.BALL_RADIUS,
                    initialYPos
            );
        }
        isFirstResized = false;
    }

    private void resizeButtons() {
        pauseOverlay.recalculateButtonPositions();
        hud.recalculateButtonPositions();
        adRewardedVideoOverlay.recalculateButtonBounds();
    }

    @Override
    public void render(float delta) {
        clearScreen();
        if (isAdOverlay) {
            renderAdScreen();
        } else {
            if (!isPaused) {
                updateScreen(delta);
            }
            renderScreen(delta);
        }
    }

    private void clearScreen() {
        Gdx.gl.glClearColor(backgroundColor.r, backgroundColor.g, backgroundColor.b, backgroundColor.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected void updateScreen(float delta) {
        //Checking for game over
        if (isBallKilled()) {
            killBall();
            return;
        }

        //Moving and tracking camera
        float movementAmount = cameraSpeed * delta;
        moveCamera(movementAmount);
        trackCamera(movementAmount);
        viewport.apply();
        hudViewPort.apply();

        //Clearing not visible platforms
        for (Platform platform : platforms) {
            if (platform.getBounds().y > camera.position.y + viewport.getWorldHeight() / 2 + 2 * Constants.BALL_RADIUS) {
                platforms.removeValue(platform, false);
            }
        }

        //ball
        ball.update(delta);

        //Spikes
        for (Spike spike : spikes) {
            //Cleaning up
            if (spike.getBounds().y > camera.position.y + viewport.getWorldHeight() / 2) {
                spikes.removeValue(spike, false);
                continue;
            }
            //Updating
            if (ball.getBounds().overlaps(spike.getRealBounds())) {
                killBall();
                return;
            }
            spike.setRotation(getSpikeRotation(elapsedTime));
        }

        //Coins
        for (Coin coin : coins) {
            //Cleaning up
            if (coin.getBounds().y > camera.position.y + viewport.getWorldHeight() / 2) {
                coins.removeValue(coin, false);
                continue;
            }
            //Updating
            if (ball.getBounds().overlaps(coin.getBounds())) {
                runCoinSound();
                coins.removeValue(coin, false);
                increaseCoins();
            }
        }

        //Keys
        for (Key key : keys) {
            //Cleaning Up
            if (key.getBounds().y > camera.position.y + viewport.getWorldHeight() / 2) {
                keys.removeValue(key, false);
                continue;
            }
            //Updating
            if (ball.getBounds().overlaps(key.getBounds())) {
                runKeySound();
                keys.removeValue(key, false);
                increaseKeys();
            }
        }

        //Spawning items
        spawnPortals();
        spawnSpikes();
        spawnKeys();
        spawnCoins();

        //tracking time
        elapsedTime += delta;
    }

    private float getSpikeRotation(float elapsedTime) {
        return ((elapsedTime / Constants.SPIKE_ROTATION_PERIODIC_TIME) % 1f) * 360f;
    }

    protected void renderScreen(float delta) {
        //rendering the game
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        ball.render(batch);
        for (Platform platform : platforms)
            platform.render(batch);
        for (Spike spike : spikes)
            spike.render(batch);
        for (Coin coin : coins)
            coin.render(batch);
        for (Key key : keys)
            key.render(batch);
        batch.end();

        //rendering the overlays
        batch.setProjectionMatrix(hudViewPort.getCamera().combined);
        batch.begin();
        hud.render(batch, bitmapFont, textLayout);
        if (isPaused) {
            pauseOverlay.render(batch, bitmapFont, textLayout, delta);
        }
        if (isAdOverlay) {
            adRewardedVideoOverlay.render(batch);
        }

        batch.end();

    }

    private void renderAdScreen() {
        //rendering the game
        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();
        for (Platform platform : platforms)
            platform.render(batch);
        for (Spike spike : spikes)
            spike.render(batch);
        for (Coin coin : coins)
            coin.render(batch);
        for (Key key : keys)
            key.render(batch);
        batch.end();

        //rendering the overlays
        batch.setProjectionMatrix(hudViewPort.getCamera().combined);
        batch.begin();
        hud.render(batch, bitmapFont, textLayout);
        adRewardedVideoOverlay.render(batch);
        batch.end();
    }

    private boolean isBallKilled() {
        return ball.getBounds().y + ball.getBounds().height < camera.position.y - viewport.getWorldHeight() / 2 || ball.getBounds().y > camera.position.y + viewport.getWorldHeight() / 2;
    }

    private void killBall() {
        if (!isAdWatched && superBallGame.actionResolver.hasRewardedVideo())
            setAdOverlay();
        else
            superBallGame.setScreen(new ResultScreen(superBallGame, getScore(), obtainedCoins));
    }

    private void moveCamera(float movementAmount) {
        camera.position.y -= movementAmount;
    }

    protected void trackCamera(float movementAmount) {
        portalDis += movementAmount;
        keyDis += movementAmount;
        spikeDis += movementAmount;
        coinDis += movementAmount;
    }

    private void spawnPortals() {
        if (portalDis >= Constants.PORTAL_CREATION_MULTIPLIER * viewport.getWorldHeight()) {
            platformCreator.addPortal(camera.position.y - viewport.getWorldHeight() / 2 - Constants.PLATFORM_THICKNESS);
            portalDis = 0;
        }
    }

    protected void spawnKeys() {
        if (keyDis >= getSpawnMultiplier(keysPerPortal) * viewport.getWorldHeight()) {
            float spawnXPos = getSpawnXPos();
            float spawnYPos = getSpawnYPos();
            keys.add(new Key(
                    assets.prizeAssets.blueKey,
                    spawnXPos,
                    spawnYPos
            ));
            keyDis = 0;
        }
    }

    protected void spawnCoins() {
        if (coinDis >= getSpawnMultiplier(coinsPerPortal) * viewport.getWorldHeight()) {
            float spawnXPos = getSpawnXPos();
            float spawnYPos = getSpawnYPos();
            coins.add(new Coin(
                    assets.prizeAssets.coin,
                    spawnXPos,
                    spawnYPos
            ));
            coinDis = 0;
        }
    }

    protected void spawnSpikes() {
        if (spikeDis >= getSpawnMultiplier(spikesPerPortal) * viewport.getWorldHeight()) {
            spawnRandomSpikeShape();
            spikeDis = 0;
        }
    }

    private boolean isSpawningSpikes = false;

    private void spawnRandomSpikeShape() {
        int currentSpikeChosen = MathUtils.random(1, 3);
        currentSpikeChosen = (currentSpikeChosen == lastSpikeChosen) ? getNext(currentSpikeChosen) : currentSpikeChosen;

        if (!isSpawningSpikes) {
            currentSpikeChosen = 4;
        }

        switch (currentSpikeChosen) {
            case 1:
                spawnWallSpike();
                isSpawningSpikes = false;
                break;
            case 2:
                spawnLeftWallSpike();
                isSpawningSpikes = false;
                break;
            case 3:
                spawnRightWallSpike();
                isSpawningSpikes = false;
                break;
            case 4:
                spawnSpike();
                isSpawningSpikes = true;
                break;
        }
        lastSpikeChosen = currentSpikeChosen;
    }

    private int getNext(int n) {
        return n < SPIKES_SHAPES_NUMBER ? n + 1 : SPIKES_SHAPES_NUMBER;
    }

    private void spawnSpike() {
        float spawnXPos = getSpawnXPos();
        float spawnYPos = getSpawnYPos();
        spikes.add(new Spike(
                assets.prizeAssets.spike,
                spawnXPos,
                spawnYPos
        ));
    }

    private void spawnWallSpike() {
        spawnLeftWallSpike();
        spawnRightWallSpike();
    }

    private void spawnLeftWallSpike() {
        float spawnYPos = getWallYPos();
        float platformXPos = -Constants.WALL_PLATFORM_PADDING;
        float spikeXPos = platformXPos + Constants.PLATFORM_SIZE - Constants.SPIKE_SIDE;

        platformCreator.addPlatform(platformXPos, spawnYPos + Constants.SPIKE_SIZE / 2 - Constants.PLATFORM_THICKNESS / 2);
        spikes.add(new Spike(assets.prizeAssets.spike, spikeXPos, spawnYPos));

        this.lastLeftWallSpikeYPos = spawnYPos;
    }

    private void spawnRightWallSpike() {
        float spawnYPos = getWallYPos();
        float platformXPos = viewport.getWorldWidth() - Constants.PLATFORM_SIZE + Constants.WALL_PLATFORM_PADDING;
        float spikeXPos = platformXPos - Constants.SPIKE_SIZE + Constants.SPIKE_SIDE;

        platformCreator.addPlatform(platformXPos, spawnYPos + Constants.SPIKE_SIZE / 2 - Constants.PLATFORM_THICKNESS / 2);
        spikes.add(new Spike(assets.prizeAssets.spike, spikeXPos, spawnYPos));

        this.lastRightWallSpikeYPos = spawnYPos;
    }

    private float getSpawnMultiplier(int n) {
        return Constants.PORTAL_CREATION_MULTIPLIER / n;
    }

    private boolean isInYRange(float y1, float y2) {
        return y1 + Constants.SPIKE_SIZE > y2 && y1 < y2 + Constants.SPIKE_SIZE;
    }

    private float getMinYPos(Vector2[] positions) {
        float min = positions[0].y;
        for (Vector2 position : positions) {
            float currentValue = position.y;
            if (currentValue < min) min = currentValue;
        }
        return min;
    }

    private float getWallYPos() {
        float spawnYPos = getSpawnYPos();
        for (Vector2 position : lastSpawnPositions) {
            if (isInYRange(spawnYPos, position.y)) {
                float minItemYPos = getMinYPos(lastSpawnPositions);
                spawnYPos = minItemYPos - Constants.ITEM_SIZE - Constants.ITEM_COLLISION_MARGIN;
            }
        }
        return getSpawnYPos();
    }

    private float getSpawnYPos() {
        float spawnYPos = camera.position.y - viewport.getWorldHeight() / 2 - Constants.ITEM_SIZE;

        //Checking collisions with the current portal and half portals
        if (currentPortal != null && isInYRange(spawnYPos, currentPortal.getBounds().y))
            spawnYPos = currentPortal.getBounds().y + Constants.PLATFORM_THICKNESS + (Constants.PORTAL_COIN_SIZE - Constants.PLATFORM_THICKNESS) / 2;
        else if (isInYRange(spawnYPos, halfPortalYPos))
            spawnYPos = halfPortalYPos - Constants.ITEM_SIZE - Constants.ITEM_COLLISION_MARGIN;

        //Checking collisions with wall spikes
        if (isInYRange(spawnYPos, lastLeftWallSpikeYPos))
            spawnYPos = lastLeftWallSpikeYPos - Constants.ITEM_SIZE - Constants.ITEM_COLLISION_MARGIN;
        else if (isInYRange(spawnYPos, lastRightWallSpikeYPos))
            spawnYPos = lastRightWallSpikeYPos - Constants.ITEM_SIZE - Constants.ITEM_COLLISION_MARGIN;

        return spawnYPos;
    }

    private float generateRandomPosition() {
        return MathUtils.random() * (viewport.getWorldWidth() - Constants.ITEM_SIZE);
    }

    private boolean isInXRange(float x1, float x2) {
        return x1 + Constants.ITEM_SIZE > x2 && x1 < x2 + Constants.ITEM_SIZE;
    }

    private boolean isOnLeft(float x, float anotherX) {
        return anotherX + Constants.ITEM_SIZE <= x;
    }

    private boolean isOutOfScreen(float x) {
        return x + Constants.ITEM_SIZE > viewport.getWorldWidth() || x < 0;
    }

    private float getSpawnXPos() {
        float spawnXPosition = generateRandomPosition();
        float firstItemXPos = 0;
        float secondItemXPos = 0;
        switch (currentSpawnIndex) {
            case 0:
                firstItemXPos = lastSpawnPositions[currentSpawnIndex + 1].x;
                secondItemXPos = lastSpawnPositions[currentSpawnIndex + 2].x;
                break;
            case 1:
                firstItemXPos = lastSpawnPositions[currentSpawnIndex - 1].x;
                secondItemXPos = lastSpawnPositions[currentSpawnIndex + 1].x;
                break;
            case 2:
                firstItemXPos = lastSpawnPositions[currentSpawnIndex - 1].x;
                secondItemXPos = lastSpawnPositions[currentSpawnIndex - 2].x;
                break;
        }

        if (isInXRange(spawnXPosition, firstItemXPos))
            if (isOnLeft(firstItemXPos, secondItemXPos)) {
                //SpawnOnRight
                spawnXPosition = firstItemXPos + Constants.ITEM_SIZE + Constants.ITEM_SPAWN_MARGIN;
                if (isOutOfScreen(spawnXPosition)) {
                    spawnXPosition = 0;
                    if (isInXRange(spawnXPosition, secondItemXPos))
                        spawnXPosition = secondItemXPos + Constants.ITEM_SIZE + Constants.ITEM_SPAWN_MARGIN;
                }
            } else {
                //SpawnOnLeft
                spawnXPosition = firstItemXPos - Constants.ITEM_SIZE - Constants.ITEM_SPAWN_MARGIN;
                if (isOutOfScreen(spawnXPosition)) {
                    spawnXPosition = viewport.getWorldWidth() - Constants.ITEM_SIZE;
                    if (isInXRange(spawnXPosition, secondItemXPos))
                        spawnXPosition = secondItemXPos - Constants.ITEM_SIZE - Constants.ITEM_SPAWN_MARGIN;
                }
            }

        lastSpawnPositions[currentSpawnIndex].x = spawnXPosition;
        currentSpawnIndex = currentSpawnIndex + 1 > ITEMS_NUMBER - 1 ? 0 : currentSpawnIndex + 1;

        return spawnXPosition;
    }

    private void changeColors() {
        changePlatformColor();
        //changeBackgroundColor();
    }

    private void changeBackgroundColor() {
        backgroundIndex = backgroundIndex == Constants.BACKGROUNDS_COUNT - 1 ? 0 : backgroundIndex + 1;
        backgroundColor = Color.valueOf(Constants.BACKGROUNDS_COLORS[backgroundIndex]);
    }

    private void changePlatformColor() {
        platformIndex = platformIndex == Constants.PLATFORMS_COUNT - 1 ? 0 : platformIndex + 1;
        platformRegion = assets.platformAssets.platforms[platformIndex];
    }

    public void onPortalCrossed() {
        //Running the sound
        runPortalSound();

        //adding the half portals
        platformCreator.addHalfPortals(currentPortal.getBounds().y, currentPortal);

        //rewarding the player
        virtualObtainedKeys = 0;
        obtainedCoins += Constants.PORTAL_COINS;
        changeColors();
        speedUp();
    }

    public int getScore() {
        return Math.round((initialYPos - ball.getBounds().y) / Constants.SCORE_MULTIPLIER);
    }

    private void increaseKeys() {
        virtualObtainedKeys++;
    }

    private void increaseCoins() {
        obtainedCoins++;
    }

    public int getVirtualObtainedKeys() {
        return virtualObtainedKeys;
    }

    public TextureAtlas.AtlasRegion getPlatformRegion() {
        return platformRegion;
    }

    public Ball getBall() {
        return ball;
    }

    public Platform getCurrentPortal() {
        return currentPortal;
    }

    public void setCurrentPortal(Platform currentPortal) {
        this.currentPortal = currentPortal;
    }

    public Array<Platform> getPlatforms() {
        return platforms;
    }

    public int getObtainedCoins() {
        return obtainedCoins;
    }

    public void setHalfPortalYPos(float halfPortalYPos) {
        this.halfPortalYPos = halfPortalYPos;
    }

    private void speedUp() {
        speedUpCamera();
        ball.speedMeUp();
        adjustKeysSpikes();
    }

    private void adjustKeysSpikes() {
        if (keysPerPortal - 1 >= Constants.MIN_KEYS_PER_PORTAL)
            keysPerPortal--;
        if (spikesPerPortal + 1 <= Constants.MAX_SPIKES_PER_PORTAL)
            spikesPerPortal += Constants.SPIKES_INCREASE_PER_PORTAL;
    }

    private void speedUpCamera() {
        float newCameraSpeed = cameraSpeed * Constants.SPEED_INCREMENT_MULTIPLIER;
        cameraSpeed = newCameraSpeed <= Constants.CAMERA_MAX_SPEED ? newCameraSpeed : Constants.CAMERA_MAX_SPEED;
    }

    @Override
    public void pause() {
        setPauseOverlay();
    }

    @Override
    public void hide() {
    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    public void setPauseOverlay() {
        if (!isAdOverlay) {
            assets.soundAssets.backgroundMusic.stop();
            isPaused = true;
            setPauseOverlayInput();
        }
    }

    private void setAdOverlay() {
        isAdOverlay = true;
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(adRewardedVideoOverlay);
    }

    @Override
    public void restoreScreen() {
        isAdOverlay = false;
        isPaused = false;
        setGameInput();
    }

    private void setPauseOverlayInput() {
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(pauseOverlay);
    }

    void setGameInput() {
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(platformCreator);
        inputMultiplexer.addProcessor(hud);
    }

    private void runCoinSound() {
        if (SuperBallGame.isSoundOn) {
            assets.soundAssets.coinSound.play();
        }
    }

    private void runKeySound() {
        if (SuperBallGame.isSoundOn) {
            assets.soundAssets.keySound.play(0.5f);
        }
    }

    private void runPortalSound() {
        if (SuperBallGame.isSoundOn) {
            assets.soundAssets.portalSound.play();
        }
    }

    public void prepareForNewLife() {
        restoreScreen();

        isAdWatched = true;

        spikes.clear();
        platforms.clear();
        coins.clear();
        keys.clear();
        currentPortal = null;

        portalDis = 0;
        keyDis = 0;
        spikeDis = 0;
        coinDis = 0;

        virtualObtainedKeys = Constants.NEEDED_KEYS;

        lastSpikeChosen = 0;
        elapsedTime = 0;

        currentSpawnIndex = 0;
        lastLeftWallSpikeYPos = DEFAULT_YPOS;
        lastRightWallSpikeYPos = DEFAULT_YPOS;
        halfPortalYPos = DEFAULT_YPOS;

        for (Vector2 position : lastSpawnPositions) {
            position.set(DEFAULT_XPOS, DEFAULT_YPOS);
        }

        ball.prepareBall(viewport);

        camera.position.y = ball.getBounds().y - viewport.getWorldHeight() / 4;
    }

}