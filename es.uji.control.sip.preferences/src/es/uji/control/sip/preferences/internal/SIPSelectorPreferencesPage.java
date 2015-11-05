package es.uji.control.sip.preferences.internal;

import java.util.Iterator;
import java.util.Set;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;

import es.uji.control.domain.service.factoryselector.ConnectionFactoryKey;
import es.uji.control.domain.service.factoryselector.IControlConnectionFactorySelector;


public class SIPSelectorPreferencesPage extends FieldEditorPreferencePage {

	private IControlConnectionFactorySelector selector;
	private ComboFieldEditor combo;
	private String selected;
	
	public SIPSelectorPreferencesPage() {
		super(GRID);
	}
	
	@Override
	protected void createFieldEditors() {
		
		String[][] connections = new String[][]{};
		selector = SIPPreferencesComponent.getSelector();
		
		if (selector != null) {
			ConnectionFactoryKey currentFactoryKey = selector.getCurrentFactoryKey();
			Set<ConnectionFactoryKey> factoryKeys = selector.getFactoryKeys();
			
			ConnectionFactoryKey[] array = factoryKeys.toArray(new ConnectionFactoryKey[factoryKeys.size()]);
			connections = new String[factoryKeys.size()][2];
						
			for (int i = 0; i < array.length; i++) {
				connections[i][0] = selector.getFactoryDescription(array[i]);
				connections[i][1] = array[i].toString();
			}
		}
		
		combo = new ComboFieldEditor("prefCombo", "Elija una conexión:", connections, getFieldEditorParent());
		addField(combo);
		
	}
	
	public String getSelectedValue() { 
		return combo.getPreferenceStore().getString(combo.getPreferenceName());
	}
	
	public ConnectionFactoryKey getConnectionFactoryKey(String value) {
	
		if (selector != null) {
			Set<ConnectionFactoryKey> factoryKeys = selector.getFactoryKeys();
			
			Iterator<ConnectionFactoryKey> iterator = factoryKeys.iterator();
			
			while (iterator.hasNext()) {
				ConnectionFactoryKey key = iterator.next();
				if (key.toString().equals(value)) {
					return key;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		String selected = getSelectedValue();
 		ConnectionFactoryKey key = getConnectionFactoryKey(selected);
		if (key != null) {
			selector.setCurrentFactoryKey(key);
		}
		return result;
	}

}
