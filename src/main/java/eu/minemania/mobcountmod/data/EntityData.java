package eu.minemania.mobcountmod.data;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;

public class EntityData
{
    protected EntityType<?> entityType;
    private Class<? extends Entity> classObject;

    public EntityData()
    {
    }

    public EntityData setClassObject(Class<? extends Entity> classObject)
    {
        this.classObject = classObject;

        return this;
    }

    public Class<? extends Entity> getClassObject()
    {
        return this.classObject;
    }

    public EntityData setEntityType(EntityType<?> entityType)
    {
        this.entityType = entityType;

        return this;
    }

    public EntityType<?> getEntityType()
    {
        return entityType;
    }
}