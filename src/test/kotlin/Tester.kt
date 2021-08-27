import javafx.scene.paint.CycleMethod
import javafx.scene.paint.RadialGradient
import javafx.scene.paint.Stop
import tornadofx.c

fun main() {
    println(RadialGradient.valueOf("radial-gradient(center 50% -40%, radius 200%, #b8ee36 45%, #80c800 50%)"))
    println(
        RadialGradient(
            0.0, 0.0, 0.5, -0.4,
            0.0, true, CycleMethod.NO_CYCLE,
            Stop(0.15, c("0076ea")), Stop(0.9, c("0076ea")), Stop(1.0, c("0483ff"))
        )
    )
    println(Stop(0.15, c("0076ea")))
}