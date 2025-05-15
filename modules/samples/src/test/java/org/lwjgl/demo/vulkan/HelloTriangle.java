/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package org.lwjgl.demo.vulkan;

import org.joml.Vector3f;
import org.lwjgl.PointerBuffer;
import org.lwjgl.demo.vulkan.platform.Helper;
import org.lwjgl.demo.vulkan.platform.Instance;
import org.lwjgl.demo.vulkan.window.Extent;
import org.lwjgl.demo.vulkan.window.GLFW_Window;
import org.lwjgl.demo.vulkan.window.Properties;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWVulkan;
import org.lwjgl.system.Configuration;
import org.lwjgl.system.MemoryStack;
import org.lwjgl.system.MemoryUtil;
import org.lwjgl.system.Platform;
import org.lwjgl.util.shaderc.Shaderc;
import org.lwjgl.util.vma.Vma;
import org.lwjgl.util.vma.VmaAllocationCreateInfo;
import org.lwjgl.util.vma.VmaAllocationInfo;
import org.lwjgl.util.vma.VmaAllocatorCreateInfo;
import org.lwjgl.util.vma.VmaVulkanFunctions;
import org.lwjgl.vulkan.EXTDebugUtils;
import org.lwjgl.vulkan.KHRGetPhysicalDeviceProperties2;
import org.lwjgl.vulkan.KHRPortabilityEnumeration;
import org.lwjgl.vulkan.KHRPortabilitySubset;
import org.lwjgl.vulkan.KHRSurface;
import org.lwjgl.vulkan.KHRSwapchain;
import org.lwjgl.vulkan.VK10;
import org.lwjgl.vulkan.VkApplicationInfo;
import org.lwjgl.vulkan.VkAttachmentDescription;
import org.lwjgl.vulkan.VkAttachmentReference;
import org.lwjgl.vulkan.VkBufferCreateInfo;
import org.lwjgl.vulkan.VkClearColorValue;
import org.lwjgl.vulkan.VkClearValue;
import org.lwjgl.vulkan.VkCommandBuffer;
import org.lwjgl.vulkan.VkCommandBufferAllocateInfo;
import org.lwjgl.vulkan.VkCommandBufferBeginInfo;
import org.lwjgl.vulkan.VkCommandPoolCreateInfo;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackDataEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCallbackEXT;
import org.lwjgl.vulkan.VkDebugUtilsMessengerCreateInfoEXT;
import org.lwjgl.vulkan.VkDevice;
import org.lwjgl.vulkan.VkDeviceCreateInfo;
import org.lwjgl.vulkan.VkDeviceQueueCreateInfo;
import org.lwjgl.vulkan.VkExtensionProperties;
import org.lwjgl.vulkan.VkExtent2D;
import org.lwjgl.vulkan.VkFenceCreateInfo;
import org.lwjgl.vulkan.VkFramebufferCreateInfo;
import org.lwjgl.vulkan.VkGraphicsPipelineCreateInfo;
import org.lwjgl.vulkan.VkImageSubresourceRange;
import org.lwjgl.vulkan.VkImageViewCreateInfo;
import org.lwjgl.vulkan.VkInstance;
import org.lwjgl.vulkan.VkInstanceCreateInfo;
import org.lwjgl.vulkan.VkLayerProperties;
import org.lwjgl.vulkan.VkPhysicalDevice;
import org.lwjgl.vulkan.VkPipelineColorBlendAttachmentState;
import org.lwjgl.vulkan.VkPipelineColorBlendStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDepthStencilStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineDynamicStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineInputAssemblyStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineLayoutCreateInfo;
import org.lwjgl.vulkan.VkPipelineMultisampleStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineRasterizationStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineShaderStageCreateInfo;
import org.lwjgl.vulkan.VkPipelineVertexInputStateCreateInfo;
import org.lwjgl.vulkan.VkPipelineViewportStateCreateInfo;
import org.lwjgl.vulkan.VkPresentInfoKHR;
import org.lwjgl.vulkan.VkQueue;
import org.lwjgl.vulkan.VkQueueFamilyProperties;
import org.lwjgl.vulkan.VkRect2D;
import org.lwjgl.vulkan.VkRenderPassBeginInfo;
import org.lwjgl.vulkan.VkRenderPassCreateInfo;
import org.lwjgl.vulkan.VkSemaphoreCreateInfo;
import org.lwjgl.vulkan.VkShaderModuleCreateInfo;
import org.lwjgl.vulkan.VkSubmitInfo;
import org.lwjgl.vulkan.VkSubpassDependency;
import org.lwjgl.vulkan.VkSubpassDescription;
import org.lwjgl.vulkan.VkSurfaceCapabilitiesKHR;
import org.lwjgl.vulkan.VkSurfaceFormatKHR;
import org.lwjgl.vulkan.VkSwapchainCreateInfoKHR;
import org.lwjgl.vulkan.VkVertexInputAttributeDescription;
import org.lwjgl.vulkan.VkVertexInputBindingDescription;
import org.lwjgl.vulkan.VkViewport;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HelloTriangle extends Demo{

    static {
        if (Helper.VK_DEBUG) {
            Configuration.DEBUG_MEMORY_ALLOCATOR.set(true);
            Configuration.DEBUG_STACK.set(true);
        }
    }

    /**
     * Swapchain state
     */
    private class SwapchainDimensions {
        // Width of the swapchain.
        int width;

        // Height of the swapchain.
        int height;

        // Pixel format of the swapchain.
        int format;
        private SwapchainDimensions(int width, int height, int format) {
            this.width = width;
            this.height = height;
            this.format = format;
        }
    }

    /**
     * Per-frame data
     */
    private class PerFrame {
        long queue_submit_fence = VK10.VK_NULL_HANDLE;
        long primary_command_pool = VK10.VK_NULL_HANDLE;
        VkCommandBuffer primary_command_buffer;
        long swapchain_acquire_semaphore = VK10.VK_NULL_HANDLE;
        long swapchain_release_semaphore = VK10.VK_NULL_HANDLE;
    }

    /**
     * Vulkan objects and global state
     */
    private class Context {
        /// The Vulkan instance.
        VkInstance instance;

        /// The Vulkan physical device.
        VkPhysicalDevice gpu;

        /// The Vulkan device.
        VkDevice device;

        /// The Vulkan device queue.
        VkQueue queue;

        /// The swapchain.
        long swapchain;

        /// The swapchain dimensions.
        SwapchainDimensions swapchain_dimensions = new SwapchainDimensions(0, 0, VK10.VK_FORMAT_UNDEFINED);

        /// The surface we will render to.
        long surface;

        /// The queue family index where graphics work will be submitted.
        int graphics_queue_index = -1;

        /// The image view for each swapchain image.
        List<Long> swapchain_image_views = new ArrayList<>();

        /// The framebuffer for each swapchain image view.
        List<Long> swapchain_framebuffers = new ArrayList<>();

        /// The renderpass description.
        long render_pass;

        /// The graphics pipeline.
        long pipeline;

        /**
         * The pipeline layout for resources.
         * Not used in this sample, but we still need to provide a dummy one.
         */
        long pipeline_layout;

        /// The debug utility callback.
        long debug_callback;

        /// A set of semaphores that can be reused.
        List<Long> recycled_semaphores = new ArrayList<>();

        /// A set of per-frame data.
        PerFrame[] per_frame = new PerFrame[0];

        long vma_allocator;
    }

    /// Properties of the vertices used in this sample.
    private class Vertex {
        Vector3f position;
        Vector3f color;
        static final int SIZE_OF = Float.BYTES * 3 * 2;
        private Vertex(Vector3f position, Vector3f color) {
            this.position = position;
            this.color = color;
        }
    }

    /// The Vulkan buffer object that holds the vertex data for the triangle.
    long vertex_buffer;

    /// Vulkan Memory Allocator (VMA) allocation info for the vertex buffer.
    long vertex_buffer_allocation;

    private Context context = new Context();

    Instance vk_instance;

    ByteBuffer vertBuffer;
    ByteBuffer fragBuffer;

    VkDebugUtilsMessengerCallbackEXT debug_callback = Helper.VK_DEBUG ? VkDebugUtilsMessengerCallbackEXT.create(
        (message_severity, message_type, pCallbackData, pUserData) -> {
            VkDebugUtilsMessengerCallbackDataEXT callback_data = VkDebugUtilsMessengerCallbackDataEXT.create(pCallbackData);
            if ((message_severity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT) != 0) {
                System.out.printf("%d Validation Layer: Error: %s: %s%n", callback_data.messageIdNumber(), callback_data.pMessageIdNameString(),
                    callback_data.pMessageString());
            }
            else if ((message_severity & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT) != 0) {
                System.out.printf("%d Validation Layer: Warning: %s: %s%n", callback_data.messageIdNumber(), callback_data.pMessageIdNameString(),
                    callback_data.pMessageString());
            }
            else if ((message_type & EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_PERFORMANCE_BIT_EXT) != 0) {
                System.out.printf("%d Validation Layer: Performance Warning: %s: %s%n", callback_data.messageIdNumber(), callback_data.pMessageIdNameString(),
                    callback_data.pMessageString());
            }
            else {
                System.out.printf("%d Validation Layer: Information: %s: %s", callback_data.messageIdNumber(), callback_data.pMessageIdNameString(),
                    callback_data.pMessageString());
            }
            return VK10.VK_FALSE;
        }) : null;

    void compile_shaders() throws IOException {
        InputStream vert_url = HelloTriangle.class.getClassLoader().getResourceAsStream("demo/vulkan/triangle.vert");
        InputStream frag_url = HelloTriangle.class.getClassLoader().getResourceAsStream("demo/vulkan/triangle.frag");
        long compiler = 0;
        long options = 0;
        try {
            compiler = Shaderc.shaderc_compiler_initialize();
            options = Shaderc.shaderc_compile_options_initialize();
            vertBuffer = compile_shader(vert_url, "triangle.vert", Shaderc.shaderc_vertex_shader, compiler, options);
            vert_url.close();
            fragBuffer = compile_shader(frag_url, "triangle.frag", Shaderc.shaderc_fragment_shader, compiler, options);
            frag_url.close();
        } finally {
            Shaderc.shaderc_compile_options_release(options);
            Shaderc.shaderc_compiler_release(compiler);
        }
    }

    static ByteBuffer compile_shader(InputStream shaderCode, String fileName, int shaderType, long compiler, long options) {
        ByteArrayOutputStream stream_buffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        int length;
        String shader_source;
        try {
            while ((length = shaderCode.read(buffer)) != -1) {
                stream_buffer.write(buffer, 0, length);
            }
            shader_source = stream_buffer.toString(StandardCharsets.UTF_8.name());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        long result = Shaderc.shaderc_compile_into_spv(
            compiler,
            shader_source,
            shaderType,
            fileName,
            "main",
            options
        );

        if (Shaderc.shaderc_result_get_compilation_status(result) != Shaderc.shaderc_compilation_status_success) {
            throw new RuntimeException("Shader compilation failed: " + Shaderc.shaderc_result_get_error_message(result));
        }

        return Shaderc.shaderc_result_get_bytes(result);
    }

    /**
     * Validates a list of required extensions, comparing it with the available ones.
     *
     * @param required A PointerBuffer containing required extension names.
     * @param available A VkExtensionProperties object containing available extensions.
     * @return true if all required extensions are available, false otherwise
     */
    private boolean validate_extensions(PointerBuffer required, VkExtensionProperties.Buffer available)
    {
        for (int i = 0; i < required.capacity(); ++i) {
            boolean found = false;
            for (VkExtensionProperties available_extension : available) {
                if (available_extension.extensionNameString().equals(MemoryUtil.memASCII(required.get(i)))) {
                    found = true;
                    break;
                }
            }
            if (!found)
            {
                return false;
            }
        }
        return true;
    }

    /**
     * Initializes the Vulkan instance.
     */
    void init_instance()
    {
        System.out.println("Initializing vulkan instance.");

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer instance_extension_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(VK10.vkEnumerateInstanceExtensionProperties((ByteBuffer)null, instance_extension_count, null));

            VkExtensionProperties.Buffer available_instance_extensions = VkExtensionProperties.calloc(instance_extension_count.get(0), memoryStack);
            Helper.VK_CHECK(VK10.vkEnumerateInstanceExtensionProperties((ByteBuffer)null, instance_extension_count, available_instance_extensions));

            List<String> required_instance_extensions_strings = new ArrayList<>();

            boolean has_debug_utils = false;
            if (Helper.VK_DEBUG) {
                // Validation layers help find wrong api usage, we enable them by default in this sample but should be disabled in release builds of applications
                // For this we use the debug utils extension if it is supported
                for (VkExtensionProperties ext : available_instance_extensions) {
                    if (ext.extensionNameString().equals(EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME)) {
                        has_debug_utils = true;
                        required_instance_extensions_strings.add(EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME);
                        break;
                    }
                }
                if (!has_debug_utils) {
                    System.out.printf("%s not supported or available%n", EXTDebugUtils.VK_EXT_DEBUG_UTILS_EXTENSION_NAME);
                    System.out.println("Make sure to compile the sample in debug mode and/or enable the validation layers");
                }
            }

            boolean portability_enumeration_available = false;
            if (Platform.get().equals(Platform.MACOSX)) {
                required_instance_extensions_strings.add(KHRGetPhysicalDeviceProperties2.VK_KHR_GET_PHYSICAL_DEVICE_PROPERTIES_2_EXTENSION_NAME);
                for (VkExtensionProperties ext : available_instance_extensions) {
                    if (ext.extensionNameString().equals(KHRPortabilityEnumeration.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME)) {
                        required_instance_extensions_strings.add(KHRPortabilityEnumeration.VK_KHR_PORTABILITY_ENUMERATION_EXTENSION_NAME);
                        portability_enumeration_available = true;
                        break;
                    }
                }
            }

            PointerBuffer glfwRequiredExtensions = GLFWVulkan.glfwGetRequiredInstanceExtensions();

            if (glfwRequiredExtensions == null) {
                throw new RuntimeException("glfwGetRequiredInstanceExtensions returned null");
            }

            PointerBuffer required_instance_extensions = memoryStack.mallocPointer(glfwRequiredExtensions.capacity() + required_instance_extensions_strings.size());
            required_instance_extensions.put(glfwRequiredExtensions);
            for (String string : required_instance_extensions_strings) {
                required_instance_extensions.put(MemoryStack.stackASCII(string));
            }
            required_instance_extensions.flip();

            if (!validate_extensions(required_instance_extensions, available_instance_extensions)) {
                throw new RuntimeException("Required instance extensions are missing.");
            }

            PointerBuffer requested_instance_layers = memoryStack.mallocPointer(1);

            if (Helper.VK_DEBUG) {
                String validationLayer = "VK_LAYER_KHRONOS_validation";

                IntBuffer instance_layer_count = memoryStack.mallocInt(1);
                Helper.VK_CHECK(VK10.vkEnumerateInstanceLayerProperties(instance_layer_count, null));

                VkLayerProperties.Buffer supported_instance_layers = VkLayerProperties.calloc(instance_layer_count.get(0), memoryStack);
                Helper.VK_CHECK(VK10.vkEnumerateInstanceLayerProperties(instance_layer_count, supported_instance_layers));

                boolean validationLayerFound = false;
                for (VkLayerProperties lp : supported_instance_layers) {
                    if (lp.layerNameString().equals(validationLayer)) {
                        validationLayerFound = true;
                        break;
                    }
                }
                if (validationLayerFound) {
                    requested_instance_layers.put(memoryStack.UTF8(validationLayer));
                    requested_instance_layers.flip();
                    System.out.printf("Enabled Validation Layer %s%n", validationLayer);
                } else {
                    System.out.printf("Validation Layer %s is not available%n", validationLayer);
                }
            }

            VkApplicationInfo app = VkApplicationInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_APPLICATION_INFO)
                .pApplicationName(MemoryStack.stackASCII("Hello Triangle"))
                .pEngineName(MemoryStack.stackASCII("Vulkan Samples"))
                .apiVersion(VK10.VK_API_VERSION_1_0);

            VkInstanceCreateInfo instance_info = VkInstanceCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_INSTANCE_CREATE_INFO)
                .pApplicationInfo(app)
                .ppEnabledLayerNames(requested_instance_layers)
                .ppEnabledExtensionNames(required_instance_extensions);

            VkDebugUtilsMessengerCreateInfoEXT debug_utils_create_info = null;
            if (Helper.VK_DEBUG) {
                // Validation layers help finding wrong api usage, we enable them when explicitly requested or in debug builds
                // For this we use the debug utils extension if it is supported
                debug_utils_create_info = VkDebugUtilsMessengerCreateInfoEXT.calloc(memoryStack)
                    .sType(EXTDebugUtils.VK_STRUCTURE_TYPE_DEBUG_UTILS_MESSENGER_CREATE_INFO_EXT);
                if (has_debug_utils) {
                    debug_utils_create_info.messageSeverity(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_ERROR_BIT_EXT | EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_SEVERITY_WARNING_BIT_EXT);
                    debug_utils_create_info.messageType(EXTDebugUtils.VK_DEBUG_UTILS_MESSAGE_TYPE_VALIDATION_BIT_EXT);
                    debug_utils_create_info.pfnUserCallback(debug_callback);
                    instance_info.pNext(debug_utils_create_info);
                }
            }

            if (portability_enumeration_available) {
                instance_info.flags(instance_info.flags() | KHRPortabilityEnumeration.VK_INSTANCE_CREATE_ENUMERATE_PORTABILITY_BIT_KHR);
            }

            PointerBuffer vulkanInstancePointer = memoryStack.mallocPointer(1);
            // Create the Vulkan instance
            Helper.VK_CHECK(VK10.vkCreateInstance(instance_info, null, vulkanInstancePointer));
            context.instance = new VkInstance(vulkanInstancePointer.get(0), instance_info);

            if (Helper.VK_DEBUG) {
                if (has_debug_utils) {
                    LongBuffer pMessenger = memoryStack.mallocLong(1);
                    Helper.VK_CHECK(EXTDebugUtils.vkCreateDebugUtilsMessengerEXT(context.instance, debug_utils_create_info, null, pMessenger));
                    context.debug_callback = pMessenger.get(0);
                }
            }
        }
    }

    /**
     * Initializes the Vulkan physical device and logical device.
     */
    void init_device()
    {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            System.out.println("Initializing vulkan device.");

            IntBuffer gpu_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(VK10.vkEnumeratePhysicalDevices(context.instance, gpu_count, null));

            if (gpu_count.get(0) < 1) {
                throw new RuntimeException("No physical device found.");
            }

            // For simplicity, the sample selects the first gpu that has a graphics and present queue
            PointerBuffer gpus = memoryStack.mallocPointer(gpu_count.get(0));
            Helper.VK_CHECK(VK10.vkEnumeratePhysicalDevices(context.instance, gpu_count, gpus));

            for (int i = 0; i < gpu_count.get(0) && (context.graphics_queue_index < 0); i++) {
                context.gpu = new VkPhysicalDevice(gpus.get(i), context.instance);

                IntBuffer queue_family_count = memoryStack.mallocInt(1);
                VK10.vkGetPhysicalDeviceQueueFamilyProperties(context.gpu, queue_family_count, null);

                if (queue_family_count.get(0) < 1) {
                    throw new RuntimeException("No queue family found.");
                }

                VkQueueFamilyProperties.Buffer queue_family_properties = VkQueueFamilyProperties.calloc(queue_family_count.get(0), memoryStack);
                VK10.vkGetPhysicalDeviceQueueFamilyProperties(context.gpu, queue_family_count, queue_family_properties);

                for (int j = 0; j < queue_family_count.get(0); j++) {
                    IntBuffer supports_present = memoryStack.mallocInt(1);
                    KHRSurface.vkGetPhysicalDeviceSurfaceSupportKHR(context.gpu, j, context.surface, supports_present);

                    // Find a queue family which supports graphics and presentation.
                    if (((queue_family_properties.get(j).queueFlags() & VK10.VK_QUEUE_GRAPHICS_BIT) != 0)
                        && supports_present.get(0) == VK10.VK_TRUE) {
                        context.graphics_queue_index = j;
                        break;
                    }
                }
            }

            if (context.graphics_queue_index < 0) {
                throw new RuntimeException("Did not find suitable device with a queue that supports graphics and presentation.");
            }

            IntBuffer device_extension_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(VK10.vkEnumerateDeviceExtensionProperties(context.gpu, (ByteBuffer)null, device_extension_count, null));
            VkExtensionProperties.Buffer device_extensions = VkExtensionProperties.calloc(device_extension_count.get(0), memoryStack);
            Helper.VK_CHECK(VK10.vkEnumerateDeviceExtensionProperties(context.gpu, (ByteBuffer)null, device_extension_count, device_extensions));

            // Since this sample has visual output, the device needs to support the swapchain extension
            PointerBuffer required_device_extensions = memoryStack.mallocPointer(1);
            required_device_extensions.put(memoryStack.UTF8(KHRSwapchain.VK_KHR_SWAPCHAIN_EXTENSION_NAME));
            required_device_extensions.flip();
            if (!validate_extensions(required_device_extensions, device_extensions)) {
                throw new RuntimeException("Required device extensions are missing.");
            }


            if (Platform.get().equals(Platform.MACOSX)) {
                // VK_KHR_portability_subset must be enabled if present in the implementation (e.g on macOS/iOS with beta extensions enabled)
                for (VkExtensionProperties extension : device_extensions) {
                    if (extension.extensionNameString().equals(KHRPortabilitySubset.VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME)) {
                        PointerBuffer required_device_extensions_additional = memoryStack.mallocPointer(required_device_extensions.capacity() + 1);
                        required_device_extensions_additional.put(required_device_extensions);
                        required_device_extensions_additional.put(memoryStack.UTF8(KHRPortabilitySubset.VK_KHR_PORTABILITY_SUBSET_EXTENSION_NAME));
                        required_device_extensions = required_device_extensions_additional;
                    }
                }
            }
            // The sample uses a single graphics queue
            FloatBuffer queue_priority = memoryStack.mallocFloat(1);
            queue_priority.put(1.0f);
            queue_priority.flip();

            VkDeviceQueueCreateInfo.Buffer queue_info = VkDeviceQueueCreateInfo.calloc(1, memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_DEVICE_QUEUE_CREATE_INFO)
                .queueFamilyIndex(context.graphics_queue_index)
                .pQueuePriorities(queue_priority);

            VkDeviceCreateInfo device_info = VkDeviceCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_DEVICE_CREATE_INFO)
                .pQueueCreateInfos(queue_info)
                .ppEnabledExtensionNames(required_device_extensions);

            PointerBuffer devicePointer = memoryStack.mallocPointer(1);
            Helper.VK_CHECK(VK10.vkCreateDevice(context.gpu, device_info, null, devicePointer));
            context.device = new VkDevice(devicePointer.get(0), context.gpu, device_info);

            PointerBuffer queuePointer = memoryStack.mallocPointer(1);
            VK10.vkGetDeviceQueue(context.device, context.graphics_queue_index, 0, queuePointer);
            context.queue = new VkQueue(queuePointer.get(0), context.device);

            // This sample uses the Vulkan Memory Alloctor (VMA), which needs to be set up
            VmaVulkanFunctions vma_vulkan_func = VmaVulkanFunctions.calloc(memoryStack)
                .set(context.instance, context.device);

            VmaAllocatorCreateInfo allocator_info = VmaAllocatorCreateInfo.calloc(memoryStack)
                .physicalDevice(context.gpu)
                .device(context.device)
                .pVulkanFunctions(vma_vulkan_func)
                .instance(context.instance);

            PointerBuffer pointerBuffer = memoryStack.mallocPointer(1);
            int result = Vma.vmaCreateAllocator(allocator_info, pointerBuffer);
            if (result != VK10.VK_SUCCESS) {
                throw new RuntimeException("Could not create allocator for VMA allocator");
            }
            context.vma_allocator = pointerBuffer.get(0);
        }
    }

    /**
     * Initializes the vertex buffer by creating it, allocating memory, binding the memory, and uploading vertex data.
     * This function must be called after the Vulkan device has been initialized.
     * throws runtime exception if any Vulkan operation fails.
     */
    void init_vertex_buffer() {
        // Vertex data for a single colored triangle
        List<Vertex> vertices = new ArrayList<>();
        Collections.addAll(vertices,
            new Vertex(new Vector3f(0.5f, -0.5f, 0.5f), new Vector3f(1.0f, 0.0f, 0.0f)),
            new Vertex(new Vector3f(0.5f, 0.5f, 0.5f), new Vector3f(0.0f, 1.0f, 0.0f)),
            new Vertex(new Vector3f(-0.5f, 0.5f, 0.5f), new Vector3f(0.0f, 0.0f, 1.0f)));

        int buffer_size = Vertex.SIZE_OF * vertices.size();

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            // Copy Vertex data to a buffer accessible by the device
            VkBufferCreateInfo buffer_info = VkBufferCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_BUFFER_CREATE_INFO)
                .size(buffer_size)
                .usage(VK10.VK_BUFFER_USAGE_VERTEX_BUFFER_BIT);

            // We use the Vulkan Memory Allocator to find a memory type that can be written and mapped from the host
            // On most setups this will return a memory type that resides in VRAM and is accessible from the host
            VmaAllocationCreateInfo buffer_alloc_ci = VmaAllocationCreateInfo.calloc(memoryStack)
                .flags(Vma.VMA_ALLOCATION_CREATE_HOST_ACCESS_SEQUENTIAL_WRITE_BIT | Vma.VMA_ALLOCATION_CREATE_MAPPED_BIT)
                .usage(Vma.VMA_MEMORY_USAGE_AUTO);

            VmaAllocationInfo buffer_alloc_info                = VmaAllocationInfo.calloc(memoryStack);
            PointerBuffer     vertex_buffer_allocation_pointer = memoryStack.mallocPointer(1);
            LongBuffer        vertex_buffer_pointer            = memoryStack.mallocLong(1);
            Vma.vmaCreateBuffer(context.vma_allocator, buffer_info, buffer_alloc_ci, vertex_buffer_pointer, vertex_buffer_allocation_pointer, buffer_alloc_info);
            if (buffer_alloc_info.pMappedData() != VK10.VK_NULL_HANDLE) {
                vertex_buffer_allocation = vertex_buffer_allocation_pointer.get(0);
                vertex_buffer = vertex_buffer_pointer.get(0);
                ByteBuffer buffer_alloc_info_byte_buffer = MemoryUtil.memByteBuffer(buffer_alloc_info.pMappedData(), buffer_size);
                for (int i = 0; i < vertices.size(); ++i) {
                    Vertex curr = vertices.get(i);
                    //This is the messy punishment of not having a sizeOf operation. Could make a class that defines the size of the vectors and inherits the vector type.
                    curr.position.get(i * Vertex.SIZE_OF, buffer_alloc_info_byte_buffer);
                    curr.color.get(i * Vertex.SIZE_OF + Float.BYTES * 3, buffer_alloc_info_byte_buffer);
                }
            } else {
                throw new RuntimeException("Could not map vertex buffer.");
            }
        }
    }

    /**
     * Initializes per frame data.
     * @param per_frame The data of a frame.
     */
    void init_per_frame(PerFrame per_frame) {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkFenceCreateInfo info = VkFenceCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_FENCE_CREATE_INFO)
                .flags(VK10.VK_FENCE_CREATE_SIGNALED_BIT);
            LongBuffer queue_submit_fence_pointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreateFence(context.device, info, null, queue_submit_fence_pointer));
            per_frame.queue_submit_fence = queue_submit_fence_pointer.get(0);

            VkCommandPoolCreateInfo cmd_pool_info = VkCommandPoolCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_COMMAND_POOL_CREATE_INFO)
                .flags(VK10.VK_COMMAND_POOL_CREATE_TRANSIENT_BIT)
                .queueFamilyIndex(context.graphics_queue_index);
            LongBuffer primary_command_pool_pointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreateCommandPool(context.device, cmd_pool_info, null, primary_command_pool_pointer));
            per_frame.primary_command_pool = primary_command_pool_pointer.get(0);

            int commandBufferCount = 1;
            VkCommandBufferAllocateInfo cmd_buf_info = VkCommandBufferAllocateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_ALLOCATE_INFO)
                .commandPool(per_frame.primary_command_pool)
                .level(VK10.VK_COMMAND_BUFFER_LEVEL_PRIMARY)
                .commandBufferCount(commandBufferCount);
            PointerBuffer primary_command_buffer_pointer = memoryStack.mallocPointer(commandBufferCount);
            Helper.VK_CHECK(VK10.vkAllocateCommandBuffers(context.device, cmd_buf_info, primary_command_buffer_pointer));
            per_frame.primary_command_buffer = new VkCommandBuffer(primary_command_buffer_pointer.get(0), context.device);
        }
    }

    /**
     * Tears down the frame data.
     * @param per_frame The data of a frame.
     */
    void teardown_per_frame(PerFrame per_frame)
    {
        if (per_frame.queue_submit_fence != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroyFence(context.device, per_frame.queue_submit_fence, null);

            per_frame.queue_submit_fence = VK10.VK_NULL_HANDLE;
        }

        if (per_frame.primary_command_buffer != null)
        {
            VK10.vkFreeCommandBuffers(context.device, per_frame.primary_command_pool, per_frame.primary_command_buffer);

            per_frame.primary_command_buffer = null;
        }

        if (per_frame.primary_command_pool != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroyCommandPool(context.device, per_frame.primary_command_pool, null);

            per_frame.primary_command_pool = VK10.VK_NULL_HANDLE;
        }

        if (per_frame.swapchain_acquire_semaphore != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroySemaphore(context.device, per_frame.swapchain_acquire_semaphore, null);

            per_frame.swapchain_acquire_semaphore = VK10.VK_NULL_HANDLE;
        }

        if (per_frame.swapchain_release_semaphore != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroySemaphore(context.device, per_frame.swapchain_release_semaphore, null);

            per_frame.swapchain_release_semaphore = VK10.VK_NULL_HANDLE;
        }
    }

    /**
     * Initializes the Vulkan swapchain.
     */
    void init_swapchain() {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkSurfaceCapabilitiesKHR surface_properties = VkSurfaceCapabilitiesKHR.calloc(memoryStack);
            Helper.VK_CHECK(KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(context.gpu, context.surface, surface_properties));

            VkSurfaceFormatKHR format = Helper.select_surface_format(context.gpu, context.surface, Collections.emptyList());

            VkExtent2D swapchain_size = VkExtent2D.malloc(memoryStack);
            if (surface_properties.currentExtent().width() == 0xFFFFFFFF) {
                swapchain_size.width(context.swapchain_dimensions.width);
                swapchain_size.height(context.swapchain_dimensions.height);
            } else {
                swapchain_size = surface_properties.currentExtent();
            }

            // FIFO must be supported by all implementations.
            int swapchain_present_mode = KHRSurface.VK_PRESENT_MODE_FIFO_KHR;

            // Determine the number of VkImage's to use in the swapchain.
            // Ideally, we desire to own 1 image at a time, the rest of the images can
            // either be rendered to and/or being queued up for display.
            int desired_swapchain_images = surface_properties.minImageCount() + 1;
            if ((surface_properties.maxImageCount() > 0) && (desired_swapchain_images > surface_properties.maxImageCount())) {
                // Application must settle for fewer images than desired.
                desired_swapchain_images = surface_properties.maxImageCount();
            }

            // Figure out a suitable surface transform.
            int pre_transform;
            if ((surface_properties.supportedTransforms() & KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR) != 0) {
                pre_transform = KHRSurface.VK_SURFACE_TRANSFORM_IDENTITY_BIT_KHR;
            } else {
                pre_transform = surface_properties.currentTransform();
            }

            long old_swapchain = context.swapchain;

            // Find a supported composite type.
            int composite = KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
            if ((surface_properties.supportedCompositeAlpha() & KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR) != 0) {
                composite = KHRSurface.VK_COMPOSITE_ALPHA_OPAQUE_BIT_KHR;
            } else if ((surface_properties.supportedCompositeAlpha() & KHRSurface.VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR) != 0) {
                composite = KHRSurface.VK_COMPOSITE_ALPHA_INHERIT_BIT_KHR;
            } else if ((surface_properties.supportedCompositeAlpha() & KHRSurface.VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR) != 0) {
                composite = KHRSurface.VK_COMPOSITE_ALPHA_PRE_MULTIPLIED_BIT_KHR;
            } else if ((surface_properties.supportedCompositeAlpha() & KHRSurface.VK_COMPOSITE_ALPHA_POST_MULTIPLIED_BIT_KHR) != 0) {
                composite = KHRSurface.VK_COMPOSITE_ALPHA_POST_MULTIPLIED_BIT_KHR;
            }

            VkSwapchainCreateInfoKHR info = VkSwapchainCreateInfoKHR.calloc(memoryStack)
                .sType(KHRSwapchain.VK_STRUCTURE_TYPE_SWAPCHAIN_CREATE_INFO_KHR)
                .surface(context.surface)
                .minImageCount(desired_swapchain_images)
                .imageFormat(format.format())
                .imageColorSpace(format.colorSpace())
                .imageExtent(swapchain_size)
                .imageArrayLayers(1)
                .imageUsage(VK10.VK_IMAGE_USAGE_COLOR_ATTACHMENT_BIT)
                .imageSharingMode(VK10.VK_SHARING_MODE_EXCLUSIVE)
                .preTransform(pre_transform)
                .compositeAlpha(composite)
                .presentMode(swapchain_present_mode)
                .clipped(true)
                .oldSwapchain(old_swapchain);

            LongBuffer swapChainPointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(KHRSwapchain.vkCreateSwapchainKHR(context.device, info, null, swapChainPointer));
            context.swapchain = swapChainPointer.get(0);

            if (old_swapchain != VK10.VK_NULL_HANDLE) {
                for (long image_view : context.swapchain_image_views) {
                    VK10.vkDestroyImageView(context.device, image_view, null);
                }
                for (PerFrame per_frame : context.per_frame) {
                    teardown_per_frame(per_frame);
                }

                context.swapchain_image_views.clear();

                KHRSwapchain.vkDestroySwapchainKHR(context.device, old_swapchain, null);
            }

            context.swapchain_dimensions = new SwapchainDimensions(swapchain_size.width(), swapchain_size.height(), format.format());

            IntBuffer image_count = memoryStack.mallocInt(1);
            Helper.VK_CHECK(KHRSwapchain.vkGetSwapchainImagesKHR(context.device, context.swapchain, image_count, null));

            /// The swapchain images.

            LongBuffer swapchain_images = memoryStack.mallocLong(image_count.get(0));
            Helper.VK_CHECK(KHRSwapchain.vkGetSwapchainImagesKHR(context.device, context.swapchain, image_count, swapchain_images));

            // Initialize per-frame resources.
            // Every swapchain image has its own command pool and fence manager.
            // This makes it very easy to keep track of when we can reset command buffers and such.
            context.per_frame = new PerFrame[image_count.get(0)];

            for (int i = 0; i < image_count.get(0); i++) {
                context.per_frame[i] = new PerFrame();
                init_per_frame(context.per_frame[i]);
            }

            for (int i = 0; i < image_count.get(0); i++) {
                // Create an image view which we can render into.
                VkImageSubresourceRange imageSubresourceRange = VkImageSubresourceRange.calloc(memoryStack)
                    .aspectMask(VK10.VK_IMAGE_ASPECT_COLOR_BIT)
                    .baseMipLevel(0)
                    .levelCount(1)
                    .baseArrayLayer(0)
                    .layerCount(1);
                VkImageViewCreateInfo view_info = VkImageViewCreateInfo.calloc(memoryStack)
                    .sType(VK10.VK_STRUCTURE_TYPE_IMAGE_VIEW_CREATE_INFO)
                    .image(swapchain_images.get(i))
                    .viewType(VK10.VK_IMAGE_VIEW_TYPE_2D)
                    .format(context.swapchain_dimensions.format)
                    .subresourceRange(imageSubresourceRange);

                LongBuffer image_view = memoryStack.mallocLong(1);
                Helper.VK_CHECK(VK10.vkCreateImageView(context.device, view_info, null, image_view));

                context.swapchain_image_views.add(image_view.get(0));
            }
        }
    }

    /**
     * Initializes the Vulkan render pass.
     */
    void init_render_pass() {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkAttachmentDescription.Buffer attachment = VkAttachmentDescription.calloc(1, memoryStack)
                .format(context.swapchain_dimensions.format)// Backbuffer format.
                .samples(VK10.VK_SAMPLE_COUNT_1_BIT)// Not multisampled.
                .loadOp(VK10.VK_ATTACHMENT_LOAD_OP_CLEAR)// When starting the frame, we want tiles to be cleared.
                .storeOp(VK10.VK_ATTACHMENT_STORE_OP_STORE)// When ending the frame, we want tiles to be written out.
                .stencilLoadOp(VK10.VK_ATTACHMENT_LOAD_OP_DONT_CARE)// Don't care about stencil since we're not using it.
                .stencilStoreOp(VK10.VK_ATTACHMENT_STORE_OP_DONT_CARE)// Don't care about stencil since we're not using it.
                .initialLayout(VK10.VK_IMAGE_LAYOUT_UNDEFINED)// The image layout will be undefined when the render pass begins.
                .finalLayout(KHRSwapchain.VK_IMAGE_LAYOUT_PRESENT_SRC_KHR);// After the render pass is complete, we will transition to PRESENT_SRC_KHR layout.

            // We have one subpass. This subpass has one color attachment.
            // While executing this subpass, the attachment will be in attachment optimal layout.
            VkAttachmentReference.Buffer color_ref = VkAttachmentReference.calloc(1, memoryStack)
                .attachment(0)
                .layout(VK10.VK_IMAGE_LAYOUT_COLOR_ATTACHMENT_OPTIMAL);

            // We will end up with two transitions.
            // The first one happens right before we start subpass #0, where
            // UNDEFINED is transitioned into COLOR_ATTACHMENT_OPTIMAL.
            // The final layout in the render pass attachment states PRESENT_SRC_KHR, so we
            // will get a final transition from COLOR_ATTACHMENT_OPTIMAL to PRESENT_SRC_KHR.
            VkSubpassDescription.Buffer subpass = VkSubpassDescription.calloc(1, memoryStack)
                .pipelineBindPoint(VK10.VK_PIPELINE_BIND_POINT_GRAPHICS)
                .colorAttachmentCount(1)
                .pColorAttachments(color_ref);

            // Create a dependency to external events.
            // We need to wait for the WSI semaphore to signal.
            // Only pipeline stages which depend on COLOR_ATTACHMENT_OUTPUT_BIT will
            // actually wait for the semaphore, so we must also wait for that pipeline stage.
            VkSubpassDependency.Buffer dependency = VkSubpassDependency.calloc(1, memoryStack)
                .srcSubpass(VK10.VK_SUBPASS_EXTERNAL)
                .dstSubpass(0)
                .srcStageMask(VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT)
                .dstStageMask(VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
            // Since we changed the image layout, we need to make the memory visible to
            // color attachment to modify.
            dependency.srcAccessMask( 0);
            dependency.dstAccessMask(VK10.VK_ACCESS_COLOR_ATTACHMENT_READ_BIT | VK10.VK_ACCESS_COLOR_ATTACHMENT_WRITE_BIT);

            // Finally, create the renderpass.
            VkRenderPassCreateInfo rp_info = VkRenderPassCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_CREATE_INFO)
                .pAttachments(attachment)
                .pSubpasses(subpass)
                .pDependencies(dependency);
            LongBuffer render_pass_pointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreateRenderPass(context.device, rp_info, null, render_pass_pointer));
            context.render_pass = render_pass_pointer.get(0);
        }
    }

    /**
     * Helper function to load a shader module from an offline-compiled SPIR-V file
     * @param path The path for the shader (relative to the assets directory).
     * @returns A VkShaderModule handle. Aborts execution if shader creation fails.
     */
    long load_shader_module(ByteBuffer buffer)
    {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkShaderModuleCreateInfo module_info = VkShaderModuleCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_SHADER_MODULE_CREATE_INFO)
                .pCode(buffer);
            LongBuffer shader_module = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreateShaderModule(context.device, module_info, null,shader_module));

            return shader_module.get(0);
        }
    }

    /**
     * Initializes the Vulkan pipeline.
     */
    void init_pipeline() {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            // Create a blank pipeline layout.
            // We are not binding any resources to the pipeline in this first sample.
            VkPipelineLayoutCreateInfo layout_info = VkPipelineLayoutCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_LAYOUT_CREATE_INFO);
            LongBuffer pipeline_layout_pointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreatePipelineLayout(context.device, layout_info, null, pipeline_layout_pointer));
            context.pipeline_layout = pipeline_layout_pointer.get(0);

            // The Vertex input properties define the interface between the vertex buffer and the vertex shader.

            // Specify we will use triangle lists to draw geometry.
            VkPipelineInputAssemblyStateCreateInfo input_assembly = VkPipelineInputAssemblyStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_INPUT_ASSEMBLY_STATE_CREATE_INFO)
                .topology(VK10.VK_PRIMITIVE_TOPOLOGY_TRIANGLE_LIST);

            // Define the vertex input binding.
            VkVertexInputBindingDescription.Buffer binding_description = VkVertexInputBindingDescription.calloc(1, memoryStack)
                .binding(0)
                .stride(6 * Float.BYTES) //sizeof(Vertex)
                .inputRate(VK10.VK_VERTEX_INPUT_RATE_VERTEX);

            // Define the vertex input attribute.
            VkVertexInputAttributeDescription.Buffer attribute_descriptions = VkVertexInputAttributeDescription.calloc(2, memoryStack);
            attribute_descriptions.get(0)
                .location(0)
                .binding(0)
                .format(VK10.VK_FORMAT_R32G32_SFLOAT)
                .offset(0);//offsetOf(Vertex, position)
            attribute_descriptions.get(1)
                .location(1)
                .binding(0)
                .format(VK10.VK_FORMAT_R32G32B32_SFLOAT)
                .offset(3 * Float.BYTES);//offsetOf(Vertex, color)

            // Define the pipeline vertex input.
            VkPipelineVertexInputStateCreateInfo vertex_input = VkPipelineVertexInputStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VERTEX_INPUT_STATE_CREATE_INFO)
                .pVertexBindingDescriptions(binding_description)
                .pVertexAttributeDescriptions(attribute_descriptions);

            // Specify rasterization state.
            VkPipelineRasterizationStateCreateInfo raster = VkPipelineRasterizationStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_RASTERIZATION_STATE_CREATE_INFO)
                .cullMode(VK10.VK_CULL_MODE_BACK_BIT)
                .frontFace(VK10.VK_FRONT_FACE_CLOCKWISE)
                .lineWidth(1.0f);

            // Our attachment will write to all color channels, but no blending is enabled.
            VkPipelineColorBlendAttachmentState.Buffer blend_attachment = VkPipelineColorBlendAttachmentState.calloc(1, memoryStack)
                .colorWriteMask(VK10.VK_COLOR_COMPONENT_R_BIT | VK10.VK_COLOR_COMPONENT_G_BIT | VK10.VK_COLOR_COMPONENT_B_BIT | VK10.VK_COLOR_COMPONENT_A_BIT);
            VkPipelineColorBlendStateCreateInfo blend = VkPipelineColorBlendStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_COLOR_BLEND_STATE_CREATE_INFO)
                .attachmentCount(1)
                .pAttachments(blend_attachment);

            // We will have one viewport and scissor box.
            VkPipelineViewportStateCreateInfo viewport = VkPipelineViewportStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_VIEWPORT_STATE_CREATE_INFO)
                .viewportCount(1)
                .scissorCount(1);

            // Disable all depth testing.
            VkPipelineDepthStencilStateCreateInfo depth_stencil = VkPipelineDepthStencilStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DEPTH_STENCIL_STATE_CREATE_INFO);

            // No multisampling.
            VkPipelineMultisampleStateCreateInfo multisample = VkPipelineMultisampleStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_MULTISAMPLE_STATE_CREATE_INFO)
                .rasterizationSamples(VK10.VK_SAMPLE_COUNT_1_BIT);

            // Specify that these states will be dynamic, i.e. not part of pipeline state object.
            IntBuffer dynamics = memoryStack.mallocInt(2);
            dynamics.put(VK10.VK_DYNAMIC_STATE_VIEWPORT);
            dynamics.put(VK10.VK_DYNAMIC_STATE_SCISSOR);
            dynamics.flip();

            VkPipelineDynamicStateCreateInfo dynamic = VkPipelineDynamicStateCreateInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_DYNAMIC_STATE_CREATE_INFO)
                .pDynamicStates(dynamics);

            // Load our SPIR-V shaders.
            VkPipelineShaderStageCreateInfo.Buffer shader_stages = VkPipelineShaderStageCreateInfo.calloc(2, memoryStack);

            // Vertex stage of the pipeline
            shader_stages.get(0)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
                .stage(VK10.VK_SHADER_STAGE_VERTEX_BIT)
                .module(load_shader_module(vertBuffer))
                .pName(memoryStack.UTF8("main"));

            // Fragment stage of the pipeline
            shader_stages.get(1)
                .sType(VK10.VK_STRUCTURE_TYPE_PIPELINE_SHADER_STAGE_CREATE_INFO)
                .stage(VK10.VK_SHADER_STAGE_FRAGMENT_BIT)
                .module(load_shader_module(fragBuffer))
                .pName(memoryStack.UTF8("main"));

            VkGraphicsPipelineCreateInfo.Buffer pipe = VkGraphicsPipelineCreateInfo.calloc(1, memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_GRAPHICS_PIPELINE_CREATE_INFO)
                .stageCount(shader_stages.capacity())
                .pStages(shader_stages)
                .pVertexInputState(vertex_input)
                .pInputAssemblyState(input_assembly)
                .pViewportState(viewport)
                .pRasterizationState(raster)
                .pMultisampleState(multisample)
                .pDepthStencilState(depth_stencil)
                .pColorBlendState(blend)
                .pDynamicState(dynamic)
                .layout(context.pipeline_layout) // We need to specify the pipeline layout up front
                .renderPass(context.render_pass); // We need to specify the render pass up front

            LongBuffer pipeline_pointer = memoryStack.mallocLong(1);
            Helper.VK_CHECK(VK10.vkCreateGraphicsPipelines(context.device, VK10.VK_NULL_HANDLE, pipe, null, pipeline_pointer));
            context.pipeline = pipeline_pointer.get(0);
            // Pipeline is baked, we can delete the shader modules now.
            VK10.vkDestroyShaderModule(context.device, shader_stages.get(0).module(), null);
            VK10.vkDestroyShaderModule(context.device, shader_stages.get(1).module(), null);
        }
    }

    /**
     * Acquires an image from the swapchain.
     * @param[out] image The swapchain index for the acquired image.
     * @returns Vulkan result code
     */
    int acquire_next_image(IntBuffer image)
    {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            long acquire_semaphore;
            if (context.recycled_semaphores.isEmpty()) {
                VkSemaphoreCreateInfo info = VkSemaphoreCreateInfo.calloc(memoryStack)
                    .sType(VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);
                LongBuffer acquire_semaphore_pointer = memoryStack.mallocLong(1);
                Helper.VK_CHECK(VK10.vkCreateSemaphore(context.device, info, null, acquire_semaphore_pointer));
                acquire_semaphore = acquire_semaphore_pointer.get(0);
            } else {
                acquire_semaphore = context.recycled_semaphores.get(context.recycled_semaphores.size() - 1);
                context.recycled_semaphores.remove(context.recycled_semaphores.size() - 1);
            }

            int res = KHRSwapchain.vkAcquireNextImageKHR(context.device, context.swapchain, Helper.UINT64_MAX, acquire_semaphore, VK10.VK_NULL_HANDLE, image);

            if (res != VK10.VK_SUCCESS) {
                context.recycled_semaphores.add(acquire_semaphore);
                return res;
            }

            // If we have outstanding fences for this swapchain image, wait for them to complete first.
            // After begin frame returns, it is safe to reuse or delete resources which
            // were used previously.
            //
            // We wait for fences which completes N frames earlier, so we do not stall,
            // waiting for all GPU work to complete before this returns.
            // Normally, this doesn't really block at all,
            // since we're waiting for old frames to have been completed, but just in case.
            if (context.per_frame[image.get(0)].queue_submit_fence != VK10.VK_NULL_HANDLE)
            {
                VK10.vkWaitForFences(context.device, context.per_frame[image.get(0)].queue_submit_fence, true, Helper.UINT64_MAX);
                VK10.vkResetFences(context.device, context.per_frame[image.get(0)].queue_submit_fence);
            }

            if (context.per_frame[image.get(0)].primary_command_pool != VK10.VK_NULL_HANDLE)
            {
                VK10.vkResetCommandPool(context.device, context.per_frame[image.get(0)].primary_command_pool, 0);
            }

            // Recycle the old semaphore back into the semaphore manager.
            long old_semaphore = context.per_frame[image.get(0)].swapchain_acquire_semaphore;

            if (old_semaphore != VK10.VK_NULL_HANDLE) {
                context.recycled_semaphores.add(old_semaphore);
            }

            context.per_frame[image.get(0)].swapchain_acquire_semaphore = acquire_semaphore;

            return VK10.VK_SUCCESS;
        }
    }

    /**
     * Renders a triangle to the specified swapchain image.
     * @param swapchain_index The swapchain index for the image being rendered.
     */
    void render_triangle(IntBuffer swapchain_index) {
        // Render to this framebuffer.
        long framebuffer = context.swapchain_framebuffers.get(swapchain_index.get(0));

        // Allocate or re-use a primary command buffer.
        VkCommandBuffer cmd = context.per_frame[swapchain_index.get(0)].primary_command_buffer;
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            // We will only submit this once before it's recycled.
            VkCommandBufferBeginInfo begin_info = VkCommandBufferBeginInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_COMMAND_BUFFER_BEGIN_INFO)
                .flags(VK10.VK_COMMAND_BUFFER_USAGE_ONE_TIME_SUBMIT_BIT);
            // Begin command recording
            VK10.vkBeginCommandBuffer(cmd, begin_info);

            // Set clear color values.
            VkClearColorValue colorValue = VkClearColorValue.calloc(memoryStack)
                .float32(0, 0.01f)
                .float32(1, 0.01f)
                .float32(2, 0.033f)
                .float32(3, 1.0f);
            VkClearValue.Buffer clear_value =  VkClearValue.calloc(1, memoryStack)
                .color(colorValue);

            VkRect2D renderArea = VkRect2D.calloc(memoryStack);
            renderArea.extent()
                .width(context.swapchain_dimensions.width)
                .height(context.swapchain_dimensions.height);
            // Begin the render pass.
            VkRenderPassBeginInfo rp_begin = VkRenderPassBeginInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_RENDER_PASS_BEGIN_INFO)
                .renderPass(context.render_pass)
                .framebuffer(framebuffer)
                .renderArea(renderArea)
                .clearValueCount(1)
                .pClearValues(clear_value);

            // We will add draw commands in the same command buffer.
            VK10.vkCmdBeginRenderPass(cmd, rp_begin, VK10.VK_SUBPASS_CONTENTS_INLINE);

            // Bind the graphics pipeline.
            VK10.vkCmdBindPipeline(cmd, VK10.VK_PIPELINE_BIND_POINT_GRAPHICS, context.pipeline);

            VkViewport.Buffer vp = VkViewport.calloc(1, memoryStack)
                .width(context.swapchain_dimensions.width)
                .height(context.swapchain_dimensions.height)
                .minDepth(0.0f)
                .maxDepth(1.0f);

            // Set viewport dynamically
            VK10.vkCmdSetViewport(cmd, 0, vp);

            VkRect2D.Buffer scissor = VkRect2D.calloc(1, memoryStack);
            scissor.extent()
                .width(context.swapchain_dimensions.width)
                .height(context.swapchain_dimensions.height);

            // Set scissor dynamically
            VK10.vkCmdSetScissor(cmd, 0, scissor);

            // Bind the vertex buffer to source the draw calls from.
            LongBuffer offset = memoryStack.longs(0);
            VK10.vkCmdBindVertexBuffers(cmd, 0, memoryStack.longs(vertex_buffer), offset);

            // Draw three vertices with one instance from the currently bound vertex bound.
            VK10.vkCmdDraw(cmd, 3, 1, 0, 0);

            // Complete render pass.
            VK10.vkCmdEndRenderPass(cmd);

            // Complete the command buffer.
            Helper.VK_CHECK(VK10.vkEndCommandBuffer(cmd));

            // Submit it to the queue with a release semaphore.
            if (context.per_frame[swapchain_index.get(0)].swapchain_release_semaphore == VK10.VK_NULL_HANDLE) {
                VkSemaphoreCreateInfo semaphore_info = VkSemaphoreCreateInfo.calloc(memoryStack)
                    .sType(VK10.VK_STRUCTURE_TYPE_SEMAPHORE_CREATE_INFO);
                LongBuffer swapchain_release_semaphore_pointer = memoryStack.mallocLong(1);
                Helper.VK_CHECK(VK10.vkCreateSemaphore(context.device, semaphore_info, null, swapchain_release_semaphore_pointer));
                context.per_frame[swapchain_index.get(0)].swapchain_release_semaphore = swapchain_release_semaphore_pointer.get(0);
            }

            IntBuffer wait_stage = memoryStack.ints(VK10.VK_PIPELINE_STAGE_COLOR_ATTACHMENT_OUTPUT_BIT);
            VkSubmitInfo info = VkSubmitInfo.calloc(memoryStack)
                .sType(VK10.VK_STRUCTURE_TYPE_SUBMIT_INFO)
                .waitSemaphoreCount(1)
                .pWaitSemaphores(memoryStack.longs(context.per_frame[swapchain_index.get(0)].swapchain_acquire_semaphore))
                .pWaitDstStageMask(wait_stage)
                .pCommandBuffers(memoryStack.pointers(cmd))
                .pSignalSemaphores(memoryStack.longs(context.per_frame[swapchain_index.get(0)].swapchain_release_semaphore));
            // Submit command buffer to graphics queue
            Helper.VK_CHECK(VK10.vkQueueSubmit(context.queue, info, context.per_frame[swapchain_index.get(0)].queue_submit_fence));
        }
    }

    /**
     * Presents an image to the swapchain.
     * @param index The swapchain index previously obtained from @ref acquire_next_image.
     * @returns Vulkan result code
     */
    int present_image(IntBuffer index) {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkPresentInfoKHR present = VkPresentInfoKHR.calloc(memoryStack)
                .sType(KHRSwapchain.VK_STRUCTURE_TYPE_PRESENT_INFO_KHR)
                .pWaitSemaphores(memoryStack.longs(context.per_frame[index.get(0)].swapchain_release_semaphore))
                .swapchainCount(1)
                .pSwapchains(memoryStack.longs(context.swapchain))
                .pImageIndices(memoryStack.ints(index.get(0)));
            // Present swapchain image
            return KHRSwapchain.vkQueuePresentKHR(context.queue, present);
        }
    }

    /**
     * Initializes the Vulkan framebuffers.
     */
    void init_framebuffers()
    {
        context.swapchain_framebuffers.clear();

        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            for (long image_view : context.swapchain_image_views) {
                // Build the framebuffer.
                LongBuffer image_view_ptr = memoryStack.mallocLong(1);
                image_view_ptr.put(image_view);
                image_view_ptr.flip();
                VkFramebufferCreateInfo fb_info = VkFramebufferCreateInfo.calloc(memoryStack)
                    .sType(VK10.VK_STRUCTURE_TYPE_FRAMEBUFFER_CREATE_INFO)
                    .renderPass(context.render_pass)
                    .attachmentCount(1)
                    .pAttachments(image_view_ptr)
                    .width(context.swapchain_dimensions.width)
                    .height(context.swapchain_dimensions.height)
                    .layers(1);

                LongBuffer framebufferPointer = memoryStack.mallocLong(1);
                Helper.VK_CHECK(VK10.vkCreateFramebuffer(context.device, fb_info, null, framebufferPointer));

                context.swapchain_framebuffers.add(framebufferPointer.get(0));
            }
        }
    }

    void destroy() {
        // When destroying the application, we need to make sure the GPU is no longer accessing any resources
        // This is done by doing a device wait idle, which blocks until the GPU signals
        VK10.vkDeviceWaitIdle(context.device);

        for (long framebuffer : context.swapchain_framebuffers)
        {
            VK10.vkDestroyFramebuffer(context.device, framebuffer, null);
        }

        for (PerFrame per_frame : context.per_frame)
        {
            teardown_per_frame(per_frame);
        }

        for (long semaphore : context.recycled_semaphores)
        {
            VK10.vkDestroySemaphore(context.device, semaphore, null);
        }

        if (context.pipeline != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroyPipeline(context.device, context.pipeline, null);
        }

        if (context.pipeline_layout != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroyPipelineLayout(context.device, context.pipeline_layout, null);
        }

        if (context.render_pass != VK10.VK_NULL_HANDLE)
        {
            VK10.vkDestroyRenderPass(context.device, context.render_pass, null);
        }

        for (Long image_view : context.swapchain_image_views)
        {
            VK10.vkDestroyImageView(context.device, image_view, null);
        }

        if (context.swapchain != VK10.VK_NULL_HANDLE)
        {
            KHRSwapchain.vkDestroySwapchainKHR(context.device, context.swapchain, null);
        }

        if (context.surface != VK10.VK_NULL_HANDLE)
        {
            KHRSurface.vkDestroySurfaceKHR(context.instance, context.surface, null);
        }

        if (vertex_buffer_allocation != VK10.VK_NULL_HANDLE)
        {
            Vma.vmaDestroyBuffer(context.vma_allocator, vertex_buffer, vertex_buffer_allocation);
        }

        if (context.vma_allocator != VK10.VK_NULL_HANDLE)
        {
            Vma.vmaDestroyAllocator(context.vma_allocator);
        }

        if (context.device != null)
        {
            VK10.vkDestroyDevice(context.device, null);
        }

        if (context.debug_callback != VK10.VK_NULL_HANDLE)
        {
            EXTDebugUtils.vkDestroyDebugUtilsMessengerEXT(context.instance, context.debug_callback, null);
        }
        vk_instance.free();
        vk_instance = null;
        if (debug_callback != null) {
            debug_callback.free();
        }
        Helper.free();
    }

    public boolean prepare(GLFW_Window window) throws IOException {
        assert window != null;

        compile_shaders();

        init_instance();

        vk_instance = new Instance(context.instance);
        context.surface = window.create_surface(vk_instance);
        Extent extent = window.getExtent();
        context.swapchain_dimensions.width  = extent.width;
        context.swapchain_dimensions.height = extent.height;

        if (context.surface == MemoryUtil.NULL)
        {
            throw new RuntimeException("Failed to create window surface.");
        }

        init_device();

        init_vertex_buffer();

        init_swapchain();

        // Create the necessary objects for rendering.
        init_render_pass();
        init_pipeline();
        init_framebuffers();

        return true;
    }

    void update()
    {
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            IntBuffer index = memoryStack.mallocInt(1);

            int res = acquire_next_image(index);

            // Handle outdated error in acquire.
            if (res == KHRSwapchain.VK_SUBOPTIMAL_KHR || res == KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR) {
                resize();
                res = acquire_next_image(index);
            }

            if (res != VK10.VK_SUCCESS) {
                VK10.vkQueueWaitIdle(context.queue);
                return;
            }

            render_triangle(index);
            res = present_image(index);

            // Handle Outdated error in present.
            if (res == KHRSwapchain.VK_SUBOPTIMAL_KHR || res == KHRSwapchain.VK_ERROR_OUT_OF_DATE_KHR) {
                resize();
            } else if (res != VK10.VK_SUCCESS) {
                System.out.println("[error] Failed to present swapchain image.");
            }
        }
    }

    @Override
    public boolean resize() {
        if (context.device == null)
        {
            return false;
        }
        try (MemoryStack memoryStack = MemoryStack.stackPush()) {
            VkSurfaceCapabilitiesKHR surface_properties = VkSurfaceCapabilitiesKHR.calloc(memoryStack);
            Helper.VK_CHECK(KHRSurface.vkGetPhysicalDeviceSurfaceCapabilitiesKHR(context.gpu, context.surface, surface_properties));

            // Only rebuild the swapchain if the dimensions have changed
            if (surface_properties.currentExtent().width() == context.swapchain_dimensions.width &&
                surface_properties.currentExtent().height() == context.swapchain_dimensions.height) {
                return false;
            }
        }

        VK10.vkDeviceWaitIdle(context.device);

        for (long framebuffer : context.swapchain_framebuffers)
        {
            VK10.vkDestroyFramebuffer(context.device, framebuffer, null);
        }

        init_swapchain();
        init_framebuffers();
        return true;
    }

    public static void main(String[] args) throws IOException {
        Properties properties = new Properties();
        properties.title = "Vulkan Samples: Hello Triangle";
        HelloTriangle app = new HelloTriangle();
        GLFW_Window windowHandle = new GLFW_Window(properties, app);
        app.prepare(windowHandle);
        while (!GLFW.glfwWindowShouldClose(windowHandle.getWindowHandlePointer())) {
            GLFW.glfwPollEvents();
            app.update();
        }
        app.destroy();
        windowHandle.free();
    }
}
