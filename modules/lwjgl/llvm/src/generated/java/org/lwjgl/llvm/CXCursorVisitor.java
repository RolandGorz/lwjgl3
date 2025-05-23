/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 * MACHINE GENERATED FILE, DO NOT EDIT
 */
package org.lwjgl.llvm;

import org.jspecify.annotations.*;

import org.lwjgl.system.*;

import static org.lwjgl.system.MemoryUtil.*;

/** Callback function: {@link #invoke (* anonymous)} */
public abstract class CXCursorVisitor extends Callback implements CXCursorVisitorI {

    /**
     * Creates a {@code CXCursorVisitor} instance from the specified function pointer.
     *
     * @return the new {@code CXCursorVisitor}
     */
    public static CXCursorVisitor create(long functionPointer) {
        CXCursorVisitorI instance = Callback.get(functionPointer);
        return instance instanceof CXCursorVisitor
            ? (CXCursorVisitor)instance
            : new Container(functionPointer, instance);
    }

    /** Like {@link #create(long) create}, but returns {@code null} if {@code functionPointer} is {@code NULL}. */
    public static @Nullable CXCursorVisitor createSafe(long functionPointer) {
        return functionPointer == NULL ? null : create(functionPointer);
    }

    /** Creates a {@code CXCursorVisitor} instance that delegates to the specified {@code CXCursorVisitorI} instance. */
    public static CXCursorVisitor create(CXCursorVisitorI instance) {
        return instance instanceof CXCursorVisitor
            ? (CXCursorVisitor)instance
            : new Container(instance.address(), instance);
    }

    protected CXCursorVisitor() {
        super(CIF);
    }

    CXCursorVisitor(long functionPointer) {
        super(functionPointer);
    }

    private static final class Container extends CXCursorVisitor {

        private final CXCursorVisitorI delegate;

        Container(long functionPointer, CXCursorVisitorI delegate) {
            super(functionPointer);
            this.delegate = delegate;
        }

        @Override
        public int invoke(CXCursor cursor, CXCursor parent, long client_data) {
            return delegate.invoke(cursor, parent, client_data);
        }

    }

}