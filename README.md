# dashboard
**dashboard** is a widget library designed to emulate the look and feel of complex control panels found in planes and starships. Built on top of the ACM Java libraries, **dashboard** provides the dozens of pre-built widgets and components, as well as a DashboardProgram class which simplifies dashboard design.

![Sample Dashboard](res/splash.PNG "Sample Dashboard")

## Table of Contents
- [Features](#id-features)
- [Usage](#id-usage)
- [Widget Gallery](#id-widgets)
  - [Button-based Controls](#id-button-controls)
  - [Group-based Controls](#id-group-controls)
  - [ButtonGrid-based Controls](#id-buttongrid-controls)
  - [Readouts](#id-readouts)
  - [Generators](#id-generators)
- [Javadoc](#id-javadoc)

<div id='id-features'/>
## Features
At its core, **dashboard** provides the DashboardProgram class (which simplifies dashboard design) and three main types of dashboard components (Controls, Readouts, and Generators).

* The DashboardProgram class extends ACM's GraphicsProgram by adding methods to:
 * draw borders and backgrounds around components
 * add and get widgets by an associated key
 * handle MouseEvents and transmit them to the components in the dashboard
* Controls are dashboard components that have mouse functionality and are used to manipulate other components. Examples include SingleIncrementer, BufferReadoutControl, and MainArrowPad.
* Readouts are dashboard components that display data. Examples include DialReadout, ImageReadout, and CompassReadout.
* Generators are undrawn components that generate data for the sole cosmetic purpose of updating Readouts. An example is the AddressGenerator.

<div id='id-usage'/>
## Usage
The library makes extensive use of the Builder pattern. In general, the first two arguments passed to a Builder's constructor will be the width and height of the specified widgets. General library usage is shown below, while more specific information pertaining to individual widgets can be found either in the widget gallery or Javadoc.

### Using a Builder
```java
ImageReadout ir = new ImageReadout.Builder(WIDTH, HEIGHT)
	.withBaseColor(BASE_COLOR)
	.withColor(COLOR)
	.withAccentColor(ACCENT_COLOR)
	.withSpacing(BUTTON_SPACING)
	.withOffColor(Color.BLACK)
	.build();
```
### Creating identical widgets
To reduce code redundancy, the Builder can be reused to create widgets with identical specifications:
```java
SingleIncrementer.Builder builder = new SingleIncrementer.Builder(WIDTH, HEIGHT)
	.withBaseColor(BASE_COLOR)
	.withButtonColor(ACCENT_COLOR)
	.withSpacing(BUTTON_SPACING);
SingleIncrementer first = builder.build();
SingleIncrementer second = builder.build();
```

### Forming a CustomGroup
A CustomGroup groups widgets together to simplify positioning them. Adding objects to a CustomGroup requires that an associated key be given:
```java
CustomGroup group = new CustomGroup()
	.withBaseColor(BASE_COLOR)
	.withSpacing(0)
	.build();
group.addWidget("START", new TouchButton(WIDTH, HEIGHT, Color.GREEN, "START"), 0, 0);
group.addWidget("STOP", new TouchButton(WIDTH, HEIGHT, Color.RED, "STOP"), 0, HEIGHT);
```

### Setting custom callbacks for Buttons
The setOnAction and setOffAction methods accept Runnables. How and when these Runnables will be invoked depends on the specific type of Button:
```java
ToggleButton switch = new ToggleButton(WIDTH, HEIGHT, Color.BLUE, "SWITCH");
switch.setOnAction(new Runnable()
{
	@Override
	public void run()
	{
		System.out.println("I've been switched on.");
  	}
});
switch.setOffAction(new Runnable()
{
  	@Override
  	public void run()
  	{
    	System.out.println("I've been switched off.");
  	}
});
```

### Forming a CustomButtonGrid
The CustomButtonGrid arranges its Buttons in a grid, resizing them to the necessary size. So, the Buttons can be created with zero width and height:
```java
TouchButton topLeft = new TouchButton(0, 0, Color.RED, "TOPLEFT");
TouchButton topRight = new TouchButton(0, 0, Color.BLUE, "TOPRIGHT");
TouchButton bottomLeft = new TouchButton(0, 0, Color.GREEN, "BOTLEFT");
TouchButton bottomRight = new TouchButton(0, 0, Color.YELLOW, "BOTRIGHT");
CustomButtonGrid array = new CustomButtonGrid.Builder(WIDTH, HEIGHT)
  	.withRowsCols(2, 2)
  	.withSpacing(SPACING)
  	.withBaseColor(BASE_COLOR)
  	.build();
array.addButton(topLeft, 0, 0);
array.addButton(topRight, 1, 0);
array.addButton(bottomLeft, 0, 1);
array.addButton(bottomRight, 1, 1);
```

### Using a Generator
Once a Generator has been created, it needs to be linked to a specific readout, and then started:
```java
AddressGenerator addrGen = new AddressGenerator(UPDATE_INTERVAL);
addrGen.addReadout("BFR", bufferReadout);
addrGen.setActive(true);
```

<div id='id-widgets'/>
## Widget Gallery
Below is a listing of all the Controls, Readouts, and Generators in **dashboard** as well as some code snippets detailing usage. Exact class and method descriptions can be found in the Javadoc.

<div id='id-button-controls'/>
### Button-based Controls
These controls are normally used within Group-based and ButtonGrid-based controls, but can be used by themselves. 
Each Button-based control has an onAction and an offAction. When these Runnables are invoked depends on the exact control.

#### TouchButton
A Button that executes its onAction exactly once when pressed, and its offAction exactly once when released.

![TouchButton](res/TouchButton.PNG "TouchButton")

```java
TouchButton touchButton = new TouchButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.RED.darker(), "TOUCH");
addWidget("TOUCH", touchButton, X, Y);
```

#### HoldButton
A Button that if held, repeatedly executes its onAction until it is released.

![HoldButton](res/HoldButton.PNG "HoldButton")

```java
HoldButton holdButton = new HoldButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.GREEN.darker(), "HOLD");
addWidget("HOLD", holdButton, X, Y);
```

#### ToggleButton
A Button that behaves like a switch or flip-flop.

![ToggleButton](res/ToggleButton.PNG "ToggleButton")

```java
ToggleButton toggleButton = new ToggleButton(BUTTON_WIDTH, BUTTON_HEIGHT, Color.BLUE.darker(), "TOGGLE");
addWidget("TOGGLE", toggleButton, X, Y);
```

<div id='id-group-controls'/>
### Group-based Controls
These controls allow for absolute positioning of constituent widgets.

#### CustomGroup
A custom group of widgets that can be constructed at runtime.

<div id='id-buttongrid-controls'/>
### ButtonGrid-based Controls
These controls are built from various Buttons, which makes them both easy to design and use.

#### MainArrowPad
A main arrow pad for controlling a vehicle. The main arrow pad can transmit and copy its button presses to multiple AuxArrowPads.

![MainArrowPad](res/MainArrowPad.PNG "MainArrowPad")

```java
MainArrowPad mainArrowPad = new MainArrowPad.Builder(CONTROL_WIDTH, CONTROL_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withButtonColor(BUTTON_COLOR)
  	.withSpacing(BUTTON_SPACING)
  	.build();
addWidget("MAP", mainArrowPad, X, Y);
```

#### AuxArrowPad
An auxiliary arrow pad for controlling a vehicle, which can be controlled by a MainArrowPad.

![AuxArrowPad](res/AuxArrowPad.PNG "AuxArrowPad")

```java
AuxArrowPad auxArrowPad = new AuxArrowPad.Builder(CONTROL_WIDTH, CONTROL_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withButtonColor(BUTTON_COLOR)
  	.withSpacing(BUTTON_SPACING)
  	.build();
addWidget("AAP", auxArrowPad, X, Y);
```

#### BufferReadoutControl
A controller for the BufferReadout class.

![BufferReadoutControl](res/BufferReadoutControl.PNG "BufferReadoutControl")

```java
BufferReadoutControl bufferReadoutControl = new BufferReadoutControl.Builder(CONTROL_WIDTH, CONTROL_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withButtonColor(BUTTON_COLOR)
  	.withSpacing(BUTTON_SPACING)
  	.build();
addWidget("BRC", bufferReadoutControl, X, Y);
```


#### SingleIncrementer
A controller for a single Incrementable.

![SingleIncrementer](res/SingleIncrementer.PNG "SingleIncrementer")

```java
SingleIncrementer singleInc = new SingleIncrementer.Builder(CONTROL_WIDTH, CONTROL_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withButtonColor(BUTTON_COLOR)
  	.withSpacing(BUTTON_SPACING)
  	.build();
addWidget("SINC", singleInc, X, Y);
```

#### MultiIncrementer
A controller for multiple Incrementables.

![MultiIncrementer](res/MultiIncrementer.PNG "MultiIncrementer")

```java
MultiIncrementer multiInc = new MultiIncrementer.Builder(CONTROL_WIDTH, CONTROL_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withButtonColor(BUTTON_COLOR)
  	.withSpacing(BUTTON_SPACING)
  	.build();
addWidget("MINC", multiInc, X, Y);
```

#### CustomButtonGrid
A custom grid of Buttons that can be constructed at runtime.

<div id='id-readouts'/>
### Readouts
In contrast to Controls, Readouts have no mouse functionality. Rather, they are used for displaying various types of data.
The exact type of data accepted by a Readout depends on the exact Updatable interface it implements.

#### BarReadout
Represents a bar whose length scales linearly with input values.

![BarReadout](res/BarReadout.PNG "BarReadout")

```java
BarReadout barReadout = new BarReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT, NUM_DIVISIONS)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
  	.withSpacing(READOUT_SPACING)
  	.withOrientation(BarReadout.VERTICAL) // Use BarReadout.HORIZONTAL for other orientation
  	.withRange(0.0, 200.0)
  	.build();
addWidget("BR", barReadout, X, Y);
barReadout.update(132.0);
```

#### BufferReadout
Represents a scrollable graphical buffer of text.

![BufferReadout](res/BufferReadout.PNG "BufferReadout")

```java
BufferReadout bufferReadout = new BufferReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
 	.withSpacing(READOUT_SPACING)
  	.withFont("Consolas-*-*")
  	.build();
addWidget("BFR", bufferReadout, X, Y);
bufferReadout.update("This is a BufferReadout, great for storing text!");
```

#### ColorReadout
Represents an LED status light.

![ColorReadout](res/ColorReadout.PNG "ColorReadout")

```java
ColorReadout.Builder colorReadoutBuilder = new ColorReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withSpacing(READOUT_SPACING);
Color[] colors = {Color.RED, Color.GREEN, Color.BLUE};
for(int i = 0; i < colors.length; ++i)
{
  	ColorReadout colorReadout = colorReadoutBuilder.build();
  	addWidget("CR" + i, colorReadout, X + i * (READOUT_WIDTH + COMPONENT_SPACING), Y);
  	colorReadout.update(colors[i]);
}
```

#### CompassReadout
Represents a compass which always points in the direction of a user-specified goal.

![CompassReadout](res/CompassReadout.PNG "CompassReadout")

```java
CompassReadout compassReadout = new CompassReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
  	.withSpacing(READOUT_SPACING)
  	.withLocation(null, null)
  	.withGoal(null)
  	.build();
addWidget("CMPR", compassReadout, X, Y);
```
Goals are specified by using ACM's GPoints, and the locations require two GPoints - one for the position, and one for the rotation.
```java
compassReadout.updateGoal(new GPoint(1, 1));
compassReadout.update(new GPoint(0, 0), new GPoint(1, 0));
```

#### DialReadout
Represents a dial whose angle scales linearly with input values.

![DialReadout](res/DialReadout.PNG "DialReadout")

```java
DialReadout dialReadout = new DialReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT, NUM_DIVISIONS)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
  	.withSpacing(READOUT_SPACING)
  	.withStartAngle(0.0)
  	.withSweepAngle(180.0)
  	.withRange(0.0, 200.0)
  	.build();
addWidget("DR", dialReadout, X, Y);
dialReadout.update(150.0);
```

#### ImageReadout
Represents a video screen.

![ImageReadout](res/ImageReadout.PNG "ImageReadout")

```java
ImageReadout imageReadout = new ImageReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
  	.withSpacing(READOUT_SPACING)
  	.withOffColor(Color.BLACK)
  	.build();
addWidget("IR", imageReadout, X, Y);
imageReadout.update(new GImage("imagename.ext"));
```

#### MinimapReadout
Represents a minimap with pins to mark various locations.

![MinimapReadout](res/MinimapReadout.PNG "MinimapReadout")

```java
MinimapReadout minimapReadout = new MinimapReadout.Builder(READOUT_WIDTH, READOUT_HEIGHT)
  	.withBaseColor(BASE_COLOR)
  	.withColor(COLOR)
  	.withAccentColor(ACCENT_COLOR)
  	.withSpacing(READOUT_SPACING)
  	.withViewRadius(10.0)
  	.build();
addWidget("MMR", minimapReadout, X, Y);
```
Pins require a name, position, and color. Updating the location requires two GPoints - one for the position, and one for the rotation.
```java
minimapReadout.addPin("NORTHEAST", new GPoint(5.0, 5.0), Color.RED);
minimapReadout.addPin("SOUTH", new GPoint(0.0, -4.0), Color.GREEN);
minimapReadout.addPin("WEST", new GPoint(-7.0, 0.0), Color.PINK);
minimapReadout.update(new GPoint(0, 0), new GPoint(0, 1));
```

<div id='id-generators'/>
### Generators
Generators generate data for the sole cosmetic purpose of animating Readouts. Being undrawn, they work behind-the-scenes
to make a dashboard seem "busier". All Generator constructors take an update interval as their first argument, which specifies
how often the Generator should run.

#### DatumGenerator
Implements a simple physics engine to simulate a ship's position and motion. An ignition switch
and controller are necessary for the DatumGenerator to work, as without them, the ship cannot be neither
started nor controlled. While not required, one can control the exact engine thrust level by using a
LevelReadout.
```java
DatumGenerator datumGen = new DatumGenerator(UPDATE_INTERVAL);
datumGen.setIgnitionSwitch(toggleButton);
datumGen.setController(auxArrowPad);
datumGen.setMainThrustSource(levelReadout);
datumGen.setRotThrustSource(levelReadout);
datumGen.setActive(true);
```

The DatumGenerator is used to update LocationUpdatable Readouts:
```java
datumGen.addReadout("MMR", minimapReadout);
datumGen.addReadout("CMPR", compassReadout);
```

However, the DatumGenerator can also output position, bearing, speed, and rotational speed:
```java
datumGen.setPositionReadouts(xBufferReadout, yBufferReadout);
datumGen.setBearingReadouts(xBufferReadout, yBufferReadout);
datumGen.setSpeedReadouts(speedLevelReadout, rotSpeedLevelReadout);
```

#### BufferGenerator
Reads text from a file and simulates data transmissions. Messages can be reset and replayed.
```java
BufferGenerator bufferGen = new BufferGenerator(UPDATE_INTERVAL);
bufferGen.addReadout("BFR", bufferReadout);
bufferGen.setSource("filename.ext");
bufferGen.setActive(true);
```

#### AddressGenerator
Generates random memory addresses in hexadecimal.
```java
AddressGenerator addrGen = new AddressGenerator(UPDATE_INTERVAL);
addrGen.addReadout("BFR", bufferReadout);
addrGen.setActive(true);
```

#### ColorGenerator
Generates random RGB or HSB colors.
```java
ColorGenerator colorGen = new ColorGenerator(UPDATE_INTERVAL);
colorGen.addReadout("CR", colorReadout);
colorGen.setActive(true);
```

<div id='id-javadoc'/>
## Javadoc
The full documentation for the library can be found at this [Javadoc](https://shinycode.github.io/projects/dashboard/doc/index.html).


