package uk.ac.cam.dab80.oop.tick5;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.util.*;
import java.io.IOException;


public class GUILife extends JFrame implements ListSelectionListener {
	
	private World world;
	private PatternStore store;
	private ArrayList<World> cachedWorlds;
	
	private GamePanel gamePanel;
	private JButton playButton;
	private java.util.Timer timer;
	private boolean playing;
	
	private World copyWorld(boolean useCloning) {
		if (useCloning){
			try{
				return world.clone();
			}catch (CloneNotSupportedException e){
				// Should never fail to clone an existing world
				throw new RuntimeException(e);
			}
		// check which child class to use for copy method
		}else if(world instanceof ArrayWorld){
			return new ArrayWorld((ArrayWorld)world);
		}else{
			return new PackedWorld((PackedWorld)world);
		}
	}
	
	public GUILife(PatternStore ps) {
		super("Game of Life");
		store = ps;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024,768);
		
		add(createPatternsPanel(),BorderLayout.WEST);
		add(createControlPanel(),BorderLayout.SOUTH);
		add(createGamePanel(),BorderLayout.CENTER);
	}
	
	private void runOrPause() {
		if (world == null){return;}
		if (playing) {
			timer.cancel();
			playing=false;
			playButton.setText("Play");
		}
		else {
			playing=true;
			playButton.setText("Stop");
			timer = new java.util.Timer(true);
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					moveForward();
				}
			}, 0, 500);
		}
	}
	
	@Override
	public void valueChanged(ListSelectionEvent e){
		// stop animating when changing pattern
		if (playing){
			runOrPause();
		}
		// object on which the event occured, only possible to be a pattern list
		@SuppressWarnings("unchecked")
		JList<Pattern> list = (JList<Pattern>) e.getSource();
		Pattern p = list.getSelectedValue();
		cachedWorlds = new ArrayList<>();
		// use PackedWorld where possible
		if (p.getWidth() * p.getHeight() <= 64){
			world = new PackedWorld(p);
		}else{
			world = new ArrayWorld(p);
		}
		cachedWorlds.add(world);
		
		// tell gamePanel to update display
		gamePanel.display(world);
	}
	
	private void addBorder(JComponent component, String title) {
		Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
		Border tb = BorderFactory.createTitledBorder(etch,title);
		component.setBorder(tb);
	}
	
	private JPanel createGamePanel() {
		gamePanel = new GamePanel();
		addBorder(gamePanel,"Game Panel");
		return gamePanel;
	}
	
	
	private JPanel createPatternsPanel() {
		// specify borderLayout instead of flowLayout to fill space
		JPanel patt = new JPanel(new BorderLayout());
		addBorder(patt,"Patterns");
		// JList needs array, not list, for construction from values
		java.util.List<Pattern> patterns = store.getPatternsNameSorted();
		Pattern[] showPatterns = new Pattern[patterns.size()];
		// copy each element into array
		showPatterns = patterns.toArray(showPatterns);
		JList<Pattern> lst = new JList<>(showPatterns);
		// listen for value selection
		lst.addListSelectionListener(this);
		
		JScrollPane scroll = new JScrollPane(lst);
		
		// fill whole panel
		patt.add(scroll,BorderLayout.CENTER);		
		return patt; 
	}
	
	private JPanel createControlPanel() {
		// gridBagLayout to evenly fill space
		JPanel ctrl =  new JPanel(new GridBagLayout());
		addBorder(ctrl,"Controls");
		// fill horizontal space evenly 
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 1.0;
		
		JButton back = new JButton("< Back");
		back.addActionListener(e -> {if (playing){runOrPause();} 
									 moveBack();});
		ctrl.add(back,c);
		
		playButton = new JButton("Play");
		playButton.addActionListener(e -> runOrPause());
		ctrl.add(playButton,c);
		
		JButton forward = new JButton("Forward >");
		forward.addActionListener(e -> {if (playing){runOrPause();} 
									 moveForward();});
		ctrl.add(forward,c);
		return ctrl;
	}
	
	private void moveBack(){
		if (world.getGenerationCount() > 0){
			world = cachedWorlds.get(world.getGenerationCount() - 1);
		}
		gamePanel.display(world);
	}
	
	private void moveForward(){
		if (world.getGenerationCount() < cachedWorlds.size() - 1){
			world = cachedWorlds.get(world.getGenerationCount() + 1);
		}else{
			world = copyWorld(true);
			world.nextGeneration();
			// store newly created world in cache
			cachedWorlds.add(world);
		}
		gamePanel.display(world);
	}
	
	public static void main(String[] args) throws IOException {
		PatternStore ps = new PatternStore("https://www.cl.cam.ac.uk/teaching/1819/OOProg/ticks/life.txt");
		GUILife gui = new GUILife(ps);
		gui.setVisible(true);
		
	}
}