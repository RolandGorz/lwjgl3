/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.util.meshoptimizer;

import org.jspecify.annotations.*;

import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;

/** Callback function: {@link #invoke (* anonymous)} */
public abstract class MeshoptAllocate extends Callback implements MeshoptAllocateI {

    /**
     * Creates a {@code MeshoptAllocate} instance from the specified function pointer.
     *
     * @return the new {@code MeshoptAllocate}
     */
    public static MeshoptAllocate create(long functionPointer) {
        MeshoptAllocateI instance = Callback.get(functionPointer);
        return instance instanceof MeshoptAllocate
            ? (MeshoptAllocate)instance
            : new Container(functionPointer, instance);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code functionPointer} is {@code NULL}. */
    public static @Nullable MeshoptAllocate createSafe(long functionPointer) {
        return functionPointer == NULL ? null : create(functionPointer);
    }

    /** Creates a {@code MeshoptAllocate} instance that delegates to the specified {@code MeshoptAllocateI} instance. */
    public static MeshoptAllocate create(MeshoptAllocateI instance) {
        return instance instanceof MeshoptAllocate
            ? (MeshoptAllocate)instance
            : new Container(instance.address(), instance);
    }

    protected MeshoptAllocate() {
        super(CIF);
    }

    MeshoptAllocate(long functionPointer) {
        super(functionPointer);
    }

    private static final class Container extends MeshoptAllocate {

        private final MeshoptAllocateI delegate;

        Container(long functionPointer, MeshoptAllocateI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public long invoke(long size) {
            return delegate.invoke(size);
        }

    }

}