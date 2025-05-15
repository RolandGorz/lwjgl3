/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan.platform;

import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

public class Helper {
    public static final int UINT32_MAX = 0xFFFFFFFF;
    public static final long UINT64_MAX = 0xFFFFFFFFFFFFFFFFL;
    private Helper() {}

    public static void VK_CHECK(int vkResult) {
        if (vkResult != VK10.VK_SUCCESS) {
            throw new RuntimeException("Detected Vulkan error: " + vkResult);
        }
    }

    public static final boolean VK_DEBUG;
    static {
        String property = System.getProperty("debug");
        if (property == null || Boolean.parseBoolean(property)) {
            VK_DEBUG = true;
        } else {
            VK_DEBUG = false;
        }
    }

    static VkSurfaceFormatKHR.Buffer supported_surface_formats;
    static VkSurfaceFormatKHR chosen_surface_format;

    public static VkSurfaceFormatKHR select_surface_format(VkPhysicalDevice gpu, long surface, List<Integer> preferred_formats) {
        if (supported_surface_formats != null) {
            return chosen_surface_format;
        }
        if (preferred_formats.isEmpty()) {
            preferred_formats = new ArrayList<>();
            preferred_formats.add(VK10.VK_FORMAT_R8G8B8A8_SRGB);
            preferred_formats.add(VK10.VK_FORMAT_B8G8R8A8_SRGB);
            preferred_formats.add(VK10.VK_FORMAT_A8B8G8R8_SRGB_PACK32);
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer surface_format_count = memoryStack.mallocInt(1);
            KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(gpu, surface, surface_format_count, null);
            assert (0 < surface_format_count.get(0));
            supported_surface_formats = VkSurfaceFormatKHR.calloc(surface_format_count.get(0));
            KHRSurface.vkGetPhysicalDeviceSurfaceFormatsKHR(gpu, surface, surface_format_count, supported_surface_formats);

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

    public static void free() {
        supported_surface_formats.free();
    }
}
