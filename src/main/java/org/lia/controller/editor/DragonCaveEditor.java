package org.lia.controller.editor;

import org.lia.models.dragon.DragonCave;
import org.lia.service.DragonCaveService;
import java.beans.PropertyEditorSupport;

public class DragonCaveEditor extends PropertyEditorSupport {
    private final DragonCaveService dragonCaveService;

    public DragonCaveEditor(DragonCaveService dragonCaveService) {
        this.dragonCaveService = dragonCaveService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            Long id = Long.valueOf(text);
            DragonCave dragonCave = dragonCaveService.findById(id);
            setValue(dragonCave);
        }
    }
}

