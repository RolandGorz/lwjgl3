/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan.khronos;

import org.lwjgl.*;
import org.lwjgl.system.*;
import org.lwjgl.vulkan.*;

import java.nio.*;
import java.util.*;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.vulkan.KHRSurface.*;
import static org.lwjgl.vulkan.VK10.*;

/** Base class and utilities for the hello_triangle samples. */
public abstract class Demo implements AutoCloseable {

    static final long UINT64_MAX = 0xFFFFFFFFFFFFFFFFL;

    static void VK_CHECK(int vkResult) {
        if (vkResult != VK_SUCCESS) {
            throw new RuntimeException("Detected Vulkan error: " + vkResult);
        }
    }

    static final boolean VK_DEBUG;
    static {
        String property = System.getProperty("debug");
        VK_DEBUG = property == null || Boolean.parseBoolean(property);
    }

    static VkSurfaceFormatKHR.Buffer supported_surface_formats;
    static VkSurfaceFormatKHR        chosen_surface_format;

    static VkSurfaceFormatKHR select_surface_format(VkPhysicalDevice gpu, long surface, List<Integer> preferred_formats) {
        if (supported_surface_formats != null) {
            return chosen_surface_format;
        }

        if (preferred_formats.isEmpty()) {
            preferred_formats = new ArrayList<>(3);
            preferred_formats.add(VK_FORMAT_R8G8B8A8_SRGB);
            preferred_formats.add(VK_FORMAT_B8G8R8A8_SRGB);
            preferred_formats.add(VK_FORMAT_A8B8G8R8_SRGB_PACK32);
        }

        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer surface_format_count = stack.mallocInt(1);
            vkGetPhysicalDeviceSurfaceFormatsKHR(gpu, surface, surface_format_count, null);
            assert (0 < surface_format_count.get(0));

            supported_surface_formats = VkSurfaceFormatKHR.calloc(surface_format_count.get(0));
            vkGetPhysicalDeviceSurfaceFormatsKHR(gpu, surface, surface_format_count, supported_surface_formats);

            for (VkSurfaceFormatKHR surface_format : supported_surface_formats) {
                for (Integer format : preferred_formats) {
                    if (format == surface_format.format()) {
                        chosen_surface_format = surface_format;
                        return chosen_surface_format;
                    }
                }
            }

            chosen_surface_format = supported_surface_formats.get(0);
            return chosen_surface_format;
        }
    }

    static void free() {
        supported_surface_formats.free();
    }

    protected Demo() {
    }

    abstract boolean prepare(Window window);
    abstract boolean resize();
    abstract void update();

    public static class Instance {

        private final VkInstance handle;

        private final List<PhysicalDevice> gpus = new ArrayList<>();

        Instance(VkInstance instance) {
            handle = instance;
            if (handle.address() != VK_NULL_HANDLE) {
                query_gpus();
            } else {
                throw new RuntimeException("Instance not valid");
            }
        }

        void query_gpus() {
            try (MemoryStack stack = MemoryStack.stackPush()) {
                // Querying valid physical devices on the machine
                IntBuffer physical_device_count = stack.mallocInt(1);
                VK_CHECK(vkEnumeratePhysicalDevices(handle, physical_device_count, null));

                if (physical_device_count.get(0) < 1) {
                    throw new RuntimeException("Couldn't find a physical device that supports Vulkan.");
                }

                PointerBuffer physical_devices = stack.mallocPointer(physical_device_count.get(0));
                VK_CHECK(vkEnumeratePhysicalDevices(handle, physical_device_count, physical_devices));

                // Create gpus wrapper objects from the VkPhysicalDevice's
                for (int i = 0; i < physical_devices.capacity(); ++i) {
                    gpus.add(new PhysicalDevice(new VkPhysicalDevice(physical_devices.get(i), handle)));
                }
            }
        }

        public void free() {
            for (PhysicalDevice gpu : gpus) {
                gpu.free();
            }
            vkDestroyInstance(handle, null);
        }

        public VkInstance getHandle() {
            return handle;
        }

    }

    public static class PhysicalDevice {

        private final VkPhysicalDevice handle;

        private final VkPhysicalDeviceFeatures         features          = VkPhysicalDeviceFeatures.calloc();
        private final VkPhysicalDeviceProperties       properties        = VkPhysicalDeviceProperties.calloc();
        private final VkPhysicalDeviceMemoryProperties memory_properties = VkPhysicalDeviceMemoryProperties.calloc();

        private final VkQueueFamilyProperties.Buffer queue_family_properties;
        private final VkExtensionProperties.Buffer   device_extensions;

        PhysicalDevice(VkPhysicalDevice physical_device) {
            handle = physical_device;

            vkGetPhysicalDeviceFeatures(physical_device, features);
            vkGetPhysicalDeviceProperties(physical_device, properties);
            vkGetPhysicalDeviceMemoryProperties(physical_device, memory_properties);

            System.out.printf("Found GPU: %s%n", properties.deviceNameString());
            try (MemoryStack stack = stackPush()) {
                IntBuffer queue_family_properties_count = stack.mallocInt(1);
                vkGetPhysicalDeviceQueueFamilyProperties(physical_device, queue_family_properties_count, null);

                queue_family_properties = VkQueueFamilyProperties.calloc(queue_family_properties_count.get(0));
                vkGetPhysicalDeviceQueueFamilyProperties(physical_device, queue_family_properties_count, queue_family_properties);

                IntBuffer device_extension_count = stack.mallocInt(1);
                VK_CHECK(vkEnumerateDeviceExtensionProperties(get_handle(), (ByteBuffer)null, device_extension_count, null));

                device_extensions = VkExtensionProperties.calloc(device_extension_count.get(0));
                VK_CHECK(vkEnumerateDeviceExtensionProperties(get_handle(), (ByteBuffer)null, device_extension_count, device_extensions));
            }

            // Display supported extensions
            if (device_extensions.capacity() > 0) {
                if (VK_DEBUG) {
                    System.out.println("[debug] Device supports the following extensions:");
                }
                for (VkExtensionProperties extension : device_extensions) {
                    if (VK_DEBUG) {
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
}