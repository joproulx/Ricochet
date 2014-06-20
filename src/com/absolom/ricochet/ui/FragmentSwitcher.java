package com.absolom.ricochet.ui;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

public class FragmentSwitcher {
	private static FragmentSwitcher m_instance;
	
	public static void createInstance(FragmentManager fragmentManager, int containerId) {
		m_instance = new FragmentSwitcher(fragmentManager, containerId);
	}
	
	public static FragmentSwitcher getInstance(){
		return m_instance;
	}
	
	
	private int m_containerId;
	private FragmentManager m_fragmentManager;

	public FragmentSwitcher(FragmentManager fragmentManager, int containerId) {
		m_containerId = containerId;
		m_fragmentManager = fragmentManager;
	}

	public void gotoFragment(Fragment fragment) {
		FragmentTransaction transaction = m_fragmentManager.beginTransaction();
		transaction.replace(m_containerId, fragment);
		transaction.addToBackStack(null);
		transaction.commit();
	}
	
	public void gotoBack() {
		m_fragmentManager.popBackStack();
	}

}
