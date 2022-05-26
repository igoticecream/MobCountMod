package eu.minemania.mobcountmod.gui;

import eu.minemania.mobcountmod.data.EntityData;
import eu.minemania.mobcountmod.gui.widget.WidgetEntities;
import eu.minemania.mobcountmod.gui.widget.WidgetEntityDataEntry;
import fi.dy.masa.malilib.gui.GuiListBase;
import fi.dy.masa.malilib.util.StringUtils;

public class GuiEntity extends GuiListBase<EntityData, WidgetEntityDataEntry, WidgetEntities>
{
    public GuiEntity()
    {
        super(12, 30);
        this.title = StringUtils.translate("Entities");
    }

    @Override
    protected WidgetEntities createListWidget(int listX, int listY)
    {
        return new WidgetEntities(listX, listY, this.getBrowserWidth(), this.getBrowserHeight(), null, this);
    }

    @Override
    protected int getBrowserWidth()
    {
        return this.width - 20;
    }

    @Override
    protected int getBrowserHeight()
    {
        return this.height - 68;
    }

    @Override
    public void initGui()
    {
        super.initGui();


    }
}
