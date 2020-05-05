# Milk-Weight-Project

> A more interactive version is avaliable on [My blog](https://shaokang.ga/2020/projects/Milk-Weight-Project/).

This is a Group project for my CS400 class, named as Milk Weight Project. I will talk about my design and usage instruction in the following space. Detailed implementation could be view at [This repository](https://github.com/ShaokangJiang/Milk-Weight-Project). 

**Github Page:** https://github.com/ShaokangJiang/Milk-Weight-Project

**Compiled version:** https://github.com/ShaokangJiang/Milk-Weight-Project/releases

**Code version:** https://github.com/ShaokangJiang/Milk-Weight-Project/archive/0.9.zip

## Usage

#### Command Line Guide

It is possible to run from command line or use command line version to interrupt with basic feature, including import. It has two mode, command line mode and  command line start. In Command line start, you can specify your argument and then program will enter GUI automatically. In command line mode, you can decide the time you want to enter GUI interface. 

##### Command line mode

In this mode, you will see an interface like this in command mode:

```bash
Welcome to command mode of this program. Because of the requirement of this assignment,
 majority effort should be done in GUI. This part is only for command line import function. 
Usage: 	 -f "path/to/file" -- This file will be imported. Should be csv files. Seperate using ; if there are multiple files
	 -d "path/to/file" -- This will import any csv file in the directory, if any of them contain incorrect formatted data, no data will be imported 
	 -h -- print help message 
	 -g -- start GUI 

> 
```

To enter it:

> **Path to JavaFx:** The path to the library directory to JavaFx. Sample, `/home/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib`

```java
java --module-path "<Path to Javafx>" --add-modules javafx.controls,javafx.fxml -jar executable.jar -c
```

##### Command line start

In this mode, it has two options, either import a directory of csv files or import a list of files separate with ';';

To import a directory of csv files: use `-d` option, e.g. `-d \home\csv\`. To import a list of files separate with ';', no option argument required, simple add absolute path of files and separate different files with ';', e.g. `\home\csv\a.csv;\home\csv\b.csv` 

Program will start GUI automatically after importing. 

To enter GUI with importing a list of files separate with ';':

```java
java --module-path "<Path to Javafx>" --add-modules javafx.controls,javafx.fxml -jar executable.jar <Path to csv file>
```

Or, start with importing a directory: 

```java
java --module-path "<Path to Javafx>" --add-modules javafx.controls,javafx.fxml -jar executable.jar -d <Path to csv directory>
```

#### Running

##### Download from release page or compile from source.

> **Path to JavaFx:** The path to the library directory to JavaFx. Sample, `/home/openjfx-11.0.2_linux-x64_bin-sdk/javafx-sdk-11.0.2/lib`

###### Download from release page on Github:

1. Download [JavaFX](https://openjfx.io/) (their official website) and download `executable.jar` from release page
2. `java --module-path "<Path to Javafx>" --add-modules javafx.controls,javafx.fxml -jar executable.jar`

###### Download and compile from source page:

> **NOT RECOMMEND** as too many arguments, dependencies and it is easy to be wrong

#### Source data:

Source data should be in the format, it means this farm export those amount of milk in this date:

```csv
date,farm_id,weight
2019-1-1,Farm 0,6760
2019-1-1,Farm 1,8644
2019-1-1,Farm 2,3547
2019-1-2,Farm 0,6824
```

We are provided with the sample data from professor [Deppeler](http://pages.cs.wisc.edu/~deppeler/). Sample data format is designed and required for this project by professor [Deppeler](http://pages.cs.wisc.edu/~deppeler/). More sample data could be downloaded from [Deb's website](https://pages.cs.wisc.edu/~deppeler/cs400/assignments/ateam/files/). 

## Design

An overall agreement of this design is that those function should not only work for this project but also for other project. 

#### Basic components:

![](https://shaokang.ga/2020/projects/Milk-Weight-Project/2.png)

It contains the structure to use in this program. 

#### Manager&Report:

![](https://shaokang.ga/2020/projects/Milk-Weight-Project/3.png)

It could be the heart of this program's non-GUI part, it contains method to handle with file, manage farmers. 

Report part is to generate different GUI report and then let it show up in the major window, `Notification.java`.

#### GUI components:

![](https://shaokang.ga/2020/projects/Milk-Weight-Project/1.png)

Components that are not shown in the graph are two, `alert1.java`, `ChoiceWindow.java`. One component is the alert class to show various alerting information.  `ChoiceWindow.java` has all window required for choosing. Such as choosing for filter categories. This is a helper class to generate interface for user to select filter or other categories they want. Such as, choosing which function you want to use to manage data

The other are major part either control or show interfaces to user.  `Main.java` also has the responsibility to handle command line input. 

#### Detailed design

A detailed class by class design document and agreement could be view at here: 

> This is the initial thought of design by me **before** the actual implementation, so the data structure used to store farmers has changed to `Hashmap` and there are  lot of minor changes. To see the latest version, go to [This repository](https://github.com/ShaokangJiang/Milk-Weight-Project). 
>
> And I have no idea about JavaFx when I design this structure, so it is possible that some places have structure error. 

<object data="https://shaokang.ga/2020/projects/Milk-Weight-Project/1.pdf" type="application/pdf" width="700px" height="700px">
    <embed src="https://shaokang.ga/2020/projects/Milk-Weight-Project/1.pdf">
        <p>This browser does not support PDFs. Please download the PDF to view it: <a href="https://shaokang.ga/2020/projects/Milk-Weight-Project/1.pdf">Download PDF</a>.</p>
    </embed>
</object>

#### Special notes:

**It requires oracle java 1.9(openjdk on linux) or above**

## Credit/Copyright info

In case needed, this is an assignment for my CS400 course. Data source and project requirement is done by professor [Deb Deppeler](http://pages.cs.wisc.edu/~deppeler/). They have copyright for multiple stuff in this project, including but not limit to the requirement of this assignment, data source. and components required for this project, including farm report, annual report, etc. 

The design and implementation is mostly done by me. Contribution of each person in this project could be viewed from the commits page of the [git repo](https://github.com/ShaokangJiang/Milk-Weight-Project).

## Last Note:

`JavaFX` should be good for multi-platform usage and it is easy to build a good, well-formed application. But it is a little bit different than the other, including `swing`, `awt`. So, we can not use the same thought to think about `JavaFX`.
