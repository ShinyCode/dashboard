package examples;

import java.awt.Color;

import dashboard.control.AuxArrowPad;
import dashboard.control.BufferReadoutControl;
import dashboard.control.CustomButtonGrid;
import dashboard.control.SingleIncrementer;
import dashboard.control.ToggleButton;
import dashboard.generator.AddressGenerator;
import dashboard.program.DashboardProgram;
import dashboard.readout.BarReadout;
import dashboard.readout.BufferReadout;

/**
 * Implements a simple dashboard design as an example illustrating basic
 * features of the library. See {@link ComplexDashboard ComplexDashboard} for a
 * more complex design.
 * 
 * @author Mark Sabini
 *
 */
public class SimpleDashboard extends DashboardProgram
{
	/**
	 * The height of a dashboard button
	 */
	private static final double BUTTON_HEIGHT = 40;
	
	/**
	 * The width of a dashboard button
	 */
	private static final double BUTTON_WIDTH = 80;
	
	/**
	 * The spacing to use for widgets
	 */
	private static final double BUTTON_SPACING = 10;
	
	/**
	 * The spacing in between separate widget groups
	 */
	private static final double COMPONENT_SPACING = 10;
	
	/**
	 * The primary color of the dashboard
	 */
	private static final Color BASE_COLOR = Color.BLACK;
	
	/**
	 * The secondary color of the dashboard
	 */
	private static final Color COLOR = Color.RED.darker();
	
	/**
	 * The accent color of the dashboard
	 */
	private static final Color ACCENT_COLOR = Color.ORANGE;
	
	/**
	 * The height of a button in a readout control
	 */
	private static final double READOUT_BUTTON_HEIGHT = BUTTON_HEIGHT / 2.0;
	
	/**
	 * The number of milliseconds between generator updates
	 */
	private static final int UPDATE_SPEED = 500;
	
	/**
	 * The width of the bordering surrounding each component
	 */
	private static final double BORDER_WIDTH = 2;
	
	/**
	 * The main power switch
	 */
	private CustomButtonGrid pwr;
	
	/**
	 * The auxiliary communications panel
	 */
	private CustomButtonGrid aux;
	
	/**
	 * Engine thrust control
	 */
	private SingleIncrementer sinc;
	
	/**
	 * Engine thrust readout
	 */
	private BarReadout br;
	
	/**
	 * Directional arrow pad control
	 */
	private AuxArrowPad aap;
	
	/**
	 * Speed readout
	 */
	private BufferReadout spd;
	
	/**
	 * Rotation readout
	 */
	private BufferReadout rot;
	
	/**
	 * Communications/instruction readout
	 */
	private BufferReadout addr;
	
	/**
	 * Control for the speed readout
	 */
	private BufferReadoutControl spdCtrl;
	
	/**
	 * Control for the rotation readout
	 */
	private BufferReadoutControl rotCtrl;
	
	/**
	 * Control for the communications/instruction readout
	 */
	private BufferReadoutControl addrCtrl;
	
	/**
	 * Address generator for the communications readout
	 */
	private AddressGenerator genAdd;
	
	/**
	 * Draws and starts the dashboard.
	 */
	public void init()
	{
		initPWR();
		initAUX();
		initAAP();
		initReadouts();
		initReadoutControls();
		bindReadoutControls();
		initPWRControl();
		initGenerators();

		addBackground(COMPONENT_SPACING, BASE_COLOR);
		addMouseListeners();
	}
	
