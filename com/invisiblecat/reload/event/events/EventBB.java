package com.invisiblecat.reload.event.events;

import com.invisiblecat.reload.event.Event;
import net.minecraft.util.BlockPos;

public class EventBB extends Event {
    private BlockPos pos;
    private double minX, minY, minZ, maxX, maxY, maxZ;

    public EventBB(BlockPos pos, double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.pos = pos;
        this.minX = minX;
        this.minY = minY;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxY = maxY;
        this.maxZ = maxZ;

    }

    public BlockPos getPos() {
        return this.pos;
    }
    public void setPos(BlockPos pos) {
        this.pos = pos;
    }

    public double getMinX() {
        return minX;
    }

    public void setMinX(double minX) {
        this.minX = minX;
    }

    public double getMinY() {
        return minY;
    }

    public void setMinY(double minY) {
        this.minY = minY;
    }

    public double getMinZ() {
        return minZ;
    }

    public void setMinZ(double minZ) {
        this.minZ = minZ;
    }

    public double getMaxX() {
        return maxX;
    }

    public void setMaxX(double maxX) {
        this.maxX = maxX;
    }

    public double getMaxY() {
        return maxY;
    }

    public void setMaxY(double maxY) {
        this.maxY = maxY;
    }

    public double getMaxZ() {
        return maxZ;
    }

    public void setMaxZ(double maxZ) {
        this.maxZ = maxZ;
    }
}
