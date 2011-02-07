package ar.com.fluxit.quick_search.startup;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.bindings.Binding;
import org.eclipse.jface.bindings.Trigger;
import org.eclipse.jface.bindings.keys.KeyStroke;
import org.eclipse.ui.IStartup;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.keys.BindingService;
import org.eclipse.ui.keys.IBindingService;

/**
 * Inicializador del ambiente
 * 
 * @author jbaris
 */
@SuppressWarnings("restriction")
public class StartupImpl implements IStartup {

	private static final String QUICK_SEARCH_KEY_SEQUENCE = "M1+M2+H";
	private static final Object QUICK_SEARCH_COMMAND = "ar.com.fluxit.quick_search.file_search.command";

	@Override
	public void earlyStartup() {
		try {
			// Quita los bindings de CTRL+SHIFT+P
			final Trigger keySecuence = KeyStroke
					.getInstance(QUICK_SEARCH_KEY_SEQUENCE);
			final IBindingService service = (IBindingService) Workbench
					.getInstance().getService(IBindingService.class);
			final List<Binding> toRemove = new ArrayList<Binding>();
			for (final Binding binding : service.getBindings()) {
				for (final Trigger trigger : binding.getTriggerSequence()
						.getTriggers()) {
					if (trigger.equals(keySecuence)
							&& !binding.getParameterizedCommand().getId()
									.equals(QUICK_SEARCH_COMMAND)) {
						toRemove.add(binding);
					}
				}
			}
			Workbench.getInstance().getDisplay().syncExec(new Runnable() {

				@Override
				public void run() {
					for (final Binding binding : toRemove) {
						((BindingService) service).removeBinding(binding);
					}
				}
			});

		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
	}
}
