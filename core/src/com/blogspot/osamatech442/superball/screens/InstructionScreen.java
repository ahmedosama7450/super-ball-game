package com.blogspot.osamatech442.superball.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.blogspot.osamatech442.superball.SuperBallGame;
import com.blogspot.osamatech442.superball.components.InstructionRectangle;
import com.blogspot.osamatech442.superball.utils.Constants;

public class InstructionScreen extends PlayScreen implements InputProcessor {

    //Instruction rectangles
    private InstructionRectangle tapInstruction;
    private InstructionRectangle slideLeftInstruction;
    private InstructionRectangle slideRightInstruction;
    private InstructionRectangle collectKeysInstruction;
    private InstructionRectangle avoidSpikesInstruction;
    private InstructionRectangle instructionsNote;
    private InstructionRectangle tapNote;

    //Instruction Square
    private Rectangle instructionSquare;
    private Rectangle pointerBounds;

    //Tracking time
    private float elapsedTime;
    private float animationElapsedTime;
    private float freezeTime1;
    private float freezeTime2;
    private float freezeTime3;

    // others
    private boolean updating;
    private boolean firstPartElapsed;
    private boolean secondPartElapsed;
    private boolean thirdPartElapsed;
    private boolean forthPartElapsed;
    private boolean fifthPartElapsed;
    private boolean isNormalGame;

    //Positioning
    private Vector2 touchDownPos;
    private float tapPos;
    private boolean isDragged;
    private boolean isOnRightPos;

    //Instruction square
    private int instructionNumber;

    public InstructionScreen(SuperBallGame superBallGame) {
        super(superBallGame);

        //Instruction Rectangles
        tapInstruction = new InstructionRectangle(
                assets.instructionAssets.tapInstruction,
                Constants.INSTRUCTION_RECTANGLE_WIDTH,
                Constants.INSTRUCTION_RECTANGLE_HEIGHT
        );
        slideLeftInstruction = new InstructionRectangle(
                assets.instructionAssets.slideLeftInstruction,
                Constants.INSTRUCTION_RECTANGLE_WIDTH,
                Constants.INSTRUCTION_RECTANGLE_HEIGHT
        );

        slideRightInstruction = new InstructionRectangle(
                assets.instructionAssets.slideRightInstruction,
                Constants.INSTRUCTION_RECTANGLE_WIDTH,
                Constants.INSTRUCTION_RECTANGLE_HEIGHT
        );

        collectKeysInstruction = new InstructionRectangle(
                assets.instructionAssets.collectKeysInstruction,
                Constants.FINAL_INSTRUCTION_RECTANGLE_WIDTH,
                Constants.FINAL_INSTRUCTION_RECTANGLE_HEIGHT
        );

        avoidSpikesInstruction = new InstructionRectangle(
                assets.instructionAssets.avoidSpikesInstruction,
                Constants.FINAL_INSTRUCTION_RECTANGLE_WIDTH,
                Constants.FINAL_INSTRUCTION_RECTANGLE_HEIGHT
        );

        instructionsNote = new InstructionRectangle(
                assets.instructionAssets.instructionsNote,
                Constants.NOTE_INSTRUCTION_WIDTH,
                Constants.NOTE_INSTRUCTION_HEIGHT
        );

        tapNote = new InstructionRectangle(
                assets.instructionAssets.tapNote,
                Constants.NOTE_INSTRUCTION_WIDTH,
                Constants.NOTE_INSTRUCTION_HEIGHT
        );

        instructionSquare = new Rectangle(0, 0, Constants.INSTRUCTION_SQUARE_WIDTH, Constants.INSTRUCTION_SQUARE_HEIGHT);

        pointerBounds = new Rectangle(0, 0, Constants.POINTER_SIZE, Constants.POINTER_SIZE);

        this.elapsedTime = 0;
        this.animationElapsedTime = 0;
        this.freezeTime1 = 0;
        this.freezeTime2 = 0;
        this.freezeTime3 = 0;

        this.updating = true;
        this.firstPartElapsed = false;
        this.secondPartElapsed = false;
        this.thirdPartElapsed = false;
        this.forthPartElapsed = false;
        this.fifthPartElapsed = false;
        this.isNormalGame = false;

        this.isDragged = false;
        this.touchDownPos = new Vector2();
        this.isOnRightPos = false;
        this.tapPos = 0;

        this.instructionNumber = 1;

        inputMultiplexer.clear();
    }

