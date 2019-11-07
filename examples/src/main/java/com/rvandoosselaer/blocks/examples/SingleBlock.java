package com.rvandoosselaer.blocks.examples;

import com.jme3.app.*;
import com.jme3.math.ColorRGBA;
import com.rvandoosselaer.blocks.*;
import com.simsilica.lemur.GuiGlobals;
import com.simsilica.lemur.style.BaseStyles;
import com.simsilica.mathd.Vec3i;
import com.simsilica.util.LogAdapter;

/**
 * An application that renders a single block.
 *
 * @author rvandoosselaer
 */
public class SingleBlock extends SimpleApplication {

    public static void main(String[] args) {
        LogAdapter.initialize();

        SingleBlock singleBlock = new SingleBlock();
        singleBlock.start();
    }

    public SingleBlock() {
        super(new StatsAppState(),
                new FlyCamAppState(),
                new DebugKeysAppState(),
                new LightingState(),
                new PostProcessingState(),
                new BasicProfilerState(false),
                new MemoryDebugState());
    }

    @Override
    public void simpleInitApp() {
        GuiGlobals.initialize(this);
        BaseStyles.loadGlassStyle();
        GuiGlobals.getInstance().getStyles().setDefaultStyle("glass");

        BlocksConfig.initialize(assetManager);

        BlockRegistry blockRegistry = BlocksConfig.getInstance().getBlockRegistry();
        ChunkMeshGenerator meshGenerator = BlocksConfig.getInstance().getChunkMeshGenerator();

        Chunk chunk = Chunk.createAt(new Vec3i(0, 0, 0));
        chunk.addBlock(new Vec3i(0, 0, 0), blockRegistry.get(Block.GRASS));
        chunk.update();

        chunk.createNode(meshGenerator);

        rootNode.attachChild(chunk.getNode());

        viewPort.setBackgroundColor(new ColorRGBA(0.5f, 0.6f, 0.7f, 1.0f));
        flyCam.setMoveSpeed(10f);

        GuiGlobals.getInstance().setCursorEventsEnabled(false);
        inputManager.setCursorVisible(false);
    }

}
