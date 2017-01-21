package core;

import java.util.Observable;
import java.util.Observer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;

import topic.BandPassParallel;
import topic.BandPassSeries;
import topic.BandStopParallel;
import topic.BandStopSeries;
import topic.HighPassRCFilter;
import topic.HighPassRLFilter;
import topic.IntegratorRC;
import topic.LowPassRCFilter;
import topic.LowPassRLFilter;


public class View implements Observer, SelectionListener, DisposeListener {
	
	private final Controller controller;
	private final Model model;
	private final Ressource ressource;
		
	private final Display display;
	private final Shell shell;

	public View() {
		
		controller = Controller.getIntanceOf();
		model = Model.getIntanceOf();
		model.addObserver(this);
		ressource = Ressource.getIntanceOf();
		
		display = ressource.getDisplay();
		shell = new Shell(display, SWT.SHELL_TRIM);
		shell.addDisposeListener(this);
		shell.setLayout(new FillLayout());
		
		TabFolder tabFolder = new TabFolder(shell, SWT.TOP);
		
		TabItem[] tabItemArray = new TabItem[8];
		
//		tabItemArray[0] = new TabItem(tabFolder, SWT.NONE);
//		tabItemArray[0].setText("Intégrateur RC");
//		tabItemArray[0].setControl(new IntegratorRC(tabFolder, SWT.NONE));
		
		// Tab 1
		
		tabItemArray[0] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[0].setText("Filtre passe bas RC");
		tabItemArray[0].setControl(new LowPassRCFilter(tabFolder, SWT.NONE));
		
		// Tab 2

		tabItemArray[1] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[1].setText("Filtre passe haut RC");
		tabItemArray[1].setControl(new HighPassRCFilter(tabFolder, SWT.NONE));
		
		// Tab 3
		
		tabItemArray[2] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[2].setText("Filtre passe bas RL");
		tabItemArray[2].setControl(new LowPassRLFilter(tabFolder, SWT.NONE));
		
		// Tab 4
		
		tabItemArray[3] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[3].setText("Filtre haut bas RL");
		tabItemArray[3].setControl(new HighPassRLFilter(tabFolder, SWT.NONE));
		
		// Tab 5
		
		tabItemArray[4] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[4].setText("Filtre passe bande série");
		tabItemArray[4].setControl(new BandPassSeries(tabFolder, SWT.NONE));
		
		// Tab 6
		
		tabItemArray[5] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[5].setText("Filtre coupe bande série");
		tabItemArray[5].setControl(new BandStopSeries(tabFolder, SWT.NONE));
		
		// Tab 7
		
		tabItemArray[6] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[6].setText("Filtre passe bande RLC");
		tabItemArray[6].setControl(new BandPassParallel(tabFolder, SWT.NONE));
		
		// Tab 8
		
		tabItemArray[7] = new TabItem(tabFolder, SWT.NONE);
		tabItemArray[7].setText("Filtre passe bande RLC");
		tabItemArray[7].setControl(new BandStopParallel(tabFolder, SWT.NONE));
		
		
		// SHELL STUFF		
		shell.pack();
		shell.setText("Les filtres passifs");			
	}
	
	public void start(){
		
		shell.open();
		
		while(!shell.isDisposed()){
			
			if(!display.readAndDispatch()){
				
				display.sleep();
			}			
		}
		
		this.stop();
	}
	
	public void stop(){
		
		//Ce code est peut-être inutile, je n'en suis pas encore certain
		if(!shell.isDisposed()){
			
			shell.close();
		}
		
		//controller.viewActionDispose();
		
		display.dispose();	
	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		display.asyncExec(new UpdateThread());
	}
	
	private class UpdateThread implements Runnable{

		@Override
		public void run() {
		
		}
	}

	
	@Override
	public void widgetSelected(SelectionEvent e) {
		
		// TIMER
		
		if(e.widget == null){
			
				
		}
		
		else if(e.widget == null){
			
	
		}
		
		else{
			
			System.err.println("Widget non pris en charge par le listener");
		}
	}
	

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
		System.err.println("Oups, il fallait faire qqch");		
	}

	@Override
	public void widgetDisposed(DisposeEvent e) {
		
		controller.viewActionDispose();
		
	}
}
