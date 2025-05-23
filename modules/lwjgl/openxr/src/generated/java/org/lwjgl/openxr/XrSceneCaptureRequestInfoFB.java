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
 * struct XrSceneCaptureRequestInfoFB {
 *     XrStructureType type;
 *     void const * next;
 *     uint32_t requestByteCount;
 *     char const * request;
 * }}</pre>
 */
public class XrSceneCaptureRequestInfoFB extends Struct<XrSceneCaptureRequestInfoFB> implements NativeResource {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        TYPE,
        NEXT,
        REQUESTBYTECOUNT,
        REQUEST;

    static {
        Layout layout = __struct(
            __member(4),
            __member(POINTER_SIZE),
            __member(4),
            __member(POINTER_SIZE)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        TYPE = layout.offsetof(0);
        NEXT = layout.offsetof(1);
        REQUESTBYTECOUNT = layout.offsetof(2);
        REQUEST = layout.offsetof(3);
    }

    protected XrSceneCaptureRequestInfoFB(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected XrSceneCaptureRequestInfoFB create(long address, @Nullable ByteBuffer container) {
        return new XrSceneCaptureRequestInfoFB(address, container);
    }

    /**
     * Creates a {@code XrSceneCaptureRequestInfoFB} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public XrSceneCaptureRequestInfoFB(ByteBuffer container) {
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
    /** @return the value of the {@code requestByteCount} field. */
    @NativeType("uint32_t")
    public int requestByteCount() { return nrequestByteCount(address()); }
    /** @return a {@link ByteBuffer} view of the data pointed to by the {@code request} field. */
    @NativeType("char const *")
    public @Nullable ByteBuffer request() { return nrequest(address()); }

    /** Sets the specified value to the {@code type} field. */
    public XrSceneCaptureRequestInfoFB type(@NativeType("XrStructureType") int value) { ntype(address(), value); return this; }
    /** Sets the {@link FBSceneCapture#XR_TYPE_SCENE_CAPTURE_REQUEST_INFO_FB TYPE_SCENE_CAPTURE_REQUEST_INFO_FB} value to the {@code type} field. */
    public XrSceneCaptureRequestInfoFB type$Default() { return type(FBSceneCapture.XR_TYPE_SCENE_CAPTURE_REQUEST_INFO_FB); }
    /** Sets the specified value to the {@code next} field. */
    public XrSceneCaptureRequestInfoFB next(@NativeType("void const *") long value) { nnext(address(), value); return this; }
    /** Sets the specified value to the {@code requestByteCount} field. */
    public XrSceneCaptureRequestInfoFB requestByteCount(@NativeType("uint32_t") int value) { nrequestByteCount(address(), value); return this; }
    /** Sets the address of the specified {@link ByteBuffer} to the {@code request} field. */
    public XrSceneCaptureRequestInfoFB request(@Nullable @NativeType("char const *") ByteBuffer value) { nrequest(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    public XrSceneCaptureRequestInfoFB set(
        int type,
        long next,
        int requestByteCount,
        @Nullable ByteBuffer request
    ) {
        type(type);
        next(next);
        requestByteCount(requestByteCount);
        request(request);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public XrSceneCaptureRequestInfoFB set(XrSceneCaptureRequestInfoFB src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code XrSceneCaptureRequestInfoFB} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static XrSceneCaptureRequestInfoFB malloc() {
        return new XrSceneCaptureRequestInfoFB(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code XrSceneCaptureRequestInfoFB} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static XrSceneCaptureRequestInfoFB calloc() {
        return new XrSceneCaptureRequestInfoFB(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code XrSceneCaptureRequestInfoFB} instance allocated with {@link BufferUtils}. */
    public static XrSceneCaptureRequestInfoFB create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new XrSceneCaptureRequestInfoFB(memAddress(container), container);
    }

    /** Returns a new {@code XrSceneCaptureRequestInfoFB} instance for the specified memory address. */
    public static XrSceneCaptureRequestInfoFB create(long address) {
        return new XrSceneCaptureRequestInfoFB(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable XrSceneCaptureRequestInfoFB createSafe(long address) {
        return address == NULL ? null : new XrSceneCaptureRequestInfoFB(address, null);
    }

    /**
     * Returns a new {@link XrSceneCaptureRequestInfoFB.Buffer} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer malloc(int capacity) {
        return new Buffer(nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
    }

    /**
     * Returns a new {@link XrSceneCaptureRequestInfoFB.Buffer} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link XrSceneCaptureRequestInfoFB.Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer create(int capacity) {
        ByteBuffer container = __create(capacity, SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link XrSceneCaptureRequestInfoFB.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static XrSceneCaptureRequestInfoFB.@Nullable Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code XrSceneCaptureRequestInfoFB} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSceneCaptureRequestInfoFB malloc(MemoryStack stack) {
        return new XrSceneCaptureRequestInfoFB(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code XrSceneCaptureRequestInfoFB} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static XrSceneCaptureRequestInfoFB calloc(MemoryStack stack) {
        return new XrSceneCaptureRequestInfoFB(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link XrSceneCaptureRequestInfoFB.Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    /**
     * Returns a new {@link XrSceneCaptureRequestInfoFB.Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static XrSceneCaptureRequestInfoFB.Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #type}. */
    public static int ntype(long struct) { return memGetInt(struct + XrSceneCaptureRequestInfoFB.TYPE); }
    /** Unsafe version of {@link #next}. */
    public static long nnext(long struct) { return memGetAddress(struct + XrSceneCaptureRequestInfoFB.NEXT); }
    /** Unsafe version of {@link #requestByteCount}. */
    public static int nrequestByteCount(long struct) { return memGetInt(struct + XrSceneCaptureRequestInfoFB.REQUESTBYTECOUNT); }
    /** Unsafe version of {@link #request() request}. */
    public static @Nullable ByteBuffer nrequest(long struct) { return memByteBufferSafe(memGetAddress(struct + XrSceneCaptureRequestInfoFB.REQUEST), nrequestByteCount(struct)); }

    /** Unsafe version of {@link #type(int) type}. */
    public static void ntype(long struct, int value) { memPutInt(struct + XrSceneCaptureRequestInfoFB.TYPE, value); }
    /** Unsafe version of {@link #next(long) next}. */
    public static void nnext(long struct, long value) { memPutAddress(struct + XrSceneCaptureRequestInfoFB.NEXT, value); }
    /** Sets the specified value to the {@code requestByteCount} field of the specified {@code struct}. */
    public static void nrequestByteCount(long struct, int value) { memPutInt(struct + XrSceneCaptureRequestInfoFB.REQUESTBYTECOUNT, value); }
    /** Unsafe version of {@link #request(ByteBuffer) request}. */
    public static void nrequest(long struct, @Nullable ByteBuffer value) { memPutAddress(struct + XrSceneCaptureRequestInfoFB.REQUEST, memAddressSafe(value)); if (value != null) { nrequestByteCount(struct, value.remaining()); } }

    // -----------------------------------

    /** An array of {@link XrSceneCaptureRequestInfoFB} structs. */
    public static class Buffer extends StructBuffer<XrSceneCaptureRequestInfoFB, Buffer> implements NativeResource {

        private static final XrSceneCaptureRequestInfoFB ELEMENT_FACTORY = XrSceneCaptureRequestInfoFB.create(-1L);

        /**
         * Creates a new {@code XrSceneCaptureRequestInfoFB.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link XrSceneCaptureRequestInfoFB#SIZEOF}, and its mark will be undefined.</p>
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
        protected XrSceneCaptureRequestInfoFB getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return the value of the {@code type} field. */
        @NativeType("XrStructureType")
        public int type() { return XrSceneCaptureRequestInfoFB.ntype(address()); }
        /** @return the value of the {@code next} field. */
        @NativeType("void const *")
        public long next() { return XrSceneCaptureRequestInfoFB.nnext(address()); }
        /** @return the value of the {@code requestByteCount} field. */
        @NativeType("uint32_t")
        public int requestByteCount() { return XrSceneCaptureRequestInfoFB.nrequestByteCount(address()); }
        /** @return a {@link ByteBuffer} view of the data pointed to by the {@code request} field. */
        @NativeType("char const *")
        public @Nullable ByteBuffer request() { return XrSceneCaptureRequestInfoFB.nrequest(address()); }

        /** Sets the specified value to the {@code type} field. */
        public XrSceneCaptureRequestInfoFB.Buffer type(@NativeType("XrStructureType") int value) { XrSceneCaptureRequestInfoFB.ntype(address(), value); return this; }
        /** Sets the {@link FBSceneCapture#XR_TYPE_SCENE_CAPTURE_REQUEST_INFO_FB TYPE_SCENE_CAPTURE_REQUEST_INFO_FB} value to the {@code type} field. */
        public XrSceneCaptureRequestInfoFB.Buffer type$Default() { return type(FBSceneCapture.XR_TYPE_SCENE_CAPTURE_REQUEST_INFO_FB); }
        /** Sets the specified value to the {@code next} field. */
        public XrSceneCaptureRequestInfoFB.Buffer next(@NativeType("void const *") long value) { XrSceneCaptureRequestInfoFB.nnext(address(), value); return this; }
        /** Sets the specified value to the {@code requestByteCount} field. */
        public XrSceneCaptureRequestInfoFB.Buffer requestByteCount(@NativeType("uint32_t") int value) { XrSceneCaptureRequestInfoFB.nrequestByteCount(address(), value); return this; }
        /** Sets the address of the specified {@link ByteBuffer} to the {@code request} field. */
        public XrSceneCaptureRequestInfoFB.Buffer request(@Nullable @NativeType("char const *") ByteBuffer value) { XrSceneCaptureRequestInfoFB.nrequest(address(), value); return this; }

    }

}