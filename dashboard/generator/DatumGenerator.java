package dashboard.generator;

import dashboard.control.AuxArrowPad;
import dashboard.control.ToggleButton;
import dashboard.readout.BufferReadout;
import dashboard.readout.LevelReadout;
import dashboard.readout.LocationUpdatable;
import dashboard.readout.Readout;
import dashboard.util.GPointMath;
import acm.graphics.GPoint;

/**
 * Implements a Generator with a simple physics engine that simulates the ship's position
 * and allows the user to control the ship. The physics engine is not meant to be an entirely
 * accurate simulation of motion, rather one that is relatively simple to implement and still has
 * good performance. In order to work, a DatumGenerator needs an ignition switch {@link ToggleButton ToggleButton}
 * and a controller {@link AuxArrowPad AuxArrowPad}.
 * 
 * @author Mark Sabini
 *
 */
public class DatumGenerator extends Generator
{
	/**
	 * How frequently the physics engine recalculates its state
	 */
	private static final int CALC_INTERVAL = 20;
	
	/**
	 * Arbitrary constant representing resistive forces during translation
	 */
	private static final double RESISTANCE_COEFF = 2.0;
	
	/**
	 * Arbitrary constant representing resistive forces during rotation
	 */
	private static final double ROT_RESISTANCE_COEFF = 2.0;
	
	/**
	 * Arbitrary constant that affects how difficult it is to move the ship
	 */
	private static final double MASS = 10.0;
	
	/**
	 * Relative bearing, i.e. the direction the ship faces relative to itself (north)
	 */
	private static final GPoint REL_BEARING = new GPoint(0, 1);
	
	/**
	 * The default thrust supplied to the main engines if no LevelReadouts are connected
	 */
	private static final double DEFAULT_MAIN_THRUST = 100.0;
	
	/**
	 * The default thrust supplied to the rotational engines if no LevelReadouts are connected
	 */
	private static final double DEFAULT_ROT_THRUST = 50.0;
	
	/**
	 * Thrust vector relative to the ship itself
	 */
	private GPoint relThrust;
	
	/**
	 * Tangential thrust vector relative to the ground
	 */
	private GPoint mainThrust;
	
	/**
	 * Current position of the ship
	 */
	private GPoint position;
	
	/**
	 * Current velocity of the ship
	 */
	private GPoint velocity;
	
	/**
	 * Current acceleration of the ship
	 */
	private GPoint acceleration;
	
	/**
	 * Current bearing of the ship
	 */
	private GPoint bearing;
	
	/**
	 * Current resistive forces acting on the ship
	 */
	private GPoint resistance;
	
	/**
	 * Current signed rotational speed of the ship
	 */
	private double rotSpeed;
	
	/**
	 * Current signed rotational acceleration of the ship
	 */
	private double rotAcceleration;
	
	/**
	 * Current rotational resistive forces acting on the ship
	 */
	private double rotResistance;
	
	/**
	 * Controls that adjust the amount of thrust from the engines
	 */
	private LevelReadout mainThrustSource, rotThrustSource;
	
	/**
	 * Optional Readouts that can display information about the ship's position and bearing
	 */
	private BufferReadout xPositionReadout, yPositionReadout, xBearingReadout, yBearingReadout;
	
	/**
	 * Optional Readouts that can display information about the ship's speed
	 */
	private LevelReadout speedReadout, rotSpeedReadout;
	
	/**
	 * The engine ignition switch. Needs to be on for the engine to work.
	 */
	private ToggleButton ignitionSwitch;
	
	/**
	 * Creates a DatumGenerator with the specified timing interval. Note the interval
	 * refers to how often the DatumGenerator updates its Readouts - the internal physics
	 * engine uses its own timing interval.
	 * 
	 * @param interval the time interval between updates, measured in milliseconds
	 */
	public DatumGenerator(int interval)
	{
		super(interval);
		initState();
		beginCalcState();
	}
	
	/**
	 * Initializes the state of the ship.
	 */
	private void initState()
	{
		position = new GPoint(0, 0);
		bearing = new GPoint(0, 1); // Initially facing north
		velocity = new GPoint(0, 0);
		acceleration = new GPoint(0, 0);
		mainThrust = new GPoint(0, 0);
		relThrust = new GPoint(0, 0);
		rotSpeed = 0.0;
		rotAcceleration = 0.0;	
	}
	
	/**
	 * Begins calculating the state of the ship. If calculations are interrupted,
	 * a message will be printed to the console.
	 */
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
	
	/**
	 * Specifies the thrust from the engines as a vector relative to the ship itself.
	 * 
	 * @param relThrust the thrust from the engines as a vector relative to the ship itself
	 */
	public void setEngineThrust(GPoint relThrust)
	{
		this.relThrust = relThrust;
	}
	
	/**
	 * Recalculates the thrust, and translates the thrust relative to the ship to one
	 * relative to the ground
	 */
	private void recalcThrust()
	{
		double angleOffset = GPointMath.angleBetween(REL_BEARING, bearing);
		mainThrust = GPointMath.rot(new GPoint(0.0, relThrust.getY()), angleOffset);
	}
	
	/**
	 * Returns the current acceleration of the ship.
	 * 
	 * @return the current acceleration of the ship
	 */
	public GPoint getAcceleration()
	{
		return acceleration;
	}
	
