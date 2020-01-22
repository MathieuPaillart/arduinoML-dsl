sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 11

state "noise" means buzzer becomes HIGH
state "ledOn" means led becomes HIGH and buzzer becomes LOW
state "off" means led becomes LOW and buzzer becomes LOW

initial off

from noise to ledOn when button becomes HIGH
from ledOn to off when button becomes HIGH
from off to noise when button becomes HIGH

export "multiStateAlarm"
