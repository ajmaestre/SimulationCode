
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.RenderingHints;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.BasicStroke;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.Border;
import javax.swing.border.MatteBorder;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;

import java.text.DecimalFormat;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;


// CLASE PRINCIPAL
public class Engine {

	public static void main(String[] args) {
		Panel panel = new Panel();
		try {
             
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}


// PANEL DE VISUALIZACION DE LA SIMULACIÓN
class Panel extends JPanel{
	
	//creamos un objeto frame para el contenedor
	JFrame jf;
    JLabel clock, clockLabel;
	JLabel rpmLb, carreraLb, areaTanqueLb, pmLb, potenciaLb;
    JPanel paux, pnGame;
    int count = 0;
	boolean StopPlay = false;
	boolean DrawNOW = false;
	
	JPanel pnBtn;//panel para los botones
	JPanel pnCtrl; //panel para los campos de entrada
    JPanel pnInfo; //panel para la información de la simulación
	
    //declaramos las variables para nuestros botones
	JButton btnNewGame,btnStop,btnReset,btnExit,btnRemove;

	//declaramos las variables para nuestros campos de entrada
	JTextField rpm, carrera, areaTanque, nivelFluido, pm, potencia;

	
	double alturaTq = 150;
	double areaTq = 100;
	DecimalFormat formating = new DecimalFormat("####.##");

	//contadores para el cronometro
    private int contSClock = 0;//segundos
    private int contMClock = 0;//minutos
    private int contHClock = 0;//horas
	private String ms = "00", ss = "00", mm = "00";

	private boolean change =  false; 
	private boolean change_fase =  false; 
	private boolean change_val =  false; 
	private double time_val = 0;
	private double time_val_1 = 0;
	private double time_val_2 = 0;
	double time = 0;
	int fase = 1;
	double x = 0;
	double y = 0;
	double frecuencia;
	int exp = 0;
	int flIZ = 0;
	int flDE = 0;


	Panel() {

		//creamos el contenedor
		jf = new JFrame();
		jf.setLayout(new BorderLayout());
		
		//creamos el panel auxiliar y lo agregamos al contenedor
		GridBagConstraints rest_pane = new GridBagConstraints();
		GridBagLayout gridBag_pane = new GridBagLayout();
		rest_pane.fill = GridBagConstraints.HORIZONTAL;
		paux = new JPanel();
		paux.setLayout(gridBag_pane);
		paux.setBorder(new MatteBorder(10,10,10,10,Color.WHITE));
		paux.setPreferredSize(new Dimension(340,500));
		jf.add(paux, BorderLayout.EAST);

        //creamos el panel para la información de la simulación y lo anexamos al panel auxiliar
		pnInfo = new JPanel();
		pnInfo.setLayout(new GridLayout(1,2));
		pnInfo.setBorder(new MatteBorder(20,0,20,0,Color.WHITE));
        pnInfo.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 0;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnInfo, rest_pane);
		paux.add(pnInfo);

        clockLabel = new JLabel("Timer: ");
        clockLabel.setHorizontalAlignment(JLabel.LEFT);
		clockLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		clockLabel.setForeground(Color.BLACK);
        clockLabel.setBorder(new MatteBorder(5,10,5,5,Color.LIGHT_GRAY));
        pnInfo.add(clockLabel);

        clock = new JLabel(mm + ":" + ss + ":" + ms + " (0 s)");
        clock.setHorizontalAlignment(JLabel.LEFT);
		clock.setFont(new Font("Helvetica",Font.BOLD,15));
		clock.setForeground(Color.RED);
        clock.setBorder(new MatteBorder(5,5,5,5,Color.LIGHT_GRAY));
        pnInfo.add(clock);

        //creamos el panel para los campos de entrada
		GridBagConstraints restricciones = new GridBagConstraints();
		GridBagLayout gridBag = new GridBagLayout();
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		pnCtrl = new JPanel();
		pnCtrl.setLayout(gridBag);
		pnCtrl.setBorder(new MatteBorder(20,10,20,10,Color.LIGHT_GRAY));
        pnCtrl.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 1;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnCtrl, rest_pane);
		paux.add(pnCtrl);

		// creamos el campo de entrada para las revoluciones del motor
		rpmLb = new JLabel("Revoluciones por minuto (rpm):");
		rpmLb.setHorizontalAlignment(JLabel.CENTER);
		rpmLb.setFont(new Font("Helvetica",Font.BOLD,15));
		rpmLb.setForeground(Color.RED);
		rpmLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 0;
		gridBag.setConstraints(rpmLb, restricciones);
		pnCtrl.add(rpmLb);
		rpm = new JTextField();
		rpm.setName("areaOrificio");
		rpm.setForeground(Color.BLACK);
		rpm.setFont(new Font("Tahoma", 1, 15));
		rpm.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		rpm.setHorizontalAlignment(JTextField.CENTER);
		restricciones.gridx = 2;
		restricciones.gridy = 0;
		restricciones.weightx = 1;
		gridBag.setConstraints(rpm, restricciones);
		pnCtrl.add(rpm);


		// creamos el campo de entrada para la presion media ejercida por el motor
		pmLb = new JLabel("Presion media (Kg/cm^2):");
		pmLb.setHorizontalAlignment(JLabel.CENTER);
		pmLb.setFont(new Font("Helvetica",Font.BOLD,15));
		pmLb.setForeground(Color.RED);
		pmLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 1;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(pmLb, restricciones);
		pnCtrl.add(pmLb);
		pm = new JTextField();
		pm.setName("densidadFluido");
		pm.setForeground(Color.BLACK);
		pm.setFont(new Font("Tahoma", 1, 15));
		pm.setHorizontalAlignment(JTextField.CENTER);
		pm.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 1;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(pm, restricciones);
		pnCtrl.add(pm);


		// creamos el campo de entrada para la carrera del cilindro
		carreraLb = new JLabel("Carrera (mm):");
		carreraLb.setHorizontalAlignment(JLabel.CENTER);
		carreraLb.setFont(new Font("Helvetica",Font.BOLD,15));
		carreraLb.setForeground(Color.RED);
		carreraLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.gridx = 1;
		restricciones.gridy = 2;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(carreraLb, restricciones);
		pnCtrl.add(carreraLb);
		carrera = new JTextField();
		carrera.setName("alturaTanque");
		carrera.setForeground(Color.BLACK);
		carrera.setFont(new Font("Tahoma", 1, 15));
		carrera.setHorizontalAlignment(JTextField.CENTER);
		carrera.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.fill = GridBagConstraints.HORIZONTAL;
		restricciones.gridx = 2;
		restricciones.gridy = 2;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(carrera, restricciones);
		pnCtrl.add(carrera);


        // creamos el campo de entrada para el diametro del cilindro
		areaTanqueLb = new JLabel("Diametro del cilindro (mm):");
		areaTanqueLb.setHorizontalAlignment(JLabel.CENTER);
		areaTanqueLb.setFont(new Font("Helvetica",Font.BOLD,15));
		areaTanqueLb.setForeground(Color.RED);
		areaTanqueLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 3;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaTanqueLb, restricciones);
		pnCtrl.add(areaTanqueLb);
		areaTanque = new JTextField();
		areaTanque.setName("areaTanque");
		areaTanque.setForeground(Color.BLACK);
		areaTanque.setFont(new Font("Tahoma", 1, 15));
		areaTanque.setHorizontalAlignment(JTextField.CENTER);
		areaTanque.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		restricciones.gridx = 2;
		restricciones.gridy = 3;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(areaTanque, restricciones);
		pnCtrl.add(areaTanque);


