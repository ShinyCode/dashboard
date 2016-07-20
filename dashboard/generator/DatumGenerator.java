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
	private static final double RESISTANCE_COEFF = 1.0;
	private static final double MASS = 100.0; // mass of ship
	private static final GPoint REL_BEARING = new GPoint(0, 1);
	
	private GPoint relThrust;
	private GPoint thrust;
	private GPoint pos;
	private GPoint vel;
	private GPoint acc;
	private GPoint bearing;
	private GPoint resistance;
	
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
		thrust = new GPoint(0, 0);
		relThrust = new GPoint(0, 0);
				
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
		thrust = GPointMath.rot(relThrust, angleOffset);
	}
	
	public GPoint getAcc()
	{
		return acc;
	}
	
	public GPoint getVel()
	{
		return vel;
	}
	
	private void calcResistance()
	{
		resistance = GPointMath.scale(vel, -RESISTANCE_COEFF);
	}
	
	private void updateAcc()
	{
		acc = GPointMath.scale(GPointMath.sum(thrust, resistance), 1.0 / MASS);
	}
	
	private void updateVel()
	{
		vel = GPointMath.sum(vel, GPointMath.scale(getAcc(), UPDATE_INTERVAL / 1000.0));
	}
	
	private void updatePos()
	{
		pos = GPointMath.sum(pos, GPointMath.scale(getVel(), UPDATE_INTERVAL / 1000.0));
	}
	
	private void recalcState()
	{
		calcResistance();
		updateAcc();
		updateVel();
		updatePos();
	}
	
	@Override
	public void generate()
	{
		recalcThrust();
		recalcState();
		System.out.println(pos);
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
