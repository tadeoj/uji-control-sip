package es.uji.control.sip.ui.parts;

import javax.inject.Inject;

import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class UsersPart {

	@Inject
	public UsersPart(Composite parent) {
		parent.setLayout(new GridLayout(1, false));
		
		
	}
	
}
