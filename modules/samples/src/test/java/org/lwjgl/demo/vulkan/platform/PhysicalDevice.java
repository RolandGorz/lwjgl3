/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan.platform;


import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPhysicalDeviceFeatures;
import org.lwjgl.vulkan.VkPhysicalDeviceMemoryProperties;
import org.lwjgl.vulkan.VkPhysicalDeviceProperties;
import org.lwjgl.vulkan.VkQueueFamilyProperties;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class PhysicalDevice {

    private final Instance instance;
    private final VkPhysicalDevice handle;
    private final VkPhysicalDeviceFeatures features = VkPhysicalDeviceFeatures.calloc();
    private final VkPhysicalDeviceProperties properties = VkPhysicalDeviceProperties.calloc();
    private final VkPhysicalDeviceMemoryProperties memory_properties = VkPhysicalDeviceMemoryProperties.calloc();
    private final VkQueueFamilyProperties.Buffer queue_family_properties;
    private final VkExtensionProperties.Buffer device_extensions;

    public PhysicalDevice(Instance instance, VkPhysicalDevice physical_device) {
        this.instance = instance;
        handle = physical_device;
        VK10.vkGetPhysicalDeviceFeatures(physical_device, features);
        VK10.vkGetPhysicalDeviceProperties(physical_device, properties);
        VK10.vkGetPhysicalDeviceMemoryProperties(physical_device, memory_properties);

        System.out.printf("Found GPU: %s%n", properties.deviceNameString());
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer queue_family_properties_count = memoryStack.mallocInt(1);
            VK10.vkGetPhysicalDeviceQueueFamilyProperties(physical_device, queue_family_properties_count, null);
            queue_family_properties = VkQueueFamilyProperties.calloc(queue_family_properties_count.get(0));
            VK10.vkGetPhysicalDeviceQueueFamilyProperties(physical_device, queue_family_properties_count, queue_family_properties);

            IntBuffer device_extension_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(VK10.vkEnumerateDeviceExtensionProperties(get_handle(), (ByteBuffer)null, device_extension_count, null));

            device_extensions = VkExtensionProperties.calloc(device_extension_count.get(0));
            Helper.VK_CHECK(VK10.vkEnumerateDeviceExtensionProperties(get_handle(), (ByteBuffer)null, device_extension_count, device_extensions));
        }

        // Display supported extensions
        if (device_extensions.capacity() > 0) {
            if (Helper.VK_DEBUG) {
                System.out.println("[debug] Device supports the following extensions:");
            }
            for (VkExtensionProperties extension : device_extensions) {
                if (Helper.VK_DEBUG) {
                    System.out.printf("[debug]  \t%s%n", extension.extensionNameString());
                }
            }
        }
    }

    public VkPhysicalDevice get_handle() {
        return handle;
    }

    public void free() {
        device_extensions.free();
        queue_family_properties.free();
        memory_properties.free();
        properties.free();
        features.free();
    }
}
