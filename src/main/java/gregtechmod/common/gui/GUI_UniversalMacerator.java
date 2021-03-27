package gregtechmod.common.gui;

import gregtechmod.api.GregTech_API;
import gregtechmod.api.gui.GT_GUIContainerMetaTile_Machine;
import gregtechmod.api.interfaces.IGregTechTileEntity;
import gregtechmod.common.containers.UniversalMacerator;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;

/**
 * @author TheDarkDnKTv
 *
 */
public class GUI_UniversalMacerator extends GT_GUIContainerMetaTile_Machine {

	private final String mName;
	/**
	 * @param aInventoryPlayer
	 * @param aTileEntity
	 * @param aName
	 * @param aTextureFile
	 */
	public GUI_UniversalMacerator(InventoryPlayer aInventoryPlayer, IGregTechTileEntity aTileEntity, String aName, String aTextureFile) {
		super(new UniversalMacerator(aInventoryPlayer, aTileEntity), GregTech_API.GUI_PATH + aTextureFile);
		mName = aName;
	}
	
    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        fontRenderer.drawString(I18n.format("metatileentity." + mName), 8,  4, 4210752);
    }
	
    @Override
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3) {
    	super.drawGuiContainerBackgroundLayer(par1, par2, par3);
    	int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
        if (mContainer != null) {
        	if (((UniversalMacerator)mContainer).mOutputting.get())			drawTexturedModalRect(x +  7, y + 62, 176, 18, 18, 18);
        	if (((UniversalMacerator)mContainer).mItemTransfer.get())		drawTexturedModalRect(x + 25, y + 62, 176, 36, 18, 18);
        	if (((UniversalMacerator)mContainer).mSeperatedInputs.get())	drawTexturedModalRect(x + 43, y + 62, 176, 54, 18, 18);
        	
        	if (mContainer.mMaxProgressTime.get() > 0) {
	        	int tSize = 20, tProgress = Math.max(1, Math.min(tSize, (mContainer.mProgressTime.get()>0?1:0) + (mContainer.mProgressTime.get() * tSize) / mContainer.mMaxProgressTime.get())) % (tSize+1);
	        	drawTexturedModalRect(x + 78, y + 24, 176, 0, tProgress	, 18);
        	}
        }
    }
}
