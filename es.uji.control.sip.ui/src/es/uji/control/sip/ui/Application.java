package es.uji.control.sip.ui;

import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.model.ModuleGroupNode;
import org.eclipse.riena.navigation.model.ModuleNode;
import org.eclipse.riena.navigation.ui.swt.application.SwtApplication;

public class Application extends SwtApplication {
	
	static public final String ID_GROUP_GBOXES = "es.uji.control.sip.ui.groupGestionBoxes";
	static public final String ID_BUSQUEDA_USUARIO = "es.uji.control.sip.ui.views.ViewSearchUser";
	static public final String ID_CONSULTA_TARJETA = "es.uji.control.sip.ui.views.ViewSearchCard";
	static public final String ID_USUARIOS = "es.uji.control.sip.ui.views.ViewEditUser";
	
	private ModuleNode moduleNodeUserSearch;
	private ModuleNode moduleNodeCardSearch;
	private ModuleNode moduleNodeUsers;

	static private ISubModuleNode moduleNodeUsersSubModule;
	static private ModuleGroupNode groupApplicationboxes;

	public Application() {
		
	}
}
