package me.lattern.redux.store;

import java.util.ArrayList;
import java.util.List;

public class CompoundDisposable implements Disposable {
    List<Disposable> disposables = new ArrayList<>();

    void add(Disposable disposable) {
        disposables.add(disposable);
    }

    @Override
    public void dispose() {
        for (Disposable d: disposables) {
            d.dispose();
        }
    }
}
