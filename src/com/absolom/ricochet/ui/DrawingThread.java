package com.absolom.ricochet.ui;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.os.SystemClock;
import android.util.Log;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class DrawingThread extends Thread {
	private boolean m_running = false;

	private SurfaceHolder m_surfaceHolder;
	private IDrawingThreadListener m_listener;

	private long m_elapsedTime;

	public DrawingThread(SurfaceHolder surfaceHolder, IDrawingThreadListener listener) {
		m_surfaceHolder = surfaceHolder;
		m_listener = listener;

	}

	@Override
	public void start() {
		m_elapsedTime = SystemClock.elapsedRealtime();
		setRunning(true);
		super.start();
	}

	public void stopAndWait() {
		boolean retry = true;
		setRunning(false);
		while (retry) {
			try {
				join();
				retry = false;
			} 
			catch (InterruptedException e) {
			}
		}
	}

	@Override
	public void run() {
		while (m_running) {
			Canvas c = null;
			try {
				if (!m_surfaceHolder.getSurface().isValid()) {
					continue;
				}

				c = m_surfaceHolder.lockCanvas(null);
				synchronized (m_surfaceHolder) {
					if (c != null) {
						long newElapsed = SystemClock.elapsedRealtime();
						m_listener.drawCanvas(c, newElapsed - m_elapsedTime);
						m_elapsedTime = newElapsed;
					}
				}
			} 
			catch(RuntimeException ex){
				Log.e("DrawingThread", ex.toString());
			}
			finally {
				try {
					sleep(10);
				} catch (InterruptedException e) {
				}
				if (c != null) {
					m_surfaceHolder.unlockCanvasAndPost(c);
				}
			}
		}
	}

	private void setRunning(boolean b) {
		m_running = b;
	}
}
