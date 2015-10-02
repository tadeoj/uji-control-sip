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
package es.uji.control.sip.preferences;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

import org.eclipse.e4.core.di.suppliers.ExtendedObjectSupplier;
import org.eclipse.jface.preference.IPreferenceStore;

/**
 * Annotation to get an {@link IPreferenceStore} injected via
 * {@link ExtendedObjectSupplier}. Intended to be used on migration paths, if
 * modifying the code for &#64;Preference annotation injection mechanism is not
 * simply possible.
 * 
 * <pre>
 * &#64;Inject
 * &#64;PrefStore(nodePath = "myNodePath")
 * IPreferenceStore store;
 * ...
 * store.addPropertyChangeListener(new IPropertyChangeListener() {
 * 			
 *     &#64;Override
 *     public void propertyChange(PropertyChangeEvent event) {
 *         // do something
 *     }
 * });
 * </pre>
 */
@Qualifier
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PrefStore {

	String nodePath() default "";
	
}
