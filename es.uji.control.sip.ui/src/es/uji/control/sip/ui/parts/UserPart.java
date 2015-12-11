package es.uji.control.sip.ui.parts;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.e4.core.di.annotations.Optional;
import org.eclipse.e4.ui.di.UIEventTopic;
import org.eclipse.e4.ui.services.IServiceConstants;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;

import es.uji.control.domain.people.IPerson;
import es.uji.control.sip.ui.Activator;

public class UserPart {

	static public final String ID = "es.uji.control.sip.ui.partStack.part.user.id";
	
	private Label labelName;
	private Label labelDni;
	private Label labelFirstLastName;
	private Label labelSecondLastName;
	private Label textName;
	private Label textDni;
	private Label textFirstLastName;
	private Label textSecondLastName;
	private Label foto;
	private ImageRegistry imageRegistry;
	private Image image;
	private ImageDescriptor descriptor;

	@PostConstruct
	public void createComposite(Composite parent, @Optional @Named(IServiceConstants.ACTIVE_SELECTION) IPerson person) {
		parent.setLayout(new GridLayout(1, false));
		Composite banner = createBanner(parent);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(banner);
		
		updatePerson(person);
	}

	private Composite createBanner(Composite parent) {

		Composite banner = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(3, true);
		banner.setLayout(layout);
		
		imageRegistry = JFaceResources.getImageRegistry();
		descriptor = ImageDescriptor.createFromURL(FileLocator.find(Activator.getContext().getBundle(), new Path("/icons/userblack.png"), null));
		image = descriptor.createImage();
		
		foto = new Label(banner, SWT.NONE); 
		foto.setText("");
		foto.setImage(image);
		foto.setSize(100, 100);
		GridDataFactory.fillDefaults().grab(false, false).align(SWT.BEGINNING, SWT.TOP).span(1, 4).indent(20, 20).applyTo(foto);

		labelName = new Label(banner, SWT.NONE);
		labelName.setText("Nombre");
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.BEGINNING, SWT.TOP).indent(0, 20).applyTo(labelName);

		textName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).indent(0, 20).applyTo(textName);

		labelDni = new Label(banner, SWT.NONE);
		labelDni.setText("DNI");
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.BEGINNING, SWT.TOP).applyTo(labelDni);

		textDni = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(textDni);

		labelFirstLastName = new Label(banner, SWT.NONE);
		labelFirstLastName.setText("Primer apellido");
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.BEGINNING, SWT.TOP).applyTo(labelFirstLastName);

		textFirstLastName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(textFirstLastName);

		labelSecondLastName = new Label(banner, SWT.NONE);
		labelSecondLastName.setText("Segundo apellido");
		GridDataFactory.fillDefaults().grab(true, true).align(SWT.BEGINNING, SWT.TOP).applyTo(labelSecondLastName);

		textSecondLastName = new Label(banner, SWT.FILL | SWT.WRAP);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(textSecondLastName);

		return banner;
	}
	
	private void updatePerson(IPerson person) {
		if (person != null) {
			textName.setText(person.getName());
			textFirstLastName.setText(person.getFirstLastName());
			textSecondLastName.setText(person.getSecondLastName());
			textDni.setText(person.getIdentification());
		} else {
			textName.setText("");
			textFirstLastName.setText("");
			textSecondLastName.setText("");
			textDni.setText("");
		}		
	}
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("SELECT_PERSON") IPerson person) {
		updatePerson(person);
	}
	
	@Inject 
	@Optional
	private void setperson(@UIEventTopic("CLEAN_PERSON") boolean clean) {
		textName.setText("");
		textFirstLastName.setText("");
		textSecondLastName.setText("");
		textDni.setText("");
	}
	
	@PreDestroy
	private void destroy() {
		descriptor.destroyResource(image);
		imageRegistry.dispose();
	}

}
