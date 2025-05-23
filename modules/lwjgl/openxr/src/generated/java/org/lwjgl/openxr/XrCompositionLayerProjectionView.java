/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.openxr;

import org.jspecify.annotations.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * <pre>{@code
 * struct XrCompositionLayerProjectionView {
 *     XrStructureType type;
 *     void const * next;
 *     {@link XrPosef XrPosef} pose;
 *     {@link XrFovf XrFovf} fov;
 *     {@link XrSwapchainSubImage XrSwapchainSubImage} subImage;
 * }}</pre>
 */
public class XrCompositionLayerProjectionView extends Struct<XrCompositionLayerProjectionView> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        POSE,
        FOV,
        SUBIMAGE;

    static {
        Layout layout = __struct(
            __member(4),
            __member(POINTER_SIZE),
            __member(XrPosef.SIZEOF, XrPosef.ALIGNOF),
            __member(XrFovf.SIZEOF, XrFovf.ALIGNOF),
            __member(XrSwapchainSubImage.SIZEOF, XrSwapchainSubImage.ALIGNOF)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        POSE = layout.offsetof(2);
        FOV = layout.offsetof(3);
        SUBIMAGE = layout.offsetof(4);
    }

    protected XrCompositionLayerProjectionView(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrCompositionLayerProjectionView create(long address, @Nullable ByteBuffer container) {
        return new XrCompositionLayerProjectionView(address, container);
    }

    /**
     * Creates a {@code XrCompositionLayerProjectionView} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrCompositionLayerProjectionView(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return the value of the {@code type} field. */
    @NativeType("XrStructureType")
    public int type() { return ntype(address()); }
    /** @return the value of the {@code next} field. */
    @NativeType("void const *")
    public long next() { return nnext(address()); }
    /** @return a {@link XrPosef} view of the {@code pose} field. */
    public XrPosef pose() { return npose(address()); }
    /** @return a {@link XrFovf} view of the {@code fov} field. */
    public XrFovf fov() { return nfov(address()); }
    /** @return a {@link XrSwapchainSubImage} view of the {@code subImage} field. */
    public XrSwapchainSubImage subImage() { return nsubImage(address()); }

    /** Sets the specified value to the {@code type} field. */
    public XrCompositionLayerProjectionView type(@NativeType("XrStructureType") int value) { ntype(address(), value); return this; }
    /** Sets the {@link XR10#XR_TYPE_COMPOSITION_LAYER_PROJECTION_VIEW TYPE_COMPOSITION_LAYER_PROJECTION_VIEW} value to the {@code type} field. */
    public XrCompositionLayerProjectionView type$Default() { return type(XR10.XR_TYPE_COMPOSITION_LAYER_PROJECTION_VIEW); }
    /** Sets the specified value to the {@code next} field. */
    public XrCompositionLayerProjectionView next(@NativeType("void const *") long value) { nnext(address(), value); return this; }
    /** Prepends the specified {@link XrCompositionLayerDepthInfoKHR} value to the {@code next} chain. */
    public XrCompositionLayerProjectionView next(XrCompositionLayerDepthInfoKHR value) { return this.next(value.next(this.next()).address()); }
    /** Prepends the specified {@link XrCompositionLayerSpaceWarpInfoFB} value to the {@code next} chain. */
    public XrCompositionLayerProjectionView next(XrCompositionLayerSpaceWarpInfoFB value) { return this.next(value.next(this.next()).address()); }
    /** Prepends the specified {@link XrFrameSynthesisInfoEXT} value to the {@code next} chain. */
    public XrCompositionLayerProjectionView next(XrFrameSynthesisInfoEXT value) { return this.next(value.next(this.next()).address()); }
    /** Copies the specified {@link XrPosef} to the {@code pose} field. */
    public XrCompositionLayerProjectionView pose(XrPosef value) { npose(address(), value); return this; }
    /** Passes the {@code pose} field to the specified {@link java.util.function.Consumer Consumer}. */
    public XrCompositionLayerProjectionView pose(java.util.function.Consumer<XrPosef> consumer) { consumer.accept(pose()); return this; }
    /** Copies the specified {@link XrFovf} to the {@code fov} field. */
    public XrCompositionLayerProjectionView fov(XrFovf value) { nfov(address(), value); return this; }
    /** Passes the {@code fov} field to the specified {@link java.util.function.Consumer Consumer}. */
    public XrCompositionLayerProjectionView fov(java.util.function.Consumer<XrFovf> consumer) { consumer.accept(fov()); return this; }
    /** Copies the specified {@link XrSwapchainSubImage} to the {@code subImage} field. */
    public XrCompositionLayerProjectionView subImage(XrSwapchainSubImage value) { nsubImage(address(), value); return this; }
    /** Passes the {@code subImage} field to the specified {@link java.util.function.Consumer Consumer}. */
    public XrCompositionLayerProjectionView subImage(java.util.function.Consumer<XrSwapchainSubImage> consumer) { consumer.accept(subImage()); return this; }

    /** Initializes this struct with the specified values. */
    public XrCompositionLayerProjectionView set(
        int type,
        long next,
        XrPosef pose,
        XrFovf fov,
        XrSwapchainSubImage subImage
    ) {
        type(type);
        next(next);
        pose(pose);
        fov(fov);
        subImage(subImage);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrCompositionLayerProjectionView set(XrCompositionLayerProjectionView src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrCompositionLayerProjectionView} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static XrCompositionLayerProjectionView malloc() {
        return new XrCompositionLayerProjectionView(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrCompositionLayerProjectionView} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static XrCompositionLayerProjectionView calloc() {
        return new XrCompositionLayerProjectionView(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrCompositionLayerProjectionView} instance allocated with {@link BufferUtils}. */
    public static XrCompositionLayerProjectionView create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrCompositionLayerProjectionView(memAddress(container), container);
    }

    /** Returns a new {@code XrCompositionLayerProjectionView} instance for the specified memory address. */
    public static XrCompositionLayerProjectionView create(long address) {
        return new XrCompositionLayerProjectionView(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable XrCompositionLayerProjectionView createSafe(long address) {
        return address == NULL ? null : new XrCompositionLayerProjectionView(address, null);
    }

    /**
     * Returns a new {@link XrCompositionLayerProjectionView.Buffer} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer malloc(int capacity) {
        return new Buffer(nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
    }

    /**
     * Returns a new {@link XrCompositionLayerProjectionView.Buffer} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link XrCompositionLayerProjectionView.Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer create(int capacity) {
        ByteBuffer container = __create(capacity, SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link XrCompositionLayerProjectionView.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrCompositionLayerProjectionView.@Nullable Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code XrCompositionLayerProjectionView} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrCompositionLayerProjectionView malloc(MemoryStack stack) {
        return new XrCompositionLayerProjectionView(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrCompositionLayerProjectionView} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrCompositionLayerProjectionView calloc(MemoryStack stack) {
        return new XrCompositionLayerProjectionView(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link XrCompositionLayerProjectionView.Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    /**
     * Returns a new {@link XrCompositionLayerProjectionView.Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static XrCompositionLayerProjectionView.Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrCompositionLayerProjectionView.TYPE); }
    /** Unsafe version of {@link #next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrCompositionLayerProjectionView.NEXT); }
    /** Unsafe version of {@link #pose}. */
    public static XrPosef npose(long struct) { return XrPosef.create(struct + XrCompositionLayerProjectionView.POSE); }
    /** Unsafe version of {@link #fov}. */
    public static XrFovf nfov(long struct) { return XrFovf.create(struct + XrCompositionLayerProjectionView.FOV); }
    /** Unsafe version of {@link #subImage}. */
    public static XrSwapchainSubImage nsubImage(long struct) { return XrSwapchainSubImage.create(struct + XrCompositionLayerProjectionView.SUBIMAGE); }

    /** Unsafe version of {@link #type(int) type}. */
    public static void ntype(long struct, int value) { memPutInt(struct + XrCompositionLayerProjectionView.TYPE, value); }
    /** Unsafe version of {@link #next(long) next}. */
    public static void nnext(long struct, long value) { memPutAddress(struct + XrCompositionLayerProjectionView.NEXT, value); }
    /** Unsafe version of {@link #pose(XrPosef) pose}. */
    public static void npose(long struct, XrPosef value) { memCopy(value.address(), struct + XrCompositionLayerProjectionView.POSE, XrPosef.SIZEOF); }
    /** Unsafe version of {@link #fov(XrFovf) fov}. */
    public static void nfov(long struct, XrFovf value) { memCopy(value.address(), struct + XrCompositionLayerProjectionView.FOV, XrFovf.SIZEOF); }
    /** Unsafe version of {@link #subImage(XrSwapchainSubImage) subImage}. */
    public static void nsubImage(long struct, XrSwapchainSubImage value) { memCopy(value.address(), struct + XrCompositionLayerProjectionView.SUBIMAGE, XrSwapchainSubImage.SIZEOF); }

    /**
     * Validates pointer members that should not be {@code NULL}.
     *
     * @param struct the struct to validate
     */
    public static void validate(long struct) {
        XrSwapchainSubImage.validate(struct + XrCompositionLayerProjectionView.SUBIMAGE);
    }

    // -----------------------------------

    /** An array of {@link XrCompositionLayerProjectionView} structs. */
    public static class Buffer extends StructBuffer<XrCompositionLayerProjectionView, Buffer> implements NativeResource {

        private static final XrCompositionLayerProjectionView ELEMENT_FACTORY = XrCompositionLayerProjectionView.create(-1L);

        /**
         * Creates a new {@code XrCompositionLayerProjectionView.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrCompositionLayerProjectionView#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(container, container.remaining() / SIZEOF);
        }

        public Buffer(long address, int cap) {
            super(address, null, -1, 0, cap, cap);
        }

        Buffer(long address, @Nullable ByteBuffer container, int mark, int pos, int lim, int cap) {
            super(address, container, mark, pos, lim, cap);
        }

        @Override
        protected Buffer self() {
            return this;
        }

        @Override
        protected Buffer create(long address, @Nullable ByteBuffer container, int mark, int position, int limit, int capacity) {
            return new Buffer(address, container, mark, position, limit, capacity);
        }

        @Override
        protected XrCompositionLayerProjectionView getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code type} field. */
        @NativeType("XrStructureType")
        public int type() { return XrCompositionLayerProjectionView.ntype(address()); }
        /** @return the value of the {@code next} field. */
        @NativeType("void const *")
        public long next() { return XrCompositionLayerProjectionView.nnext(address()); }
        /** @return a {@link XrPosef} view of the {@code pose} field. */
        public XrPosef pose() { return XrCompositionLayerProjectionView.npose(address()); }
        /** @return a {@link XrFovf} view of the {@code fov} field. */
        public XrFovf fov() { return XrCompositionLayerProjectionView.nfov(address()); }
        /** @return a {@link XrSwapchainSubImage} view of the {@code subImage} field. */
        public XrSwapchainSubImage subImage() { return XrCompositionLayerProjectionView.nsubImage(address()); }

        /** Sets the specified value to the {@code type} field. */
        public XrCompositionLayerProjectionView.Buffer type(@NativeType("XrStructureType") int value) { XrCompositionLayerProjectionView.ntype(address(), value); return this; }
        /** Sets the {@link XR10#XR_TYPE_COMPOSITION_LAYER_PROJECTION_VIEW TYPE_COMPOSITION_LAYER_PROJECTION_VIEW} value to the {@code type} field. */
        public XrCompositionLayerProjectionView.Buffer type$Default() { return type(XR10.XR_TYPE_COMPOSITION_LAYER_PROJECTION_VIEW); }
        /** Sets the specified value to the {@code next} field. */
        public XrCompositionLayerProjectionView.Buffer next(@NativeType("void const *") long value) { XrCompositionLayerProjectionView.nnext(address(), value); return this; }
        /** Prepends the specified {@link XrCompositionLayerDepthInfoKHR} value to the {@code next} chain. */
        public XrCompositionLayerProjectionView.Buffer next(XrCompositionLayerDepthInfoKHR value) { return this.next(value.next(this.next()).address()); }
        /** Prepends the specified {@link XrCompositionLayerSpaceWarpInfoFB} value to the {@code next} chain. */
        public XrCompositionLayerProjectionView.Buffer next(XrCompositionLayerSpaceWarpInfoFB value) { return this.next(value.next(this.next()).address()); }
        /** Prepends the specified {@link XrFrameSynthesisInfoEXT} value to the {@code next} chain. */
        public XrCompositionLayerProjectionView.Buffer next(XrFrameSynthesisInfoEXT value) { return this.next(value.next(this.next()).address()); }
        /** Copies the specified {@link XrPosef} to the {@code pose} field. */
        public XrCompositionLayerProjectionView.Buffer pose(XrPosef value) { XrCompositionLayerProjectionView.npose(address(), value); return this; }
        /** Passes the {@code pose} field to the specified {@link java.util.function.Consumer Consumer}. */
        public XrCompositionLayerProjectionView.Buffer pose(java.util.function.Consumer<XrPosef> consumer) { consumer.accept(pose()); return this; }
        /** Copies the specified {@link XrFovf} to the {@code fov} field. */
        public XrCompositionLayerProjectionView.Buffer fov(XrFovf value) { XrCompositionLayerProjectionView.nfov(address(), value); return this; }
        /** Passes the {@code fov} field to the specified {@link java.util.function.Consumer Consumer}. */
        public XrCompositionLayerProjectionView.Buffer fov(java.util.function.Consumer<XrFovf> consumer) { consumer.accept(fov()); return this; }
        /** Copies the specified {@link XrSwapchainSubImage} to the {@code subImage} field. */
        public XrCompositionLayerProjectionView.Buffer subImage(XrSwapchainSubImage value) { XrCompositionLayerProjectionView.nsubImage(address(), value); return this; }
        /** Passes the {@code subImage} field to the specified {@link java.util.function.Consumer Consumer}. */
        public XrCompositionLayerProjectionView.Buffer subImage(java.util.function.Consumer<XrSwapchainSubImage> consumer) { consumer.accept(subImage()); return this; }

    }

}