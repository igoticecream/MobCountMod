package eu.minemania.mobcountmod.gui.widget;

import eu.minemania.mobcountmod.counter.DataManager;
import eu.minemania.mobcountmod.data.EntityData;
import fi.dy.masa.malilib.gui.widgets.WidgetListEntryBase;
import fi.dy.masa.malilib.render.RenderUtils;
import fi.dy.masa.malilib.util.StringUtils;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.predicate.entity.EntityPredicates;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class WidgetEntityDataEntry extends WidgetListEntryBase<EntityData>
{
    private final WidgetEntities parent;
    private final EntityData entityData;
    private final boolean isOdd;
    public WidgetEntityDataEntry(int x, int y, int width, int height, boolean isOdd, EntityData entityData, int listIndex, WidgetEntities parent)
    {
        super(x, y, width, height, entityData, listIndex);

        this.isOdd = isOdd;
        this.entityData = entityData;
        this.parent = parent;
    }

    @Override
    public void render(int mouseX, int mouseY, boolean selected, MatrixStack matrixStack)
    {
        RenderUtils.color(1f, 1f, 1f, 1f);

        if (selected || this.isMouseOver(mouseX, mouseY))
        {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x70FFFFFF);
        }
        else if (this.isOdd)
        {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x20FFFFFF);
        }
        else
        {
            RenderUtils.drawRect(this.x, this.y, this.width, this.height, 0x50FFFFFF);
        }

        EntityData data = this.entry;
        int y = this.y;
        if (data.getEntityType() != null)
        {
            List<? extends Entity> entities = mc.world.getEntitiesByType(data.getEntityType(), DataManager.getCounter().getPassiveBB(), EntityPredicates.EXCEPT_SPECTATOR);
            int size = entities.size() - (!StringUtils.translate(entityData.getEntityType().getTranslationKey()).equals("Player") ? 0 : (mc.player.isSpectator() ? 0 : 1));
            y += 7;
            this.drawString(this.x + 20, y, 0xFFFFFFFF, data.getEntityType().toString() + ": " + size, matrixStack);
            AtomicInteger finalY = new AtomicInteger(y);
            entities.forEach(entity ->
            {
                int i = finalY.get() + 7;
                this.drawString(this.x + 20, i, 0xFFFFFFFF, "x: "+ entity.getX() + " y: " + entity.getY() + " z: " + entity.getZ(), matrixStack);
                finalY.set(i);
            });
            y = finalY.get();
        }
        if (data.getClassObject() != null)
        {
            List<? extends Entity> entities = mc.world.getEntitiesByClass(data.getClassObject(), DataManager.getCounter().getPassiveBB(), EntityPredicates.EXCEPT_SPECTATOR);
            int size = entities.size();
            y += 7;
            this.drawString(this.x + 20, y, 0xFFFFFFFF, data.getClassObject().toString() + ": " + size, matrixStack);
            y += 7;
            AtomicInteger finalY = new AtomicInteger(y);
            entities.forEach(entity ->
            {
                int i = finalY.get() + 7;
                this.drawString(this.x + 20, i, 0xFFFFFFFF, "x: "+ entity.getX() + " y: " + entity.getY() + " z: " + entity.getZ(), matrixStack);
                finalY.set(i);
            });
        }
        //this.drawString(this.x + 20, this.y + 7, 0xFFFFFFFF, test.toString(), matrixStack);
    }
}
