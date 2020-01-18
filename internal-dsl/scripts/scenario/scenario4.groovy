sensor "button" onPin 9
actuator "led" pin 12
actuator "buzzer" pin 11

state "noise" means "buzzer" becomes "high"
state "ledOn" means "led" becomes "high" and "buzzer" becomes "low"
state "off" means "led" becomes "low" and "buzzer" becomes "low"

initial "off"

from "noise" to "ledOn" when "button" becomes "high"
from "ledOn" to "off" when "button" becomes "high"
from "off" to "noise" when "button" becomes "high"

export "Switch!"
