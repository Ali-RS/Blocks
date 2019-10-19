package com.rvandoosselaer.blocks;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.simsilica.mathd.Vec3i;
import lombok.RequiredArgsConstructor;

/**
 * An appstate implementation that manages the lifecycle of a {@link ChunkPager}.
 *
 * @author remy
 */
@RequiredArgsConstructor
public class ChunkPagerState extends BaseAppState {

    private final ChunkPager chunkPager;

    public ChunkPagerState(Node node, BlocksManager blocksManager) {
        this(new ChunkPager(node, blocksManager));
    }

    @Override
    protected void initialize(Application app) {
        chunkPager.initialize();
    }

    @Override
    protected void cleanup(Application app) {
        chunkPager.cleanup();
    }

    @Override
    protected void onEnable() {
    }

    @Override
    protected void onDisable() {
    }

    @Override
    public void update(float tpf) {
        chunkPager.update();
    }

    public void setGridSize(Vec3i gridSize) {
        chunkPager.setGridSize(gridSize);
    }

    public Vec3i getGridSize() {
        return chunkPager.getGridSize();
    }

    public void setLocation(Vector3f location) {
        chunkPager.setLocation(location);
    }

    public Vector3f getLocation() {
        return chunkPager.getLocation();
    }

}