	/**
	 * Initializes the main power switch.
	 */
	private void initPWR()
	{
		pwr = new CustomButtonGrid.Builder(1 * BUTTON_WIDTH + 2 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 1)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		pwr.setName("PWR");
		ToggleButton mpwr = new ToggleButton(0, 0, ACCENT_COLOR, "MPWR");
		pwr.addButton(mpwr, 0, 0);
		addWidget("PWR", pwr, COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(pwr, ACCENT_COLOR, BORDER_WIDTH);
	}
	
	/**
	 * Initializes the auxiliary communications panel.
	 */
	private void initAUX()
	{
		aux = new CustomButtonGrid.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 1 * BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 3)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		aux.setName("AUX");
		ToggleButton com0 = new ToggleButton(0, 0, COLOR, "COM0");
		ToggleButton com1 = new ToggleButton(0, 0, COLOR, "COM1");
		ToggleButton com2 = new ToggleButton(0, 0, COLOR, "COM2");
		aux.addButton(com0, 0, 0);
		aux.addButton(com1, 0, 1);
		aux.addButton(com2, 0, 2);
		addWidget("AUX", aux, pwr.getX() + pwr.getWidth() + COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(aux, COLOR, BORDER_WIDTH);
	}
	
	/**
	 * Initializes the direction control.
	 */
	private void initAAP()
	{
		aap = new AuxArrowPad.Builder(3 * BUTTON_WIDTH + 4 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		aap.setName("CTRL");
		addWidget("AAP", aap, aux.getX(), aux.getY() + aux.getHeight() + COMPONENT_SPACING);
		addBorder(aap, COLOR, BORDER_WIDTH);	
	}
	
	/**
	 * Initializes the speed, rotation, and communications readouts.
	 */
	private void initReadouts()
	{
		double readoutWidth = BUTTON_WIDTH + 2 * BUTTON_SPACING;
		double readoutHeight = 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING + COMPONENT_SPACING;
		BufferReadout.Builder bufferBuilder = new BufferReadout.Builder(readoutWidth, readoutHeight)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR);
		spd = bufferBuilder.build();
		rot = bufferBuilder.build();
		addr = bufferBuilder.build();
		addWidget("SPD", spd, aux.getX() + aux.getWidth() + COMPONENT_SPACING, pwr.getY());
		addWidget("ROT", rot, spd.getX() + spd.getWidth() + COMPONENT_SPACING, spd.getY());
		addWidget("ADDR", addr, rot.getX() + rot.getWidth() + COMPONENT_SPACING, rot.getY());
		addBorder(spd, ACCENT_COLOR, BORDER_WIDTH);
		addBorder(rot, ACCENT_COLOR, BORDER_WIDTH);
		addBorder(addr, ACCENT_COLOR, BORDER_WIDTH);
	}
	
	/**
	 * Initializes the controls for the speed, rotation, and communications readouts.
	 */
	private void initReadoutControls()
	{
		double readoutWidth = BUTTON_WIDTH + 2 * BUTTON_SPACING;
		spdCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		addWidget("SPDCTRL", spdCtrl, spd.getX(), spd.getY() + spd.getHeight() + COMPONENT_SPACING);
		spdCtrl.setName("SPD_CTRL");
		rotCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		addWidget("ROTCTRL", rotCtrl, rot.getX(), rot.getY() + rot.getHeight() + COMPONENT_SPACING);
		rotCtrl.setName("ROT_CTRL");
		addrCtrl = new BufferReadoutControl.Builder(readoutWidth, 4 * READOUT_BUTTON_HEIGHT + 5 * BUTTON_SPACING)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.build();
		addWidget("ADDRCTRL", addrCtrl, addr.getX(), addr.getY() + addr.getHeight() + COMPONENT_SPACING);
		addrCtrl.setName("ADDR_CTRL");
		addBorder(spdCtrl, COLOR, BORDER_WIDTH);
		addBorder(rotCtrl, COLOR, BORDER_WIDTH);
		addBorder(addrCtrl, COLOR, BORDER_WIDTH);
	}
	
	/**
	 * Associates the readout controls with their specific readouts.
	 */
	private void bindReadoutControls()
	{
		spdCtrl.addBufferReadout(spd);
		rotCtrl.addBufferReadout(rot);
		addrCtrl.addBufferReadout(addr);
	}
	
	/**
	 * Initializes the engine thrust readout and control.
	 */
	private void initPWRControl()
	{
		sinc = new SingleIncrementer.Builder(BUTTON_WIDTH / 2.0 + BUTTON_SPACING - BORDER_WIDTH, spdCtrl.getY() + spdCtrl.getHeight() - aap.getY())
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(ACCENT_COLOR)
			.build();
		sinc.setName("SINC");
		br = new BarReadout.Builder(sinc.getWidth(), sinc.getHeight(), 0)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withRange(0.0, 100.0)
			.withOrientation(BarReadout.VERTICAL)
			.build();
		br.update(25.0);
		sinc.setIncrementable(br);
		addWidget("SINC", sinc, pwr.getX(), aap.getY());
		addWidget("BR", br, sinc.getX() + sinc.getWidth() + 2 * BORDER_WIDTH, aap.getY());
		addBorder(sinc, ACCENT_COLOR, BORDER_WIDTH);
		addBorder(br, COLOR, BORDER_WIDTH);
	}
	
	/**
	 * Initializes and starts the address generator that updates the
	 * communications readout.
	 */
	private void initGenerators()
	{
		genAdd = new AddressGenerator(UPDATE_SPEED);
		genAdd.addReadout("addr", addr);
		genAdd.addReadout("rot", rot);
		genAdd.setActive(true);
	}

}

