package topic;

import org.eclipse.swt.widgets.Composite;

import core.Ressource;
import electrical.Complex;
import electrical.Electrical;

public class LowPassRLFilter extends AbstractRLFilter {

	public LowPassRLFilter(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void loadImage() {
		Ressource ressource = Ressource.getIntanceOf();
		
		schematicNoLoad = ressource.getFilterImage(Ressource.TYPE_LOW_PASS_RL, false);
		schematiWithLoad = ressource.getFilterImage(Ressource.TYPE_LOW_PASS_RL, true);
	}

	@Override
	protected void recalculate(double f) {
		
		Complex zl;
		Complex zr;
		Complex zrl;
		
		fc = r / (2 * Math.PI * l);
		
		xl = Electrical.xl(f, l);
		
		zl = Electrical.zl(xl);
		zr = Electrical.zr(r);
		zrl = Electrical.zr(rl);
		
		z2 = Electrical.zParallel(zr, zrl);
		zt = Electrical.zSeries(zl, z2);
		
		a = Complex.div(z2, zt);
		phi = a.angle();
		adb = Electrical.db(a.abs());
	}
}
