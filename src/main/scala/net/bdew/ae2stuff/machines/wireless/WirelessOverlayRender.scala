/*
 * Copyright (c) bdew, 2014 - 2015
 * https://github.com/bdew/ae2stuff
 *
 * This mod is distributed under the terms of the Minecraft Mod Public
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://bdew.net/minecraft-mod-public-license/
 */

package net.bdew.ae2stuff.machines.wireless

import net.bdew.ae2stuff.misc.WorldOverlayRenderer
import net.bdew.lib.Client
import net.bdew.lib.block.BlockRef
import net.minecraft.util.MovingObjectPosition
import org.lwjgl.opengl.GL11

object WirelessOverlayRender extends WorldOverlayRenderer {
  override def doRender(partialTicks: Float): Unit = {
    val mop = Client.minecraft.objectMouseOver
    if (mop != null && mop.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
      val pos = BlockRef(mop.blockX, mop.blockY, mop.blockZ)
      for {
        tile <- pos.getTile[TileWireless](Client.world)
        other <- tile.link.value
      } {
        GL11.glPushAttrib(GL11.GL_ENABLE_BIT)

        GL11.glDisable(GL11.GL_LIGHTING)
        GL11.glDisable(GL11.GL_TEXTURE_2D)
        GL11.glDisable(GL11.GL_DEPTH_TEST)
        GL11.glEnable(GL11.GL_LINE_SMOOTH)
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST)
        GL11.glLineWidth(4.0F)

        GL11.glBegin(GL11.GL_LINES)
        GL11.glColor3f(0, 0, 1)
        GL11.glVertex3d(pos.x + 0.5D, pos.y + 0.5D, pos.z + 0.5D)
        GL11.glVertex3d(other.x + 0.5D, other.y + 0.5D, other.z + 0.5D)
        GL11.glEnd()

        GL11.glPopAttrib()
      }
    }
  }
}
