package gui.anisotropy;

import java.awt.Label;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;

import gui.anisotropy.AnisotropyCoefficient.ValueChange;
import mpicbg.imglib.multithreading.SimpleMultiThreading;

public class ThresholdListener implements AdjustmentListener {
	final AnisotropyCoefficient parent;
	final Label label;
	final float min, max;
	final float log1001 = (float) Math.log10(1001);

	public ThresholdListener(
			final AnisotropyCoefficient parent,
			final Label label, final float min, final float max) {
		this.parent = parent;
		this.label = label;
		this.min = min;
		this.max = max;
	}

	@Override
	public void adjustmentValueChanged(final AdjustmentEvent event) {
		float threshold = min + ((log1001 - (float) Math.log10(1001 - event.getValue())) / log1001) * (max - min);
		parent.params.setThresholdDoG(threshold); 
				
		label.setText("Threshold = " + String.format(java.util.Locale.US, "%.4f", parent.params.getThresholdDoG()));

		if (!parent.isComputing) {
			parent.updatePreview(ValueChange.THRESHOLD);
		} else if (!event.getValueIsAdjusting()) {
			while (parent.isComputing) {
				SimpleMultiThreading.threadWait(10);
			}
			parent.updatePreview(ValueChange.THRESHOLD);
		}
	}
}
