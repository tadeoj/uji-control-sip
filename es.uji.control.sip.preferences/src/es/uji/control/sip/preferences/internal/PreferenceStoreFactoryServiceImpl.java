/*******************************************************************************
 * Copyright (c) 2015 Dirk Fauth.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Dirk Fauth <dirk.fauth@googlemail.com> - initial API and implementation
 *******************************************************************************/
package es.uji.control.sip.preferences.internal;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.preferences.InstanceScope;
import org.eclipse.jface.preference.IPreferenceStore;

import es.uji.control.sip.preferences.IPreferenceStoreFactoryService;

/**
 * Implementation of {@link IPreferenceStoreFactoryService} that creates and manages instances of 
 * {@link ScopedPreferenceStore} for the instance scope and specified qualifiers.
 */
public class PreferenceStoreFactoryServiceImpl implements IPreferenceStoreFactoryService {

	protected Map<String, IPreferenceStore> storeReferences = new HashMap<>();
	
	@Override
	public IPreferenceStore getPreferenceStoreInstance(String qualifier) {
		if (!this.storeReferences.containsKey(qualifier)) {
			this.storeReferences.put(qualifier, new ScopedPreferenceStore(InstanceScope.INSTANCE, qualifier));
		}
		return this.storeReferences.get(qualifier);
	}

}
