package ar.com.fluxit.packageExplorerAddOns.startup;

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

	private static final String PACKAGE_EXPLORER_FINDER_KEY_SEQUENCE = "M1+M2+P";
	private static final Object PACKAGE_EXPLORER_FINDER_COMMAND = "ar.com.fluxit.packageExplorerAddOns.commands.PackageExplorerOpenDialogAction";

	@Override
	public void earlyStartup() {
		try {
			// Quita los bindings de CTRL+SHIFT+P
			final Trigger keySecuence = KeyStroke
					.getInstance(PACKAGE_EXPLORER_FINDER_KEY_SEQUENCE);
			final IBindingService service = (IBindingService) Workbench
					.getInstance().getService(IBindingService.class);
			final List<Binding> toRemove = new ArrayList<Binding>();
			for (final Binding binding : service.getBindings()) {
				for (final Trigger trigger : binding.getTriggerSequence()
						.getTriggers()) {
					if (trigger.equals(keySecuence)
							&& !binding.getParameterizedCommand().getId()
									.equals(PACKAGE_EXPLORER_FINDER_COMMAND)) {
						toRemove.add(binding);
					}
				}
			}
			Workbench.getInstance().getDisplay().asyncExec(new Runnable() {

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