    @Override
    protected void updateScreen(float delta) {
        if (elapsedTime >= Constants.INSTRUCTION_START_TIME && !firstPartElapsed) {
            stopUpdating();
            setInstructionRectangle(tapInstruction, Constants.TAP_INSTRUCTION_TRIANGLE_LOCATION, 0);
            animationElapsedTime = 0;
            firstPartElapsed = true;
        } else if (freezeTime1 > 0 && elapsedTime >= freezeTime1 && !secondPartElapsed) {
            stopUpdating();
            setInstructionRectangle(slideLeftInstruction, Constants.SLIDE_LEFT_INSTRUCTION_TRIANGLE_LOCATION, Constants.INSTRUCTION_DISPLACEMENT);
            animationElapsedTime = 0;
            secondPartElapsed = true;
        } else if (freezeTime2 > 0 && elapsedTime >= freezeTime2 && !thirdPartElapsed) {
            stopUpdating();
            setInstructionRectangle(slideRightInstruction, Constants.SLIDE_RIGHT_INSTRUCTION_TRIANGLE_LOCATION, -Constants.INSTRUCTION_DISPLACEMENT);
            animationElapsedTime = 0;
            thirdPartElapsed = true;
        } else if (freezeTime3 > 0 && elapsedTime >= freezeTime3 && !forthPartElapsed) {
            stopUpdating();
            collectKeysInstruction.setPosition(
                    viewport.getWorldWidth() / 2 - Constants.FINAL_INSTRUCTION_RECTANGLE_WIDTH / 2,
                    ball.getBounds().y - Constants.FINAL_INSTRUCTION_RECTANGLE_HEIGHT - Constants.FINAL_INSTRUCTION_RECTANGLE_MARGIN
            );
            tapNote.setPosition(0, viewport.getCamera().position.y - viewport.getWorldHeight() / 2);
            animationElapsedTime = 0;
            forthPartElapsed = true;
        } else if (updating) {
            super.updateScreen(delta);
            elapsedTime += delta;
        }

        animationElapsedTime += delta;
    }

    @Override
    protected void renderScreen(float delta) {
        super.renderScreen(delta);
        if (!updating) {
            batch.setProjectionMatrix(viewport.getCamera().combined);
            batch.begin();
            if (firstPartElapsed && !secondPartElapsed) {
                //learn to create simple platform
                drawStandingPointer();
                tapInstruction.render(batch);
                instructionsNote.render(batch);
            } else if (secondPartElapsed && !thirdPartElapsed) {
                //learn to create right-flagged platform
                drawPointer(-1, delta);
                slideLeftInstruction.render(batch);
                instructionsNote.render(batch);
            } else if (thirdPartElapsed && !forthPartElapsed) {
                //learn to create left-flagged platform
                drawPointer(1, delta);
                slideRightInstruction.render(batch);
                instructionsNote.render(batch);
            } else if (forthPartElapsed && !fifthPartElapsed) {
                //learn to collect keys
                collectKeysInstruction.render(batch);
                tapNote.render(batch);
            } else if (fifthPartElapsed && !isNormalGame) {
                //Learn to avoid spikes
                collectKeysInstruction.render(batch);
                avoidSpikesInstruction.render(batch);
                tapNote.render(batch);
            }
            batch.end();
        }

    }

