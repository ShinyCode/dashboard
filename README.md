# dashboard
**dashboard** is a widget library designed to emulate the look and feel of complex control panels found in planes and starships. Built on top of the ACM Java libraries, **dashboard** provides the dozens of pre-built widgets and components, as well as a [DashboardProgram](dashboard/program/DashboardProgram.java) class which simplifies dashboard design.

## Motivation


## Skills/Concepts Exercised
While **dashboard** originally started as a side project for fun, it also became a learning experience in library design and some of Java's more advanced features. Some of the skills/concepts exercised were:

1. Designing effective inheritance hierarchies
2. Builder pattern
3. Javadoc comments
4. Java generics
5. Basic multithreading

## Features
At its core, **dashboard** provides the DashboardProgram class (which simplifies dashboard design) and three main types of dashboard components (Controls, Readouts, and Generators).

* The DashboardProgram class extends ACM's GraphicsProgram by adding methods to:
 * draw borders and backgrounds around components
 * add and get widgets by an associated key
 * handle MouseEvents and transmit them to the components in the dashboard
* Controls are dashboard components that have mouse functionality and are used to manipulate other components. Examples include SingleIncrementer, BufferReadoutControl, and MainArrowPad.
* Readouts are dashboard components that display data. Examples include DialReadout, ImageReadout, and CompassReadout.
* Generators are undrawn components that generate data for the sole cosmetic purpose of updating Readouts. An example is the AddressGenerator.

## Usage
### Using a Builder

### Creating identical widgets

### Forming a CustomGroup

### Setting custom callbacks for Buttons

### Forming a CustomButtonGrid

### Using a Generator
