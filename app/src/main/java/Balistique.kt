//Air density assumes 0% humidity

//p  = Atmospheric Pressure (Pa)
//m  = Mass in (kg)
//a  = Angle in (deg)
//v  = Initial Velocity in (m/s)
//r  = Air Density in (kg/m^3)
//t  = Time in (s)
//T  = Temperature in (K)
//A  = Projected Area in (m^2) / Cross-Scetional Area
//Cd = Drag Coefficient
//Vt = Terminal Velocity in (m/s)
//Vy = Vertical Velocity in (m/s)
//Vx = Horizontal Velocity in (m/s)


import kotlin.math.*

object Balistique
{
    val G = 9.80665        //Earth's Gravity in (m/s)
    val RS = 287.058       //Specific gas constant for dry air in J/(kgK)

    fun toRadians (a: Double) : Double { return a * (PI / 180) }
    fun getXVelocity(a: Double, v: Double): Double  { return v * cos(toRadians(a)) }
    fun getYVelocity(a: Double, v: Double): Double  { return v * sin(toRadians(a)) }
    fun getAirDensity(p: Double, T: Double): Double { return (p / (RS * T)) }

    fun getTerminalVelocity(r: Double, Cd: Double, A: Double, m: Double): Double {
        return sqrt((2 * m * G)/(Cd * r * A))
    }

    //Get X position at time t
    fun getXPos(Vt: Double, v: Double, t: Double): Double {
        val Vt2 = Vt.pow(2)
        return (Vt2 / G) * ln((Vt2 + G * v * t) / Vt2)
    }

    //Get Peak Y value
    fun getYMax(Vt: Double, v: Double): Double {
        val v2  = v.pow(2)
        val vt2 = Vt.pow(2)

        return ((vt2)/(2*G)) * ln((v2 + vt2) / vt2)
    }

    //Get flight duration
    fun getTime(Vt: Double, v: Double): Double {
        return (Vt/G) * atan(v/Vt)
    }
}