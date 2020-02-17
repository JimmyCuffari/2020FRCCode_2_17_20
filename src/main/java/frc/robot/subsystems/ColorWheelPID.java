/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.controller.PIDController;
import edu.wpi.first.wpilibj.controller.SimpleMotorFeedforward;
import edu.wpi.first.wpilibj2.command.PIDSubsystem;
import frc.robot.Constants.ColorWheelConstants;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class ColorWheelPID extends PIDSubsystem {
  /**
   * Creates a new ColorWheelPID.
   */

   public static WPI_TalonSRX colorWheel = new WPI_TalonSRX (ColorWheelConstants.kcolorWheel);

   public static PIDController colorWheelPID; 
  
   public final SimpleMotorFeedforward m_colorWheelFeedForward = 
   new SimpleMotorFeedforward(0.004, (12/13180));

  public ColorWheelPID() {
    super(
        // The PIDController used by the subsystem

        colorWheelPID = new PIDController(0.0001, 0, 0));
        colorWheelPID.setTolerance(200);
        colorWheelPID.setSetpoint(20000);

        //SmartDashboard.putNumber("kP", colorWheelPID.getP());
        //SmartDashboard.putNumber("kI", colorWheelPID.getI());
        //SmartDashboard.putNumber("kD", colorWheelPID.getD());
        //SmartDashboard.putNumber("Tolerance", 200);
        //SmartDashboard.putNumber("Setpoint", 20000);
  }

  public void colorWheelForward (double speed){
    colorWheel.set(speed);
    SmartDashboard.putNumber("Position",colorWheel.getSelectedSensorPosition());
  }

  public void colorWheelReverse (double speed){
    colorWheel.set(-speed);
    colorWheel.getSelectedSensorPosition();
    SmartDashboard.putNumber("Position",colorWheel.getSelectedSensorPosition());
  }

  public void colorWheelStop (){
    colorWheel.set(0);
    colorWheel.getSelectedSensorPosition();
    SmartDashboard.putNumber("Position",colorWheel.getSelectedSensorPosition());
  }

  public boolean atSetpoint(){
    return colorWheelPID.atSetpoint();
  }

  public void setkP(double kP){
   colorWheelPID.setP(kP);
  }

  public void setkI(double kI){
    colorWheelPID.setI(kI);
  }

  public void setkD(double kD) {
    colorWheelPID.setD(kD);
  }

  public void resetPIDValues(){
    colorWheelPID.setP(SmartDashboard.getNumber("kP", colorWheelPID.getP())); 
    colorWheelPID.setI(SmartDashboard.getNumber("kI", colorWheelPID.getI()));
    colorWheelPID.setD(SmartDashboard.getNumber("kD", colorWheelPID.getD()));
    colorWheelPID.setTolerance(SmartDashboard.getNumber("Tolerance", 200));
    colorWheelPID.setSetpoint(SmartDashboard.getNumber("Setpoint", 20000));
  }

  public void resetEncoder(){
    colorWheel.getSensorCollection().setQuadraturePosition(0, 10);
  }
  @Override
  public void useOutput(double output, double setpoint) {
    colorWheel.set(output + m_colorWheelFeedForward.calculate(setpoint));

  }

  @Override
  public double getMeasurement() {
    return colorWheel.getSelectedSensorPosition();
  }
}
