/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.llvm;

import org.jspecify.annotations.*;

import java.nio.*;

import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;

/**
 * <pre>{@code
 * struct CXIdxIncludedFileInfo {
 *     {@link CXIdxLoc CXIdxLoc} hashLoc;
 *     char const * filename;
 *     CXFile file;
 *     int isImport;
 *     int isAngled;
 *     int isModuleImport;
 * }}</pre>
 */
public class CXIdxIncludedFileInfo extends Struct<CXIdxIncludedFileInfo> {

    /** The struct size in bytes. */
    public static final int SIZEOF;

    /** The struct alignment in bytes. */
    public static final int ALIGNOF;

    /** The struct member offsets. */
    public static final int
        HASHLOC,
        FILENAME,
        FILE,
        ISIMPORT,
        ISANGLED,
        ISMODULEIMPORT;

    static {
        Layout layout = __struct(
            __member(CXIdxLoc.SIZEOF, CXIdxLoc.ALIGNOF),
            __member(POINTER_SIZE),
            __member(POINTER_SIZE),
            __member(4),
            __member(4),
            __member(4)
        );

        SIZEOF = layout.getSize();
        ALIGNOF = layout.getAlignment();

        HASHLOC = layout.offsetof(0);
        FILENAME = layout.offsetof(1);
        FILE = layout.offsetof(2);
        ISIMPORT = layout.offsetof(3);
        ISANGLED = layout.offsetof(4);
        ISMODULEIMPORT = layout.offsetof(5);
    }

    protected CXIdxIncludedFileInfo(long address, @Nullable ByteBuffer container) {
        super(address, container);
    }

    @Override
    protected CXIdxIncludedFileInfo create(long address, @Nullable ByteBuffer container) {
        return new CXIdxIncludedFileInfo(address, container);
    }

    /**
     * Creates a {@code CXIdxIncludedFileInfo} instance at the current position of the specified {@link ByteBuffer} container. Changes to the buffer's content will be
     * visible to the struct instance and vice versa.
     *
     * <p>The created instance holds a strong reference to the container object.</p>
     */
    public CXIdxIncludedFileInfo(ByteBuffer container) {
        super(memAddress(container), __checkContainer(container, SIZEOF));
    }

    @Override
    public int sizeof() { return SIZEOF; }

    /** @return a {@link CXIdxLoc} view of the {@code hashLoc} field. */
    public CXIdxLoc hashLoc() { return nhashLoc(address()); }
    /** @return a {@link ByteBuffer} view of the null-terminated string pointed to by the {@code filename} field. */
    @NativeType("char const *")
    public ByteBuffer filename() { return nfilename(address()); }
    /** @return the null-terminated string pointed to by the {@code filename} field. */
    @NativeType("char const *")
    public String filenameString() { return nfilenameString(address()); }
    /** @return the value of the {@code file} field. */
    @NativeType("CXFile")
    public long file() { return nfile(address()); }
    /** @return the value of the {@code isImport} field. */
    @NativeType("int")
    public boolean isImport() { return nisImport(address()) != 0; }
    /** @return the value of the {@code isAngled} field. */
    @NativeType("int")
    public boolean isAngled() { return nisAngled(address()) != 0; }
    /** @return the value of the {@code isModuleImport} field. */
    @NativeType("int")
    public boolean isModuleImport() { return nisModuleImport(address()) != 0; }

    // -----------------------------------

