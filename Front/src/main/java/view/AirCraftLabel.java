package view;


import com.sothawo.mapjfx.MapLabel;

import java.util.Objects;

public class AirCraftLabel extends MapLabel {
    String newText;

    public AirCraftLabel(String text) {
        super(text);
        this.newText = text;
    }

    public AirCraftLabel(String text, int offsetX, int offsetY) {
        super(text, offsetX, offsetY);
        this.newText = text;
    }

    @Override
    public String getText() {
        return this.newText;
    }

    public void setNewText(String newText) {
        this.newText = newText;
    }

    @Override
    public MapLabel setVisible(boolean visible) {
        this.visibleProperty().set(visible);
        return this;
    }
}