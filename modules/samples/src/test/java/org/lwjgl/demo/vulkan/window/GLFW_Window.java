/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan.window;

import org.lwjgl.demo.vulkan.Demo;
import org.lwjgl.demo.vulkan.platform.Instance;
import org.lwjgl.glfw.Callbacks;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.glfw.GLFWWindowSizeCallback;
import org.lwjgl.glfw.GLFWWindowSizeCallbackI;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.vulkan.VK10;

import java.nio.LongBuffer;

public class GLFW_Window {

    private final long windowHandlePointer;

    private final Properties properties;

    public GLFW_Window(Properties properties, Demo demo) {
        this.properties = properties;
        // Set up an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }
        // Configure GLFW
        GLFW.glfwWindowHint(GLFW.GLFW_CLIENT_API, GLFW.GLFW_NO_API);

        // Create the window
        windowHandlePointer = GLFW.glfwCreateWindow(properties.extent.width, properties.extent.height, properties.title, MemoryUtil.NULL, MemoryUtil.NULL);
        GLFWWindowSizeCallback glfwWindowSizeCallback = GLFWWindowSizeCallback.create((window, width, height) -> {
            demo.resize();
        });
        GLFW.glfwSetWindowSizeCallback(windowHandlePointer, glfwWindowSizeCallback);
    }

    public long getWindowHandlePointer() {
        return windowHandlePointer;
    }

    public long create_surface(Instance instance) {
        if (instance == null) {
            return MemoryUtil.NULL;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            LongBuffer longBuffer = memoryStack.mallocLong(1);
            int result = GLFWVulkan.glfwCreateWindowSurface(instance.getHandle(), windowHandlePointer, null, longBuffer);
            if (result != VK10.VK_SUCCESS) {
                return MemoryUtil.NULL;
            }
            return longBuffer.get(0);
        }
    }

    public Extent getExtent() {
        return properties.extent;
    }

    public void free() {
        // Free the window callbacks and destroy the window
        Callbacks.glfwFreeCallbacks(windowHandlePointer);
        GLFW.glfwDestroyWindow(windowHandlePointer);

        // Terminate GLFW and free the error callback
        GLFW.glfwTerminate();
        GLFW.glfwSetErrorCallback(null).free();
    }
}
