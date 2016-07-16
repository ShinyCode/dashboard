package outside;

import java.awt.Color;

import dashboard.*;

public class ComplexDashboard extends DashboardProgram
{	
	private static final double BUTTON_HEIGHT = 40.0;
	private static final double BUTTON_WIDTH = 80.0;
	private static final double BUTTON_SPACING = 10.0;
	private static final double COMPONENT_SPACING = 10.0;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color COLOR = Color.RED.darker();
	private static final Color ACCENT_COLOR = Color.ORANGE;
	private static final int NUM_DIVISIONS = 100;
	private static final double BORDER_WIDTH = 2.0;
	private static final double DATUM_READOUT_WIDTH = 100.0;
	private static final double DATUM_READOUT_HEIGHT = 60.0;
	private static final double ENGINE_BUTTON_HEIGHT = 25.0;
	private static final double CHAT_BUTTON_WIDTH = 2.0 * BUTTON_WIDTH / 3.0;
	private static final double CHAT_BUTTON_HEIGHT = BUTTON_HEIGHT;
	
	private CustomButtonGrid mpwr;
	private ImageReadout cir;
	private CustomButtonGrid chat;
	private CustomButtonGrid epwr;
	private CustomGroup dat;
	private CustomGroup vit;
	private AuxArrowPad aap;
	private CustomGroup engcwg1;
	private CustomGroup engcwg2;
	private CustomGroup freqcwg;
	private CustomGroup engcrcwg;
	private CustomGroup chatcrcwg;
	private MinimapReadout mmr;
	private CustomButtonGrid reset;
	private CompassReadout cmpr;
	
	public void init()
	{
		initMPWR();
		initCIR();
		bindActionsCPWR();
		initCHAT();
		initEPWR();
		initDAT();
		initVIT();
		initAAP();
		initENGCWG();
		initFREQCWG();
		initENGCRCWG();
		initCHATCRCWG();
		bindActionsCHAT();
		initMMR();
		initRESET();
		initCMPR();
		
		addBackground(COMPONENT_SPACING, BASE_COLOR);
		
		addMouseListeners();
		setSize(1200, 500);
	}
	