		// creamos el campo de entrada para la potencia del motor
		potenciaLb = new JLabel("Potencia del motor (CV):");
		potenciaLb.setHorizontalAlignment(JLabel.CENTER);
		potenciaLb.setFont(new Font("Helvetica",Font.BOLD,17));
		potenciaLb.setForeground(Color.BLACK);
		potenciaLb.setHorizontalAlignment(JLabel.LEFT);
		restricciones.gridx = 1;
		restricciones.gridy = 4;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(potenciaLb, restricciones);
		pnCtrl.add(potenciaLb);
		potencia = new JTextField();
		potencia.setName("areaTanque");
		potencia.setForeground(Color.BLACK);
		potencia.setFont(new Font("Tahoma", 1, 15));
		potencia.setHorizontalAlignment(JTextField.CENTER);
		potencia.setBorder(new MatteBorder(10,1,10,1,Color.LIGHT_GRAY));
		potencia.setEditable(false);
		potencia.setEnabled(false);
		restricciones.gridx = 2;
		restricciones.gridy = 4;
		restricciones.weightx = 0.5;
		gridBag.setConstraints(potencia, restricciones);
		pnCtrl.add(potencia);

		
        //creamos el panel para los botones y lo agregamos al panel auxiliar
		pnBtn = new JPanel();
		pnBtn.setLayout(new GridLayout(2,4));
		pnBtn.setBorder(new MatteBorder(20,0,20,0,Color.WHITE));
        pnBtn.setBackground(Color.LIGHT_GRAY);
		rest_pane.gridx = 0;
		rest_pane.gridy = 3;
		rest_pane.weightx = 1;
		gridBag_pane.setConstraints(pnBtn, rest_pane);
		paux.add(pnBtn);

