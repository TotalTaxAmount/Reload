package com.invisiblecat.reload.utils.font.render;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.nio.ByteBuffer;

@Getter
@AllArgsConstructor
public final class TextureData {
    private final int textureId;
    private final int width, height;
    private final ByteBuffer buffer;
}
