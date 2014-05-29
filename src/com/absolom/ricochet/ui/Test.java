package com.absolom.ricochet.ui;

import android.content.Context;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class Test extends SurfaceView implements SurfaceHolder.Callback {

	@Override
	public void surfaceCreated(SurfaceHolder p1) {
		// TODO: Implement this method
	}

	@Override
	public void surfaceChanged(SurfaceHolder p1, int p2, int p3, int p4) {
		// TODO: Implement this method
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder p1) {
		// TODO: Implement this method
	}

	int m_test;

	public Test(Context context) {
		super(context);
	}
}