		//creamos el boton nuevo juego y lo agregamos al panel de botones
		btnNewGame = new JButton();
		btnNewGame.setIcon(new ImageIcon(getClass().getResource("img_game/play.png")));
		btnNewGame.setFocusPainted(false);
		btnNewGame.setContentAreaFilled(false);
		pnBtn.add(btnNewGame);
        newGame();
		
		//boton stop
		btnStop = new JButton();
		btnStop.setIcon(new ImageIcon(getClass().getResource("img_game/stop.png")));
		btnStop.setFocusPainted(false);
		btnStop.setContentAreaFilled(false);
		pnBtn.add(btnStop);
        stopGame();
		
		//boton reset
		btnReset = new JButton();
		btnReset.setIcon(new ImageIcon(getClass().getResource("img_game/reset.png")));
		btnReset.setFocusPainted(false);
		btnReset.setContentAreaFilled(false);
		pnBtn.add(btnReset);
		resetGame();
		
		//boton salir
		btnExit = new JButton();
		btnExit.setIcon(new ImageIcon(getClass().getResource("img_game/close.png")));
		btnExit.setFocusPainted(false);
		btnExit.setContentAreaFilled(false);
		pnBtn.add(btnExit);
		exitGame();

		jf.add(this);

        //establecemos los parametros del contenedor
		jf.pack();
		jf.setSize(1200, 600);
		jf.setResizable(false);
		Image img2 = new ImageIcon(getClass().getResource("img_game/game.png")).getImage();
		jf.setIconImage(img2);
		jf.setLocationRelativeTo(null);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.setVisible(true);
	
		// iniciamos el ciclo del hilo que controlara la simulación
		while (true) {
			this.fillTank();
			this.repaint();
			try
            {
                Thread.sleep(10);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
		}
	}

	// Un pequeño Frame para desplegar mensajes 
	public void boxMessage(String msg, String title){
		JFrame frame_msg = new JFrame();
		frame_msg.setLayout(new BorderLayout());

		JPanel pnTxt = new JPanel();
		pnTxt.setLayout(new BorderLayout());
		pnTxt.setBorder(new MatteBorder(40,0,40,0,Color.WHITE));
        pnTxt.setBackground(Color.LIGHT_GRAY);
		frame_msg.add(pnTxt, BorderLayout.CENTER);

		JLabel titleLabel = new JLabel("<HTML><CENTER>" + title + "</CENTER></HTML>");
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
		titleLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		titleLabel.setForeground(Color.RED);
        titleLabel.setBorder(new MatteBorder(20,25,0,25,Color.LIGHT_GRAY));
        pnTxt.add(titleLabel, BorderLayout.NORTH);

		JLabel txtLabel = new JLabel("<HTML><CENTER>" + msg + "</CENTER></HTML>");
        txtLabel.setHorizontalAlignment(JLabel.CENTER);
		txtLabel.setFont(new Font("Helvetica",Font.BOLD,15));
		txtLabel.setForeground(Color.BLACK);
        txtLabel.setBorder(new MatteBorder(0,25,5,25,Color.LIGHT_GRAY));
        pnTxt.add(txtLabel, BorderLayout.CENTER);

		frame_msg.pack();
		frame_msg.setSize(500, 400);
		frame_msg.setResizable(false);
		Image img2 = new ImageIcon(getClass().getResource("img_game/game.png")).getImage();
		frame_msg.setIconImage(img2);
		frame_msg.setLocationRelativeTo(null);
		frame_msg.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame_msg.setVisible(true);
	}

	// Función para iniciar la simulación
	private void fillTank() {
        if(StopPlay){
			double rpm_ = (Float.parseFloat(rpm.getText()) / 100);
			if(time <= 0){
                change = true;
				exp = 20;
				time_val++;
            }else if(time >= 60){
                change = false;
				exp = 0;
            }
			giroCigenal();
			clockView();
            if(change){
                time+=rpm_;
            }else{
                time+=-rpm_;
            }
			if(time_val % 2 == 0){
				change_val = true;
			}else{
				change_val = false;
			}
			if(change_val){
				time_val_1 = time;
				flDE = 10;
				flIZ = 0;
			}else{
				time_val_2 = time;
				flIZ = 10;
				flDE = 0;
			}
		}
	}

