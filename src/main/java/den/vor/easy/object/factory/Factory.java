package den.vor.easy.object.factory;

import den.vor.easy.object.value.ref.FieldRef;

import java.util.Collections;
import java.util.List;

public abstract class Factory<T> {

    private boolean visible = true;

    public abstract Generator<T> getGenerator();

    public List<FieldRef> getDependencies() {
        return Collections.emptyList();
    }

    public boolean isVisible() {
        return visible;
    }

    public Factory<T> setVisible(boolean visible) {
        this.visible = visible;
        return this;
    }

    public Factory<T> invisible() {
        return setVisible(false);
    }
}
