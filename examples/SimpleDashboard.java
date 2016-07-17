package outside;

import java.awt.Color;

import dashboard.*;

public class SimpleDashboard extends DashboardProgram
{
	private static final double BUTTON_HEIGHT = 40;
	private static final double BUTTON_WIDTH = 80;
	private static final double BUTTON_SPACING = 10;
	private static final double COMPONENT_SPACING = 10;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color COLOR = Color.RED.darker();
	private static final Color ACCENT_COLOR = Color.ORANGE;
	private static final double READOUT_BUTTON_HEIGHT = BUTTON_HEIGHT / 2.0;
	private static final int UPDATE_SPEED = 500;
	private static final double BORDER_WIDTH = 2;
	
	private CustomButtonGrid pwr;
	private CustomButtonGrid aux;
	private SingleIncrementer sinc;
	private BarReadout br;
	private AuxArrowPad aap;
	private BufferReadout spd;
	private BufferReadout rot;
	private BufferReadout addr;
	private BufferReadoutControl spdCtrl;
	private BufferReadoutControl rotCtrl;
	private BufferReadoutControl addrCtrl;
	private AddressGenerator genAdd;
	
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
	
	private void bindReadoutControls()
	{
		spdCtrl.addBufferReadout(spd);
		rotCtrl.addBufferReadout(rot);
		addrCtrl.addBufferReadout(addr);
	}
	
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
	
	private void initGenerators()
	{
		genAdd = new AddressGenerator(UPDATE_SPEED);
		genAdd.addStringUpdatable("addr", addr);
		genAdd.addStringUpdatable("rot", rot);
		genAdd.setActive(true);
	}
	
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

}

