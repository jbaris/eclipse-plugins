package ar.com.fluxit.quick_search;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

/**
 * Activator
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class Activator extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "ar.com.fluxit.quick_search.plugin";

	private static Activator plugin;

	public static Activator getDefault() {
		return plugin;
	}

	public static ImageDescriptor getImageDescriptor(String path) {
		return imageDescriptorFromPlugin(PLUGIN_ID, path);
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}
}
