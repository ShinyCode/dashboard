package outside;

import java.awt.Color;
import java.awt.event.MouseEvent;

import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.*;
import dashboard.*;

public class Dashboard2 extends GraphicsProgram
{
	private Processor prc;
	
	private static final double BUTTON_HEIGHT = 40.0;
	private static final double BUTTON_WIDTH = 80.0;
	private static final double BUTTON_SPACING = 10.0;
	private static final double COMPONENT_SPACING = 10.0;
	private static final Color BASE_COLOR = Color.BLACK;
	private static final Color COLOR = Color.RED.darker();
	private static final Color ACCENT_COLOR = Color.ORANGE;
	private static final double BORDER_WIDTH = 2.0;
	private static final int NUM_DIVISIONS = 100;
	
	private static final double DATUM_READOUT_WIDTH = 100.0;
	private static final double DATUM_READOUT_HEIGHT = 60.0;
	private static final double ENGINE_BUTTON_HEIGHT = 25.0;
	private static final double CHAT_BUTTON_WIDTH = 2.0 * BUTTON_WIDTH / 3.0;
	private static final double CHAT_BUTTON_HEIGHT = BUTTON_HEIGHT;
	
	public void run()
	{
		setSize(1200, 500);
		addMouseListeners();
		prc = new Processor(0);
		prc.setActive(true);
		
		ToggleButton mpwrButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, ACCENT_COLOR, "MPWR");
		ToggleButton cpwrButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "CPWR");
		ToggleButton cfrzButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "CFRZ");
		CustomButtonGrid mpwr = new CustomButtonGrid.Builder(2 * BUTTON_SPACING + BUTTON_WIDTH, 4 * BUTTON_SPACING + 3 * BUTTON_HEIGHT)
			.withRowsCols(3, 1)
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		mpwr.addButton(mpwrButton, 0, 0);
		mpwr.addButton(cpwrButton, 1, 0);
		mpwr.addButton(cfrzButton, 2, 0);
		add(mpwr, COMPONENT_SPACING, COMPONENT_SPACING);
		addBorder(mpwr, COLOR);
		
		double cirSide = 1.3 * mpwr.getHeight();
		ImageReadout cir = new ImageReadout.Builder(cirSide, cirSide)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withOffColor(Color.BLACK)
			.build();
		add(cir, mpwr.getX() + mpwr.getWidth() + COMPONENT_SPACING, mpwr.getY());
		addBorder(cir, COLOR);
		
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
		// TODO: Implement setFrozen in ImageReadout
		
		/******************************/
		
		TouchButton chatClsButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "CLS");
		ToggleButton chatFrzButton = new ToggleButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "FRZ");
		TouchButton chatIncButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "INC");
		TouchButton chatDecButton = new TouchButton(CHAT_BUTTON_WIDTH, CHAT_BUTTON_HEIGHT, COLOR, "DEC");
		CustomButtonGrid chat = new CustomButtonGrid.Builder(2 * CHAT_BUTTON_WIDTH + 3 * BUTTON_SPACING, 2 * CHAT_BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withRowsCols(2, 2)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		chat.addButton(chatClsButton, 0, 0);
		chat.addButton(chatFrzButton, 1, 0);
		chat.addButton(chatIncButton, 0, 1);
		chat.addButton(chatDecButton, 1, 1);
		add(chat, cir.getX() + cir.getWidth() + COMPONENT_SPACING, cir.getY());
		addBorder(chat, COLOR);
		
		/******************************/
		
		CustomButtonGrid epwr = new CustomButtonGrid.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, BUTTON_HEIGHT + 2 * BUTTON_SPACING)
			.withRowsCols(1, 1)
			.withSpacing(BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		ToggleButton epwrButton = new ToggleButton(0, 0, ACCENT_COLOR, "EPWR");
		epwr.addButton(epwrButton, 0, 0);
		add(epwr, chat.getX(), chat.getY() + chat.getHeight() + COMPONENT_SPACING);
		addBorder(epwr, ACCENT_COLOR);
		
		
		String datumNames[] = {"XPOS", "YPOS", "BRX", "BRY", "ALT"};
		/*
		CustomWidgetGroup dat = new CustomWidgetGroup.Builder(datumNames.length * (DATUM_READOUT_WIDTH + BUTTON_SPACING) + BUTTON_SPACING, DATUM_READOUT_HEIGHT + 2 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		*/
		CustomGroup dat = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		for(int i = 0; i < datumNames.length; ++i)
		{
			BufferReadout bfr = new BufferReadout.Builder(DATUM_READOUT_WIDTH, DATUM_READOUT_HEIGHT)
				.withBaseColor(BASE_COLOR)
				.withColor(COLOR)
				.withAccentColor(ACCENT_COLOR)
				.build();
			dat.addWidget(datumNames[i], bfr, (BUTTON_SPACING + DATUM_READOUT_WIDTH) * i + BUTTON_SPACING, BUTTON_SPACING);
		}
		add(dat, epwr.getX() + epwr.getWidth() + COMPONENT_SPACING, epwr.getY());
		addBorder(dat, COLOR);
		
		String vitalNames[] = {"SPD", "THR", "FUEL"};
		/*
		CustomWidgetGroup vit = new CustomWidgetGroup.Builder(vitalNames.length * (DATUM_READOUT_WIDTH + BUTTON_SPACING) + BUTTON_SPACING, DATUM_READOUT_HEIGHT + 2 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.build();
		*/
		CustomGroup vit = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
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
		add(vit, dat.getX(), dat.getY() + dat.getHeight() + COMPONENT_SPACING);
		addBorder(vit, COLOR);
		
		AuxArrowPad aap = new AuxArrowPad.Builder(vit.getWidth(), 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		add(aap, vit.getX(), vit.getY() + vit.getHeight() + COMPONENT_SPACING);
		addBorder(aap, COLOR);
		
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
		SingleIncrementer engsinc2 = engsincBuilder.build();
		engsinc1.setIncrementable(engbr1);
		engsinc2.setIncrementable(engbr2);
		/*
		CustomWidgetGroup.Builder engcwgBuilder = new CustomWidgetGroup.Builder(engsinc1.getWidth() - 2 * BUTTON_SPACING, remainHeight - COMPONENT_SPACING)
			.withBaseColor(BASE_COLOR);
		*/
		CustomGroup.Builder engcwgBuilder = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(0);
		CustomGroup engcwg1 = engcwgBuilder.build();
		CustomGroup engcwg2 = engcwgBuilder.build();
		engcwg1.addWidget("ENGBR1", engbr1, 0, 0);
		engcwg1.addWidget("ENGSINC1", engsinc1, 0, engbr1.getHeight() - BUTTON_SPACING);
		engcwg2.addWidget("ENGBR2", engbr2, 0, 0);
		engcwg2.addWidget("ENGSINC2", engsinc2, 0, engbr2.getHeight() - BUTTON_SPACING);
		add(engcwg1, epwr.getX(), epwr.getY() + epwr.getHeight() + COMPONENT_SPACING);
		add(engcwg2, engcwg1.getX() + engcwg1.getWidth() + 2 * BORDER_WIDTH, engcwg1.getY());
		addBorder(engcwg1, COLOR);
		addBorder(engcwg2, COLOR);
		
		double freqButtonWidth = engbr1.getWidth() - 2 * BUTTON_SPACING;
		MultiIncrementer freqminc = new MultiIncrementer.Builder(freqButtonWidth + 2 * BUTTON_SPACING, 3 * freqButtonWidth + 4 * BUTTON_SPACING)
			.withBaseColor(BASE_COLOR)
			.withButtonColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		double freqBarHeight = freqButtonWidth;
		double freqBarWidth = dat.getX() + dat.getWidth() - vit.getX() - vit.getWidth() - COMPONENT_SPACING - BUTTON_SPACING - freqminc.getWidth();
		BarReadout.Builder freqbrBuilder = new BarReadout.Builder(freqBarWidth + 2 * BUTTON_SPACING, freqBarHeight + 2 * BUTTON_SPACING, NUM_DIVISIONS)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withOrientation(BarReadout.HORIZONTAL);
		CustomGroup freqcwg = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(0)
			.build();
		for(int i = 0; i < 3; ++i)
		{
			BarReadout freqbr = freqbrBuilder.build();
			freqcwg.addWidget("FREQBR" + i, freqbr, 0, i * (BUTTON_SPACING + freqBarHeight));
			freqminc.addIncrementable(freqbr);
		}
		freqcwg.addWidget("FREQMINC", freqminc, BUTTON_SPACING + freqBarWidth, 0);
		add(freqcwg, vit.getX() + vit.getWidth() + COMPONENT_SPACING, vit.getY());
		addBorder(freqcwg, COLOR);
		
		double engcrWidth = (freqcwg.getWidth() - 3.5 * BUTTON_SPACING) / 6.0;
		double engcrHeight = (aap.getY() + aap.getHeight() - vit.getY() - freqcwg.getHeight() - COMPONENT_SPACING - 1.5 * BUTTON_SPACING) / 2.0;
		CustomGroup engcrcwg = new CustomGroup.Builder()
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING/ 2.0)
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
		add(engcrcwg, freqcwg.getX(), freqcwg.getY() + freqcwg.getHeight() + COMPONENT_SPACING);
		addBorder(engcrcwg, COLOR);
		
		/******************************/
		
		double totalWidth = dat.getX() + dat.getWidth() - epwr.getX();
		double chatcrHeight = (chat.getHeight() - 2.0 * BUTTON_SPACING) / 3.0;
		double chatcrWidth = chatcrHeight;
		CustomGroup chatcrcwg = new CustomGroup.Builder()
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
		add(chatcrcwg, chat.getX() + totalWidth - chatcrcwg.getWidth(), chat.getY());
		addBorder(chatcrcwg, COLOR);
		
		double chatbfrHeight = chat.getHeight();
		double chatbfrWidth = totalWidth - chat.getWidth() - chatcrcwg.getWidth() - 2 * COMPONENT_SPACING;
		BufferReadout chatbfr = new BufferReadout.Builder(chatbfrWidth, chatbfrHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		add(chatbfr, chat.getX() + chat.getWidth() + COMPONENT_SPACING, chat.getY());
		addBorder(chatbfr, COLOR);
		
		chatClsButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.clear();
			}
		});
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
		chatIncButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.increment();
			}
		});
		chatDecButton.setOnAction(new Runnable()
		{
			public void run()
			{
				chatbfr.decrement();
			}
		});
		
		/******************************/
		
		double totalHeight = aap.getY() + aap.getHeight() - mpwr.getY();
		double mmrHeight = totalHeight - cir.getHeight() - COMPONENT_SPACING;
		double mmrWidth = cir.getWidth();
		MinimapReadout mmr = new MinimapReadout.Builder(mmrWidth, mmrHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING)
			.withViewRadius(10.0)
			.build();
		double remainWidth = cir.getX() + cir.getWidth() - mpwr.getX();
		add(mmr, mpwr.getX() + remainWidth - mmr.getWidth(), cir.getY() + cir.getHeight() + COMPONENT_SPACING);
		addBorder(mmr, COLOR);
		
		CustomButtonGrid reset = new CustomButtonGrid.Builder(BUTTON_WIDTH + 2 * BUTTON_SPACING, 2 * BUTTON_HEIGHT + 3 * BUTTON_SPACING)
			.withRowsCols(2, 1)
			.withBaseColor(BASE_COLOR)
			.withSpacing(BUTTON_SPACING)
			.build();
		TouchButton resetButton = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, ACCENT_COLOR, "RST");
		ToggleButton commButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, COLOR, "COMM");
		reset.addButton(commButton, 0, 0);
		reset.addButton(resetButton, 1, 0);
		add(reset, mpwr.getX(), mpwr.getY() + totalHeight - reset.getHeight());
		addBorder(reset, ACCENT_COLOR);
		
		double cmprWidth = mpwr.getWidth();
		double cmprHeight = totalHeight - mpwr.getHeight() - reset.getHeight() - 2 * COMPONENT_SPACING;
		CompassReadout.Builder cmprBuilder = new CompassReadout.Builder(cmprWidth, cmprHeight)
			.withBaseColor(BASE_COLOR)
			.withColor(COLOR)
			.withAccentColor(ACCENT_COLOR)
			.withSpacing(BUTTON_SPACING);
		CompassReadout cmpr1 = cmprBuilder.build();
		add(cmpr1, mpwr.getX(), mpwr.getY() + mpwr.getHeight() + COMPONENT_SPACING);
		addBorder(cmpr1, COLOR);
		
		double backgroundWidth = dat.getX() + dat.getWidth() - mpwr.getX() + 2 * COMPONENT_SPACING;
		double backgroundHeight = totalHeight + 2 * COMPONENT_SPACING;
		GRect background = new GRect(backgroundWidth, backgroundHeight);
		background.setFilled(true);
		background.setFillColor(BASE_COLOR);
		add(background, mpwr.getX() - COMPONENT_SPACING, mpwr.getY() - COMPONENT_SPACING);
		background.sendToBack();
	}
	
	public void mousePressed(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mousePressed(e);
		if(cmd != null && prc != null) 
		{
			prc.issue(cmd);
		}
	}
	
	public void mouseReleased(MouseEvent e)
	{
		String cmd = null;
		GObject o = getElementAt(e.getX(), e.getY());
		if(o != null) e.translatePoint(-(int)o.getX(), -(int)o.getY());
		if(o instanceof MouseWidget) cmd = ((MouseWidget) o).mouseReleased(e);
		if(cmd != null && prc != null)
		{
			prc.issue(cmd);
		}
	}
	
	private void addBorder(GObject o, Color c)
	{
		GRect background = new GRect(o.getWidth() + 2 * BORDER_WIDTH, o.getHeight() + 2 * BORDER_WIDTH);
		background.setFilled(true);
		background.setFillColor(c);
		add(background, o.getX() - BORDER_WIDTH, o.getY() - BORDER_WIDTH);
		o.sendToFront();
	}
}