    private void drawStandingPointer() {
        batch.draw(
                assets.instructionAssets.tapAnimation.getKeyFrame(animationElapsedTime, true),
                pointerBounds.x,
                pointerBounds.y,
                pointerBounds.width,
                pointerBounds.height
        );
    }

    private void drawPointer(int velocitySign, float delta) {
        TextureRegion region;
        float n = (animationElapsedTime / Constants.SLIDE_ANIMATION_DURATION) % 1; // between 0, 1
        if (n <= Constants.TAP_ANIMATION_DURATION) {
            tapPos = pointerBounds.x;
            if (n >= Constants.TAP_ANIMATION_DURATION / 2)
                region = assets.instructionAssets.tap;
            else
                region = assets.instructionAssets.tapTick;
        } else {
            tapPos += velocitySign * Constants.TAP_VELOCITY * delta;
            region = assets.instructionAssets.tapTick;
        }

        batch.draw(
                region,
                tapPos,
                pointerBounds.y,
                Constants.POINTER_SIZE,
                Constants.POINTER_SIZE
        );
    }

    private void setInstructionRectangle(InstructionRectangle instructionRectangle, float triangleLocation, float squareDisplacement) {
        instructionSquare.setPosition(
                ball.getBounds().x - Constants.INSTRUCTION_SQUARE_WIDTH / 2 + Constants.BALL_RADIUS + squareDisplacement,
                ball.getBounds().y - Constants.INSTRUCTION_SQUARE_HEIGHT - Constants.INSTRUCTION_SQUARE_BALL_MARGIN
        );
        instructionRectangle.setPosition(
                instructionSquare.x - triangleLocation + instructionSquare.width / 2,
                instructionSquare.y - instructionRectangle.bounds.height - Constants.INSTRUCTION_SQUARE_RECTANGLE_MARGIN
        );
        pointerBounds.setPosition(
                instructionSquare.x + instructionSquare.width / 2 - Constants.POINTER_SIZE / 2,
                instructionSquare.y + instructionSquare.height / 2 - Constants.POINTER_SIZE / 2
        );
        instructionsNote.setPosition(0, viewport.getCamera().position.y - viewport.getWorldHeight() / 2);
    }

    private void setInstructionInput() {
        inputMultiplexer.clear();
        inputMultiplexer.addProcessor(this);
    }

    private void startUpdating() {
        inputMultiplexer.clear();
        updating = true;
    }

