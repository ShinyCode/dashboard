# dashboard
**dashboard** is a widget library designed to emulate the look and feel of complex control panels found in planes and starships. Built on top of the ACM Java libraries, **dashboard** provides the dozens of pre-built widgets and components, as well as a [DashboardProgram](dashboard/program/DashboardProgram.java) class which simplifies dashboard design.

## Table of Contents
- [Motivation](#id-motivation)
- [Skills/Concepts Exercised](#id-skills)
- [Features](#id-features)
- [Usage](#id-usage)
- [Widget Gallery](#id-widgets)

<div id='id-motivation'/>
## Motivation


<div id='id-skills'/>
## Skills/Concepts Exercised
While **dashboard** originally started as a side project for fun, it also became a learning experience in library design and some of Java's more advanced features. Some of the skills/concepts exercised were:

1. Designing effective inheritance hierarchies
2. Builder pattern
3. Javadoc comments
4. Java generics
5. Basic multithreading

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
AddressGenerator addrGen = new AddressGenerator(UPDATE_SPEED);
addrGen.addStringUpdatable("BUFFERREADOUT", bufferReadout); // The BufferReadout class implements StringUpdatable
addrGen.setActive(true);
```

<div id='id-widgets'/>
## Widget Gallery

### Button-based Controls
These controls are normally used within Group-based and ButtonGrid-based controls, but can be used by themselves. 
Each Button-based control has an onAction and an offAction. When these Runnables are invoked depends on the exact control.

#### TouchButton
A Button that executes its onAction exactly once when pressed, and its offAction exactly once when released.

#### HoldButton
A Button that if held, repeatedly executes its onAction until it is released.

#### ToggleButton
A Button that behaves like a switch or flip-flop.

### Group-based Controls
These controls allow for absolute positioning of constituent widgets.

#### CustomGroup
A custom group of widgets that can be constructed at runtime.

### ButtonGrid-based Controls
These controls are built from various Buttons, which makes them both easy to design and use.

#### MainArrowPad
A main arrow pad for controlling a vehicle. The main arrow pad can transmit and copy its button presses to multiple {@link AuxArrowPad AuxArrowPads}.

#### AuxArrowPad
An auxiliary arrow pad for controlling a vehicle, which can be controlled by a MainArrowPad.

#### BufferReadoutControl
A controller for the BufferReadout class.

#### SingleIncrementer
A controller for a single Incrementable.

#### MultiIncrementer
A controller for multiple Incrementables.

#### CustomButtonGrid
A custom grid of Buttons that can be constructed at runtime.

### Readouts
In contrast to Controls, Readouts have no mouse functionality. Rather, they are used for displaying various types of data.
The exact type of data accepted by a Readout depends on the exact Updatable interface it implements.

#### BarReadout
Represents a bar whose length scales linearly with input values.

#### BufferReadout
Represents a scrollable graphical buffer of text.

#### ColorReadout
Represents an LED status light.

#### CompassReadout
Represents a compass which always points in the direction of a user-specified goal.

#### DialReadout
Represents a dial whose angle scales linearly with input values.

#### ImageReadout
Represents a video screen.

#### MinimapReadout
Represents a minimap with pins to mark various locations.

### Generators
Generators generate data for the sole cosmetic purpose of animating Readouts. Being undrawn, they work behind-the-scenes
to make a dashboard seem "busier".

#### AddressGenerator
Generates random memory addresses in hexadecimal.