	public void giroCigenal(){
		frecuencia = (Float.parseFloat(rpm.getText()) / 100);;
		if(x >= 35){
            change_fase = true;
            fase = fase * -1;
        }else if(x <= -35){
            change_fase = false;
            fase = fase * -1;
        }
        if(change_fase){
            x -= frecuencia;
        }else{
            x += frecuencia;
        }
	}

    //dibujamos la simulación en el plano
	@Override
	public void paint(Graphics g) {
		super.paint(g);

        // CREAMOS EL LIEZO DONDE DIBUJAREMOS LA SIMULACIÓN
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // movemos el el punto (0,0) a la ezquina inferior izquierda del panel
        g.translate(getWidth()*10/100, getHeight()*90/100);

		// Invocamos la función para dibujar los cilindros
        drawTank(g, 50, 200);
        drawTank(g, 100, 95);
        drawTank(g, 100, 95);
        drawTank(g, 100, 95);

	}

    // dibujamos el cilindro
    public void drawTank(Graphics g, int pos_x, int pos_y){

        // Dibujamos el contorno del cilindro y establecemos los valores iniciales para la altura del cilindro
        g.setColor(Color.GREEN);
        g.fillRect(pos_x, (int) -((alturaTq) + pos_y), 10, (int) (alturaTq)); // Pared izquierda del cilindro
        g.fillRect((int) (areaTq) + pos_x, (int) -((alturaTq) + pos_y), 10, (int) (alturaTq)); // Pared derecha del cilindro
		g.fillRect(pos_x, (int) -((alturaTq) + pos_y), (int) (areaTq), 10);// Parte superior del cilindro

		// Tuberias para el flujo del combustible
		g.fillRect((int) (pos_x + 15), (int) -((alturaTq+50) + pos_y), 10, 100);
		g.fillRect((int) (pos_x + 35), (int) -((alturaTq+70) + pos_y), 10, 100);
		g.fillRect(pos_x - 5, (int) -((alturaTq+50) + pos_y), 20, 10);
		g.fillRect(pos_x - 5, (int) -((alturaTq+70) + pos_y), 50, 10);
		g.fillRect((int) (pos_x + 65), (int) -((alturaTq+70) + pos_y), 10, 100);
		g.fillRect((int) (pos_x + 85), (int) -((alturaTq+50) + pos_y), 10, 100);
		g.fillRect(pos_x + 95, (int) -((alturaTq+50) + pos_y), 20, 10);
		g.fillRect(pos_x + 65, (int) -((alturaTq+70) + pos_y), 50, 10);

		// fluido
		g.setColor(Color.PINK);
		g.fillRect((int) (pos_x + 10), (int) -((alturaTq-10) + pos_y), (int) (areaTq-10), (int) (45 + time));
		g.fillRect((int) (pos_x + 25), (int) -((alturaTq+50) + pos_y), flDE, 100);
		g.fillRect(pos_x - 5, (int) -((alturaTq+60) + pos_y), 40, flDE);
		g.fillRect((int) (pos_x + 75), (int) -((alturaTq+50) + pos_y), flIZ, 100);
		g.fillRect(pos_x + 75, (int) -((alturaTq+60) + pos_y), 40, flIZ);


		// valvulas
		g.setColor(Color.RED);
		g.fillRect((int) (pos_x + 25), (int) -((alturaTq+30-(time_val_1/3)) + pos_y), 10, 40);
		g.fillRect((int) (pos_x + 20), (int) -((alturaTq-10-(time_val_1/3)) + pos_y), 20, 10);
		g.fillRect((int) (pos_x + 75), (int) -((alturaTq+30-(time_val_2/3)) + pos_y), 10, 40);
		g.fillRect((int) (pos_x + 70), (int) -((alturaTq-10-(time_val_2/3)) + pos_y), 20, 10);

		// bujia
		g.setColor(Color.GRAY);
		g.fillRect((int) (pos_x + 50), (int) -((alturaTq+10) + pos_y), 10, 25);
		g.fillOval((int) (pos_x + 50), (int) -((alturaTq-10) + pos_y), 9, 9);
		// Chispa
		g.setColor(Color.YELLOW);
		g.fillOval((int) (pos_x + 45), (int) -((alturaTq-20) + pos_y), exp, exp);

		//piston
		g.setColor(Color.BLUE);
		g.fillRect((int) (pos_x + 10), (int) -((alturaTq-time-45) + pos_y), (int) (areaTq-10), 50);

		// cigueñal
		g.setColor(Color.GRAY);
		g.fillOval((int) (pos_x), (int) -((alturaTq-200) + pos_y), 110, 110);
		g.setColor(Color.BLUE);
		g.fillOval((int) (pos_x) + 45, (int) -((alturaTq-245) + pos_y), 20, 20);


		g.translate((int) (pos_x + 45), (int) -((alturaTq-245) + pos_y));

		// biela
		g.setColor(Color.RED);
		g.fillOval((int) (x), -fase*calcularEjeY((int) x, 35), 20, 20);
		g.fillOval(0, (int) -((-20-time) + 200), 20, 20);

		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke( new BasicStroke( 10 ));
		g2d.drawLine(10, (int) -((-30-time) + 200), (int) (10+x), -fase*calcularEjeY((int) x, 35));

    }

