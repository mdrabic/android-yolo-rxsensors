package com.mdrabic.rxsensors;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;

import mortar.Mortar;
import mortar.MortarActivityScope;
import mortar.MortarScope;

import static android.content.Intent.ACTION_MAIN;
import static android.content.Intent.CATEGORY_LAUNCHER;

public class MainActivity extends Activity {

    private MortarActivityScope activityScope;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isWrongInstance()) {
            finish();
            return;
        }

        MortarScope rootScope = Mortar.getScope(getApplication());
        activityScope = Mortar.requireActivityScope(rootScope, new Main());
        Mortar.inject(this, this);

        activityScope.onCreate(savedInstanceState);
        setContentView(new MainView(this));

    }

    @Override public Object getSystemService(@NonNull String name) {
        if (Mortar.isScopeSystemService(name)) {
            return activityScope;
        }
        return super.getSystemService(name);
    }

    @Override protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        activityScope.onSaveInstanceState(outState);
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        // activityScope may be null in case isWrongInstance() returned true in onCreate()
        if (isFinishing() && activityScope != null) {
            MortarScope parentScope = Mortar.getScope(getApplication());
            parentScope.destroyChild(activityScope);
            activityScope = null;
        }
    }

    /**
     * Dev tools and the play store (and others?) launch with a different intent, and so
     * lead to a redundant instance of this activity being spawned. <a
     * href="http://stackoverflow.com/questions/17702202/find-out-whether-the-current-activity-will-be-task-root-eventually-after-pendin"
     * >Details</a>.
     */
    private boolean isWrongInstance() {
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            boolean isMainAction = intent.getAction() != null && intent.getAction().equals(ACTION_MAIN);
            return intent.hasCategory(CATEGORY_LAUNCHER) && isMainAction;
        }
        return false;
    }
}
