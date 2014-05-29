android-yolo-rxsensors
======================
An example demonstrating the use of [rxjava](https://github.com/Netflix/RxJava) and how it can be leveraged to create a stream of sensor 
data that can be subscribed to. 

[Dagger](https://github.com/square/dagger) is used to inject a singleton type "service" 
 provides access to the Ambient Light Sensor. `LightSensorService` has a `Subject` that 
 can be subscribed to by calling `LightSensorService#subscribeToSensor()`. 

`AmbientLightFragment` subscribes to the service and displays the raw value reported
from the sensor. 
