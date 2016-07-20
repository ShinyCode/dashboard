package dashboard.generator;

import java.awt.Color;

import dashboard.control.AuxArrowPad;
import dashboard.control.Button;
import dashboard.control.ToggleButton;
import dashboard.readout.BufferReadout;
import dashboard.readout.ColorUpdatable;
import dashboard.readout.LevelReadout;
import dashboard.readout.LocationUpdatable;
import dashboard.readout.Readout;
import dashboard.util.GPointMath;
import acm.graphics.GPoint;

public class DatumGenerator extends Generator
{
	private static final int CALC_INTERVAL = 20; // milliseconds between time steps
	private static final double RESISTANCE_COEFF = 2.0;
	private static final double ROT_RESISTANCE_COEFF = 2.0;
	private static final double MASS = 10.0; // mass of ship
	private static final GPoint REL_BEARING = new GPoint(0, 1);
	private static final double DEFAULT_MAIN_THRUST = 100.0;
	private static final double DEFAULT_ROT_THRUST = 50.0;
	
	private GPoint relThrust;
	private GPoint mainThrust;
	private GPoint position;
	private GPoint velocity;
	private GPoint acceleration;
	private GPoint bearing;
	private GPoint resistance;
	private double rotVelocity;
	private double rotAcceleration;
	private double rotResistance;
	
	private LevelReadout mainThrustSource;
	private LevelReadout rotThrustSource;
	
	private BufferReadout xPositionReadout;
	private BufferReadout yPositionReadout;
	private BufferReadout xBearingReadout;
	private BufferReadout yBearingReadout;
	
	private LevelReadout speedReadout;
	private LevelReadout rotSpeedReadout;
	
	private ToggleButton ignitionSwitch;
	
	public DatumGenerator(int interval)
	{
		super(interval);
		initState();
		beginCalcState();
	}
	
