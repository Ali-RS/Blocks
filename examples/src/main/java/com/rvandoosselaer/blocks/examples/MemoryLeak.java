package com.rvandoosselaer.blocks.examples;

import com.jme3.app.SimpleApplication;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.util.BufferUtils;
import com.rvandoosselaer.blocks.*;
import com.simsilica.mathd.Vec3i;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.util.logging.Level;
import java.util.logging.LogManager;

/**
 * An application that pages endless terrain around the camera.
 *
 * @author rvandoosselaer
 */
public class MemoryLeak extends SimpleApplication {

    private ChunkPagerState chunkPagerState;

    public static void main(String[] args) {
        LogManager.getLogManager().getLogger("").setLevel(Level.ALL);
        SLF4JBridgeHandler.removeHandlersForRootLogger();
        SLF4JBridgeHandler.install();

        MemoryLeak endlessTerrain = new MemoryLeak();
        endlessTerrain.start();
    }

    @Override
    public void simpleInitApp() {
        BufferUtils.setTrackDirectMemoryEnabled(true);

        BlocksConfig.initialize(assetManager);

        BlocksConfig.getInstance().setGridSize(new Vec3i(5, 1, 5));

        BlockRegistry blockRegistry = BlocksConfig.getInstance().getBlockRegistry();

        // create the BlocksManager that will generate the terrain
        BlocksManager blocksManager = BlocksManager.builder()
                .cacheSize(50)
                .chunkGenerator(new FlatTerrainGenerator(8, blockRegistry.get(Block.GRASS)))
                .chunkGenerationPoolSize(3)
                .meshGenerationPoolSize(1)
                .build();

        // create the appstates that manage the lifecycle of the BlocksManager and ChunkPager
        BlocksManagerState blocksManagerState = new BlocksManagerState(blocksManager);
        chunkPagerState = new ChunkPagerState(rootNode, blocksManager);

        // atttach the states
        stateManager.attachAll(blocksManagerState, chunkPagerState);

        addLights(rootNode);
        viewPort.setBackgroundColor(ColorRGBA.Cyan);
        flyCam.setMoveSpeed(10f);
        cam.setLocation(new Vector3f(0, 20f, 0));
    }

    private float counter = 0;

    @Override
    public void simpleUpdate(float tpf) {
        cam.setLocation(cam.getLocation().add(0, 0, -10f * tpf));
        chunkPagerState.setLocation(new Vector3f(cam.getLocation().x, 0, cam.getLocation().z));

        counter += tpf;
        if (counter >= 1) {
            BufferUtils.printCurrentDirectMemory(null);
            counter = 0;
        }
    }

    private static void addLights(Node node) {
        node.addLight(new AmbientLight(new ColorRGBA(0.2f, 0.2f, 0.2f, 1.0f)));
        node.addLight(new DirectionalLight(new Vector3f(-0.2f, -1.0f, -0.2f).normalizeLocal(), ColorRGBA.White));
    }

}
