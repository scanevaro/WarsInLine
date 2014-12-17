package com.deeep.game.classes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g3d.*;
import com.badlogic.gdx.graphics.g3d.attributes.BlendingAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalShadowLight;
import com.badlogic.gdx.graphics.g3d.model.Animation;
import com.badlogic.gdx.graphics.g3d.utils.*;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.viewport.ScalingViewport;

/**
 * Created by scanevaro on 13/12/2014.
 */
public class World extends Actor implements InputProcessor, Disposable {

    public final static int PREF_HUDWIDTH = 640;
    public final static int PREF_HUDHEIGHT = 480;
    public final static float rotationSpeed = 0.02f * 360f; // degrees per second
    public final static float moveSpeed = 0.25f; // cycles per second

    ModelInstance skydome;
    Model floorModel;
    ModelInstance character;
    ModelInstance tree;
    AnimationController animation;
    DirectionalShadowLight shadowLight;
    ModelBatch shadowBatch;

    Environment lights;

    protected Stage hud;
    protected float hudWidth, hudHeight;
    protected Skin skin;
    protected Label fpsLabel;
    protected CollapsableWindow modelsWindow;
    protected CheckBox gridCheckBox, rotateCheckBox, moveCheckBox;
    protected final StringBuilder stringBuilder = new StringBuilder();
    protected final Matrix4 transform = new Matrix4();
    protected float moveRadius = 2f;

    protected String models[] = new String[]{"car.obj", "cube.obj", "scene.obj", "scene2.obj", "wheel.obj", "g3d/invaders.g3dj",
            "g3d/head.g3db", "g3d/knight.g3dj", "g3d/knight.g3db", "g3d/monkey.g3db", "g3d/ship.obj", "g3d/shapes/cube_1.0x1.0.g3dj",
            "g3d/shapes/cube_1.5x1.5.g3dj", "g3d/shapes/sphere.g3dj", "g3d/shapes/teapot.g3dj", "g3d/shapes/torus.g3dj"};

    public AssetManager assets;

    public PerspectiveCamera cam;
    public CameraInputController inputController;
    public ModelBatch modelBatch;
    public Model axesModel;
    public ModelInstance axesInstance;
    public boolean showAxes = true;
    public Array<ModelInstance> instances = new Array<ModelInstance>();
    public final Color bgColor = new Color(0, 0, 0, 1);

    protected boolean loading = false;

    final AnimationController.Transform trTmp = new AnimationController.Transform();
    final AnimationController.Transform trForward = new AnimationController.Transform();
    final AnimationController.Transform trBackward = new AnimationController.Transform();
    final AnimationController.Transform trRight = new AnimationController.Transform();
    final AnimationController.Transform trLeft = new AnimationController.Transform();
    final Matrix4 tmpMatrix = new Matrix4();
    final Vector3 tmpVector = new Vector3();
    int status = 0;
    final static int idle = 1;
    final static int walk = 2;
    final static int back = 3;
    final static int attack = 4;
    float angle = 0f;

    final float GRID_MIN = -10f;
    final float GRID_MAX = 10f;
    final float GRID_STEP = 1f;

    protected float rotation, movement;

