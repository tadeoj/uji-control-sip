package es.uji.control.sip.preferences.internal;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.eclipse.jface.preference.ComboFieldEditor;
import org.eclipse.jface.preference.FieldEditorPreferencePage;

import es.uji.control.domain.service.factoryselector.ConnectionFactoryKey;
import es.uji.control.domain.service.factoryselector.IControlConnectionFactorySelector;


public class SIPSelectorPreferencesPage extends FieldEditorPreferencePage {

	private IControlConnectionFactorySelector selector;
	
	public SIPSelectorPreferencesPage() {
		super(GRID);
	}
	
	@Override
	protected void createFieldEditors() {
		
		ConnectionFactoryKey currentFactoryKey = selector.getCurrentFactoryKey();
		Set<ConnectionFactoryKey> factoryKeys = selector.getFactoryKeys();
		
		Iterator<ConnectionFactoryKey> iterator = factoryKeys.iterator();
		List<String> connections = new ArrayList<>(1);
		
		while (iterator.hasNext()) {
			ConnectionFactoryKey key = (ConnectionFactoryKey) iterator.next();
			connections.add(selector.getFactoryDescription(key));
		}
	
		addField(new ComboFieldEditor("prefCombo", "Elija una conexión:", new String[][] { { "display1", "value1" }, { "display2", "value2" } }, getFieldEditorParent()));
		
	}
	
	@Override
	public boolean performOk() {
		boolean result = super.performOk();
		return result;
	}
	

}
