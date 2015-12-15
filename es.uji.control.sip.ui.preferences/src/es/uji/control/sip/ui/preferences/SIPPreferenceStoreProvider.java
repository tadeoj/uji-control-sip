package es.uji.control.sip.ui.preferences;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import com.opcoach.e4.preferences.IPreferenceStoreProvider;
import com.opcoach.e4.preferences.ScopedPreferenceStore;

public class SIPPreferenceStoreProvider implements IPreferenceStoreProvider {

	private IPreferenceStore store;
	
	@Override
	public IPreferenceStore getPreferenceStore() {
		store  = new ScopedPreferenceStore(InstanceScope.INSTANCE, "es.uji.control.sip.ui.preferences");
		return store;
	}
	
}