	public int calcularEjeY(int ejeX, int r){
        int ejeY = (int) Math.sqrt(Math.abs((r*r) - Math.pow(ejeX, 2)));
        return ejeY;
    }

	// Restablecemos los campos de entrada
	public void deleteFields(){

		btnRemove.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				rpm.setText("");
				carrera.setText("");
				areaTanque.setText("");
				pm.setText("");
			}
		});

	}
	
	//variables hora, minuto y segundo del cronometro
	public void clockView()
	{
		contSClock++;

		if (contSClock > 59)
		{
			contSClock = 0;
			contMClock++;
			ms = "0" + String.valueOf(contSClock);
			if (contMClock > 59)
			{
				contMClock = 0;
				contHClock++;
				ss = "0" + String.valueOf(contMClock);
				if (contHClock > 24)
				{
					contHClock = 0;
					mm = "0" + String.valueOf(contHClock);
				}
				else if (contMClock < 10)
				{
					mm = "0" + String.valueOf(contHClock);
				}
				else
				{
					mm = String.valueOf(contHClock);
				}
			}
			else if (contMClock < 10)
			{
				ss = "0" + String.valueOf(contMClock);
			}
			else
			{
				ss = String.valueOf(contMClock);
			}
		}
		else if (contSClock < 10)
		{
			ms = "0" + String.valueOf(contSClock);
		}
		else
		{
			ms = String.valueOf(contSClock);
		}
		clock.setText(mm + ":" + ss + ":" + ms + " (" + time + " s)");
	}

	//Calculamos la potencia del motor
	public void calcularPotencia(){
		double rpm_ = Float.parseFloat(rpm.getText());
		double carrera_ = Float.parseFloat(carrera.getText());
		double areaTanque_ = Float.parseFloat(areaTanque.getText());
		double pm_ = (Float.parseFloat(pm.getText()));

		double area_cilindro = Math.PI * (Math.pow(areaTanque_/10, 2))/4;
		double potencia_ = 4 * pm_ * area_cilindro * (carrera_/1000) * (rpm_/120) * (9.8/736);

		potencia.setText(String.valueOf(formating.format(potencia_)));
	}

    //iniciamos una nuevo juego
	public void newGame() {
		btnNewGame.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				initSimulation();
				calcularPotencia();
			}
		});
	}

	// Iniciamos la simulación
	public void initSimulation(){
		if(isFieldFill()){
				StopPlay = true;
		}else{
			boxMessage("Debe rellenar todos los campos de las variables, para poder iniciar una simulacion.", "Advertencia !");
		}
	}

	//Comprobamos que los campos de texto esten llenos
	boolean isFieldFill(){
		if(rpm.getText().toString().length() != 0 &&
			carrera.getText().toString().length() != 0 &&
			areaTanque.getText().toString().length() != 0 &&
			pm.getText().toString().length() != 0){
				return true;
		}
		return false;
	}

    //Pausamos el la simulación
	public void stopGame() {
		btnStop.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
			}
		});
	}

	public void reseted(){
		StopPlay = false;
		alturaTq = 150;
		areaTq = 100;
		
		time = 0;

		rpm.setText("");
		carrera.setText("");
		areaTanque.setText("");
		pm.setText("");

		pm.setEditable(true);
		pm.setEnabled(true);

		contSClock = 0;
    	contMClock = 0;
    	contHClock = 0;
		ms = "00";
		ss = "00";
		mm = "00";

		clock.setText(mm + ":" + ss + ":" + ms + " (" + time + " s)");

	}

	//Reiniciamos la simulación
	public void resetGame() {
		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				StopPlay = false;
				reseted();
			}
		});
	}

	//Salimos de la simulación
	public void exitGame() {
		btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				exit();
			}
		});
	}
	
	//metodo para salir de la simulación
	public void exit() {
		System.exit(0);
	}

}