    private void stopUpdating() {
        setInstructionInput();
        updating = false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        touchDownPos = viewport.unproject(new Vector2(screenX, screenY));
        if (instructionNumber == 2 || instructionNumber == 3) {
            isOnRightPos = instructionSquare.contains(touchDownPos.x, touchDownPos.y);
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        Vector2 touchUpPos = viewport.unproject(new Vector2(screenX, screenY));

        if (instructionNumber == 1 && !isDragged && instructionSquare.contains(touchUpPos.x, touchUpPos.y)) {
            //
            freezeTime1 = elapsedTime +
                    getTimeAcceleratedDistance(ball.getBounds().y - touchUpPos.y - Constants.PLATFORM_THICKNESS / 2 - Constants.BALL_RADIUS) +
                    getTimeConstantDistance(Constants.BALL_RADIUS +Constants.PLATFORM_SIZE / 2) +
                    Constants.BALL_BOUNCING_TIME + Constants.EXTRA_TIME;
            platformCreator.addPlatform(
                    ball.getBounds().x + Constants.BALL_RADIUS - Constants.PLATFORM_SIZE / 2,
                    touchUpPos.y - Constants.PLATFORM_THICKNESS / 2
            );
            startUpdating();
            instructionNumber++;
        } else if (instructionNumber == 4) {
            fifthPartElapsed = true;
            avoidSpikesInstruction.setPosition(
                    collectKeysInstruction.bounds.x,
                    collectKeysInstruction.bounds.y - Constants.FINAL_INSTRUCTION_RECTANGLE_HEIGHT - Constants.FINAL_INSTRUCTION_RECTANGLE_MARGIN / 2
            );
            tapNote.setPosition(0, viewport.getCamera().position.y - viewport.getWorldHeight() / 2);
            instructionNumber++;
        } else if (instructionNumber == 5) {
            setGameInput();
            updating = true;
            isNormalGame = true;
            showOnce();
        }

        isDragged = false;
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (!isOnRightPos) return false;

        Vector2 touchDraggedPos = viewport.unproject(new Vector2(screenX, screenY));

        if (instructionNumber == 2 && touchDownPos.x > touchDraggedPos.x) {
            //right
            float platformXPos = ball.getBounds().x + Constants.BALL_RADIUS - Constants.PLATFORM_SIZE / 2;
            float platformYPos = touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2;
            freezeTime2 =
                    elapsedTime +
                            getTimeAcceleratedDistance(ball.getBounds().y - touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2 - Constants.BALL_RADIUS) +
                            getTimeConstantDistance(ball.getBounds().x + Constants.BALL_RADIUS + 3 * Constants.PLATFORM_SIZE / 2 - 2 * Constants.PLATFORM_THICKNESS - 2 * Constants.BALL_RADIUS - ball.getBounds().x) +
                            Constants.BALL_BOUNCING_TIME + Constants.EXTRA_TIME;
            platformCreator.addFlaggedPlatform(
                    platformXPos,
                    platformYPos,
                    false
            );
            startUpdating();
            instructionNumber++;
        } else if (instructionNumber == 3 && touchDownPos.x < touchDraggedPos.x) {
            //left
            freezeTime3 =
                    elapsedTime +
                            getTimeAcceleratedDistance(ball.getBounds().y - touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2 - Constants.BALL_RADIUS) +
                            getTimeConstantDistance(ball.getBounds().x - touchDraggedPos.x + 3 * Constants.PLATFORM_SIZE / 2 - 2 * Constants.PLATFORM_THICKNESS) +
                            Constants.BALL_BOUNCING_TIME + Constants.EXTRA_TIME;
            platformCreator.addFlaggedPlatform(
                    ball.getBounds().x + Constants.BALL_RADIUS - Constants.PLATFORM_SIZE / 2,
                    touchDraggedPos.y - Constants.PLATFORM_THICKNESS / 2,
                    true
            );
            startUpdating();
            instructionNumber++;
        }
        isOnRightPos = false;
        isDragged = true;
        return false;
    }


    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    private float getTimeAcceleratedDistance(float dis) {
        float t1 = (-ball.getVelocity().y +
                (float) Math.sqrt(ball.getVelocity().y * ball.getVelocity().y + 2 * Constants.BALL_INITIAL_YACCELERATION * dis)) / Constants.BALL_INITIAL_YACCELERATION;
        float t2 = (-ball.getVelocity().y -
                (float) Math.sqrt(ball.getVelocity().y * ball.getVelocity().y + 2 * Constants.BALL_INITIAL_YACCELERATION * dis)) / Constants.BALL_INITIAL_YACCELERATION;
        return t1 >= 0 ? t1 : t2;
    }

    private float getTimeConstantDistance(float dis) {
        return dis / Constants.BALL_CONSTANT_XVELOCITY;
    }

    private void showOnce() {
        ((SuperBallGame) Gdx.app.getApplicationListener()).
                preferences.putBoolean(Constants.SHOWS_INSTRUCTIONS_KEY, false).flush();
    }

    @Override
    protected void spawnKeys() {
        if (isNormalGame) {
            super.spawnKeys();
        }
    }

    @Override
    protected void spawnSpikes() {
        if (isNormalGame) {
            super.spawnSpikes();
        }
    }

    @Override
    protected void spawnCoins() {
        if (isNormalGame) {
            super.spawnCoins();
        }
    }

    @Override
    protected void trackCamera(float amount) {
        if (isNormalGame) {
            super.trackCamera(amount);
        }
    }

    @Override
    public void pause() {

    }
}
