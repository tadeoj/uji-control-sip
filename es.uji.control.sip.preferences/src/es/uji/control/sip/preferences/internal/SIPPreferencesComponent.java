package es.uji.control.sip.preferences.internal;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.ComponentContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

import es.uji.control.domain.service.factoryselector.IControlConnectionFactorySelector;

@Component(name = "sip.preferences", immediate = true)
public class SIPPreferencesComponent {

	private BundleContext bundlecontext;
	static private IControlConnectionFactorySelector factorySelector;

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

	@Reference(policy = ReferencePolicy.STATIC, cardinality = ReferenceCardinality.MANDATORY, name = "sipControlConnectionFactorySelector")
	public void bindConnectionFactorySelector(IControlConnectionFactorySelector factorySelector) {
		this.factorySelector = factorySelector;
	}

	public void unbindConnectionFactorySelector(IControlConnectionFactorySelector factorySelector) {
		this.factorySelector = null;
	}

	public static IControlConnectionFactorySelector getSelector() {
		return factorySelector;
	}

}
