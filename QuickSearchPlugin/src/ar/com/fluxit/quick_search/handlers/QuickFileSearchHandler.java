package ar.com.fluxit.quick_search.handlers;

import java.lang.reflect.Field;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.runtime.Platform;
import org.eclipse.search.internal.ui.SearchDialog;
import org.eclipse.search.internal.ui.text.TextSearchPage;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.ui.internal.Workbench;

import ar.com.fluxit.quick_search.Activator;

/**
 * Handler de Quick File Search
 * 
 * @author jbaris
 */
@SuppressWarnings("restriction")
public class QuickFileSearchHandler extends AbstractHandler {

	private final String extensions;

	public QuickFileSearchHandler() {
		extensions = Platform.getResourceString(Activator.getDefault()
				.getBundle(),
				"%quick_search.file_search.default_file_extension");
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			final SearchDialog dialog = new SearchDialog(Workbench
					.getInstance().getActiveWorkbenchWindow(),
					"org.eclipse.search.internal.ui.text.TextSearchPage");
			dialog.create();
			final TextSearchPage page = (TextSearchPage) dialog
					.getSelectedPage();
			final Field declaredField = page.getClass().getDeclaredField(
					"fExtensions");

			declaredField.setAccessible(true);
			final Combo fExtensions = (Combo) declaredField.get(page);
			fExtensions.setText(extensions);
			dialog.open();
		} catch (final Exception e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

}
