package eu.minemania.mobcountmod.gui.widget;

import eu.minemania.mobcountmod.data.EntityData;
import eu.minemania.mobcountmod.gui.GuiEntity;
import eu.minemania.mobcountmod.render.MobCountRenderer;
import fi.dy.masa.malilib.gui.interfaces.ISelectionListener;
import fi.dy.masa.malilib.gui.widgets.WidgetListBase;

import java.util.ArrayList;
import java.util.Collection;

public class WidgetEntities extends WidgetListBase<EntityData, WidgetEntityDataEntry>
{
    private final GuiEntity parent;
    public WidgetEntities(int x, int y, int width, int height, ISelectionListener<EntityData> selectionListener, GuiEntity parent)
    {
        super(x, y, width, height, selectionListener);

        this.parent = parent;
    }

    protected GuiEntity getGuiParent()
    {
        return this.parent;
    }

    @Override
    protected Collection<EntityData> getAllEntries()
    {
        ArrayList<EntityData> data = new ArrayList<>(MobCountRenderer.getPassive());
        return data;
    }

    @Override
    protected WidgetEntityDataEntry createListEntryWidget(int x, int y, int listIndex, boolean isOdd, EntityData entry)
    {
        return new WidgetEntityDataEntry(x, y, this.browserEntryWidth, this.getBrowserEntryHeightFor(entry), isOdd, entry, listIndex, this);
    }
}