	private void initMPWR()
	{
		ToggleButton mpwrButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, ACCENT_COLOR, "MPWR");
		ToggleButton cpwrButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "CPWR");
		cpwrButton.setOn(true);
		ToggleButton cfrzButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "CFRZ");
		mpwr = new CustomButtonGrid.Builder(2 * BUTTON_SPACING + BUTTON_WIDTH, 4 * BUTTON_SPACING + 3 * BUTTON_HEIGHT)
			.withRowsCols(3, 1)
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		mpwr.setName("MPWR");
		mpwr.addButton(mpwrButton, 0, 0);
		mpwr.addButton(cpwrButton, 1, 0);
		mpwr.addButton(cfrzButton, 2, 0);
		addWidget("MPWR", mpwr, COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(mpwr, COLOR, BORDER_WIDTH);
	}
	
	private void initCIR()
	{
		double cirSide = 1.3 * mpwr.getHeight();
		cir = new ImageReadout.Builder(cirSide, cirSide)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withOffColor(Color.BLACK)
			.build();
		addWidget("CIR", cir, mpwr.getX() + mpwr.getWidth() + COMPONENT_SPACING, mpwr.getY());
		addBorder(cir, COLOR, BORDER_WIDTH);
	}
	
	private void bindActionsCPWR()
	{
		Button cpwrButton = mpwr.getButton(1, 0);
		cpwrButton.setOnAction(new Runnable()
		{
			public void run()
			{
				cir.setOn(true);
			}
		});
		cpwrButton.setOffAction(new Runnable()
		{
			public void run()
			{
				cir.setOn(false);
			}
		});
	}
	
	private void initCHAT()
	{
		TouchButton chatClsButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "CLS");
		ToggleButton chatFrzButton = new ToggleButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "FRZ");
		TouchButton chatIncButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "INC");
		TouchButton chatDecButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "DEC");
		chat = new CustomButtonGrid.Builder(2 * CHAT_BUTTON_WIDTH + 3 * BUTTON_SPACING, 2 * CHAT_BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withRowsCols(2, 2)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		chat.setName("CHAT");
		chat.addButton(chatClsButton, 0, 0);
		chat.addButton(chatFrzButton, 1, 0);
		chat.addButton(chatIncButton, 0, 1);
		chat.addButton(chatDecButton, 1, 1);
		addWidget("CHAT", chat, cir.getX() + cir.getWidth() + COMPONENT_SPACING, cir.getY());
		addBorder(chat, COLOR, BORDER_WIDTH);
	}
	
	private void initEPWR()
	{
		epwr = new CustomButtonGrid.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 1)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		epwr.setName("EPWR");
		ToggleButton epwrButton = new ToggleButton(0, 0, ACCENT_COLOR, "EPWR");
		epwr.addButton(epwrButton, 0, 0);
		addWidget("EPWR", epwr, chat.getX(), chat.getY() + chat.getHeight() + COMPONENT_SPACING);
		addBorder(epwr, ACCENT_COLOR, BORDER_WIDTH);
	}
	
	private void initDAT()
	{
		String datumNames[] = {"XPOS", "YPOS", "BRX", "BRY", "ALT"};

		dat = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		dat.setName("DAT");
		for(int i = 0; i < datumNames.length; ++i)
		{
			BufferReadout bfr = new BufferReadout.Builder(DATUM_READOUT_WIDTH, DATUM_READOUT_HEIGHT)
				.withBaseColor(BASE_COLOR)
				.withColor(COLOR)
				.withAccentColor(ACCENT_COLOR)
				.build();
			dat.addWidget(datumNames[i], bfr, (BUTTON_SPACING + DATUM_READOUT_WIDTH) * i + BUTTON_SPACING, BUTTON_SPACING);
		}
		addWidget("DAT", dat, epwr.getX() + epwr.getWidth() + COMPONENT_SPACING, epwr.getY());
		addBorder(dat, COLOR, BORDER_WIDTH);
	}
	
	private void initVIT()
	{
		String vitalNames[] = {"SPD", "THR", "FUEL"};
		vit = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		vit.setName("VIT");
		for(int i = 0; i < vitalNames.length; ++i)
		{
			DialReadout dr = new DialReadout.Builder(DATUM_READOUT_WIDTH, DATUM_READOUT_HEIGHT, NUM_DIVISIONS)
				.withBaseColor(BASE_COLOR)
				.withColor(COLOR)
				.withAccentColor(ACCENT_COLOR)
				.withStartAngle(0.0)
				.withSweepAngle(180.0)
				.build();
			vit.addWidget(vitalNames[i], dr, (BUTTON_SPACING + DATUM_READOUT_WIDTH) * i + BUTTON_SPACING, BUTTON_SPACING);
		}
		addWidget("VIT", vit, dat.getX(), dat.getY() + dat.getHeight() + COMPONENT_SPACING);
		addBorder(vit, COLOR, BORDER_WIDTH);
	}
	
	private void initAAP()
	{
		aap = new AuxArrowPad.Builder(vit.getWidth(), 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		aap.setName("AAP");
		addWidget("AAP", aap, vit.getX(), vit.getY() + vit.getHeight() + COMPONENT_SPACING);
		addBorder(aap, COLOR, BORDER_WIDTH);
	}
	
	private void initENGCWG()
	{
		double remainHeight = aap.getY() + aap.getHeight() - epwr.getY() - epwr.getHeight();
		double engineReadoutHeight = remainHeight - 2 * ENGINE_BUTTON_HEIGHT - 2 * BUTTON_SPACING - COMPONENT_SPACING;
		double engineReadoutWidth = epwr.getWidth() / 2.0 - 1.5 * BORDER_WIDTH;
		BarReadout.Builder engbrBuilder = new BarReadout.Builder(engineReadoutWidth, engineReadoutHeight, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING);
		SingleIncrementer.Builder engsincBuilder = new SingleIncrementer.Builder(engineReadoutWidth, 2 * ENGINE_BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING);
		BarReadout engbr1 = engbrBuilder.build();
		BarReadout engbr2 = engbrBuilder.build();
		SingleIncrementer engsinc1 = engsincBuilder.build();
		engsinc1.setName("ENGSINC1");
		SingleIncrementer engsinc2 = engsincBuilder.build();
		engsinc2.setName("ENGSINC2");
		engsinc1.setIncrementable(engbr1);
		engsinc2.setIncrementable(engbr2);
		
		CustomGroup.Builder engcwgBuilder = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(0);
		engcwg1 = engcwgBuilder.build();
		engcwg1.setName("ENGCWG1");
		engcwg2 = engcwgBuilder.build();
		engcwg2.setName("ENGCWG2");
		engcwg1.addWidget("ENGBR1", engbr1, 0, 0);
		engcwg1.addWidget("ENGSINC1", engsinc1, 0, engbr1.getHeight() - BUTTON_SPACING);
		engcwg2.addWidget("ENGBR2", engbr2, 0, 0);
		engcwg2.addWidget("ENGSINC2", engsinc2, 0, engbr2.getHeight() - BUTTON_SPACING);
		addWidget("ENGCWG1", engcwg1, epwr.getX(), epwr.getY() + epwr.getHeight() + COMPONENT_SPACING);
		addWidget("ENGCWG2", engcwg2, engcwg1.getX() + engcwg1.getWidth() + 2 * BORDER_WIDTH, engcwg1.getY());
		addBorder(engcwg1, COLOR, BORDER_WIDTH);
		addBorder(engcwg2, COLOR, BORDER_WIDTH);
	}
	
	private void initFREQCWG()
	{
		double freqButtonWidth = engcwg1.getWidget("ENGBR1").getWidth() - 2 * BUTTON_SPACING;
		MultiIncrementer freqminc = new MultiIncrementer.Builder(freqButtonWidth + 2 * BUTTON_SPACING, 3 * freqButtonWidth + 4 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		freqminc.setName("FREQMINC");
		double freqBarHeight = freqButtonWidth;
		double freqBarWidth = dat.getX() + dat.getWidth() - vit.getX() - vit.getWidth() - COMPONENT_SPACING - BUTTON_SPACING - freqminc.getWidth();
		BarReadout.Builder freqbrBuilder = new BarReadout.Builder(freqBarWidth + 2 * BUTTON_SPACING, freqBarHeight + 2 * BUTTON_SPACING, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withOrientation(BarReadout.HORIZONTAL);
		freqcwg = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(0)
			.build();
		freqcwg.setName("FREQCWG");
		for(int i = 0; i < 3; ++i)
		{
			BarReadout freqbr = freqbrBuilder.build();
			freqcwg.addWidget("FREQBR" + i, freqbr, 0, i * (BUTTON_SPACING + freqBarHeight));
			freqminc.addIncrementable(freqbr);
		}
		freqcwg.addWidget("FREQMINC", freqminc, BUTTON_SPACING + freqBarWidth, 0);
		addWidget("FREQCWG", freqcwg, vit.getX() + vit.getWidth() + COMPONENT_SPACING, vit.getY());
		addBorder(freqcwg, COLOR, BORDER_WIDTH);
	}
	
	private void initENGCRCWG()
	{
		double engcrWidth = (freqcwg.getWidth() - 3.5 * BUTTON_SPACING) / 6.0;
		double engcrHeight = (aap.getY() + aap.getHeight() - vit.getY() - freqcwg.getHeight() - COMPONENT_SPACING - 1.5 * BUTTON_SPACING) / 2.0;
		engcrcwg = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING / 2.0)
			.build();
		for(int i = 0; i < 2; ++i)
		{
			for(int j = 0; j < 6; ++j)
			{
				ColorReadout engcr = new ColorReadout.Builder(engcrWidth, engcrHeight)
					.withBaseColor(COLOR)
					.withColor(ACCENT_COLOR)
					.withSpacing(BUTTON_SPACING / 2.0)
					.build();
				engcrcwg.addWidget("ENGCR" + i + j, engcr, BUTTON_SPACING / 2.0 + j * (BUTTON_SPACING / 2.0 + engcrWidth), BUTTON_SPACING / 2.0 + i * (BUTTON_SPACING / 2.0 + engcrHeight));
			}
		}
		addWidget("ENGCRCWG", engcrcwg, freqcwg.getX(), freqcwg.getY() + freqcwg.getHeight() + COMPONENT_SPACING);
		addBorder(engcrcwg, COLOR, BORDER_WIDTH);
	}
	
	private void initCHATCRCWG()
	{
		double totalWidth = dat.getX() + dat.getWidth() - epwr.getX();
		double chatcrHeight = (chat.getHeight() - 2.0 * BUTTON_SPACING) / 3.0;
		double chatcrWidth = chatcrHeight;
		chatcrcwg = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING / 2.0)
			.build();
		for(int i = 0; i < 3; ++i)
		{
			ColorReadout chatcr = new ColorReadout.Builder(chatcrWidth, chatcrHeight)
				.withBaseColor(COLOR)
				.withColor(ACCENT_COLOR)
				.withSpacing(BUTTON_SPACING / 2.0)
				.build();
			chatcrcwg.addWidget("CHATCR" + i, chatcr, BUTTON_SPACING / 2.0, BUTTON_SPACING / 2.0 + i * (BUTTON_SPACING / 2.0 + chatcrHeight));
		}
		addWidget("CHATCRCWG", chatcrcwg, chat.getX() + totalWidth - chatcrcwg.getWidth(), chat.getY());
		addBorder(chatcrcwg, COLOR, BORDER_WIDTH);
		
		double chatbfrHeight = chat.getHeight();
		double chatbfrWidth = totalWidth - chat.getWidth() - chatcrcwg.getWidth() - 2 * COMPONENT_SPACING;
		BufferReadout chatbfr = new BufferReadout.Builder(chatbfrWidth, chatbfrHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		addWidget("CHATBFR", chatbfr, chat.getX() + chat.getWidth() + COMPONENT_SPACING, chat.getY());
		addCommandOutput("CHATBFR", chatbfr);
		addBorder(chatbfr, COLOR, BORDER_WIDTH);
	}
	
	private void bindActionsCHAT()
	{
		BufferReadout chatbfr = (BufferReadout)getWidget("CHATBFR");
		Button chatClsButton = chat.getButton(0, 0);
		chatClsButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.clear();
			}
		});
		Button chatFrzButton = chat.getButton(1, 0);
		chatFrzButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.setFrozen(true);
			}
		});
		chatFrzButton.setOffAction(new Runnable()
		{
			public void run()
			{
				chatbfr.setFrozen(false);
			}
		});
		Button chatIncButton = chat.getButton(0, 1);
		chatIncButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.increment();
			}
		});
		Button chatDecButton = chat.getButton(1, 1);
		chatDecButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.decrement();
			}
		});
	}
	
	private void initMMR()
	{
		double totalHeight = getTotalWidgetHeight();
		double mmrHeight = totalHeight - cir.getHeight() - COMPONENT_SPACING;
		double mmrWidth = cir.getWidth();
		mmr = new MinimapReadout.Builder(mmrWidth, mmrHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withViewRadius(10.0)
			.build();
		double remainWidth = cir.getX() + cir.getWidth() - mpwr.getX();
		addWidget("MMR", mmr, mpwr.getX() + remainWidth - mmr.getWidth(), cir.getY() + cir.getHeight() + COMPONENT_SPACING);
		addBorder(mmr, COLOR, BORDER_WIDTH);
	}
	
	private void initRESET()
	{
		double totalHeight = getTotalWidgetHeight();
		reset = new CustomButtonGrid.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withRowsCols(2, 1)
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		reset.setName("RESET");
		TouchButton resetButton = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, ACCENT_COLOR, "RST");
		ToggleButton commButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "COMM");
		reset.addButton(commButton, 0, 0);
		reset.addButton(resetButton, 1, 0);
		addWidget("RESET", reset, mpwr.getX(), mpwr.getY() + totalHeight - reset.getHeight());
		addBorder(reset, ACCENT_COLOR, BORDER_WIDTH);
	}
	
	private void initCMPR()
	{
		double totalHeight = getTotalWidgetHeight();
		double cmprWidth = mpwr.getWidth();
		double cmprHeight = totalHeight - mpwr.getHeight() - reset.getHeight() - 2 * COMPONENT_SPACING;
		CompassReadout.Builder cmprBuilder = new CompassReadout.Builder(cmprWidth, cmprHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING);
		cmpr = cmprBuilder.build();
		addWidget("CMPR1", cmpr, mpwr.getX(), mpwr.getY() + mpwr.getHeight() + COMPONENT_SPACING);
		addBorder(cmpr, COLOR, BORDER_WIDTH);
	}
}