    /** Returns a new {@code CXIdxIncludedFileInfo} instance for the specified memory address. */
    public static CXIdxIncludedFileInfo create(long address) {
        return new CXIdxIncludedFileInfo(address, null);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static @Nullable CXIdxIncludedFileInfo createSafe(long address) {
        return address == NULL ? null : new CXIdxIncludedFileInfo(address, null);
    }

    /**
     * Create a {@link CXIdxIncludedFileInfo.Buffer} instance at the specified memory.
     *
     * @param address  the memory address
     * @param capacity the buffer capacity
     */
    public static CXIdxIncludedFileInfo.Buffer create(long address, int capacity) {
        return new Buffer(address, capacity);
    }

    /** Like {@link #create(long, int) create}, but returns {@code null} if {@code address} is {@code NULL}. */
    public static CXIdxIncludedFileInfo.@Nullable Buffer createSafe(long address, int capacity) {
        return address == NULL ? null : new Buffer(address, capacity);
    }

    // -----------------------------------

    /** Unsafe version of {@link #hashLoc}. */
    public static CXIdxLoc nhashLoc(long struct) { return CXIdxLoc.create(struct + CXIdxIncludedFileInfo.HASHLOC); }
    /** Unsafe version of {@link #filename}. */
    public static ByteBuffer nfilename(long struct) { return memByteBufferNT1(memGetAddress(struct + CXIdxIncludedFileInfo.FILENAME)); }
    /** Unsafe version of {@link #filenameString}. */
    public static String nfilenameString(long struct) { return memASCII(memGetAddress(struct + CXIdxIncludedFileInfo.FILENAME)); }
    /** Unsafe version of {@link #file}. */
    public static long nfile(long struct) { return memGetAddress(struct + CXIdxIncludedFileInfo.FILE); }
    /** Unsafe version of {@link #isImport}. */
    public static int nisImport(long struct) { return memGetInt(struct + CXIdxIncludedFileInfo.ISIMPORT); }
    /** Unsafe version of {@link #isAngled}. */
    public static int nisAngled(long struct) { return memGetInt(struct + CXIdxIncludedFileInfo.ISANGLED); }
    /** Unsafe version of {@link #isModuleImport}. */
    public static int nisModuleImport(long struct) { return memGetInt(struct + CXIdxIncludedFileInfo.ISMODULEIMPORT); }

    // -----------------------------------

    /** An array of {@link CXIdxIncludedFileInfo} structs. */
    public static class Buffer extends StructBuffer<CXIdxIncludedFileInfo, Buffer> {

        private static final CXIdxIncludedFileInfo ELEMENT_FACTORY = CXIdxIncludedFileInfo.create(-1L);

        /**
         * Creates a new {@code CXIdxIncludedFileInfo.Buffer} instance backed by the specified container.
         *
         * <p>Changes to the container's content will be visible to the struct buffer instance and vice versa. The two buffers' position, limit, and mark values
         * will be independent. The new buffer's position will be zero, its capacity and its limit will be the number of bytes remaining in this buffer divided
         * by {@link CXIdxIncludedFileInfo#SIZEOF}, and its mark will be undefined.</p>
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
        protected CXIdxIncludedFileInfo getElementFactory() {
            return ELEMENT_FACTORY;
        }

        /** @return a {@link CXIdxLoc} view of the {@code hashLoc} field. */
        public CXIdxLoc hashLoc() { return CXIdxIncludedFileInfo.nhashLoc(address()); }
        /** @return a {@link ByteBuffer} view of the null-terminated string pointed to by the {@code filename} field. */
        @NativeType("char const *")
        public ByteBuffer filename() { return CXIdxIncludedFileInfo.nfilename(address()); }
        /** @return the null-terminated string pointed to by the {@code filename} field. */
        @NativeType("char const *")
        public String filenameString() { return CXIdxIncludedFileInfo.nfilenameString(address()); }
        /** @return the value of the {@code file} field. */
        @NativeType("CXFile")
        public long file() { return CXIdxIncludedFileInfo.nfile(address()); }
        /** @return the value of the {@code isImport} field. */
        @NativeType("int")
        public boolean isImport() { return CXIdxIncludedFileInfo.nisImport(address()) != 0; }
        /** @return the value of the {@code isAngled} field. */
        @NativeType("int")
        public boolean isAngled() { return CXIdxIncludedFileInfo.nisAngled(address()) != 0; }
        /** @return the value of the {@code isModuleImport} field. */
        @NativeType("int")
        public boolean isModuleImport() { return CXIdxIncludedFileInfo.nisModuleImport(address()) != 0; }

    }

}