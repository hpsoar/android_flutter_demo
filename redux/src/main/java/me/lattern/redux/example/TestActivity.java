package me.lattern.redux.example;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.lattern.redux.Logic;
import me.lattern.redux.store.RdxStateChangeListener;

public class TestActivity extends Activity {
    private Logic<TestState> logic_;

    Button btn_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        logic_ = new Logic<>(this, new TestState(), new TestReducer(), new TestEffect(), null);

        logic_.subscribe(new RdxStateChangeListener() {
            @Override
            public void onStateChanged(Object o) {
                // update page
            }
        });

        btn_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logic_.dispatch(TestActions.action1(null));
            }
        });
    }
}
