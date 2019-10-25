package com.rvandoosselaer.blocks;

import com.jme3.math.*;
import com.simsilica.mathd.Vec3i;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

/**
 * A shape implementation for a stair.
 *
 * @author rvandoosselaer
 */
@ToString
@RequiredArgsConstructor
public class Stair implements Shape {

    private final Direction direction;

    @Override
    public void add(Vec3i location, Chunk chunk, ChunkMesh chunkMesh) {
        // get the rotation of the stair
        Quaternion rotation = new Quaternion().fromAngleAxis(rotationFromDirection(direction), Vector3f.UNIT_Y);
        // get the block scale, we multiply it with the vertex positions
        float blockScale = BlocksConfig.getInstance().getBlockScale();
        // check if we have 3 images or only one
        boolean multipleImages = chunk.getBlock(location.x, location.y, location.z).isUsingMultipleImages();

        // top face should always be rendered
        if (true) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            // top stair
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // middle stair
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // lower chair
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            // top
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);
            // middle
            chunkMesh.getIndices().add(offset + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 3 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            // lower
            chunkMesh.getIndices().add(offset + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 3 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 12; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(0.0f, 1.0f, 0.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                    // lower
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                } else {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 8f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 8f / 9f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 8f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 8f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 7f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 7f / 9f));
                    // lower
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 7f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 7f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                }
            }
        }
        // bottom face
        if (chunk.isFaceVisible(location, getCorrectedDirection(Direction.BOTTOM))) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 4; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(0.0f, -1.0f, 0.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                } else {
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.3333333f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.3333333f));
                }
            }
        }
        // left face
        if (chunk.isFaceVisible(location, getCorrectedDirection(Direction.LEFT))) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            // top
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // middle
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // bottom
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            // top
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);
            // middle
            chunkMesh.getIndices().add(offset + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 3 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            // bottom
            chunkMesh.getIndices().add(offset + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 3 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 12; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(-1.0f, 0.0f, 0.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(0.0f, 0.0f, 1.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                } else {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 6f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 6f / 9f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 5f / 9f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 4f / 9f));

                }
            }
        }
        // right face
        if (chunk.isFaceVisible(location, getCorrectedDirection(Direction.RIGHT))) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            // top
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // middle
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // bottom
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            // top
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);
            // middle
            chunkMesh.getIndices().add(offset + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 3 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            // bottom
            chunkMesh.getIndices().add(offset + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 3 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 12; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(1.0f, 0.0f, 0.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(0.0f, 0.0f, -1.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 1.0f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 2f / 3f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                } else {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 6f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(2f / 3f, 6f / 9f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1f / 3f, 5f / 9f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 4f / 9f));
                }
            }
        }
        // front face should always be rendered
        if (true) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            // top
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // middle
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 2f / 3f, 1f / 6f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // bottom
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1f / 3f, 0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            // top
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);
            // middle
            chunkMesh.getIndices().add(offset + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            chunkMesh.getIndices().add(offset + 1 + 4);
            chunkMesh.getIndices().add(offset + 3 + 4);
            chunkMesh.getIndices().add(offset + 2 + 4);
            // bottom
            chunkMesh.getIndices().add(offset + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);
            chunkMesh.getIndices().add(offset + 1 + 8);
            chunkMesh.getIndices().add(offset + 3 + 8);
            chunkMesh.getIndices().add(offset + 2 + 8);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 12; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(0.0f, 0.0f, 1.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(1.0f, 0.0f, 0.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 2f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 2f / 3f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1f / 3f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1f / 3f));
                } else {
                    // top
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 6f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 6f / 9f));
                    // middle
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 5f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 5f / 9f));
                    // bottom
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 4f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 3f / 9f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 4f / 9f));
                }
            }
        }
        // back face
        if (chunk.isFaceVisible(location, getCorrectedDirection(Direction.BACK))) {
            // calculate index offset, we use this to connect the triangles
            int offset = chunkMesh.getPositions().size();
            // vertices
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(-0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 0.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            chunkMesh.getPositions().add(rotation.mult(new Vector3f(0.5f, 1.0f, -0.5f)).addLocal(location.x, location.y, location.z).multLocal(blockScale));
            // indices
            chunkMesh.getIndices().add(offset);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 2);
            chunkMesh.getIndices().add(offset + 1);
            chunkMesh.getIndices().add(offset + 3);
            chunkMesh.getIndices().add(offset + 2);

            if (!chunkMesh.isCollisionMesh()) {
                // normals and tangents
                for (int i = 0; i < 4; i++) {
                    chunkMesh.getNormals().add(rotation.mult(new Vector3f(0.0f, 0.0f, -1.0f)));
                    Matrix4f rotationMatrix = rotation.toRotationMatrix(new Matrix4f());
                    chunkMesh.getTangents().add(rotationMatrix.mult(new Vector4f(-1.0f, 0.0f, 0.0f, 1.0f)));
                }
                // uvs
                if (!multipleImages) {
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 1.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.0f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 1.0f));
                } else {
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.3333333f));
                    chunkMesh.getUvs().add(new Vector2f(1.0f, 0.6666666f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.3333333f));
                    chunkMesh.getUvs().add(new Vector2f(0.0f, 0.6666666f));
                }
            }
        }
    }

    /**
     * Returns the rotation around the y-axis for the given direction
     *
     * @param direction
     * @return rotation in radians
     */
    private static float rotationFromDirection(Direction direction) {
        switch (direction) {
            case RIGHT:
                return FastMath.HALF_PI;
            case BACK:
                return FastMath.PI;
            case LEFT:
                return -FastMath.HALF_PI;
            default:
                return 0;
        }
    }

    /**
     * Calculates the direction of a side of the wedge, based on the rotation. A wedge facing right, is rotated 90°
     * around the y-axis. The original left side of this wedge is now facing to the front.
     *
     * @param direction
     * @return
     */
    private Direction getCorrectedDirection(Direction direction) {
        Quaternion rotation = new Quaternion().fromAngleAxis(rotationFromDirection(this.direction), Vector3f.UNIT_Y);
        Vector3f correctedDirection = rotation.mult(direction.getVector().toVector3f());

        return Direction.fromVector(correctedDirection);
    }

}