	private void initState()
	{
		position = new GPoint(0, 0);
		bearing = new GPoint(0, 1); // Initially facing north
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);
		mainThrust = new GPoint(0, 0);
		relThrust = new GPoint(0, 0);
		rotVelocity = 0.0;
		rotAcceleration = 0.0;	
	}
	
	private void beginCalcState()
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				while(!Thread.interrupted())
				{	
					try
					{
						recalcState();
						Thread.sleep(CALC_INTERVAL);
					}
					catch(InterruptedException e)
					{
						System.out.println("Physics calculations have been interrupted!");
						return;
					}
					
				}
			}
		}).start();
	}
	
	// relThrust is in N, and is vector relative to ship's reference frame
	public void setEngineThrust(GPoint relThrust)
	{
		this.relThrust = relThrust;
	}
	
	public void recalcThrust()
	{
		double angleOffset = GPointMath.angleBetween(REL_BEARING, bearing);
		mainThrust = GPointMath.rot(new GPoint(0.0, relThrust.getY()), angleOffset);
	}
	
	public GPoint getAcceleration()
	{
		return acceleration;
	}
	
	public GPoint getVelocity()
	{
		return velocity;
	}
	
	// Calculates all the physics
	private void recalcState()
	{
		recalcThrust();
		resistance = GPointMath.scale(velocity, -RESISTANCE_COEFF * GPointMath.norm(velocity));
		acceleration = GPointMath.scale(GPointMath.sum(mainThrust, resistance), 1.0 / MASS);
		velocity = GPointMath.sum(velocity, GPointMath.scale(getAcceleration(), CALC_INTERVAL / 1000.0));
		position = GPointMath.sum(position, GPointMath.scale(getVelocity(), CALC_INTERVAL / 1000.0));
		
		rotResistance = -ROT_RESISTANCE_COEFF * rotVelocity * Math.abs(rotVelocity);
		rotAcceleration = relThrust.getX() + rotResistance;
		rotVelocity += rotAcceleration * CALC_INTERVAL / 1000.0;
		bearing = GPointMath.rot(bearing, rotVelocity / 200.0);
	}
	
	public void bindController(AuxArrowPad controller)
	{
		ToggleButton fwdButton = (ToggleButton)controller.getButton(0, 1);
		ToggleButton revButton = (ToggleButton)controller.getButton(1, 1);
		ToggleButton leftButton = (ToggleButton)controller.getButton(1, 0);
		ToggleButton rightButton = (ToggleButton)controller.getButton(1, 2);
		
		fwdButton.setOnAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(revButton.isOn()) revButton.setOn(false);
				if(!ignitionSwitch.isOn()) return;
				if(mainThrustSource == null) setEngineThrust(new GPoint(relThrust.getX(), DEFAULT_MAIN_THRUST));
				else setEngineThrust(new GPoint(relThrust.getX(), mainThrustSource.getValue()));
			}
		});
		fwdButton.setOffAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(!ignitionSwitch.isOn()) return;
				setEngineThrust(new GPoint(relThrust.getX(), 0.0));
			}
		});
		
		revButton.setOnAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(fwdButton.isOn()) fwdButton.setOn(false);
				if(!ignitionSwitch.isOn()) return;
				if(mainThrustSource == null) setEngineThrust(new GPoint(relThrust.getX(), -DEFAULT_MAIN_THRUST));
				else setEngineThrust(new GPoint(relThrust.getX(), -mainThrustSource.getValue()));
			}
		});
		revButton.setOffAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(!ignitionSwitch.isOn()) return;
				setEngineThrust(new GPoint(relThrust.getX(), 0.0));
			}
		});
		
		leftButton.setOnAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(rightButton.isOn()) rightButton.setOn(false);
				if(!ignitionSwitch.isOn()) return;
				if(rotThrustSource == null) setEngineThrust(new GPoint(DEFAULT_ROT_THRUST, relThrust.getY()));
				else setEngineThrust(new GPoint(rotThrustSource.getValue(), relThrust.getY()));
			}
		});
		leftButton.setOffAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(!ignitionSwitch.isOn()) return;
				setEngineThrust(new GPoint(0.0, relThrust.getY()));
			}
		});
		
		rightButton.setOnAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(leftButton.isOn()) leftButton.setOn(false);
				if(!ignitionSwitch.isOn()) return;
				if(rotThrustSource == null) setEngineThrust(new GPoint(-DEFAULT_ROT_THRUST, relThrust.getY()));
				else setEngineThrust(new GPoint(-rotThrustSource.getValue(), relThrust.getY()));
			}
		});
		rightButton.setOffAction(new Runnable()
		{
			@Override
			public void run()
			{
				if(!ignitionSwitch.isOn()) return;
				setEngineThrust(new GPoint(0.0, relThrust.getY()));
			}
		});
	}
	
	public void setMainThrustSource(LevelReadout mainThrustSource)
	{
		this.mainThrustSource = mainThrustSource;
	}
	
	public void setRotThrustSource(LevelReadout rotThrustSource)
	{
		this.rotThrustSource = rotThrustSource;
	}
	
	public void setPositionReadouts(BufferReadout xPositionReadout, BufferReadout yPositionReadout)
	{
		this.xPositionReadout = xPositionReadout;
		this.yPositionReadout = yPositionReadout;
	}
	
	public void setBearingReadouts(BufferReadout xBearingReadout, BufferReadout yBearingReadout)
	{
		this.xBearingReadout = xBearingReadout;
		this.yBearingReadout = yBearingReadout;
	}
	
	public void setSpeedReadouts(LevelReadout speedReadout, LevelReadout rotSpeedReadout)
	{
		this.speedReadout = speedReadout;
		this.rotSpeedReadout = rotSpeedReadout;
	}
	
	public void setIgnitionSwitch(ToggleButton ignitionSwitch)
	{
		this.ignitionSwitch = ignitionSwitch;
		ignitionSwitch.setOffAction(new Runnable()
		{
			@Override
			public void run()
			{
				setEngineThrust(new GPoint(0.0, 0.0));
			}
		});
	}
	
	@Override
	public void generate()
	{
		if(xPositionReadout != null) xPositionReadout.update(Double.toString(position.getX()));
		if(yPositionReadout != null) yPositionReadout.update(Double.toString(position.getY()));
		if(xBearingReadout != null) xBearingReadout.update(Double.toString(bearing.getX()));
		if(yBearingReadout != null) yBearingReadout.update(Double.toString(bearing.getY()));
		if(speedReadout != null) speedReadout.update(GPointMath.norm(velocity));
		if(rotSpeedReadout != null) rotSpeedReadout.update(Math.abs(rotVelocity));
		for(String key : readouts.keySet())
		{
			Readout readout = readouts.get(key);
			if(readout instanceof LocationUpdatable)
			{
				((LocationUpdatable)readout).update(position, bearing);
			}
		}
	}

}
