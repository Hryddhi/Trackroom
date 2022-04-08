package com.rifatul.trackroom;

import android.content.Intent;
import android.os.Bundle;

public class ActivityDetailedPost extends BaseDataActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_post);

        Intent ClassroomInfo = getIntent();
        int classPK = ClassroomInfo.getIntExtra("classPk", 0);
    }
}
