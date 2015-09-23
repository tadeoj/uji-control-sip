package es.uji.control.sip.ui;

import org.eclipse.riena.navigation.IApplicationNode;
import org.eclipse.riena.navigation.ISubApplicationNode;
import org.eclipse.riena.navigation.ISubModuleNode;
import org.eclipse.riena.navigation.NavigationNodeId;
import org.eclipse.riena.navigation.model.ApplicationNode;
import org.eclipse.riena.navigation.model.ModuleGroupNode;
import org.eclipse.riena.navigation.model.ModuleNode;
import org.eclipse.riena.navigation.model.SubApplicationNode;
import org.eclipse.riena.navigation.model.SubModuleNode;
import org.eclipse.riena.navigation.ui.controllers.ApplicationController;
import org.eclipse.riena.navigation.ui.swt.application.SwtApplication;
import org.eclipse.riena.ui.workarea.WorkareaManager;

import es.uji.control.sip.ui.views.ViewVoid;

public class Application extends SwtApplication {
	
	static public final String ID_GROUP_GBOXES = "es.uji.control.sip.groupSip";
	static public final String ID_BUSQUEDA_USUARIO = "es.uji.control.sip.ui.views.ViewSearchUser";
	static public final String ID_CONSULTA_TARJETA = "es.uji.control.sip.ui.views.ViewSearchCard";
	static public final String ID_USUARIOS = "es.uji.control.sip.ui.views.ViewEditUser";
	
	private ModuleNode moduleNodeUserSearch;
	private ModuleNode moduleNodeCardSearch;
	private ModuleNode moduleNodeUsers;

	static private ISubModuleNode moduleNodeUsersSubModule;
	static private ModuleGroupNode groupApplication;
	static private int usuariosId;
	
	@Override
	protected ApplicationController createApplicationController(IApplicationNode node) {
		ApplicationController controller = super.createApplicationController(node);
		controller.setMenubarVisible(true);
		return controller;
	}
	
	@Override
	public IApplicationNode createModel() {
		
		IApplicationNode app = new ApplicationNode("SIP-" + Activator.getDefault().getBundle().getVersion().toString());
		app.setIcon("tray.bmp");
		
		ISubApplicationNode subApp = new SubApplicationNode(new NavigationNodeId("SIP"),"SIP");
		WorkareaManager.getInstance().registerDefinition(subApp, "es.uji.control.tag.ui.perspective");
		app.addChild(subApp);

		groupApplication = new ModuleGroupNode(new NavigationNodeId(Application.ID_GROUP_GBOXES));
		subApp.addChild(groupApplication);
		
		moduleNodeUserSearch = new ModuleNode(new NavigationNodeId(Application.ID_BUSQUEDA_USUARIO), "Busqueda de usuario");
		moduleNodeUserSearch.setClosable(false);
		moduleNodeUserSearch.setIcon("zoom.png");
		ISubModuleNode moduleNodeUserSearchSubModule = new SubModuleNode(new NavigationNodeId("moduleNodeUserSearchSubModule"), "Busqueda de usuario");
		WorkareaManager.getInstance().registerDefinition(moduleNodeUserSearchSubModule, ViewSearchUser.ID);
		moduleNodeUserSearch.addChild(moduleNodeUserSearchSubModule);
		groupApplication.addChild(moduleNodeUserSearch);

		moduleNodeCardSearch = new ModuleNode(new NavigationNodeId(Application.ID_CONSULTA_TARJETA),"Consulta de tarjeta");
		moduleNodeCardSearch.setClosable(false);
		moduleNodeCardSearch.setIcon("vcard.png");
		ISubModuleNode moduleNodeCardSearchSubModule = new SubModuleNode(new NavigationNodeId("moduleNodeCardSearchSubModule"), "Consulta de tarjeta");
		WorkareaManager.getInstance().registerDefinition(moduleNodeCardSearchSubModule, ViewSearchCard.ID);
		moduleNodeCardSearch.addChild(moduleNodeCardSearchSubModule);
		groupApplication.addChild(moduleNodeCardSearch);

		moduleNodeUsers = new ModuleNode(new NavigationNodeId(Application.ID_USUARIOS), "Usuarios");
		moduleNodeUsers.setClosable(false);
		moduleNodeUsers.setIcon("group.png");
		moduleNodeUsersSubModule = new SubModuleNode(new NavigationNodeId("moduleNodeUsersSubModule"), "Usuarios");
		WorkareaManager.getInstance().registerDefinition(moduleNodeUsersSubModule, ViewVoid.ID);
		moduleNodeUsers.addChild(moduleNodeUsersSubModule);
		groupApplication.addChild(moduleNodeUsers);

 		return app;
	}
	
	static public void generateSubModule(String label) {
		boolean repetido = false; 
		int j = -1;
		int size = moduleNodeUsersSubModule.getChildren().size();
				
		if(size != 0){
			for(int i = 0; i < size; i++) {
				if(label.compareTo(moduleNodeUsersSubModule.getChild(i).getLabel()) == 0 ) {
					repetido  = true;
					j = i;
				}
			}
			if(repetido == true) {
				moduleNodeUsersSubModule.getChild(j).activate();
			} else {
				ISubModuleNode usuarioNuevo = new SubModuleNode(new NavigationNodeId("usuarioNuevo", String.valueOf(usuariosId++)), label);
				usuarioNuevo.setIcon("user.png");
				WorkareaManager.getInstance().registerDefinition(usuarioNuevo, ViewEditUser.ID);
				moduleNodeUsersSubModule.addChild(usuarioNuevo);
				usuarioNuevo.activate();
			}
		} else {
			ISubModuleNode usuarioNuevo = new SubModuleNode(new NavigationNodeId("usuarioNuevo", String.valueOf(usuariosId++)), label);
			usuarioNuevo.setIcon("user.png");
			WorkareaManager.getInstance().registerDefinition(usuarioNuevo, ViewEditUser.ID);
			moduleNodeUsersSubModule.addChild(usuarioNuevo);
			usuarioNuevo.activate();
		}	
	}
	
	static public ModuleGroupNode getModuleGroupNode() {
		return groupApplication;
	}
	
}
