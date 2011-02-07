package ar.com.fluxit.quick_search.handlers;

import java.net.URL;
import java.util.Collection;
import java.util.Iterator;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.expressions.EvaluationContext;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.browser.IWorkbenchBrowserSupport;

import ar.com.fluxit.quick_search.Activator;

/**
 * Handler de Quick Internet Search
 * 
 * @author Juan Barisich (<a href="mailto:juan.barisich@gmail.com">juan.barisich@gmail.com</a>)
 */
public class QuickInternetSearchHandler extends AbstractHandler {

	private final String url;

	public QuickInternetSearchHandler() {
		url = Platform.getResourceString(Activator.getDefault().getBundle(),
				"%quick_search.internet_search.url");
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			final EvaluationContext context = (EvaluationContext) event
					.getApplicationContext();
			final Collection<?> set = (Collection<?>) context
					.getDefaultVariable();
			Iterator<?> iterator = set.iterator();
			if (iterator.hasNext()) {
				final ITextSelection selection = (ITextSelection) iterator
						.next();
				final String textSelecion = selection.getText();
				final String _url = this.url.replace("%%", textSelecion);
				PlatformUI
						.getWorkbench()
						.getBrowserSupport()
						.createBrowser(IWorkbenchBrowserSupport.AS_EXTERNAL,
								"aCustomId", "url", "url")
						.openURL(new URL(_url));
			}
		} catch (Exception e) {
			throw new IllegalStateException(e);
		}
		return null;
	}

}
