package es.uji.control.sip.ui.preferences.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import es.uji.control.domain.provider.service.factoryselector.IControlConnectionFactorySelector;

@Component(name = "sip.ui.preferences", immediate = true)
public class SIPPreferencesComponent {

	private BundleContext bundlecontext;
	static public IControlConnectionFactorySelector factorySelector;

	@Activate
	public void activate(ComponentContext componentContext) throws Exception {
		this.bundlecontext = componentContext.getBundleContext();
	}

	@Deactivate
	public void deactivate(ComponentContext componentContext) {
		this.bundlecontext = null;
	}

	public BundleContext getContext() {
		return this.bundlecontext;
	}

	@Reference(policy = ReferencePolicy.DYNAMIC, cardinality = ReferenceCardinality.MANDATORY, name = "sipControlConnectionFactorySelector")
	public void bindConnectionFactorySelector(IControlConnectionFactorySelector factorySelector) {
		this.factorySelector = factorySelector;
	}

	public void unbindConnectionFactorySelector(IControlConnectionFactorySelector factorySelector) {
		this.factorySelector = null;
	}

}
