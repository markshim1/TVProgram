package com.example.markshim.tvprogram.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.markshim.tvprogram.R;
import com.example.markshim.tvprogram.databinding.ConflictActivityBinding;
import com.example.markshim.tvprogram.di.DaggerServerRequestComponent;
import com.example.markshim.tvprogram.di.ServerRequestComponent;
import com.example.markshim.tvprogram.model.Program;
import com.example.markshim.tvprogram.model.ProgramService;
import com.example.markshim.tvprogram.viewmodel.ConflictViewModel;

import javax.inject.Inject;

/**
 * Created by Mark Shim on 2018-01-21.
 */

public class ConflictActivity extends AppCompatActivity {
    private static final String TAG = ConflictActivity.class.getSimpleName();
    private static final String PROGRAM = "PROGRAM";
    private ConflictActivityBinding conflictActivityBinding;
    private ConflictViewModel conflictViewModel;
    private Program program;
    private ServerRequestComponent component;

    @Inject
    ProgramService programService;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initComponent();
        initBinding();
    }

    private void initComponent() {
        component = DaggerServerRequestComponent.builder().build();
        component.inject(this);
    }

    public static Intent getStartIntent(Context context, Program program) {
        Intent intent = new Intent(context, ConflictActivity.class);
        intent.putExtra(PROGRAM, program);
        return intent;
    }

    public void btnBack(View view) {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        upIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
            TaskStackBuilder.create(this)
                    .addNextIntentWithParentStack(upIntent)
                    .startActivities();
        } else {
            NavUtils.navigateUpTo(this, upIntent);
        }
    }

    private void initBinding() {
        program = (Program) getIntent().getSerializableExtra(PROGRAM);
        conflictActivityBinding = DataBindingUtil.setContentView(this, R.layout.conflict_activity);
        conflictViewModel = new ConflictViewModel(this, program, programService);
        conflictActivityBinding.setConflictViewModel(conflictViewModel);
    }
}
