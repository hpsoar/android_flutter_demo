package me.lattern.redux.store;

public abstract class AbsDisposable implements Disposable {
    public void addTo(CompoundDisposable disposable) {
        disposable.add(this);
    }
}
