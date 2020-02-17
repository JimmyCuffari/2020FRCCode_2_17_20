/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
  
  private static WPI_VictorSPX ShooterOne = new WPI_VictorSPX(ShooterConstants.kShooterOne);
  private static WPI_VictorSPX ShooterTwo = new WPI_VictorSPX(ShooterConstants.kShooterTwo);




  public Shooter() {

    
  }

  public void forwardShooter (double speed){
    ShooterOne.set(speed);
    ShooterTwo.set(speed);
  }

  public void reverseShooter (double speed){
    ShooterOne.set(-speed);
    ShooterTwo.set(-speed);
  }

  public void stopShooter (){
    ShooterOne.set(0);
    ShooterTwo.set(0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
