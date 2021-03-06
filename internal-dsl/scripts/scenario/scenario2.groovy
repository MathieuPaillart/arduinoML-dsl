sensor "button1" onPin 9
sensor "button2" onPin 10
actuator "buzzer" pin 11

state "on" means buzzer becomes HIGH
state "transitionButton1" means buzzer becomes LOW
state "transitionButton2" means buzzer becomes LOW
state "off" means buzzer becomes LOW

initial off

from on to transitionButton2 when button1 becomes LOW
from on to transitionButton1 when button2 becomes LOW
from transitionButton1 to off when button1 becomes LOW
from transitionButton1 to on when button2 becomes HIGH
from transitionButton2 to on when button1 becomes HIGH
from transitionButton2 to off when button2 becomes LOW
from off to transitionButton1 when button1 becomes HIGH
from off to transitionButton2 when button2 becomes HIGH

export "dualCheckAlarm"

