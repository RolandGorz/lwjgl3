/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan.platform;

import org.lwjgl.PointerBuffer;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkPhysicalDevice;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Instance {
    final VkInstance handle;
    final List<PhysicalDevice> gpus = new ArrayList<>();

    public Instance(VkInstance instance) {
        handle = instance;
        if (handle.address() != VK10.VK_NULL_HANDLE) {
            query_gpus();
        } else {
            throw new RuntimeException("Instance not valid");
        }
    }

    void query_gpus() {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            // Querying valid physical devices on the machine
            IntBuffer physical_device_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(VK10.vkEnumeratePhysicalDevices(handle, physical_device_count, null));

            if (physical_device_count.get(0) < 1) {
                throw new RuntimeException("Couldn't find a physical device that supports Vulkan.");
            }
            PointerBuffer physical_devices = memoryStack.mallocPointer(physical_device_count.get(0));
            Helper.VK_CHECK(VK10.vkEnumeratePhysicalDevices(handle, physical_device_count, physical_devices));

            // Create gpus wrapper objects from the VkPhysicalDevice's
            for (int i = 0; i < physical_devices.capacity(); ++i) {
                gpus.add(new PhysicalDevice(this, new VkPhysicalDevice(physical_devices.get(i), handle)));
            }
        }
    }

    public void free() {
        for (PhysicalDevice gpu: gpus) {
            gpu.free();
        }
        VK10.vkDestroyInstance(handle, null);
    }

    public VkInstance getHandle() {
        return handle;
    }
}