	/**
	 * Returns the current velocity of the ship.
	 * 
	 * @return the current velocity of the ship
	 */
	public GPoint getVelocity()
	{
		return velocity;
	}
	
	/**
	 * Recalculates the state of the ship based on the engines' thrust
	 */
	private void recalcState()
	{
		recalcThrust();
		resistance = GPointMath.scale(velocity, -RESISTANCE_COEFF * GPointMath.norm(velocity));
		acceleration = GPointMath.scale(GPointMath.sum(mainThrust, resistance), 1.0 / MASS);
		velocity = GPointMath.sum(velocity, GPointMath.scale(getAcceleration(), CALC_INTERVAL / 1000.0));
		position = GPointMath.sum(position, GPointMath.scale(getVelocity(), CALC_INTERVAL / 1000.0));
		
		rotResistance = -ROT_RESISTANCE_COEFF * rotSpeed * Math.abs(rotSpeed);
		rotAcceleration = relThrust.getX() + rotResistance;
		rotSpeed += rotAcceleration * CALC_INTERVAL / 1000.0;
		bearing = GPointMath.rot(bearing, rotSpeed / 200.0);
	}
	
	/**
	 * Sets an auxiliary arrow pad to control the DatumGenerator. A DatumGenerator
	 * can only have one controller.
	 * 
	 * @param controller the auxiliary arrow pad that will control the DatumGenerator
	 */
	public void setController(AuxArrowPad controller)
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
				if(!revButton.isOn()) setEngineThrust(new GPoint(relThrust.getX(), 0.0));
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
				if(!fwdButton.isOn()) setEngineThrust(new GPoint(relThrust.getX(), 0.0));
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
				if(!rightButton.isOn()) setEngineThrust(new GPoint(0.0, relThrust.getY()));
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
				if(!leftButton.isOn()) setEngineThrust(new GPoint(0.0, relThrust.getY()));
			}
		});
	}
	
	/**
	 * Sets the source used to determine the main engine thrust.
	 * 
	 * @param mainThrustSource the source used to determine the main engine thrust
	 */
	public void setMainThrustSource(LevelReadout mainThrustSource)
	{
		this.mainThrustSource = mainThrustSource;
	}
	
	/**
	 * Sets the source used to determine the rotational engine thrust.
	 * 
	 * @param rotThrustSource the source used to determine the rotational engine thrust.
	 */
	public void setRotThrustSource(LevelReadout rotThrustSource)
	{
		this.rotThrustSource = rotThrustSource;
	}
	
	/**
	 * Sets where the x and y position will be output.
	 * 
	 * @param xPositionReadout the Readout that will display the x position
	 * @param yPositionReadout the Readout that will display the y position
	 */
	public void setPositionReadouts(BufferReadout xPositionReadout, BufferReadout yPositionReadout)
	{
		this.xPositionReadout = xPositionReadout;
		this.yPositionReadout = yPositionReadout;
	}
	
	/**
	 * Sets where the x and y bearing will be output.
	 * 
	 * @param xBearingReadout the Readout that will display the x bearing
	 * @param yBearingReadout the Readout that will display the y bearing
	 */
	public void setBearingReadouts(BufferReadout xBearingReadout, BufferReadout yBearingReadout)
	{
		this.xBearingReadout = xBearingReadout;
		this.yBearingReadout = yBearingReadout;
	}
	
	/**
	 * Sets where the speed and rotational speed will be output.
	 * 
	 * @param speedReadout the Readout that will display the speed
	 * @param rotSpeedReadout the Readout that will display the rotational speed
	 */
	public void setSpeedReadouts(LevelReadout speedReadout, LevelReadout rotSpeedReadout)
	{
		this.speedReadout = speedReadout;
		this.rotSpeedReadout = rotSpeedReadout;
	}
	
	/**
	 * Sets the ignition switch that will turn the engine on and off.
	 * 
	 * @param ignitionSwitch the ignition switch that will turn the engine on and off
	 */
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
	
	/**
	 * Updates all the Readouts associated with the DatumGenerator. A complete DatumGenerator setup
	 * will have one {@link ToggleButton ToggleButton} (ignition switch), one {@link AuxArrowPad AuxArrowPad} (controller),
	 * four {@link LevelReadout LevelReadouts} (two for engine control, two for speed),
	 * two {@link dashboard.control.SingleIncrementer SingleIncrementers} (to control the engine levels),
	 * four {@link BufferReadout BufferReadouts} (to print out position and bearing), and several
	 * {@link LocationUpdatable LocationUpdatables} (such as a {@link dashboard.readout.CompassReadout CompassReadout}
	 * and {@link dashboard.readout.MinimapReadout MinimapReadout}).
	 */
	@Override
	public void generate()
	{
		if(xPositionReadout != null) xPositionReadout.update(Double.toString(position.getX()));
		if(yPositionReadout != null) yPositionReadout.update(Double.toString(position.getY()));
		if(xBearingReadout != null) xBearingReadout.update(Double.toString(bearing.getX()));
		if(yBearingReadout != null) yBearingReadout.update(Double.toString(bearing.getY()));
		if(speedReadout != null) speedReadout.update(GPointMath.norm(velocity));
		if(rotSpeedReadout != null) rotSpeedReadout.update(Math.abs(rotSpeed));
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
