package com.shich.game;

import static org.lwjgl.glfw.GLFW.glfwInit;

import com.shich.game.entities.Mob;
import com.shich.game.level.Block;
import com.shich.game.render.Renderer;
import com.shich.game.render.Shader;
import com.shich.game.states.GameStateManager;
import com.shich.game.util.Camera;
import com.shich.game.util.Input;
import com.shich.game.util.Timer;
import com.shich.game.util.Window;

import org.lwjgl.glfw.GLFWErrorCallback;

public class Main implements Runnable {
    private Thread game;
    private Window window;
    private Shader shader;
    private Input input;
    private Camera camera;
    private Renderer renderer;
    private Timer timer;

    private GameStateManager gsm;

    // temps

    public void start() {
        game = new Thread(this, "game thread");
        game.start();
    }

    private void init() {
        GLFWErrorCallback.createPrint().set();

        if (!glfwInit()) {
            System.err.println("GLFW failed to initiate");
            System.exit(1); // exit with error code 1
        }

        window = new Window(1920, 1080, "Parallax", true);
        window.setBackgroundColour(1, 1, 1);
        shader = new Shader("shaders/shader");
        camera = new Camera(window);
        input = new Input(window, camera);
        renderer = new Renderer(shader, camera);
        timer = new Timer();

        gsm = new GameStateManager(camera);
        Block.init();
    }

    @Override
    public void run() {
        init();

        // AABB a = new AABB(0, 0, 5, 5);
        // Entity e = new Entity(a, "block/block-1.png");
        // Entity y = new Entity(a, "block/block-1.png");
        // Entity p = new Entity(new AABB(-1.0f, -1.0f, 1f, 1f), "block/block-1");
        // Entity v = new Entity(new AABB(0, 0, 1f, 1f), "block/block-1");
        // Entity z = new Entity(new AABB(0, 0, 0.1f, 0.1f), "block/block-1.png");
        Mob.renderer = renderer;

        while (!window.shouldClose()) {
            // if (input.isButtonDown(input.MOUSE_LEFT)) {
            //     Vector3f direction = new Vector3f();
            //     direction.set(input.mouse_pos);

            //     v.setPos(direction);
            //     direction.sub(p.getPos());

            //     Collision col = a.getCollision(p.bounding_box, direction);
            //     if (col.intersects) {
            //         y.render(renderer);

            //         Vector3f newDir = direction.sub(col.normal.mul(direction.mul(1 - col.time, new Vector3f())));

            //         v.setPos(new Vector3f(p.getPos()).add(newDir));
            //         z.setPos(col.normal);
            //         z.render(renderer);
            //     }
            // }
            // if (input.isButtonPressed(input.MOUSE_RIGHT)) {
            //     p.setPos(input.mouse_pos);
            // }
            // if (input.isKeyPressed(input.DASH)) {
            //     p.setPos(v.getPos());
            // }



            // e.render(renderer);
            // p.render(renderer);
            // v.render(renderer);
            // window.swapBuffers();
            
            input(input);
            update(timer);
            render(renderer);
        }
        destroy();
    }

    public void input(Input input) {
        if (input.isButtonPressed(input.MOUSE_LEFT)) {
            System.out.println(input.mouse_pos);
        }

        gsm.input(input);
        window.input(input);
    }

    public void update(Timer timer) {
        input.update();
        timer.update();
        camera.update();

        gsm.update(timer);
        
        window.update(timer);

        if (gsm.states.isEmpty()) {
            window.shouldClose();
        }
    }

    public void render(Renderer renderer) {
        gsm.render(renderer);

        window.swapBuffers();
    }

    public void destroy() {
        input.destroy();
        window.destroy();
    }

    public static void main(String[] args) {
        new Main().start();
    }

}
