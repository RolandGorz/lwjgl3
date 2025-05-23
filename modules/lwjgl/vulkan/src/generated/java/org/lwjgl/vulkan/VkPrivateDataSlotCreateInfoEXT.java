/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.vulkan;

import org.jspecify.annotations.*;

import java.nio.*;

import org.lwjgl.*;
import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * <pre>{@code
 * struct VkPrivateDataSlotCreateInfoEXT {
 *     VkStructureType sType;
 *     void const * pNext;
 *     VkPrivateDataSlotCreateFlags flags;
 * }}</pre>
 */
public class VkPrivateDataSlotCreateInfoEXT extends VkPrivateDataSlotCreateInfo {

    protected VkPrivateDataSlotCreateInfoEXT(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected VkPrivateDataSlotCreateInfoEXT create(long address, @Nullable ByteBuffer container) {
        return new VkPrivateDataSlotCreateInfoEXT(address, container);
    }

    /**
     * Creates a {@code VkPrivateDataSlotCreateInfoEXT} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public VkPrivateDataSlotCreateInfoEXT(ByteBuffer container) {
        super(container);
    }

    /** Sets the specified value to the {@code sType} field. */
    @Override
    public VkPrivateDataSlotCreateInfoEXT sType(@NativeType("VkStructureType") int value) { nsType(address(), value); return this; }
    /** Sets the {@link VK13#VK_STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO} value to the {@code sType} field. */
    @Override
    public VkPrivateDataSlotCreateInfoEXT sType$Default() { return sType(VK13.VK_STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO); }
    /** Sets the specified value to the {@code pNext} field. */
    @Override
    public VkPrivateDataSlotCreateInfoEXT pNext(@NativeType("void const *") long value) { npNext(address(), value); return this; }
    /** Sets the specified value to the {@code flags} field. */
    @Override
    public VkPrivateDataSlotCreateInfoEXT flags(@NativeType("VkPrivateDataSlotCreateFlags") int value) { nflags(address(), value); return this; }

    /** Initializes this struct with the specified values. */
    @Override
    public VkPrivateDataSlotCreateInfoEXT set(
        int sType,
        long pNext,
        int flags
    ) {
        sType(sType);
        pNext(pNext);
        flags(flags);

        return this;
    }

    /**
     * Copies the specified struct data to this struct.
     *
     * @param src the source struct
     *
     * @return this struct
     */
    public VkPrivateDataSlotCreateInfoEXT set(VkPrivateDataSlotCreateInfoEXT src) {
        memCopy(src.address(), address(), SIZEOF);
        return this;
    }

    // -----------------------------------

    /** Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed. */
    public static VkPrivateDataSlotCreateInfoEXT malloc() {
        return new VkPrivateDataSlotCreateInfoEXT(nmemAllocChecked(SIZEOF), null);
    }

    /** Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed. */
    public static VkPrivateDataSlotCreateInfoEXT calloc() {
        return new VkPrivateDataSlotCreateInfoEXT(nmemCallocChecked(1, SIZEOF), null);
    }

    /** Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance allocated with {@link BufferUtils}. */
    public static VkPrivateDataSlotCreateInfoEXT create() {
        ByteBuffer container = BufferUtils.createByteBuffer(SIZEOF);
        return new VkPrivateDataSlotCreateInfoEXT(memAddress(container), container);
    }

    /** Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance for the specified memory address. */
    public static VkPrivateDataSlotCreateInfoEXT create(long address) {
        return new VkPrivateDataSlotCreateInfoEXT(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable VkPrivateDataSlotCreateInfoEXT createSafe(long address) {
        return address == NULL ? null : new VkPrivateDataSlotCreateInfoEXT(address, null);
    }

    /**
     * Returns a new {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance allocated with {@link MemoryUtil#memAlloc memAlloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer malloc(int capacity) {
        return new Buffer(nmemAllocChecked(__checkMalloc(capacity, SIZEOF)), capacity);
    }

    /**
     * Returns a new {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance allocated with {@link MemoryUtil#memCalloc memCalloc}. The instance must be explicitly freed.
     *
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer calloc(int capacity) {
        return new Buffer(nmemCallocChecked(capacity, SIZEOF), capacity);
    }

    /**
     * Returns a new {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance allocated with {@link BufferUtils}.
     *
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer create(int capacity) {
        ByteBuffer container = __create(capacity, SIZEOF);
        return new Buffer(memAddress(container), container, -1, 0, capacity, capacity);
    }

    /**
     * Create a {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static VkPrivateDataSlotCreateInfoEXT.@Nullable Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : new Buffer(address, capacity);
    }

    /**
     * Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack the stack from which to allocate
     */
    public static VkPrivateDataSlotCreateInfoEXT malloc(MemoryStack stack) {
        return new VkPrivateDataSlotCreateInfoEXT(stack.nmalloc(ALIGNOF, SIZEOF), null);
    }

    /**
     * Returns a new {@code VkPrivateDataSlotCreateInfoEXT} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack the stack from which to allocate
     */
    public static VkPrivateDataSlotCreateInfoEXT calloc(MemoryStack stack) {
        return new VkPrivateDataSlotCreateInfoEXT(stack.ncalloc(ALIGNOF, 1, SIZEOF), null);
    }

    /**
     * Returns a new {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance allocated on the specified {@link MemoryStack}.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer malloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.nmalloc(ALIGNOF, capacity * SIZEOF), capacity);
    }

    /**
     * Returns a new {@link VkPrivateDataSlotCreateInfoEXT.Buffer} instance allocated on the specified {@link MemoryStack} and initializes all its bits to zero.
     *
     * @param stack    the stack from which to allocate
     * @param capacity the buffer capacity
     */
    public static VkPrivateDataSlotCreateInfoEXT.Buffer calloc(int capacity, MemoryStack stack) {
        return new Buffer(stack.ncalloc(ALIGNOF, capacity, SIZEOF), capacity);
    }

    // -----------------------------------

    /** An array of {@link VkPrivateDataSlotCreateInfoEXT} structs. */
    public static class Buffer extends VkPrivateDataSlotCreateInfo.Buffer {

        private static final VkPrivateDataSlotCreateInfoEXT ELEMENT_FACTORY = VkPrivateDataSlotCreateInfoEXT.create(-1L);

        /**
         * Creates a new {@code VkPrivateDataSlotCreateInfoEXT.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link VkPrivateDataSlotCreateInfoEXT#SIZEOF}, and its mark will be undefined.</p>
         *
         * <p>The created buffer instance holds a strong reference to the container object.</p>
         */
        public Buffer(ByteBuffer container) {
            super(container);
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
        protected VkPrivateDataSlotCreateInfoEXT getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** Sets the specified value to the {@code sType} field. */
        @Override
        public VkPrivateDataSlotCreateInfoEXT.Buffer sType(@NativeType("VkStructureType") int value) { VkPrivateDataSlotCreateInfoEXT.nsType(address(), value); return this; }
        /** Sets the {@link VK13#VK_STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO} value to the {@code sType} field. */
        @Override
        public VkPrivateDataSlotCreateInfoEXT.Buffer sType$Default() { return sType(VK13.VK_STRUCTURE_TYPE_PRIVATE_DATA_SLOT_CREATE_INFO); }
        /** Sets the specified value to the {@code pNext} field. */
        @Override
        public VkPrivateDataSlotCreateInfoEXT.Buffer pNext(@NativeType("void const *") long value) { VkPrivateDataSlotCreateInfoEXT.npNext(address(), value); return this; }
        /** Sets the specified value to the {@code flags} field. */
        @Override
        public VkPrivateDataSlotCreateInfoEXT.Buffer flags(@NativeType("VkPrivateDataSlotCreateFlags") int value) { VkPrivateDataSlotCreateInfoEXT.nflags(address(), value); return this; }

    }

}