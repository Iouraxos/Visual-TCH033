package graph;

import java.util.ArrayList;

import electrical.Electrical;

public class PhaseGraphArea extends LogGraphArea {

	public PhaseGraphArea() {

		setAxisLimits(1.00e0, 1.00e6, -90., 90.);
	}

	@Override
	protected void recalculateYAxisGraduationList() {

		yAxisGraduationList = new ArrayList<Graduation>();

		final int primaryGraduationSpan = 45;
		final int secondaryGraduationSpan = 5;

		String tempLabel = null;

		// Je sais que c'est loin d'être la manière la plus performante de faire
		// les chose, mais d'un autre coté ça assure que les axes vont être
		// centré sur 0°
		for (int i = (int) yAxisMin; i <= (int) yAxisMax; i++) {

			// Si on est sur un multiple du secondaire 
			if(i % secondaryGraduationSpan == 0){
				
				//Si on est sur un multiple du primaire
				if (i % primaryGraduationSpan == 0) {
	
					tempLabel = Electrical.formatEngineer((double)i, 3);
				}
	
				else {
	
					tempLabel = null;
				}
	
				yAxisGraduationList.add(new Graduation(i, tempLabel));	
			}
		}
	}
}
