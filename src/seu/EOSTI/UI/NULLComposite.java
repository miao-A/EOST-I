package seu.EOSTI.UI;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;

public class NULLComposite extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public NULLComposite(Composite parent, int style) {
		super(parent, style);
		
		Label lblNull = new Label(this, SWT.NONE);
		lblNull.setBounds(36, 64, 61, 17);
		lblNull.setText("Null");

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