    public World() {
        addListener(clickListener);

        createG3DTest();

        createHUD();

        Gdx.input.setInputProcessor(new InputMultiplexer(this, hud, inputController));

        lights = new Environment();
        lights.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.4f, 0.4f, 0.4f, 1.f));
        lights.add((shadowLight = new DirectionalShadowLight(1024, 1024, 30f, 30f, 1f, 100f))
                .set(0.8f, 0.8f, 0.8f, -.4f, -.4f, -.4f));
        lights.shadowMap = shadowLight;
        inputController.rotateLeftKey = inputController.rotateRightKey = inputController.forwardKey = inputController.backwardKey = 0;
        cam.position.set(25, 25, 25);
        cam.lookAt(0, 0, 0);
        cam.update();
        modelsWindow.setVisible(false);
        assets.load("data/g3d/skydome.g3db", Model.class);
        assets.load("data/g3d/concrete.png", Texture.class);
        assets.load("data/tree.png", Texture.class);
        loading = true;
        trForward.translation.set(0, 0, 8f);
        trBackward.translation.set(0, 0, -8f);
        trLeft.rotation.setFromAxis(Vector3.Y, 90);
        trRight.rotation.setFromAxis(Vector3.Y, -90);

        ModelBuilder builder = new ModelBuilder();
        builder.begin();
        builder.node().id = "floor";
        MeshPartBuilder part = builder.part("floor", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal,
                new Material("concrete"));
        ((MeshBuilder) part).ensureRectangles(1600);
        for (float x = -200f; x < 200f; x += 10f) {
            for (float z = -200f; z < 200f; z += 10f) {
                part.rect(x, 0, z + 10f, x + 10f, 0, z + 10f, x + 10f, 0, z, x, 0, z, 0, 1, 0);
            }
        }
        builder.node().id = "tree";
        part = builder.part("tree", GL20.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.TextureCoordinates | VertexAttributes.Usage.Normal,
                new Material("tree"));
        part.rect(0f, 0f, -10f, 10f, 0f, -10f, 10f, 10f, -10f, 0f, 10f, -10f, 0, 0, 1f);
        part.setUVRange(1, 0, 0, 1);
        part.rect(10f, 0f, -10f, 0f, 0f, -10f, 0f, 10f, -10f, 10f, 10f, -10f, 0, 0, -1f);
        floorModel = builder.end();

        shadowBatch = new ModelBatch(new DepthShaderProvider());
    }

    private void createG3DTest() {
        if (assets == null) assets = new AssetManager();

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(10f, 10f, 10f);
        cam.lookAt(0, 0, 0);
        cam.near = 0.1f;
        cam.far = 1000f;
        cam.update();

        //createAxes
        {
            ModelBuilder modelBuilder = new ModelBuilder();
            modelBuilder.begin();
            MeshPartBuilder builder = modelBuilder.part("grid", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Color, new Material());
            builder.setColor(Color.LIGHT_GRAY);
            for (float t = GRID_MIN; t <= GRID_MAX; t += GRID_STEP) {
                builder.line(t, 0, GRID_MIN, t, 0, GRID_MAX);
                builder.line(GRID_MIN, 0, t, GRID_MAX, 0, t);
            }
            builder = modelBuilder.part("axes", GL20.GL_LINES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Color, new Material());
            builder.setColor(Color.RED);
            builder.line(0, 0, 0, 100, 0, 0);
            builder.setColor(Color.GREEN);
            builder.line(0, 0, 0, 0, 100, 0);
            builder.setColor(Color.BLUE);
            builder.line(0, 0, 0, 0, 0, 100);
            axesModel = modelBuilder.end();
            axesInstance = new ModelInstance(axesModel);

        }

        Gdx.input.setInputProcessor(inputController = new CameraInputController(cam));
    }

    private void createHUD() {
        hud = new Stage(new ScalingViewport(Scaling.fit, PREF_HUDWIDTH, PREF_HUDHEIGHT));
        hudWidth = hud.getWidth();
        hudHeight = hud.getHeight();
        skin = new Skin(Gdx.files.internal("data/uiskin.json"));

        final List<String> modelsList = new List(skin);
        modelsList.setItems(models);
        modelsList.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!modelsWindow.isCollapsed() && getTapCount() == 2) {
                    onModelClicked(modelsList.getSelected());
                    modelsWindow.collapse();
                }
            }
        });
        modelsWindow = addListWindow("Models", modelsList, 0, -1);

        fpsLabel = new Label("FPS: 999", skin);
        hud.addActor(fpsLabel);
        gridCheckBox = new CheckBox("Show grid", skin);
        gridCheckBox.setChecked(showAxes);
        gridCheckBox.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                showAxes = gridCheckBox.isChecked();
            }
        });
        gridCheckBox.setPosition(hudWidth - gridCheckBox.getWidth(), 0);
        hud.addActor(gridCheckBox);

        rotateCheckBox = new CheckBox("Rotate", skin);
        rotateCheckBox.setChecked(true);
        rotateCheckBox.setPosition(hudWidth - rotateCheckBox.getWidth(), gridCheckBox.getHeight());
        hud.addActor(rotateCheckBox);

        moveCheckBox = new CheckBox("Move", skin);
        moveCheckBox.setChecked(false);
        moveCheckBox.setPosition(hudWidth - moveCheckBox.getWidth(), rotateCheckBox.getTop());
        hud.addActor(moveCheckBox);
    }

    private ClickListener clickListener = new ClickListener() {
        @Override
        public void clicked(InputEvent event, float x, float y) {

        }
    };

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = new Color(getColor().r, getColor().g,
                getColor().b, getColor().a * parentAlpha);

        batch.setColor(color);
        render();
    }

    private void render() {
        if (character != null) {
            animation.update(Gdx.graphics.getDeltaTime());
            if (upKey) {
                if (!animation.inAction) {
                    trTmp.idt().lerp(trForward, Gdx.graphics.getDeltaTime() / animation.current.animation.duration);
                    character.transform.mul(trTmp.toMatrix4(tmpMatrix));
                }
                if (status != walk) {
                    animation.animate("Walk", -1, 1f, null, 0.2f);
                    status = walk;
                }
            } else if (downKey) {
                if (!animation.inAction) {
                    trTmp.idt().lerp(trBackward, Gdx.graphics.getDeltaTime() / animation.current.animation.duration);
                    character.transform.mul(trTmp.toMatrix4(tmpMatrix));
                }
                if (status != back) {
                    animation.animate("Walk", -1, -1f, null, 0.2f);
                    status = back;
                }
            } else if (status != idle) {
                animation.animate("Idle", -1, 1f, null, 0.2f);
                status = idle;
            }
            if (rightKey && (status == walk || status == back) && !animation.inAction) {
                trTmp.idt().lerp(trRight, Gdx.graphics.getDeltaTime() / animation.current.animation.duration);
                character.transform.mul(trTmp.toMatrix4(tmpMatrix));
            } else if (leftKey && (status == walk || status == back) && !animation.inAction) {
                trTmp.idt().lerp(trLeft, Gdx.graphics.getDeltaTime() / animation.current.animation.duration);
                character.transform.mul(trTmp.toMatrix4(tmpMatrix));
            }
            if (spaceKey && !animation.inAction) {
                animation.action("Attack", 1, 1f, null, 0.2f);
            }
        }

        if (character != null) {
            shadowLight.begin(character.transform.getTranslation(tmpVector), cam.direction);
            shadowBatch.begin(shadowLight.getCamera());
            if (character != null) shadowBatch.render(character);
            if (tree != null) shadowBatch.render(tree);
            shadowBatch.end();
            shadowLight.end();
        }

        {/***/
            transform.idt();
            if (rotateCheckBox.isChecked())
                transform.rotate(Vector3.Y, rotation = (rotation + rotationSpeed * Gdx.graphics.getRawDeltaTime()) % 360);
            if (moveCheckBox.isChecked()) {
                movement = (movement + moveSpeed * Gdx.graphics.getRawDeltaTime()) % 1f;
                final float sm = MathUtils.sin(movement * MathUtils.PI2);
                final float cm = MathUtils.cos(movement * MathUtils.PI2);
                transform.trn(0, moveRadius * cm, moveRadius * sm);
            }

            {/***/
                if (loading && assets.update()) {
                    loading = false;
                    onLoaded();
                }

                inputController.update();

                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
                Gdx.gl.glClearColor(bgColor.r, bgColor.g, bgColor.b, bgColor.a);

                render(instances);
            }

            stringBuilder.setLength(0);
            getStatus(stringBuilder);
            fpsLabel.setText(stringBuilder);
            hud.act(Gdx.graphics.getDeltaTime());
            hud.draw();
        }
    }

    public void render(final Array<ModelInstance> instances) {
        modelBatch.begin(cam);
        if (showAxes) modelBatch.render(axesInstance);
        if (instances != null) render(modelBatch, instances);
        modelBatch.end();
    }

    protected void render(ModelBatch batch, Array<ModelInstance> instances) {
        batch.render(instances, lights);
        if (skydome != null) batch.render(skydome);
    }

    protected void onLoaded() {
        if (skydome == null) {
            skydome = new ModelInstance(assets.get("data/g3d/skydome.g3db", Model.class));
            floorModel.getMaterial("concrete").set(TextureAttribute.createDiffuse(assets.get("data/g3d/concrete.png", Texture.class)));
            floorModel.getMaterial("tree").set(
                    TextureAttribute.createDiffuse(assets.get("data/tree.png", Texture.class)),
                    new BlendingAttribute()
            );
            instances.add(new ModelInstance(floorModel, "floor"));
            instances.add(tree = new ModelInstance(floorModel, "tree"));
            assets.load("data/g3d/knight.g3db", Model.class);
            loading = true;
        } else if (character == null) {
            character = new ModelInstance(assets.get("data/g3d/knight.g3db", Model.class));
            BoundingBox bbox = new BoundingBox();
            character.calculateBoundingBox(bbox);
            character.transform.setToRotation(Vector3.Y, 180).trn(0, -bbox.min.y, 0);
            instances.add(character);
            animation = new AnimationController(character);
            animation.animate("Idle", -1, 1f, null, 0.2f);
            status = idle;
            for (Animation anim : character.animations)
                Gdx.app.log("Test", anim.id);
        }
    }

    protected void getStatus(final StringBuilder stringBuilder) {
        stringBuilder.append("FPS: ").append(Gdx.graphics.getFramesPerSecond());
        if (loading) stringBuilder.append(" loading...");
    }

    protected void onModelClicked(final String name) {
    }

    boolean rightKey, leftKey, upKey, downKey, spaceKey;

    @Override
    public boolean keyUp(int keycode) {
        if (keycode == Input.Keys.LEFT) leftKey = false;
        if (keycode == Input.Keys.RIGHT) rightKey = false;
        if (keycode == Input.Keys.UP) upKey = false;
        if (keycode == Input.Keys.DOWN) downKey = false;
        if (keycode == Input.Keys.SPACE) spaceKey = false;
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Input.Keys.LEFT) leftKey = true;
        if (keycode == Input.Keys.RIGHT) rightKey = true;
        if (keycode == Input.Keys.UP) upKey = true;
        if (keycode == Input.Keys.DOWN) downKey = true;
        if (keycode == Input.Keys.SPACE) spaceKey = true;
        return true;
    }

    @Override
    public void dispose() {
        floorModel.dispose();
        shadowLight.dispose();
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
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

    protected CollapsableWindow addListWindow(String title, List list, float x, float y) {
        CollapsableWindow window = new CollapsableWindow(title, skin);
        window.row();
        ScrollPane pane = new ScrollPane(list, skin);
        pane.setFadeScrollBars(false);
        window.add(pane);
        window.pack();
        window.pack();
        if (window.getHeight() > hudHeight) {
            window.setHeight(hudHeight);
        }
        window.setX(x < 0 ? hudWidth - (window.getWidth() - (x + 1)) : x);
        window.setY(y < 0 ? hudHeight - (window.getHeight() - (y + 1)) : y);
        window.layout();
        window.collapse();
        hud.addActor(window);
        pane.setScrollX(0);
        pane.setScrollY(0);
        return window;
    }

    public static class CollapsableWindow extends Window {
        private boolean collapsed;
        private float collapseHeight = 20f;
        private float expandHeight;

        public CollapsableWindow(String title, Skin skin) {
            super(title, skin);
            addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (getTapCount() == 2 && getHeight() - y <= getPadTop() && y < getHeight() && x > 0 && x < getWidth())
                        toggleCollapsed();
                }
            });
        }

        public void expand() {
            if (!collapsed) return;
            setHeight(expandHeight);
            setY(getY() - expandHeight + collapseHeight);
            collapsed = false;
        }

        public void collapse() {
            if (collapsed) return;
            expandHeight = getHeight();
            setHeight(collapseHeight);
            setY(getY() + expandHeight - collapseHeight);
            collapsed = true;
            if (getStage() != null) getStage().setScrollFocus(null);
        }

        public void toggleCollapsed() {
            if (collapsed)
                expand();
            else
                collapse();
        }

        public boolean isCollapsed() {
            return collapsed;
        }
    }
}