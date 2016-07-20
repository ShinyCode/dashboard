package dashboard.generator;

import java.awt.Color;

import dashboard.readout.ColorUpdatable;
import dashboard.readout.LocationUpdatable;
import dashboard.readout.Readout;
import dashboard.util.GPointMath;
import acm.graphics.GPoint;

public class DatumGenerator extends Generator
{
	private static final int UPDATE_INTERVAL = 20; // milliseconds between time steps
	private static final double RESISTANCE_COEFF = 12;
	private static final double MASS = 100.0; // mass of ship
	private static final GPoint REL_BEARING = new GPoint(0, 1);
	private static final double MAX_TURN_ANGLE = Math.PI / 6;
	
	private GPoint relThrust;
	private GPoint tanThrust;
	private GPoint centrThrust;
	private GPoint pos;
	private GPoint vel;
	private GPoint acc;
	private GPoint bearing;
	private GPoint resistance;
	private double angVel;
	private double angAcc;
	private double angResistance;
	
	public DatumGenerator()
	{
		super(UPDATE_INTERVAL);
		initState();
	}
	
	private void initState()
	{
		pos = new GPoint(0, 0);
		bearing = new GPoint(0, 1); // Initially facing north
		vel = new GPoint(0, 0);
		acc = new GPoint(0, 0);
		tanThrust = new GPoint(0, 0);
		centrThrust = new GPoint(0, 0);
		relThrust = new GPoint(0, 0);
		angVel = 0.0;
		angAcc = 0.0;
				
	}
	
	// relThrust is in N, and is vector relative to ship's reference frame
	public void setEngineThrust(GPoint relThrust)
	{
		this.relThrust = relThrust;
		// Calculate the absolute thrust from the ship's bearing
		recalcThrust();
	}
	
	public void recalcThrust()
	{
		double angleOffset = GPointMath.angleBetween(REL_BEARING, bearing);
		tanThrust = GPointMath.rot(new GPoint(0.0, relThrust.getY()), angleOffset);
		centrThrust = GPointMath.rot(new GPoint(relThrust.getX(), 0.0), angleOffset);
	}
	
	public GPoint getAcc()
	{
		return acc;
	}
	
	public GPoint getVel()
	{
		return vel;
	}
	
	private void recalcState()
	{
		resistance = GPointMath.scale(vel, -RESISTANCE_COEFF * GPointMath.norm(vel));
		acc = GPointMath.scale(GPointMath.sum(tanThrust, resistance), 1.0 / MASS);
		vel = GPointMath.sum(vel, GPointMath.scale(getAcc(), UPDATE_INTERVAL / 1000.0));
		pos = GPointMath.sum(pos, GPointMath.scale(getVel(), UPDATE_INTERVAL / 1000.0));
		
		angResistance = -RESISTANCE_COEFF * angVel * angVel;
		angAcc = relThrust.getX() + angResistance;
		angVel += angAcc * UPDATE_INTERVAL / 1000.0;
		bearing = GPointMath.rot(bearing, angVel / 100.0);
	}
	
	@Override
	public void generate()
	{
		recalcThrust();
		recalcState();
		for(String key : readouts.keySet())
		{
			Readout readout = readouts.get(key);
			if(readout instanceof LocationUpdatable)
			{
				((LocationUpdatable)readout).update(pos, bearing);
			}
		}
	}

}
