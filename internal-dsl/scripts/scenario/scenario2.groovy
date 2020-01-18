sensor "button1" onPin 9
sensor "button2" onPin 10
actuator "buzzer" pin 11

state "on" means "buzzer" becomes "high"
state "transitionButton1" means "buzzer" becomes "low"
state "transitionButton2" means "buzzer" becomes "low"
state "off" means "buzzer" becomes "low"

initial "off"

from "on" to "transitionButton2" when "button1" becomes "low"
from "on" to "transitionButton1" when "button2" becomes "low"
from "transitionButton1" to "off" when "button1" becomes "low"
from "transitionButton1" to "on" when "button2" becomes "high"
from "transitionButton2" to "off" when "button2" becomes "low"
from "transitionButton2" to "on" when "button1" becomes "high"
from "off" to "transitionButton1" when "button1" becomes "high"
from "off" to "transitionButton2" when "button2" becomes "high"

export "Switch!"

