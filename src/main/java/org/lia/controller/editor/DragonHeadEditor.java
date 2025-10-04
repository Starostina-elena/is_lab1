package org.lia.controller.editor;

import org.lia.models.dragon.DragonHead;
import org.lia.service.DragonHeadService;
import java.beans.PropertyEditorSupport;

public class DragonHeadEditor extends PropertyEditorSupport {
    private final DragonHeadService dragonHeadService;

    public DragonHeadEditor(DragonHeadService dragonHeadService) {
        this.dragonHeadService = dragonHeadService;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            setValue(null);
        } else {
            Long id = Long.valueOf(text);
            DragonHead dragonHead = dragonHeadService.findById(id);
            setValue(dragonHead);
        }
    }
}