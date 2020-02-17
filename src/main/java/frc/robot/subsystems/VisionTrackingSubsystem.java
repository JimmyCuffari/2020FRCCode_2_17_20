/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import frc.robot.models.*;

public class VisionTrackingSubsystem extends SubsystemBase {
  private static final double[] DEF_DOUB_ARR = {0.0, 0.0, 0.0, 0.0, 0.0, 0.0};
  private NetworkTableInstance instance;
  private boolean validTarget;
  private double horizOffset;
  private double vertOffset;
  private double targetArea;
  private double rotation;
  private double latency; //in milliseconds
  private double shortSideLength; //shortes side of fitted bouding box(pixels)
  private double longSideLength; //longest side of fitted bounding box(pixels)
  private double horizSideLength; //Horizontal sidelength of the rough bounding box (0 - 320 pixels)
  private double vertSideLength; //Vertical sidelength of the rough bounding box (0 - 320 pixels)
  private int activePipeline; //0...9
  private CamTran camTran; //3D position calculations
  /**
   * Creates a new VisionTrackingSubsystem.
   */
  public VisionTrackingSubsystem () {
    instance = NetworkTableInstance.getDefault();
    validTarget = false;
    horizOffset = 0;
    vertOffset = 0;
    targetArea = 0;
    rotation = 0;
    latency = 0;
    shortSideLength = 0;
    longSideLength = 0;
    horizSideLength = 0;
    vertSideLength = 0;
    activePipeline = 0;
    camTran = new CamTran(0, 0, 0, 0, 0, 0);
  }

  /**
   * Set the variables with the current information from LimeLight
   */
  private void setInstanceVariables() {
    //TODO: Verify these are the correct data types. 
    //LimeLight documentation does not specify the retuned data types, so there might need to be
    //a couple debug sessions with the LimeLight server in order to see the specific data type returned
    //from the getEntry().get.. command.
    try {
      validTarget = instance.getEntry("").getBoolean(false);
      horizOffset = instance.getEntry("").getDouble(0);
      vertOffset = instance.getEntry("").getDouble(0);
      targetArea = instance.getEntry("").getDouble(0);
      rotation = instance.getEntry("").getDouble(0);
      latency = instance.getEntry("").getDouble(0);
      shortSideLength = instance.getEntry("").getDouble(0);
      longSideLength = instance.getEntry("").getDouble(0);
      horizSideLength = instance.getEntry("").getDouble(0);
      vertSideLength = instance.getEntry("").getDouble(0);
      activePipeline = (int) instance.getEntry("").getDouble(0);
      camTran = new CamTran(instance.getEntry("").getDoubleArray(DEF_DOUB_ARR));
    } catch (Exception e) {
      SmartDashboard.putString("VisionTrackingSystem", "EXCEPTION OCCURRED: " + e.getMessage());
    }
  }

  @Override
  public void periodic() {
    setInstanceVariables();
    SmartDashboard.putString("VisionTrackingSystem", generateCurrentDataString());
  }

  /**
   * Generate a String with current statistics from LimeLight vision
   */
  private String generateCurrentDataString() {
    String currentData = "";
    currentData += "TARGET VALID: " + validTarget + "\r\n";
    currentData += "HORIZ OFF: " + horizOffset + "\r\n";
    currentData += "VERT OFF: " + vertOffset + "\r\n";
    currentData += "TARGET AREA: " + targetArea + "\r\n";
    currentData += "ROT: " + rotation + "\r\n";
    currentData += "LATENCY: " + latency + "\r\n";
    currentData += "SHORT LENGTH: " + shortSideLength + "\r\n";
    currentData += "LONG LENGTH: " + longSideLength + "\r\n";
    currentData += "HORIZ LENGTH: " + horizSideLength + "\r\n";
    currentData += "VERT LENGTH: " + vertSideLength + "\r\n";
    currentData += "ACTIVE PIPE: " + activePipeline + "\r\n";
    currentData += "3D CORD: " + camTran + "\r\n";

    return currentData;
  }
}
