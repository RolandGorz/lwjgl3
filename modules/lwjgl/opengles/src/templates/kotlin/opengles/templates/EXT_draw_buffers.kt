/*
 * Copyright LWJGL. All rights reserved.
 * License terms: https://www.lwjgl.org/license
 */
package opengles.templates

import org.lwjgl.generator.*
import opengles.*

val EXT_draw_buffers = "EXTDrawBuffers".nativeClassGLES("EXT_draw_buffers", postfix = EXT) {
    IntConstant(
        "MAX_COLOR_ATTACHMENTS_EXT"..0x8CDF
    )

    IntConstant(
        "MAX_DRAW_BUFFERS_EXT"..0x8824,
        "DRAW_BUFFER0_EXT"..0x8825,
        "DRAW_BUFFER1_EXT"..0x8826,
        "DRAW_BUFFER2_EXT"..0x8827,
        "DRAW_BUFFER3_EXT"..0x8828,
        "DRAW_BUFFER4_EXT"..0x8829,
        "DRAW_BUFFER5_EXT"..0x882A,
        "DRAW_BUFFER6_EXT"..0x882B,
        "DRAW_BUFFER7_EXT"..0x882C,
        "DRAW_BUFFER8_EXT"..0x882D,
        "DRAW_BUFFER9_EXT"..0x882E,
        "DRAW_BUFFER10_EXT"..0x882F,
        "DRAW_BUFFER11_EXT"..0x8830,
        "DRAW_BUFFER12_EXT"..0x8831,
        "DRAW_BUFFER13_EXT"..0x8832,
        "DRAW_BUFFER14_EXT"..0x8833,
        "DRAW_BUFFER15_EXT"..0x8834
    )

    IntConstant(
        "COLOR_ATTACHMENT0_EXT"..0x8CE0,
        "COLOR_ATTACHMENT1_EXT"..0x8CE1,
        "COLOR_ATTACHMENT2_EXT"..0x8CE2,
        "COLOR_ATTACHMENT3_EXT"..0x8CE3,
        "COLOR_ATTACHMENT4_EXT"..0x8CE4,
        "COLOR_ATTACHMENT5_EXT"..0x8CE5,
        "COLOR_ATTACHMENT6_EXT"..0x8CE6,
        "COLOR_ATTACHMENT7_EXT"..0x8CE7,
        "COLOR_ATTACHMENT8_EXT"..0x8CE8,
        "COLOR_ATTACHMENT9_EXT"..0x8CE9,
        "COLOR_ATTACHMENT10_EXT"..0x8CEA,
        "COLOR_ATTACHMENT11_EXT"..0x8CEB,
        "COLOR_ATTACHMENT12_EXT"..0x8CEC,
        "COLOR_ATTACHMENT13_EXT"..0x8CED,
        "COLOR_ATTACHMENT14_EXT"..0x8CEE,
        "COLOR_ATTACHMENT15_EXT"..0x8CEF
    )

    void(
        "DrawBuffersEXT",

        AutoSize("bufs")..GLsizei("n"),
        SingleValue("buf")..GLenum.const.p("bufs")
    )
}