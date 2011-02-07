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
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
@SuppressWarnings("restriction")
public class StartupImpl implements IStartup {

	private static final String QUICK_INTERNET_SEARCH_KEY = "M1+M2+B";
	private static final String QUICK_INTERNET_SEARCH_COMMAND = "ar.com.fluxit.quick_search.internet_search.command";
	private static final String QUICK_FILE_SEARCH_KEY = "M1+M2+H";
	private static final Object QUICK_FILE_SEARCH_COMMAND = "ar.com.fluxit.quick_search.file_search.command";

	@Override
	public void earlyStartup() {
		removeBinding(QUICK_FILE_SEARCH_KEY, QUICK_FILE_SEARCH_COMMAND);
		removeBinding(QUICK_INTERNET_SEARCH_KEY, QUICK_INTERNET_SEARCH_COMMAND);
	}

	private void removeBinding(String keySequence, Object targetCommand) {
		try {
			// Quita los bindings de CTRL+SHIFT+P
			final Trigger keySecuence = KeyStroke
					.getInstance(keySequence);
			final IBindingService service = (IBindingService) Workbench
					.getInstance().getService(IBindingService.class);
			final List<Binding> toRemove = new ArrayList<Binding>();
			for (final Binding binding : service.getBindings()) {
				for (final Trigger trigger : binding.getTriggerSequence()
						.getTriggers()) {
					if (trigger.equals(keySecuence)
							&& !binding.getParameterizedCommand().getId()
									.equals(targetCommand)) {
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
